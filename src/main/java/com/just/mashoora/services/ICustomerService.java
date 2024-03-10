package com.just.mashoora.services;

import com.just.mashoora.models.Customer;

import java.util.List;

public interface ICustomerService {

    Customer insertCustomer(Customer customer);

    List<Customer> fetchCustomerList();
}
