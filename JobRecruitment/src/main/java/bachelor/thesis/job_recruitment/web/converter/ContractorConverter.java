package bachelor.thesis.job_recruitment.web.converter;

import bachelor.thesis.job_recruitment.core.model.Contractor;
import bachelor.thesis.job_recruitment.web.dto.ContractorDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContractorConverter extends BaseConverter<Contractor, ContractorDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Contractor convertDtoToModel(ContractorDTO dto) {
        Contractor contractor = modelMapper.map(dto, Contractor.class);
        contractor.setId(dto.getId());
        return contractor;
    }

    @Override
    public ContractorDTO convertModelToDto(Contractor contractor) {
        ContractorDTO contractorDTO = modelMapper.map(contractor, ContractorDTO.class);
        contractorDTO.setId(contractor.getId());
        return contractorDTO;
    }
}
