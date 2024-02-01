package org.university.payment_for_utilities.pojo.requests.user;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.service_information_institutions.PhoneNum;
import org.university.payment_for_utilities.enumarations.Role;

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
        return RegisteredUserRequest
                .builder()
                .username("")
                .phoneNum(PhoneNum.empty())
                .role(Role.EMPTY)
                .password("")
                .build();
    }
}
