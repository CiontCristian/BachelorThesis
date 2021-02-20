package bachelor.thesis.job_recruitment.core.repository;

import bachelor.thesis.job_recruitment.core.model.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
}
