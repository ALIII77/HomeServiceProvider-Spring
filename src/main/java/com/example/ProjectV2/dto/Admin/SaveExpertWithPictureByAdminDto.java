package com.example.ProjectV2.dto.Admin;

import com.example.ProjectV2.entity.Expert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.File;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaveExpertWithPictureByAdminDto {
    private Expert expert;
    private File pictureFile;

    private Long expertId;
}
