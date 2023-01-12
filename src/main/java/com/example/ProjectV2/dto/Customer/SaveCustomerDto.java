package com.example.ProjectV2.dto.Customer;


import com.example.ProjectV2.entity.Customer;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SaveCustomerDto {
    private String firstName;
    private String lastName;
    private String password;
    private String username;
    private String email;

    public Customer getCustomer(){
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setUsername(username);
        customer.setPassword(password);
        return customer;
    }
}
