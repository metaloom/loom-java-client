package io.metaloom.loom.client.http.model;

import io.metaloom.loom.rest.model.RestModel;

public class ErrorResponse implements RestModel {

	private ReponseStatus status;

	private float time;

	private Object result;

	public ReponseStatus getStatus() {
		return status;
	}

	public ErrorResponse setStatus(ReponseStatus status) {
		this.status = status;
		return this;
	}

	public float getTime() {
		return time;
	}

	public ErrorResponse setTime(float time) {
		this.time = time;
		return this;
	}

	public Object getResult() {
		return result;
	}

	public ErrorResponse setResult(Object result) {
		this.result = result;
		return this;
	}
}
