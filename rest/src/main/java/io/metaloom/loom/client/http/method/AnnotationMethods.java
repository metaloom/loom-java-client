package io.metaloom.loom.client.http.method;

import java.util.UUID;

import io.metaloom.loom.client.http.LoomClientRequest;
import io.metaloom.loom.rest.model.NoResponse;
import io.metaloom.loom.rest.model.annotation.AnnotationCreateRequest;
import io.metaloom.loom.rest.model.annotation.AnnotationListResponse;
import io.metaloom.loom.rest.model.annotation.AnnotationResponse;
import io.metaloom.loom.rest.model.annotation.AnnotationUpdateRequest;

public interface AnnotationMethods {

	LoomClientRequest<AnnotationResponse> loadAnnotation(UUID uuid);

	LoomClientRequest<AnnotationResponse> createAnnotation(AnnotationCreateRequest request);

	LoomClientRequest<AnnotationResponse> updateAnnotation(UUID uuid, AnnotationUpdateRequest request);

	LoomClientRequest<AnnotationListResponse> listAnnotations(UUID startUuid, int pageSize);

	LoomClientRequest<NoResponse> deleteAnnotation(UUID uuid);
}
