package bachelor.thesis.job_recruitment.core.service;


import bachelor.thesis.job_recruitment.core.model.Contractor;
import bachelor.thesis.job_recruitment.core.model.GenericUser;
import bachelor.thesis.job_recruitment.core.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<GenericUser> findAll() {
        List<GenericUser> users = userRepository.findAll();
        log.trace("In UserServiceImpl - method: findAll() - users={}", users);
        return users;
    }

    @Override
    public Optional<GenericUser> verifyUserCredentials(String email, String password) {
        log.trace("In UserServiceImpl - method: verifyUserCredentials() - credentials={}",email+ " " + password);

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
    @Transactional
    public Optional<GenericUser> modifyCompany(Long id, Contractor contractor) {
        Optional<GenericUser> current = findAll().stream()
                .filter(genericUser -> genericUser.getId().equals(id))
                .findFirst();

        if(current.isEmpty())
            return Optional.empty();

        //current.get().setCompany(contractor);
        return current;
    }


}
