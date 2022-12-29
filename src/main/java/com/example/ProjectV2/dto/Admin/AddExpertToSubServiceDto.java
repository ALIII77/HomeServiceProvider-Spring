package com.example.ProjectV2.dto.Admin;

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
public class AddExpertToSubServiceDto {
    private String expertUsername;
    private String subServiceName;

    public Expert getExpert(){
        return new ExpertBuilder().username(expertUsername).build();
    }

}
