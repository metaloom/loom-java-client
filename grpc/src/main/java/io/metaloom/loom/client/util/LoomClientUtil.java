package io.metaloom.loom.client.util;

import java.util.concurrent.TimeUnit;

import io.metaloom.loom.client.grpc.ClientSettings;
import io.reactivex.rxjava3.core.Maybe;
import io.vertx.core.Future;

public final class LoomClientUtil {

	private LoomClientUtil() {
	}

	public static <T> Maybe<T> toMaybe(Future<T> fut, ClientSettings settings) {
		return Maybe.fromFuture(fut.toCompletionStage().toCompletableFuture(),
			settings.getReadTimeout().toMillis(),
			TimeUnit.MILLISECONDS);
	}
}
