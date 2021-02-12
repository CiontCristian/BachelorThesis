package bachelor.thesis.job_recruitment.web.controller;

import bachelor.thesis.job_recruitment.core.model.Contractor;
import bachelor.thesis.job_recruitment.core.model.File;
import bachelor.thesis.job_recruitment.core.repository.ContractorRepository;
import bachelor.thesis.job_recruitment.core.service.ContractorService;
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
import java.util.Optional;

@RestController
@RequestMapping(value = "/contractor")
public class ContractorController {
    public static final Logger logger = LoggerFactory.getLogger(ContractorController.class);
    @Autowired
    private ContractorService contractorService;

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
                contractorDTO.getDescription(), contractorDTO.getNrOfEmployees(), null, contractorDTO.getLocation(), null,
                contractorDTO.getOwner());
        contractor.setLogo(fileObject);
        Optional<Contractor> savedContractor = contractorService.save(contractorConverter.convertDtoToModel(contractor));
        if(savedContractor.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        logger.trace("In ContractorController - method: saveContractor - savedContractor={}", savedContractor);
        return new ResponseEntity<>(contractorConverter.convertModelToDto(savedContractor.get()), HttpStatus.OK);
    }

    @GetMapping(value = "/findContractorForUser/{id}")
    ResponseEntity<ContractorDTO> findContractorForUser(@PathVariable Long id){
        Optional<Contractor> contractor = contractorService.findContractorForUser(id);
        if(contractor.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(contractorConverter.convertModelToDto(contractor.get()), HttpStatus.OK);

    }

    @PutMapping(value = "/modifyContractor", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<ContractorDTO> modifyContractor(@RequestPart("file") MultipartFile file, @RequestPart("contractorDTO") ContractorDTO contractorDTO,
                                                   @RequestPart("logoID") String id) throws IOException {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        File fileObject = new File(fileName, file.getContentType(), file.getBytes());
        fileObject.setId(Long.parseLong(id));

        //contractorDTO.setLogo(fileObject);
        //Contractor savedContractor = contractorRepository.save(contractorConverter.convertDtoToModel(contractorDTO));
        ContractorDTO contractor = new ContractorDTO(contractorDTO.getName(),
                contractorDTO.getDescription(), contractorDTO.getNrOfEmployees(), null, contractorDTO.getLocation(), null,
                contractorDTO.getOwner());
        contractor.setLogo(fileObject);
        contractor.setId(contractorDTO.getId());
        Optional<Contractor> modifiedContractor = contractorService.modifyContractor(contractorConverter.convertDtoToModel(contractor));
        if(modifiedContractor.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        logger.trace("In ContractorController - method: modifyContractor - modifiedContractor={}", modifiedContractor);
        return new ResponseEntity<>(contractorConverter.convertModelToDto(modifiedContractor.get()), HttpStatus.OK);
    }
}
