package com.example.ProjectV2.dto.Admin;


import com.example.ProjectV2.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminChangePasswordDto {
    private AdminDto adminDto;
    private String oldPassword;
    private String newPassword;

    public Admin getAdmin(){
        Admin admin = new Admin();
        admin.setId(adminDto.getId());
        admin.setPassword(oldPassword);
        admin.setUsername(adminDto.getUsername());
        return admin;
    }

}
