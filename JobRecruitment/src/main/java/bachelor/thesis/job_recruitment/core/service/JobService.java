package bachelor.thesis.job_recruitment.core.service;

import bachelor.thesis.job_recruitment.core.model.Job;
import bachelor.thesis.job_recruitment.core.model.Preference;

import java.util.List;
import java.util.Optional;

public interface JobService {
    List<Job> findAll(Integer pageIndex, Integer pageSize);
    Optional<Job> findJobById(Long id);
    Job save(Job job);
    Job modify(Job modifiedJob);
    void remove(Long id);
    void savePreference(Preference preference);
    List<Job> findJobsForContractor(Long id);
}
