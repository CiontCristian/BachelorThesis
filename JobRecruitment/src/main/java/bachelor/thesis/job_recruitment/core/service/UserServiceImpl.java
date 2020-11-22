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
    public Optional<GenericUser> login(String username, String password) {


        return Optional.empty();
    }
}
