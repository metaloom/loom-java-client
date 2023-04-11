package io.metaloom.loom.client.common;

import java.time.Duration;

public interface CommonSettings {

	/**
	 * Return the configured Loom Server port for the client connection.
	 * 
	 * @return
	 */
	int getPort();

	/**
	 * Return the configured host for the client connection.
	 * 
	 * @return
	 */
	String getHostname();

	/**
	 * Timeout for server connections.
	 * 
	 * @return
	 */
	Duration getConnectTimeout();

	/**
	 * Timeout for read operations.
	 * 
	 * @return
	 */
	Duration getReadTimeout();

	/**
	 * Timeout for write operations.
	 * 
	 * @return
	 */
	Duration getWriteTimeout();

}
