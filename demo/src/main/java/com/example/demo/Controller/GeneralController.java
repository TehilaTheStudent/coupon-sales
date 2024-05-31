package com.example.demo.Controller;

import com.example.demo.Category;
import com.example.demo.Credentials;
import com.example.demo.DTOs.CompanyDto;
import com.example.demo.DTOs.CouponDto;
import com.example.demo.DTOs.CustomerDto;
import com.example.demo.Role;
import com.example.demo.Service.AdminService;
import com.example.demo.Service.CompanyService;
import com.example.demo.Service.CustomerService;
import com.example.demo.Service.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class GeneralController {
    @Autowired
    private GeneralService generalService;
    @Autowired
    private AdminService adminService;
    @PostMapping("/login")
    public ResponseEntity<?> getRoleByCredentials(@RequestBody Credentials credentials){
        if(credentials.getEmail().equals("admin@gamil.com")&&credentials.getPassword().equals("admin1"))
            return ResponseEntity.ok(Role.ADMIN_ROLE);
        List<CompanyDto> allCompanies = adminService.getAllCompanies();
        List<CustomerDto> allCustomers=adminService.getAllCustomers();

        if(allCompanies.stream().anyMatch(companyDto->companyDto.getEmail().equals(credentials.getEmail())&&companyDto.getPassword().equals(credentials.getPassword()))){
            return ResponseEntity.ok( Role.COMPANY_ROLE);
        }
        if(allCustomers.stream().anyMatch(customerDto->customerDto.getEmail().equals(credentials.getEmail())&&customerDto.getPassword().equals(credentials.getPassword()))){
            return ResponseEntity.ok( Role.CUSTOMER_ROLE);
        }
        return new ResponseEntity<>("no role found by credentials!", HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/coupon")
    public List<CouponDto> getAllCoupons(
            //NOTE: the Double here instead of double is a solution to check if its null
            //NOTE : the order of the values doesnt matter when requesting
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false)Category category
            ) {
        if(maxPrice!=null&&category!=null){
            return generalService.getCouponsByCategoryAndMaxPrice(category,maxPrice);
        }
        if(maxPrice!=null){
           return generalService.getCouponsByMaxPrice(maxPrice);
        }
        if(category!=null){
            return generalService.getCouponsByCategory(category);
        }
        List<CouponDto> couponList = generalService.getAllCoupons();
        return couponList;
    }

}
