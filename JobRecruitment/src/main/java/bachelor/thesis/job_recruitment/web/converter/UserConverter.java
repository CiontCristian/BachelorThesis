package bachelor.thesis.job_recruitment.web.converter;

import bachelor.thesis.job_recruitment.core.model.GenericUser;
import bachelor.thesis.job_recruitment.web.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter extends BaseConverter<GenericUser, UserDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GenericUser convertDtoToModel(UserDTO dto) {
        GenericUser user = modelMapper.map(dto, GenericUser.class);
        user.setId(dto.getId());
        return user;
    }

    @Override
    public UserDTO convertModelToDto(GenericUser genericUser) {
        UserDTO userDTO = modelMapper.map(genericUser, UserDTO.class);
        userDTO.setId(genericUser.getId());
        return userDTO;
    }
}
