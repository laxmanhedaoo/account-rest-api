package laxman.task.config;

import com.google.inject.AbstractModule;

import laxman.task.interfaces.IDataService;
import laxman.task.interfaces.IService;
import laxman.task.service.DataService;
import laxman.task.service.FunctionService;

/**
 * @author hedaoo
 *
 */
public class BeanInitializer extends AbstractModule {
	@Override
	protected void configure() {
		bind(IService.class).to(FunctionService.class);
		bind(IDataService.class).to(DataService.class);
	}
}
