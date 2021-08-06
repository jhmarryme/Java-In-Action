package com.jhmarryme.keycloak.dao;

import com.jhmarryme.keycloak.pojo.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerDAO extends CrudRepository<Customer, Long> {

}
