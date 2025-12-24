package com.example.elearning.web;

import com.example.elearning.model.CourseModule;
import com.example.elearning.model.Resource;
import com.example.elearning.repository.CommentDao;
import com.example.elearning.repository.ModuleDao;
import com.example.elearning.repository.ResourceDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ModuleServlet extends HttpServlet {

    private final ModuleDao moduleDao = new ModuleDao();
    private final ResourceDao resourceDao = new ResourceDao();
    private final CommentDao commentDao = new CommentDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }
        long moduleId = Long.parseLong(pathInfo.substring(1));
        try {
            CourseModule module = moduleDao.findById(moduleId);
            if (module == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            List<Resource> resources = resourceDao.findByModule(moduleId);
            // Lazy map of comments per resource to keep JSP simple
            var commentMap = new java.util.HashMap<Long, java.util.List<com.example.elearning.model.Comment>>();
            for (Resource resource : resources) {
                commentMap.put(resource.getId(), commentDao.findByResource(resource.getId()));
            }
            req.setAttribute("module", module);
            req.setAttribute("resources", resources);
            req.setAttribute("commentsByResource", commentMap);
            req.getRequestDispatcher("/WEB-INF/views/module.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Impossible de charger le module", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/dashboard");
    }
}

