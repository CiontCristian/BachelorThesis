package bachelor.thesis.job_recruitment.core.service;

import bachelor.thesis.job_recruitment.core.model.Job;
import bachelor.thesis.job_recruitment.core.repository.JobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService{
    public static final Logger logger = LoggerFactory.getLogger(JobService.class);

    @Autowired
    private JobRepository jobRepository;

    @Override
    public List<Job> findAll(Integer pageIndex, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
        logger.trace("In JobServiceImpl - method: findAll() - pageIndex={}, pageSize={}", pageIndex, pageSize);
        Page<Job> jobs = jobRepository.findAll(pageRequest);
        logger.trace("In JobServiceImpl - method: findAll() - jobs={}", jobs);

        return jobs.getContent();
    }

    @Override
    public Optional<Job> findJobById(Long id) {
        return jobRepository.findById(id);
    }

    @Override
    public Job save(Job job) {
        logger.trace("In JobServiceImpl - method: save() - job={}", job);

        return jobRepository.save(job);
    }

    @Override
    public Job modify(Job modifiedJob) {
        logger.trace("In JobServiceImpl - method: modify() - job={}", modifiedJob);

        return jobRepository.saveAndFlush(modifiedJob);
    }

    @Override
    public void remove(Long id) {
        logger.trace("In JobServiceImpl - method: remove() - id={}", id);

        jobRepository.deleteById(id);
    }
}
