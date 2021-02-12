package bachelor.thesis.job_recruitment.core.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Preference extends BaseEntity<Long>{
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private GenericUser user;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Job job;
    private Boolean isInterested;
}
