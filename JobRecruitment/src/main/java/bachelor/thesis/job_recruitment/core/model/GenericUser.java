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
public class GenericUser extends BaseEntity<Long>{

    @Column(length = 20)
    private String username;
    @Column(length = 30)
    private String password;
    @Column(length = 40)
    private String email;
    @Column(length = 15)
    private String firstName;
    @Column(length = 15)
    private String lastName;
    private Date dateOfBirth;
    @Column(length = 10)
    private String phoneNumber;
    private Character gender;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Background background;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Permission permission;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Location location;
}
