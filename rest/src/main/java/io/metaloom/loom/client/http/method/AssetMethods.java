package io.metaloom.loom.client.http.method;

import java.util.UUID;

import static io.metaloom.loom.api.asset.AssetId.assetId;
import io.metaloom.loom.api.asset.AssetId;
import io.metaloom.loom.client.http.LoomClientRequest;
import io.metaloom.loom.rest.model.NoResponse;
import io.metaloom.loom.rest.model.asset.AssetCreateRequest;
import io.metaloom.loom.rest.model.asset.AssetListResponse;
import io.metaloom.loom.rest.model.asset.AssetResponse;
import io.metaloom.loom.rest.model.asset.AssetUpdateRequest;
import io.metaloom.utils.hash.SHA512Sum;

public interface AssetMethods {

	LoomClientRequest<AssetResponse> loadAsset(AssetId id);

	default LoomClientRequest<AssetResponse> loadAsset(UUID uuid) {
		return loadAsset(assetId(uuid));
	}

	default LoomClientRequest<AssetResponse> loadAsset(SHA512Sum hash) {
		return loadAsset(assetId(hash));
	}

	default LoomClientRequest<NoResponse> deleteAsset(UUID uuid) {
		return deleteAsset(assetId(uuid));
	}

	default LoomClientRequest<NoResponse> deleteAsset(SHA512Sum hash) {
		return deleteAsset(assetId(hash));
	}

	LoomClientRequest<NoResponse> deleteAsset(AssetId id);

	LoomClientRequest<AssetResponse> createAsset(AssetCreateRequest request);

	default LoomClientRequest<AssetResponse> updateAsset(UUID uuid, AssetUpdateRequest request) {
		return updateAsset(assetId(uuid), request);
	}

	default LoomClientRequest<AssetResponse> updateAsset(SHA512Sum hash, AssetUpdateRequest request) {
		return updateAsset(assetId(hash), request);
	}

	LoomClientRequest<AssetResponse> updateAsset(AssetId id, AssetUpdateRequest request);

	LoomClientRequest<AssetListResponse> listAssets();

}
