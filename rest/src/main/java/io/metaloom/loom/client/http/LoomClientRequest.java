package io.metaloom.loom.client.http;

import com.fasterxml.jackson.databind.JsonNode;

import io.metaloom.loom.client.http.impl.HttpErrorException;
import io.metaloom.loom.client.http.impl.LoomClientRequestImpl;
import io.metaloom.loom.client.http.parameter.QueryParameters;
import io.metaloom.loom.rest.model.NoResponse;
import io.metaloom.loom.rest.model.RestRequestModel;
import io.metaloom.loom.rest.model.RestResponseModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;

public interface LoomClientRequest<T extends RestResponseModel<T>> extends QueryParameters<T> {

	public static final String PUT = "PUT";
	public static final String PATCH = "PATCH";
	public static final String GET = "GET";
	public static final String DELETE = "DELETE";
	public static final String POST = "POST";

	/**
	 * Create request without payload.
	 * 
	 * @param <T>
	 * @param method
	 * @param path
	 * @param loomClient
	 * @param okClient
	 * @return
	 */
	public static LoomClientRequest<NoResponse> create(String method, String path, LoomHttpClient loomClient,
		OkHttpClient okClient) {
		return new LoomClientRequestImpl<>(method, path, loomClient, okClient, null, NoResponse.class);
	}

	/**
	 * Create request without payload.
	 * 
	 * @param <T>
	 * @param method
	 * @param path
	 * @param loomClient
	 * @param okClient
	 * @param responseClass
	 * @return
	 */
	public static <T extends RestResponseModel<T>> LoomClientRequest<T> create(String method, String path, LoomHttpClient loomClient,
		OkHttpClient okClient, Class<T> responseClass) {
		return new LoomClientRequestImpl<>(method, path, loomClient, okClient, null, responseClass);
	}

	/**
	 * Create request with payload.
	 * 
	 * @param <T>
	 * @param method
	 * @param path
	 * @param loomClient
	 * @param okClient
	 * @param request
	 * @param responseClass
	 * @return
	 */
	public static <T extends RestResponseModel<T>> LoomClientRequest<T> create(String method, String path, LoomHttpClient loomClient,
		OkHttpClient okClient, RestRequestModel request, Class<T> responseClass) {
		return new LoomClientRequestImpl<>(method, path, loomClient, okClient, request, responseClass);
	}

	/**
	 * Returns a single which can be used to execute the request and listen to the result.
	 * 
	 * @return
	 */
	Single<T> async();

	/**
	 * Executes the request in a synchronized blocking way and returns the returned JSON data.
	 * 
	 * @return
	 * @throws HttpErrorException
	 */
	JsonNode json() throws HttpErrorException;

	/**
	 * Executes the request in a synchronized blocking way.
	 * 
	 * @return
	 * @throws HttpErrorException
	 */
	T sync() throws HttpErrorException;

}
