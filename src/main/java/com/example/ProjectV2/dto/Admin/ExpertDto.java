package com.example.ProjectV2.dto.Admin;


import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.entity.Order;
import com.example.ProjectV2.entity.enums.ExpertStatus;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ExpertDto {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private double score;

}


