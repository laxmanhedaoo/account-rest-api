package laxman.task.controller;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import laxman.task.BaseTest;
import laxman.task.model.Account;
import lombok.val;

@RunWith(VertxUnitRunner.class)
public class AccountControllerTest extends BaseTest {

	@Test
	public void hasAnyAccount(TestContext context) {
		Async async = context.async();
		HttpClient client = client();

		accounts(context, client, new TestFunction() {
			@Override
			public void test(HttpClientResponse response) {
				context.assertEquals(response.statusCode(), 200);

				response.bodyHandler(body -> {
					final Account[] accounts = Json.decodeValue(body.toString(), Account[].class);
					context.assertTrue(accounts.length > 0);
					client.close();
					async.complete();

				});
			}
		});
	}

	@Test
	public void findAccountByValidId(TestContext context) {
		Async async = context.async();
		HttpClient client = client();

		accounts(context, client, new TestFunction() {
			@Override
			public void test(HttpClientResponse response) {
				context.assertEquals(response.statusCode(), 200);

				response.bodyHandler(body -> {
					final Account[] accounts = Json.decodeValue(body.toString(), Account[].class);
					context.assertTrue(accounts.length > 0);

					async.complete();
					val async2 = context.async();
					accountById(context, accounts[0].getAccountId(), client, new TestFunction() {
						@Override
						public void test(HttpClientResponse response) {
							context.assertEquals(response.statusCode(), 200);

							response.bodyHandler(body2 -> {
								final Account[] accounts2 = Json.decodeValue(body.toString(), Account[].class);
								context.assertTrue(accounts2.length > 0);
								context.assertTrue(Arrays.stream(accounts2).anyMatch(a -> a.getAccountId().equals(1)));
								client.close();
								async2.complete();
							});
						}
					});

				});
			}
		});

	}

	@Test
	public void findAccountByFakeId(TestContext context) {
		Async async = context.async();
		HttpClient client = client();
		Long accountId = 1L;
		accountById(context, accountId, client, new TestFunction() {
			@Override
			public void test(HttpClientResponse response) {
				context.assertEquals(response.statusCode(), 404);

				response.bodyHandler(body -> {
					context.assertTrue(body.toString().contains("No Account found"));
					client.close();
					async.complete();
				});
			}
		});
	}

}
