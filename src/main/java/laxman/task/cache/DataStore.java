package laxman.task.cache;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;

import laxman.task.enums.ConstantEnum;
import laxman.task.model.Account;
import laxman.task.model.Transfer;
import lombok.SneakyThrows;

/**
 * @author hedaoo
 *
 */
@Singleton
public class DataStore {

	private static final ConcurrentMap<String, Object> cache = new ConcurrentHashMap<>();

	@SuppressWarnings("unchecked")
	public List<Account> findAll() {
		if (null == cache.get(ConstantEnum.ACCOUNTS_KEY.val())) {
			cache.put(ConstantEnum.ACCOUNTS_KEY.val(), Lists.newArrayList());
		}
		return (List<Account>) cache.get(ConstantEnum.ACCOUNTS_KEY.val());
	}

	public Optional<Account> findById(Long id) {
		List<Account> accounts = findAll();
		return accounts.stream().filter(a -> a.getAccountId().equals(id)).findFirst();
	}

	public void save(Account account) {
		List<Account> accounts = findAll();
		accounts.add(account);
	}

	public void update(Account account) {
		Optional<Account> optional = findById(account.getAccountId());
		Account existingAccount = optional.get();

		if (null != account.getName()) {
			existingAccount.setName(account.getName());
		}
		if (null != account.getBalance()) {
			existingAccount.setBalance(account.getBalance());
		}
		if (null != account.getCurrency()) {
			existingAccount.setCurrency(account.getCurrency());
		}
	}

	@SuppressWarnings("unchecked")
	public List<Transfer> findAllTransfers() {
		if (null == cache.get(ConstantEnum.TRANSFER_KEY.val())) {
			cache.put(ConstantEnum.TRANSFER_KEY.val(), Lists.newArrayList());
		}
		return (List<Transfer>) cache.get(ConstantEnum.TRANSFER_KEY.val());
	}

	@SneakyThrows
	public void save(Transfer transfer) {

		Long sourceAccountId = transfer.getSourceAccountId();

		Optional<Account> optional = findById(sourceAccountId);
		Account account = optional.get();

		if (account.getBalance() < transfer.getAmount()) {
			throw new Exception("Insufficient balance for transfer " + transfer.getAmount()
					+ transfer.getCurrency().currencyCode());
		}

		List<Transfer> transfers = findAllTransfers();
		transfers.add(transfer);

	}

	public Optional<Transfer> findTransferById(UUID transferId) {
		List<Transfer> transfers = findAllTransfers();
		return transfers.stream().filter(t -> t.getTransferId().equals(transferId)).findFirst();
	}

}
