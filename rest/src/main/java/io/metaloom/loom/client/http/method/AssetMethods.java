package io.metaloom.loom.client.http.method;

import java.util.UUID;

import io.metaloom.loom.client.http.LoomClientRequest;
import io.metaloom.loom.rest.model.NoResponse;
import io.metaloom.loom.rest.model.asset.AssetCreateRequest;
import io.metaloom.loom.rest.model.asset.AssetListResponse;
import io.metaloom.loom.rest.model.asset.AssetResponse;

public interface AssetMethods {

	LoomClientRequest<NoResponse> deleteAsset(UUID uuid);

	LoomClientRequest<AssetResponse> storeAsset(AssetCreateRequest request);

	LoomClientRequest<AssetResponse> loadAsset(UUID uuid);

	LoomClientRequest<AssetListResponse> listAssets();
}