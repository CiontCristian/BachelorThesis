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
    private Integer nrOfEmployees;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private File logo;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Location location;
    @OneToOne(fetch = FetchType.EAGER)
    private GenericUser owner;

    /*@Override
    public String toString(){
        return "contractor";
    }*/

}
