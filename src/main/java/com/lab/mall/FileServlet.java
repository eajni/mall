package com.lab.mall;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

@WebServlet("/images/*")
public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String filename = URLDecoder.decode(req.getPathInfo().substring(1), "UTF-8");
        File file = new File("c:/workspace_spring/mall/src/main/resources/static/images/", filename);

        resp.setHeader("Content-Type", getServletContext().getMimeType(filename));
        resp.setHeader("Content-length", String.valueOf(file.length()));
        resp.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), resp.getOutputStream());

    }
}