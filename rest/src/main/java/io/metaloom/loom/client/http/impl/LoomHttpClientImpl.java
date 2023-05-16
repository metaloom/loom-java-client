package io.metaloom.loom.client.http.impl;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.metaloom.loom.client.http.AbstractLoomOkHttpClient;
import io.metaloom.loom.client.http.LoomClientRequest;
import io.metaloom.loom.client.http.LoomHttpClient;
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
import io.metaloom.loom.rest.model.asset.location.LocationListResponse;
import io.metaloom.loom.rest.model.asset.location.AssetLocationResponse;
import io.metaloom.loom.rest.model.asset.location.AssetLocationUpdateRequest;
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
	 * @param connectTimeout
	 * @param readTimeout
	 * @param writeTimeout
	 */
	public LoomHttpClientImpl(OkHttpClient okClient, String scheme, String hostname, int port, Duration connectTimeout, Duration readTimeout,
		Duration writeTimeout) {
		super(okClient, scheme, hostname, port, connectTimeout, readTimeout, writeTimeout);
	}

	public static class Builder {

		private String scheme = "http";
		private String hostname = "localhost";
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

			return new LoomHttpClientImpl(okClient, scheme, hostname, port, connectTimeout, readTimeout, writeTimeout);
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
	public LoomClientRequest<AuthLoginResponse> login(String username, String password) {
		AuthLoginRequest request = new AuthLoginRequest();
		request.setUsername(username);
		request.setPassword(password);
		return postRequest("/login", request, AuthLoginResponse.class);
	}

	@Override
	public LoomClientRequest<UserResponse> loadUser(UUID uuid) {
		return getRequest("users/" + uuid, UserResponse.class);
	}

	@Override
	public LoomClientRequest<UserResponse> createUser(UserCreateRequest request) {
		return postRequest("users", request, UserResponse.class);
	}

	@Override
	public LoomClientRequest<UserResponse> updateUser(UUID userUuid, UserUpdateRequest request) {
		return postRequest("users/" + userUuid, request, UserResponse.class);
	}

	@Override
	public LoomClientRequest<UserListResponse> listUsers() {
		return getRequest("users", UserListResponse.class);
	}

	@Override
	public LoomClientRequest<NoResponse> deleteUser(UUID uuid) {
		return deleteRequest("/users/" + uuid);
	}

	// ASSET

	@Override
	public LoomClientRequest<AssetResponse> loadAsset(UUID uuid) {
		return getRequest("assets/" + uuid, AssetResponse.class);
	}

	@Override
	public LoomClientRequest<AssetResponse> storeAsset(AssetCreateRequest request) {
		return postRequest("assets", request, AssetResponse.class);
	}

	@Override
	public LoomClientRequest<AssetResponse> updateAsset(UUID uuid, AssetUpdateRequest request) {
		return postRequest("assets/" + uuid.toString(), request, AssetResponse.class);
	}

	@Override
	public LoomClientRequest<NoResponse> deleteAsset(UUID uuid) {
		return deleteRequest("assets/" + uuid.toString());
	}

	@Override
	public LoomClientRequest<AssetListResponse> listAssets() {
		return getRequest("assets", AssetListResponse.class);
	}

	// LOCATION

	@Override
	public LoomClientRequest<NoResponse> deleteLocation(UUID uuid) {
		return null;
	}

	@Override
	public LoomClientRequest<AssetLocationResponse> storeLocation(AssetLocationCreateRequest request) {
		return postRequest("locations", request, AssetLocationResponse.class);
	}

	@Override
	public LoomClientRequest<AssetLocationResponse> updateLocation(UUID uuid, AssetLocationUpdateRequest request) {
		return postRequest("locations/" + uuid.toString(), request, AssetLocationResponse.class);
	}

	@Override
	public LoomClientRequest<AssetLocationResponse> loadLocation(UUID uuid) {
		return getRequest("locations/" + uuid.toString(), AssetLocationResponse.class);
	}

	@Override
	public LoomClientRequest<LocationListResponse> listLocations() {
		return getRequest("locations", LocationListResponse.class);
	}

	// CLUSTER

	@Override
	public LoomClientRequest<ClusterResponse> loadCluster(UUID uuid) {
		return getRequest("/clusters/" + uuid, ClusterResponse.class);
	}

	@Override
	public LoomClientRequest<NoResponse> deleteCluster(UUID uuid) {
		return deleteRequest("/clusters/" + uuid);
	}

	@Override
	public LoomClientRequest<ClusterResponse> updateCluster(UUID uuid, ClusterUpdateRequest request) {
		return postRequest("/clusters/" + uuid, request, ClusterResponse.class);
	}

	@Override
	public LoomClientRequest<ClusterResponse> createCluster(ClusterCreateRequest request) {
		return postRequest("/clusters", request, ClusterResponse.class);
	}

	@Override
	public LoomClientRequest<ClusterListResponse> listClusters() {
		return getRequest("/clusters", ClusterListResponse.class);
	}

	// PROJECT

	@Override
	public LoomClientRequest<ProjectResponse> loadProject(UUID uuid) {
		return getRequest("/projects/" + uuid, ProjectResponse.class);
	}

	@Override
	public LoomClientRequest<NoResponse> deleteProject(UUID uuid) {
		return deleteRequest("/projects/" + uuid);
	}

	@Override
	public LoomClientRequest<ProjectResponse> createProject(ProjectCreateRequest request) {
		return postRequest("/projects", request, ProjectResponse.class);
	}

	@Override
	public LoomClientRequest<ProjectResponse> updateProject(UUID uuid, ProjectUpdateRequest request) {
		return postRequest("/projects/" + uuid, request, ProjectResponse.class);
	}

	@Override
	public LoomClientRequest<ProjectListResponse> listProject() {
		return getRequest("/projects", ProjectListResponse.class);
	}

	// LIBRARY

	@Override
	public LoomClientRequest<LibraryResponse> loadLibrary(UUID uuid) {
		return getRequest("/libraries/" + uuid, LibraryResponse.class);
	}

	@Override
	public LoomClientRequest<NoResponse> deleteLibrary(UUID uuid) {
		return deleteRequest("/libraries/" + uuid);
	}

	@Override
	public LoomClientRequest<LibraryListResponse> listLibrary() {
		return getRequest("/libraries", LibraryListResponse.class);
	}

	@Override
	public LoomClientRequest<LibraryResponse> updateLibrary(UUID uuid, LibraryUpdateRequest request) {
		return postRequest("/libraries/" + uuid, request, LibraryResponse.class);
	}

	@Override
	public LoomClientRequest<LibraryResponse> createLibrary(LibraryCreateRequest request) {
		return postRequest("/libraries", request, LibraryResponse.class);
	}

	// ANNOTATION

	@Override
	public LoomClientRequest<AnnotationResponse> loadAnnotation(UUID uuid) {
		return getRequest("/annotations/" + uuid, AnnotationResponse.class);
	}

	@Override
	public LoomClientRequest<NoResponse> deleteAnnotation(UUID uuid) {
		return deleteRequest("/annotations/" + uuid);
	}

	@Override
	public LoomClientRequest<AnnotationResponse> updateAnnotation(UUID uuid, AnnotationUpdateRequest request) {
		return postRequest("/annotations/" + uuid, request, AnnotationResponse.class);
	}

	@Override
	public LoomClientRequest<AnnotationResponse> createAnnotation(AnnotationCreateRequest request) {
		return postRequest("/annotations", request, AnnotationResponse.class);
	}

	@Override
	public LoomClientRequest<AnnotationListResponse> listAnnotations() {
		return getRequest("/annotations", AnnotationListResponse.class);
	}

	// COLLECTION

	@Override
	public LoomClientRequest<CollectionResponse> loadCollection(UUID uuid) {
		return getRequest("/collections/" + uuid, CollectionResponse.class);
	}

	@Override
	public LoomClientRequest<CollectionResponse> createCollection(CollectionCreateRequest request) {
		return postRequest("/collections", request, CollectionResponse.class);
	}

	@Override
	public LoomClientRequest<CollectionResponse> updateCollection(UUID uuid, CollectionUpdateRequest request) {
		return postRequest("/collections/" + uuid, request, CollectionResponse.class);
	}

	@Override
	public LoomClientRequest<CollectionListResponse> listCollections() {
		return getRequest("/collections", CollectionListResponse.class);
	}

	@Override
	public LoomClientRequest<NoResponse> deleteCollection(UUID uuid) {
		return deleteRequest("/collections/" + uuid);
	}

	// REACTION

	@Override
	public LoomClientRequest<ReactionResponse> loadReaction(UUID uuid) {
		return getRequest("/reactions/" + uuid, ReactionResponse.class);
	}

	@Override
	public LoomClientRequest<NoResponse> deleteReaction(UUID uuid) {
		return deleteRequest("/reactions/" + uuid);
	}

	@Override
	public LoomClientRequest<ReactionResponse> updateReaction(UUID uuid, ReactionUpdateRequest request) {
		return postRequest("/reactions/" + uuid, request, ReactionResponse.class);
	}

	@Override
	public LoomClientRequest<ReactionResponse> createReaction(ReactionCreateRequest request) {
		return postRequest("/reactions", request, ReactionResponse.class);
	}

	@Override
	public LoomClientRequest<ReactionListResponse> listReaction() {
		return getRequest("/reactions", ReactionListResponse.class);
	}

	// EMBEDDING

	@Override
	public LoomClientRequest<EmbeddingResponse> loadEmbedding(UUID uuid) {
		return getRequest("/embeddings/" + uuid, EmbeddingResponse.class);
	}

	@Override
	public LoomClientRequest<NoResponse> deleteEmbedding(UUID uuid) {
		return deleteRequest("/embeddings/" + uuid);
	}

	@Override
	public LoomClientRequest<EmbeddingListResponse> listEmbeddings() {
		return getRequest("/embeddings", EmbeddingListResponse.class);
	}

	@Override
	public LoomClientRequest<EmbeddingResponse> updateEmbedding(UUID uuid, EmbeddingUpdateRequest request) {
		return postRequest("/embeddings/" + uuid, request, EmbeddingResponse.class);
	}

	@Override
	public LoomClientRequest<EmbeddingResponse> createEmbedding(EmbeddingCreateRequest request) {
		return postRequest("/embeddings", request, EmbeddingResponse.class);
	}

	// GROUP

	@Override
	public LoomClientRequest<GroupResponse> loadGroup(UUID uuid) {
		return getRequest("/groups/" + uuid, GroupResponse.class);
	}

	@Override
	public LoomClientRequest<GroupListResponse> listGroups() {
		return getRequest("/groups", GroupListResponse.class);
	}

	@Override
	public LoomClientRequest<NoResponse> deleteGroup(UUID uuid) {
		return deleteRequest("/groups/" + uuid);
	}

	@Override
	public LoomClientRequest<GroupResponse> updateGroup(UUID uuid, GroupUpdateRequest request) {
		return postRequest("/groups/" + uuid, request, GroupResponse.class);
	}

	@Override
	public LoomClientRequest<GroupResponse> createGroup(GroupCreateRequest request) {
		return postRequest("/groups", request, GroupResponse.class);
	}

	// COMMENT

	@Override
	public LoomClientRequest<CommentListResponse> listCommentsForAnnotation(UUID annotationUuid) {
		return getRequest("/annotation/" + annotationUuid + "/comments", CommentListResponse.class);
	}

	@Override
	public LoomClientRequest<CommentResponse> loadComment(UUID uuid) {
		return getRequest("/comments/" + uuid, CommentResponse.class);
	}

	@Override
	public LoomClientRequest<NoResponse> deleteComment(UUID uuid) {
		return deleteRequest("/comments/" + uuid);
	}

	@Override
	public LoomClientRequest<CommentResponse> updateComment(UUID uuid, CommentUpdateRequest request) {
		return postRequest("/comments/" + uuid, request, CommentResponse.class);
	}

	@Override
	public LoomClientRequest<CommentResponse> createComment(CommentCreateRequest request) {
		return postRequest("/comments", request, CommentResponse.class);
	}

	// ROLE

	@Override
	public LoomClientRequest<RoleResponse> loadRole(UUID uuid) {
		return getRequest("/roles/" + uuid, RoleResponse.class);
	}

	@Override
	public LoomClientRequest<NoResponse> deleteRole(UUID uuid) {
		return deleteRequest("/roles/" + uuid);
	}

	@Override
	public LoomClientRequest<RoleResponse> updateRole(UUID uuid, RoleUpdateRequest request) {
		return postRequest("/roles/" + uuid, request, RoleResponse.class);
	}

	@Override
	public LoomClientRequest<RoleResponse> createRole(RoleCreateRequest request) {
		return postRequest("/roles", request, RoleResponse.class);
	}

	@Override
	public LoomClientRequest<RoleListResponse> listRoles() {
		return getRequest("/roles", RoleListResponse.class);
	}

	// TASK

	@Override
	public LoomClientRequest<TaskResponse> loadTask(UUID uuid) {
		return getRequest("/tasks/" + uuid, TaskResponse.class);
	}

	@Override
	public LoomClientRequest<NoResponse> deleteTask(UUID uuid) {
		return deleteRequest("/tasks/" + uuid);
	}

	@Override
	public LoomClientRequest<TaskListResponse> listTasks() {
		return getRequest("/tasks", TaskListResponse.class);
	}

	@Override
	public LoomClientRequest<TaskResponse> updateTask(UUID uuid, TaskUpdateRequest request) {
		return postRequest("/tasks/" + uuid, request, TaskResponse.class);
	}

	@Override
	public LoomClientRequest<TaskResponse> createTask(TaskCreateRequest request) {
		return postRequest("/tasks", request, TaskResponse.class);
	}

	// TOKEN

	@Override
	public LoomClientRequest<TokenResponse> loadToken(UUID uuid) {
		return getRequest("/tokens/" + uuid, TokenResponse.class);
	}

	@Override
	public LoomClientRequest<TokenListResponse> listTokens() {
		return getRequest("/tokens", TokenListResponse.class);
	}

	@Override
	public LoomClientRequest<TokenResponse> updateToken(UUID uuid, TokenUpdateRequest request) {
		return postRequest("/tokens/" + uuid, request, TokenResponse.class);
	}

	@Override
	public LoomClientRequest<TokenResponse> createToken(TokenCreateRequest request) {
		return postRequest("/tokens", request, TokenResponse.class);
	}

	@Override
	public LoomClientRequest<NoResponse> deleteToken(UUID uuid) {
		return deleteRequest("/tokens/" + uuid);
	}

}
