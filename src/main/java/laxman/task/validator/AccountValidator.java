package laxman.task.validator;

import java.util.Optional;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import laxman.task.config.BeanInjector;
import laxman.task.enums.CurrencyEnum;
import laxman.task.interfaces.IDataService;
import laxman.task.model.Account;
import laxman.task.model.Amount;

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

	public Optional<String> validateWithdraw(Long accountId, String accountJson, Account existingAccount) {
		if (isNull(accountJson) || accountJson.isEmpty()) {
			return Optional.of("No account information provided to withdraw");
		}
		try {
			Amount amount = Json.decodeValue(accountJson, Amount.class);
			if (!existingAccount.getCurrency().equals(amount.getCurrency())) {
				return Optional.of(
						"Currency not matched. Expected currency is " + existingAccount.getCurrency().currencyCode());
			}

			if (amount.getMoney() < 100) {
				return Optional.of("Minimum withdrawn amount is 100.0");
			}
			if (amount.getMoney() % 100 != 0) {
				return Optional.of("withdrawn amount must be multiple of 100.00");
			}
			if (existingAccount.getBalance() < amount.getMoney()) {
				return Optional.of("Can't withdraw! Insufficient balance in account");
			}
		} catch (DecodeException de) {
			return Optional.of(de.getMessage());
		}
		return Optional.empty();
	}

	public Optional<String> validateDeposit(Long accountId, String accountJson, CurrencyEnum currency) {
		if (isNull(accountJson) || accountJson.isEmpty()) {
			return Optional.of("No account information provided to deposit");
		}
		try {
			Amount amount = Json.decodeValue(accountJson, Amount.class);
			if (!currency.equals(amount.getCurrency())) {
				return Optional.of("Currency not matched. Expected currency is " + currency.currencyCode());
			}
			if (amount.getMoney() <= 0) {
				return Optional.of("Minimum deposit amount is 1.0");
			}
		} catch (DecodeException de) {
			return Optional.of(de.getMessage());
		}
		return Optional.empty();
	}

}
