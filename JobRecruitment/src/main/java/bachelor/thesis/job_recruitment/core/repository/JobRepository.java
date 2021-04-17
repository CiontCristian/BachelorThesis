package bachelor.thesis.job_recruitment.core.repository;

import bachelor.thesis.job_recruitment.core.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobRepository extends JPARepository<Job, Long> {
}
