package com.technokratos.oris_semectrovka_01.listener;

import com.technokratos.oris_semectrovka_01.connection.DBConnection;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ServletContextListenerImpl implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        try {
            DBConnection.init();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
