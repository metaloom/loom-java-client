package io.metaloom.loom.client.http.model;

import io.metaloom.loom.rest.model.RestModel;

public class ReponseStatus implements RestModel {

	private String error;

	public String getError() {
		return error;
	}

	public ReponseStatus setError(String error) {
		this.error = error;
		return this;
	}

}
