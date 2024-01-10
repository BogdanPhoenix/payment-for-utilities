package org.university.payment_for_utilities.domains.address;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.domains.TableInfo;
import org.university.payment_for_utilities.domains.company.Company;
import org.university.payment_for_utilities.domains.user.RegisteredUser;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "addresses_residence",
    uniqueConstraints = {
            @UniqueConstraint(columnNames = {"id_settlement", "en_name_street", "num_house", "num_entrance"}),
            @UniqueConstraint(columnNames = {"id_settlement", "en_name_street", "num_house", "num_entrance", "num_apartment"})
    })
public class AddressResidence implements TableInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_settlement", nullable = false)
    @NonNull
    private Settlement settlement;

    @Column(name = "ua_name_street", nullable = false)
    @NonNull
    private String uaNameStreet;

    @Column(name = "en_name_street", nullable = false)
    @NonNull
    private String enNameStreet;

    @Column(name = "num_house", length = 5, nullable = false)
    @NonNull
    private String numHouse;

    @Column(name = "num_entrance", length = 5)
    private String numEntrance;

    @Column(name = "num_apartment", length = 5)
    private String numApartment;

    @Column(name = "current_data")
    private boolean currentData;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Company company;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "addresses", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RegisteredUser> users;

    @Override
    public boolean isEmpty() {
        return settlement.isEmpty() ||
                uaNameStreet.isEmpty() ||
                enNameStreet.isEmpty() ||
                numHouse.isEmpty();

    }
}
