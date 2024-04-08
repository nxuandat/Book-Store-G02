package main.java.com.example.ui;

import main.java.com.example.model.Role;
import main.java.com.example.service.RoleService;
import main.java.com.example.model.User;
import main.java.com.example.service.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class ManageUsersPanel extends JPanel {

    public ManageUsersPanel() {
        this.userService = new UserService();
        this.roleService = new RoleService();
        initializeUI();
    }

    private void initializeUI() {
    }
}
