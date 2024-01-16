package org.university.payment_for_utilities.domains.abstract_class;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class TableInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected Long id;

    @Column(name = "current_data")
    protected boolean currentData;

    /**
     * Checks if the entity is empty.
     *
     * @return true if the entity does not contain at least one empty attribute, otherwise false.
     */
    public abstract boolean isEmpty();
}
