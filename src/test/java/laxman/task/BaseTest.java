package laxman.task;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.ext.unit.TestContext;
import laxman.task.BaseTest.TestFunction;
import laxman.task.enums.ConstantEnum;

public class BaseTest {

	protected final int PORT = Integer.valueOf(ConstantEnum.HOST_PORT.val());
	protected final String HOST = ConstantEnum.HOST.val();
	Vertx vertx;

	@Before
	public void before(TestContext context) throws IOException {
		vertx = Vertx.vertx();
		vertx.deployVerticle(HttpServer.class.getName(), context.asyncAssertSuccess());
	}

	@After
	public void after(TestContext context) {
		vertx.close(context.asyncAssertSuccess());
	}

	public HttpClient client() {
		return vertx.createHttpClient();
	}

	public static class TestFunction {

		public void test(HttpClientResponse response) {

		}
	}

	protected void accountById(TestContext context, Long accountId, HttpClient client, TestFunction testFunction) {
		client.getNow(PORT, HOST, "/api/v1/accounts/" + accountId, response -> {
			testFunction.test(response);
		});
	}

	protected void accounts(TestContext context, HttpClient client, TestFunction testFunction) {
		client.getNow(PORT, HOST, "/api/v1/accounts/", response -> {
			testFunction.test(response);
		});
	}
}
