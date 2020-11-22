package bachelor.thesis.job_recruitment.core.repository;

import bachelor.thesis.job_recruitment.core.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface JPARepository<T extends BaseEntity<ID>, ID extends Serializable>
        extends JpaRepository<T, ID> {
}