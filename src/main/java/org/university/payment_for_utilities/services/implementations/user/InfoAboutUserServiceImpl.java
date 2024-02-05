package org.university.payment_for_utilities.services.implementations.user;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.user.InfoAboutUser;
import org.university.payment_for_utilities.domains.user.RegisteredUser;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.requests.user.InfoAboutUserRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.repositories.user.InfoAboutUserRepository;
import org.university.payment_for_utilities.repositories.user.RegisteredUserRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.user.InfoAboutUserService;

import java.util.Optional;

@Service
public class InfoAboutUserServiceImpl extends CrudServiceAbstract<InfoAboutUser, InfoAboutUserRepository> implements InfoAboutUserService {
    private final RegisteredUserRepository registeredUserRepository;

    @Autowired
    public InfoAboutUserServiceImpl(
            InfoAboutUserRepository repository,
            RegisteredUserRepository registeredUserRepository
    ) {
        super(repository, "Info about users");
        this.registeredUserRepository = registeredUserRepository;
    }

    @Override
    protected InfoAboutUser createEntity(Request request) {
        var infoRequest = (InfoAboutUserRequest) request;
        var registeredUser = getRegisteredUser(infoRequest.getRegistered());

        return InfoAboutUser
                .builder()
                .registered(registeredUser)
                .firstName(infoRequest.getFirstName())
                .lastName(infoRequest.getLastName())
                .build();
    }

    @Override
    protected void updateEntity(@NonNull InfoAboutUser entity, @NonNull Request request) {
        var newValue = (InfoAboutUserRequest) request;

        if(!newValue.getRegistered().equals(Response.EMPTY_PARENT_ENTITY)){
            var registeredUser = getRegisteredUser(newValue.getRegistered());
            entity.setRegistered(registeredUser);
        }
        if(!newValue.getFirstName().isBlank()){
            entity.setFirstName(newValue.getFirstName());
        }
        if(!newValue.getLastName().isBlank()){
            entity.setLastName(newValue.getLastName());
        }
    }

    private @NonNull RegisteredUser getRegisteredUser(@NonNull Long id) {
        return CrudServiceAbstract
                .getEntity(registeredUserRepository, id);
    }

    @Override
    protected void validationProcedureRequest(@NonNull Request request) throws InvalidInputDataException {
        var infoRequest = (InfoAboutUserRequest) request;

        validateName(infoRequest.getFirstName());
        validateName(infoRequest.getLastName());
    }

    @Override
    protected Optional<InfoAboutUser> findEntity(@NonNull Request request) {
        var infoRequest = (InfoAboutUserRequest) request;
        return repository
                .findByFirstNameAndLastName(
                        infoRequest.getFirstName(),
                        infoRequest.getLastName()
                );
    }
}
