package com.example.ProjectV2.dto.Customer;

import com.example.ProjectV2.entity.Customer;
import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.entity.builder.CustomerBuilder;
import com.example.ProjectV2.entity.builder.ExpertBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerSaveDto {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;


    public Customer getCustomer() {
        return new CustomerBuilder().firstName(firstName).lastName(lastName).username(username).password(password)
                .email(email).build();
    }
}