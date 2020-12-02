package bachelor.thesis.job_recruitment.web.controller;


import bachelor.thesis.job_recruitment.core.model.GenericUser;
import bachelor.thesis.job_recruitment.core.service.UserService;
import bachelor.thesis.job_recruitment.web.converter.PermissionConverter;
import bachelor.thesis.job_recruitment.web.converter.UserConverter;
import bachelor.thesis.job_recruitment.web.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private PermissionConverter permissionConverter;

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    List<UserDTO> findAll(){
        List<GenericUser> users = userService.findAll();

        logger.trace("In UserController - method: findAll - users={}", users);

        return userConverter.convertModelsToDtos(users);
    }

    @PostMapping(value = "/login")
    UserDTO login(@RequestBody String[] credentials){
        String email = credentials[0];
        String password = credentials[1];

        logger.trace("In UserController - method: login - credentials={}",email+ " " + password);
        Optional<GenericUser> genericUser = userService.verifyUserCredentials(email, password);
        if(genericUser.isPresent()){
            logger.trace("In UserController - method: login - retrievedUser={}",genericUser);
            return userConverter.convertModelToDto(genericUser.get());
        }
        return null;
    }

    @PostMapping(value = "/register")
    UserDTO register(@RequestBody UserDTO userDTO){
        logger.trace("In UserController - method: register - user={}", userDTO);
        Optional<GenericUser> genericUser = userService.save(userConverter.convertDtoToModel(userDTO));

        if(genericUser.isEmpty())
            return null;
        return userConverter.convertModelToDto(genericUser.get());
    }

}
