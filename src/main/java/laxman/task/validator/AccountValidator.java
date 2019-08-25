package laxman.task.validator;

import java.util.Optional;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import laxman.task.config.BeanInjector;
import laxman.task.interfaces.IDataService;
import laxman.task.model.Account;

public class AccountValidator extends Validator {

	IDataService dataService = BeanInjector.getInstance(IDataService.class);

	public Optional<String> validateAccountId(String id) {
		if (validateId(id)) {
			Optional<Account> account = dataService.findAccountById(Long.valueOf(id));
			if (!account.isPresent()) {
				return Optional.of("No Account found");
			}
		} else {
			return Optional.of("Invalid Account Id");
		}
		return Optional.empty();
	}

	public Optional<String> validateAccount(String accountJson) {

		if (isNull(accountJson) || accountJson.isEmpty()) {
			return Optional.of("No account details provided");
		}
		try {
			Account account = Json.decodeValue(accountJson, Account.class);
			if (isNull(account.getCurrency())) {
				return Optional.of("Currency required");
			}
			if (isNull(account.getName())) {
				return Optional.of("name is required");
			}
			if (isNull(account.getBalance())) {
				return Optional.of("balance is required");
			}

			if (account.getBalance() < 0) {
				return Optional.of("Minimum balance should be 0.0");
			}
		} catch (DecodeException de) {
			return Optional.of(de.getMessage());
		}
		return Optional.empty();
	}

	public Optional<String> validateUpdateAccount(String accountId, String accountJson) {
		if (isNull(accountJson) || accountJson.isEmpty()) {
			return Optional.of("No account information provided to update");
		}
		try {
			Account account = Json.decodeValue(accountJson, Account.class);
			if (!isNull(account.getAccountId())) {
				return Optional.of("Account Id can not be updated");
			}
			if (account.getBalance() < 0) {
				return Optional.of("Minimum balance should be 0.0");
			}
		} catch (DecodeException de) {
			return Optional.of(de.getMessage());
		}
		return Optional.empty();
	}

}
