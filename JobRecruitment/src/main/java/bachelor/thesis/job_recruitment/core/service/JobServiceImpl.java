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
import org.springframework.data.domain.Sort;
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
    private ContractorService contractorService;

    @Override
    public List<Job> findAll(Integer pageIndex, Integer pageSize, String sortType, Filter criteria) {
        PageRequest pageRequest;
        if(sortType.equals("minCompensation")){
            pageRequest = PageRequest.of(pageIndex, pageSize, Sort.by(sortType).descending());
        }
        else{
            pageRequest = PageRequest.of(pageIndex, pageSize, Sort.by(sortType).ascending());
        }
        log.trace("In JobServiceImpl - method: findAll() - pageIndex={}, pageSize={}", pageIndex, pageSize);
        log.trace("In JobServiceImpl - method: findAll() - filter criteria={}", criteria);
        log.trace("In JobServiceImpl - method: findAll() - sort type={}", sortType);


        if(criteria.getTitle() == null || (criteria.getTitle().equals("") && criteria.getTechs().equals("")
        && criteria.getJobType().equals("") && criteria.getDevType().equals("") && criteria.getMinExperience().equals("")
        && criteria.getRemote().equals(false) && criteria.getMinCompensation().equals(0) && criteria.getAvailablePos().equals(0))){
            return jobRepository.findAll(pageRequest).getContent();
        }
        return jobRepository.findAll(Sort.by(sortType).ascending()).stream()
                .filter(job -> criteria.getTitle().equals("") || job.getTitle().toLowerCase().contains(criteria.getTitle().toLowerCase()))
                .filter(job -> criteria.getTechs().equals("") || job.getTechs().toLowerCase().contains(criteria.getTechs().toLowerCase()))
                .filter(job -> criteria.getDevType().equals("") || job.getDevType().toLowerCase().contains(criteria.getDevType().toLowerCase()))
                .filter(job -> criteria.getJobType().equals("") || job.getJobType().toLowerCase().contains(criteria.getJobType().toLowerCase()))
                .filter(job -> job.getRemote().equals(criteria.getRemote()))
                .filter(job -> criteria.getMinExperience().equals("") || job.getMinExperience().toLowerCase().contains(criteria.getMinExperience().toLowerCase()))
                .filter(job -> job.getAvailablePos() >= criteria.getAvailablePos())
                .filter(job -> job.getMinCompensation() >= criteria.getMinCompensation())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Job> findJobById(Long id) {
        log.trace("In JobServiceImpl - method: findJobById() - id={}", id);
        return jobRepository.findById(id);
    }

    @Override
    public List<Job> findJobsByIds(Integer count, List<Long> ids) {
        log.trace("In JobServiceImpl - method: findJobsByIds() - nr. of received jobs={}", ids.size());
        log.trace("In JobServiceImpl - method: findJobsByIds() - nr. of required jobs={}", count);

        List<Job> jobs = new ArrayList<>();
        for(int i=0; i<count;i++){
            jobs.add(jobRepository.findById(ids.get(i)).orElse(null));
        }

        return jobs;

    }

    @Override
    public List<Job> findAll() {
        log.trace("In JobServiceImpl - method: findAll() - no parameters");
        return jobRepository.findAll();
    }

    @Override
    public Job save(Job job) {
        log.trace("In JobServiceImpl - method: save() - job={}", job);
        return jobRepository.save(job);
    }

    @Override
    public List<Job> saveJobs(List<Job> jobs) {
        log.trace("In JobServiceImpl - method: saveJobs() - nr. of jobs={}", jobs.size());

        List<Long> contractorIds = contractorService.findContractorIds();
        Random random = new Random();
        jobRepository.deleteAll();
        jobs.forEach(job -> job.setContractor(contractorService.
                findById(contractorIds.get(random.nextInt(contractorIds.size()))).orElse(new Contractor())));

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
        log.trace("In JobServiceImpl - method: savePreference() - preference={}", preference);

        preference.setKey(new PreferenceKey(preference.getUser().getId(), preference.getJob().getId()));
        preferenceRepository.save(preference);
    }

    @Override
    public List<Job> findJobsForContractor(Long id) {
        log.trace("In JobServiceImpl - method: findJobsForContractor() - id={}", id);

        return jobRepository.findAll().stream()
                .filter(job -> job.getContractor().getId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findJobsTitles() {
        log.trace("In JobServiceImpl - method: findJobsTitles()");

        return jobRepository.findAll().stream()
                .map(Job::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Job> findJobByTitle(String title) {
        log.trace("In JobServiceImpl - method: findJobByTitle() - title={}", title);

        return jobRepository.findAll().stream()
                .filter(job -> job.getTitle().equals(title))
                .findAny();
    }

    @Override
    @Transactional
    public Optional<Preference> findJobPreferenceForUser(Long userId, Long jobId) {
        log.trace("In JobServiceImpl - method: findJobPreferenceForUser() - userId={},jobId={}", userId, jobId);

        return preferenceRepository.findByKey(new PreferenceKey(userId, jobId));
    }

    @Override
    public Integer countLikedJobPreferences(Long id) {
        log.trace("In JobServiceImpl - method: countLikedJobPreferences() - id={}", id);

        return (int)preferenceRepository.findAll().stream()
                .filter(preference -> preference.getJob().getId().equals(id))
                .filter(preference -> preference.getInterested() != null && preference.getInterested().equals(true))
                .count();
    }

    @Override
    public Integer countAppliedJobPreferences(Long id) {
        log.trace("In JobServiceImpl - method: countAppliedJobPreferences() - id={}", id);

        return (int)preferenceRepository.findAll().stream()
                .filter(preference -> preference.getJob().getId().equals(id))
                .filter(preference -> preference.getApplied() != null && preference.getApplied().equals(true))
                .count();
    }

    @Override
    public Set<String> getAvailableTechs() {
        log.trace("In JobServiceImpl - method: getAvailableTechs()");

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
        log.trace("In JobServiceImpl - method: getAvailableDevTypes()");

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
        log.trace("In JobServiceImpl - method: getJobRecordCount()");
        return jobRepository.findAll().size();
    }

    @Override
    public Integer getContractorJobCount(Long id) {
        log.trace("In JobServiceImpl - method: getContractorJobCount() - id={}", id);

        return (int) jobRepository.findAll().stream()
                .filter(job -> job.getContractor().getId().equals(id)).count();
    }

}
