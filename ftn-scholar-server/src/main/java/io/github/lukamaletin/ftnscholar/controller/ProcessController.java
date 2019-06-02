package io.github.lukamaletin.ftnscholar.controller;

import io.github.lukamaletin.ftnscholar.dto.FormResponse;
import io.github.lukamaletin.ftnscholar.dto.TaskResponse;
import io.github.lukamaletin.ftnscholar.service.ProcessService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class ProcessController {

    private final ProcessService processService;

    private final FormService formService;

    public ProcessController(ProcessService processService, FormService formService) {
        this.processService = processService;
        this.formService = formService;
    }

    @PostMapping("process/start/{processKey}")
    public ResponseEntity startProcess(@PathVariable String processKey, @RequestBody Map<String, Object> variables) {
        final String processId = processService.startProcessInstanceByKey(processKey, variables);

        return new ResponseEntity<>(processId, HttpStatus.CREATED);
    }

    @GetMapping("form/{processId}/{taskKey}")
    public ResponseEntity getForm(@PathVariable String processId, @PathVariable String taskKey) {
        final TaskDto task = processService.getTaskByProcessIdAndTaskKey(processId, taskKey);
        final TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        final FormResponse formResponse = new FormResponse(task.getId(), taskFormData.getFormFields());

        return new ResponseEntity<>(formResponse, HttpStatus.OK);
    }

    @PostMapping("form/{taskId}")
    public ResponseEntity submitForm(@PathVariable String taskId, @RequestBody Map<String, Object> variables) {
        formService.submitTaskForm(taskId, variables);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("process/{processId}/variables")
    public ResponseEntity setProcessVariables(@PathVariable String processId, @RequestBody Map<String, Object> variables) {
        processService.setProcessVariables(processId, variables);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("task/{processId}/{taskKey}/complete")
    public ResponseEntity completeTask(@PathVariable String processId, @PathVariable String taskKey) {
        final TaskDto task = processService.getTaskByProcessIdAndTaskKey(processId, taskKey);
        processService.completeTask(task.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("task/{processId}/{taskKey}/complete-variables")
    public ResponseEntity completeTaskWithVariables(@PathVariable String processId, @PathVariable String taskKey, @RequestBody Map<String, Object> variables) {
        processService.setProcessVariables(processId, variables);
        final TaskDto task = processService.getTaskByProcessIdAndTaskKey(processId, taskKey);
        processService.completeTask(task.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("task")
    public ResponseEntity getTasks() {
        final List<TaskResponse> tasks = processService.getTasks();

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
}
