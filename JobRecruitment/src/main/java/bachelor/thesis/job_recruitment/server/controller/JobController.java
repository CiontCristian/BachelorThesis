package bachelor.thesis.job_recruitment.server.controller;

import bachelor.thesis.job_recruitment.core.model.*;
import bachelor.thesis.job_recruitment.core.service.JobService;

import bachelor.thesis.job_recruitment.server.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/job")
@Slf4j
public class JobController {
    @Autowired
    private JobService jobService;

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleNotFound(ResourceNotFoundException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/findAllJobs")
    ResponseEntity<List<Job>> findAllJobs(@RequestParam Integer pageIndex, @RequestParam Integer pageSize,
        @RequestParam String sortType, @RequestBody Filter criteria)
    {
        log.trace("In JobController - method: findAllJobs - pageIndex={}, pageSize={}", pageIndex, pageSize);
        List<Job> jobs = jobService.findAll(pageIndex, pageSize, sortType, criteria);
        log.trace("In JobController - method: findAllJobs - jobs={}", jobs.size());

        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping(value = "/getJobRecordCount")
    ResponseEntity<Integer> getJobRecordCount(){
        Integer count = jobService.getJobRecordCount();

        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping(value = "/findJobsByIds")
    ResponseEntity<List<Job>> findJobsByIds(@RequestParam Integer count, @RequestParam List<Long> ids){
        List<Job> jobs = jobService.findJobsByIds(count, ids);

        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @PostMapping(value = "/saveJob")
    ResponseEntity<Job> saveJob(@RequestBody Job job){
        log.trace("In JobController - method: saveJob - job={}", job);

        Job savedJob = jobService.save(job);
        log.trace("In JobController - method: saveJob - savedJob={}", savedJob);


        return new ResponseEntity<>(savedJob, HttpStatus.OK);
    }

    @PostMapping(value = "/saveJobs")
    ResponseEntity<List<Job>> saveJobs(@RequestBody List<Job> jobs){
        jobs.forEach(job -> System.out.println(job.getId()));
        List<Job> savedJobs = jobService.saveJobs(jobs);

        return new ResponseEntity<>(savedJobs, HttpStatus.OK);
    }

    @PutMapping(value = "/modifyJob")
    ResponseEntity<Job> modifyJob(@RequestBody Job job){
        log.trace("In JobController - method: saveJob - jobDTO={}", job);

        Job modifiedJob = jobService.modify(job);
        log.trace("In JobController - method: saveJob - savedJob={}", modifiedJob);

        return new ResponseEntity<>(modifiedJob, HttpStatus.OK);
    }

    @DeleteMapping(value = "/removeJob/{id}")
    ResponseEntity<?> removeJob(@PathVariable Long id){
        jobService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/findJobById/{id}")
    ResponseEntity<Job> findJobById(@PathVariable Long id){

        Optional<Job> job = jobService.findJobById(id);
        if(job.isEmpty())
            throw new ResourceNotFoundException("Could not find the job with id: " + id);

        return new ResponseEntity<>(job.get(), HttpStatus.OK);
    }

    @PostMapping(value = "/savePreference")
    ResponseEntity<?> savePreference(@RequestBody Preference preference){
        jobService.savePreference(preference);
        log.trace("In JobController - method: savePreference - preference={}", preference);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value="/findJobsForContractor/{id}")
    ResponseEntity<List<Job>> findJobsForContractor(@PathVariable Long id){
        List<Job> jobs = jobService.findJobsForContractor(id);
        log.trace("In JobController - method: findJobsForContractor - Nr. of retrieved jobs={}", jobs.size());

        return new ResponseEntity<>(jobs,
                HttpStatus.OK);
    }

    @GetMapping(value = "/findJobsTitles")
    ResponseEntity<List<String>> findJobsTitles(){
        return new ResponseEntity<>(jobService.findJobsTitles(), HttpStatus.OK);
    }

    @GetMapping(value = "/findJobByTitle")
    ResponseEntity<Job> findJobByTitle(@RequestParam String title){
        Optional<Job> job = jobService.findJobByTitle(title);
        if(job.isEmpty())
            throw new ResourceNotFoundException("Job with the title " + title + " was not found!");
        return new ResponseEntity<>(job.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/getJobPreferenceForUser")
    ResponseEntity<Preference> getJobPreferenceForUser(@RequestParam Long userId, @RequestParam Long jobId){
        log.trace("In JobController - method: getJobPreferenceForUser - userId={}, jobId={}",userId, jobId);
        Optional<Preference> preference = jobService.findJobPreferenceForUser(userId, jobId);
        if(preference.isEmpty())
            throw new ResourceNotFoundException("Preference not found!");
        return new ResponseEntity<>(preference.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/getAvailableTechs")
    ResponseEntity<List<String>> getAvailableTechs(){
        List<String> techs = jobService.getAvailableTechs();
        log.trace("In JobController - method: getAvailableTechs - Nr. of techs={}", techs.size());

        return new ResponseEntity<>(techs, HttpStatus.OK);
    }

    @GetMapping(value = "/getAvailableDevTypes")
    ResponseEntity<Set<String>> getAvailableDevTypes(){
        Set<String> devs = jobService.getAvailableDevTypes();
        log.trace("In JobController - method: getAvailableDevTypes - Nr. of developer types={}", devs.size());

        return new ResponseEntity<>(devs, HttpStatus.OK);
    }

    @GetMapping(value = "/computeMainRecommenderAccuracy/{userId}")
    ResponseEntity<Integer> computeMainRecommenderAccuracy(@PathVariable Long userId){
        Integer accuracy = jobService.computeMainRecommenderAccuracy(userId);
        log.trace("In JobController - method: computeMainRecommenderAccuracy - accuracy={}", accuracy);
        return new ResponseEntity<>(accuracy, HttpStatus.OK);
    }

    @GetMapping(value = "/computeSecondaryRecommenderAccuracy/{userId}")
    ResponseEntity<Integer> computeSecondaryRecommenderAccuracy(@PathVariable Long userId){
        Integer accuracy = jobService.computeSecondaryRecommenderAccuracy(userId);
        log.trace("In JobController - method: computeSecondaryRecommenderAccuracy - accuracy={}", accuracy);
        return new ResponseEntity<>(accuracy, HttpStatus.OK);
    }
}
