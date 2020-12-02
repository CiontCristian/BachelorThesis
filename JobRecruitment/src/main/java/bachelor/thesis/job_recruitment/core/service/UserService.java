package bachelor.thesis.job_recruitment.core.service;


import bachelor.thesis.job_recruitment.core.model.GenericUser;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<GenericUser> findAll();
    Optional<GenericUser> verifyUserCredentials(String email, String password);
    Optional<GenericUser> save(GenericUser user);
}