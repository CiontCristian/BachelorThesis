package bachelor.thesis.job_recruitment.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class FileDTO extends BaseDTO<Long>{
    private String name;
    private String type;
    private byte[] data;
}
