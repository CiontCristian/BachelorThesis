package bachelor.thesis.job_recruitment.core.service;

import bachelor.thesis.job_recruitment.core.model.Contractor;

import java.util.List;
import java.util.Optional;

public interface ContractorService {
    Optional<Contractor> save(Contractor contractor);
    List<Contractor> findAll();
}
