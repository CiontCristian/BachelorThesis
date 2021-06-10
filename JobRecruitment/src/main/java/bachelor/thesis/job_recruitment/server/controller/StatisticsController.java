package bachelor.thesis.job_recruitment.server.controller;

import bachelor.thesis.job_recruitment.core.model.Statistic;
import bachelor.thesis.job_recruitment.core.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/statistic")
@Slf4j
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @GetMapping(value = "/companiesWithNumberOfOffers")
    ResponseEntity<List<Statistic>> companiesWithNumberOfOffers(){
        List<Statistic> statistics = statisticsService.companiesWithNumberOfOffers();
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    @GetMapping(value = "/mostLikedCompanies")
    ResponseEntity<List<Statistic>> mostLikedCompanies(){
        List<Statistic> statistics = statisticsService.mostLikedCompanies();
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    @GetMapping(value = "/mostAppliedJobsForContractor/{id}")
    ResponseEntity<List<Statistic>> mostAppliedJobsForContractor(@PathVariable Long id){
        log.trace("In StatisticsController, method = mostAppliedJobsForContractor, id={}", id);
        List<Statistic> statistics = statisticsService.mostAppliedJobsForContractor(id);
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }
}
