package org.university.payment_for_utilities.pojo.requests.user;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.service_information_institutions.PhoneNum;
import org.university.payment_for_utilities.enumarations.Role;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserRequest extends Request {
    private String username;
    private PhoneNum phoneNum;
    private Role role;

    @Override
    public boolean isEmpty() {
        return username.isBlank() ||
                phoneNum.isEmpty() ||
                role == Role.EMPTY;
    }

    @Contract(" -> new")
    public static @NonNull UserRequest empty() {
        return UserRequest
                .builder()
                .username("")
                .phoneNum(PhoneNum.empty())
                .role(Role.EMPTY)
                .build();
    }
}
