package bachelor.thesis.job_recruitment.server.controller;

import bachelor.thesis.job_recruitment.core.model.Job;
import bachelor.thesis.job_recruitment.core.model.Preference;
import bachelor.thesis.job_recruitment.core.service.JobService;

import bachelor.thesis.job_recruitment.server.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/job")
public class JobController {
    public static final Logger logger = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private JobService jobService;

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleException(ResourceNotFoundException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/findAllJobs")
    ResponseEntity<List<Job>> findAllJobs(@RequestParam Integer pageIndex, @RequestParam Integer pageSize){
        logger.trace("In JobController - method: findAllJobs - pageIndex={}, pageSize={}", pageIndex, pageSize);
        List<Job> jobs = jobService.findAll(pageIndex, pageSize);
        logger.trace("In JobController - method: findAllJobs - jobs={}", jobs);

        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @PostMapping(value = "/saveJob")
    ResponseEntity<Job> saveJob(@RequestBody Job job){
        logger.trace("In JobController - method: saveJob - jobDTO={}", job);

        Job savedJob = jobService.save(job);
        logger.trace("In JobController - method: saveJob - savedJob={}", savedJob);


        return new ResponseEntity<>(savedJob, HttpStatus.OK);
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
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value="/findJobsForContractor/{id}")
    ResponseEntity<List<Job>> findJobsForContractor(@PathVariable Long id){
        return new ResponseEntity<>(jobService.findJobsForContractor(id),
                HttpStatus.OK);
    }


}
