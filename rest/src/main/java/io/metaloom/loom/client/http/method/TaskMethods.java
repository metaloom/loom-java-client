package io.metaloom.loom.client.http.method;

import java.util.UUID;

import io.metaloom.loom.client.http.LoomClientRequest;
import io.metaloom.loom.rest.model.NoResponse;
import io.metaloom.loom.rest.model.task.TaskCreateRequest;
import io.metaloom.loom.rest.model.task.TaskListResponse;
import io.metaloom.loom.rest.model.task.TaskResponse;
import io.metaloom.loom.rest.model.task.TaskUpdateRequest;

public interface TaskMethods {

	LoomClientRequest<TaskResponse> loadTask(UUID uuid);

	LoomClientRequest<TaskResponse> createTask(TaskCreateRequest request);

	LoomClientRequest<TaskResponse> updateTask(TaskUpdateRequest request);

	LoomClientRequest<TaskListResponse> listTasks(UUID startUuid, int perPage);

	LoomClientRequest<NoResponse> deleteTask(UUID uuid);

}