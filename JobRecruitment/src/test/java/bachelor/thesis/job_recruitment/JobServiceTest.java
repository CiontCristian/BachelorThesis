package bachelor.thesis.job_recruitment;


import bachelor.thesis.job_recruitment.core.model.Job;
import bachelor.thesis.job_recruitment.core.repository.ContractorRepository;
import bachelor.thesis.job_recruitment.core.repository.JobRepository;
import bachelor.thesis.job_recruitment.core.repository.PreferenceRepository;
import bachelor.thesis.job_recruitment.core.service.JobService;
import bachelor.thesis.job_recruitment.core.service.JobServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class JobServiceTest {
    @InjectMocks
    private JobServiceImpl jobService;
    @MockBean
    private JobRepository jobRepository;
    @MockBean
    private PreferenceRepository preferenceRepository;
    @MockBean
    private ContractorRepository contractorRepository;

    @Test
    void testSaveJob(){
        Job job = new Job("JobTest", "DescTest","full-time", true, "entry",800, "QA", "Spring", 2,null, null);

        when(jobRepository.save(job)).thenReturn(job);

        assertThat(jobService.save(job),is(equalTo(job)));
    }

    @Test
    void testJobRecordCount(){
        List<Job> jobs = new ArrayList<>(50);

        when(jobRepository.findAll()).thenReturn(jobs);

        assertThat(jobService.getJobRecordCount(),is(equalTo(jobs.size())));
    }
}
