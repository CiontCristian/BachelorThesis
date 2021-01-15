package bachelor.thesis.job_recruitment.web.controller;

import bachelor.thesis.job_recruitment.core.model.Job;
import bachelor.thesis.job_recruitment.core.service.JobService;
import bachelor.thesis.job_recruitment.web.converter.JobConverter;
import bachelor.thesis.job_recruitment.web.dto.JobDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/job")
public class JobController {
    public static final Logger logger = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private JobService jobService;

    @Autowired
    private JobConverter jobConverter;

    @GetMapping(value = "/findAllJobs")
    ResponseEntity<List<JobDTO>> findAllJobs(@RequestParam Integer pageIndex, @RequestParam Integer pageSize){
        logger.trace("In JobController - method: findAllJobs - pageIndex={}, pageSize={}", pageIndex, pageSize);
        List<Job> jobs = jobService.findAll(pageIndex, pageSize);
        logger.trace("In JobController - method: findAllJobs - jobs={}", jobs);

        return new ResponseEntity<>(jobConverter.convertModelsToDtos(jobs), HttpStatus.OK);
    }

}
