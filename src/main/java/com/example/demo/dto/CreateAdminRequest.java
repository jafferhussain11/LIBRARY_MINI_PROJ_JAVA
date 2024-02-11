package com.example.demo.dto;

import com.example.demo.models.Admin;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CreateAdminRequest {

    @NonNull
    private String email;
    @NonNull
    private String name;

    //function to convert the DTO to Entity

    public Admin toAdmin(){
        return Admin.builder()
                .email(email)
                .name(name)
                .build();
    }
}
