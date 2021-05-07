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
import java.util.stream.Collectors;

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
            log.trace("In ContractorServiceImpl - method: saveContractor() - duplicate name={}", contractor.getName());
            return Optional.empty();
        }
        log.trace("In ContractorServiceImpl - method: saveContractor() - success");
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
        log.trace("In ContractorServiceImpl - method: findContractorForUser() - id={}", id);
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
        log.trace("In ContractorServiceImpl - method: removeContractor() - id={}", id);

        jobService.findJobsForContractor(id)
                .forEach(job -> jobService.remove(job.getId()));
        contractorRepository.deleteById(id);
    }

    @Override
    public List<Long> findContractorIds() {
        log.trace("In ContractorServiceImpl - method: findContractorIds()");

        return contractorRepository.findAll().stream()
                .map(Contractor::getId)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Contractor> findById(Long id) {
        log.trace("In ContractorServiceImpl - method: findById() - id={}", id);

        return contractorRepository.findById(id);
    }
}
