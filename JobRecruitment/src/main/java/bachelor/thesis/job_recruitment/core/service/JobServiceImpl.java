package bachelor.thesis.job_recruitment.core.service;

import bachelor.thesis.job_recruitment.core.model.Job;
import bachelor.thesis.job_recruitment.core.model.Preference;
import bachelor.thesis.job_recruitment.core.model.PreferenceKey;
import bachelor.thesis.job_recruitment.core.repository.JobRepository;
import bachelor.thesis.job_recruitment.core.repository.PreferenceRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JobServiceImpl implements JobService{
    //public static final Logger logger = LoggerFactory.getLogger(JobService.class);

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PreferenceRepository preferenceRepository;

    @Override
    public List<Job> findAll(Integer pageIndex, Integer pageSize, String value) {
        //saveJobsToFile();

        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
        log.trace("In JobServiceImpl - method: findAll() - pageIndex={}, pageSize={}", pageIndex, pageSize);

        if(value.equals("")) {
            Page<Job> jobs = jobRepository.findAll(pageRequest);
            return jobs.getContent();
        }
        else{
            return jobRepository.findAll(pageRequest).stream()
                    .filter(job -> job.getTitle().contains(value)
                    || job.getTechs().contains(value))
                    .collect(Collectors.toList());
        }
        //logger.trace("In JobServiceImpl - method: findAll() - jobs={}", jobs);

        //return jobs.getContent();
    }

    @Override
    public Optional<Job> findJobById(Long id) {
        return jobRepository.findById(id);
    }

    @Override
    public Job save(Job job) {
        log.trace("In JobServiceImpl - method: save() - job={}", job);

        return jobRepository.save(job);
    }

    @Override
    public Job modify(Job modifiedJob) {
        log.trace("In JobServiceImpl - method: modify() - job={}", modifiedJob);

        return jobRepository.saveAndFlush(modifiedJob);
    }

    @Override
    public void remove(Long id) {
        //TODO handle preference delete
        log.trace("In JobServiceImpl - method: remove() - id={}", id);
        List<Preference> preferences = preferenceRepository.findAll().stream()
                .filter(preference -> preference.getJob().getId().equals(id))
                .collect(Collectors.toList());
        preferenceRepository.deleteInBatch(preferences);

        jobRepository.deleteById(id);
    }

    @Override
    public void savePreference(Preference preference) {
        preferenceRepository.save(preference);
    }

    @Override
    public List<Job> findJobsForContractor(Long id) {
        return jobRepository.findAll().stream()
                .filter(job -> job.getContractor().getId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findJobsTitles() {
        return jobRepository.findAll().stream()
                .map(Job::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Job> findJobByTitle(String title) {
        return jobRepository.findAll().stream()
                .filter(job -> job.getTitle().equals(title))
                .findAny();
    }

    @Override
    @Transactional
    public Optional<Preference> findJobPreferenceForUser(Long userId, Long jobId) {
        return preferenceRepository.findByKey(new PreferenceKey(userId, jobId));
    }

    @Override
    public void saveJobsToFile() {
        List<Job> jobs = jobRepository.findAll();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("E:\\FACULTATE\\AN 3\\SEMESTRU 5\\Licenta\\BachelorThesis\\jobs.txt"));
            writer.write("id"+"|"+"title"+"|"+"dev_type"+"|"+"job_type"+"|"+"min_experience"+"|"+"remote"+
                    "|"+"techs");
            writer.write("\n");
            for(Job job : jobs){
                writer.write(job.getId() + "|" +job.getTitle() + "|" + job.getDevType() + "|" +job.getJobType() +
                        "|"+ job.getMinExperience()+ "|" + job.getRemote() + "|"+job.getTechs());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException runtimeException){
            System.out.println(runtimeException.getMessage());
        }
    }
}
