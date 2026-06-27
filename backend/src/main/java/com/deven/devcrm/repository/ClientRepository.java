package com.deven.devcrm.repository;

import com.deven.devcrm.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    
}
