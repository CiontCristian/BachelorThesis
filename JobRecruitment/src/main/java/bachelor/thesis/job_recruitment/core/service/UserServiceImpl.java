package bachelor.thesis.job_recruitment.core.service;


import bachelor.thesis.job_recruitment.core.model.GenericUser;
import bachelor.thesis.job_recruitment.core.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    public static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<GenericUser> findAll() {
        List<GenericUser> users = userRepository.findAll();
        logger.trace("In UserServiceImpl - method: findAll() - users={}", users);
        return users;
    }

    @Override
    public Optional<GenericUser> verifyUserCredentials(String email, String password) {
        logger.trace("In UserServiceImpl - method: verifyUserCredentials() - credentials={}",email+ " " + password);

        return findAll().stream().filter(genericUser -> genericUser.getEmail().equals(email)
                && genericUser.getPassword().equals(password)).findFirst();
    }

    @Override
    public Optional<GenericUser> save(GenericUser user) {
        logger.trace("In UserServiceImpl - method: save() - user={}", user);
        Optional<GenericUser> alreadyUsed = findAll().stream()
                .filter(genericUser -> genericUser.getEmail().equals(user.getEmail())
                || genericUser.getUsername().equals(user.getUsername()))
                .findFirst();
        if(alreadyUsed.isPresent())
            return Optional.empty();
        userRepository.save(user);

        return Optional.of(user);
    }


}
