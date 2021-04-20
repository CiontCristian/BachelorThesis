package bachelor.thesis.job_recruitment.core.service;

import bachelor.thesis.job_recruitment.core.model.Contractor;
import bachelor.thesis.job_recruitment.core.model.Job;
import bachelor.thesis.job_recruitment.core.repository.ContractorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ContractorServiceImpl implements ContractorService{

    @Autowired
    private ContractorRepository contractorRepository;
    @Autowired
    private JobService jobService;

    @Override
    public Optional<Contractor> save(Contractor contractor) {
        log.trace("In ContractorServiceImpl - method: saveContractor() - contractor={}", contractor);
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
        log.trace("In ContractorServiceImpl - method: findAll()");
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
    public Contractor modifyContractor(Contractor modifiedContractor) {
        log.trace("In ContractorServiceImpl - method: modifyContractor() - contractor={}", modifiedContractor);

        return contractorRepository.save(modifiedContractor);
    }

    @Override
    public void removeContractor(Long id) {
        jobService.findJobsForContractor(id)
                .forEach(job -> jobService.remove(job.getId()));
        contractorRepository.deleteById(id);
    }
}
