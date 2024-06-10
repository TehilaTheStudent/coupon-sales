package com.example.demo.Controller;

import com.example.demo.Company;
import com.example.demo.Coupon;
import com.example.demo.Credentials;
import com.example.demo.DTOs.CouponDto;
import com.example.demo.Exception.CustomException;
import com.example.demo.Role;
import com.example.demo.Service.CompanyService;
import com.example.demo.jwt.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
public class CompanyController extends ClientController {
   @Autowired
    private CompanyService companyService;

    @PostMapping("/{companyId}")
    public ResponseEntity<?> addNewCouponToExistingCompany(@PathVariable Long companyId, @RequestBody CouponDto newCouponDto) {
        try {
            return ResponseEntity.ok(companyService.addNewCouponToExistingCompany(companyId, newCouponDto));

        } catch (CustomException customException) {
            return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{companyId}/coupon")
    public ResponseEntity<?> getCouponsByCompanyId(@PathVariable Long companyId){
        return ResponseEntity.ok(companyService.getCouponsByCompanyId(companyId));
    }
    @PostMapping()
    public ResponseEntity<?> getCompanyByCredentials(@RequestBody Credentials credentials){
        try {
            return ResponseEntity.ok(companyService.getCompanyByCredentials(credentials));

        } catch (CustomException customException) {
            return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("{companyId}/coupon/{couponId}")
    public ResponseEntity<?> updateExistingCouponForExistingCompany(@PathVariable Long companyId,@PathVariable Long couponId,@RequestBody CouponDto couponDto){
        try {
            return ResponseEntity.ok(companyService.updateExistingCouponOfExistingCompany(companyId,couponId,couponDto));

        } catch (CustomException customException) {
            return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("{companyId}/coupon/{couponId}")
    public ResponseEntity<?> deleteExistingCouponForExistingCompany(@PathVariable Long companyId,@PathVariable Long couponId ){
        try {
            return ResponseEntity.ok(companyService.deleteCouponByCustomerIdAndCouponId(companyId,couponId));

        } catch (CustomException customException) {
            return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Override
    @PostMapping("/login")
    public ResponseEntity<?> loginByCredentials(@RequestBody Credentials credentials) {
        try {
           companyService.login(credentials);
           String token =jwtUtils.generateToken(credentials,Role.COMPANY_ROLE);
           return ResponseEntity.ok(new AuthenticationResponse(token));
        } catch (CustomException customException) {
            return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
