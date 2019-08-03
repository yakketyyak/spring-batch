package infra;

import domain.User;
import lombok.Data;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.util.List;

@Data
public class UserWriter implements ItemWriter<List<User>> {

    private HibernateTemplate hibernateTemplate;

    @Override
    public void write(List<? extends List<User>> users) throws Exception {
        System.out.println("WRITER");
        users.forEach(System.out::println);
        users.forEach(user->{
            user.forEach(u->{
                hibernateTemplate.update(u);
            });

        });
    }
}
