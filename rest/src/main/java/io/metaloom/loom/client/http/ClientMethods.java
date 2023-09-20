package io.metaloom.loom.client.http;

import io.metaloom.loom.client.http.method.AnnotationMethods;
import io.metaloom.loom.client.http.method.AssetLocationMethods;
import io.metaloom.loom.client.http.method.AssetMethods;
import io.metaloom.loom.client.http.method.AttachmentMethods;
import io.metaloom.loom.client.http.method.AuthenticationMethods;
import io.metaloom.loom.client.http.method.ClusterMethods;
import io.metaloom.loom.client.http.method.CollectionMethods;
import io.metaloom.loom.client.http.method.CommentMethods;
import io.metaloom.loom.client.http.method.EmbeddingMethods;
import io.metaloom.loom.client.http.method.GroupMethods;
import io.metaloom.loom.client.http.method.LibraryMethods;
import io.metaloom.loom.client.http.method.ProjectMethods;
import io.metaloom.loom.client.http.method.ReactionMethods;
import io.metaloom.loom.client.http.method.RoleMethods;
import io.metaloom.loom.client.http.method.TagMethods;
import io.metaloom.loom.client.http.method.TaskMethods;
import io.metaloom.loom.client.http.method.TokenMethods;
import io.metaloom.loom.client.http.method.UserMethods;

public interface ClientMethods extends
	UserMethods,
	AssetMethods,
	AssetLocationMethods,
	AttachmentMethods,
	ClusterMethods,
	GroupMethods,
	RoleMethods,
	CollectionMethods,
	AnnotationMethods,
	TaskMethods,
	TagMethods,
	AuthenticationMethods,
	ReactionMethods,
	TokenMethods,
	LibraryMethods,
	ProjectMethods,
	CommentMethods,
	EmbeddingMethods {

}
