package io.metaloom.loom.client.grpc;

import org.junit.Test;

public class LoomGRPCClientTest {

	@Test
	public void testClient() {
		LoomGRPCClient client = LoomGRPCClient.builder()
			.setPort(4242)
			.setHostname("localhost")
			.build();
		
		//client.loadAsset()
	}
}
