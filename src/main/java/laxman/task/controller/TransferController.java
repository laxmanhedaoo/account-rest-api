package laxman.task.controller;

import java.util.Optional;
import java.util.UUID;

import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import laxman.task.config.BeanInjector;
import laxman.task.function.transfer.FindAllTransfersFunction;
import laxman.task.function.transfer.SendTransferFunction;
import laxman.task.function.transfer.TransferByIdFunction;
import laxman.task.interfaces.IFunctionRequest;
import laxman.task.interfaces.IService;
import laxman.task.model.ResponsePayload;
import laxman.task.model.Transfer;
import laxman.task.provider.HttpProvider;
import laxman.task.validator.TransferValidator;

/**
 * @author hedaoo
 *
 */
public class TransferController {

	private IService functionService = BeanInjector.getInstance(IService.class);
	private TransferValidator validator = BeanInjector.getInstance(TransferValidator.class);
	private HttpProvider httpProvider = BeanInjector.getInstance(HttpProvider.class);

	public void endpoints(Router router) {
		router.get("/api/v1/transfers").handler(this::findAll);
		router.get("/api/v1/transfers/:id").handler(this::transferById);
		router.post("/api/v1/transfers").handler(this::transfer);
	}

	private void transferById(RoutingContext context) {
		Optional<String> validateResponse = validator.validateTransferId(context.request().getParam("id"));
		if (validateResponse.isPresent()) {
			httpProvider.sendHttpResponse(ResponsePayload.builder().message(validateResponse.get()).status(400).build(),
					context.response());
		} else {
			UUID transferId = UUID.fromString(context.request().getParam("id"));
			IFunctionRequest request = TransferByIdFunction.Request.builder().transferId(transferId).context(context)
					.build();
			functionService.execute(new TransferByIdFunction(), request);
		}
	}

	private void findAll(RoutingContext context) {
		IFunctionRequest request = FindAllTransfersFunction.Request.builder().context(context).build();
		functionService.execute(new FindAllTransfersFunction(), request);
	}

	private void transfer(RoutingContext context) {
		Optional<String> validateResponse = validator.validateTransfer(context.getBodyAsString());

		if (validateResponse.isPresent()) {
			httpProvider.sendHttpResponse(ResponsePayload.builder().message(validateResponse.get()).status(400).build(),
					context.response());
		} else {
			Transfer transfer = Json.decodeValue(context.getBodyAsString(), Transfer.class);
			transfer.setTransferId(UUID.randomUUID());
			IFunctionRequest request = SendTransferFunction.Request.builder().transfer(transfer).context(context)
					.build();
			functionService.execute(new SendTransferFunction(), request);
		}
	}
}
