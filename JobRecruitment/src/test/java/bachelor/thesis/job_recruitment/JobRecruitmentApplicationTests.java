package bachelor.thesis.job_recruitment;

import bachelor.thesis.job_recruitment.core.model.Job;
import bachelor.thesis.job_recruitment.core.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class JobRecruitmentApplicationTests {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JobRepository jobRepository;

    @Test
    void testSaveJob() throws Exception {
        Job job = new Job("Job1", "Desc1","full-time", true, "entry",800, "QA", "java", 1,null, null);

        mvc.perform(post("/job/saveJob")
                .content(job.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
               ;
    }

}
