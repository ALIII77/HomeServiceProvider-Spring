package com.example.ProjectV2.dto.Admin;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
}
