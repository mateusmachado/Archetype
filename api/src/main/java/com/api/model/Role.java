package com.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;
import lombok.Data;
import org.springframework.boot.json.JsonParserFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Role {

    public static final String TABLE_NAME = "roles";

    private long id;

    private String name;

    private String permissions;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public boolean hasPermission(Object entity, Object access) {
        if (Strings.isNullOrEmpty(permissions)) return false;
        Map ps = JsonParserFactory.getJsonParser().parseMap(permissions);

        String entityName;
        if (entity instanceof String) {
            entityName = (String) entity;
        } else {
            entityName = (String) entity;
        }

        String permission;
        if (access instanceof Permission) {
            permission = ((Permission) access).name();
        } else {
            permission = (String) access;
        }

        if (ps.containsKey(entityName)) {
            List<String> methodNames = (List<String>) ps.get(entityName);
            return methodNames.stream().filter(m -> m.equals(permission)).count() > 0;
        }
        return false;
    }

    @JsonIgnore
    public Map getPermissionsMap() {
        return Strings.isNullOrEmpty(permissions) ?
                new HashMap() : JsonParserFactory.getJsonParser().parseMap(permissions);
    }


    public static String getTableName() {
        return TABLE_NAME;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
