package db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;

public class SqlRunner {

    public static void runFile(Connection conn, String path) throws Exception {
        String sql = Files.readString(Paths.get(path));

        // Split on semicolon
        String[] commands = sql.split(";");

        for (String cmd : commands) {
            cmd = cmd.trim();
            if (cmd.isEmpty()) continue;

            try (Statement stmt = conn.createStatement()) {
                stmt.execute(cmd);
            }
        }
    }
}