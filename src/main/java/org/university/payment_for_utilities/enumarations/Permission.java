package org.university.payment_for_utilities.enumarations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * A list representing different permissions for registered users in the system.
 * Each permission has a specific value.
 */
@Getter
@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    BANK_ADMIN_READ("bank_admin:read"),
    BANK_ADMIN_UPDATE("bank_admin:update"),
    BANK_ADMIN_CREATE("bank_admin:create"),
    BANK_ADMIN_DELETE("bank_admin:delete"),

    COMPANY_ADMIN_READ("company_admin:read"),
    COMPANY_ADMIN_UPDATE("company_admin:update"),
    COMPANY_ADMIN_CREATE("company_admin:create"),
    COMPANY_ADMIN_DELETE("company_admin:delete"),

    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_CREATE("user:create"),
    USER_DELETE("user:delete");

    private final String value;
}
