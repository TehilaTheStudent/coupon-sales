package com.example.demo.Service;

import com.example.demo.Company;
import com.example.demo.Coupon;
import com.example.demo.Credentials;
import com.example.demo.Customer;
import com.example.demo.DAO_IRepository.ICompanyRepository;
import com.example.demo.DTOs.CompanyDto;
import com.example.demo.DTOs.CouponDto;
import com.example.demo.DTOs.CustomerDto;
import com.example.demo.Exception.CustomException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService extends ClientService {


    public CustomerDto createCustomer(CustomerDto customerDto) throws CustomException {
        if (customerDto == null) {
            throw new CustomException("in createCustomer: customer is null");
        }
        //TODO null checking
        Customer customer = DtoToCustomer(customerDto);
        existsByEmailOrPasswordInClients(null,null,customerDto.getEmail(),customerDto.getPassword());
        return customerToDto(iCustomerRepository.save(customer));
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customerList = iCustomerRepository.findAll();
        List<CustomerDto> customerDtos = customerList.stream().map(this::customerToDto).toList();
        return customerDtos;
    }

    public Customer getCustomerById(Long id) throws CustomException {
        Customer customer = iCustomerRepository.findById(id).orElse(null);
        if (customer == null) {
            throw new CustomException("in getCustomerById: customer not found");
        }
        return customer;
    }

    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) throws CustomException {
        if (customerDto == null) {
            throw new CustomException("in updateCustomer: customer is null");
        }
        Customer existingCustomer = iCustomerRepository.findById(id).orElse(null);
        if (existingCustomer == null) {
            throw new CustomException("in updateCustomer: customer not found");
        }
        existsByEmailOrPasswordInClients(id,null,customerDto.getEmail(),customerDto.getPassword());
        existingCustomer.setCredentials(new Credentials(customerDto.getEmail(),customerDto.getPassword()));
        existingCustomer.setName(customerDto.getName());
        return customerToDto( iCustomerRepository.save(existingCustomer));
    }

    public CustomerDto deleteCustomer(Long id) throws CustomException {
        Customer customer = iCustomerRepository.findById(id).orElse(null);
        if (customer == null) {
            throw new CustomException("in deleteCustomer: customer not found");
        }
        //NOTE this deletes from the customer_coupon also
        iCustomerRepository.deleteById(id);
        return customerToDto(customer);
    }

    public CompanyDto createCompany(CompanyDto companyDto) throws CustomException {
        if (companyDto == null) {
            throw new CustomException("in createCompany: company is null");
        }
        //TODO check for null values
        Company company = DtoToCompany(companyDto);
       existsByEmailOrPasswordInClients(null,null,companyDto.getEmail(),companyDto.getPassword());
        if (iCompanyRepository.existsByName(company.getName())) {
            throw new CustomException("in createCompany: name exists");

        }
        return companyToDto(iCompanyRepository.save(company));
    }

    public List<CompanyDto> getAllCompanies() {
        List<Company> companyList = iCompanyRepository.findAll();
        List<CompanyDto> companyDtos = companyList.stream().map(this::companyToDto).toList();
        return companyDtos;
    }

    public Company getCompanyById(Long id) throws CustomException {
        Company company = iCompanyRepository.findById(id).orElse(null);
        if (company == null) {
            throw new CustomException("in getCompanyById: company not found");
        }
        return iCompanyRepository.findById(id).orElse(null);
    }

    public CompanyDto updateCompany(Long id, CompanyDto companyDto) throws CustomException {
        if (companyDto == null) {
            throw new CustomException("in updateCompany: company is null");
        }
        Company existingCompany = iCompanyRepository.findById(id).orElse(null);
        if (existingCompany == null) {
            throw new CustomException("in updateCompany: company not found");
        }
        existsByEmailOrPasswordInClients(null,id,companyDto.getEmail(),companyDto.getPassword());
        existingCompany.setCredentials(new Credentials(companyDto.getEmail(),companyDto.getPassword()));
        existingCompany.setName(companyDto.getName());
        return companyToDto( iCompanyRepository.save(existingCompany));
    }

    public CompanyDto deleteCompany(Long id) throws CustomException {
        Company company = iCompanyRepository.findById(id).orElse(null);
        if (company == null) {
            throw new CustomException("in deleteCompany: company not found");
        }
        if (!company.getCoupons().isEmpty()) {
            for (Coupon coupon : company.getCoupons()
            ) {
                if (iCouponRepository.existsByCustomersNotEmptyAndId(coupon.getId())) {
                    throw new CustomException("in deleteCompany: company has coupons which customers had bought already, cannot delete");
                }
            }
        }
        //NOTE this deletes from the coupons also
        iCompanyRepository.deleteById(id);
        return companyToDto(company);
    }



    public List<CustomerDto> getCustomersByCouponId(Long couponId) throws CustomException {
        Coupon coupon = iCouponRepository.findById(couponId).orElse(null);
        if (coupon == null) {
            throw new CustomException("in getCustomersByCouponId: coupon not found");
        }
        return iCouponRepository.findCustomersByCouponId(couponId).stream().map(this::customerToDto).collect(Collectors.toList());
    }
    private void existsByEmailOrPasswordInClients(Long customerId,Long companyId,String email,String password) throws CustomException{
        if(customerId==null&&companyId==null){
           customerWithEmailOrPassword(email,password);
           companyWithEmailOrPassword(email,password);
        }
        else{//updating existing client
            if(customerId==null){//its company
                customerWithEmailOrPassword(email,password);
                Company existingCompanyEmail= iCompanyRepository.findByCredentialsEmail(email);
                if(existingCompanyEmail!=null&&existingCompanyEmail.getId()!=companyId){
                    throw new CustomException("email exists for another client-company");
                }
                Company existingCompanyPassword=iCompanyRepository.findByCredentialsPassword(password);
                if(existingCompanyPassword!=null&&  existingCompanyPassword.getId()!=companyId){
                    throw new CustomException("password exists for another client-company");
                }
            }
            else{//its customer
                Customer existingCustomerEmail=iCustomerRepository.findByCredentialsEmail(email);
                if(existingCustomerEmail!=null&&existingCustomerEmail.getId()!=customerId){
                    throw new CustomException("email exists for another client-customer");
                }
                Customer existingCustomerPassword=iCustomerRepository.findByCredentialsPassword(password);
                if(existingCustomerPassword!=null&& existingCustomerPassword.getId()!=customerId){
                    throw new CustomException("password exists for another client-customer");
                }
               companyWithEmailOrPassword(email,password);
            }

        }
    }


    private void customerWithEmailOrPassword(String email,String password) throws CustomException{
        Customer existingCustomerEmail=iCustomerRepository.findByCredentialsEmail(email);
        if(existingCustomerEmail!=null){
            throw new CustomException("email exists for another client-customer");
        }
        Customer existingCustomerPassword=iCustomerRepository.findByCredentialsPassword(password);
        if(existingCustomerPassword!=null){
            throw new CustomException("password exists for another client-customer");
        }
    }
    private void companyWithEmailOrPassword(String email,String password) throws CustomException{
        Company existingCompanyEmail= iCompanyRepository.findByCredentialsEmail(email);
        if(existingCompanyEmail!=null){
            throw new CustomException("email exists for another client-company");
        }
        Company existingCompanyPassword=iCompanyRepository.findByCredentialsPassword(password);
        if(existingCompanyPassword!=null){
            throw new CustomException("password exists for another client-company");
        }
    }

    @Override
    public boolean login(Credentials credentials) {
        return credentials.getEmail().equals("admin@gmail.com")&&credentials.getPassword().equals("admin");
    }
}
