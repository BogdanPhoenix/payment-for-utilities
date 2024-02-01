package org.university.payment_for_utilities.pojo.requests.user;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RegisteredUserRequest extends UserRequest {
    private String password;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || password.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull RegisteredUserRequest empty() {
        return UserRequest
                .initEmpty(builder())
                .password("")
                .build();
    }
}
