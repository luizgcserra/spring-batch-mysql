package com.example.configuration;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.example.batch.MySqlItemWriter;
import com.example.listeners.BeneficiaryJobCompletionNotificationListener;
import com.example.processors.BeneficiaryItemProcessor;
import com.example.repository.BeneficiaryRepository;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration  {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	private final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);

	@Value("${app.filePath}")
	private String filePath;

	@Value("${batch.chunk}")
	private int chunk;
	
	@Bean
	public FlatFileItemReader<com.example.contracts.Beneficiary> reader() {

		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter("\t");
		lineTokenizer.setNames(new String[] { "uf", "codigoMunicipio", "nomeMuncipio", "codigoFuncao",
				"codigoSubFuncao", "codigoPrograma", "codigoAcao", "nisBeneficiario", "nomeBeneficiario",
				"fonteFinalidade", "mesReferencia", "valorParcela", "mesCompetencia", "dataSaque" });
		lineTokenizer.setStrict(false);

		DefaultLineMapper<com.example.contracts.Beneficiary> lineMapper = new DefaultLineMapper<com.example.contracts.Beneficiary>();
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(new BeanWrapperFieldSetMapper<com.example.contracts.Beneficiary>() {
			{
				setTargetType(com.example.contracts.Beneficiary.class);
			}
		});

		FlatFileItemReader<com.example.contracts.Beneficiary> flatFile = new FlatFileItemReader<com.example.contracts.Beneficiary>();
		flatFile.setName("beneficiaryItemReader");
		flatFile.setResource(new ClassPathResource(this.filePath));
		flatFile.setLinesToSkip(1);
		flatFile.setStrict(false);
		flatFile.setLineMapper(lineMapper);

		return flatFile;
	}

	@Bean
	public BeneficiaryItemProcessor processor() {
		return new BeneficiaryItemProcessor();
	}

	@Bean
	public MySqlItemWriter<com.example.model.Beneficiary, String> writer(
			BeneficiaryRepository beneficiaryRepository) {
		return new MySqlItemWriter<com.example.model.Beneficiary, String>(beneficiaryRepository);
	}

	@Bean
	public Job importUserJob(BeneficiaryJobCompletionNotificationListener listener, Step step1) {
		log.info("Starting Job");

		return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer()).listener(listener).flow(step1)
				.end().build();
	}

	@Bean
	public Step step1(ItemWriter<com.example.model.Beneficiary> writer) {
		return stepBuilderFactory
				.get("step1").<com.example.contracts.Beneficiary, com.example.model.Beneficiary>chunk(chunk)
				.reader(reader()).processor(processor()).writer(writer).build();
	}
}
