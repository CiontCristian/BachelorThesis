package bachelor.thesis.job_recruitment.core.service;


import bachelor.thesis.job_recruitment.core.model.*;
import bachelor.thesis.job_recruitment.core.repository.PreferenceRepository;
import bachelor.thesis.job_recruitment.core.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContractorService contractorService;
    @Autowired
    private PreferenceRepository preferenceRepository;

    @Override
    public List<GenericUser> findAll() {
        List<GenericUser> users = userRepository.findAll();
        log.trace("In UserServiceImpl - method: findAll() - users={}", users);
        return users;
    }

    @Override
    public Optional<GenericUser> verifyUserCredentials(String email, String password) {
        log.trace("In UserServiceImpl - method: verifyUserCredentials() - email={}, password={}",email, password);

        return findAll().stream().filter(genericUser -> genericUser.getEmail().equals(email)
                && genericUser.getPassword().equals(password)).findFirst();
    }

    @Override
    public Optional<GenericUser> save(GenericUser user) {
        log.trace("In UserServiceImpl - method: save() - user={}", user);
        Optional<GenericUser> alreadyUsed = findAll().stream()
                .filter(genericUser -> genericUser.getEmail().equals(user.getEmail()))
                .findFirst();
        if(alreadyUsed.isPresent())
            return Optional.empty();
        GenericUser savedUser = userRepository.save(user);

        return Optional.of(savedUser);
    }

    @Override
    public GenericUser modify(GenericUser modifiedUser) {
        log.trace("In UserServiceImpl - method: modify() - modifiedUser={}", modifiedUser);
        return userRepository.save(modifiedUser);
    }

    @Override
    public void remove(Long id) {
        log.trace("In UserServiceImpl - method: remove() - id={}", id);

        contractorService.findContractorForUser(id).ifPresent(contractor ->
                contractorService.removeContractor(contractor.getId()));
        userRepository.deleteById(id);
    }

    @Override
    public List<GenericUser> findJobCandidates(Long jobId) {
        log.trace("In UserServiceImpl - method: findJobCandidates() - id={}", jobId);

        return preferenceRepository.findAll().stream()
                .filter(preference -> preference.getJob().getId().equals(jobId))
                .map(Preference::getUser)
                .collect(Collectors.toList());
    }
}
