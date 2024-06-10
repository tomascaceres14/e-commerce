package com.tomasdev.akhanta.users.customer;

import com.tomasdev.akhanta.auth.dto.CustomerRegisterDTO;
import com.tomasdev.akhanta.users.IUser;

public interface CustomerService extends IUser<Customer, CustomerRegisterDTO> {
}
