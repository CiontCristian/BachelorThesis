package bachelor.thesis.job_recruitment.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Background{
    @Id
    private Long id;
    private String techs;
    private String experience;
    private String jobType;
    private String devType;
    private Boolean remote;
    @OneToOne
    @MapsId
    @JsonIgnoreProperties("background")
    private GenericUser user;

    @Override
    public String toString() {
        return "Background{" +
                "id=" + id +
                ", techs='" + techs + '\'' +
                ", experience='" + experience + '\'' +
                ", jobType='" + jobType + '\'' +
                ", devType='" + devType + '\'' +
                ", remote=" + remote +
                '}';
    }
}
