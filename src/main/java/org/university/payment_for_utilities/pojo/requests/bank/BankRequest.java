package org.university.payment_for_utilities.pojo.requests.bank;

import lombok.Builder;
import lombok.Data;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;

@Data
@Builder
public class BankRequest implements Request {
    private String name;
    private String webSite;
    private String edrpou;
    private String mfo;

    @Override
    public boolean isEmpty() {
        return this.name == null || this.name.isEmpty() ||
                this.webSite == null || webSite.isEmpty() ||
                this.edrpou == null || this.edrpou.isEmpty() ||
                this.mfo == null || this.mfo.isEmpty();
    }
}
