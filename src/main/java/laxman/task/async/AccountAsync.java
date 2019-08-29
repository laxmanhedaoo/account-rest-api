package laxman.task.async;

import java.util.List;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import laxman.task.config.BeanInjector;
import laxman.task.interfaces.IDataService;
import laxman.task.model.Account;
import laxman.task.model.Amount;

/**
 * @author hedaoo
 *
 */
public class AccountAsync {

	private IDataService dataService = BeanInjector.getInstance(IDataService.class);

	public void accountById(Long accountId, Handler<AsyncResult<Account>> accountHandler) {

		Future<Account> future = Future.future();
		future.setHandler(accountHandler);

		try {
			future.complete(dataService.findAccountById(accountId).get());
		} catch (Exception ex) {
			future.fail(ex);
		}
	}

	public void save(Account account, Handler<AsyncResult<Boolean>> accountHandler) {

		Future<Boolean> future = Future.future();
		future.setHandler(accountHandler);

		try {
			dataService.save(account);
			future.complete(Boolean.TRUE);
		} catch (Exception ex) {
			future.fail(ex);
		}
	}

	public void findAll(Handler<AsyncResult<List<Account>>> accountHandler) {

		Future<List<Account>> future = Future.future();
		future.setHandler(accountHandler);

		try {
			future.complete(dataService.findAll());
		} catch (Exception ex) {
			future.fail(ex);
		}
	}

	public void update(Account account,Handler<AsyncResult<Boolean>> accountHandler) {

		Future<Boolean> future = Future.future();
		future.setHandler(accountHandler);

		try {
			dataService.update(account);
			future.complete(Boolean.TRUE);
		} catch (Exception ex) {
			future.fail(ex);
		}
		// TODO Auto-generated method stub
		
	}
	public void withdraw(Long accountId, Amount amount,Handler<AsyncResult<Boolean>> accountHandler) {

		Future<Boolean> future = Future.future();
		future.setHandler(accountHandler);

		try {
			dataService.withdraw(accountId, amount);
			future.complete(Boolean.TRUE);
		} catch (Exception ex) {
			future.fail(ex);
		}
		// TODO Auto-generated method stub
		
	}
	public void deposit(Long accountId, Amount amount,Handler<AsyncResult<Boolean>> accountHandler) {

		Future<Boolean> future = Future.future();
		future.setHandler(accountHandler);

		try {
			dataService.deposit(accountId, amount);
			future.complete(Boolean.TRUE);
		} catch (Exception ex) {
			future.fail(ex);
		}
		// TODO Auto-generated method stub
		
	}
}
