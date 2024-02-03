package org.university.payment_for_utilities.domains.abstract_class;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
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

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;

    @Column(name = "current_data")
    protected boolean enabled;

    public abstract Response getResponse();

    protected <T extends Response.ResponseBuilder<?, ?>> T responseBuilder(@NonNull T builder){
        builder.id(this.id)
                .createDate(this.createDate)
                .updateDate(this.updateDate);
        return builder;
    }

    @PrePersist
    public void onPrePersist(){
        this.setEnabled(true);
        this.setCreateDate(LocalDateTime.now());
        this.setUpdateDate(LocalDateTime.now());
    }
}
