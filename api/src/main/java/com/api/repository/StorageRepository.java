package com.api.repository;

import com.api.model.Storage;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

@Repository
public class StorageRepository extends BaseRepository<Storage> {

    public Storage findByFileHash(String fileHash) {
        return this.find("file_hash = ?", new Object[]{ fileHash });
    }

    @Override
    public String tableName() {
        return Storage.TABLE_NAME;
    }

    @Override
    public String[] columnNames() {
        return new String[] {
                "file_hash", "file_name", "file_size", "file_type", "original_file_name", "created_at"
        };
    }

    @Override
    public int[] columnTypes() {
        return new int[] {
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP_WITH_TIMEZONE
        };
    }

    @Override
    public Storage convert(ResultSet rs) throws SQLException {
        Storage storage = new Storage();
        return storage;
    }

}
