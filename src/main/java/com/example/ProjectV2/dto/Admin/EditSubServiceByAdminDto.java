package com.example.ProjectV2.dto.Admin;

import com.example.ProjectV2.entity.SubService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EditSubServiceByAdminDto {
    private SubService subService;
    private String description;
    private double basePrice;
}
