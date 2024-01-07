package org.university.payment_for_utilities.pojo.responses.bank;

import lombok.Builder;
import lombok.Data;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;

@Data
@Builder
public class BankResponse implements Response {
    private Long id;
    private String name;
    private String webSite;
    private String edrpou;
    private String mfo;
}
