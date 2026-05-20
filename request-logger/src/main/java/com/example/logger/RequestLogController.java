package com.example.logger;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/log-request")
public class RequestLogController extends HttpServlet {

    private final RequestLogService service = new RequestLogService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String> headers = new LinkedHashMap<>();
        Enumeration<String> names = req.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            headers.put(name, req.getHeader(name));
        }

        StringBuilder bodyBuilder = new StringBuilder();
        String line;
        try (var reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                bodyBuilder.append(line).append(System.lineSeparator());
            }
        }

        LoggedRequest logged = new LoggedRequest(
                req.getMethod(),
                req.getRequestURI(),
                req.getQueryString(),
                req.getRemoteAddr(),
                headers,
                bodyBuilder.toString().strip()
        );

        service.log(logged);

        resp.setContentType("text/plain");
        resp.getWriter().write("Request logged.");
    }
}