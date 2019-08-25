package laxman.task.controller;

import java.util.Optional;
import java.util.Random;

import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import laxman.task.config.BeanInjector;
import laxman.task.function.account.FindAllFunction;
import laxman.task.function.account.GetAccountByIdFunction;
import laxman.task.function.account.SaveAccountFunction;
import laxman.task.function.account.UpdateAccountFunction;
import laxman.task.interfaces.IFunctionRequest;
import laxman.task.interfaces.IService;
import laxman.task.model.Account;
import laxman.task.model.ResponsePayload;
import laxman.task.provider.HttpProvider;
import laxman.task.validator.AccountValidator;

/**
 * @author hedaoo
 *
 */
public class AccountController {

	private IService functionService = BeanInjector.getInstance(IService.class);
	private AccountValidator validator = BeanInjector.getInstance(AccountValidator.class);
	private HttpProvider httpProvider = BeanInjector.getInstance(HttpProvider.class);

	public void endpoints(Router router) {
		router.get("/api/v1/accounts").handler(this::findAllAccounts);
		router.get("/api/v1/accounts/:id").handler(this::findAccountById);
		router.post("/api/v1/accounts").handler(this::createAccount);
		router.put("/api/v1/accounts/:id").handler(this::updateAccount);

	}

	private void findAccountById(RoutingContext context) {

		Optional<String> validateResponse = validator.validateAccountId(context.request().getParam("id"));
		if (validateResponse.isPresent()) {
			httpProvider.sendHttpResponse(ResponsePayload.builder().message(validateResponse.get()).status(400).build(),
					context.response());
		} else {
			Long accountId = Long.valueOf(context.request().getParam("id"));
			IFunctionRequest request = GetAccountByIdFunction.Request.builder().accountId(accountId).context(context)
					.build();

			functionService.execute(new GetAccountByIdFunction(), request);

		}
	}

	private void findAllAccounts(RoutingContext context) {
		IFunctionRequest request = FindAllFunction.Request.builder().context(context).build();
		functionService.execute(new FindAllFunction(), request);
	}

	private void createAccount(RoutingContext context) {

		Optional<String> validateResponse = validator.validateAccount(context.getBodyAsString());

		if (validateResponse.isPresent()) {
			httpProvider.sendHttpResponse(ResponsePayload.builder().message(validateResponse.get()).status(400).build(),
					context.response());
		} else {
			Account newAccount = Json.decodeValue(context.getBodyAsString(), Account.class);

			newAccount.setAccountId(Math.abs(new Random().nextLong()));

			IFunctionRequest request = SaveAccountFunction.Request.builder().account(newAccount).context(context)
					.build();

			functionService.execute(new SaveAccountFunction(), request);
		}
	}

	private void updateAccount(RoutingContext context) {
		String accountIdStr = context.request().getParam("id");
		Optional<String> validateResponse = validator.validateAccountId(accountIdStr);
		if (validateResponse.isPresent()) {
			httpProvider.sendHttpResponse(ResponsePayload.builder().message(validateResponse.get()).status(400).build(),
					context.response());
		} else {
			validateResponse = validator.validateUpdateAccount(accountIdStr, context.getBodyAsString());

			if (validateResponse.isPresent()) {
				httpProvider.sendHttpResponse(
						ResponsePayload.builder().message(validateResponse.get()).status(400).build(),
						context.response());
			} else {
				Long accountId = Long.valueOf(context.request().getParam("id"));

				Account account = Json.decodeValue(context.getBodyAsString(), Account.class);
				account.setAccountId(accountId);

				IFunctionRequest request = UpdateAccountFunction.Request.builder().account(account).context(context)
						.build();

				functionService.execute(new UpdateAccountFunction(), request);
			}
		}
	}
}
