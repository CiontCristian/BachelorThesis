package bachelor.thesis.job_recruitment.web.converter;

import bachelor.thesis.job_recruitment.core.model.Job;
import bachelor.thesis.job_recruitment.web.dto.JobDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobConverter extends BaseConverter<Job, JobDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Job convertDtoToModel(JobDTO dto) {
        Job job = modelMapper.map(dto, Job.class);
        job.setId(dto.getId());
        return job;
    }

    @Override
    public JobDTO convertModelToDto(Job job) {
        JobDTO jobDTO = modelMapper.map(job, JobDTO.class);
        jobDTO.setId(job.getId());
        return jobDTO;
    }
}
