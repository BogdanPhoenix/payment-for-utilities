package org.university.payment_for_utilities.domains;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.enumarations.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "info_about_users")
public class InfoAboutUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_registered_user", nullable = false, unique = true)
    @NonNull
    private RegisteredUser registered;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "first_name", nullable = false)
    @NonNull
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NonNull
    private String lastName;

    @Column(name = "patronymic", nullable = false)
    @NonNull
    private String patronymic;

    @Column(name = "current_data")
    private boolean currentData;
}
