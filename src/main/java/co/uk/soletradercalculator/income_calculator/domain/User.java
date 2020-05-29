package co.uk.soletradercalculator.income_calculator.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {


    @Column(unique = true)
    @Email
    private String email;

    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

}
