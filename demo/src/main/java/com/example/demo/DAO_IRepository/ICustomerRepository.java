package com.example.demo.DAO_IRepository;

import com.example.demo.Company;
import com.example.demo.Credentials;
import com.example.demo.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository<Customer,Long> {
    Customer findByCredentialsEmail(String email);
    Customer findByCredentialsPassword(String password);
    Customer findCustomersByCredentials(Credentials credentials);


}
