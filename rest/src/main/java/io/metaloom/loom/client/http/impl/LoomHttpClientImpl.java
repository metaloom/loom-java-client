package io.metaloom.loom.client.http.impl;

import static io.metaloom.loom.client.http.LoomClientRequest.DELETE;
import static io.metaloom.loom.client.http.LoomClientRequest.GET;
import static io.metaloom.loom.client.http.LoomClientRequest.PATCH;
import static io.metaloom.loom.client.http.LoomClientRequest.POST;
import static io.metaloom.loom.client.http.LoomClientRequest.PUT;

import java.time.Duration;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.metaloom.loom.client.http.AbstractLoomClient;
import io.metaloom.loom.client.http.LoomClientRequest;
import io.metaloom.loom.client.http.LoomHttpClient;
import io.metaloom.loom.rest.model.RestRequestModel;
import io.metaloom.loom.rest.model.RestResponseModel;
import io.metaloom.loom.rest.model.user.UserResponse;
import okhttp3.OkHttpClient;

/**
 * Implementation for the {@link LoomHttpClient}
 */
public class LoomHttpClientImpl extends AbstractLoomClient {

	public static final Logger log = LoggerFactory.getLogger(LoomHttpClientImpl.class);

	public static Builder builder() {
		return new Builder();
	}

	private OkHttpClient okClient;

	/**
	 * Create a new client with a default timeout of 10s for all timeouts (connect, read and write).
	 * 
	 * @param okClient
	 * @param scheme
	 * @param hostname
	 * @param port
	 * @param connectTimeout
	 * @param readTimeout
	 * @param writeTimeout
	 */
	protected LoomHttpClientImpl(OkHttpClient okClient, String scheme, String hostname, int port, Duration connectTimeout, Duration readTimeout,
		Duration writeTimeout) {
		super(scheme, hostname, port, connectTimeout, readTimeout, writeTimeout);
		this.okClient = okClient;
	}

	/**
	 * @deprecated No longer needed. OkHttpClient will be initialized in the builder
	 */
	@Deprecated
	public void init() {
	}

	/**
	 * Return the used OK HTTP client.
	 * 
	 * @return
	 */
	public OkHttpClient getOkHttpClient() {
		return okClient;
	}

	@Override
	public void close() {
		// Not needed for OkClient
	}

	public static class Builder {

		private String scheme = "http";
		private String hostname = "localhost";
		private int port = 6333;

		private Duration connectTimeout = Duration.ofMillis(10_000);
		private Duration readTimeout = Duration.ofMillis(10_000);
		private Duration writeTimeout = Duration.ofMillis(10_000);
		private OkHttpClient okClient;

		/**
		 * Verify the builder and build the client.
		 * 
		 * @return
		 */
		public LoomHttpClientImpl build() {
			Objects.requireNonNull(scheme, "A protocol scheme has to be specified.");
			Objects.requireNonNull(hostname, "A hostname has to be specified.");

			if (okClient == null) {
				okClient = createDefaultOkHttpClient();
			}

			return new LoomHttpClientImpl(okClient, scheme, hostname, port, connectTimeout, readTimeout, writeTimeout);
		}

		private OkHttpClient createDefaultOkHttpClient() {
			okhttp3.OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(connectTimeout);
			builder.readTimeout(readTimeout);
			builder.writeTimeout(writeTimeout);
			// // Disable gzip
			// builder.addInterceptor(chain -> {
			// Request request = chain.request();
			// Request newRequest;
			// try {
			// newRequest = request.newBuilder().addHeader("Accept-Encoding", "identity").build();
			// } catch (Exception e) {
			// log.error("Error while creating new request", e);
			// return chain.proceed(request);
			// }
			// return chain.proceed(newRequest);
			// });
			return builder.build();
		}

		/**
		 * Set the protocol scheme to be used for the client (e.g.: http, https).
		 * 
		 * @param scheme
		 * @return Fluent API
		 */
		public Builder setScheme(String scheme) {
			this.scheme = scheme;
			return this;
		}

		/**
		 * Set the hostname for the client.
		 * 
		 * @param hostname
		 * @return Fluent API
		 */
		public Builder setHostname(String hostname) {
			this.hostname = hostname;
			return this;
		}

		/**
		 * Set a custom http client to be used. A default client will be generated if non is specified.
		 * 
		 * @param okClient
		 * @return
		 */
		public Builder setOkHttpClient(OkHttpClient okClient) {
			this.okClient = okClient;
			return this;
		}

		/**
		 * Set the port to connect to. (e.g. 6333).
		 * 
		 * @param port
		 * @return Fluent API
		 */
		public Builder setPort(int port) {
			this.port = port;
			return this;
		}

		/**
		 * Set connection timeout.
		 * 
		 * @param connectTimeout
		 * @return Fluent API
		 */
		public Builder setConnectTimeout(Duration connectTimeout) {
			if (okClient != null) {
				throw new RuntimeException("Please configure the timeout on the okHttpClient you provided.");
			}
			this.connectTimeout = connectTimeout;
			return this;
		}

		/**
		 * Set read timeout for the client.
		 * 
		 * @param readTimeout
		 * @return Fluent API
		 */
		public Builder setReadTimeout(Duration readTimeout) {
			if (okClient != null) {
				throw new RuntimeException("Please configure the timeout on the okHttpClient you provided.");
			}
			this.readTimeout = readTimeout;
			return this;
		}

		/**
		 * Set write timeout for the client.
		 * 
		 * @param writeTimeout
		 * @return Fluent API
		 */
		public Builder setWriteTimeout(Duration writeTimeout) {
			if (okClient != null) {
				throw new RuntimeException("Please configure the timeout on the okHttpClient you provided.");
			}
			this.writeTimeout = writeTimeout;
			return this;
		}

	}

	// REST Methods

	@Override
	public LoomClientRequest<UserResponse> getUserResponse() {
		return getRequest("users", UserResponse.class);
	}

	// @Override
	// public LoomClientRequest<LoomBinaryResponse> downloadCollectionSnapshot(String collectionName, String snapshotName) {
	// assertCollectionName(collectionName);
	// return getRequest("collections/" + collectionName + "/snapshots/" + snapshotName, LoomBinaryResponse.class);
	// }

	private <T extends RestResponseModel> LoomClientRequest<T> deleteRequest(String path, Class<T> responseClass) {
		return LoomClientRequest.create(DELETE, path, this, okClient, responseClass);
	}

	private <T extends RestResponseModel> LoomClientRequest<T> getRequest(String path, Class<T> responseClass) {
		return LoomClientRequest.create(GET, path, this, okClient, responseClass);
	}

	private <T extends RestResponseModel> LoomClientRequest<T> postRequest(String path, RestRequestModel request, Class<T> responseClass) {
		return LoomClientRequest.create(POST, path, this, okClient, request, responseClass);
	}

	private <T extends RestResponseModel> LoomClientRequest<T> postRequest(String path, Class<T> responseClass) {
		return LoomClientRequest.create(POST, path, this, okClient, responseClass);
	}

	private <T extends RestResponseModel> LoomClientRequest<T> putRequest(String path, RestRequestModel request, Class<T> responseClass) {
		return LoomClientRequest.create(PUT, path, this, okClient, request, responseClass);
	}

	private <T extends RestResponseModel> LoomClientRequest<T> patchRequest(String path, RestRequestModel request, Class<T> responseClass) {
		return LoomClientRequest.create(PATCH, path, this, okClient, request, responseClass);
	}

}
