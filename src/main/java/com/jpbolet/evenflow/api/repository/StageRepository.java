package com.jpbolet.evenflow.api.repository;

import com.jpbolet.evenflow.api.model.Stage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "stages", path = "stages")
public interface StageRepository extends MongoRepository<Stage, String> {

	List<Stage> findById(@Param("id") Long id);

	List<Stage> findByName(@Param("name") String name);

	List<Stage> findAll();

}
