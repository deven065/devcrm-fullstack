package com.deven.devcrm.dto;

import com.deven.devcrm.entity.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ProjectRequest {

    @NotNull(message = "Client id is requireid")
    private Long clientId;

    @NotBlank(message = "Project title is required")
    private String title;

    private String description;

    private ProjectStatus status;

    private LocalDate deadline;

    public Long getClientId() {
        return clientId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}
