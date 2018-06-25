package com.api.repository;

import com.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

@Repository
public class UserRepository extends BaseRepository<User> {

    @Autowired
    @Qualifier("writeJdbcTemplate")
    JdbcTemplate writeJdbcTemplate;

    public User findByUserName(String userName) {
        return this.find("user_name = ?", new Object[]{ userName });
    }

    @Override
    public String tableName() {
        return User.TABLE_NAME;
    }

    @Override
    public String[] columnNames() {
        return new String[] {
                "user_name", "role_id", "encrypted_password", "state", "created_at", "updated_at"
        };
    }

    @Override
    public int[] columnTypes() {
        return new int[] {
                Types.VARCHAR, Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP
        };
    }

    @Override
    public User convert(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setRoleId(rs.getLong("role_id"));
        user.setUserName(rs.getString("user_name"));
        user.setEncryptedPassword(rs.getString("encrypted_password"));
        user.setState(User.State.valueOf(rs.getString("state")));
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        user.setCreatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return user;
    }

}
