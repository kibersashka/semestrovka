package com.technokratos.oris_semectrovka_01.controller;

import com.technokratos.oris_semectrovka_01.entity.User;
import com.technokratos.oris_semectrovka_01.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/usercheck")
public class UserCheckController extends HttpServlet {
    private UserService userService  = new UserService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        System.out.println(session);

        String resource = "/login.ftl";

        if(session == null || session.getAttribute("user") == null) {

            User user = new User();

            user.setLogin(request.getParameter("login"));
            user.setPassword(request.getParameter("password"));
            user.setName(request.getParameter("name"));
            user.setEmail(request.getParameter("email"));

            if(userService.loginUser(user)){
                session = request.getSession();
                session.setAttribute("user", user.getLogin());
                resource = "/index.ftl";

            } else {
                //request.setAttribute("errorMessage", "Invalid username or password");
                resource = "/login.ftl";
            }
        }

        request.getRequestDispatcher(resource).forward(request, response);


    }
}
