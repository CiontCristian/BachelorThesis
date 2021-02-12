package bachelor.thesis.job_recruitment.core.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Job extends BaseEntity<Long>{
    private String title;
    private String description;
    private String jobType;//full-time, part-time, internship
    private Boolean remote;
    private String minExperience;//entry, junior, senior, lead,...
    private Integer minCompensation;//will be optional
    private String devType;//backend, frontend, QA, full-stack,...
    private String techs;//java, python, c#,...
    private Integer availablePos;
    @ManyToOne(fetch = FetchType.EAGER)
    private Contractor contractor;

}
