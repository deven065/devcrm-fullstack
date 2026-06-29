package com.deven.devcrm.service;

import com.deven.devcrm.dto.ProjectRequest;
import com.deven.devcrm.dto.ProjectResponse;
import com.deven.devcrm.entity.Client;
import com.deven.devcrm.entity.Project;
import com.deven.devcrm.entity.ProjectStatus;
import com.deven.devcrm.exception.ClientNotFoundException;
import com.deven.devcrm.exception.ProjectNotFoundException;
import com.deven.devcrm.repository.ClientRepository;
import com.deven.devcrm.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ClientRepository clientRepository;

    public ProjectService(ProjectRepository projectRepository, ClientRepository clientRepository) {
        this.projectRepository = projectRepository;
        this.clientRepository = clientRepository;
    }

    public ProjectResponse createProject(ProjectRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + request.getClientId()));

        ProjectStatus status = request.getStatus();

        if (status == null) {
            status = ProjectStatus.PLANNING;
        }

        Project project = new Project(
                request.getTitle(),
                request.getDescription(),
                status,
                request.getDeadline(),
                client
        );

        Project savedProject = projectRepository.save(project);

        return mapToResponse(savedProject);
    }

    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + id));

        return mapToResponse(project);
    }

    public List<ProjectResponse> getProjectsByClientId(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new ClientNotFoundException("Client not found with id: " + clientId);
        }

        return projectRepository.findByClientId(clientId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + id));

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + request.getClientId()));

        ProjectStatus status = request.getStatus();

        if (status == null) {
            status = ProjectStatus.PLANNING;
        }

        existingProject.setTitle(request.getTitle());
        existingProject.setDescription(request.getDescription());
        existingProject.setStatus(status);
        existingProject.setDeadline(request.getDeadline());
        existingProject.setClient(client);

        Project updatedProject = projectRepository.save(existingProject);

        return mapToResponse(updatedProject);
    }

    public String deleteProject(Long id) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + id));

        projectRepository.delete(existingProject);

        return "Project deleted successfully with id: " + id;
    }

    private ProjectResponse mapToResponse(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getStatus(),
                project.getDeadline(),
                project.getClient().getId(),
                project.getClient().getName(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}