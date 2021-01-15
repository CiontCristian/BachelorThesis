package bachelor.thesis.job_recruitment.web.converter;

import bachelor.thesis.job_recruitment.core.model.Contractor;
import bachelor.thesis.job_recruitment.web.dto.ContractorDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ContractorConverter extends BaseConverter<Contractor, ContractorDTO> {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LocationConverter locationConverter;
    @Autowired
    private JobConverter jobConverter;

    @Override
    public Contractor convertDtoToModel(ContractorDTO dto) {
        /*Contractor contractor = Contractor.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .logo(dto.getLogo())
                .location(dto.getLocation() == null ? null : locationConverter.convertDtoToModel(dto.getLocation()))
                .offers(dto.getOffers() == null ? null : jobConverter.convertDtosToModels(dto.getOffers()))
                .build();*/
        Contractor contractor = modelMapper.map(dto, Contractor.class);
        contractor.setId(dto.getId());
        //System.out.println(contractor);
        return contractor;
    }

    @Override
    public ContractorDTO convertModelToDto(Contractor contractor) {
        ContractorDTO contractorDTO = modelMapper.map(contractor, ContractorDTO.class);
        contractorDTO.setId(contractor.getId());
        return contractorDTO;
    }
}
