package bachelor.thesis.job_recruitment.core.repository;

import bachelor.thesis.job_recruitment.core.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JPARepository<File, Long> {
}
