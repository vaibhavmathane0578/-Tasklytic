package com.tasklytic.taskservice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasklytic.taskservice.dto.TaskDTO;
import com.tasklytic.taskservice.service.impl.TaskServiceImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


import org.springframework.boot.test.mock.mockito.MockBean;

@SuppressWarnings("removal")
@WebMvcTest(TaskController.class)
@ActiveProfiles("test")
public class TaskControllerTest {

	@MockBean
	private TaskServiceImpl taskService; // Mocking TaskServiceImpl

	@Autowired
	private MockMvc mockMvc; // MockMvc for simulating HTTP requests

	@Autowired
	private ObjectMapper objectMapper; // ObjectMapper for converting objects to JSON

	@Test
	public void testCreateTaskWithValidationErrors() throws Exception {
		// Create a TaskDTO object with invalid data
		TaskDTO invalidTaskDTO = new TaskDTO();
		invalidTaskDTO.setTitle("T"); // Invalid title (too short)
		invalidTaskDTO.setStatus("Unknown"); // Invalid status
		invalidTaskDTO.setPriority("Critical"); // Invalid priority

		// Perform the POST request to the create task endpoint and check for validation
		// errors
		ResultActions resultActions = mockMvc.perform(post("/api/tasks/new").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(invalidTaskDTO))); // Convert TaskDTO to JSON

		// Assert that the response is BadRequest (400) and contains validation errors
		// in the 'validationErrors' field
		resultActions.andExpect(status().isBadRequest()) // Expect 400 Bad Request
				.andExpect(
						jsonPath("$.validationErrors.title").value("Task title should be between 5 and 100 characters"))
				.andExpect(jsonPath("$.validationErrors.status").value("Invalid task status"))
				.andExpect(jsonPath("$.validationErrors.priority").value("Priority must be Low, Medium, or High"));
	}

	// Testing Update with wrong input
																						// request

	@Test
    public void testUpdateTaskWithValidationErrors() throws Exception {
        // Create a TaskDTO object with invalid data (same as before, but for updating)
        TaskDTO invalidTaskDTO = new TaskDTO();
        invalidTaskDTO.setTitle("T");  // Invalid title (too short)
        invalidTaskDTO.setStatus("Unknown");  // Invalid status
        invalidTaskDTO.setPriority("Critical");  // Invalid priority

        // Assume we're trying to update a task with ID 1
        Long taskId = 1L;

        // Perform the PUT request to the update task endpoint and check for validation errors
        ResultActions resultActions = mockMvc.perform(put("/api/tasks/{id}", taskId)  // Make sure you have put here
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidTaskDTO)));  // Convert TaskDTO to JSON

        // Assert that the response is BadRequest (400) and contains validation errors in the 'validationErrors' field
        resultActions.andExpect(status().isBadRequest())  // Expect 400 Bad Request
                     .andExpect(jsonPath("$.validationErrors.title").value("Task title should be between 5 and 100 characters"))
                     .andExpect(jsonPath("$.validationErrors.status").value("Invalid task status"))
                     .andExpect(jsonPath("$.validationErrors.priority").value("Priority must be Low, Medium, or High"));
    }

}
