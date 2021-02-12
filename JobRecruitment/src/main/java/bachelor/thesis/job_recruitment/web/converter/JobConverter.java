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
    @Autowired
    private ContractorConverter contractorConverter;

    @Override
    public Job convertDtoToModel(JobDTO dto) {
        Job job = modelMapper.map(dto, Job.class);
        job.setId(dto.getId());
        job.setContractor(contractorConverter.convertDtoToModel(dto.getContractor()));
        return job;
    }

    @Override
    public JobDTO convertModelToDto(Job job) {
        JobDTO jobDTO = modelMapper.map(job, JobDTO.class);
        jobDTO.setId(job.getId());
        jobDTO.setContractor(contractorConverter.convertModelToDto(job.getContractor()));
        return jobDTO;
        /*JobDTO jobDTO = JobDTO.builder()
                .title(job.getTitle())
                .description(job.getDescription())
                .jobType(job.getJobType())
                .availablePos(job.getAvailablePos())
                .devType(job.getDevType())
                .minCompensation(job.getMinCompensation())
                .minExperience(job.getMinExperience())
                .remote(job.getRemote())
                .techs(job.getTechs())
                .build();
        jobDTO.setId(job.getId());
        return jobDTO;*/
    }
}
