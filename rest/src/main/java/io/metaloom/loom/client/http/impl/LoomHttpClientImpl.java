package io.metaloom.loom.client.http.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.metaloom.loom.api.asset.AssetId;
import io.metaloom.loom.client.common.LoomBinaryResponse;
import io.metaloom.loom.client.http.AbstractLoomOkHttpClient;
import io.metaloom.loom.client.http.LoomClientHttpRequest;
import io.metaloom.loom.rest.model.NoResponse;
import io.metaloom.loom.rest.model.annotation.AnnotationCreateRequest;
import io.metaloom.loom.rest.model.annotation.AnnotationListResponse;
import io.metaloom.loom.rest.model.annotation.AnnotationResponse;
import io.metaloom.loom.rest.model.annotation.AnnotationUpdateRequest;
import io.metaloom.loom.rest.model.asset.AssetCreateRequest;
import io.metaloom.loom.rest.model.asset.AssetListResponse;
import io.metaloom.loom.rest.model.asset.AssetResponse;
import io.metaloom.loom.rest.model.asset.AssetUpdateRequest;
import io.metaloom.loom.rest.model.asset.location.AssetLocationCreateRequest;
import io.metaloom.loom.rest.model.asset.location.AssetLocationListResponse;
import io.metaloom.loom.rest.model.asset.location.AssetLocationResponse;
import io.metaloom.loom.rest.model.asset.location.AssetLocationUpdateRequest;
import io.metaloom.loom.rest.model.attachment.AttachmentListResponse;
import io.metaloom.loom.rest.model.attachment.AttachmentResponse;
import io.metaloom.loom.rest.model.attachment.AttachmentUpdateRequest;
import io.metaloom.loom.rest.model.auth.AuthLoginRequest;
import io.metaloom.loom.rest.model.auth.AuthLoginResponse;
import io.metaloom.loom.rest.model.cluster.ClusterCreateRequest;
import io.metaloom.loom.rest.model.cluster.ClusterListResponse;
import io.metaloom.loom.rest.model.cluster.ClusterResponse;
import io.metaloom.loom.rest.model.cluster.ClusterUpdateRequest;
import io.metaloom.loom.rest.model.collection.CollectionCreateRequest;
import io.metaloom.loom.rest.model.collection.CollectionListResponse;
import io.metaloom.loom.rest.model.collection.CollectionResponse;
import io.metaloom.loom.rest.model.collection.CollectionUpdateRequest;
import io.metaloom.loom.rest.model.comment.CommentCreateRequest;
import io.metaloom.loom.rest.model.comment.CommentListResponse;
import io.metaloom.loom.rest.model.comment.CommentResponse;
import io.metaloom.loom.rest.model.comment.CommentUpdateRequest;
import io.metaloom.loom.rest.model.embedding.EmbeddingCreateRequest;
import io.metaloom.loom.rest.model.embedding.EmbeddingListResponse;
import io.metaloom.loom.rest.model.embedding.EmbeddingResponse;
import io.metaloom.loom.rest.model.embedding.EmbeddingUpdateRequest;
import io.metaloom.loom.rest.model.group.GroupCreateRequest;
import io.metaloom.loom.rest.model.group.GroupListResponse;
import io.metaloom.loom.rest.model.group.GroupResponse;
import io.metaloom.loom.rest.model.group.GroupUpdateRequest;
import io.metaloom.loom.rest.model.library.LibraryCreateRequest;
import io.metaloom.loom.rest.model.library.LibraryListResponse;
import io.metaloom.loom.rest.model.library.LibraryResponse;
import io.metaloom.loom.rest.model.library.LibraryUpdateRequest;
import io.metaloom.loom.rest.model.project.ProjectCreateRequest;
import io.metaloom.loom.rest.model.project.ProjectListResponse;
import io.metaloom.loom.rest.model.project.ProjectResponse;
import io.metaloom.loom.rest.model.project.ProjectUpdateRequest;
import io.metaloom.loom.rest.model.reaction.ReactionCreateRequest;
import io.metaloom.loom.rest.model.reaction.ReactionListResponse;
import io.metaloom.loom.rest.model.reaction.ReactionResponse;
import io.metaloom.loom.rest.model.reaction.ReactionUpdateRequest;
import io.metaloom.loom.rest.model.role.RoleCreateRequest;
import io.metaloom.loom.rest.model.role.RoleListResponse;
import io.metaloom.loom.rest.model.role.RoleResponse;
import io.metaloom.loom.rest.model.role.RoleUpdateRequest;
import io.metaloom.loom.rest.model.tag.TagCreateRequest;
import io.metaloom.loom.rest.model.tag.TagListResponse;
import io.metaloom.loom.rest.model.tag.TagResponse;
import io.metaloom.loom.rest.model.tag.TagUpdateRequest;
import io.metaloom.loom.rest.model.task.TaskCreateRequest;
import io.metaloom.loom.rest.model.task.TaskListResponse;
import io.metaloom.loom.rest.model.task.TaskResponse;
import io.metaloom.loom.rest.model.task.TaskUpdateRequest;
import io.metaloom.loom.rest.model.token.TokenCreateRequest;
import io.metaloom.loom.rest.model.token.TokenListResponse;
import io.metaloom.loom.rest.model.token.TokenResponse;
import io.metaloom.loom.rest.model.token.TokenUpdateRequest;
import io.metaloom.loom.rest.model.user.UserCreateRequest;
import io.metaloom.loom.rest.model.user.UserListResponse;
import io.metaloom.loom.rest.model.user.UserResponse;
import io.metaloom.loom.rest.model.user.UserUpdateRequest;
import okhttp3.OkHttpClient;

/**
 * Implementation for the {@link LoomHttpClient}
 */
public class LoomHttpClientImpl extends AbstractLoomOkHttpClient {

	public static final Logger log = LoggerFactory.getLogger(LoomHttpClientImpl.class);

	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Create a new client with a default timeout of 10s for all timeouts (connect, read and write).
	 * 
	 * @param okClient
	 * @param scheme
	 * @param hostname
	 * @param port
	 * @param pathPrefix
	 * @param connectTimeout
	 * @param readTimeout
	 * @param writeTimeout
	 */
	public LoomHttpClientImpl(OkHttpClient okClient, String scheme, String hostname, int port, String pathPrefix, Duration connectTimeout, Duration readTimeout,
		Duration writeTimeout) {
		super(okClient, scheme, hostname, port, pathPrefix, connectTimeout, readTimeout, writeTimeout);
	}

	public static class Builder {

		private String scheme = "http";
		private String hostname = "localhost";
		private String pathPrefix = "";
		private int port = 6333;

		private Duration connectTimeout = Duration.ofMillis(10_000);
		private Duration readTimeout = Duration.ofMillis(10_000);
		private Duration writeTimeout = Duration.ofMillis(10_000);
		private OkHttpClient okClient;

		/**
		 * Verify the builder and build the client.
		 * 
		 * @return
		 */
		public LoomHttpClientImpl build() {
			Objects.requireNonNull(scheme, "A protocol scheme has to be specified.");
			Objects.requireNonNull(hostname, "A hostname has to be specified.");

			if (okClient == null) {
				okClient = createDefaultOkHttpClient();
			}

			return new LoomHttpClientImpl(okClient, scheme, hostname, port, pathPrefix, connectTimeout, readTimeout, writeTimeout);
		}

		private OkHttpClient createDefaultOkHttpClient() {
			okhttp3.OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(connectTimeout);
			builder.readTimeout(readTimeout);
			builder.writeTimeout(writeTimeout);
			// // Disable gzip
			// builder.addInterceptor(chain -> {
			// Request request = chain.request();
			// Request newRequest;
			// try {
			// newRequest = request.newBuilder().addHeader("Accept-Encoding", "identity").build();
			// } catch (Exception e) {
			// log.error("Error while creating new request", e);
			// return chain.proceed(request);
			// }
			// return chain.proceed(newRequest);
			// });
			return builder.build();
		}

		/**
		 * Set the protocol scheme to be used for the client (e.g.: http, https).
		 * 
		 * @param scheme
		 * @return Fluent API
		 */
		public Builder setScheme(String scheme) {
			this.scheme = scheme;
			return this;
		}

		/**
		 * Set the hostname for the client.
		 * 
		 * @param hostname
		 * @return Fluent API
		 */
		public Builder setHostname(String hostname) {
			this.hostname = hostname;
			return this;
		}

		/**
		 * Set a custom http client to be used. A default client will be generated if non is specified.
		 * 
		 * @param okClient
		 * @return
		 */
		public Builder setOkHttpClient(OkHttpClient okClient) {
			this.okClient = okClient;
			return this;
		}

		/**
		 * Set the port to connect to. (e.g. 6333).
		 * 
		 * @param port
		 * @return Fluent API
		 */
		public Builder setPort(int port) {
			this.port = port;
			return this;
		}

		/**
		 * Set connection timeout.
		 * 
		 * @param connectTimeout
		 * @return Fluent API
		 */
		public Builder setConnectTimeout(Duration connectTimeout) {
			if (okClient != null) {
				throw new RuntimeException("Please configure the timeout on the okHttpClient you provided.");
			}
			this.connectTimeout = connectTimeout;
			return this;
		}

		/**
		 * Set read timeout for the client.
		 * 
		 * @param readTimeout
		 * @return Fluent API
		 */
		public Builder setReadTimeout(Duration readTimeout) {
			if (okClient != null) {
				throw new RuntimeException("Please configure the timeout on the okHttpClient you provided.");
			}
			this.readTimeout = readTimeout;
			return this;
		}

		/**
		 * Set write timeout for the client.
		 * 
		 * @param writeTimeout
		 * @return Fluent API
		 */
		public Builder setWriteTimeout(Duration writeTimeout) {
			if (okClient != null) {
				throw new RuntimeException("Please configure the timeout on the okHttpClient you provided.");
			}
			this.writeTimeout = writeTimeout;
			return this;
		}

	}

	// REST Methods

	@Override
	public LoomClientHttpRequest<AuthLoginResponse> login(String username, String password) {
		AuthLoginRequest request = new AuthLoginRequest();
		request.setUsername(username);
		request.setPassword(password);
		return postRequest("/login", request, AuthLoginResponse.class);
	}

	@Override
	public LoomClientHttpRequest<UserResponse> loadUser(UUID uuid) {
		return getRequest("users/" + uuid, UserResponse.class);
	}

	@Override
	public LoomClientHttpRequest<UserResponse> createUser(UserCreateRequest request) {
		return postRequest("users", request, UserResponse.class);
	}

	@Override
	public LoomClientHttpRequest<UserResponse> updateUser(UUID userUuid, UserUpdateRequest request) {
		return postRequest("users/" + userUuid, request, UserResponse.class);
	}

	@Override
	public LoomClientHttpRequest<UserListResponse> listUsers() {
		return getRequest("users", UserListResponse.class);
	}

	@Override
	public LoomClientHttpRequest<NoResponse> deleteUser(UUID userUuid) {
		return deleteRequest("users/" + userUuid);
	}

	// ASSET

	@Override
	public LoomClientHttpRequest<AssetResponse> loadAsset(AssetId assetId) {
		return getRequest("assets/" + assetId, AssetResponse.class);
	}

	@Override
	public LoomClientHttpRequest<AssetResponse> createAsset(AssetCreateRequest request) {
		return postRequest("assets", request, AssetResponse.class);
	}

	@Override
	public LoomClientHttpRequest<AssetResponse> updateAsset(AssetId assetId, AssetUpdateRequest request) {
		return postRequest("assets/" + assetId, request, AssetResponse.class);
	}

	@Override
	public LoomClientHttpRequest<NoResponse> deleteAsset(AssetId assetId) {
		return deleteRequest("assets/" + assetId);
	}

	@Override
	public LoomClientHttpRequest<AssetListResponse> listAssets() {
		return getRequest("assets", AssetListResponse.class);
	}

	// ASSET + TAG

	@Override
	public LoomClientHttpRequest<TagResponse> tagAsset(AssetId assetId, TagCreateRequest request) {
		return postRequest("assets/" + assetId + "/tags", request, TagResponse.class);
	}

	@Override
	public LoomClientHttpRequest<NoResponse> untagAsset(AssetId assetId, UUID tagUuid) {
		return deleteRequest("assets/" + assetId + "/tags/" + tagUuid);
	}

	// LOCATION

	@Override
	public LoomClientHttpRequest<NoResponse> deleteLocation(UUID locationUuid) {
		return deleteRequest("locations/" + locationUuid);
	}

	@Override
	public LoomClientHttpRequest<AssetLocationResponse> createLocation(AssetLocationCreateRequest request) {
		return postRequest("locations", request, AssetLocationResponse.class);
	}

	@Override
	public LoomClientHttpRequest<AssetLocationResponse> updateLocation(UUID locationUuid, AssetLocationUpdateRequest request) {
		return postRequest("locations/" + locationUuid, request, AssetLocationResponse.class);
	}

	@Override
	public LoomClientHttpRequest<AssetLocationResponse> loadLocation(UUID locationUuid) {
		return getRequest("locations/" + locationUuid, AssetLocationResponse.class);
	}

	@Override
	public LoomClientHttpRequest<AssetLocationListResponse> listLocations() {
		return getRequest("locations", AssetLocationListResponse.class);
	}

	// CLUSTER

	@Override
	public LoomClientHttpRequest<ClusterResponse> loadCluster(UUID uuid) {
		return getRequest("/clusters/" + uuid, ClusterResponse.class);
	}

	@Override
	public LoomClientHttpRequest<NoResponse> deleteCluster(UUID uuid) {
		return deleteRequest("/clusters/" + uuid);
	}

	@Override
	public LoomClientHttpRequest<ClusterResponse> updateCluster(UUID clusterUuid, ClusterUpdateRequest request) {
		return postRequest("clusters/" + clusterUuid, request, ClusterResponse.class);
	}

	@Override
	public LoomClientHttpRequest<ClusterResponse> createCluster(ClusterCreateRequest request) {
		return postRequest("clusters", request, ClusterResponse.class);
	}

	@Override
	public LoomClientHttpRequest<ClusterListResponse> listClusters() {
		return getRequest("clusters", ClusterListResponse.class);
	}

	// PROJECT

	@Override
	public LoomClientHttpRequest<ProjectResponse> loadProject(UUID projectUuid) {
		return getRequest("projects/" + projectUuid, ProjectResponse.class);
	}

	@Override
	public LoomClientHttpRequest<NoResponse> deleteProject(UUID projectUuid) {
		return deleteRequest("projects/" + projectUuid);
	}

	@Override
	public LoomClientHttpRequest<ProjectResponse> createProject(ProjectCreateRequest request) {
		return postRequest("projects", request, ProjectResponse.class);
	}

	@Override
	public LoomClientHttpRequest<ProjectResponse> updateProject(UUID projectUuid, ProjectUpdateRequest request) {
		return postRequest("projects/" + projectUuid, request, ProjectResponse.class);
	}

	@Override
	public LoomClientHttpRequest<ProjectListResponse> listProjects() {
		return getRequest("projects", ProjectListResponse.class);
	}

	// LIBRARY

	@Override
	public LoomClientHttpRequest<LibraryResponse> loadLibrary(UUID libraryUuid) {
		return getRequest("libraries/" + libraryUuid, LibraryResponse.class);
	}

	@Override
	public LoomClientHttpRequest<NoResponse> deleteLibrary(UUID libraryUuid) {
		return deleteRequest("libraries/" + libraryUuid);
	}

	@Override
	public LoomClientHttpRequest<LibraryListResponse> listLibraries() {
		return getRequest("libraries", LibraryListResponse.class);
	}

	@Override
	public LoomClientHttpRequest<LibraryResponse> updateLibrary(UUID libraryUuid, LibraryUpdateRequest request) {
		return postRequest("libraries/" + libraryUuid, request, LibraryResponse.class);
	}

	@Override
	public LoomClientHttpRequest<LibraryResponse> createLibrary(LibraryCreateRequest request) {
		return postRequest("libraries", request, LibraryResponse.class);
	}

	// ANNOTATION

	@Override
	public LoomClientHttpRequest<AnnotationResponse> loadAnnotation(UUID annotationUuid) {
		return getRequest("annotations/" + annotationUuid, AnnotationResponse.class);
	}

	@Override
	public LoomClientHttpRequest<NoResponse> deleteAnnotation(UUID annotationUuid) {
		return deleteRequest("annotations/" + annotationUuid);
	}

	@Override
	public LoomClientHttpRequest<AnnotationResponse> updateAnnotation(UUID annotationUuid, AnnotationUpdateRequest request) {
		return postRequest("annotations/" + annotationUuid, request, AnnotationResponse.class);
	}

	@Override
	public LoomClientHttpRequest<AnnotationResponse> createAnnotation(AnnotationCreateRequest request) {
		return postRequest("annotations", request, AnnotationResponse.class);
	}

	@Override
	public LoomClientHttpRequest<AnnotationListResponse> listAnnotations() {
		return getRequest("annotations", AnnotationListResponse.class);
	}

	// COLLECTION

	@Override
	public LoomClientHttpRequest<CollectionResponse> loadCollection(UUID collectionUuid) {
		return getRequest("collections/" + collectionUuid, CollectionResponse.class);
	}

	@Override
	public LoomClientHttpRequest<CollectionResponse> createCollection(CollectionCreateRequest request) {
		return postRequest("collections", request, CollectionResponse.class);
	}

	@Override
	public LoomClientHttpRequest<CollectionResponse> updateCollection(UUID collectionUuid, CollectionUpdateRequest request) {
		return postRequest("collections/" + collectionUuid, request, CollectionResponse.class);
	}

	@Override
	public LoomClientHttpRequest<CollectionListResponse> listCollections() {
		return getRequest("collections", CollectionListResponse.class);
	}

	@Override
	public LoomClientHttpRequest<NoResponse> deleteCollection(UUID collectionUuid) {
		return deleteRequest("collections/" + collectionUuid);
	}

	// TASK REACTION

	public LoomClientHttpRequest<ReactionResponse> loadTaskReaction(UUID taskUuid, UUID reactionUuid) {
		return getRequest("tasks/" + taskUuid + "/reactions/" + reactionUuid, ReactionResponse.class);
	}

	public LoomClientHttpRequest<ReactionResponse> createTaskReaction(UUID taskUuid, ReactionCreateRequest request) {
		return postRequest("tasks/" + taskUuid + "/reactions", request, ReactionResponse.class);
	}

	public LoomClientHttpRequest<ReactionResponse> updateTaskReaction(UUID taskUuid, UUID reactionUuid, ReactionUpdateRequest request) {
		return postRequest("tasks/" + taskUuid + "/reactions/" + reactionUuid, request, ReactionResponse.class);
	}

	public LoomClientHttpRequest<ReactionListResponse> listTaskReaction(UUID taskUuid) {
		return getRequest("tasks/" + taskUuid + "/reactions", ReactionListResponse.class);
	}

	public LoomClientHttpRequest<NoResponse> deleteTaskReaction(UUID taskUuid, UUID reactionUuid) {
		return deleteRequest("tasks/" + taskUuid + "/reactions/" + reactionUuid);
	}

	// ASSET REACTION

	public LoomClientHttpRequest<ReactionResponse> loadAssetReaction(AssetId assetId, UUID reactionUuid) {
		return getRequest("assets/" + assetId + "/reactions/" + reactionUuid, ReactionResponse.class);
	}

	public LoomClientHttpRequest<ReactionResponse> createAssetReaction(AssetId assetId, ReactionCreateRequest request) {
		return postRequest("assets/" + assetId + "/reactions", request, ReactionResponse.class);
	}

	public LoomClientHttpRequest<ReactionResponse> updateAssetReaction(AssetId assetId, UUID reactionUuid, ReactionUpdateRequest request) {
		return postRequest("assets/" + assetId + "/reactions/" + reactionUuid, request, ReactionResponse.class);
	}

	public LoomClientHttpRequest<ReactionListResponse> listAssetReaction(AssetId assetId) {
		return getRequest("assets/" + assetId + "/reactions", ReactionListResponse.class);
	}

	public LoomClientHttpRequest<NoResponse> deleteAssetReaction(AssetId assetId, UUID reactionUuid) {
		return deleteRequest("assets/" + assetId + "/reactions/" + reactionUuid);
	}

	// COMMENT REACTION

	public LoomClientHttpRequest<ReactionResponse> loadCommentReaction(UUID commentUuid, UUID reactionUuid) {
		return getRequest("comments/" + commentUuid + "/reactions/" + reactionUuid, ReactionResponse.class);
	}

	public LoomClientHttpRequest<ReactionResponse> createCommentReaction(UUID commentUuid, ReactionCreateRequest request) {
		return postRequest("comments/" + commentUuid + "/reactions", request, ReactionResponse.class);
	}

	public LoomClientHttpRequest<ReactionResponse> updateCommentReaction(UUID commentUuid, UUID reactionUuid, ReactionUpdateRequest request) {
		return postRequest("comments/" + commentUuid + "/reactions/" + reactionUuid, request, ReactionResponse.class);
	}

	public LoomClientHttpRequest<ReactionListResponse> listCommentReaction(UUID commentUuid) {
		return getRequest("comments/" + commentUuid + "/reactions", ReactionListResponse.class);
	}

	public LoomClientHttpRequest<NoResponse> deleteCommentReaction(UUID commentUuid, UUID reactionUuid) {
		return deleteRequest("comments/" + commentUuid + "/reactions/" + reactionUuid);
	}

	// EMBEDDING

	@Override
	public LoomClientHttpRequest<EmbeddingResponse> loadEmbedding(UUID embeddingUuid) {
		return getRequest("embeddings/" + embeddingUuid, EmbeddingResponse.class);
	}

	@Override
	public LoomClientHttpRequest<NoResponse> deleteEmbedding(UUID embeddingUuid) {
		return deleteRequest("embeddings/" + embeddingUuid);
	}

	@Override
	public LoomClientHttpRequest<EmbeddingListResponse> listEmbeddings() {
		return getRequest("embeddings", EmbeddingListResponse.class);
	}

	@Override
	public LoomClientHttpRequest<EmbeddingResponse> updateEmbedding(UUID embeddingUuid, EmbeddingUpdateRequest request) {
		return postRequest("embeddings/" + embeddingUuid, request, EmbeddingResponse.class);
	}

	@Override
	public LoomClientHttpRequest<EmbeddingResponse> createEmbedding(EmbeddingCreateRequest request) {
		return postRequest("embeddings", request, EmbeddingResponse.class);
	}

	// GROUP

	@Override
	public LoomClientHttpRequest<GroupResponse> loadGroup(UUID groupUuid) {
		return getRequest("groups/" + groupUuid, GroupResponse.class);
	}

	@Override
	public LoomClientHttpRequest<GroupListResponse> listGroups() {
		return getRequest("groups", GroupListResponse.class);
	}

	@Override
	public LoomClientHttpRequest<NoResponse> deleteGroup(UUID groupUuid) {
		return deleteRequest("groups/" + groupUuid);
	}

	@Override
	public LoomClientHttpRequest<GroupResponse> updateGroup(UUID uuid, GroupUpdateRequest request) {
		return postRequest("groups/" + uuid, request, GroupResponse.class);
	}

	@Override
	public LoomClientHttpRequest<GroupResponse> createGroup(GroupCreateRequest request) {
		return postRequest("groups", request, GroupResponse.class);
	}

	// COMMENT

	@Override
	public LoomClientHttpRequest<CommentListResponse> listCommentsForAnnotation(UUID annotationUuid) {
		return getRequest("annotation/" + annotationUuid + "/comments", CommentListResponse.class);
	}

	@Override
	public LoomClientHttpRequest<CommentResponse> loadComment(UUID uuid) {
		return getRequest("comments/" + uuid, CommentResponse.class);
	}

	@Override
	public LoomClientHttpRequest<NoResponse> deleteComment(UUID uuid) {
		return deleteRequest("comments/" + uuid);
	}

	@Override
	public LoomClientHttpRequest<CommentResponse> updateComment(UUID uuid, CommentUpdateRequest request) {
		return postRequest("comments/" + uuid, request, CommentResponse.class);
	}

	@Override
	public LoomClientHttpRequest<CommentResponse> createComment(CommentCreateRequest request) {
		return postRequest("comments", request, CommentResponse.class);
	}

	// ROLE

	@Override
	public LoomClientHttpRequest<RoleResponse> loadRole(UUID uuid) {
		return getRequest("roles/" + uuid, RoleResponse.class);
	}

	@Override
	public LoomClientHttpRequest<NoResponse> deleteRole(UUID uuid) {
		return deleteRequest("roles/" + uuid);
	}

	@Override
	public LoomClientHttpRequest<RoleResponse> updateRole(UUID uuid, RoleUpdateRequest request) {
		return postRequest("roles/" + uuid, request, RoleResponse.class);
	}

	@Override
	public LoomClientHttpRequest<RoleResponse> createRole(RoleCreateRequest request) {
		return postRequest("roles", request, RoleResponse.class);
	}

	@Override
	public LoomClientHttpRequest<RoleListResponse> listRoles() {
		return getRequest("roles", RoleListResponse.class);
	}

	// TASK

	@Override
	public LoomClientHttpRequest<TaskResponse> loadTask(UUID uuid) {
		return getRequest("tasks/" + uuid, TaskResponse.class);
	}

	@Override
	public LoomClientHttpRequest<NoResponse> deleteTask(UUID uuid) {
		return deleteRequest("tasks/" + uuid);
	}

	@Override
	public LoomClientHttpRequest<TaskListResponse> listTasks() {
		return getRequest("tasks", TaskListResponse.class);
	}

	@Override
	public LoomClientHttpRequest<TaskResponse> updateTask(UUID uuid, TaskUpdateRequest request) {
		return postRequest("tasks/" + uuid, request, TaskResponse.class);
	}

	@Override
	public LoomClientHttpRequest<TaskResponse> createTask(TaskCreateRequest request) {
		return postRequest("tasks", request, TaskResponse.class);
	}

	// ATTACHMENT

	@Override
	public LoomClientHttpRequest<AttachmentResponse> uploadAttachment(String filename, String mimeType, InputStream fileData) {
		Objects.requireNonNull(filename, "filename must not be null");
		Objects.requireNonNull(fileData, "fileData must not be null");
		Objects.requireNonNull(mimeType, "mimeType must not be null");

		if (mimeType.isEmpty()) {
			throw new IllegalArgumentException("The mimeType of the attachment cannot be empty.");
		}

		// TODO handle escaping of filename
		String boundary = "--------Geg2Oob";
		StringBuilder multiPartFormData = new StringBuilder();

		// multiPartFormData.append("--").append(boundary).append("\r\n");
		// multiPartFormData.append("Content-Disposition: form-data; name=\"language\"\r\n");
		// multiPartFormData.append("\r\n");
		// multiPartFormData.append(languageTag).append("\r\n");

		multiPartFormData.append("--").append(boundary).append("\r\n");
		multiPartFormData.append("Content-Disposition: form-data; name=\"" + "shohY6d" + "\"; filename=\"").append(filename).append("\"\r\n");
		multiPartFormData.append("Content-Type: ").append(mimeType).append("\r\n");
		multiPartFormData.append("Content-Transfer-Encoding: binary\r\n" + "\r\n");

		InputStream prefix;
		InputStream suffix;
		try {
			prefix = new ByteArrayInputStream(multiPartFormData.toString().getBytes("utf-8"));
			suffix = new ByteArrayInputStream(("\r\n--" + boundary + "--\r\n").getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		String bodyContentType = "multipart/form-data; boundary=" + boundary;

		Vector<InputStream> streams = new Vector<>(Arrays.asList(
			prefix,
			fileData,
			suffix));
		SequenceInputStream completeStream = new SequenceInputStream(streams.elements());

		return postUploadRequest("attachments", AttachmentResponse.class, completeStream, bodyContentType);
	}

	@Override
	public LoomClientHttpRequest<AttachmentListResponse> listAttachments() {
		return getRequest("/attachments", AttachmentListResponse.class);
	}

	@Override
	public LoomClientHttpRequest<NoResponse> deleteAttachment(UUID uuid) {
		return deleteRequest("/attachments/" + uuid);
	}

	@Override
	public LoomClientHttpRequest<AttachmentResponse> loadAttachment(UUID uuid) {
		return getRequest("/attachments/" + uuid, AttachmentResponse.class);
	}

	@Override
	public LoomClientHttpRequest<LoomBinaryResponse> downloadAttachment(UUID uuid) {
		return getDownloadRequest("/attachments/" + uuid);
	}

	@Override
	public LoomClientHttpRequest<AttachmentResponse> updateAttachment(UUID uuid, AttachmentUpdateRequest request) {
		return postRequest("/attachments/" + uuid, request, AttachmentResponse.class);
	}

	// TAG

	@Override
	public LoomClientHttpRequest<TagResponse> loadTag(UUID uuid) {
		return getRequest("/tags/" + uuid, TagResponse.class);
	}

	@Override
	public LoomClientHttpRequest<NoResponse> deleteTag(UUID uuid) {
		return deleteRequest("/tags/" + uuid);
	}

	@Override
	public LoomClientHttpRequest<TagListResponse> listTags() {
		return getRequest("/tags", TagListResponse.class);
	}

	@Override
	public LoomClientHttpRequest<TagResponse> updateTag(UUID uuid, TagUpdateRequest request) {
		return postRequest("/tags/" + uuid, request, TagResponse.class);
	}

	@Override
	public LoomClientHttpRequest<TagResponse> createTag(TagCreateRequest request) {
		return postRequest("/tags", request, TagResponse.class);
	}

	// TOKEN

	@Override
	public LoomClientHttpRequest<TokenResponse> loadToken(UUID uuid) {
		return getRequest("/tokens/" + uuid, TokenResponse.class);
	}

	@Override
	public LoomClientHttpRequest<TokenListResponse> listTokens() {
		return getRequest("/tokens", TokenListResponse.class);
	}

	@Override
	public LoomClientHttpRequest<TokenResponse> updateToken(UUID uuid, TokenUpdateRequest request) {
		return postRequest("/tokens/" + uuid, request, TokenResponse.class);
	}

	@Override
	public LoomClientHttpRequest<TokenResponse> createToken(TokenCreateRequest request) {
		return postRequest("/tokens", request, TokenResponse.class);
	}

	@Override
	public LoomClientHttpRequest<NoResponse> deleteToken(UUID uuid) {
		return deleteRequest("/tokens/" + uuid);
	}

}
