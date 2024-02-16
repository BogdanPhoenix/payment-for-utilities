package org.university.payment_for_utilities.controllers.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/${application.uri.main}/users")
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
    public AuthenticationResponse refreshToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken
    ) {
        return service.refreshToken(refreshToken);
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
    public AuthenticationResponse changePassword(
            @RequestBody ChangePasswordRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken
    ) {
        return service.changePassword(request, refreshToken);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response updateData(
            @PathVariable Long id,
            @RequestBody UserRequest request
    ) {
        return service.updateData(id, request);
    }

    @DeleteMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    public Response delete(@RequestBody RegisteredUserRequest request) {
        return service.deactivate(request);
    }

    @DeleteMapping("/admin/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteAll() {
        var countRemoved = service.deactivateAll();
        var message = String.format("%d entity(s) has been deleted.", countRemoved);

        return ResponseEntity.ok(message);
    }
}
