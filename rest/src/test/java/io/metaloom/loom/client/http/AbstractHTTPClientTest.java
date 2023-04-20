package io.metaloom.loom.client.http;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import io.metaloom.loom.client.http.impl.HttpErrorException;
import io.metaloom.loom.client.http.model.ErrorResponse;
import io.metaloom.loom.rest.model.RestResponseModel;
import io.metaloom.loom.rest.model.common.AbstractResponse;

public abstract class AbstractHTTPClientTest extends AbstractContainerTest {

	protected LoomHttpClient client;

	@BeforeEach
	public void prepareClient() {
		client = LoomHttpClient.builder()
			.setScheme("http")
			.setHostname(loom.getHost())
			.setPort(loom.httpPort())
			.build();
	}

	@AfterEach
	public void closeClient() {
		if (client != null) {
			client.close();
		}
	}

	/**
	 * Return JSON with name property and specified value.
	 * 
	 * @param name
	 * @return
	 */
	protected String json(String key, String name) {
		return "{ \"" + key + "\": \"" + name + "\"}";
	}

	protected void assertSuccess(RestResponseModel response) {
		//assertTrue("The response should be successful.", response.getResult());
		fail("Not impl");
	}

	protected void assertSuccess(AbstractResponse response) {
		//assertEquals("ok", response.getStatus(), "The response should be successful.");
		fail("Not impl");
	}

	protected <T extends RestResponseModel> T invoke(LoomClientRequest<T> request) throws HttpErrorException {
		try {
			T response = request.sync();
			assertSuccess((AbstractResponse) response);
			return response;
		} catch (HttpErrorException e) {
			ErrorResponse error = e.getError();
			fail("Request failed with error " + error.getStatus());
			return null;
		}
	}

}
