package io.metaloom.loom.client.http.method;

import java.util.UUID;

import io.metaloom.loom.client.http.LoomClientRequest;
import io.metaloom.loom.rest.model.NoResponse;
import io.metaloom.loom.rest.model.group.GroupCreateRequest;
import io.metaloom.loom.rest.model.group.GroupListResponse;
import io.metaloom.loom.rest.model.group.GroupResponse;
import io.metaloom.loom.rest.model.group.GroupUpdateRequest;

public interface GroupMethods {

	LoomClientRequest<GroupResponse> loadGroup(UUID uuid);

	LoomClientRequest<GroupResponse> createGroup(GroupCreateRequest request);

	LoomClientRequest<GroupResponse> updateGroup(GroupUpdateRequest request);

	LoomClientRequest<GroupListResponse> listGroups(UUID startUuid, int perPage);

	LoomClientRequest<NoResponse> deleteGroup(UUID uuid);
}
