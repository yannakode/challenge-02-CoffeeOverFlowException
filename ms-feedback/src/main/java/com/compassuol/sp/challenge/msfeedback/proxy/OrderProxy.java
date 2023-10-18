package com.compassuol.sp.challenge.msfeedback.proxy;

import com.compassuol.sp.challenge.msfeedback.response.OrderResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msorders", url = "localhost:8000")
public interface OrderProxy {
    @GetMapping("/orders/{id}")
    public OrderResponseDTO getOrderById(@PathVariable("id") Long id);
}
