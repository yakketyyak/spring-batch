package infra;

import domain.User;
import lombok.Data;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.util.List;

@Data
public class UserReader implements ItemReader<List<User>> {

    private HibernateTemplate hibernateTemplate;


    public List<User> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        List<User> users = hibernateTemplate.loadAll(User.class);
        users.forEach(System.out::println);
        return users;
    }
}
