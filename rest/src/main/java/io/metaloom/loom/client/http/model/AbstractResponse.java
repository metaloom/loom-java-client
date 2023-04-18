package io.metaloom.loom.client.http.model;

import io.metaloom.loom.rest.json.Json;
import io.metaloom.loom.rest.model.RestModel;
import io.metaloom.loom.rest.model.RestResponseModel;

public abstract class AbstractResponse implements RestResponseModel, RestModel {

	private float time;

	private String status;

	public float getTime() {
		return time;
	}

	public AbstractResponse setTime(float time) {
		this.time = time;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public AbstractResponse setStatus(String status) {
		this.status = status;
		return this;
	}

	@Override
	public String toString() {
		return Json.parse(this);
	}

}
