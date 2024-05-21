package com.pricecheker.project.infrastructure.adapters.outbound.persistence.mysql.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "user", schema = "PriceChecker")
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "surname", nullable = false, length = 50)
    private String surname;

    @Column(name = "user_name", unique = true, length = 100)
    private String userName;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "registration_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registrationDate;
}