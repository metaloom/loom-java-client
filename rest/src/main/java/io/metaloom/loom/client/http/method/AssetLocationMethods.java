package io.metaloom.loom.client.http.method;

import java.util.UUID;

import io.metaloom.loom.client.http.LoomClientRequest;
import io.metaloom.loom.rest.model.NoResponse;
import io.metaloom.loom.rest.model.asset.location.LocationCreateRequest;
import io.metaloom.loom.rest.model.asset.location.LocationListResponse;
import io.metaloom.loom.rest.model.asset.location.LocationResponse;
import io.metaloom.loom.rest.model.asset.location.LocationUpdateRequest;

public interface AssetLocationMethods {

	LoomClientRequest<LocationResponse> loadLocation(UUID uuid);

	LoomClientRequest<LocationResponse> createLocation(LocationCreateRequest request);

	LoomClientRequest<LocationResponse> updateLocation(LocationUpdateRequest request);

	LoomClientRequest<LocationListResponse> listLocations(UUID startUuid, int perPage);

	LoomClientRequest<NoResponse> deleteBinary(UUID uuid);
}
