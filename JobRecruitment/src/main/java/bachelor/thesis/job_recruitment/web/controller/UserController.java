package bachelor.thesis.job_recruitment.web.controller;


import bachelor.thesis.job_recruitment.core.model.GenericUser;
import bachelor.thesis.job_recruitment.core.service.UserService;
import bachelor.thesis.job_recruitment.web.converter.PermissionConverter;
import bachelor.thesis.job_recruitment.web.converter.UserConverter;
import bachelor.thesis.job_recruitment.web.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
