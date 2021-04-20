package bachelor.thesis.job_recruitment.core.service;

import bachelor.thesis.job_recruitment.core.model.Contractor;
import bachelor.thesis.job_recruitment.core.model.Filter;
import bachelor.thesis.job_recruitment.core.model.Job;
import bachelor.thesis.job_recruitment.core.model.Preference;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface JobService {
    List<Job> findAll(Integer pageIndex, Integer pageSize, Filter criteria);
    Optional<Job> findJobById(Long id);
    List<Job> findJobsByIds(Integer count, List<Long> ids);
    List<Job> findAll();
    Job save(Job job);
    List<Job> saveJobs(List<Job> jobs);
    Job modify(Job modifiedJob);
    void remove(Long id);
    void savePreference(Preference preference);
    List<Job> findJobsForContractor(Long id);
    List<String> findJobsTitles();
    Optional<Job> findJobByTitle(String title);
    Optional<Preference> findJobPreferenceForUser(Long userId, Long jobId);
    Integer countLikedJobPreferences(Long id);
    Integer countAppliedJobPreferences(Long id);
    Set<String> getAvailableTechs();
    Set<String> getAvailableDevTypes();
    Integer getJobRecordCount();
    Integer getContractorJobCount(Long id);
}
