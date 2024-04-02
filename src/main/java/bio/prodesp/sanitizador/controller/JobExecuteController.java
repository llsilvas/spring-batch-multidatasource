package bio.prodesp.sanitizador.controller;

import bio.prodesp.sanitizador.domain.coleta.principal.ColetaPrincipal;
import bio.prodesp.sanitizador.domain.coleta.principal.repository.ColetaPrincipalRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobExecuteController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @Autowired
    ColetaPrincipalRepository principalRepository;

    @RequestMapping("/start")
    public void handle() throws Exception {
        jobLauncher.run(job, new JobParameters());
    }
}
