package org.university.payment_for_utilities.domains.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.abstract_class.TableInfo;
import org.university.payment_for_utilities.domains.address.AddressResidence;
import org.university.payment_for_utilities.domains.bank.Bank;
import org.university.payment_for_utilities.domains.service_information_institutions.PhoneNum;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "registered_users")
public class RegisteredUser extends TableInfo {
    @Column(name = "user_email", nullable = false, unique = true)
    @NonNull
    private String userEmail;

    @Column(name = "password_user", length = 1000, nullable = false)
    @NonNull
    private String passwordUser;

    @OneToOne
    @JoinColumn(name = "id_phone_num", nullable = false, unique = true)
    @NonNull
    private PhoneNum phoneNum;

    @OneToOne(mappedBy = "registered", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private transient InfoAboutUser infoUser;

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
    private transient List<ContractEntity> contractEntities;

    @Override
    public boolean isEmpty() {
        return userEmail.isBlank() ||
                passwordUser.isBlank() ||
                phoneNum.isEmpty();
    }

    @Contract(" -> new")
    public static @NonNull RegisteredUser empty() {
        var builder = builder();
        TableInfo.initEmpty(builder);

        return builder
                .userEmail("")
                .passwordUser("")
                .phoneNum(PhoneNum.empty())
                .build();
    }
}
