package bachelor.thesis.job_recruitment.server.controller;

import bachelor.thesis.job_recruitment.core.model.Contractor;
import bachelor.thesis.job_recruitment.core.model.File;
import bachelor.thesis.job_recruitment.core.service.ContractorService;

import bachelor.thesis.job_recruitment.server.exception.BadRequestException;
import bachelor.thesis.job_recruitment.server.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/contractor")
@Slf4j
public class ContractorController {
    @Autowired
    private ContractorService contractorService;

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleNotFound(ResourceNotFoundException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleBadRequest(BadRequestException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @PostMapping(value = "/saveContractor", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<Contractor> saveContractor(@RequestPart("file") MultipartFile file,
                                              @RequestPart("contractor") Contractor contractor) throws IOException {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        File fileObject = new File(fileName, file.getContentType(), file.getBytes());
        fileObject.setId(null);

        contractor.setLogo(fileObject);
        Optional<Contractor> savedContractor = contractorService.save(contractor);
        if(savedContractor.isEmpty())
            throw new BadRequestException("Contractor name already taken!");

        log.trace("In ContractorController - method: saveContractor - savedContractor={}", savedContractor);
        return new ResponseEntity<>(savedContractor.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/findAllContractors")
    ResponseEntity<List<Contractor>> findAllContractors(){
        List<Contractor> contractors = contractorService.findAll();
        return new ResponseEntity<>(contractors, HttpStatus.OK);
    }

    @GetMapping(value = "/findContractorForUser/{id}")
    ResponseEntity<Contractor> findContractorForUser(@PathVariable Long id){
        Optional<Contractor> contractor = contractorService.findContractorForUser(id);
        if(contractor.isEmpty())
            throw new ResourceNotFoundException("Contractor with the id: " + id + " was not found");

        log.trace("In ContractorController - method: findContractorForUser - contractor retrieved!");
        return new ResponseEntity<>(contractor.get(), HttpStatus.OK);

    }

    @PutMapping(value = "/modifyContractor", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<Contractor> modifyContractor(@RequestPart("file") MultipartFile file, @RequestPart("contractorDTO") Contractor contractor,
                                                   @RequestPart("logoID") String id) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if(!fileName.equals("no_change")){
            File fileObject = new File(fileName, file.getContentType(), file.getBytes());
            fileObject.setId(Long.parseLong(id));

            contractor.setLogo(fileObject);
        }

        Contractor modifiedContractor = contractorService.modifyContractor(contractor);

        log.trace("In ContractorController - method: modifyContractor - modifiedContractor={}", modifiedContractor);
        return new ResponseEntity<>(modifiedContractor, HttpStatus.OK);
    }

    @DeleteMapping(value = "/removeContractor/{id}")
    ResponseEntity<?> removeContractor(@PathVariable Long id){
        contractorService.removeContractor(id);
        log.trace("In ContractorController - method: removeContractor - id={}",id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
