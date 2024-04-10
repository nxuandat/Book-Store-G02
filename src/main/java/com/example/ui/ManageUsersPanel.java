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
	
	private final UserService userService;
    private final RoleService roleService;

    private DefaultTableModel tableModel;
    private JTable userTable;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<Role> roleComboBox;
    private JTextField fullnameField;
    private JTextField emailField;
    private JTextField phoneNumberField;
    private JTextField addressField;
    private JTextField genderField;
    private JCheckBox isActiveCheckbox;
    private JButton addButton;
    private JButton updateButton;
    private JButton deactivateButton;
    private JButton activateButton;
    private JTextField searchField;
    private JButton searchButton;
    private JComboBox<String> sortComboBox;
    private JButton sortButton;
	private List<Role> roles;

    public ManageUsersPanel() {
        this.userService = new UserService();
        this.roleService = new RoleService();
        initializeUI();
    }

    private void initializeUI() {
    }
    private void addOrUpdateUser(boolean isAdd) {
        try {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            Role selectedRole = (Role) roleComboBox.getSelectedItem();
            String fullname = fullnameField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneNumberField.getText();
            String address = addressField.getText();
            String gender = genderField.getText();
            boolean isActive = isActiveCheckbox.isSelected();

            if (username.isEmpty() || password.isEmpty() || selectedRole == null || fullname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User selectedUser = getSelectedUser();

            if (selectedUser == null) {
                JOptionPane.showMessageDialog(this, "Please select a user.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update the selected user with the new values
            selectedUser.setUsername(username);
            selectedUser.setPassword(password);
            selectedUser.setRoleID(selectedRole.getRoleID());
            selectedUser.setFullname(fullname);
            selectedUser.setEmail(email);
            selectedUser.setPhoneNumber(phoneNumber);
            selectedUser.setAddress(address);
            selectedUser.setGender(gender);
            selectedUser.setActive(isActive);

            if (isAdd) {
                userService.addUser(selectedUser);
                JOptionPane.showMessageDialog(this, "User added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                userService.updateUser(selectedUser);
                JOptionPane.showMessageDialog(this, "User updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }

            loadUsers();
            clearUserFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void deactivateOrActivateUser(boolean isActivate) {
        try {
            User selectedUser = getSelectedUser();

            if (selectedUser == null) {
                JOptionPane.showMessageDialog(this, "Please select a user.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            selectedUser.setActive(isActivate);
            userService.updateUser(selectedUser);

            if (isActivate) {
                JOptionPane.showMessageDialog(this, "User activated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "User deactivated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }

            loadUsers();
            clearUserFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchUsers() {
        try {
            String searchTerm = searchField.getText();

            if (searchTerm.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a search term.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<User> searchResults = userService.searchUsers(searchTerm);

            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No matching users found.", "No Results", JOptionPane.INFORMATION_MESSAGE);
            } else {
                updateTable(searchResults);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


 // Inside ManageUsersPanel class
    private void sortUsers() {
        try {
            String sortType = (String) sortComboBox.getSelectedItem();
            // Convert the sort type to uppercase for consistency
            sortType = sortType.toUpperCase();
            
            List<User> sortedUsers = userService.sortUsers(sortType);
            updateTable(sortedUsers);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


 // Update the populateUserFields method
    private void populateUserFields(int selectedRow) {
        try {
            String userId = tableModel.getValueAt(selectedRow, 0).toString();
            User user = userService.getUserById(Integer.parseInt(userId));

            usernameField.setText(user.getUsername());
            passwordField.setText(user.getPassword());
            for(Role role : roles) {
            	if(user.getRoleID() == role.getRoleID()) {
            		roleComboBox.setSelectedItem(role);
            		break;
            	}
            }
            fullnameField.setText(user.getFullname());
            emailField.setText(user.getEmail());
            phoneNumberField.setText(user.getPhoneNumber());
            addressField.setText(user.getAddress());
            genderField.setText(user.getGender());
            isActiveCheckbox.setSelected(user.isActive());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
 // Add this method to get the role name from the role ID
    private String getRoleName(int roleID) {
        try {
            Role role = roleService.getRoleById(roleID);
            return (role != null) ? role.getRoleName() : "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
