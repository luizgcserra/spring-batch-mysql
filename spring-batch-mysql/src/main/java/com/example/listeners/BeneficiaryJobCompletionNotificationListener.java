package com.example.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.repository.BeneficiaryRepository;

@Component
public class BeneficiaryJobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(BeneficiaryJobCompletionNotificationListener.class);
	private final BeneficiaryRepository repository;

	@Autowired
	public BeneficiaryJobCompletionNotificationListener(BeneficiaryRepository repository) {
		this.repository = repository;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");

			long countRows = repository.count();

			log.info(String.format("%s rows processed",countRows));
		}
	}

}
