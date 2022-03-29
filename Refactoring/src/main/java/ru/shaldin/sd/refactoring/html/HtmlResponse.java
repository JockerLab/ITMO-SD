package ru.shaldin.sd.refactoring.html;

import ru.shaldin.sd.refactoring.domain.Product;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class HtmlResponse {
    private HttpServletResponse response;
    private String responseBody;

    public void init(HttpServletResponse response) {
        this.response = response;
        this.responseBody = "";
    }

    public HtmlResponse setStatus() {
        response.setStatus(HttpServletResponse.SC_OK);
        return this;
    }

    public HtmlResponse setContentType() {
        response.setContentType("text/html");
        return this;
    }

    public HtmlResponse write() throws IOException {
        response.getWriter().println(responseBody);
        return this;
    }

    public HtmlResponse addToBody(String responseBody) throws IOException {
        this.responseBody = this.responseBody + responseBody;
        return this;
    }

    public HtmlResponse wrapResponse() {
        responseBody = "<html><body>\r\n" + responseBody + "</body></html>";
        return this;
    }

    public HtmlResponse extractList(List<Product> data) {
        StringBuilder result = new StringBuilder();
        for (Product product : data) {
            result.append(product.toString()).append("</br>\r\n");
        }
        responseBody = responseBody + result.toString();
        return this;
    }

    public HtmlResponse extractLong(Optional<Long> data) {
        if (data.isPresent()) {
            responseBody = responseBody + data.get() + "\r\n";
        }
        return this;
    }
}
