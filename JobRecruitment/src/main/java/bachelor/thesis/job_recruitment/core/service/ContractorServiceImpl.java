package bachelor.thesis.job_recruitment.core.service;

import bachelor.thesis.job_recruitment.core.model.Contractor;
import bachelor.thesis.job_recruitment.core.repository.ContractorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            System.out.println("AAAAAAAAAAAA");
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
}
