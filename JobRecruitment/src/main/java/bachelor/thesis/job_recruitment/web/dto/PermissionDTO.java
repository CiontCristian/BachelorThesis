package bachelor.thesis.job_recruitment.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class PermissionDTO extends BaseDTO<Long>{
    private Boolean isClient;
    private Boolean isAdmin;
}
