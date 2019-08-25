package laxman.task.async;

import java.util.List;
import java.util.UUID;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import laxman.task.config.BeanInjector;
import laxman.task.interfaces.IDataService;
import laxman.task.model.Transfer;

/**
 * @author hedaoo
 *
 */
public class TransferAsync {

	private IDataService dataService = BeanInjector.getInstance(IDataService.class);

	public void tranferById(UUID transferId, Handler<AsyncResult<Transfer>> transferHandler) {

		Future<Transfer> future = Future.future();
		future.setHandler(transferHandler);

		try {
			future.complete(dataService.findTransferById(transferId).get());
		} catch (Exception ex) {
			future.fail(ex);
		}
	}

	public void send(Transfer transfer, Handler<AsyncResult<Boolean>> transferHandler) {

		Future<Boolean> future = Future.future();
		future.setHandler(transferHandler);

		try {
			dataService.save(transfer);
			future.complete(Boolean.TRUE);
		} catch (Exception ex) {
			future.fail(ex);
		}
	}

	public void findAll(Handler<AsyncResult<List<Transfer>>> transferHandler) {

		Future<List<Transfer>> future = Future.future();
		future.setHandler(transferHandler);

		try {
			future.complete(dataService.findAllTransfers());
		} catch (Exception ex) {
			future.fail(ex);
		}
	}
}
