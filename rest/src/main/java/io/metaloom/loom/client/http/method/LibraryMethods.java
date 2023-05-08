package io.metaloom.loom.client.http.method;

import java.util.UUID;

import io.metaloom.loom.client.http.LoomClientRequest;
import io.metaloom.loom.rest.model.NoResponse;
import io.metaloom.loom.rest.model.library.LibraryCreateRequest;
import io.metaloom.loom.rest.model.library.LibraryListResponse;
import io.metaloom.loom.rest.model.library.LibraryResponse;
import io.metaloom.loom.rest.model.library.LibraryUpdateRequest;

public interface LibraryMethods {

	LoomClientRequest<LibraryResponse> loadLibrary(UUID uuid);

	LoomClientRequest<LibraryResponse> createLibrary(LibraryCreateRequest request);

	LoomClientRequest<LibraryResponse> updateLibrary(LibraryUpdateRequest request);

	LoomClientRequest<LibraryListResponse> listLibrary(UUID startUuid, int perPage);

	LoomClientRequest<NoResponse> deleteLibrary(UUID uuid);
}
