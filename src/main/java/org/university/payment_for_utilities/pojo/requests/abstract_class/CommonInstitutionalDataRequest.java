package org.university.payment_for_utilities.pojo.requests.abstract_class;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommonInstitutionalDataRequest extends TransliterationRequest {
    private Long edrpou;
    private Long website;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() ||
                edrpou.equals(Response.EMPTY_PARENT_ENTITY) ||
                website.equals(Response.EMPTY_PARENT_ENTITY);
    }

    @Contract("_ -> param1")
    protected static <T extends CommonInstitutionalDataRequestBuilder<?, ?>> @NonNull T initEmpty(@NonNull T builder) {
        TransliterationRequest
                .initEmpty(builder)
                .edrpou(Response.EMPTY_PARENT_ENTITY)
                .website(Response.EMPTY_PARENT_ENTITY);
        return builder;
    }
}
