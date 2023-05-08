package io.metaloom.loom.client.http.impl;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import io.metaloom.loom.client.http.LoomBinaryResponse;
import okhttp3.Response;

public class LoomBinaryResponseImpl implements LoomBinaryResponse {

	private final Response response;

	public static final String FILENAME_DISPOSITION_ATTR = "filename*=utf-8''";

	public LoomBinaryResponseImpl(Response response) {
		this.response = response;
	}

	@Override
	public InputStream getStream() {
		return response.body().byteStream();
	}

	@Override
	public String getFilename() {
		String disposition = response.header("content-disposition");
		if (disposition == null) {
			return null;
		}
		try {
			int start = disposition.indexOf(FILENAME_DISPOSITION_ATTR);
			String encodedName = disposition.substring(start + FILENAME_DISPOSITION_ATTR.length());
			return URLDecoder.decode(encodedName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getContentType() {
		return response.body().contentType().toString();
	}

	@Override
	public void close() {
		response.close();
	}

	@Override
	public boolean isSuccessful() {
		return response.isSuccessful();
	}

	@Override
	public int code() {
		return response.code();
	}

	@Override
	public LoomBinaryResponse self() {
		return this;
	}
}
