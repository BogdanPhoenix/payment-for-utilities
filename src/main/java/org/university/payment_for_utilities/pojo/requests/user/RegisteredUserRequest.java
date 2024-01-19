package org.university.payment_for_utilities.pojo.requests.user;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.service_information_institutions.PhoneNum;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RegisteredUserRequest extends Request {
    private String userEmail;
    private String passwordUser;
    private PhoneNum phoneNum;

    @Override
    public boolean isEmpty() {
        return userEmail.isBlank() ||
                passwordUser.isBlank() ||
                phoneNum.isEmpty();
    }

    @Contract(" -> new")
    public static @NonNull RegisteredUserRequest empty() {
        return RegisteredUserRequest
                .builder()
                .userEmail("")
                .passwordUser("")
                .phoneNum(PhoneNum.empty())
                .build();
    }
}
