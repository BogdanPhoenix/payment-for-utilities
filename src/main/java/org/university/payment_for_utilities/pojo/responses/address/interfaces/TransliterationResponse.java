package org.university.payment_for_utilities.pojo.responses.address.interfaces;

import org.university.payment_for_utilities.pojo.responses.interfaces.Response;

/**
 * The interface that defines the response for transliteration extends the basic response interface.
 */
public interface TransliterationResponse extends Response {
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
