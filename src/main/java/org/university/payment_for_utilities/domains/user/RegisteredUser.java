package org.university.payment_for_utilities.domains.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.domains.address.AddressResidence;
import org.university.payment_for_utilities.domains.bank.Bank;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "registered_users")
public class RegisteredUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_email", nullable = false, unique = true)
    @NonNull
    private String userEmail;

    @Column(name = "password_user", length = 1000, nullable = false)
    @NonNull
    private String passwordUser;

    @Column(name = "phone_num", length = 20, nullable = false, unique = true)
    @NonNull
    private String phoneNum;

    @Column(name = "current_data")
    private boolean currentData;

    @OneToOne(mappedBy = "registered", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private InfoAboutUser infoUser;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_payment_addresses",
            joinColumns = @JoinColumn(name = "id_registered_user", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_address", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_registered_user", "id_address"})
    )
    private List<AddressResidence> addresses;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "registered_user_banks",
            joinColumns = @JoinColumn(name = "id_registered_user", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_bank", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_registered_user", "id_bank"})
    )
    private List<Bank> banks;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "registeredUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ContractEntity> contractEntities;
}
