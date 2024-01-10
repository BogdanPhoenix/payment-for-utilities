package org.university.payment_for_utilities.pojo.responses.bank;

import lombok.Builder;
import lombok.Data;
import org.university.payment_for_utilities.domains.service_information_institutions.Edrpou;
import org.university.payment_for_utilities.domains.service_information_institutions.Website;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;

@Data
@Builder
public class BankResponse implements Response {
    private Long id;
    private String name;
    private Website webSite;
    private Edrpou edrpou;
    private String mfo;
}
