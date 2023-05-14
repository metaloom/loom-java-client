package io.metaloom.loom.client.http.method;

import java.util.UUID;

import io.metaloom.loom.client.http.LoomClientRequest;
import io.metaloom.loom.rest.model.NoResponse;
import io.metaloom.loom.rest.model.collection.CollectionCreateRequest;
import io.metaloom.loom.rest.model.collection.CollectionListResponse;
import io.metaloom.loom.rest.model.collection.CollectionResponse;
import io.metaloom.loom.rest.model.collection.CollectionUpdateRequest;

public interface CollectionMethods {

	LoomClientRequest<CollectionResponse> loadCollection(UUID uuid);

	LoomClientRequest<CollectionResponse> createCollection(CollectionCreateRequest request);

	LoomClientRequest<CollectionResponse> updateCollection(UUID uuid, CollectionUpdateRequest request);

	LoomClientRequest<CollectionListResponse> listCollections(UUID startUuid, int perPage);

	LoomClientRequest<NoResponse> deleteCollection(UUID uuid);

}
