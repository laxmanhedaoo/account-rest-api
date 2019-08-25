package laxman.task;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import laxman.task.config.BeanInjector;
import laxman.task.enums.ConstantEnum;
import laxman.task.provider.EndpointRegistry;
import laxman.task.provider.HttpProvider;
import laxman.task.service.TestDataService;

/**
 * @author hedaoo
 *
 */
public class HttpServer extends AbstractVerticle {

	private EndpointRegistry endpointRegistry = BeanInjector.getInstance(EndpointRegistry.class);
	private HttpProvider httpProvider = BeanInjector.getInstance(HttpProvider.class);
	private TestDataService testDataService = BeanInjector.getInstance(TestDataService.class);

	@Override
	public void start(Future<Void> future) {

		Router router = Router.router(vertx);

		routerSettings(router);

		endpointRegistry.register(router);

		createServer(router, future);

		testDataService.createTestData();
	}

	private void createServer(Router router, Future<Void> future) {
		vertx.createHttpServer().requestHandler(router::accept).listen(Integer.valueOf(ConstantEnum.SERVER_PORT.val()),
				ConstantEnum.SERVER_IP.val(), response -> {
					if (response.succeeded())
						future.complete();
					else
						future.fail(response.cause());
				});
	}

	private void routerSettings(Router router) {
		router.route().handler(CorsHandler.create("*").allowedHeaders(httpProvider.allowHeaders())
				.allowedMethods(httpProvider.allowMethods()));

		router.route().handler(BodyHandler.create());
	}

}
