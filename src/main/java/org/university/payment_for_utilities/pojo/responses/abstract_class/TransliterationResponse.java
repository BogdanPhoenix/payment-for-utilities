package org.university.payment_for_utilities.pojo.responses.abstract_class;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * The abstract class that defines the response for transliteration extends the basic response interface.
 */
@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class TransliterationResponse extends Response {
    private String uaName;
    private String enName;
}
