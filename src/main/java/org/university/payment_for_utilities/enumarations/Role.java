package org.university.payment_for_utilities.enumarations;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.university.payment_for_utilities.enumarations.Permission.*;

/**
 * An enumeration representing the different roles in the system.
 * Each constant represents a specific role that a user can have.
 */

@Getter
@RequiredArgsConstructor
public enum Role {
    EMPTY("", "", Set.of()),
    ADMIN("Адмін", "Admin",
            Set.of(
                    ADMIN_READ, ADMIN_CREATE, ADMIN_DELETE, ADMIN_UPDATE
            )
    ),

    BANK_ADMIN("Адміністратор банку", "Bank administrator",
            Set.of(
                    BANK_ADMIN_READ, BANK_ADMIN_CREATE, BANK_ADMIN_DELETE, BANK_ADMIN_UPDATE
            )
    ),

    COMPANY_ADMIN("Адміністратор компанії", "Company administrator",
            Set.of(
                    COMPANY_ADMIN_READ, COMPANY_ADMIN_CREATE, COMPANY_ADMIN_DELETE, COMPANY_ADMIN_UPDATE
            )
    ),

    USER("Користувач", "User",
            Set.of(
                    USER_READ, USER_CREATE, USER_DELETE, USER_UPDATE
            )
    );

    private final String uaName;
    private final String enName;
    private final Set<Permission> permissions;

    public @NonNull List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getValue()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
