package bachelor.thesis.job_recruitment.core.service;


import bachelor.thesis.job_recruitment.core.model.Statistic;
import bachelor.thesis.job_recruitment.core.repository.ContractorRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements  StatisticsService{
    @Autowired
    private JobService jobService;
    @Autowired
    private ContractorRepository contractorRepository;

    @Override
    public List<Statistic> companiesWithNumberOfOffers() {
        List<Statistic> statistics = new ArrayList<>();
        contractorRepository.findAll()
                .forEach(
                        contractor -> {
                            Statistic statistic = new Statistic(contractor.getName(),
                                    jobService.getContractorJobCount(contractor.getId()));
                            statistics.add(statistic);
                        }
                );

        return statistics;
    }

    @Override
    public List<Statistic> mostLikedJobs() {
        List<Statistic> statistics = new ArrayList<>();
        jobService.findAll().forEach(
                job -> {
                    Statistic statistic = new Statistic(job.getTitle(),
                            jobService.countLikedJobPreferences(job.getId()));
                    statistics.add(statistic);

                }
        );

        return statistics.stream()
                .sorted(Comparator.comparingInt(Statistic::getValue).reversed())
                .limit(10L)
                .collect(Collectors.toList());
    }

    @Override
    public List<Statistic> mostAppliedJobsForContractor(Long id) {
        List<Statistic> statistics = new ArrayList<>();
        jobService.findJobsForContractor(id).forEach(
                job -> {
                    Statistic statistic = new Statistic(job.getTitle(),
                            jobService.countAppliedJobPreferences(job.getId()));
                    statistics.add(statistic);
                }
        );
        return statistics;
    }
}
