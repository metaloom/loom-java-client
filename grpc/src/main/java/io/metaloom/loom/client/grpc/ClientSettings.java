package io.metaloom.loom.client.grpc;

import java.util.function.Supplier;

import io.grpc.ManagedChannel;
import io.metaloom.loom.client.common.CommonSettings;
import io.metaloom.loom.client.grpc.method.LoomClientRequest;
import io.vertx.core.Future;

public interface ClientSettings extends CommonSettings {

	/**
	 * Return the prepared gRPC channel.
	 * 
	 * @return
	 */
	ManagedChannel channel();

	default <T> LoomClientRequest<T> request(Supplier<T> blockingSupplier,
		Supplier<Future<T>> asyncSupplier) {
		return new LoomClientRequest<>(this, blockingSupplier, asyncSupplier);
	}

}
