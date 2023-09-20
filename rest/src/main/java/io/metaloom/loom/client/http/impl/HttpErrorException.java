package io.metaloom.loom.client.http.impl;

import java.util.function.Function;

import io.metaloom.loom.rest.json.Json;
import io.metaloom.loom.rest.model.message.GenericMessageResponse;

/**
 * Exception which is also used to return non-200 error responses.
 */
public class HttpErrorException extends Exception {

	private static final long serialVersionUID = -1799524340729007029L;

	private int statusCode;

	private String body;

	private String statusMsg;

	public HttpErrorException(String message, int statusCode, String statusMsg, String body) {
		super(message);
		this.statusCode = statusCode;
		this.statusMsg = statusMsg;
		this.body = body;
	}

	public HttpErrorException(String message, Exception e) {
		super(message, e);
	}

	/**
	 * Returns the error response body.
	 * 
	 * @return
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Returns the error HTTTP status code.
	 * 
	 * @return
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * Return the error HTTP status message.
	 * 
	 * @return
	 */
	public String getStatusMsg() {
		return statusMsg;
	}

	/**
	 * Return the server response.
	 * 
	 * @return
	 */
	public GenericMessageResponse getResponse() {
		return Json.parse(body, GenericMessageResponse.class);
	}

	/**
	 * Transform the body string into the object of choice.
	 * 
	 * @param parser
	 *            Function used to transform the string.
	 * @return
	 */
	public <T> T getBodyObject(Function<String, T> parser) {
		return parser.apply(getBody());
	}

	@Override
	public String toString() {
		return getMessage() + " - status: " + statusCode + " body {" + body + "}";
	}

}
