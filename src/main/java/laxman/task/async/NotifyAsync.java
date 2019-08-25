package laxman.task.async;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import laxman.task.model.Transfer;

/**
 * @author hedaoo
 *
 */
public class NotifyAsync {

	public void onTransfer(Transfer transfer, Handler<AsyncResult<Boolean>> notificationHandler) {
		// TODO - Notify via SMS or Email on recent transfer
	}

	public void onFailedTransfer(Transfer transfer, Handler<AsyncResult<Boolean>> notificationHandler) {
		// TODO - Notify via SMS or Email on failed transfer
	}

}
