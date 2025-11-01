package com.technokratos.oris_semectrovka_01.service;

import com.technokratos.oris_semectrovka_01.DAO.UserDAOImpl;
import com.technokratos.oris_semectrovka_01.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;
import java.util.Optional;

public class UserService {
    private UserDAOImpl userDAO = new UserDAOImpl();
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public void registrationUser(User user) throws SQLException {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userDAO.save(user);
    }
    public boolean loginUser(User user)  {
        try {
            Optional<User> userDB = userDAO.find(user);
            System.out.println(userDB);
            if(userDB.isPresent()) {


                String passwordIn = user.getPassword();
                String hashedPassword = userDB.get().getPassword();

                return passwordEncoder.matches(passwordIn, hashedPassword);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public void changePassword(User user) {
        try {
            userDAO.update(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
