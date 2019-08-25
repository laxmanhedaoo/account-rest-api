package laxman.task.function.transfer;

import io.vertx.ext.web.RoutingContext;
import laxman.task.async.AccountingAsync;
import laxman.task.async.NotifyAsync;
import laxman.task.async.TransferAsync;
import laxman.task.config.BeanInjector;
import laxman.task.interfaces.IFunction;
import laxman.task.interfaces.IFunctionRequest;
import laxman.task.model.ResponsePayload;
import laxman.task.model.Transfer;
import laxman.task.provider.HttpProvider;
import lombok.Builder;
import lombok.Data;

/**
 * @author hedaoo
 *
 */
public class SendTransferFunction implements IFunction {

	private HttpProvider httpProvider = BeanInjector.getInstance(HttpProvider.class);
	private AccountingAsync accountingAsync = BeanInjector.getInstance(AccountingAsync.class);
	private NotifyAsync notify = BeanInjector.getInstance(NotifyAsync.class);

	@Builder
	@Data
	public static class Request implements IFunctionRequest {
		private Transfer transfer;
		@Builder.Default
		private TransferAsync transferAsync = BeanInjector.getInstance(TransferAsync.class);
		private RoutingContext context;
	}

	public void apply(IFunctionRequest functionRequest) {
		Request request = (Request) functionRequest;

		request.getTransferAsync().send(request.getTransfer(), transferSubscriber -> {
			if (transferSubscriber.succeeded()) {
				if (transferSubscriber.result()) {
					/* Transfer money to the target account */
					accountingAsync.onTransfer(request.getTransfer(), accountingSubscriber -> {

						if (accountingSubscriber.succeeded()) {
							httpProvider.transferHttpResponse(request.transfer, request.getContext().response());
							notify.onTransfer(request.transfer, notificationHandler -> {
								// handle notify response
							});

						} else {
							httpProvider.sendHttpResponse(
									ResponsePayload.builder()
											.message("Unable to transfer money : "
													+ accountingSubscriber.cause().getMessage())
											.status(422).build(),
									request.getContext().response());
						}
					});
				} else {
					httpProvider.sendHttpResponse(ResponsePayload.builder()
							.message("Failed to store transfer details, please try after some time.").status(422)
							.build(), request.getContext().response());
				}
			} else {
				httpProvider.sendHttpResponse(
						ResponsePayload.builder().message(transferSubscriber.cause().getMessage()).status(422).build(),
						request.getContext().response());
			}

		});

	}
}
