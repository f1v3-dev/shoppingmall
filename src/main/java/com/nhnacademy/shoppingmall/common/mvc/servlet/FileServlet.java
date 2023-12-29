package com.nhnacademy.shoppingmall.common.mvc.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100,
        location = "/Users/seungjo/Desktop/shoppingmall/src/main/webapp/resources/images"
)
@WebServlet(name = "fileServlet", urlPatterns = {"/admin/product/register", "/admin/product/update"})
public class FileServlet extends HttpServlet {

    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String UPLOAD_DIR = "/Users/seungjo/Desktop/shoppingmall/src/main/webapp/resources/images";

    @Override
    public void init() throws ServletException {
        String path = System.getProperty("user.dir");
        log.debug("현재 작업 경로 : " + path);
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        try {
            Part filePart = null;
            String modelNumber = null;
            for (Part part : req.getParts()) {
                String contentDisposition = part.getHeader(CONTENT_DISPOSITION);

                if (contentDisposition.contains("filename=")) {
                    filePart = part;
                } else if (contentDisposition.contains("model_number")) {
                    modelNumber = req.getParameter(part.getName());
                }
            }

            String fileName = null;
            if (filePart.getSize() > 0) {
                fileName = modelNumber + "_" + extractFileName(filePart.getHeader(CONTENT_DISPOSITION));
                filePart.write(UPLOAD_DIR + "/" + fileName);
                filePart.delete();
            }

            req.setAttribute("product_image", fileName);

            String requestURI = req.getRequestURI();
            if (requestURI.contains("register")) {
                req.getRequestDispatcher(requestURI + ".do").forward(req, resp);
            } else if (requestURI.contains("update")) {
                req.getRequestDispatcher(requestURI + ".do").forward(req, resp);
            } else {
                resp.sendRedirect("/admin/product/register.do");
                throw new RuntimeException("잘못된 요청입니다.");
            }
        } catch (Exception e) {
            log.debug("error = {}", e.getMessage());
            resp.sendRedirect("/admin/product/register.do");
            throw new RuntimeException(e.getMessage());
        }
    }

    private String extractFileName(String contentDisposition) {
        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
}
