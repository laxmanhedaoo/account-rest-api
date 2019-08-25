package laxman.task.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import laxman.task.config.BeanInjector;
import laxman.task.enums.CurrencyEnum;
import laxman.task.interfaces.IDataService;
import laxman.task.model.Account;
import laxman.task.model.Transfer;
import laxman.task.provider.DateProvier;

public class TestDataService {

	private IDataService dummyDataService = BeanInjector.getInstance(IDataService.class);

	public void createTestData() {
		createDummyAccounts();
		createDummyTransfers();
	}

	private void createDummyAccounts() {
		for (int i = 1; i <= 10; i++) {
			dummyDataService.save(createDummyAccount(i));
		}
	}

	private void createDummyTransfers() {
		for (int index = 0; index < 5; index++) {
			dummyDataService.save(dummyTransfer(index));
		}
	}

	private Account createDummyAccount(int index) {
		long prefix = 15671753194730L;
		Account account = new Account();
		account.setAccountId(prefix + index);
		account.setName("Customer" + index);
		account.setCreatedTime(DateProvier.dateAsString(new Date()));
		account.setBalance(1000.00);
		account.setCurrency(CurrencyEnum.USD);
		return account;
	}

	private Transfer dummyTransfer(int index) {
		List<Account> accounts = dummyDataService.findAll();
		Transfer transfer = new Transfer();
		Account sourceAccount = accounts.get(index);
		Account targetAccount = accounts.get(index + 1);
		transfer.setTransferId(UUID.randomUUID());
		transfer.setAmount(10.00);
		transfer.setCurrency(CurrencyEnum.USD);
		transfer.setSourceAccountId(sourceAccount.getAccountId());
		transfer.setTargetAccountId(targetAccount.getAccountId());
		transfer.setComment("Test");
		return transfer;
	}

}
