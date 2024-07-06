package com.its.test.controller;

import com.its.test.dto.CourseDTO;
import com.its.test.entity.Course;
import com.its.test.entity.Student;
import com.its.test.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/load")
@Tag(name = "API for executing batch job", description="")
public class LoadController {

  private final JobLauncher jobLauncher;
  private final Job importBooksJob;

    public LoadController(JobLauncher jobLauncher, Job importBooksJob) {
        this.jobLauncher = jobLauncher;
        this.importBooksJob = importBooksJob;
    }

    @GetMapping
    public ResponseEntity<BatchStatus> load() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(maps);
        JobExecution jobExecution = jobLauncher.run(importBooksJob, jobParameters);
        System.out.println("JobExecution: "+jobExecution.getStatus());
        System.out.println("Batch is running...");
        while (jobExecution.isRunning()){
            System.out.println("...");
        }
        return ResponseEntity.ok(BatchStatus.COMPLETED);
    }


}
