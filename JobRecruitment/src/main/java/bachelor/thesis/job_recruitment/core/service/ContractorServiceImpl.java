package bachelor.thesis.job_recruitment.core.service;

import bachelor.thesis.job_recruitment.core.model.Contractor;
import bachelor.thesis.job_recruitment.core.repository.ContractorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ContractorServiceImpl implements ContractorService{
    public static final Logger logger = LoggerFactory.getLogger(ContractorService.class);

    @Autowired
    private ContractorRepository contractorRepository;

    @Override
    public Optional<Contractor> save(Contractor contractor) {
        logger.trace("In ContractorServiceImpl - method: saveContractor() - contractor={}", contractor);
        Optional<Contractor> alreadyUsed = findAll().stream()
                .filter(contractor1 -> contractor1.getName().equals(contractor.getName()))
                .findFirst();
        if(alreadyUsed.isPresent()) {
            return Optional.empty();
        }
        contractorRepository.save(contractor);

        return Optional.of(contractor);
    }

    @Override
    public List<Contractor> findAll() {
        logger.trace("In ContractorServiceImpl - method: findAll()");
        return contractorRepository.findAll();
    }

    @Override
    public Optional<Contractor> findContractorForUser(Long id) {
        return findAll().stream()
                .filter(contractor -> contractor.getOwner().getId().equals(id))
                .findFirst();
    }

    @Override
    @Transactional
    public Optional<Contractor> modifyContractor(Contractor modifiedContractor) {
        logger.trace("In ContractorServiceImpl - method: modifyContractor() - contractor={}", modifiedContractor);

        System.out.println("AAAA" + modifiedContractor.getId());
        Optional<Contractor> contractorOptional = findAll().stream()
                .filter(contractor1 -> contractor1.getId().equals(modifiedContractor.getId()))
                .findFirst();

        if(contractorOptional.isEmpty())
            return Optional.empty();

        Contractor contractor = contractorOptional.get();

        contractor.setName(modifiedContractor.getName());
        contractor.setDescription(modifiedContractor.getDescription());
        contractor.getLogo().setName(modifiedContractor.getLogo().getName());
        contractor.getLogo().setType(modifiedContractor.getLogo().getType());
        contractor.getLogo().setData(modifiedContractor.getLogo().getData());
        contractor.getLocation().setAddress(modifiedContractor.getLocation().getAddress());
        contractor.getLocation().setCity(modifiedContractor.getLocation().getCity());
        contractor.getLocation().setCountry(modifiedContractor.getLocation().getCountry());
        contractor.setNrOfEmployees(modifiedContractor.getNrOfEmployees());

        return Optional.of(contractor);
    }
}
