package org.university.payment_for_utilities.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.controllers.AdminDeleteController;
import org.university.payment_for_utilities.pojo.requests.user.InfoAboutUserRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.services.interfaces.user.InfoAboutUserService;

import java.util.List;

@RestController
@RequestMapping("/${application.uri.main}/user-info")
public class InfoAboutUserController extends AdminDeleteController<InfoAboutUserRequest> {
    @Autowired
    public InfoAboutUserController(InfoAboutUserService service) { super(service); }

    @GetMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    public List<Response> getAll() {
        return service.getAll();
    }

    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response getEntity(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@RequestBody InfoAboutUserRequest request) {
        return service.addValue(request);
    }

    @PatchMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response update(
            @PathVariable Long id,
            @RequestBody InfoAboutUserRequest request
    ) {
        return service.updateValue(id, request);
    }

    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response delete(@PathVariable Long id) {
        return service.removeValue(id);
    }
}
