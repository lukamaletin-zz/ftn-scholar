package io.github.lukamaletin.ftnscholar.dto;

import org.camunda.bpm.engine.form.FormField;

import java.util.List;

public class FormResponse {

    private String taskId;

    private List<FormField> formFields;

    public FormResponse(String taskId, List<FormField> formFields) {
        this.taskId = taskId;
        this.formFields = formFields;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<FormField> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<FormField> formFields) {
        this.formFields = formFields;
    }
}
