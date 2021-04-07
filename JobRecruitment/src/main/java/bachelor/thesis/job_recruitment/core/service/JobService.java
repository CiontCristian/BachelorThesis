package bachelor.thesis.job_recruitment.core.service;

import bachelor.thesis.job_recruitment.core.model.Job;
import bachelor.thesis.job_recruitment.core.model.Preference;

import java.util.List;
import java.util.Optional;

public interface JobService {
    List<Job> findAll(Integer pageIndex, Integer pageSize, String value);
    Optional<Job> findJobById(Long id);
    Job save(Job job);
    List<Job> saveJobs(List<Job> jobs);
    Job modify(Job modifiedJob);
    void remove(Long id);
    void savePreference(Preference preference);
    List<Job> findJobsForContractor(Long id);
    List<String> findJobsTitles();
    Optional<Job> findJobByTitle(String title);
    Optional<Preference> findJobPreferenceForUser(Long userId, Long jobId);
}
