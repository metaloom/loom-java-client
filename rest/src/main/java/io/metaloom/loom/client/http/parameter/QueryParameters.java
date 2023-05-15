package io.metaloom.loom.client.http.parameter;

import java.util.UUID;

import io.metaloom.loom.client.http.LoomClientRequest;
import io.metaloom.loom.rest.model.RestResponseModel;
import io.metaloom.loom.rest.parameter.QueryParameterKey;

public interface QueryParameters<T extends RestResponseModel<T>> {

	/**
	 * Add an additional query parameter.
	 * 
	 * @param key
	 * @param value
	 * @return Fluent API
	 */
	LoomClientRequest<T> addQueryParameter(String key, String value);

	default LoomClientRequest<T> addLimit(Integer limit) {
		if (limit != null) {
			return addQueryParameter(QueryParameterKey.PER_PAGE.key(), String.valueOf(limit));
		} else {
			return self();
		}
	}

	default LoomClientRequest<T> addFrom(UUID startUuid) {
		if (startUuid != null) {
			return addQueryParameter("from", startUuid.toString());
		} else {
			return self();
		}
	}

	LoomClientRequest<T> self();

}