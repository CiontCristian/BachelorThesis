package bachelor.thesis.job_recruitment.server.controller;


import bachelor.thesis.job_recruitment.core.model.Contractor;
import bachelor.thesis.job_recruitment.core.model.GenericUser;
import bachelor.thesis.job_recruitment.core.service.UserService;

import bachelor.thesis.job_recruitment.server.exception.BadRequestException;
import bachelor.thesis.job_recruitment.server.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleNotFound(ResourceNotFoundException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleBadRequest(BadRequestException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/findAllUsers")
    ResponseEntity<List<GenericUser>> findAllUsers(){
        List<GenericUser> users = userService.findAll();

        log.trace("In UserController - method: findAll - number of users={}", users.size());

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/login")
    ResponseEntity<GenericUser> login(@RequestParam String email, @RequestParam String password) throws HttpClientErrorException{

        log.trace("In UserController - method: login - credentials={}",email+ " " + password);
        Optional<GenericUser> genericUser = userService.verifyUserCredentials(email, password);

        if (genericUser.isPresent()) {
            log.trace("In UserController - method: login - retrievedUser={}", genericUser);
            return new ResponseEntity<>(genericUser.get(), HttpStatus.OK);
        }

        throw new ResourceNotFoundException("Invalid credentials!");

    }

    @PostMapping(value = "/register")
    ResponseEntity<GenericUser> register(@RequestBody GenericUser receivedUser){
        log.trace("In UserController - method: register - user={}", receivedUser);
        Optional<GenericUser> savedUser = userService.save(receivedUser);

        if(savedUser.isPresent()) {
            log.trace("In UserController - method: register - savedUser={}", savedUser);
            return new ResponseEntity<>(savedUser.get(), HttpStatus.CREATED);
        }

        throw new BadRequestException("Email or username already taken!");
    }

    @PutMapping(value = "/modify")
    ResponseEntity<GenericUser> modify(@RequestBody GenericUser modifiedUser){
        modifiedUser = userService.modify(modifiedUser);

        return new ResponseEntity<>(modifiedUser, HttpStatus.OK);
    }

    @GetMapping(value = "/findJobCandidates/{jobId}")
    ResponseEntity<List<GenericUser>> findJobCandidates(@PathVariable Long jobId){
        log.trace("In UserController - method: findJobCandidates - jobId={}", jobId);
        List<GenericUser> users = userService.findJobCandidates(jobId);
        log.trace("In UserController - method: findJobCandidates - users={}", users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping(value = "/removeUser/{id}")
    ResponseEntity<?> removeUser(@PathVariable Long id){
        userService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

