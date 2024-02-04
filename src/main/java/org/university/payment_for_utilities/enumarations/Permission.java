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
    ADMIN_DELETE("admin:delete");

    private final String value;
}
