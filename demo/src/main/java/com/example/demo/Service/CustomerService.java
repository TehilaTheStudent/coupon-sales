package com.example.demo.Service;

import com.example.demo.Company;
import com.example.demo.Coupon;
import com.example.demo.Credentials;
import com.example.demo.Customer;
import com.example.demo.DAO_IRepository.ICouponRepository;
import com.example.demo.DAO_IRepository.ICustomerRepository;
import com.example.demo.DTOs.CompanyDto;
import com.example.demo.DTOs.CouponDto;
import com.example.demo.DTOs.CustomerDto;
import com.example.demo.Exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerService extends ClientService {
    public CouponDto addExistingCouponToExistingCustomer(Long customerId, Long couponId) throws CustomException {
        Customer existingCustomer = iCustomerRepository.findById(customerId).orElse(null);
        if (existingCustomer == null) {
            throw new CustomException("in addExistingCouponToExistingCustomer: existingCustomer not found");
        }
        Coupon existingCoupon = iCouponRepository.findById(couponId).orElse(null);
        if (existingCoupon == null) {
            throw new CustomException("in addExistingCouponToExistingCustomer: existingCoupon not found");
        }
        if(existingCoupon.getEndDate().isBefore(LocalDate.now())){
            throw new CustomException("in addExistingCouponToExistingCustomer: the existing coupon end date is before today");
        }
        if(iCouponRepository.findByIdAndCustomersContainingCustomerId(couponId,customerId)!=null){
            throw new CustomException("in addExistingCouponToExistingCustomer: existing customer has this coupon already");
        }
        if(existingCoupon.getAmount()<1){
            throw  new CustomException("in addExistingCouponToExistingCustomer: the existing coupon amount is 0");
        }
        existingCoupon.setAmount(existingCoupon.getAmount()-1);
        iCouponRepository.save(existingCoupon);
        existingCustomer.getCoupons().add(existingCoupon);
        existingCustomer = iCustomerRepository.save(existingCustomer);
        return couponToDto(existingCoupon);
    }

    public List<CouponDto> getCouponsByCustomerId(Long customerId) throws CustomException{
        Customer existingCustomer = iCustomerRepository.findById(customerId).orElse(null);
        if (existingCustomer == null) {
            throw new CustomException("in getCouponsByCustomerId: existingCustomer not found");
        }
        List<CouponDto> couponDtos = iCouponRepository.findCouponsByCustomerId(customerId).stream().map(this::couponToDto).toList();
        return couponDtos;
    }
    public CustomerDto getCustomerByCredentials(Credentials credentials) throws CustomException{
        Customer customer=iCustomerRepository.findCustomersByCredentials(credentials);
        if(customer==null){
            throw new CustomException("in getCustomerByCredentials: customer not found "+credentials);
        }
        return customerToDto(customer);
    }


}
