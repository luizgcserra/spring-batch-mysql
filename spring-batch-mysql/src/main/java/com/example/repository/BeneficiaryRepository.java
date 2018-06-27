package com.example.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Beneficiary;

@Repository
public interface BeneficiaryRepository extends CrudRepository<Beneficiary, String> {
	@Query(value="SELECT count(*) FROM beneficiary")
	public long count();
}
