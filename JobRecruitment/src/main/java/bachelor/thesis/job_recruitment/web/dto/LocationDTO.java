package bachelor.thesis.job_recruitment.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class LocationDTO extends BaseDTO<Long>{
    private String address;
    private String city;
    private String country;
}
