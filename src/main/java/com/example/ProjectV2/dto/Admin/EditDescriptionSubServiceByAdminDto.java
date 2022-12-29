package com.example.ProjectV2.dto.Admin;

import com.example.ProjectV2.entity.SubService;
import com.example.ProjectV2.entity.builder.SubServiceBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EditDescriptionSubServiceByAdminDto {
    private String subServiceName;
    private String description;

    public SubService getSubService(){
        SubService subService = new SubServiceBuilder().name(subServiceName).build();
        return subService;
    }
}
