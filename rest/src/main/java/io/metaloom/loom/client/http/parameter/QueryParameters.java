package io.metaloom.loom.client.http.parameter;

import java.util.UUID;

import io.metaloom.filter.Filter;
import io.metaloom.filter.FilterKey;
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
		return addQueryParameter(QueryParameterKey.LIMIT.key(), String.valueOf(limit));
	}

	default LoomClientRequest<T> addFrom(UUID startUuid) {
		return addQueryParameter(QueryParameterKey.FROM.key(), startUuid.toString());
	}

	default LoomClientRequest<T> addFilter(Filter<?> filter) {
		return addQueryParameter(QueryParameterKey.FILTER.key(), filter.toString());
	}

	default LoomClientRequest<T> addEqualsFilter(FilterKey key, Object value) {
		return addQueryParameter(QueryParameterKey.FILTER.key(), key.eq(value.toString()).toString());
	}

	LoomClientRequest<T> self();

}