package bachelor.thesis.job_recruitment.web.controller;

import bachelor.thesis.job_recruitment.core.model.Contractor;
import bachelor.thesis.job_recruitment.core.model.File;
import bachelor.thesis.job_recruitment.core.repository.ContractorRepository;
import bachelor.thesis.job_recruitment.web.converter.ContractorConverter;
import bachelor.thesis.job_recruitment.web.dto.ContractorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@RestController
@RequestMapping(value = "/contractor")
public class ContractorController {
    public static final Logger logger = LoggerFactory.getLogger(ContractorController.class);
    @Autowired
    private ContractorRepository contractorRepository;

    @Autowired
    private ContractorConverter contractorConverter;

    @PostMapping(value = "/saveContractor", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<ContractorDTO> saveContractor(@RequestPart("file") MultipartFile file, @RequestPart("contractorDTO") ContractorDTO contractorDTO) throws IOException {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        File fileObject = new File(fileName, file.getContentType(), file.getBytes());
        fileObject.setId(null);

        //contractorDTO.setLogo(fileObject);
        //Contractor savedContractor = contractorRepository.save(contractorConverter.convertDtoToModel(contractorDTO));
        ContractorDTO contractor = new ContractorDTO(contractorDTO.getName(),
                contractorDTO.getDescription(), null, null, null);
        contractor.setLogo(fileObject);
        Contractor savedContractor = contractorRepository.save(contractorConverter.convertDtoToModel(contractor));

        return new ResponseEntity<>(contractorConverter.convertModelToDto(savedContractor), HttpStatus.OK);
    }
}
