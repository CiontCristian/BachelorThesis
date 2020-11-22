package bachelor.thesis.job_recruitment.web.converter;



import bachelor.thesis.job_recruitment.core.model.BaseEntity;
import bachelor.thesis.job_recruitment.web.dto.BaseDTO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public abstract class BaseConverter<Model extends BaseEntity<Long>, Dto extends BaseDTO<Long>> implements
                                                                                         Converter<Model, Dto> {

    public List<Dto> convertModelsToDtos(Collection<Model> models) {
        return models.stream()
                .map(this::convertModelToDto)
                .collect(Collectors.toList());
    }

    public List<Model> convertDtosToModels(Collection<Dto> dtos) {
        return dtos.stream()
                .map(this::convertDtoToModel)
                .collect(Collectors.toList());
    }
}
