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
public class Permission extends BaseEntity<Long>{
    private Boolean isClient;
    private Boolean isCompany;
    private Boolean isAdmin;
}
