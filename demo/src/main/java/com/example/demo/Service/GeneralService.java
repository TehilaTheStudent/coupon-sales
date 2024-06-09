package com.example.demo.Service;

import com.example.demo.Category;
import com.example.demo.Coupon;
import com.example.demo.Credentials;
import com.example.demo.DTOs.CouponDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GeneralService extends ClientService{
    public List<CouponDto> getAllCoupons() {
        List<Coupon> couponList = iCouponRepository.findAll();
        List<CouponDto> couponDtos = couponList.stream().map(this::couponToDto).toList();
        return couponDtos;
    }

    public List<CouponDto> getCouponsByCategory(Category category) {
        Set<Coupon> couponSet = iCouponRepository.findByCategory(category);
        List<CouponDto> couponDtos = couponSet.stream().map(this::couponToDto).toList();
        return couponDtos;
    }
    public List<CouponDto> getCouponsByMaxPrice(double maxPrice) {
        Set<Coupon> couponSet = iCouponRepository.findByPriceLessThanEqual(maxPrice);
        List<CouponDto> couponDtos = couponSet.stream().map(this::couponToDto).toList();
        return couponDtos;
    }
    public List<CouponDto> getCouponsByCategoryAndMaxPrice(Category category,double maxPrice) {
        Set<Coupon> couponSet = iCouponRepository.findByPriceLessThanEqualAndCategory(maxPrice,category);
        List<CouponDto> couponDtos = couponSet.stream().map(this::couponToDto).toList();
        return couponDtos;
    }

    @Override
    public boolean login(Credentials credentials) {
        return false;
    }
}
