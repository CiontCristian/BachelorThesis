package bachelor.thesis.job_recruitment;

import bachelor.thesis.job_recruitment.core.model.Job;
import bachelor.thesis.job_recruitment.core.repository.JobRepository;
import bachelor.thesis.job_recruitment.core.service.JobService;
import bachelor.thesis.job_recruitment.server.controller.JobController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(JobController.class)
class JobControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private JobService jobService;

    @Test
    void testJobCount() throws Exception {
        when(jobService.getJobRecordCount()).thenReturn(50);

        mvc.perform(get("/job/getJobRecordCount"))
                .andExpect(status().isOk())
                .andExpect(content().string("50"));
    }

    @Test
    void testSaveJob() throws Exception {
        String jobJSON = "{\"title\" : \"JobTest\", \"description\" : \"DescTest\", \"jobType\" : \"full-time\", \"remote\" : true," +
                " \"minExperience\" : \"entry\",\"minCompensation\" : 800," +
                "\"devType\" : \"QA\",\"techs\" : \"Spring\",\"availablePos\" : 2,\"dateAdded\" : null,\"contractor\" : null}";
        Job job = new Job("JobTest", "DescTest","full-time", true, "entry",800, "QA", "Spring", 2,null, null);

        when(jobService.save(job)).thenReturn(job);

        mvc.perform(post("/job/saveJob")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jobJSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(job.getTitle())));
    }

}
