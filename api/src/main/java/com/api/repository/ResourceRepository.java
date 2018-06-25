package com.api.repository;

import com.api.model.Permission;
import com.api.model.Resource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

@Repository
public class ResourceRepository extends BaseRepository<Resource> {

    @Override
    public String tableName() {
        return Resource.TABLE_NAME;
    }

    @Override
    public String[] columnNames() {
        return new String[] {
                "entity_name", "menu_id", "permission"
        };
    }

    @Override
    public int[] columnTypes() {
        return new int[] {
                Types.VARCHAR, Types.BIGINT, Types.VARCHAR
        };
    }

    @Override
    public Resource convert(ResultSet rs) throws SQLException {
        Resource resource = new Resource();
        resource.setId(rs.getLong("id"));
        resource.setEntityName(rs.getString("entity_name"));
        resource.setMenuId(rs.getLong("menu_id"));
        resource.setPermission(Permission.valueOf(rs.getString("permission")));
        return resource;
    }

}
