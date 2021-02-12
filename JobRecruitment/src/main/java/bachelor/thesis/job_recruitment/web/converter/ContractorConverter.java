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
    @Autowired
    private UserConverter userConverter;

    @Override
    public Contractor convertDtoToModel(ContractorDTO dto) {
        /*Contractor contractor = modelMapper.map(dto, Contractor.class);
        contractor.setId(dto.getId());
        contractor.setLocation(locationConverter.convertDtoToModel(dto.getLocation()));
        contractor.setOffers(dto.getOffers() == null ? null : jobConverter.convertDtosToModels(dto.getOffers()));
        return contractor;*/

        Contractor contractor = Contractor.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .nrOfEmployees(dto.getNrOfEmployees())
                .logo(dto.getLogo())
                .location(locationConverter.convertDtoToModel(dto.getLocation()))
                .offers(dto.getOffers() == null ? null : jobConverter.convertDtosToModels(dto.getOffers()))
                .build();
        contractor.setId(dto.getId());
        return contractor;

    }

    @Override
    public ContractorDTO convertModelToDto(Contractor contractor) {
        /*ContractorDTO contractorDTO = modelMapper.map(contractor, ContractorDTO.class);
        contractorDTO.setId(contractor.getId());
        contractorDTO.setLocation(locationConverter.convertModelToDto(contractor.getLocation()));
        contractorDTO.setOffers(contractor.getOffers() == null ? null : jobConverter.convertModelsToDtos(contractor.getOffers()));
        return contractorDTO;*/

        ContractorDTO contractorDTO = ContractorDTO.builder()
                .name(contractor.getName())
                .description(contractor.getDescription())
                .nrOfEmployees(contractor.getNrOfEmployees())
                .logo(contractor.getLogo())
                .location(locationConverter.convertModelToDto(contractor.getLocation()))
                .offers(contractor.getOffers() == null ? null : jobConverter.convertModelsToDtos(contractor.getOffers()))
                .build();
        contractorDTO.setId(contractor.getId());
        return contractorDTO;
    }
}
