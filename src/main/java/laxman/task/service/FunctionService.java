package laxman.task.service;

import laxman.task.interfaces.IFunction;
import laxman.task.interfaces.IFunctionRequest;
import laxman.task.interfaces.IService;

public class FunctionService implements IService {

	@Override
	public void execute(IFunction function, IFunctionRequest request) {
		function.apply(request);
	}

}
