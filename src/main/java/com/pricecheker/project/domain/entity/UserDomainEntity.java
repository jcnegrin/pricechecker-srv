package com.pricecheker.project.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDomainEntity {
    private String id;
    private String name;
    private String surname;
    private String userName;
    private String email;
    private String password;
    private LocalDateTime registrationDate;




}
