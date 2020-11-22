package bachelor.thesis.job_recruitment.web.converter;


import bachelor.thesis.job_recruitment.core.model.Permission;
import bachelor.thesis.job_recruitment.web.dto.PermissionDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissionConverter extends BaseConverter<Permission, PermissionDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Permission convertDtoToModel(PermissionDTO dto) {
        Permission permission = modelMapper.map(dto, Permission.class);
        permission.setId(dto.getId());
        return permission;
    }

    @Override
    public PermissionDTO convertModelToDto(Permission permission) {
        PermissionDTO permissionDTO = modelMapper.map(permission, PermissionDTO.class);
        permissionDTO.setId(permission.getId());
        return permissionDTO;
    }
}
