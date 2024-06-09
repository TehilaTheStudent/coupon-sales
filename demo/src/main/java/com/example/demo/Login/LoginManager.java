package com.example.demo.Login;

import com.example.demo.Credentials;
import com.example.demo.Exception.CustomException;
import com.example.demo.Role;
import com.example.demo.Service.AdminService;
import com.example.demo.Service.ClientService;
import com.example.demo.Service.CompanyService;
import com.example.demo.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class LoginManager {

    @Autowired
    AdminService adminService;


    @Autowired
    CompanyService companyService;

    @Autowired
    CustomerService customerService;

    private LoginManager() {

    }

    public ClientService login(Credentials credential, Role clientType) throws CustomException {
        boolean boolLogin = false;
        ClientService clientService = null;
        switch (clientType) {
            case ADMIN_ROLE:
                clientService = adminService;
                boolLogin = clientService.login(credential);
                break;
            case COMPANY_ROLE:
                clientService = companyService;
                boolLogin = clientService.login(credential);
                break;
            case CUSTOMER_ROLE:
                clientService = customerService;
                boolLogin = clientService.login(credential);
                break;
        }

        if(!boolLogin)
            throw  new CustomException("login failed- no client found by credentials!");
        else
            return clientService;
    }
}
