package eu.crg.ega.microservice.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.QueryDslMongoRepository;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.util.Assert;

import com.mysema.query.types.EntityPath;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.path.PathBuilder;

import eu.crg.ega.microservice.helper.CommonQuery;

public class CustomQuerydslMongoRepositoryImpl<T, ID extends Serializable>
    extends QueryDslMongoRepository<T, ID> implements CustomQuerydslMongoRepository<T, ID> {

  private final PathBuilder<T> builder;
  private final EntityInformation<T, ID> entityInformation;
  private final MongoOperations mongoOperations;


  public CustomQuerydslMongoRepositoryImpl(MongoEntityInformation<T, ID> entityInformation,
                                           MongoOperations mongoOperations) {
    this(entityInformation, mongoOperations, SimpleEntityPathResolver.INSTANCE);
  }

  public CustomQuerydslMongoRepositoryImpl(MongoEntityInformation<T, ID> entityInformation,
                                           MongoOperations mongoOperations,
                                           EntityPathResolver resolver) {
    super(entityInformation, mongoOperations);
    Assert.notNull(resolver);
    EntityPath path = resolver.createPath(entityInformation.getJavaType());
    this.builder = new PathBuilder(path.getType(), path.getMetadata());
    this.entityInformation = entityInformation;
    this.mongoOperations = mongoOperations;
  }

  public Page<T> findAll(Predicate predicate, CommonQuery commonQuery) {
    Page<T> page = null;
    if (commonQuery.getLimit() == 0 && commonQuery.getSkip() == 0) {
      // Return ALL results
      List<T> list = findAll(predicate);
      page = new PageImpl<T>(list);
    } else {
      page = findAll(predicate, commonQuery.getPageable());
    }
    return page;
  }

  public Page<T> findAll(CommonQuery commonQuery) {
    Page<T> page = null;
    if (commonQuery.getLimit() == 0 && commonQuery.getSkip() == 0) {
      // Return ALL results
      List<T> list = findAll();
      page = new PageImpl<T>(list);
    } else {
      page = findAll(null, commonQuery);
    }
    return page;
  }
}