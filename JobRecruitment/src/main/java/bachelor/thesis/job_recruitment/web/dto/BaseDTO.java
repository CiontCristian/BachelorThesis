package bachelor.thesis.job_recruitment.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseDTO<ID extends Serializable> implements Serializable {
    private ID id;
}
