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
    @Autowired
    private UserRepository userRepository;

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
            writer.write(Job.headersString());
            writer.write("\n");
            for(Job job : jobs){
                writer.write(job.toFileString());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException runtimeException){
            log.debug(runtimeException.getMessage());
        }
    }

    @Override
    public void generateJobs() {
        List<String> titles = Arrays.asList("Junior .NET Web Developer", "JAVA SE 8+ Developer",
                "Fullstack Spring/Angular Dev", "Senior System Administrator", "C++ Developer",
                "Research Analyst", "Senior ML Expert", "Application Technical Support", "Software QA Engineer",
                "Game Server Developer", "Senior Technical Analyst", "Linux systems expert");
        String description = "A small job description that requires some expertise";
        List<String> jobType = Arrays.asList("full-time", "part-time", "internship");
        List<Boolean> remote = Arrays.asList(true, false);
        List<String> experience = Arrays.asList("entry", "junior", "middle", "senior", "lead", "manager");
        List<String> devType = Arrays.asList("Full Stack Developer", "Backend Developer", "Frontend Developer", "QA/Tester", "Designer",
                "Mobile Developer");
        List<String> techs = Arrays.asList("java", "c#", "python","react","angular","nodeJS","sql","html",
                "kotlin","c/c++");
        List<Contractor> contractors = contractorRepository.findAll();

        Random random = new Random();
        List<Job> jobs = new ArrayList<>();
        for(int i = 0; i < 30; i++){
            Job job = new Job(titles.get(random.nextInt(titles.size())), description, jobType.get(random.nextInt(jobType.size())),
                    remote.get(random.nextInt(2)), selectRandomItems(experience), random.nextInt((1500 - 500) + 500),
                    selectRandomItems(devType), selectRandomItems(techs), random.nextInt((6 - 1) + 1),
                    contractors.get(random.nextInt(contractors.size())));
            jobs.add(job);
        }

        jobRepository.saveAll(jobs);

    }

    @Override
    public String selectRandomItems(List<String> items) {
        Random random = new Random();
        List<String> selectedItems = new ArrayList<>();

        int numberOfElements = random.nextInt(items.size() / 2);

        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = random.nextInt(items.size());
            String randomElement = items.get(randomIndex);
            selectedItems.add(randomElement);
            items.remove(randomIndex);
        }

        return StringUtils.join(selectedItems, ",");
    }

    @Override
    public void savePreferencesToFile() {
        List<Preference> preferences = preferenceRepository.findAll();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("E:\\FACULTATE\\AN 3\\SEMESTRU 5\\Licenta\\BachelorThesis\\preferences.txt"));
            writer.write(Preference.headersString());
            writer.write("\n");
            for(Preference preference: preferences){
                writer.write(preference.toFileString());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException runtimeException){
            log.debug(runtimeException.getMessage());
        }
    }

    @Override
    public void generatePreferences() {
        List<Boolean> isInterested = Arrays.asList(true, false);
        List<Integer> ratings = Arrays.asList(1,2,3,4,5);

        List<Long> userIds = userRepository.findAll().stream()
                .map(GenericUser::getId)
                .collect(Collectors.toList());
        List<Long> jobIds = jobRepository.findAll().stream()
                .map(Job::getId)
                .collect(Collectors.toList());

        List<PreferenceKey> alreadyUsed = new ArrayList<>();
        List<Preference> preferences = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            PreferenceKey key = new PreferenceKey(userIds.get(random.nextInt(userIds.size())),
                    jobIds.get(random.nextInt(jobIds.size())));
            while(alreadyUsed.contains(key)){
                key = new PreferenceKey(userIds.get(random.nextInt(userIds.size())),
                        jobIds.get(random.nextInt(jobIds.size())));
            }
            alreadyUsed.add(key);
            Preference preference = new Preference(key, userRepository.findById(key.getUserId()).orElse(null),
                    jobRepository.findById(key.getJobId()).orElse(null), isInterested.get(random.nextInt(2)),
                    ratings.get(random.nextInt(ratings.size())));
            userIds.remove(key.getUserId());
            jobIds.remove(key.getJobId());
            preferences.add(preference);
        }

        preferenceRepository.saveAll(preferences);
    }
}
