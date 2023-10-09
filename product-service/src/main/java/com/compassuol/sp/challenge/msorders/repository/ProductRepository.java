package com.compassuol.sp.challenge.msorders.repository;

import com.compassuol.sp.challenge.msorders.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
