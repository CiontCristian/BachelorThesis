package bachelor.thesis.job_recruitment.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class BackgroundDTO extends BaseDTO<Long>{
    private String formalEducation;
    private String experience;
}
