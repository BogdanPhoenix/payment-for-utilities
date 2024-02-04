package org.university.payment_for_utilities.controllers.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.pojo.requests.user.AuthenticationRequest;
import org.university.payment_for_utilities.pojo.requests.user.ChangePasswordRequest;
import org.university.payment_for_utilities.pojo.requests.user.RegisteredUserRequest;
import org.university.payment_for_utilities.pojo.requests.user.UserRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.user.AuthenticationResponse;
import org.university.payment_for_utilities.services.interfaces.user.RegisteredUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment_for_utilities/users")
public class RegisteredUserController {
    private final RegisteredUserService service;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse registration(@RequestBody RegisteredUserRequest request) {
        return service.registration(request);
    }

    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse authentication(@RequestBody AuthenticationRequest request) {
        return service.authenticate(request);
    }

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public void refreshToken(
            @RequestBody HttpServletRequest request,
            @RequestBody HttpServletResponse response
    ) {
        service.refreshToken(request, response);
    }

    @GetMapping("/find/{username}")
    @ResponseStatus(HttpStatus.OK)
    public Response findByUsername(@PathVariable String username) {
        return service.getByUsername(username);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response deactivate(@PathVariable Long id) {
        return service.deactivate(id);
    }

    @PatchMapping("/change-password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(
            @RequestBody ChangePasswordRequest request,
            @RequestBody Authentication authentication
    ) {
        service.changePassword(request, authentication);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response updateData(
            @PathVariable Long id,
            @RequestBody UserRequest request
    ) {
        return service.updateData(id, request);
    }
}
