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
import io.metaloom.loom.rest.model.annotation.AnnotationResponse;
import io.metaloom.loom.rest.model.annotation.AnnotationUpdateRequest;
import io.metaloom.loom.rest.model.asset.AssetCreateRequest;
import io.metaloom.loom.rest.model.asset.AssetListResponse;
import io.metaloom.loom.rest.model.asset.AssetResponse;
import io.metaloom.loom.rest.model.asset.AssetUpdateRequest;
import io.metaloom.loom.rest.model.auth.AuthLoginRequest;
import io.metaloom.loom.rest.model.auth.AuthLoginResponse;
import io.metaloom.loom.rest.model.binary.BinaryCreateRequest;
import io.metaloom.loom.rest.model.binary.BinaryListResponse;
import io.metaloom.loom.rest.model.binary.BinaryResponse;
import io.metaloom.loom.rest.model.binary.BinaryUpdateRequest;
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
	public LoomClientRequest<UserResponse> loadUser(String username) {
		return getRequest("users/" + username, UserResponse.class);
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
	public LoomClientRequest<UserListResponse> listUsers(UUID startUuid, int perPage) {
		return getRequest("users", UserListResponse.class);
	}

	@Override
	public LoomClientRequest<NoResponse> deleteUser(UUID uuid) {
		return deleteRequest("/users/" + uuid);
	}

	// BINARY

	@Override
	public LoomClientRequest<BinaryResponse> loadBinary(UUID uuid) {
		return getRequest("binary/" + uuid, BinaryResponse.class);
	}

	@Override
	public LoomClientRequest<BinaryResponse> createBinary(BinaryCreateRequest request) {
		return postRequest("binaries", request, BinaryResponse.class);
	}

	@Override
	public LoomClientRequest<BinaryResponse> updateBinary(BinaryUpdateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<NoResponse> deleteBinary(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<BinaryListResponse> listBinary(UUID startUuid, int perPage) {
		// TODO Auto-generated method stub
		return null;
	}

	// ASSET

	@Override
	public LoomClientRequest<NoResponse> deleteAsset(UUID uuid) {
		return deleteRequest("assets/" + uuid.toString());
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
	public LoomClientRequest<AssetResponse> loadAsset(UUID uuid) {
		return getRequest("assets/" + uuid.toString(), AssetResponse.class);
	}

	@Override
	public LoomClientRequest<AssetListResponse> listAssets(UUID startUuid, int pageSize) {
		LoomClientRequest<AssetListResponse> request = getRequest("assets", AssetListResponse.class);
		request.addLimit(pageSize);
		if (startUuid != null) {
			request.addFrom(startUuid);
		}
		return request;
	}

	// CLUSTER

	@Override
	public LoomClientRequest<ClusterResponse> loadCluster(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<NoResponse> deleteCluster(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<ClusterResponse> updateCluster(ClusterUpdateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<ClusterResponse> createCluster(ClusterCreateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<ClusterListResponse> listClusters(UUID startUuid, int perPage) {
		// TODO Auto-generated method stub
		return null;
	}

	// PROJECT

	@Override
	public LoomClientRequest<ProjectResponse> loadProject(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<NoResponse> deleteProject(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<ProjectResponse> createProject(ProjectCreateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<ProjectResponse> updateProject(ProjectUpdateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<ProjectListResponse> listProject(UUID startUuid, int perPage) {
		// TODO Auto-generated method stub
		return null;
	}

	// LIBRARY

	@Override
	public LoomClientRequest<LibraryResponse> loadLibrary(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<NoResponse> deleteLibrary(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<LibraryListResponse> listLibrary(UUID startUuid, int perPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<LibraryResponse> updateLibrary(LibraryUpdateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<LibraryResponse> createLibrary(LibraryCreateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	// ANNOTATION

	@Override
	public LoomClientRequest<AnnotationResponse> loadAnnotation(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<NoResponse> deleteAnnotation(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<AnnotationResponse> updateAnnotation(AnnotationUpdateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<AnnotationResponse> createAnnotation(AnnotationCreateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	// COLLECTION

	@Override
	public LoomClientRequest<CollectionResponse> loadCollection(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<CollectionResponse> createCollection(CollectionCreateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<CollectionResponse> updateCollection(CollectionUpdateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<CollectionListResponse> listCollections(UUID startUuid, int perPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<NoResponse> deleteCollection(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	// REACTION

	@Override
	public LoomClientRequest<ReactionResponse> loadReaction(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<NoResponse> deleteReaction(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<ReactionResponse> updateReaction(ReactionUpdateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<ReactionResponse> createReaction(ReactionCreateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<ReactionListResponse> listReaction(UUID startUuid, int perPage) {
		// TODO Auto-generated method stub
		return null;
	}

	// EMBEDDING

	@Override
	public LoomClientRequest<EmbeddingResponse> loadEmbedding(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<NoResponse> deleteEmbedding(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<EmbeddingListResponse> listEmbeddings(UUID startUuid, int perPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<EmbeddingResponse> updateEmbedding(EmbeddingUpdateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<EmbeddingResponse> createEmbedding(EmbeddingCreateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	// GROUP

	@Override
	public LoomClientRequest<GroupResponse> loadGroup(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<GroupListResponse> listGroups(UUID startUuid, int perPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<NoResponse> deleteGroup(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<GroupResponse> updateGroup(GroupUpdateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<GroupResponse> createGroup(GroupCreateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	// COMMENT

	@Override
	public LoomClientRequest<CommentListResponse> listCommentsForAnnotation(UUID annotationUuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<CommentResponse> loadComment(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<NoResponse> deleteComment(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<CommentResponse> updateComment(CommentUpdateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<CommentResponse> createComment(CommentCreateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	// ROLE

	@Override
	public LoomClientRequest<RoleResponse> loadRole(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<NoResponse> deleteRole(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<RoleResponse> updateRole(RoleUpdateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<RoleResponse> createRole(RoleCreateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<RoleListResponse> listRoles(UUID startUuid, int perPage) {
		// TODO Auto-generated method stub
		return null;
	}

	// TASK

	@Override
	public LoomClientRequest<TaskResponse> loadTask(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<NoResponse> deleteTask(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<TaskListResponse> listTasks(UUID startUuid, int perPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<TaskResponse> updateTask(TaskUpdateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<TaskResponse> createTask(TaskCreateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	// TOKEN

	@Override
	public LoomClientRequest<TokenResponse> loadToken(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<TokenListResponse> listTokens(UUID startUuid, int perPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<TokenResponse> updateToken(TokenUpdateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<TokenResponse> createToken(TokenCreateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoomClientRequest<NoResponse> deleteToken(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

}
