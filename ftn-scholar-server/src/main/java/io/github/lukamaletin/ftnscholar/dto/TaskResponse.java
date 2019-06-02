package io.github.lukamaletin.ftnscholar.dto;

import io.github.lukamaletin.ftnscholar.model.Journal;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;

import java.util.Date;

public class TaskResponse {

    private TaskDto data;

    private Journal journal;

    private Date deadlineDate;

    public TaskResponse() {
    }

    public TaskDto getData() {
        return data;
    }

    public void setData(TaskDto data) {
        this.data = data;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }
}
