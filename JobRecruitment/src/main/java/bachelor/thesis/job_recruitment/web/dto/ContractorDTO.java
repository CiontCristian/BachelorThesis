package bachelor.thesis.job_recruitment.web.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class ContractorDTO extends BaseDTO<Long>{
    private String name;
    private String description;
    private byte[] logo;
    private LocationDTO location;
    private List<JobDTO> offers;
}
