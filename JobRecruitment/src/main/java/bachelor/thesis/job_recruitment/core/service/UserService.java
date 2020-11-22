package bachelor.thesis.job_recruitment.core.service;


import bachelor.thesis.job_recruitment.core.model.GenericUser;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<GenericUser> findAll();
    Optional<GenericUser> login(String username, String password);
}
