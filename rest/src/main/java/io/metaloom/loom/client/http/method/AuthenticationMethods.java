package io.metaloom.loom.client.http.method;

import io.metaloom.loom.client.http.LoomClientRequest;
import io.metaloom.loom.rest.model.auth.AuthLoginResponse;

public interface AuthenticationMethods {

	LoomClientRequest<AuthLoginResponse> login(String username, String password);
}
