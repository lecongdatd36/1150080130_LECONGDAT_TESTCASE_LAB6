package bai6;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserManagementForm form = new UserManagementForm();
            form.setVisible(true);
        });
    }
}
