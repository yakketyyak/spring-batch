package fr.pb.test.batch;

import fr.pb.test.batch.config.BatchConfig;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String args[]){

        /*ApplicationContext context = new ClassPathXmlApplicationContext("job/job-config.xml");

        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("myFirstJob");

        try {
            JobExecution execution = jobLauncher.run(job, new JobParameters());
            System.out.println("Job Exit Status : "+ execution.getStatus());
            if(execution.getStatus().equals(BatchStatus.COMPLETED)){
                System.exit(0);
            }

        } catch (JobExecutionException e) {
            System.out.println("Job ExamResult failed");
            e.printStackTrace();
        }*/
        ApplicationContext context = new AnnotationConfigApplicationContext(BatchConfig.class);

        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        Job job = context.getBean("listUserJob", Job.class);

        JobParameters jobParameters = new JobParametersBuilder().toJobParameters();

        try {
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        }
        catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        }
        catch (JobRestartException e) {
            e.printStackTrace();
        }
        catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        }
        catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }
}
