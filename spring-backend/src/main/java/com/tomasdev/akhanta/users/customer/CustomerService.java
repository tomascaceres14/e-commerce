package com.tomasdev.akhanta.users.customer;

import com.tomasdev.akhanta.auth.PasswordChangeDTO;
import com.tomasdev.akhanta.auth.dto.CustomerRegisterDTO;

public interface CustomerService {

    Customer registerCustomer(CustomerRegisterDTO req);
    Customer findByEmail(String email);
    void changePassword(PasswordChangeDTO passwordDTO, String jwt);
}
