package com.example.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class RequestLogService {

    private static final String LOG_FILE =
            "C:\\wildfly-39.0.1.Final\\standalone\\log\\http-requests.log";

    public void log(LoggedRequest req) throws IOException {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            w.write("=== " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " ===");
            w.newLine();
            w.write("Method: " + req.method());
            w.newLine();
            w.write("URI: " + req.uri());
            w.newLine();
            w.write("QueryString: " + req.queryString());
            w.newLine();
            w.write("RemoteAddr: " + req.remoteAddr());
            w.newLine();
            w.write("Headers:");
            w.newLine();
            for (Map.Entry<String, String> h : req.headers().entrySet()) {
                w.write("  " + h.getKey() + "=" + h.getValue());
                w.newLine();
            }
            if (req.body() != null && !req.body().isEmpty()) {
                w.write("Body:");
                w.newLine();
                w.write(req.body());
                w.newLine();
            }
            w.write("---");
            w.newLine();
        }
    }
}