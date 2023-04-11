package io.metaloom.loom.client.grpc.method;

import static io.metaloom.loom.client.grpc.InternalGrpcUtil.assetsStub;
import static io.metaloom.loom.client.grpc.InternalGrpcUtil.assetsVertxStub;

import java.util.Objects;

import io.metaloom.loom.client.grpc.ClientSettings;
import io.metaloom.loom.proto.AssetRequest;
import io.metaloom.loom.proto.AssetResponse;

public interface AssetMethods extends ClientSettings {

	default LoomClientRequest<AssetResponse> loadAsset(String sha512sum) {
		Objects.requireNonNull(sha512sum, "Hashsum must be specified");

		AssetRequest.Builder request = AssetRequest.newBuilder()
			.setSha512Sum(sha512sum);

		return request(
			() -> assetsStub(this).load(request.build()),
			() -> assetsVertxStub(this).load(request.build()));
	}

	default LoomClientRequest<AssetResponse> storeAsset(String sha512sum) {
		Objects.requireNonNull(sha512sum, "Hashsum must be specified");

		AssetRequest.Builder request = AssetRequest.newBuilder()
			.setSha512Sum(sha512sum);

		return request(
			() -> assetsStub(this).store(request.build()),
			() -> assetsVertxStub(this).store(request.build()));
	}

}
