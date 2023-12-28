package org.university.payment_for_utilities.domains;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_payment_addresses",
            joinColumns = @JoinColumn(name = "id_registered_user", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_address", nullable = false)
    )
    private List<AddressResidence> addresses;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "registered_user_banks",
            joinColumns = @JoinColumn(name = "id_registered_user", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_bank", nullable = false)
    )
    private List<Bank> banks;

    @OneToMany(mappedBy = "registeredUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contract> contracts;
}
