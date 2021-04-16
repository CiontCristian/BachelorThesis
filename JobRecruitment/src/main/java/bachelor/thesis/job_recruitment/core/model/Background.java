package bachelor.thesis.job_recruitment.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Background extends BaseEntity<Long>{
    private String techs;
    private String experience;
    private String jobType;
    private String devType;
    private Boolean remote;

}
