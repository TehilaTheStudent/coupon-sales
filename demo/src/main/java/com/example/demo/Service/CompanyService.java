package com.example.demo.Service;

import com.example.demo.Company;
import com.example.demo.Coupon;
import com.example.demo.Credentials;
import com.example.demo.DTOs.CompanyDto;
import com.example.demo.DTOs.CouponDto;
import com.example.demo.Exception.CustomException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class CompanyService extends ClientService {
    public CompanyDto getCompanyByCredentials(Credentials credentials) throws CustomException {
        Company company = iCompanyRepository.findCompanyByCredentials(credentials);
        if (company == null) {
            throw new CustomException("in getCompanyByCredentials: company not found " + credentials);
        }
        return companyToDto(company);
    }

    public List<CouponDto> getCouponsByCompanyId(Long companyId) {
        List<CouponDto> couponDtos = iCouponRepository.findCouponsByCompanyId(companyId).stream().map(this::couponToDto).toList();
        return couponDtos;
    }

    public CouponDto addNewCouponToExistingCompany(Long companyId, CouponDto newCouponDto) throws CustomException {
        startAfterTodayAndEndAfterStart(newCouponDto.getStartDate(), newCouponDto.getEndDate());
        Company existingCompany = iCompanyRepository.findById(companyId).orElse(null);
        if (existingCompany == null) {
            throw new CustomException("in addNewCouponToExistingCompany: existingCompany not found");
        }
        if (iCouponRepository.existsByNameAndCompanyId(newCouponDto.getName(), companyId)) {
            throw new CustomException("in addNewCouponToExistingCompany: this company has coupon with the same name");
        } else {
            Coupon newCoupon = DtoToCoupon(newCouponDto);
            newCoupon.setCompany(existingCompany);
            newCoupon = iCouponRepository.save(newCoupon);
            return couponToDto(newCoupon);
        }
    }

    public CouponDto updateExistingCouponOfExistingCompany(Long companyId, Long couponId, CouponDto coupon) throws CustomException {
        Company existingCompany = iCompanyRepository.findById(companyId).orElse(null);
        if (existingCompany == null) {
            throw new CustomException("in updateExistingCouponOfExistingCompany: company not found");
        }
        Coupon existingCoupon = iCouponRepository.findByIdAndCompanyId(couponId, companyId);
        if (existingCoupon == null) {
            throw new CustomException("in updateExistingCouponOfExistingCompany: coupon not exists for existing company");
        }
        if(!existingCoupon.getCustomers().isEmpty()){
            throw new CustomException("in updateExistingCouponOfExistingCompany: coupon was bought already by some customer");
        }
        Coupon newCoupon = DtoToCoupon(coupon);
        startAfterTodayAndEndAfterStart(newCoupon.getStartDate(), newCoupon.getEndDate());
        Coupon existingSameName=iCouponRepository.findByNameAndCompanyId(newCoupon.getName(), companyId);
        if ( existingSameName!=null&& existingSameName.getId()!= existingCoupon.getId()) {
            throw new CustomException("in updateExistingCouponOfExistingCompany: existing company has coupon with the same name");
        }

        newCoupon.setId(existingCoupon.getId());
        newCoupon.setCompany(existingCompany);
        return couponToDto(iCouponRepository.save(newCoupon));

    }
    public CouponDto deleteCouponByCustomerIdAndCouponId(Long companyId,Long couponId) throws CustomException{
        Company existingCompany = iCompanyRepository.findById(companyId).orElse(null);
        if (existingCompany == null) {
            throw new CustomException("in deleteCouponByCustomerIdAndCouponId: company not found");
        }
        Coupon existingCoupon = iCouponRepository.findByIdAndCompanyId(couponId, companyId);
        if (existingCoupon == null) {
            throw new CustomException("in deleteCouponByCustomerIdAndCouponId: coupon not exists for existing company");
        }
        if(!existingCoupon.getCustomers().isEmpty()){
            throw new CustomException("in deleteCouponByCustomerIdAndCouponId: coupon was bought already by some customer");
        }
        existingCompany.getCoupons().remove(existingCoupon);
        iCompanyRepository.save(existingCompany);
        return  couponToDto(existingCoupon);
    }
    //if starts today it's okay, and if ends dame day starts it's okay
    private void startAfterTodayAndEndAfterStart(LocalDate startDate, LocalDate endDate) throws CustomException {
        if (startDate.isBefore(LocalDate.now())) {
            throw new CustomException("start date before now");
        }
        if (endDate.isBefore(startDate)) {
            throw new CustomException("end date before start date");
        }
    }

    @Override
    public boolean login(Credentials credentials) {
        Company company=iCompanyRepository.findCompanyByCredentials(credentials);
        if(company!=null){
            return true;
        }
        return false;
    }
}
