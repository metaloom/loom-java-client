package io.metaloom.loom.client.http.method;

import java.util.UUID;

import io.metaloom.loom.client.http.LoomClientRequest;
import io.metaloom.loom.rest.model.NoResponse;
import io.metaloom.loom.rest.model.reaction.ReactionCreateRequest;
import io.metaloom.loom.rest.model.reaction.ReactionListResponse;
import io.metaloom.loom.rest.model.reaction.ReactionResponse;
import io.metaloom.loom.rest.model.reaction.ReactionUpdateRequest;

public interface ReactionMethods {

	LoomClientRequest<ReactionResponse> loadReaction(UUID uuid);

	LoomClientRequest<ReactionResponse> createReaction(ReactionCreateRequest request);

	LoomClientRequest<ReactionResponse> updateReaction(UUID uuid, ReactionUpdateRequest request);

	LoomClientRequest<ReactionListResponse> listReaction();

	LoomClientRequest<NoResponse> deleteReaction(UUID uuid);
}
