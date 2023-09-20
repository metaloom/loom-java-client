package io.metaloom.loom.client.http;

import static io.metaloom.loom.client.http.LoomClientRequest.DELETE;
import static io.metaloom.loom.client.http.LoomClientRequest.GET;
import static io.metaloom.loom.client.http.LoomClientRequest.PATCH;
import static io.metaloom.loom.client.http.LoomClientRequest.POST;
import static io.metaloom.loom.client.http.LoomClientRequest.PUT;

import java.io.InputStream;
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

	protected <T extends RestResponseModel<T>> LoomClientRequest<T> postUploadRequest(String path, Class<T> responseClass,
		InputStream binaryData, String mimeType) {
		return LoomClientRequest.createBinaryRequest(POST, path, this, okClient, responseClass, binaryData, mimeType);
	}

	protected LoomClientRequest<LoomBinaryResponse> getDownloadRequest(String path) {
		return LoomClientRequest.createDownloadRequest(GET, path, this, okClient, LoomBinaryResponse.class);
	}

	protected <T extends RestResponseModel<T>> LoomClientRequest<T> deleteRequest(String path, Class<T> responseClass) {
		return LoomClientRequest.createNoBodyRequest(DELETE, path, this, okClient, responseClass);
	}

	protected LoomClientRequest<NoResponse> deleteRequest(String path) {
		return LoomClientRequest.createNoResponseRequest(DELETE, path, this, okClient);
	}

	protected <T extends RestResponseModel<T>> LoomClientRequest<T> getRequest(String path, Class<T> responseClass) {
		return LoomClientRequest.createNoBodyRequest(GET, path, this, okClient, responseClass);
	}

	protected <T extends RestResponseModel<T>> LoomClientRequest<T> postRequest(String path, RestRequestModel request, Class<T> responseClass) {
		return LoomClientRequest.createJsonRequest(POST, path, this, okClient, request, responseClass);
	}

	protected <T extends RestResponseModel<T>> LoomClientRequest<T> postRequest(String path, Class<T> responseClass) {
		return LoomClientRequest.createNoBodyRequest(POST, path, this, okClient, responseClass);
	}

	protected <T extends RestResponseModel<T>> LoomClientRequest<T> putRequest(String path, RestRequestModel request, Class<T> responseClass) {
		return LoomClientRequest.createJsonRequest(PUT, path, this, okClient, request, responseClass);
	}

	protected <T extends RestResponseModel<T>> LoomClientRequest<T> patchRequest(String path, RestRequestModel request, Class<T> responseClass) {
		return LoomClientRequest.createJsonRequest(PATCH, path, this, okClient, request, responseClass);
	}
}
