package io.metaloom.loom.client.http.method;

import java.util.UUID;

import io.metaloom.loom.client.http.LoomClientRequest;
import io.metaloom.loom.rest.model.NoResponse;
import io.metaloom.loom.rest.model.role.RoleCreateRequest;
import io.metaloom.loom.rest.model.role.RoleListResponse;
import io.metaloom.loom.rest.model.role.RoleResponse;
import io.metaloom.loom.rest.model.role.RoleUpdateRequest;

public interface RoleMethods {

	LoomClientRequest<RoleResponse> loadRole(UUID uuid);

	LoomClientRequest<RoleResponse> createRole(RoleCreateRequest request);

	LoomClientRequest<RoleResponse> updateRole(RoleUpdateRequest request);

	LoomClientRequest<RoleListResponse> listRoles(UUID startUuid, int perPage);

	LoomClientRequest<NoResponse> deleteRole(UUID uuid);
}
