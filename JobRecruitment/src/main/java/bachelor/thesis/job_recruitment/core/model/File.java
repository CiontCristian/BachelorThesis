package bachelor.thesis.job_recruitment.core.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Lob;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class File extends BaseEntity<Long>{
    private String name;
    private String type;
    @Lob
    private byte[] data;

    @Override
    public String toString() {
        return "File{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
