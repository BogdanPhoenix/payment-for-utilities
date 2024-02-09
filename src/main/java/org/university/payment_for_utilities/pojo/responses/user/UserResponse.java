package org.university.payment_for_utilities.pojo.responses.user;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.enumarations.Role;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserResponse extends Response {
    private Role role;
    private String username;

    @Override
    public boolean isEmpty() {
        return username.isBlank() ||
                role == Role.EMPTY;
    }

    @Contract(" -> new")
    public static @NonNull UserResponse empty() {
        return Response
                .initEmpty(builder())
                .role(Role.EMPTY)
                .username("")
                .build();
    }
}
