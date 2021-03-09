package bachelor.thesis.job_recruitment.core.repository;

import bachelor.thesis.job_recruitment.core.model.Preference;
import bachelor.thesis.job_recruitment.core.model.PreferenceKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {

    Optional<Preference> findByKey(PreferenceKey key);
}
