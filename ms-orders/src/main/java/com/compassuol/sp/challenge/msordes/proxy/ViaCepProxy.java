package com.compassuol.sp.challenge.msordes.proxy;

import com.compassuol.sp.challenge.msordes.model.dto.Address;
import com.compassuol.sp.challenge.msordes.response.AddressViaCep;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Via-CEP", url = "viacep.com.br")
public interface ViaCepProxy {
    @GetMapping(value = "/ws/{cep}/json")
    public AddressViaCep getCEP(@PathVariable("cep") String cep);
}
