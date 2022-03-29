package ru.shaldin.sd.refactoring.servlet;

import ru.shaldin.sd.refactoring.sql.Queries;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));

        try {
            Queries.insert(name, price);
            htmlResponse.init(response);
            htmlResponse.addToBody("OK");
            sendResponse();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
