package laxman.task.config;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class BeanInjector<T> {

	private static Injector injector;

	static {
		injector = Guice.createInjector(new BeanInitializer());
	}

	private BeanInjector() {
	}

	public static <T> T getInstance(Class<T> beanClass) {
		return injector.getInstance(beanClass);
	}
}
