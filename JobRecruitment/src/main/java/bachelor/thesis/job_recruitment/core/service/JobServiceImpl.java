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
    public List<Job> findAll(Integer pageIndex, Integer pageSize, Filter criteria) {

        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
        log.trace("In JobServiceImpl - method: findAll() - pageIndex={}, pageSize={}", pageIndex, pageSize);
        log.trace("In JobServiceImpl - method: findAll() - filter criteria={}", criteria);

        if(criteria.getTitle() == null || (criteria.getTitle().equals("") && criteria.getTechs().equals("")
        && criteria.getJobType().equals("") && criteria.getDevType().equals("") && criteria.getMinExperience().equals("")
        && criteria.getRemote().equals(false) && criteria.getMinCompensation().equals(0) && criteria.getAvailablePos().equals(0))){
            return jobRepository.findAll(pageRequest).getContent();
        }
        return jobRepository.findAll().stream()
                .filter(job -> criteria.getTitle().equals("") || job.getTitle().toLowerCase().contains(criteria.getTitle().toLowerCase()))
                .filter(job -> criteria.getTechs().equals("") || job.getTechs().toLowerCase().contains(criteria.getTechs().toLowerCase()))
                .filter(job -> criteria.getDevType().equals("") || job.getDevType().toLowerCase().contains(criteria.getDevType().toLowerCase()))
                .filter(job -> criteria.getJobType().equals("") || job.getJobType().toLowerCase().contains(criteria.getJobType().toLowerCase()))
                .filter(job -> job.getRemote().equals(criteria.getRemote()))
                .filter(job -> criteria.getMinExperience().equals("") || job.getMinExperience().toLowerCase().contains(criteria.getMinExperience().toLowerCase()))
                .filter(job -> job.getAvailablePos() >= criteria.getAvailablePos())
                .filter(job -> job.getMinCompensation() >= criteria.getMinCompensation())
                .collect(Collectors.toList());

        //logger.trace("In JobServiceImpl - method: findAll() - jobs={}", jobs);

        //return jobs.getContent();
    }

    @Override
    public Optional<Job> findJobById(Long id) {
        return jobRepository.findById(id);
    }

    @Override
    public List<Job> findJobsByIds(Integer count, List<Long> ids) {
        log.trace("In JobServiceImpl - method: findJobsByIds() - ids={}", ids);
        List<Job> jobs = new ArrayList<>();
        for(int i=0; i<count;i++){
            jobs.add(jobRepository.findById(ids.get(i)).orElse(null));
        }

        return jobs;

    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
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
    public Integer countLikedJobPreferences(Long id) {
        return (int)preferenceRepository.findAll().stream()
                .filter(preference -> preference.getJob().getId().equals(id))
                .filter(preference -> preference.getInterested().equals(true))
                .count();
    }

    @Override
    public Integer countAppliedJobPreferences(Long id) {
        return (int)preferenceRepository.findAll().stream()
                .filter(preference -> preference.getJob().getId().equals(id))
                .filter(preference -> preference.getApplied().equals(true))
                .count();
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

    @Override
    public Integer getJobRecordCount() {
        return jobRepository.findAll().size();
    }

    @Override
    public Integer getContractorJobCount(Long id) {
        return (int) jobRepository.findAll().stream()
                .filter(job -> job.getContractor().getId().equals(id)).count();
    }

}
