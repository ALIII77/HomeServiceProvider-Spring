package com.example.ProjectV2.dto.Customer;

import com.example.ProjectV2.entity.Admin;
import com.example.ProjectV2.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerChangePasswordDto {
    private String username;
    private String oldPassword;
    private String newPassword;

    public Customer getCustomer(){
        Customer customer = new Customer();
        customer.setPassword(oldPassword);
        customer.setUsername(username);
        return customer;
    }
}
