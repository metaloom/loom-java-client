package io.metaloom.loom.client.http;


import org.junit.jupiter.api.extension.RegisterExtension;

import io.metaloom.loom.test.container.LoomContainer;

public abstract class AbstractContainerTest {

	@RegisterExtension
	public LoomContainer loom = new LoomContainer();

	protected void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
