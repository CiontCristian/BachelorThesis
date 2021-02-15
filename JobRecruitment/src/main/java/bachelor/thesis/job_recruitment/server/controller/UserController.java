package bachelor.thesis.job_recruitment.server.controller;


import bachelor.thesis.job_recruitment.core.model.Contractor;
import bachelor.thesis.job_recruitment.core.model.GenericUser;
import bachelor.thesis.job_recruitment.core.service.UserService;

import bachelor.thesis.job_recruitment.server.exception.BadRequestException;
import bachelor.thesis.job_recruitment.server.exception.ResourceNotFoundException;
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
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleNotFound(ResourceNotFoundException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleNotFound(BadRequestException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/findAll")
    ResponseEntity<List<GenericUser>> findAll(){
        List<GenericUser> users = userService.findAll();

        logger.trace("In UserController - method: findAll - users={}", users);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/login")
    ResponseEntity<GenericUser> login(@RequestParam String email, @RequestParam String password) throws HttpClientErrorException{

        logger.trace("In UserController - method: login - credentials={}",email+ " " + password);
        Optional<GenericUser> genericUser = userService.verifyUserCredentials(email, password);

        if (genericUser.isPresent()) {
            logger.trace("In UserController - method: login - retrievedUser={}", genericUser);
            //return new ResponseEntity<>(userConverter.convertModelToDto(genericUser.get()), HttpStatus.OK);
            return new ResponseEntity<>(genericUser.get(), HttpStatus.OK);
        }

        //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        throw new ResourceNotFoundException("Invalid credentials!");

    }

    @PostMapping(value = "/register")
    ResponseEntity<GenericUser> register(@RequestBody GenericUser receivedUser){
        logger.trace("In UserController - method: register - user={}", receivedUser);
        Optional<GenericUser> savedUser = userService.save(receivedUser);

        if(savedUser.isPresent()) {
            logger.trace("In UserController - method: register - savedUser={}", savedUser);
            return new ResponseEntity<>(savedUser.get(), HttpStatus.CREATED);
        }

        throw new BadRequestException("Email or username already taken!");
    }

}

