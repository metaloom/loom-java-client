package io.metaloom.loom.client.http;

import java.time.Duration;

import io.metaloom.loom.client.http.impl.LoomHttpClientImpl;
import io.metaloom.loom.client.http.method.AssetMethods;
import io.metaloom.loom.client.http.method.AuthenticationMethods;
import io.metaloom.loom.client.http.method.UserMethods;

public interface LoomHttpClient extends UserMethods, AssetMethods, AuthenticationMethods, ClientSettings, AutoCloseable {

    static LoomHttpClientImpl.Builder builder() {
        return LoomHttpClientImpl.builder();
    }

    /**
     * Return the configured protocol scheme.
     *
     * @return
     */
    String getScheme();

    /**
     * Return the configured server hostname.
     *
     * @return
     */
    String getHostname();

    /**
     * Return the configured server port.
     *
     * @return
     */
    int getPort();

    /**
     * Close the client and release all resources.
     */
    void close();

    /**
     * Return the configured connect timeout.
     *
     * @return
     */
    Duration getConnectTimeout();

    /**
     * Return the configured read timeout.
     *
     * @return
     */
    Duration getReadTimeout();

    /**
     * Return the configured write timeout.
     *
     * @return
     */
    Duration getWriteTimeout();

    /**
     * Set the token to be used for authentication.
     *
     * @param token
     */
    void setToken(String token);

    /**
     * Return the used authentication token.
     *
     * @return
     */
    String getToken();
}
