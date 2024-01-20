package org.university.payment_for_utilities.pojo.requests.user;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.user.RegisteredUser;
import org.university.payment_for_utilities.enumarations.Role;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InfoAboutUserRequest extends Request {
    private RegisteredUser registered;
    private Role role;
    private String firstName;
    private String lastName;

    @Override
    public boolean isEmpty() {
        return registered.isEmpty() ||
                firstName.isBlank() ||
                lastName.isBlank() ||
                role == Role.EMPTY;
    }

    @Contract(" -> new")
    public static @NonNull InfoAboutUserRequest empty() {
        return InfoAboutUserRequest
                .builder()
                .registered(RegisteredUser.empty())
                .role(Role.EMPTY)
                .firstName("")
                .lastName("")
                .build();
    }
}
