package io.metaloom.loom.client.http.method;

import io.metaloom.loom.client.http.LoomClientRequest;
import io.metaloom.loom.rest.model.user.UserResponse;

public interface UserMethods {

	LoomClientRequest<UserResponse> getUserResponse();

}
