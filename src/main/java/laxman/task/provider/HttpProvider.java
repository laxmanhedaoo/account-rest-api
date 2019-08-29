package laxman.task.provider;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import io.vertx.core.AsyncResult;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import laxman.task.enums.TransferStateEnum;
import laxman.task.model.Account;
import laxman.task.model.AccountResponsePayload;
import laxman.task.model.ResponsePayload;
import laxman.task.model.Transfer;
import laxman.task.model.TransferResponsePayload;

/**
 * @author hedaoo
 *
 */
public class HttpProvider {

	private Logger LOG = LoggerFactory.getLogger(HttpProvider.class);
	
	public Set<String> allowHeaders() {
		Set<String> headers = new HashSet<>();
		headers.add("x-requested-with");
		headers.add("Access-Control-Allow-Origin");
		headers.add("origin");
		headers.add("Content-Type");
		headers.add("accept");
		return headers;
	}

	public Set<HttpMethod> allowMethods() {
		Set<HttpMethod> httpMethods = new HashSet<>();
		httpMethods.add(HttpMethod.GET);
		httpMethods.add(HttpMethod.POST);
		httpMethods.add(HttpMethod.DELETE);
		httpMethods.add(HttpMethod.PUT);
		return httpMethods;
	}

	public void handleResponse(AsyncResult<?> subscriber, HttpServerResponse httpServerResponse) {
		if (subscriber.succeeded()) {
			if (subscriber.result() != null) {
				sendSuccess(Json.encodePrettily(subscriber.result()), httpServerResponse);
			} else {
				sendSuccess(httpServerResponse);
			}
		} else {
			sendHttpResponse(subscriber.cause().getMessage(), 422, httpServerResponse);
		}
	}

	private void sendSuccess(HttpServerResponse response) {
		LOG.info("200;");
		response.setStatusCode(200).putHeader("content-type", "application/json; charset=utf-8").end();
	}

	private void sendSuccess(String responseBody, HttpServerResponse response) {
		LOG.info("200;request processed");
		response.setStatusCode(200).putHeader("content-type", "application/json; charset=utf-8").end(responseBody);
	}

	public void sendHttpResponse(ResponsePayload payload, HttpServerResponse response) {
		LOG.info(payload.getStatus() + ";" + Json.encodePrettily(payload));
		response.setStatusCode(payload.getStatus()).putHeader("content-type", "application/json; charset=utf-8")
				.end(Json.encodePrettily(payload));
	}

	public void sendHttpResponse(Object data, int status, HttpServerResponse response) {
		LOG.info(status + ";" + Json.encodePrettily(data));
		response.setStatusCode(status).putHeader("content-type", "application/json; charset=utf-8")
				.end(Json.encodePrettily(data));
	}

	public void transferHttpResponse(Transfer transfer, HttpServerResponse response) {
		TransferResponsePayload payload = new TransferResponsePayload();
		payload.setTransferId(transfer.getTransferId());
		payload.setStatus(200);
		payload.setTransferState(TransferStateEnum.COMPLETED);
		payload.setTransferDate(DateProvier.dateAsString(new Date()));
		payload.setMessage("transfer request processed");
		sendHttpResponse(payload, response);
	}

	public void accountHttpResponse(Account account, HttpServerResponse response) {
		// TODO Auto-generated method stub
		AccountResponsePayload payload = new AccountResponsePayload();
		payload.setAccountId(account.getAccountId());
		payload.setCreatedOn(DateProvier.dateAsString(new Date()));
		payload.setMessage("successful");
		payload.setStatus(200);
		sendHttpResponse(payload, response);
	}
}
