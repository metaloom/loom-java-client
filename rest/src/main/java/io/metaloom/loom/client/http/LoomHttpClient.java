package io.metaloom.loom.client.http;

import java.time.Duration;

import io.metaloom.loom.client.http.impl.LoomHttpClientImpl;

public interface LoomHttpClient extends ClientMethods, ClientSettings, AutoCloseable {

	static LoomHttpClientImpl.Builder builder() {
		return LoomHttpClientImpl.builder();
	}

	String API_V1_PATH = "/api/v1";

	/**
	 * Return the configured protocol scheme.
	 *
	 * @return
	 */
	String getScheme();

	/**
	 * Return the configured server hostname.
	 *
	 * @return
	 */
	String getHostname();

	/**
	 * Return the configured server port.
	 *
	 * @return
	 */
	int getPort();

	/**
	 * Return the configured prefix for the API calls.
	 * 
	 * @return
	 */
	String getPathPrefix();

	/**
	 * Close the client and release all resources.
	 */
	void close();

	/**
	 * Return the configured connect timeout.
	 *
	 * @return
	 */
	Duration getConnectTimeout();

	/**
	 * Return the configured read timeout.
	 *
	 * @return
	 */
	Duration getReadTimeout();

	/**
	 * Return the configured write timeout.
	 *
	 * @return
	 */
	Duration getWriteTimeout();

	/**
	 * Set the token to be used for authentication.
	 *
	 * @param token
	 */
	void setToken(String token);

	/**
	 * Return the used authentication token.
	 *
	 * @return
	 */
	String getToken();

}
