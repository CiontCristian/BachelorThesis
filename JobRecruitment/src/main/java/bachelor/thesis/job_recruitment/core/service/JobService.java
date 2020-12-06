package bachelor.thesis.job_recruitment.core.service;

import bachelor.thesis.job_recruitment.core.model.Job;

import java.util.List;

public interface JobService {
    List<Job> findAll(Integer pageIndex, Integer pageSize);
}
