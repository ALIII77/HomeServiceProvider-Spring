package com.example.ProjectV2.dto.Admin;

import com.example.ProjectV2.entity.Admin;
import com.example.ProjectV2.entity.builder.AdminBuilder;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AdminDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    public Admin getAdmin(){
        Admin admin = AdminBuilder.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password)
                .email(email)
                .build();
        return admin;
    }
}
