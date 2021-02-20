package bachelor.thesis.job_recruitment.core.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Preference{
    @EmbeddedId
    private PreferenceKey key;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapsId("userId")
    private GenericUser user;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapsId("jobId")
    private Job job;
    private Boolean isInterested;
}
