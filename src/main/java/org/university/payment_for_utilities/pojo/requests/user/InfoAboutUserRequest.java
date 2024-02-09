package org.university.payment_for_utilities.pojo.requests.user;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InfoAboutUserRequest extends Request {
    private Long registered;
    private String firstName;
    private String lastName;
    private Long phoneNum;

    @Override
    public boolean isEmpty() {
        return registered.equals(Response.EMPTY_PARENT_ENTITY) ||
                phoneNum.equals(Response.EMPTY_PARENT_ENTITY) ||
                firstName.isBlank() ||
                lastName.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull InfoAboutUserRequest empty() {
        return InfoAboutUserRequest
                .builder()
                .registered(Response.EMPTY_PARENT_ENTITY)
                .phoneNum(Response.EMPTY_PARENT_ENTITY)
                .firstName("")
                .lastName("")
                .build();
    }
}
