package io.metaloom.loom.client.http.method;

import java.util.UUID;

import io.metaloom.loom.client.http.LoomClientRequest;
import io.metaloom.loom.rest.model.NoResponse;
import io.metaloom.loom.rest.model.binary.BinaryCreateRequest;
import io.metaloom.loom.rest.model.binary.BinaryListResponse;
import io.metaloom.loom.rest.model.binary.BinaryResponse;
import io.metaloom.loom.rest.model.binary.BinaryUpdateRequest;

public interface BinaryMethods {

	LoomClientRequest<BinaryResponse> loadBinary(UUID uuid);

	LoomClientRequest<BinaryResponse> createBinary(BinaryCreateRequest request);

	LoomClientRequest<BinaryResponse> updateBinary(BinaryUpdateRequest request);

	LoomClientRequest<BinaryListResponse> listBinary(UUID startUuid, int perPage);

	LoomClientRequest<NoResponse> deleteBinary(UUID uuid);
}
