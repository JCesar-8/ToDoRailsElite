package com.todo.rails.elite.starter.code.controller;

import com.todo.rails.elite.starter.code.model.Task;
import com.todo.rails.elite.starter.code.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private Task sampleTask;

    @BeforeEach
    void setUp() {
        // TODO: Initialize mocks
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        // TODO: Initialize sampleTask object
        sampleTask = new Task("Test task","Sample task for testing",false, LocalDate.now());
    }

    @Test
    void getAllTasks_Success() throws Exception {
        // TODO: Mock taskService.getAllTasks() to return a list
        // TODO: Perform GET request to /api/tasks/all
        // TODO: Assert response status and JSON content
        mockMvc.perform(get("/api/tasks/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test task"));
    }

    // TODO: Add more test cases for createTask, updateTask, deleteTask
}
