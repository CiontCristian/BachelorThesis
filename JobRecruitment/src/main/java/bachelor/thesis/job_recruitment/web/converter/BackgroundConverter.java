package bachelor.thesis.job_recruitment.web.converter;

import bachelor.thesis.job_recruitment.core.model.Background;
import bachelor.thesis.job_recruitment.web.dto.BackgroundDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BackgroundConverter extends BaseConverter<Background, BackgroundDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Background convertDtoToModel(BackgroundDTO dto) {
        Background background = modelMapper.map(dto, Background.class);
        background.setId(dto.getId());
        return background;
    }

    @Override
    public BackgroundDTO convertModelToDto(Background background) {
        BackgroundDTO backgroundDTO = modelMapper.map(background, BackgroundDTO.class);
        backgroundDTO.setId(background.getId());
        return backgroundDTO;
    }
}
