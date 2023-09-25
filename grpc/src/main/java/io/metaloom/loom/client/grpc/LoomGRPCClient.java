package io.metaloom.loom.client.grpc;

import io.metaloom.loom.client.grpc.impl.LoomGRPCClientImpl;
import io.metaloom.loom.client.grpc.method.AssetMethods;

public interface LoomGRPCClient extends AssetMethods, ClientSettings, AutoCloseable {

	static LoomGRPCClientImpl.Builder builder() {
		return LoomGRPCClientImpl.builder();
	}

	// TODO add version info
	String USER_AGENT = "Loom gRPC Client";

	/**
	 * Close the prepared transport channel.
	 */
	void close();

	/**
	 * Set the token that will be used to authenticate the requests.
	 * 
	 * @param token
	 */
	void setToken(String token);

}
