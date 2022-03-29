package ru.shaldin.sd.refactoring.servlet;

import ru.shaldin.sd.refactoring.sql.Queries;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QueryServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        try {
            htmlResponse.init(response);
            switch (command) {
                case "max":
                    htmlResponse.addToBody("<h1>Product with max price: </h1>\r\n").extractList(Queries.getMax()).wrapResponse();
                    break;
                case "min":
                    htmlResponse.addToBody("<h1>Product with min price: </h1>\r\n").extractList(Queries.getMin()).wrapResponse();
                    break;
                case "sum":
                    htmlResponse.addToBody("Summary price: \r\n").extractLong(Queries.getSum()).wrapResponse();
                    break;
                case "count":
                    htmlResponse.addToBody("Number of products: \r\n").extractLong(Queries.getCount()).wrapResponse();
                    break;
                default:
                    htmlResponse.addToBody("Unknown command: " + command);
                    break;
            }
            sendResponse();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
