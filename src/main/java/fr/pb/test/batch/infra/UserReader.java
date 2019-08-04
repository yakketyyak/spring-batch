package fr.pb.test.batch.infra;

import fr.pb.test.batch.domain.User;
import lombok.Data;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

@Data
public class UserReader implements ItemReader<List<User>> {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<User> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String QUERY = "SELECT * FROM user";
        List<User> users = namedParameterJdbcTemplate.query(QUERY, new UserRowMapper());
        users.forEach(System.out::println);
        return users;
    }
}
