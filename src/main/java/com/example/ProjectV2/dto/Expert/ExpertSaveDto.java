package com.example.ProjectV2.dto.Expert;

import com.example.ProjectV2.entity.Expert;
import com.example.ProjectV2.entity.builder.ExpertBuilder;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ExpertSaveDto {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;


    public Expert getExpert(){
        return new ExpertBuilder().firstName(firstName).lastName(lastName).username(username).password(password)
                .email(email).build();
    }
}
