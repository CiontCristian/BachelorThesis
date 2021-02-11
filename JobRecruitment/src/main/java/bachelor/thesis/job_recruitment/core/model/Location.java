package bachelor.thesis.job_recruitment.core.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Location extends BaseEntity<Long>{
    private String address;
    private String city;
    private String country;
}
