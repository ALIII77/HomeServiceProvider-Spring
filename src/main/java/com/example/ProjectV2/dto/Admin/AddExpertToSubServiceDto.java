package com.example.ProjectV2.dto.Admin;

import com.example.ProjectV2.entity.Expert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddExpertToSubServiceDto {
    private Expert expert;
    private String subServiceName;
}
