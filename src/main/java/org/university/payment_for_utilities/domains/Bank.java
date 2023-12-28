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
@Table(name = "banks")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    @NonNull
    private String name;

    @Column(name = "web_site", length = 500, nullable = false, unique = true)
    @NonNull
    private String webSite;

    @Column(name = "edrpou", length = 8, nullable = false, unique = true)
    @NonNull
    private String edrpou;

    @Column(name = "mfo", length = 6, nullable = false, unique = true)
    @NonNull
    private String mfo;

    @Column(name = "current_data")
    private boolean currentData;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BankPhoneNum> phones;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Receipt> receipts;

    @ManyToMany(mappedBy = "banks", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RegisteredUser> users;
}
