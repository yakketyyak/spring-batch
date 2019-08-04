package fr.pb.test.batch.infra;

import fr.pb.test.batch.domain.User;
import lombok.Data;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

@Data
public class UserWriter implements ItemWriter<List<User>> {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
                namedParameterJdbcTemplate.update(QUERY, in);
            });

        });
    }
}
