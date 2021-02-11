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
    @Autowired
    private LocationConverter locationConverter;
    @Autowired
    private BackgroundConverter backgroundConverter;
    @Autowired
    private PermissionConverter permissionConverter;
    @Autowired
    private ContractorConverter contractorConverter;

    @Override
    public GenericUser convertDtoToModel(UserDTO dto) {
        GenericUser user = modelMapper.map(dto, GenericUser.class);
        user.setId(dto.getId());
        user.setBackground(backgroundConverter.convertDtoToModel(dto.getBackground()));
        user.setLocation(locationConverter.convertDtoToModel(dto.getLocation()));
        user.setPermission(permissionConverter.convertDtoToModel(dto.getPermission()));
        user.setCompany(dto.getCompany() == null ? null : contractorConverter.convertDtoToModel(dto.getCompany()));
        return user;
    }

    @Override
    public UserDTO convertModelToDto(GenericUser genericUser) {
        UserDTO userDTO = modelMapper.map(genericUser, UserDTO.class);
        userDTO.setId(genericUser.getId());
        userDTO.setBackground(backgroundConverter.convertModelToDto(genericUser.getBackground()));
        userDTO.setLocation(locationConverter.convertModelToDto(genericUser.getLocation()));
        userDTO.setPermission(permissionConverter.convertModelToDto(genericUser.getPermission()));
        userDTO.setCompany(genericUser.getCompany() == null ? null : contractorConverter.convertModelToDto(genericUser.getCompany()));
        return userDTO;
    }
}
