package bachelor.thesis.job_recruitment.web.converter;


import bachelor.thesis.job_recruitment.core.model.BaseEntity;
import bachelor.thesis.job_recruitment.web.dto.BaseDTO;

public interface Converter<Model extends BaseEntity<Long>, Dto extends BaseDTO<Long>> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);

}

