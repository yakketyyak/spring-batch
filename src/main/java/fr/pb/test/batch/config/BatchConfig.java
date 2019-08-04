package fr.pb.test.batch.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.pb.test.batch.domain.User;
import fr.pb.test.batch.infra.UserRowMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.*;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private Environment env;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public NamedParameterJdbcTemplate getTemplate() throws Exception{
        return new NamedParameterJdbcTemplate(getDataSource());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    public JobRepository jobRepository() throws Exception {
        return new MapJobRepositoryFactoryBean(transactionManager()).getJobRepository();
    }

    @Bean
    public JobLauncher jobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository());
        return jobLauncher;
    }
    @Bean
    public Step step1() throws Exception{
        return stepBuilderFactory.get("step1")
                .<List<User>, List<User>>chunk(2)
                .reader(userReader())
                .processor(userProcessor())
                .writer(userWriter())
                .build();
    }

    @Bean
    public Job listUserJob(Step step1) throws Exception {
        return jobBuilderFactory.get("listUserJob")
                .start(step1())
                .build();
    }
    @Bean
    ItemReader<List<User>> userReader(){
        return new ItemReader<List<User>>() {
            @Override
            public List<User> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                String QUERY = "SELECT * FROM user";
                List<User> users = getTemplate().query(QUERY, new UserRowMapper());
                users.forEach(System.out::println);
                return users;
            }
        };

    }
    @Bean
    ItemProcessor<List<User>,List<User>> userProcessor(){
        return new ItemProcessor<List<User>, List<User>>() {
            @Override
            public List<User> process(List<User> users) throws Exception {
                System.out.println("PROCESSOR");
                users.stream().forEach(user-> {
                    ((User) user).setExpired(new Date());
                    ((User) user).setLocked(Boolean.FALSE);
                    user.setForgotPasswordCode(new String("JESUS"));
                });
                return users;
            }
        };
    }

    @Bean
    ItemWriter<List<User>> userWriter() throws Exception{
        return new ItemWriter<List<User>>() {
            @Override
            public void write(List<? extends List<User>> users) throws Exception {
                System.out.println("WRITER");
                users.forEach(System.out::println);
                String QUERY = "UPDATE user set expired = :expired, locked = :locked, forgot_password_code = :forgot_password_code where id = :id";
                users.forEach(user->{
                    user.forEach(u->{
                        MapSqlParameterSource in = new MapSqlParameterSource();
                        in.addValue("id", u.getId());
                        in.addValue("expired", u.getExpired());
                        in.addValue("locked", u.getLocked());
                        in.addValue("forgot_password_code", u.getForgotPasswordCode());
                        try {
                            getTemplate().update(QUERY, in);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

                });
            }
        };
    }

    @Bean(name="dataSource")
    public DataSource getDataSource() throws IOException {
        Resource resource = new ClassPathResource("/db.properties");
        Properties props = PropertiesLoaderUtils.loadProperties(resource);
        HikariConfig hc = new HikariConfig();
        hc.setDriverClassName(props.getProperty("driverClassName"));
        hc.setJdbcUrl(props.getProperty("jdbcUrl"));
        hc.setUsername(props.getProperty("username"));
        hc.setPassword(props.getProperty("password"));
        hc.setPoolName("PoolName");

        return new HikariDataSource(hc);
    }
}
