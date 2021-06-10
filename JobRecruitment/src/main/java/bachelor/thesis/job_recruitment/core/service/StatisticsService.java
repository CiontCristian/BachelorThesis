package bachelor.thesis.job_recruitment.core.service;

import bachelor.thesis.job_recruitment.core.model.Statistic;

import java.util.List;

public interface StatisticsService {
    List<Statistic> companiesWithNumberOfOffers();
    List<Statistic> mostLikedCompanies();
    List<Statistic> mostAppliedJobsForContractor(Long id);
}
