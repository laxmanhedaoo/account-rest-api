package laxman.task.controller;

import java.util.Optional;
import java.util.Random;

import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import laxman.task.config.BeanInjector;
import laxman.task.function.account.DepositFunction;
import laxman.task.function.account.FindAllFunction;
import laxman.task.function.account.GetAccountByIdFunction;
import laxman.task.function.account.SaveAccountFunction;
import laxman.task.function.account.UpdateAccountFunction;
import laxman.task.function.account.WithdrawFunction;
import laxman.task.interfaces.IDataService;
import laxman.task.interfaces.IFunctionRequest;
import laxman.task.interfaces.IService;
import laxman.task.model.Account;
import laxman.task.model.Amount;
import laxman.task.model.ResponsePayload;
import laxman.task.provider.HttpProvider;
import laxman.task.validator.AccountValidator;

/**
 * @author hedaoo
 *
 */
public class AccountController {

	private Logger LOG = LoggerFactory.getLogger(AccountController.class);
	private IService functionService = BeanInjector.getInstance(IService.class);
	private AccountValidator validator = BeanInjector.getInstance(AccountValidator.class);
	private HttpProvider httpProvider = BeanInjector.getInstance(HttpProvider.class);
	private IDataService dataService = BeanInjector.getInstance(IDataService.class);

	public void endpoints(Router router) {
		router.get("/api/v1/accounts").handler(this::findAllAccounts);
		router.get("/api/v1/accounts/:id").handler(this::findAccountById);
		router.post("/api/v1/accounts").handler(this::createAccount);
		router.put("/api/v1/accounts/:id").handler(this::updateAccount);
		router.post("/api/v1/accounts/:id/withdraw").handler(this::withdrawAmount);
		router.post("/api/v1/accounts/:id/deposit").handler(this::depositAmount);
		router.get("/api/v1/accounts/:id/balance").handler(this::checkBalance);

	}

	private void findAccountById(RoutingContext context) {

		Optional<String> validateResponse = validator.validateAccountId(context.request().getParam("id"));
		if (validateResponse.isPresent()) {
			httpProvider.sendHttpResponse(ResponsePayload.builder().message(validateResponse.get()).status(404).build(),
					context.response());
		} else {
			Long accountId = Long.valueOf(context.request().getParam("id"));
			LOG.info("Request to get details of " + accountId + " account");
			IFunctionRequest request = GetAccountByIdFunction.Request.builder().accountId(accountId).context(context)
					.build();

			functionService.execute(new GetAccountByIdFunction(), request);

		}
	}

	private void findAllAccounts(RoutingContext context) {
		LOG.info("Request to list all accounts");
		IFunctionRequest request = FindAllFunction.Request.builder().context(context).build();
		functionService.execute(new FindAllFunction(), request);
	}

	private void createAccount(RoutingContext context) {
		LOG.info("Request to create new account");
		Optional<String> validateResponse = validator.validateAccount(context.getBodyAsString());

		if (validateResponse.isPresent()) {
			httpProvider.sendHttpResponse(ResponsePayload.builder().message(validateResponse.get()).status(400).build(),
					context.response());
		} else {
			Account newAccount = Json.decodeValue(context.getBodyAsString(), Account.class);

			newAccount.setAccountId(Math.abs(new Random().nextLong()));

			IFunctionRequest request = SaveAccountFunction.Request.builder().account(newAccount).context(context)
					.build();
			LOG.info("Request is valid");
			functionService.execute(new SaveAccountFunction(), request);
		}
	}

	private void updateAccount(RoutingContext context) {
		String accountIdStr = context.request().getParam("id");
		LOG.info("Request to update '" + accountIdStr + "' account");
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
				LOG.debug("Account Request : " + account);
				IFunctionRequest request = UpdateAccountFunction.Request.builder().account(account).context(context)
						.build();
				LOG.info("Request is valid");
				functionService.execute(new UpdateAccountFunction(), request);
			}
		}
	}

	private void withdrawAmount(RoutingContext context) {

		Optional<String> validateResponse = validator.validateAccountId(context.request().getParam("id"));
		if (validateResponse.isPresent()) {
			httpProvider.sendHttpResponse(ResponsePayload.builder().message(validateResponse.get()).status(400).build(),
					context.response());
		} else {
			Long accountId = Long.valueOf(context.request().getParam("id"));
			Account existingAccount = dataService.findAccountById(accountId).get();

			validateResponse = validator.validateWithdraw(accountId, context.getBodyAsString(), existingAccount);

			if (validateResponse.isPresent()) {
				httpProvider.sendHttpResponse(
						ResponsePayload.builder().message(validateResponse.get()).status(400).build(),
						context.response());
			} else {
				LOG.info("Request to withdraw amount for " + accountId + " account");
				Amount amount = Json.decodeValue(context.getBodyAsString(), Amount.class);

				IFunctionRequest request = WithdrawFunction.Request.builder().accountId(accountId).amount(amount)
						.context(context).build();

				functionService.execute(new WithdrawFunction(), request);
			}
		}
	}

	private void depositAmount(RoutingContext context) {

		Optional<String> validateResponse = validator.validateAccountId(context.request().getParam("id"));
		if (validateResponse.isPresent()) {
			httpProvider.sendHttpResponse(ResponsePayload.builder().message(validateResponse.get()).status(400).build(),
					context.response());
		} else {
			Long accountId = Long.valueOf(context.request().getParam("id"));
			Account existingAccount = dataService.findAccountById(accountId).get();

			validateResponse = validator.validateDeposit(accountId, context.getBodyAsString(),
					existingAccount.getCurrency());

			if (validateResponse.isPresent()) {
				httpProvider.sendHttpResponse(
						ResponsePayload.builder().message(validateResponse.get()).status(400).build(),
						context.response());
			} else {
				LOG.info("Request to deposit amount for " + accountId + " account");
				Amount amount = Json.decodeValue(context.getBodyAsString(), Amount.class);

				IFunctionRequest request = DepositFunction.Request.builder().accountId(accountId).amount(amount).context(context)
						.build();

				functionService.execute(new DepositFunction(), request);
			}
		}
	}

	private void checkBalance(RoutingContext context) {

		Optional<String> validateResponse = validator.validateAccountId(context.request().getParam("id"));
		if (validateResponse.isPresent()) {
			httpProvider.sendHttpResponse(ResponsePayload.builder().message(validateResponse.get()).status(400).build(),
					context.response());
		} else {
			Long accountId = Long.valueOf(context.request().getParam("id"));
			LOG.info("Request to get balance of " + accountId + " account");
			IFunctionRequest request = GetAccountByIdFunction.Request.builder().accountId(accountId).context(context)
					.build();

			functionService.execute(new GetAccountByIdFunction(), request);

		}
	}

}
