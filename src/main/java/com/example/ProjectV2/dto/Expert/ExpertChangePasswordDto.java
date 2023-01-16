package com.example.ProjectV2.dto.Expert;


import com.example.ProjectV2.entity.Admin;
import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.entity.builder.ExpertBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExpertChangePasswordDto {
    private String oldPassword;
    private String newPassword;
}
