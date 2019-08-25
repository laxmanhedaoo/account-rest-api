package laxman.task.validator;

import java.util.Optional;
import java.util.UUID;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import laxman.task.config.BeanInjector;
import laxman.task.interfaces.IDataService;
import laxman.task.model.Account;
import laxman.task.model.Transfer;

public class TransferValidator extends Validator {
	IDataService dataService = BeanInjector.getInstance(IDataService.class);

	public Optional<String> validateTransferId(String id) {
		if (validateId(id)) {

			Optional<Transfer> transfer = dataService.findTransferById(UUID.fromString(id));
			if (!transfer.isPresent()) {
				return Optional.of("No Transfer found");
			}
		} else {
			return Optional.of("Invalid Transfer Id");
		}
		return Optional.empty();
	}

	public Optional<String> validateTransfer(String transferJson) {

		if (isNull(transferJson) || transferJson.isEmpty()) {
			return Optional.of("No Transfer details provided");
		}
		try {
			Transfer transfer = Json.decodeValue(transferJson, Transfer.class);
			if (isNull(transfer.getCurrency())) {
				return Optional.of("Currency required");
			}
			if (isNull(transfer.getSourceAccountId())) {
				return Optional.of("sourceAccountId is required");
			}
			if (isNull(transfer.getTargetAccountId())) {
				return Optional.of("targetAccountId is required");
			}
			if (isNull(transfer.getAmount())) {
				return Optional.of("amount is required");
			}
			if (transfer.getAmount() < 1) {
				return Optional.of("Transfer amount must be more than 1" + transfer.getCurrency().currency());
			}

			if (transfer.getSourceAccountId().equals(transfer.getTargetAccountId())) {
				return Optional.of("Transfer to self account not allowed");
			}
			Optional<Account> sourceAccount = dataService.findAccountById(transfer.getSourceAccountId());
			if (!sourceAccount.isPresent()) {
				return Optional.of("invalid source account");
			}

			Optional<Account> targetAccount = dataService.findAccountById(transfer.getTargetAccountId());
			if (!targetAccount.isPresent()) {
				return Optional.of("Invalid target account");
			}

			if (!sourceAccount.get().getCurrency().equals(transfer.getCurrency())) {
				return Optional
						.of("Source account's currency not matched with " + transfer.getCurrency().currencyCode());
			}
			if (!targetAccount.get().getCurrency().equals(transfer.getCurrency())) {
				return Optional
						.of("Target account's currency not matched with " + transfer.getCurrency().currencyCode());
			}
		} catch (DecodeException de) {
			return Optional.of(de.getMessage());
		}
		return Optional.empty();
	}

}
