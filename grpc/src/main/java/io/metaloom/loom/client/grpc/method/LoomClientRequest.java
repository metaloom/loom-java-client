package io.metaloom.loom.client.grpc.method;

import java.util.function.Supplier;

import io.metaloom.loom.client.grpc.ClientSettings;
import io.metaloom.loom.client.util.LoomClientUtil;
import io.reactivex.rxjava3.core.Maybe;
import io.vertx.core.Future;

public class LoomClientRequest<T> {

	private final Supplier<T> blocking;
	private final Supplier<Future<T>> async;
	private final ClientSettings settings;

	public LoomClientRequest(ClientSettings settings, Supplier<T> blockingSupplier, Supplier<Future<T>> asyncSupplier) {
		this.settings = settings;
		this.blocking = blockingSupplier;
		this.async = asyncSupplier;
	}

	/**
	 * Execute the request synchronously / blocking.
	 * 
	 * @return
	 */
	public T sync() {
		return blocking.get();
	}

	/**
	 * Execute the request asynchronously.
	 * 
	 * @return
	 */
	public Future<T> async() {
		return async.get();
	}

	/**
	 * Execute the the request asynchronously via RxJava.
	 * 
	 * @return
	 */
	public Maybe<T> rx() {
		return LoomClientUtil.toMaybe(async(), settings);
	}
}
