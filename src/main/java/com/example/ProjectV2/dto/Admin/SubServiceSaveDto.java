package com.example.ProjectV2.dto.Admin;

import com.example.ProjectV2.entity.SubService;
import com.example.ProjectV2.entity.builder.ServiceBuilder;
import com.example.ProjectV2.entity.builder.SubServiceBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubServiceSaveDto {
    private String serviceName;
    private String subServiceName;
    private String description;
    private double basePrice;

    public SubService getSubService(){
        return new SubServiceBuilder().name(subServiceName).description(description).basePrice(basePrice).build();
    }

}
