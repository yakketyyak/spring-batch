import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String args[]){

        ApplicationContext context = new ClassPathXmlApplicationContext("job/job-config.xml");

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
        }
    }
}
