package com.tasklytic.taskservice.service;

import com.tasklytic.taskservice.dto.TaskDTO;
import com.tasklytic.taskservice.model.TaskEntity;
import com.tasklytic.taskservice.repository.TaskRepository;
import com.tasklytic.taskservice.service.impl.TaskServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.BindingResult;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private TaskServiceImpl taskService;

    private TaskDTO taskDTO;
    private TaskEntity taskEntity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        taskDTO = new TaskDTO();
        taskDTO.setTitle("Test Task");
        taskDTO.setDescription("Test Description");
        taskDTO.setStatus("To-Do");
        taskDTO.setPriority("Low");
        taskDTO.setAssigneeId(1L);
        taskDTO.setCreatedBy(2L);
        taskDTO.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        taskDTO.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        taskEntity = new TaskEntity();
        taskEntity.setId(1L);
        taskEntity.setTitle("Test Task");
        taskEntity.setDescription("Test Description");
        taskEntity.setStatus("To-Do");
        taskEntity.setPriority("Low");
        taskEntity.setAssigneeId(1L);
        taskEntity.setCreatedBy(2L);
        taskEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        taskEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
    }

    // Test createTask with validation
    @Test
    public void testCreateTaskWithValidInput() {
        when(modelMapper.map(taskDTO, TaskEntity.class)).thenReturn(taskEntity);
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);
        when(modelMapper.map(taskEntity, TaskDTO.class)).thenReturn(taskDTO);

        TaskDTO createdTask = taskService.createTask(taskDTO);

        assertNotNull(createdTask);
        assertEquals("Test Task", createdTask.getTitle());
    }

    
    // Test getTaskById
    @Test
    public void testGetTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));
        when(modelMapper.map(taskEntity, TaskDTO.class)).thenReturn(taskDTO);

        TaskDTO task = taskService.getTaskById(1L);

        assertNotNull(task);
        assertEquals("Test Task", task.getTitle());
    }

    // Test updateTask with validation
    @Test
    public void testUpdateTaskWithValidInput() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));
        when(modelMapper.map(taskDTO, TaskEntity.class)).thenReturn(taskEntity);
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);
        when(modelMapper.map(taskEntity, TaskDTO.class)).thenReturn(taskDTO);

        taskDTO.setTitle("Updated Task");
        TaskDTO updatedTask = taskService.updateTask(1L, taskDTO);

        assertNotNull(updatedTask);
        assertEquals("Updated Task", updatedTask.getTitle());
    }

    // Test deleteTask
    @Test
    public void testDeleteTask() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }
}
