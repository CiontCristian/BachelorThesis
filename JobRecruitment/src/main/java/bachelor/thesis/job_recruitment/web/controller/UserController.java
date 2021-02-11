package bachelor.thesis.job_recruitment.web.controller;


import bachelor.thesis.job_recruitment.core.model.GenericUser;
import bachelor.thesis.job_recruitment.core.service.UserService;
import bachelor.thesis.job_recruitment.web.converter.ContractorConverter;
import bachelor.thesis.job_recruitment.web.converter.PermissionConverter;
import bachelor.thesis.job_recruitment.web.converter.UserConverter;
import bachelor.thesis.job_recruitment.web.dto.ContractorDTO;
import bachelor.thesis.job_recruitment.web.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

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
    @Autowired
    private ContractorConverter contractorConverter;

    @GetMapping(value = "/findAll")
    ResponseEntity<List<UserDTO>> findAll(){
        List<GenericUser> users = userService.findAll();

        logger.trace("In UserController - method: findAll - users={}", users);

        return new ResponseEntity<>(userConverter.convertModelsToDtos(users), HttpStatus.OK);
    }

    @GetMapping(value = "/login")
    ResponseEntity<UserDTO> login(@RequestParam String email, @RequestParam String password) throws HttpClientErrorException{

        logger.trace("In UserController - method: login - credentials={}",email+ " " + password);
        Optional<GenericUser> genericUser = userService.verifyUserCredentials(email, password);

        if (genericUser.isPresent()) {
            logger.trace("In UserController - method: login - retrievedUser={}", genericUser);
            return new ResponseEntity<>(userConverter.convertModelToDto(genericUser.get()), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping(value = "/register")
    ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO){
        logger.trace("In UserController - method: register - user={}", userDTO);
        Optional<GenericUser> genericUser = userService.save(userConverter.convertDtoToModel(userDTO));

        if(genericUser.isPresent()) {
            logger.trace("In UserController - method: register - savedUser={}", genericUser);
            return new ResponseEntity<>(userConverter.convertModelToDto(genericUser.get()), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "modifyCompany/{id}")
    ResponseEntity<UserDTO> modifyCompany(@PathVariable Long id, @RequestBody ContractorDTO contractorDTO){
        logger.debug("In UserController - method: modifyCompany - id={}, company={}", id, contractorDTO);
        Optional<GenericUser> modifiedUser = userService.modifyCompany(id, contractorConverter.convertDtoToModel(contractorDTO));

        if(modifiedUser.isPresent()){
            return new ResponseEntity<>(userConverter.convertModelToDto(modifiedUser.get()), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

