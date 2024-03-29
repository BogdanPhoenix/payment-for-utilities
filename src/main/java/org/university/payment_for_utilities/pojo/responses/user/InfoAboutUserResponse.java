package org.university.payment_for_utilities.pojo.responses.user;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.PhoneNumResponse;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class InfoAboutUserResponse extends Response {
    private UserResponse registered;
    private String firstName;
    private String lastName;
    private PhoneNumResponse phoneNum;

    @Override
    public boolean isEmpty() {
        return registered.isEmpty() ||
                firstName.isBlank() ||
                lastName.isBlank() ||
                phoneNum.isEmpty();
    }

    @Contract(" -> new")
    public static @NonNull InfoAboutUserResponse empty() {
        return Response
                .initEmpty(builder())
                .registered(UserResponse.empty())
                .firstName("")
                .lastName("")
                .phoneNum(PhoneNumResponse.empty())
                .build();
    }
}
