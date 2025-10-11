package com.kelevo.pizza.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user_role")
@IdClass(UserRolId.class)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserRoleEntity {

    @Id
    @Column(nullable = false, length = 20)
    private String username;

    @Id
    @Column(nullable = false, length = 20)
    private String role;

    @Column(name = "granted_date", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime grantedDate;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private UserEntity user;

}
