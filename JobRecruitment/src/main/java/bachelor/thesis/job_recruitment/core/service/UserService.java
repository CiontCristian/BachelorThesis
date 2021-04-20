package bachelor.thesis.job_recruitment.core.service;


import bachelor.thesis.job_recruitment.core.model.Contractor;
import bachelor.thesis.job_recruitment.core.model.GenericUser;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    List<GenericUser> findAll();
    Optional<GenericUser> verifyUserCredentials(String email, String password);
    Optional<GenericUser> save(GenericUser user);
    GenericUser modify(GenericUser modifiedUser);
    void remove(Long id);
    List<GenericUser> findJobCandidates(Long jobId);
}
