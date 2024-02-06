package org.university.payment_for_utilities.services.interfaces.receipt;

import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.services.interfaces.CrudService;

import java.util.List;

public interface ReceiptService extends CrudService {
    List<Response> findByUserId(Long id);
}
