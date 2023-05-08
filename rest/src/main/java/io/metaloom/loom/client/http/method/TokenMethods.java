package io.metaloom.loom.client.http.method;

import java.util.UUID;

import io.metaloom.loom.client.http.LoomClientRequest;
import io.metaloom.loom.rest.model.NoResponse;
import io.metaloom.loom.rest.model.token.TokenCreateRequest;
import io.metaloom.loom.rest.model.token.TokenListResponse;
import io.metaloom.loom.rest.model.token.TokenResponse;
import io.metaloom.loom.rest.model.token.TokenUpdateRequest;

public interface TokenMethods {

	LoomClientRequest<TokenResponse> loadToken(UUID uuid);

	LoomClientRequest<TokenResponse> createToken(TokenCreateRequest request);

	LoomClientRequest<TokenResponse> updateToken(TokenUpdateRequest request);

	LoomClientRequest<TokenListResponse> listTokens(UUID startUuid, int perPage);

	LoomClientRequest<NoResponse> deleteToken(UUID uuid);

}
