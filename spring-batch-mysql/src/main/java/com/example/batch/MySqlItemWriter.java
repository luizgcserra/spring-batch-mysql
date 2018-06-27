package com.example.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.repository.CrudRepository;

public class MySqlItemWriter<Model, Key> implements ItemWriter<Model>, InitializingBean {

	private static final Logger log = LoggerFactory.getLogger(MySqlItemWriter.class);
	private static long rowsProcessed = 0;
	private CrudRepository<Model, Key> repository;

	public MySqlItemWriter(CrudRepository<Model, Key> repository) {
		this.repository = repository;
	}

	@Override
	public void write(List<? extends Model> arg0) throws Exception {
		repository.saveAll(arg0);

		rowsProcessed = rowsProcessed + arg0.size();
		log.info(String.format("%s rows processed", rowsProcessed));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}

}
