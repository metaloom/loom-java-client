package io.metaloom.loom.client.http;

import java.time.Duration;

public abstract class AbstractLoomClient implements LoomHttpClient {

	protected final String scheme;
	protected final String hostname;
	protected final int port;

	protected final Duration connectTimeout;
	protected final Duration readTimeout;
	protected final Duration writeTimeout;
	protected String token;

	/**
	 * @param scheme
	 * @param hostname
	 * @param port
	 * @param connectTimeout
	 * @param readTimeout
	 * @param writeTimeout
	 */
	protected AbstractLoomClient(String scheme, String hostname, int port, Duration connectTimeout, Duration readTimeout, Duration writeTimeout) {
		this.scheme = scheme;
		this.hostname = hostname;
		this.port = port;

		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
		this.writeTimeout = writeTimeout;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public String getScheme() {
		return scheme;
	}

	@Override
	public String getHostname() {
		return hostname;
	}

	@Override
	public Duration getConnectTimeout() {
		return connectTimeout;
	}

	@Override
	public Duration getReadTimeout() {
		return readTimeout;
	}

	@Override
	public Duration getWriteTimeout() {
		return writeTimeout;
	}

	@Override
	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String getToken() {
		return token;
	}
	
	
	
}
