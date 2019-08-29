package laxman.task.function.account;

import io.vertx.ext.web.RoutingContext;
import laxman.task.async.AccountAsync;
import laxman.task.config.BeanInjector;
import laxman.task.interfaces.IFunction;
import laxman.task.interfaces.IFunctionRequest;
import laxman.task.model.Amount;
import laxman.task.model.ResponsePayload;
import laxman.task.provider.HttpProvider;
import lombok.Builder;
import lombok.Data;

/**
 * @author hedaoo
 *
 */
public class DepositFunction implements IFunction {

	private HttpProvider httpProvider = new HttpProvider();

	@Builder
	@Data
	public static class Request implements IFunctionRequest {
		private Long accountId;
		private Amount amount;
		@Builder.Default
		private AccountAsync accountAsync = BeanInjector.getInstance(AccountAsync.class);
		private RoutingContext context;
	}

	public void apply(IFunctionRequest functionRequest) {
		Request request = (Request) functionRequest;
		request.getAccountAsync().deposit(request.getAccountId(), request.getAmount(), subscriber -> {

			if (subscriber.succeeded()) {
				httpProvider.sendHttpResponse(
						ResponsePayload.builder().message("Money deposited successfully").status(200).build(),
						request.context.response());
			} else {
				httpProvider.sendHttpResponse(
						ResponsePayload.builder().message(subscriber.cause().getMessage()).status(422).build(),
						request.context.response());
			}
		});
	}
}
