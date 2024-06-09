package com.example.demo.Controller;

import com.example.demo.*;
import com.example.demo.DTOs.CompanyDto;
import com.example.demo.DTOs.CouponDto;
import com.example.demo.DTOs.CustomerDto;
import com.example.demo.Exception.CustomException;
import com.example.demo.Service.AdminService;
import com.example.demo.Service.CompanyService;
import com.example.demo.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController extends ClientController {

    //    @Autowired
    private AdminService adminService;


//    @Autowired
//    private CompanyService companyService;

    @GetMapping("/demo")
    public Credentials getDemo() {
        System.out.println("demo");

        return new Credentials("fdfd", "fdfd");
    }

    @GetMapping("/customer")
    public List<CustomerDto> getAllCustomers() {
        return adminService.getAllCustomers();
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(adminService.getCustomerById(id));
        } catch (CustomException customException) {
            return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/customer")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDto customerDto) {
        try {
            return ResponseEntity.ok(adminService.createCustomer(customerDto));
        } catch (CustomException customException) {
            return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        try {
            return ResponseEntity.ok(adminService.updateCustomer(id, customerDto));
        } catch (CustomException customException) {
            return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(adminService.deleteCustomer(id));
        } catch (CustomException customException) {
            return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/company")
    public List<CompanyDto> getAllCompanies() {
        return adminService.getAllCompanies();
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<?> getCompanyById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(adminService.getCustomerById(id));
        } catch (CustomException customException) {
            return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/company")
    public ResponseEntity<?> createCompany(@RequestBody CompanyDto companyDto) {
        try {
            return ResponseEntity.ok(adminService.createCompany(companyDto));
        } catch (CustomException customException) {
            return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/company/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Long id, @RequestBody CompanyDto companyDto) {
        try {
            return ResponseEntity.ok(adminService.updateCompany(id, companyDto));
        } catch (CustomException customException) {
            return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/company/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(adminService.deleteCompany(id));
        } catch (CustomException customException) {
            return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/coupon/{id}/customer")
    public ResponseEntity<?> getCustomersByCouponId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(adminService.getCustomersByCouponId(id));
        } catch (CustomException customException) {
            return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<?> loginByCredentials(@RequestBody   Credentials credentials) {
        System.out.println(credentials);
        try {
            adminService = (AdminService) loginManager.login(credentials,Role.ADMIN_ROLE);
            return ResponseEntity.ok(true);
        } catch (CustomException customException) {
            return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


//    @PostMapping("/fillData")
//    public ResponseEntity<String> fillData() {
//        Credentials credentials=new Credentials("email","password");
//        CompanyDto company1 = new CompanyDto(-1L, "company1", credentials.getPassword(), credentials.getEmail());
//        CompanyDto company2 = new CompanyDto(-1L, "company2", new HashSet<>(),credentials);
//        CompanyDto company3 = new CompanyDto(-1L, "company3", new HashSet<>(),credentials);
//        Coupon coupon1 = new Coupon(-1L, "coupon1", new HashSet<>(), null,10,10,"desc", LocalDate.now(),LocalDate.now(), Category.Restaurants);
//        Coupon coupon2 = new Coupon(-1L, "coupon2", new HashSet<>(), null,10,10,"desc", LocalDate.now(),LocalDate.now(), Category.Restaurants);
//        Coupon coupon3 = new Coupon(-1L, "coupon3", new HashSet<>(), null,10,10,"desc", LocalDate.now(),LocalDate.now(), Category.Restaurants);
//        CustomerDto customer1 = new CustomerDto(-1L, "customer1", new HashSet<>(),credentials);
//        CustomerDto customer2 = new CustomerDto(-1L, "customer2", new HashSet<>(),credentials);
//        CustomerDto customer3 = new CustomerDto(-1L, "customer3", new HashSet<>(),credentials);
//        try {
//            customer1 = adminService.createCustomer(customer1);
//            customer2 = adminService.createCustomer(customer2);
//            customer3 = adminService.createCustomer(customer3);
//            company1  = adminService.createCompany(company1);
//            company2 = adminService.createCompany(company2);
//            company3 = adminService.createCompany(company3);
//            coupon1 = companyService.addNewCouponToExistingCompany(company1.getId(), coupon1);
//            coupon2 = companyService.addNewCouponToExistingCompany(company1.getId(), coupon2);
//            coupon3 = companyService.addNewCouponToExistingCompany(company1.getId(), coupon3);
//            customer1 = customerService.addExistingCouponToExistingCustomer(customer1.getId(), coupon1.getId());
//            customer2 = customerService.addExistingCouponToExistingCustomer(customer2.getId(), coupon2.getId());
//            customer3 = customerService.addExistingCouponToExistingCustomer(customer3.getId(), coupon3.getId());
//            return ResponseEntity.ok("3 customers, companies and coupons were created successfully 😊");
//        } catch (CustomException customException) {
//            return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//
//    }
}
