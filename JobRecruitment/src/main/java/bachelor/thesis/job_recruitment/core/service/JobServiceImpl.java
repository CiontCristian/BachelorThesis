package bachelor.thesis.job_recruitment.core.service;

import bachelor.thesis.job_recruitment.core.model.*;
import bachelor.thesis.job_recruitment.core.repository.ContractorRepository;
import bachelor.thesis.job_recruitment.core.repository.JobRepository;
import bachelor.thesis.job_recruitment.core.repository.PreferenceRepository;
import bachelor.thesis.job_recruitment.core.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
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
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

@Service
@Slf4j
public class JobServiceImpl implements JobService{
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PreferenceRepository preferenceRepository;
    @Autowired
    private ContractorRepository contractorRepository;

    @Override
    public List<Job> findAll(Integer pageIndex, Integer pageSize, String value) {

        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
        log.trace("In JobServiceImpl - method: findAll() - pageIndex={}, pageSize={}", pageIndex, pageSize);

        if(value.equals("")) {
            Page<Job> jobs = jobRepository.findAll(pageRequest);
            return jobs.getContent();
        }
        else{
            return jobRepository.findAll(pageRequest).stream()
                    .filter(job -> job.getTitle().toLowerCase().contains(value.toLowerCase()))
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
    public List<Job> findJobsByIds(List<Long> ids) {
        return jobRepository.findAllById(ids);
    }

    @Override
    public Job save(Job job) {
        log.trace("In JobServiceImpl - method: save() - job={}", job);

        return jobRepository.save(job);
    }

    @Override
    public List<Job> saveJobs(List<Job> jobs) {
        jobRepository.deleteAll();
        jobs.forEach(job -> job.setContractor(contractorRepository.findAll().stream().findFirst().get()));

        return jobRepository.saveAll(jobs);
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
    public Set<String> getAvailableTechs() {
        List<Job> jobs = jobRepository.findAll();
        if(jobs.isEmpty())
            return new HashSet<>();

        StringBuilder allTechs = new StringBuilder();
        jobs.forEach(
                job -> allTechs.append(job.getTechs())
        );
        allTechs.deleteCharAt(allTechs.length()-1);

        return new HashSet<>(Arrays.asList(allTechs.toString().split(",")));

    }

    @Override
    public Set<String> getAvailableDevTypes() {
        List<Job> jobs = jobRepository.findAll();
        if(jobs.isEmpty())
            return new HashSet<>();

        StringBuilder allDevTypes = new StringBuilder();
        jobs.forEach(
                job -> allDevTypes.append(job.getDevType()).append(",")
        );
        allDevTypes.deleteCharAt(allDevTypes.length()-1);

        return new HashSet<>(Arrays.asList(allDevTypes.toString().split(",")));
    }

}
