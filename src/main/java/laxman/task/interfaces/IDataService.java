package laxman.task.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import laxman.task.model.Account;
import laxman.task.model.Transfer;

/**
 * @author hedaoo
 *
 */
public interface IDataService {

	Optional<Account> findAccountById(Long id);

	void save(Account account);

	List<Account> findAll();

	void save(Transfer transfer);

	Optional<Transfer> findTransferById(UUID transferId);

	List<Transfer> findAllTransfers();

	void update(Account account);
}
