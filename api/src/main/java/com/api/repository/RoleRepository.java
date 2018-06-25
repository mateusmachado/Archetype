package com.api.repository;

import com.api.model.Role;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

@Repository
public class RoleRepository extends BaseRepository<Role> {

    @Override
    public String tableName() {
        return Role.TABLE_NAME;
    }

    @Override
    public String[] columnNames() {
        return new String[] {
                "name", "permissions", "created_at", "updated_at"
        };
    }

    @Override
    public int[] columnTypes() {
        return new int[] {
                Types.VARCHAR, Types.CLOB, Types.TIMESTAMP, Types.TIMESTAMP
        };
    }

    @Override
    public Role convert(ResultSet rs) throws SQLException {
        Role role = new Role();
        role.setId(rs.getLong("id"));
        role.setName(rs.getString("name"));
        role.setPermissions(rs.getString("permissions"));
        role.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        role.setCreatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return role;
    }

}
