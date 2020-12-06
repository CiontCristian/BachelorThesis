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
    private String type;
    private ContractorDTO contractor;
}
