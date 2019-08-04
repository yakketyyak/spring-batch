package fr.pb.test.batch.infra;

import fr.pb.test.batch.domain.User;
import org.springframework.batch.item.ItemProcessor;

import java.util.Date;
import java.util.List;

public class UserProcessor implements ItemProcessor<List<User>,List<User>> {

    public List<User> process(List<User> users) throws Exception {
        System.out.println("PROCESSOR");
        users.stream().forEach(user-> {
            ((User) user).setExpired(new Date());
            ((User) user).setLocked(Boolean.FALSE);
            user.setForgotPasswordCode(new String("JESUS"));
        });
        return users;
    }
}
