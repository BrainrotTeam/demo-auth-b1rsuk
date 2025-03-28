package com.codecon.backend.model;

import com.codecon.backend.model.dto.ClientDto;
import com.codecon.backend.shared.model.BaseModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Client implements BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    Role role = Role.USER;

    @NotBlank(message = "first_name cannot be blank")
    String firstName;

    @NotBlank(message = "last_name cannot be blank")
    String lastName;

    boolean isActivated = false;
    boolean isBanned = false;

    @Email(message = "Email is invalid")
    String email;
    String hashedPassword;

    public ClientDto toDto() {
        return new ClientDto(id, email, role);
    }

}
