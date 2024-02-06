package org.university.payment_for_utilities.controllers.receipt;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.pojo.requests.receipt.PaymentHistoryRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.services.interfaces.receipt.PaymentHistoryService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/${application.uri.main}/payment-history")
public class PaymentHistoryController {
    private final PaymentHistoryService service;

    @GetMapping({"/company-admin", "/admin"})
    @ResponseStatus(HttpStatus.OK)
    public List<Response> getAll() {
        return service.getAll();
    }

    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response getEntity(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/company-admin/system")
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@RequestBody PaymentHistoryRequest request) {
        return service.addValue(request);
    }

    @DeleteMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteAll() {
        var countRemoved = service.removeAll();
        var message = String.format("%d entity(s) has been deleted.", countRemoved);

        return ResponseEntity.ok(message);
    }
}
