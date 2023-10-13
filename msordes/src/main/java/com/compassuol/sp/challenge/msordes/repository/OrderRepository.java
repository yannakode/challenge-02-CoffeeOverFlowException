package com.compassuol.sp.challenge.msordes.repository;

import com.compassuol.sp.challenge.msordes.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
