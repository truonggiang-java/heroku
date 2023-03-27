package com.example.springboot3.configs;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.springboot3.dto.UserBatchDto;

@Configuration
public class SpringBatchConfigs{

	
//	private static final String sourceUrl = System.getProperty("user.dir") + "\\src\\main\\resources\\static" + "\\file";
	
	@Autowired
	private UserWriter userWriter;


	@Bean
	public UserProcessor processor() {
		return new UserProcessor();
	}


	@Bean
	@StepScope
	public FlatFileItemReader<UserBatchDto> reader(@Value("#{jobParameters['fileName']}") String path) {

		FlatFileItemReader<UserBatchDto> flatItemReader = new FlatFileItemReader<>();
		flatItemReader.setResource(new FileSystemResource(path));
		flatItemReader.setName("csvReader");
		flatItemReader.setLinesToSkip(1);
		flatItemReader.setLineMapper(lineMapper());
		return flatItemReader;
	}

	private LineMapper<UserBatchDto> lineMapper() {
		DefaultLineMapper<UserBatchDto> UserBatchDtoBatchDefaultLineMapper = new DefaultLineMapper<>();

		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setDelimiter(",");
		delimitedLineTokenizer.setStrict(false);
		String[] names = { "email", "password", "address", "phone", "fullname" };
		delimitedLineTokenizer.setNames(names);

		BeanWrapperFieldSetMapper<UserBatchDto> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
		beanWrapperFieldSetMapper.setTargetType(UserBatchDto.class);

		UserBatchDtoBatchDefaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
		UserBatchDtoBatchDefaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
		return UserBatchDtoBatchDefaultLineMapper;
	}



	@Bean
	public Step step1(JobRepository jobRepository,
			PlatformTransactionManager transactionManager,FlatFileItemReader<UserBatchDto> reader) {
		return new StepBuilder("step1", jobRepository)
			.<UserBatchDto, UserBatchDto> chunk(50, transactionManager)
			.reader(reader)
			.processor(processor())
			.writer(userWriter)
			.taskExecutor(taskExecutor())
			.build();
	}

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setCorePoolSize(10);
		taskExecutor.setQueueCapacity(10);
		return taskExecutor;
	}

	@Bean
	public Job runJob(JobRepository jobRepository, Step step1) {
		return new JobBuilder("importUserBatchDtoJob", jobRepository)
				.incrementer(new RunIdIncrementer())
				.flow(step1)
				.end()
				.build();

	}


	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
