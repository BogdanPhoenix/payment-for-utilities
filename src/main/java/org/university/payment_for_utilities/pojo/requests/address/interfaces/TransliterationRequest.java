package org.university.payment_for_utilities.pojo.requests.address.interfaces;

/**
 * An interface representing the query structure that must necessarily contain the name of the entity in Ukrainian and English that can be used to interact with the system.
 */
public interface TransliterationRequest extends Request {
    /**
     * Gets the Ukrainian name of the attribute.
     * @return The Ukrainian name of the attribute.
     */
    String getUaName();

    /**
     * Gets the English name of the attribute.
     * @return The English name of the attribute.
     */
    String getEnName();
}
