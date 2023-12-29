package com.nhnacademy.shoppingmall.common.mvc.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.controller.register.CheckController;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(name = "checkServlet", urlPatterns = {"/signup/checkDuplicate"})
public class CheckServlet extends HttpServlet {

    class Response {
        private String notExists;

        public Response() {
        }

        public Response(String exists) {
            this.notExists = exists;
        }

        public String getNotExists() {
            return notExists;
        }

        public void setNotExists(String notExists) {
            this.notExists = notExists;
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            DbConnectionThreadLocal.initialize();

            String userId = req.getParameter("userId");

            log.debug("userId = {}", userId);

            CheckController checkController = new CheckController();

            // 존재하지 않다면 true
            boolean notExists = checkController.checkDuplicate(userId);
            log.debug("exists = {}", notExists);

            ObjectMapper objectMapper = new ObjectMapper();

            if (notExists) {
                String response = objectMapper.writeValueAsString(new Response("true"));
                resp.getWriter().write(response);
            } else {
                String response = objectMapper.writeValueAsString(new Response("false"));
                resp.getWriter().write(response);
            }
        } catch (Exception e) {
            log.error("error = {}", e);
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException(e.getMessage());
        } finally {
            DbConnectionThreadLocal.reset();
        }
    }

}
