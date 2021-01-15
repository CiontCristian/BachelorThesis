package bachelor.thesis.job_recruitment.core.service;

import bachelor.thesis.job_recruitment.core.model.Contractor;
import bachelor.thesis.job_recruitment.core.repository.ContractorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractorServiceImpl implements ContractorService{
    public static final Logger logger = LoggerFactory.getLogger(ContractorService.class);

    @Autowired
    private ContractorRepository contractorRepository;

    @Override
    public Contractor saveContractor(Contractor contractor) {
        return contractorRepository.save(contractor);
    }
}
