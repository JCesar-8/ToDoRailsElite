package com.todo.rails.elite.starter.code.service;

import com.todo.rails.elite.starter.code.model.Task;
import com.todo.rails.elite.starter.code.repository.TaskRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// TODO 8: reformat code. Use your IDE's formatting tools to ensure consistent indentation and spacing.
// TODO 9: add method comments. Add method-level comments to explain the purpose and logic of methods.
// TODO 16: Log Exceptions. Use SLF4J to log exceptions in the service and controller layers.

@Service
public class TaskService {
    /**
     * Repository for Task entities.
     * This is an instance of TaskRepository, which is used to interact with the database for Task-related operations.
     */
    private final TaskRepository taskRepository;

    // TODO 16: Add SLF4J logger here
    // Example: private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    /**
     * Constructor for TaskService.
     * This constructor is used to inject the TaskRepository dependency.
     *
     * @param taskRepository The TaskRepository instance to be used for database operations.
     */
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Adds a new task to the database.
     * This method is used to create a new Task entity and save it to the database.
     *
     * @param task The Task object to be added.
     * @return The saved Task object.
     * @throws RuntimeException If a task with the same title already exists.
     */
    public Task addTask(@NotNull(message = "Task cannot be null") Task task) throws RuntimeException {
        if (taskRepository.findByTitle(task.getTitle()).isPresent()) {
            throw new RuntimeException("Task already exists");
        }
        return taskRepository.save(task);
    }

    /**
     * Retrieves a task by its ID.
     * This method is used to fetch a Task entity from the database by its ID.
     *
     * @param id The ID of the task to be retrieved.
     * @return The Task object with the specified ID.
     * @throws RuntimeException If the task with the specified ID is not found.
     */
    public Task getTaskById(@NotNull(message = "Id cannot be null") Long id) throws RuntimeException {
        return taskRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Task not found")
                );
    }

    /**
     * Retrieves a task by its title.
     * This method is used to fetch a Task entity from the database by its title.
     *
     * @param title The title of the task to be retrieved.
     * @return The Task object with the specified title.
     * @throws RuntimeException If the task with the specified title is not found.
     */
    public Task getTaskByTitle(
            @NotNull(message = "Title cannot be null")
            @NotBlank(message = "Title cannot be blank")
            String title
    ) throws RuntimeException {
        return taskRepository.findByTitle(title)
                .orElseThrow(
                        () -> new RuntimeException("Task not found")
                );
    }

    /**
     * Retrieves all tasks from the database.
     * This method is used to fetch all Task entities from the database.
     *
     * @return A list of all Task objects.
     */
    public List<Task> getAllTasks() {
        if (taskRepository.findAll().isEmpty()) {
            return List.of();
        }
        return taskRepository.findAll();
    }

    /**
     * Updates an existing task in the database.
     * This method is used to update an existing Task entity in the database.
     *
     * @param task The Task object with updated information.
     * @return The updated Task object.
     * @throws RuntimeException If the task to be updated is not found.
     */
    public Task updateTask(@NotNull(message = "Task cannot be null") Task task) throws RuntimeException {
        // TODO 10: use meaningful names. Rename variables and methods for clarity. Ex - taskByTitle can be refactored to existingTask.
        Optional<Task> existingTask = taskRepository.findByTitle(task.getTitle());
        if (existingTask.isEmpty()) {
            throw new RuntimeException("Task not found");
        }
        Task taskToUpdate = existingTask.get();
        taskToUpdate.setTitle(task.getTitle());
        taskToUpdate.setDescription(task.getDescription());
        taskToUpdate.setCompleted(task.isCompleted());
        taskToUpdate.setDueDate(task.getDueDate());
        return taskRepository.save(taskToUpdate);
    }

    /**
     * Deletes a task from the database.
     * This method is used to delete a Task entity from the database.
     *
     * @param task The Task object to be deleted.
     * @throws RuntimeException If the task to be deleted is not found.
     */
    public void deleteTask(@NotNull(message = "Task cannot be null") Task task) throws RuntimeException {
        Optional<Task> taskByTitle = taskRepository.findByTitle(task.getTitle());
        if (taskByTitle.isEmpty()) {
            throw new RuntimeException("Task not found");
        }
        taskRepository.delete(task);
    }

    /**
     * Retrieves all pending tasks from the database.
     * This method is used to fetch all Task entities that are not completed.
     *
     * @return A list of pending Task objects.
     */
    public List<Task> getPendingTasks() {
        List<Task> allTasks = getAllTasks();
        if (allTasks.isEmpty()) {
            return List.of();
        }
        return allTasks.stream()
                .filter(task -> !task.isCompleted())
                .toList();
    }

    /**
     * Retrieves all completed tasks from the database.
     * This method is used to fetch all Task entities that are completed.
     *
     * @return A list of completed Task objects.
     */
    public List<Task> getCompletedTasks() {
        List<Task> allTasks = getAllTasks();
        if (allTasks.isEmpty()) {
            return List.of();
        }
        return allTasks.stream()
                .filter(Task::isCompleted)
                .toList();
    }

    /**
     * Retrieves all tasks due today from the database.
     * This method is used to fetch all Task entities that are due on the current date.
     *
     * @return A list of Task objects due today.
     */
    public List<Task> getTodayTasks() {
        List<Task> allTasks = getAllTasks();
        if (allTasks.isEmpty()) {
            return List.of();
        }
        return allTasks.stream()
                .filter(
                        task -> !task.isCompleted()
                )
                .filter(
                        task -> task.getDueDate()
                                .isEqual(LocalDate.now())
                )
                .toList();
    }
}
