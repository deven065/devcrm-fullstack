//  This contains business logic.
//  Business Logic means the actual logic of the application

package com.deven.devcrm.service;

import com.deven.devcrm.dto.ClientRequest;
import com.deven.devcrm.dto.ClientResponse;
import com.deven.devcrm.entity.Client;
import com.deven.devcrm.exception.ClientNotFoundException;
import com.deven.devcrm.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    //  Create Client
    public ClientResponse createClient(ClientRequest request) {
        Client client = new Client(
                request.getName(),
                request.getEmail(),
                request.getPhone(),
                request.getCompanyName()
        );

        Client savedClient = clientRepository.save(client);

        return mapToResponse(savedClient);
    }

    //  Get all the clients
    public List<ClientResponse> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    //  Get client by id
    public ClientResponse getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id; " + id));

        return mapToResponse(client);
    }

    //  Update client
    public ClientResponse updateClient(Long id, ClientRequest request) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + id));

        existingClient.setName(request.getName());
        existingClient.setEmail(request.getEmail());
        existingClient.setPhone(request.getPhone());
        existingClient.setCompanyName(request.getCompanyName());

        Client updatedClient = clientRepository.save(existingClient);

        return mapToResponse(updatedClient);
    }

    //  Delete client
    public String deleteClient(Long id) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + id));

        clientRepository.delete(existingClient);

        return "Client deleted successfully with id: " + id;
    }

    private ClientResponse mapToResponse(Client client) {
        return new ClientResponse(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getPhone(),
                client.getCompanyName(),
                client.getCreatedAt(),
                client.getUpdatedAt()
        );
    }
}
