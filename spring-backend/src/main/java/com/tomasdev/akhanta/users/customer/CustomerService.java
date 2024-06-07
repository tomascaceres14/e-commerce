package com.tomasdev.akhanta.users.customer;

import com.tomasdev.akhanta.auth.PasswordChangeDTO;

public interface CustomerService {

    Customer registerCustomer(CustomerDTO req);
    Customer findByEmail(String email);
    void changePassword(PasswordChangeDTO passwordDTO, String jwt);
}
