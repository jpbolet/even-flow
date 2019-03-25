package com.jpbolet.evenflow.api.repository;

import com.jpbolet.evenflow.api.model.Visit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "visits", path = "visits")
public interface VisitRepository extends MongoRepository<Visit, String> {

	List<Visit> findById(@Param("id") Long id);

	List<Visit> findAll();


}
