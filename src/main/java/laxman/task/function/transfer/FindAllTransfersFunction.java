package laxman.task.function.transfer;

import io.vertx.ext.web.RoutingContext;
import laxman.task.async.TransferAsync;
import laxman.task.config.BeanInjector;
import laxman.task.interfaces.IFunction;
import laxman.task.interfaces.IFunctionRequest;
import laxman.task.provider.HttpProvider;
import lombok.Builder;
import lombok.Data;

/**
 * @author hedaoo
 *
 */
public class FindAllTransfersFunction implements IFunction {
	private HttpProvider httpProvider = BeanInjector.getInstance(HttpProvider.class);

	@Builder
	@Data
	public static class Request implements IFunctionRequest {
		@Builder.Default
		private TransferAsync transferAsync = BeanInjector.getInstance(TransferAsync.class);
		private RoutingContext context;
	}

	public void apply(IFunctionRequest functionRequest) {
		Request request = (Request) functionRequest;
		request.getTransferAsync().findAll(subscriber -> {
			httpProvider.handleResponse(subscriber, request.getContext().response());
		});
	}
}
