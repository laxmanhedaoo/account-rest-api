package laxman.task.function.account;

import io.vertx.ext.web.RoutingContext;
import laxman.task.async.AccountAsync;
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
public class GetAccountByIdFunction implements IFunction {
	private HttpProvider httpProvider = new HttpProvider();

	@Builder
	@Data
	public static class Request implements IFunctionRequest {
		private Long accountId;
		@Builder.Default
		private AccountAsync accountAsync = BeanInjector.getInstance(AccountAsync.class);
		private RoutingContext context;
	}

	public void apply(IFunctionRequest functionRequest) {
		Request request = (Request) functionRequest;
		request.getAccountAsync().accountById(request.getAccountId(), subscriber -> {
			httpProvider.handleResponse(subscriber, request.getContext().response());
		});
	}
}
