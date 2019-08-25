package laxman.task.provider;

import com.google.inject.Singleton;

import io.vertx.ext.web.Router;
import laxman.task.controller.AccountController;
import laxman.task.controller.TransferController;

/**
 * @author hedaoo
 *
 */
@Singleton
public class EndpointRegistry {

	public void register(Router router) {
		new AccountController().endpoints(router);
		new TransferController().endpoints(router);
	}

}
