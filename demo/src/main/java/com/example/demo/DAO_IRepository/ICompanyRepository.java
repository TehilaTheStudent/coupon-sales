package com.example.demo.DAO_IRepository;

import com.example.demo.Company;
import com.example.demo.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICompanyRepository extends JpaRepository<Company,Long> {
    Company findByCredentialsEmail(String email);
    Company findByCredentialsPassword(String password);
    boolean existsByName(String name);
    Company findCompanyByCredentials(Credentials credentials);

    Company findByCredentials(Credentials credentials);

}
