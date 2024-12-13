package com.tasklytic.taskservice.constants;

public class Constants {

	// Error Messages
	public static final String TASK_NOT_FOUND = "Task with ID %d not found.";
	public static final String INVALID_TASK_DATA = "Invalid task data provided.";
	public static final String DATABASE_ERROR = "Error accessing the database.";
	public static final String UNEXPECTED_ERROR = "An unexpected error occurred. Please try again.";

	// Success Messages
	public static final String TASK_CREATED = "Task created successfully.";
	public static final String TASK_UPDATED = "Task updated successfully.";
	public static final String TASK_DELETED = "Task deleted successfully.";

	// exceptions
	@SuppressWarnings("serial")
	public static class TaskNotFoundException extends RuntimeException {
		public TaskNotFoundException(String message) {
			super(message);
		}
	}
	
	
}
