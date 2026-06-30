import { useEffect, useState } from "react";
import axios from "axios";
import "./App.css";

const CLIENT_API = "http://localhost:8080/api/clients";
const PROJECT_API = "http://localhost:8080/api/projects";

function App() {
  const [activeTab, setActiveTab] = useState("clients");

  const [clients, setClients] = useState([]);
  const [projects, setProjects] = useState([]);

  const [clientForm, setClientForm] = useState({
    name: "",
    email: "",
    phone: "",
    companyName: ""
  });

  const [projectForm, setProjectForm] = useState({
    clientId: "",
    title: "",
    description: "",
    status: "",
    deadline: ""
  });

  const [editingClientId, setEditingClientId] = useState(null);
  const [editingProjectId, setEditingProjectId] = useState(null);

  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const fetchClients = async () => {
    try {
      const response = await axios.get(CLIENT_API);
      setClients(response.data);
    } catch (err) {
      showError("Failed to fetch clients");
    }
  };

  const fetchProjects = async () => {
    try {
      const response = await axios.get(PROJECT_API);
      setProjects(response.data);
    } catch (err) {
      showError("Failed to fetch projects");
    }
  };

  useEffect(() => {
    fetchClients();
    fetchProjects();
  }, []);

  const showError = (message) => {
    setSuccess("");
    setError(message);
  };

  const showSuccess = (message) => {
    setError("");
    setSuccess(message);
  };

  const getErrorMessage = (err, fallbackMessage) => {
    if (err.response && err.response.data && err.response.data.message) {
      return err.response.data.message;
    }

    return fallbackMessage;
  };

  const handleClientChange = (e) => {
    setClientForm({
      ...clientForm,
      [e.target.name]: e.target.value
    });
  };

  const handleProjectChange = (e) => {
    setProjectForm({
      ...projectForm,
      [e.target.name]: e.target.value
    });
  };

  const resetClientForm = () => {
    setClientForm({
      name: "",
      email: "",
      phone: "",
      companyName: ""
    });

    setEditingClientId(null);
  };

  const resetProjectForm = () => {
    setProjectForm({
      clientId: "",
      title: "",
      description: "",
      status: "",
      deadline: ""
    });

    setEditingProjectId(null);
  };

  const handleClientSubmit = async (e) => {
    e.preventDefault();

    try {
      if (editingClientId === null) {
        await axios.post(CLIENT_API, clientForm);
        showSuccess("Client created successfully");
      } else {
        await axios.put(`${CLIENT_API}/${editingClientId}`, clientForm);
        showSuccess("Client updated successfully");
      }

      resetClientForm();
      fetchClients();
      fetchProjects();
    } catch (err) {
      showError(getErrorMessage(err, "Failed to save client"));
    }
  };

  const handleEditClient = (client) => {
    setActiveTab("clients");
    setEditingClientId(client.id);

    setClientForm({
      name: client.name || "",
      email: client.email || "",
      phone: client.phone || "",
      companyName: client.companyName || ""
    });

    setError("");
    setSuccess("");
  };

  const deleteClient = async (id) => {
    try {
      await axios.delete(`${CLIENT_API}/${id}`);
      showSuccess("Client deleted successfully");
      fetchClients();
      fetchProjects();
    } catch (err) {
      showError(
        getErrorMessage(
          err,
          "Failed to delete client. Delete related projects first."
        )
      );
    }
  };

  const handleProjectSubmit = async (e) => {
    e.preventDefault();

    const payload = {
      clientId: projectForm.clientId ? Number(projectForm.clientId) : null,
      title: projectForm.title,
      description: projectForm.description || null,
      status: projectForm.status || null,
      deadline: projectForm.deadline || null
    };

    try {
      if (editingProjectId === null) {
        await axios.post(PROJECT_API, payload);
        showSuccess("Project created successfully");
      } else {
        await axios.put(`${PROJECT_API}/${editingProjectId}`, payload);
        showSuccess("Project updated successfully");
      }

      resetProjectForm();
      fetchProjects();
    } catch (err) {
      showError(getErrorMessage(err, "Failed to save project"));
    }
  };

  const handleEditProject = (project) => {
    setActiveTab("projects");
    setEditingProjectId(project.id);

    setProjectForm({
      clientId: project.clientId ? String(project.clientId) : "",
      title: project.title || "",
      description: project.description || "",
      status: project.status || "",
      deadline: project.deadline || ""
    });

    setError("");
    setSuccess("");
  };

  const deleteProject = async (id) => {
    try {
      await axios.delete(`${PROJECT_API}/${id}`);
      showSuccess("Project deleted successfully");
      fetchProjects();
    } catch (err) {
      showError(getErrorMessage(err, "Failed to delete project"));
    }
  };

  const formatDateTime = (value) => {
    if (!value) return "-";
    return new Date(value).toLocaleString();
  };

  return (
    <div className="app">
      <header className="header">
        <div>
          <h1>DevCRM</h1>
          <p>Full Stack Client & Project Management System</p>
        </div>
      </header>

      <div className="tabs">
        <button
          className={activeTab === "clients" ? "active" : ""}
          onClick={() => setActiveTab("clients")}
        >
          Clients
        </button>

        <button
          className={activeTab === "projects" ? "active" : ""}
          onClick={() => setActiveTab("projects")}
        >
          Projects
        </button>
      </div>

      {error && <div className="alert error">{error}</div>}
      {success && <div className="alert success">{success}</div>}

      {activeTab === "clients" && (
        <section>
          <div className="section-header">
            <h2>{editingClientId === null ? "Add Client" : "Update Client"}</h2>
          </div>

          <form className="form" onSubmit={handleClientSubmit}>
            <input
              type="text"
              name="name"
              placeholder="Client Name"
              value={clientForm.name}
              onChange={handleClientChange}
            />

            <input
              type="email"
              name="email"
              placeholder="Client Email"
              value={clientForm.email}
              onChange={handleClientChange}
            />

            <input
              type="text"
              name="phone"
              placeholder="Phone Number"
              value={clientForm.phone}
              onChange={handleClientChange}
            />

            <input
              type="text"
              name="companyName"
              placeholder="Company Name"
              value={clientForm.companyName}
              onChange={handleClientChange}
            />

            <button type="submit">
              {editingClientId === null ? "Add Client" : "Update Client"}
            </button>

            {editingClientId !== null && (
              <button type="button" className="secondary-btn" onClick={resetClientForm}>
                Cancel Edit
              </button>
            )}
          </form>

          <div className="section-header">
            <h2>All Clients</h2>
          </div>

          <div className="grid">
            {clients.length === 0 ? (
              <p className="empty-text">No clients found</p>
            ) : (
              clients.map((client) => (
                <div className="card" key={client.id}>
                  <h3>{client.name}</h3>
                  <p><strong>Email:</strong> {client.email}</p>
                  <p><strong>Phone:</strong> {client.phone}</p>
                  <p><strong>Company:</strong> {client.companyName}</p>
                  <p><strong>Created:</strong> {formatDateTime(client.createdAt)}</p>

                  <div className="card-actions">
                    <button onClick={() => handleEditClient(client)}>Edit</button>
                    <button className="danger-btn" onClick={() => deleteClient(client.id)}>
                      Delete
                    </button>
                  </div>
                </div>
              ))
            )}
          </div>
        </section>
      )}

      {activeTab === "projects" && (
        <section>
          <div className="section-header">
            <h2>{editingProjectId === null ? "Add Project" : "Update Project"}</h2>
          </div>

          <form className="form" onSubmit={handleProjectSubmit}>
            <select
              name="clientId"
              value={projectForm.clientId}
              onChange={handleProjectChange}
            >
              <option value="">Select Client</option>
              {clients.map((client) => (
                <option key={client.id} value={client.id}>
                  {client.name} - {client.companyName}
                </option>
              ))}
            </select>

            <input
              type="text"
              name="title"
              placeholder="Project Title"
              value={projectForm.title}
              onChange={handleProjectChange}
            />

            <input
              type="text"
              name="description"
              placeholder="Project Description"
              value={projectForm.description}
              onChange={handleProjectChange}
            />

            <select
              name="status"
              value={projectForm.status}
              onChange={handleProjectChange}
            >
              <option value="">Default: PLANNING</option>
              <option value="PLANNING">PLANNING</option>
              <option value="ACTIVE">ACTIVE</option>
              <option value="ON_HOLD">ON_HOLD</option>
              <option value="COMPLETED">COMPLETED</option>
            </select>

            <input
              type="date"
              name="deadline"
              value={projectForm.deadline}
              onChange={handleProjectChange}
            />

            <button type="submit">
              {editingProjectId === null ? "Add Project" : "Update Project"}
            </button>

            {editingProjectId !== null && (
              <button type="button" className="secondary-btn" onClick={resetProjectForm}>
                Cancel Edit
              </button>
            )}
          </form>

          <div className="section-header">
            <h2>All Projects</h2>
          </div>

          <div className="grid">
            {projects.length === 0 ? (
              <p className="empty-text">No projects found</p>
            ) : (
              projects.map((project) => (
                <div className="card" key={project.id}>
                  <div className="card-title-row">
                    <h3>{project.title}</h3>
                    <span className={`badge ${project.status?.toLowerCase()}`}>
                      {project.status}
                    </span>
                  </div>

                  <p><strong>Description:</strong> {project.description || "-"}</p>
                  <p><strong>Client:</strong> {project.clientName}</p>
                  <p><strong>Deadline:</strong> {project.deadline || "-"}</p>
                  <p><strong>Created:</strong> {formatDateTime(project.createdAt)}</p>

                  <div className="card-actions">
                    <button onClick={() => handleEditProject(project)}>Edit</button>
                    <button className="danger-btn" onClick={() => deleteProject(project.id)}>
                      Delete
                    </button>
                  </div>
                </div>
              ))
            )}
          </div>
        </section>
      )}
    </div>
  );
}

export default App;