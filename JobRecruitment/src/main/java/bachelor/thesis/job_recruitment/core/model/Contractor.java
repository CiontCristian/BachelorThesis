package bachelor.thesis.job_recruitment.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Contractor extends BaseEntity<Long>{
    @Column(length = 50)
    private String name;
    @Column(length = 3000)
    private String description;



    private byte[] logo;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, orphanRemoval = true)
    private Location location;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "company")
    private List<Job> offers;
}
