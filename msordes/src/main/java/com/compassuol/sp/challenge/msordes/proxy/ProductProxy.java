package com.compassuol.sp.challenge.msordes.proxy;

import com.compassuol.sp.challenge.msordes.response.Products;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msproducts", url = "localhost:8080")
public interface ProductProxy {
    @GetMapping("/products/{id}")
    public Products getProductById(@PathVariable("id") Long id);
}
