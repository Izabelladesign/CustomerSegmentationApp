package service;

import db.SqlRunner;
import db.DBConnection;

import java.sql.Connection;

public class RfmService {

    public void recompute() throws Exception {
        Connection conn = DBConnection.get();
        SqlRunner.runFile(conn, "sql/rfm/rfm_refresh.sql");
    }
}