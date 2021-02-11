package bachelor.thesis.job_recruitment.web.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class UserDTO extends BaseDTO<Long> {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String phoneNumber;
    private Character gender;
    private BackgroundDTO background;
    private PermissionDTO permission;
    private LocationDTO location;
    private ContractorDTO company;
}
