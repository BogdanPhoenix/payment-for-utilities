package org.university.payment_for_utilities.pojo.responses.user;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.university.payment_for_utilities.domains.user.RegisteredUser;
import org.university.payment_for_utilities.enumarations.Role;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InfoAboutUserResponse extends Response {
    private RegisteredUser registered;
    private Role role;
    private String firstName;
    private String lastName;
}
