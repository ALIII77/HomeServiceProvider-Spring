package com.example.ProjectV2.dto.Admin;

import com.example.ProjectV2.entity.Service;
import com.example.ProjectV2.entity.builder.ServiceBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceSaveDto {
    private String name;

    public Service getService(){
        return new ServiceBuilder().name(name).build();
    }
}
