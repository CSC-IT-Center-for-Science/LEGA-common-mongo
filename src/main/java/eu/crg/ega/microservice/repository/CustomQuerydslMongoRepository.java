package eu.crg.ega.microservice.repository;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.mysema.query.types.Predicate;

import eu.crg.ega.microservice.helper.CommonQuery;

@NoRepositoryBean
public interface CustomQuerydslMongoRepository<T, ID extends Serializable>
    extends
    MongoRepository<T, ID>,
    QueryDslPredicateExecutor<T> {

  public Page<T> findAll(Predicate predicate, CommonQuery commonQuery);

  public Page<T> findAll(CommonQuery commonQuery);
}
