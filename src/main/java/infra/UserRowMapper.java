package infra;

import domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUserName(resultSet.getString("user_name"));
        user.setIsDeleted(resultSet.getBoolean("is_deleted"));
        user.setCreatedAt(resultSet.getDate("created_at"));

        return user;
    }
}
