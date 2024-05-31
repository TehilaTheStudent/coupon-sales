package com.example.demo.DAO_IRepository;

import com.example.demo.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ICouponRepository extends JpaRepository<Coupon, Long> {
    boolean existsByName(String name);

    Set<Coupon> findCouponsByCompanyId(Long companyId);
    @Query("select coupon from Coupon coupon join coupon.customers customer where customer.id=?1")
    Set<Coupon> findCouponsByCustomerId(Long customerId);

    boolean existsByNameAndCompanyId(String name, Long companyId);
    Coupon findByNameAndCompanyId(String name,Long companyId);
    Coupon findByIdAndCompanyId(Long couponId,Long companyId);
    boolean existsByCustomersNotEmptyAndId(Long couponId);

    @Query("select coupon from Coupon coupon join coupon.customers customer where  coupon.id=?1 and customer.id=?2 ")
    Coupon findByIdAndCustomersContainingCustomerId(Long couponId, Long customerId);

    @Query("select coupon.customers from Coupon coupon where coupon.id =?1")
    Set<Customer> findCustomersByCouponId(Long couponId);


   Set<Coupon> findByCategory(Category category);

   Set<Coupon> findByPriceLessThanEqual(double maxPrice);

    Set<Coupon> findByPriceLessThanEqualAndCategory(double maxPrice,Category category);
}
