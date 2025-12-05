package db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;

public class SqlRunner {

    /**
     * Runs a SQL file. The path can be relative to the project root or absolute.
     * Tries multiple path resolution strategies to find the file.
     */
    public static void runFile(Connection conn, String path) throws Exception {
        Path sqlPath = resolvePath(path);
        String sql = Files.readString(sqlPath);

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

    /**
     * Resolves the SQL file path, trying multiple strategies:
     * 1. If absolute path, use as-is
     * 2. Try relative to current working directory
     * 3. Try relative to project root (look for sql/ directory)
     */
    private static Path resolvePath(String path) throws IOException {
        Path filePath = Paths.get(path);
        
        // If absolute path and exists, return it
        if (filePath.isAbsolute() && Files.exists(filePath)) {
            return filePath;
        }
        
        // Try relative to current working directory
        if (Files.exists(filePath)) {
            return filePath;
        }
        
        // Try relative to project root (common case: sql/ directory)
        // Look for sql directory in parent directories
        Path current = Paths.get(System.getProperty("user.dir"));
        Path sqlDir = current.resolve("sql");
        if (Files.exists(sqlDir)) {
            Path resolved = current.resolve(path);
            if (Files.exists(resolved)) {
                return resolved;
            }
        }
        
        // If still not found, try going up one level (in case running from app/ directory)
        Path parent = current.getParent();
        if (parent != null) {
            Path resolved = parent.resolve(path);
            if (Files.exists(resolved)) {
                return resolved;
            }
        }
        
        // Last attempt: try as absolute from project root
        throw new IOException("SQL file not found: " + path + 
            " (tried: " + filePath.toAbsolutePath() + ", relative to: " + current + ")");
    }
}