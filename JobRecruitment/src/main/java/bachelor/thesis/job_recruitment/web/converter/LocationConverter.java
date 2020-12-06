package bachelor.thesis.job_recruitment.web.converter;

import bachelor.thesis.job_recruitment.core.model.Location;
import bachelor.thesis.job_recruitment.web.dto.LocationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationConverter extends BaseConverter<Location, LocationDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Location convertDtoToModel(LocationDTO dto) {
        Location location = modelMapper.map(dto, Location.class);
        location.setId(dto.getId());
        return location;
    }

    @Override
    public LocationDTO convertModelToDto(Location location) {
        LocationDTO locationDTO = modelMapper.map(location, LocationDTO.class);
        locationDTO.setId(location.getId());
        return locationDTO;
    }
}
