package com.technokratos.oris_semectrovka_01.controller;

import com.technokratos.oris_semectrovka_01.DAO.UserDAOImpl;
import com.technokratos.oris_semectrovka_01.entity.User;
import com.technokratos.oris_semectrovka_01.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/registration")
public class RegistrationController extends HttpServlet {
    private UserService userService = new UserService();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name"));
        user.setEmail(request.getParameter("email"));


        try {
            userService.registrationUser(user);


        } catch (SQLException re) {
            request.setAttribute("errorMessage", re);

            request.getRequestDispatcher("/login.ftl").forward(request, response);
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        request.setAttribute("user", user.getLogin());

        request.getRequestDispatcher("/index.ftl").forward(request, response);

    }
}
