package bachelor.thesis.job_recruitment.core.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
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
