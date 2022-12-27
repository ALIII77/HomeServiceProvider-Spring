package com.example.ProjectV2.dto.Expert;


import com.example.ProjectV2.entity.Expert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExpertChangePasswordDto {
    private Expert expert;
    private String newPassword;
}
