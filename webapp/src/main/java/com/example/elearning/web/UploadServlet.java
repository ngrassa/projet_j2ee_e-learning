package com.example.elearning.web;

import com.example.elearning.repository.ModuleDao;
import com.example.elearning.repository.ResourceDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@MultipartConfig
public class UploadServlet extends HttpServlet {

    private final ModuleDao moduleDao = new ModuleDao();
    private final ResourceDao resourceDao = new ResourceDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("modules", moduleDao.findAll());
            req.getRequestDispatcher("/WEB-INF/views/upload.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Impossible de charger les modules", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long moduleId = Long.parseLong(req.getParameter("moduleId"));
        String title = req.getParameter("title");
        Part filePart = req.getPart("file");
        String uploader = (String) req.getSession().getAttribute("username");

        if (filePart == null || filePart.getSize() == 0) {
            req.setAttribute("error", "Veuillez sélectionner un fichier PDF.");
            doGet(req, resp);
            return;
        }

        String uploadsDir = getServletContext().getRealPath("/uploads");
        Files.createDirectories(Path.of(uploadsDir));

        String originalName = Path.of(filePart.getSubmittedFileName()).getFileName().toString();
        String safeName = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()) + "-" + originalName;
        Path destination = Path.of(uploadsDir, safeName);
        filePart.write(destination.toString());

        String publicPath = req.getContextPath() + "/uploads/" + safeName;

        try {
            resourceDao.add(moduleId, title, publicPath, uploader);
            resp.sendRedirect(req.getContextPath() + "/modules/" + moduleId);
        } catch (SQLException e) {
            throw new ServletException("Impossible d'enregistrer le fichier", e);
        }
    }
}

