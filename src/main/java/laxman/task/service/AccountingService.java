package laxman.task.service;

import java.util.Optional;

import laxman.task.config.BeanInjector;
import laxman.task.interfaces.IDataService;
import laxman.task.model.Account;
import lombok.SneakyThrows;

public class AccountingService {

	private IDataService dataService = BeanInjector.getInstance(IDataService.class);

	public void processMoney(Account sourceAccount, Account targetAccount, Double amount) {
		sourceAccount.setBalance(sourceAccount.getBalance() - amount);
		targetAccount.setBalance(targetAccount.getBalance() + amount);
	}

	@SneakyThrows
	public Boolean processMoney(Long sourceAccountId, Long targetAccountId, Double amount) {

		Optional<Account> sourceAccount = dataService.findAccountById(sourceAccountId);
		Optional<Account> targetAccount = dataService.findAccountById(targetAccountId);

		processMoney(sourceAccount.get(), targetAccount.get(), amount);
		return true;
	}

}
