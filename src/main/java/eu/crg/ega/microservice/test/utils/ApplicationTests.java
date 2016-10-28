package eu.crg.ega.microservice.test.utils;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Rule;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;

import eu.crg.ega.microservice.test.util.TestUtils;

import static com.lordofthejars.nosqlunit.mongodb.MongoDbConfigurationBuilder.mongoDb;

/**
 * Common configuration for executing tests.
 *
 */
public class ApplicationTests {
  
  @Value("${spring.data.mongodb.host}")
  private String host;
  
  @Value("${spring.data.mongodb.database}")
  private String database;
  
  /*
   * MongoDB configuration
   */
  @Autowired
  private ApplicationContext applicationContext;
  
  @Rule
  public MongoDbRule remoteMongoDbRule = null;

  @PostConstruct
  public void initMongoConnection() {
    /*
     * Initialize connection to database. It's mandatory do it at this point because when @Rule is
     * executed properties have not been injected yet
     */
    remoteMongoDbRule = new MongoDbRule(mongoDb().host(host).databaseName(database).build());
  }
  /*
   * END MongoDB configuration
   */

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    
    TestUtils.removeUserFromContext();
  }

}
