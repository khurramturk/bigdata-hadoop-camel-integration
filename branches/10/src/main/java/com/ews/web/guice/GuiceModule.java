package com.ews.web.guice;

import java.util.List;
import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class GuiceModule extends AbstractModule {

	private final List<ClassPair> pairs;
	private final Properties props;

	public GuiceModule() {
		this(null, null);
	}

	public GuiceModule(final List<ClassPair> pairs) {
		this(pairs, null);
	}

	public GuiceModule(final List<ClassPair> pairs, final Properties props) {
		this.pairs = pairs;
		this.props = props;
	}

	protected final void configure() {
		if (pairs != null) {
			for (ClassPair pair : pairs) {
				bind(pair.getService()).to(pair.getImpl());
			}
		}
	}

	public static class ClassPair<T> {

		private final Class<T> service;
		private final Class<? extends T> impl;

		public ClassPair(final Class<T> service, final Class<? extends T> impl) {

			this.service = service;
			this.impl = impl;

		}

		public final Class<T> getService() {
			return service;
		}

		public final Class<? extends T> getImpl() {
			return impl;
		}

	}

	@Provides
	final Properties providesProperties() {
		return props;
	}

}
