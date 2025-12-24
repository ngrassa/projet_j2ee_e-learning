package com.example.elearning.web;

import com.example.elearning.model.Resource;
import com.example.elearning.repository.CommentDao;
import com.example.elearning.repository.ResourceDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class CommentServlet extends HttpServlet {

    private final CommentDao commentDao = new CommentDao();
    private final ResourceDao resourceDao = new ResourceDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long resourceId = Long.parseLong(req.getParameter("resourceId"));
        String content = req.getParameter("content");
        String author = (String) req.getSession().getAttribute("username");
        if (content == null || content.isBlank()) {
            resp.sendRedirect(req.getHeader("Referer"));
            return;
        }
        try {
            commentDao.add(resourceId, author, content.trim());
            Resource resource = resourceDao.findById(resourceId);
            if (resource != null) {
                resp.sendRedirect(req.getContextPath() + "/modules/" + resource.getModuleId());
            } else {
                resp.sendRedirect(req.getContextPath() + "/dashboard");
            }
        } catch (SQLException e) {
            throw new ServletException("Impossible d'ajouter le commentaire", e);
        }
    }
}

