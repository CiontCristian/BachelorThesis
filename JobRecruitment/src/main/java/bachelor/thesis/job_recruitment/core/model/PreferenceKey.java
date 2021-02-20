package bachelor.thesis.job_recruitment.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreferenceKey implements Serializable {
    private Long userId;
    private Long jobId;
}
