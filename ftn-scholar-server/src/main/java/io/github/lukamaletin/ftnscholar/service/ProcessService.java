package io.github.lukamaletin.ftnscholar.service;

import io.github.lukamaletin.ftnscholar.controller.exception.NotFoundException;
import io.github.lukamaletin.ftnscholar.dto.TaskResponse;
import io.github.lukamaletin.ftnscholar.dto.VariablesDto;
import io.github.lukamaletin.ftnscholar.model.constants.Constants;
import io.github.lukamaletin.ftnscholar.model.constants.PaymentOption;
import io.github.lukamaletin.ftnscholar.model.user.BaseUser;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.identity.UserDto;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.runtime.StartProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ProcessService {

    @Value("${camunda-rest-url}")
    private String camundaRestUrl;

    private final RestTemplate restTemplate;

    private final RuntimeService runtimeService;

    private final JournalService journalService;

    private final UserService userService;

    public ProcessService(RestTemplate restTemplate, RuntimeService runtimeService, JournalService journalService, UserService userService) {
        this.restTemplate = restTemplate;
        this.runtimeService = runtimeService;
        this.journalService = journalService;
        this.userService = userService;
    }

    public void saveUser(UserDto user) {
        final String url = String.format("%s/user/create", camundaRestUrl);
        restTemplate.postForObject(url, user, Void.class);
    }

    public String startProcessInstanceByKey(String processKey, Map<String, Object> variables) {
        final String url = String.format("%s/process-definition/key/%s/start", camundaRestUrl, processKey);
        final StartProcessInstanceDto request = new StartProcessInstanceDto();
        request.setVariables(new VariablesDto(variables));
        final ProcessInstanceDto response = restTemplate.postForObject(url, request, ProcessInstanceDto.class);

        return response.getId();
    }

    public TaskDto getTaskByProcessIdAndTaskKey(String processId, String taskKey) {
        final String url = String.format("%s/task", camundaRestUrl);
        final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("processInstanceId", processId)
                .queryParam("taskDefinitionKey", taskKey);

        final TaskDto[] tasks = restTemplate.getForObject(uriBuilder.toUriString(), TaskDto[].class);

        return Arrays.stream(tasks).findFirst().orElseThrow(() -> new NotFoundException("Task not found!"));
    }

    public void submitTaskForm(String taskId, Map<String, Object> variables) {
        final String url = String.format("%s/task/%s/submit-form", camundaRestUrl, taskId);
        restTemplate.postForEntity(url, variables, Void.class);
    }

    public void setProcessVariables(String processId, Map<String, Object> variables) {
        for (String key : variables.keySet()) {
            final String value = String.valueOf(variables.get(key));
            // Some variables need to be read from db:
            switch (key) {
                case "isOpenAccess":
                    variables.put(key, this.journalService.findById(new Long(value)).getPaymentOption() == PaymentOption.OpenAccess);
                    break;
            }

            runtimeService.setVariable(processId, key, variables.get(key));
        }
    }

    public Object getProcessVariables(String processId, String variable) {
        try {
            return restTemplate.getForObject(String.format("%s/process-instance/%s/variables/%s", camundaRestUrl, processId, variable), VariableValueDto.class).getValue();
        } catch (Exception e) {
            return null;
        }
    }

    public void completeTask(String taskId) {
        final String url = String.format("%s/task/%s/complete", camundaRestUrl, taskId);
        restTemplate.postForObject(url, new VariablesDto(), Void.class);
    }

    public List<TaskResponse> getTasks() {
        final BaseUser user = userService.getCurrentUser();
        final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(String.format("%s/task", camundaRestUrl))
                .queryParam("assignee", user.getUsername());

        final TaskDto[] taskDtos = restTemplate.getForObject(uriBuilder.build().toString(), TaskDto[].class);
        final List<TaskResponse> tasks = new ArrayList<>();

        for (TaskDto taskDto : taskDtos) {
            final TaskResponse taskResponse = new TaskResponse();
            taskResponse.setData(taskDto);

            final Object journalId = Constants.SELECT_JOURNAL_TASK_KEY.equals(taskDto.getTaskDefinitionKey()) ? null : getProcessVariables(taskDto.getProcessInstanceId(), "journalId");
            taskResponse.setJournal(journalId != null ? journalService.findById(new Long((Integer) journalId)) : null);

            final Object deadlineDate =
                    !Constants.RESUBMIT_PAPER_TASK_KEY.equals(taskDto.getTaskDefinitionKey()) &&
                            !Constants.REVIEW_PAPER_TASK_KEY.equals(taskDto.getTaskDefinitionKey()) &&
                            !Constants.MAKE_PAPER_CORRECTIONS_TASK_KEY.equals(taskDto.getTaskDefinitionKey()) ?
                            null : getProcessVariables(taskDto.getProcessInstanceId(), "deadlineDate");
            try {
                taskResponse.setDeadlineDate(deadlineDate != null ? Constants.DATA_FORMAT.parse((String) deadlineDate) : null);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            tasks.add(taskResponse);
        }

        return tasks;
    }
}
