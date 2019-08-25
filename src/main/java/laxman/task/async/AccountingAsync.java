package laxman.task.async;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import laxman.task.config.BeanInjector;
import laxman.task.model.Transfer;
import laxman.task.service.AccountingService;

public class AccountingAsync {

	private AccountingService calculationService = BeanInjector.getInstance(AccountingService.class);

	public void onTransfer(Transfer transfer, Handler<AsyncResult<Boolean>> transferHandler) {

		Future<Boolean> future = Future.future();
		future.setHandler(transferHandler);

		try {
			future.complete(calculationService.processMoney(transfer.getSourceAccountId(),
					transfer.getTargetAccountId(), transfer.getAmount()));
		} catch (Exception ex) {
			future.fail(ex);
		}
	}
}
