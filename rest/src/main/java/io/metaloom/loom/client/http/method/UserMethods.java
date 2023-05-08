package io.metaloom.loom.client.http.method;

import java.util.UUID;

import io.metaloom.loom.client.http.LoomClientRequest;
import io.metaloom.loom.rest.model.NoResponse;
import io.metaloom.loom.rest.model.user.UserCreateRequest;
import io.metaloom.loom.rest.model.user.UserListResponse;
import io.metaloom.loom.rest.model.user.UserResponse;
import io.metaloom.loom.rest.model.user.UserUpdateRequest;

public interface UserMethods {

	LoomClientRequest<UserResponse> loadUser(String username);

	LoomClientRequest<UserResponse> createUser(UserCreateRequest request);

	LoomClientRequest<UserResponse> updateUser(UUID userUuid, UserUpdateRequest request);

	LoomClientRequest<UserListResponse> listUsers(UUID startUuid, int perPage);

	LoomClientRequest<NoResponse> deleteUser(UUID uuid);
}
