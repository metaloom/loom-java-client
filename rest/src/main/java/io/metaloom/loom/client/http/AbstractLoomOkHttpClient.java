package io.metaloom.loom.client.http;

import static io.metaloom.loom.client.http.LoomClientRequest.DELETE;
import static io.metaloom.loom.client.http.LoomClientRequest.GET;
import static io.metaloom.loom.client.http.LoomClientRequest.PATCH;
import static io.metaloom.loom.client.http.LoomClientRequest.POST;
import static io.metaloom.loom.client.http.LoomClientRequest.PUT;

import java.time.Duration;

import io.metaloom.loom.rest.model.NoResponse;
import io.metaloom.loom.rest.model.RestRequestModel;
import io.metaloom.loom.rest.model.RestResponseModel;
import okhttp3.OkHttpClient;

public abstract class AbstractLoomOkHttpClient extends AbstractLoomClient {

	private OkHttpClient okClient;

	public AbstractLoomOkHttpClient(OkHttpClient okClient, String scheme, String hostname, int port, Duration connectTimeout, Duration readTimeout,
		Duration writeTimeout) {
		super(scheme, hostname, port, connectTimeout, readTimeout, writeTimeout);
		this.okClient = okClient;
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

	// @Override
	// public LoomClientRequest<LoomBinaryResponse> download(String collectionName, String snapshotName) {
	// return getRequest("download/" + collectionName + "/snapshots/" + snapshotName, LoomBinaryResponse.class);
	// }

	protected <T extends RestResponseModel<T>> LoomClientRequest<T> deleteRequest(String path, Class<T> responseClass) {
		return LoomClientRequest.create(DELETE, path, this, okClient, responseClass);
	}

	protected LoomClientRequest<NoResponse> deleteRequest(String path) {
		return LoomClientRequest.create(DELETE, path, this, okClient);
	}

	protected <T extends RestResponseModel<T>> LoomClientRequest<T> getRequest(String path, Class<T> responseClass) {
		return LoomClientRequest.create(GET, path, this, okClient, responseClass);
	}

	protected <T extends RestResponseModel<T>> LoomClientRequest<T> postRequest(String path, RestRequestModel request, Class<T> responseClass) {
		return LoomClientRequest.create(POST, path, this, okClient, request, responseClass);
	}

	protected <T extends RestResponseModel<T>> LoomClientRequest<T> postRequest(String path, Class<T> responseClass) {
		return LoomClientRequest.create(POST, path, this, okClient, responseClass);
	}

	protected <T extends RestResponseModel<T>> LoomClientRequest<T> putRequest(String path, RestRequestModel request, Class<T> responseClass) {
		return LoomClientRequest.create(PUT, path, this, okClient, request, responseClass);
	}

	protected <T extends RestResponseModel<T>> LoomClientRequest<T> patchRequest(String path, RestRequestModel request, Class<T> responseClass) {
		return LoomClientRequest.create(PATCH, path, this, okClient, request, responseClass);
	}
}
