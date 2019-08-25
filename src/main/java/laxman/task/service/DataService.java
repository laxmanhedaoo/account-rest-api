package laxman.task.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.google.inject.Singleton;

import laxman.task.cache.DataStore;
import laxman.task.config.BeanInjector;
import laxman.task.interfaces.IDataService;
import laxman.task.model.Account;
import laxman.task.model.Transfer;
import lombok.SneakyThrows;

/**
 * @author hedaoo
 *
 */
@Singleton
public class DataService implements IDataService {

	private DataStore dataStore = BeanInjector.getInstance(DataStore.class);

	@Override
	@SneakyThrows
	public Optional<Account> findAccountById(Long id) {
		Optional<Account> account = dataStore.findById(id);
		return account;
	}

	@Override
	public void save(Account account) {
		dataStore.save(account);
	}

	@Override
	public List<Account> findAll() {
		return dataStore.findAll();
	}

	@Override
	public void save(Transfer transfer) {
		dataStore.save(transfer);
	}

	@Override
	public Optional<Transfer> findTransferById(UUID transferId) {
		return dataStore.findTransferById(transferId);
	}

	@Override
	public List<Transfer> findAllTransfers() {
		return dataStore.findAllTransfers();
	}

	@Override
	public void update(Account account) {
		dataStore.update(account);
	}

}
