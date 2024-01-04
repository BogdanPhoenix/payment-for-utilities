package org.university.payment_for_utilities.domains;

public interface TableInfo {
    /**
     * Gets the identifier for the object.
     *
     * @return object identifier of type Long
     */
    Long getId();

    /**
     * Checks if the entity is empty.
     *
     * @return true if the entity does not contain at least one empty attribute, otherwise false.
     */
    boolean isEmpty();
}
