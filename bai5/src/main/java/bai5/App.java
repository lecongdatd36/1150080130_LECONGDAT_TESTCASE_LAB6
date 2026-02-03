package bai5;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JobTitleForm form = new JobTitleForm();
            form.setVisible(true);
        });
    }
}
