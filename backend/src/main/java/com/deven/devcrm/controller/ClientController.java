//  In Java Spring Boot, a Controller is used to handle the request coming from the outside world.
//  Think link this:
//  Client/ Browser/ Postman
//  --> Send Request
//  --> Controller Receives it
//  --> Service does business logic
//  --> Controller sends response back

package com.deven.devcrm.controller;

import com.deven.devcrm.dto.ClientRequest;
import com.deven.devcrm.dto.ClientResponse;
import com.deven.devcrm.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //  This class will handle API requests and returns data as response
@RequestMapping("/api/clients") //  This sets the base URL for this controller
public class ClientController {

    private final ClientService clientService;  //  final means once clientService is assigned, it cannot be changed

    public ClientController(ClientService clientService) {  //  Constructor Injection --> Spring Boot automatically gives an object of ClientService
        this.clientService = clientService;
    }

    @PostMapping
    public ClientResponse createClient(@Valid @RequestBody ClientRequest request) {
        return clientService.createClient(request);
    }

    @GetMapping
    public List<ClientResponse> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ClientResponse getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @PutMapping("/{id}")
    public ClientResponse updateClient(@PathVariable Long id, @Valid @RequestBody ClientRequest request) {
        return clientService.updateClient(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteClient(@PathVariable Long id) {
        return clientService.deleteClient(id);
    }
}
