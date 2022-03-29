package ru.shaldin.sd.refactoring.servlet;

import ru.shaldin.sd.refactoring.sql.Queries;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetProductsServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            htmlResponse.init(response);
            htmlResponse.extractList(Queries.selectAll()).wrapResponse();
            sendResponse();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
