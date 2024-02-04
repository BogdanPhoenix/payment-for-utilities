package org.university.payment_for_utilities.controllers.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.services.interfaces.user.RegisteredUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class RegisteredUserController {
    private final RegisteredUserService service;

    @DeleteMapping("/{id}")
    public Response deactivate(@PathVariable Long id) {
        return service.deactivate(id);
    }
}
