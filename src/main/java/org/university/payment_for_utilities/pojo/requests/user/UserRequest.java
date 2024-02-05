package org.university.payment_for_utilities.pojo.requests.user;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.enumarations.Role;
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
public class UserRequest extends Request {
    private String username;
    private Long phoneNum;
    private Role role;

    @Override
    public boolean isEmpty() {
        return username.isBlank() ||
                phoneNum.equals(Response.EMPTY_PARENT_ENTITY) ||
                role == Role.EMPTY;
    }

    @Contract(" -> new")
    public static @NonNull UserRequest empty() {
        return initEmpty(builder())
                .build();
    }

    @Contract("_ -> param1")
    protected static <T extends UserRequestBuilder<?, ?>> @NonNull T initEmpty(@NonNull T builder) {
        builder.username("")
                .phoneNum(Response.EMPTY_PARENT_ENTITY)
                .role(Role.EMPTY);
        return builder;
    }
}
