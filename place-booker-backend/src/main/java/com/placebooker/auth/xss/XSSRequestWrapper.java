package com.placebooker.auth.xss;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.owasp.encoder.Encode;

public class XSSRequestWrapper extends HttpServletRequestWrapper {
    public XSSRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = sanitizeInput(values[i]);
        }
        return encodedValues;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ServletInputStream originalInputStream = super.getInputStream();
        String requestBody = new String(originalInputStream.readAllBytes());

        // Sanitize the JSON body
        String sanitizedBody = sanitizeInput(requestBody);

        return new ServletInputStream() {
            private final ByteArrayInputStream byteArrayInputStream =
                    new ByteArrayInputStream(sanitizedBody.getBytes());

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {}
        };
    }

    /*
    HTML encoding is a technique used to convert special characters
    into their corresponding HTML entities. This is commonly done
    to prevent these characters from being interpreted as HTML markup,
    which could potentially lead to security vulnerabilities like cross-site scripting (XSS) attacks.
     */
    private String sanitizeInput(String string) {
        return Encode.forHtmlContent(string);
    }
}
