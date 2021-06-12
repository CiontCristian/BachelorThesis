package bachelor.thesis.job_recruitment.core.model;

import lombok.*;

import javax.persistence.*;

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
    private Boolean interested;
    private Boolean applied;
    private Boolean relevanceMain;
    private Boolean relevanceSec;

    @Override
    public String toString() {
        return "Preference{" +
                "key=" + key +
                ", interested=" + interested +
                ", applied=" + applied +
                ", relevanceMain=" + relevanceMain +
                ", relevanceSec=" + relevanceSec +
                '}';
    }
}
