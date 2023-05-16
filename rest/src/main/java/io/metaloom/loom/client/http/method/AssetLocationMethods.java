package io.metaloom.loom.client.http.method;

import java.util.UUID;

import io.metaloom.loom.client.http.LoomClientRequest;
import io.metaloom.loom.rest.model.NoResponse;
import io.metaloom.loom.rest.model.asset.location.AssetLocationCreateRequest;
import io.metaloom.loom.rest.model.asset.location.LocationListResponse;
import io.metaloom.loom.rest.model.asset.location.AssetLocationResponse;
import io.metaloom.loom.rest.model.asset.location.AssetLocationUpdateRequest;

public interface AssetLocationMethods {

	LoomClientRequest<AssetLocationResponse> loadLocation(UUID uuid);

	LoomClientRequest<NoResponse> deleteLocation(UUID uuid);

	LoomClientRequest<AssetLocationResponse> storeLocation(AssetLocationCreateRequest request);

	LoomClientRequest<AssetLocationResponse> updateLocation(UUID uuid, AssetLocationUpdateRequest request);

	LoomClientRequest<LocationListResponse> listLocations();

}
