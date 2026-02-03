package bai4;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OrganizationUnitForm form = new OrganizationUnitForm();
            form.setVisible(true);
        });
    }
}
