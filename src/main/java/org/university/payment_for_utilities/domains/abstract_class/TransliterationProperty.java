package org.university.payment_for_utilities.domains.abstract_class;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.abstract_class.TransliterationResponse;

@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public abstract class TransliterationProperty extends TableInfo {
    @Column(name = "ua_name", nullable = false, unique = true)
    @NonNull
    protected String uaName;

    @Column(name = "en_name", nullable = false, unique = true)
    @NonNull
    protected String enName;

    @Contract("_ -> new")
    protected <T extends TransliterationResponse.TransliterationResponseBuilder<?, ?>> T responseTransliterationPropertyBuilder(@NonNull T builder) {
        super.responseBuilder(builder)
                .uaName(this.uaName)
                .enName(this.enName);
        return builder;
    }
}
