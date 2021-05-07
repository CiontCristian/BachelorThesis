package bachelor.thesis.job_recruitment.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Job extends BaseEntity<Long>{
    private String title;
    @Column(length = 5000)
    private String description;
    private String jobType;//full-time, part-time, internship
    private Boolean remote;
    private String minExperience;//entry, junior, senior, lead,...
    private Integer minCompensation;//will be optional
    private String devType;//backend, frontend, QA, full-stack,...
    private String techs;//java, python, c#,...
    private Integer availablePos;
    private Date dateAdded;
    @ManyToOne(fetch = FetchType.EAGER)
    private Contractor contractor;

    @Override
    public String toString() {
        return "Job{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", jobType='" + jobType + '\'' +
                ", remote=" + remote +
                ", minExperience='" + minExperience + '\'' +
                ", minCompensation=" + minCompensation +
                ", devType='" + devType + '\'' +
                ", techs='" + techs + '\'' +
                ", availablePos=" + availablePos +
                ", dateAdded=" + dateAdded +
                '}';
    }
}
