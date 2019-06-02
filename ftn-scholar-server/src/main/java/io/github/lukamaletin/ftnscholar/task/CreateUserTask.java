package io.github.lukamaletin.ftnscholar.task;

import io.github.lukamaletin.ftnscholar.controller.exception.BadRequestException;
import io.github.lukamaletin.ftnscholar.model.constants.Constants;
import io.github.lukamaletin.ftnscholar.model.user.Member;
import io.github.lukamaletin.ftnscholar.service.ProcessService;
import io.github.lukamaletin.ftnscholar.service.UserService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.rest.dto.identity.UserCredentialsDto;
import org.camunda.bpm.engine.rest.dto.identity.UserDto;
import org.camunda.bpm.engine.rest.dto.identity.UserProfileDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.springframework.stereotype.Service;

@Service
public class CreateUserTask implements JavaDelegate {

    private final ProcessService processService;

    private final FormService formService;

    private final UserService userService;

    public CreateUserTask(ProcessService processService, FormService formService, UserService userService) {
        this.processService = processService;
        this.formService = formService;
        this.userService = userService;
    }

    @Override
    public void execute(DelegateExecution execution) {
        final String processId = execution.getProcessInstanceId();
        final TaskDto task = processService.getTaskByProcessIdAndTaskKey(processId, Constants.ENTER_DETAILS_TASK_KEY);
        final TaskFormData taskFormData = formService.getTaskFormData(task.getId());

        final UserDto user = new UserDto();
        user.setProfile(new UserProfileDto());
        user.setCredentials(new UserCredentialsDto());

        final Member member = new Member();

        for (FormField formField : taskFormData.getFormFields()) {
            String value;
            switch (formField.getId()) {
                case "username":
                    value = (String) formField.getValue().getValue();
                    user.getProfile().setId(value);
                    member.setUsername(value);
                    break;
                case "password":
                    value = (String) formField.getValue().getValue();
                    user.getCredentials().setPassword(value);
                    member.setPassword(value);
                    break;
                case "firstName":
                    value = (String) formField.getValue().getValue();
                    user.getProfile().setFirstName(value);
                    member.setFirstName(value);
                    break;
                case "lastName":
                    value = (String) formField.getValue().getValue();
                    user.getProfile().setLastName(value);
                    member.setLastName(value);
                    break;
                case "email":
                    value = (String) formField.getValue().getValue();
                    user.getProfile().setEmail(value);
                    member.setEmail(value);
                    break;
                case "city":
                    value = (String) formField.getValue().getValue();
                    member.setCity(value);
                    break;
                case "country":
                    value = (String) formField.getValue().getValue();
                    member.setCountry(value);
                    break;
                default:
                    throw new BadRequestException();
            }
        }

        userService.save(member);
        processService.saveUser(user);
    }
}
