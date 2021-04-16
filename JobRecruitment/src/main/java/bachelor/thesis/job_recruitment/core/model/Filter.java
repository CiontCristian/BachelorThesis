package bachelor.thesis.job_recruitment.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Filter {
    private String jobType;
    private Boolean remote;
    private String minExperience;
    private Integer minCompensation;
    private String devType;
    private String techs;
    private Integer availablePos;
}
