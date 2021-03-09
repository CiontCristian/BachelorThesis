package bachelor.thesis.job_recruitment.core.service;


import bachelor.thesis.job_recruitment.core.model.*;
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

    @Override
    public void saveUsersToFile() {
        List<GenericUser> users = userRepository.findAll();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("E:\\FACULTATE\\AN 3\\SEMESTRU 5\\Licenta\\BachelorThesis\\users.txt"));
            writer.write("id");
            writer.write("\n");
            for(GenericUser user: users){
                writer.write(user.getId().toString());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException runtimeException){
            log.debug(runtimeException.getMessage());
        }
    }

    @Override
    public void generateUsers() {
        List<String> emails = Arrays.asList("e1@gmail.com","e2@gmail.com","e3@gmail.com","e4@gmail.com","e5@gmail.com");
        String password = "1234";
        List<String> firstNames = Arrays.asList("Chris","John","Steve","Ana","Tom");
        List<String> lastNames = Arrays.asList("Ciont","Doe","Johnson","Blandiana");
        List<Character> gender = Arrays.asList('M','F');

        List<String> formalEducation = Arrays.asList("Bachelor CS", "Highschool MI");
        List<String> experience = Arrays.asList("entry", "junior", "middle", "senior", "lead", "manager");

        Random random = new Random();
        List<GenericUser> users = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            Background background = new Background(formalEducation.get(random.nextInt(formalEducation.size())),
                    experience.get(random.nextInt(experience.size())));
            Permission permission = new Permission(true, false, false);
            Location location = new Location("","Cluj-Napoca","Romania");
            GenericUser genericUser = new GenericUser(password, emails.get(i), firstNames.get(random.nextInt(firstNames.size())),
                    lastNames.get(random.nextInt(lastNames.size())), new Date(), gender.get(random.nextInt(gender.size())),
                    background, permission, location);
            users.add(genericUser);
        }
        userRepository.saveAll(users);

    }


}
