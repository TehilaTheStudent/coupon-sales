package com.example.demo.Service;

import com.example.demo.Company;
import com.example.demo.Coupon;
import com.example.demo.Credentials;
import com.example.demo.Customer;
import com.example.demo.DAO_IRepository.ICompanyRepository;
import com.example.demo.DAO_IRepository.ICouponRepository;
import com.example.demo.DAO_IRepository.ICustomerRepository;
import com.example.demo.DTOs.CompanyDto;
import com.example.demo.DTOs.CouponDto;
import com.example.demo.DTOs.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ClientService {
    @Autowired
    protected ICustomerRepository iCustomerRepository;

    @Autowired
    protected ICompanyRepository iCompanyRepository;
    @Autowired
    protected ICouponRepository iCouponRepository;

    protected CouponDto couponToDto(Coupon coupon) {
        CouponDto couponDto = new CouponDto(coupon.getId(), coupon.getName(), coupon.getAmount(), coupon.getPrice(), coupon.getDescription(), coupon.getStartDate(), coupon.getEndDate(), coupon.getCategory(), coupon.getCompany().getId());
        return couponDto;
    }

    protected CustomerDto customerToDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto(customer.getId(), customer.getName(), customer.getCredentials().getEmail(), customer.getCredentials().getPassword());
        return customerDto;
    }

    protected CompanyDto companyToDto(Company company) {
        CompanyDto companyDto = new CompanyDto(company.getId(), company.getName(), company.getCredentials().getEmail(), company.getCredentials().getPassword());
        return companyDto;
    }

    protected Customer DtoToCustomer(CustomerDto customerDto) {
        Customer customer = new Customer(-1l, customerDto.getName(), new HashSet<>(), new Credentials(customerDto.getEmail(), customerDto.getPassword()));
        return customer;
    }

    protected Company DtoToCompany(CompanyDto companyDto) {
        Company company = new Company(-1l, companyDto.getName(), new HashSet<>(), new Credentials(companyDto.getEmail(), companyDto.getPassword()));
        return company;
    }

    protected Coupon DtoToCoupon(CouponDto couponDto) {
        Coupon coupon = new Coupon(couponDto.getId(), couponDto.getName(), new HashSet<>(), null, couponDto.getAmount(), couponDto.getPrice(), couponDto.getDescription(), couponDto.getStartDate(), couponDto.getEndDate(), couponDto.getCategory());
        return coupon;
    }


}
