package bachelor.thesis.job_recruitment.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class JobDTO extends BaseDTO<Long>{
    private String title;
    private String description;
    private String jobType;//full-time, part-time, internship
    private Boolean remote;
    private String minExperience;//entry, junior, senior, lead,...
    private Integer minCompensation;//will be optional
    private String devType;//backend, frontend, QA, full-stack,...
    private String techs;//java, python, c#,...
    private Integer availablePos;
    private ContractorDTO contractor;
}
