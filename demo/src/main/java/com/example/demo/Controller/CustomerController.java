package com.example.demo.Controller;

import com.example.demo.Credentials;
import com.example.demo.Customer;
import com.example.demo.DAO_IRepository.ICustomerRepository;
import com.example.demo.Exception.CustomException;
import com.example.demo.Role;
import com.example.demo.Service.CompanyService;
import com.example.demo.Service.CustomerService;
import com.example.demo.jwt.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController extends ClientController {
//TODO check if email/password exists in adding company/customer,
    //TODO make error checking- the coupon exists for the customer...
    //TODO pay attention to the dates!!!
   @Autowired
    private CustomerService customerService;

    @PutMapping("/{customerId}/coupon/{couponId}")
    public ResponseEntity<?> addExistingCouponToExistingCustomer(@PathVariable Long customerId,@PathVariable Long couponId) {
        try {
            return ResponseEntity.ok(customerService.addExistingCouponToExistingCustomer(customerId, couponId));
        } catch (CustomException customException) {
            return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{customerId}/coupon")
    public ResponseEntity<?> getCouponsByCustomerId(@PathVariable Long customerId){
        try {
            return ResponseEntity.ok( customerService.getCouponsByCustomerId(customerId));

        } catch (CustomException customException) {
            return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping()
    public ResponseEntity<?> getCustomerByCredentials(@RequestBody Credentials credentials){

        try {
            return ResponseEntity.ok(customerService.getCustomerByCredentials(credentials));

        } catch (CustomException customException) {
            System.out.println(customException.getMessage());
            return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Override
    @PostMapping("/login")
    public ResponseEntity<?> loginByCredentials(  Credentials credentials) {
        try {
           customerService.login(credentials);
           String token=jwtUtils.generateToken(credentials,Role.CUSTOMER_ROLE);
           return ResponseEntity.ok(new AuthenticationResponse(token));
        } catch (CustomException customException) {
            return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
