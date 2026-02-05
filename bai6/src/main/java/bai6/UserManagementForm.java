package bai6;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

public class UserManagementForm extends JFrame {
    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JTextField fullNameField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JLabel messageLabel = new JLabel(" ");
    private final DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Username", "Full name", "Email", "Created"}, 0);
    private DbConfig config;

    public UserManagementForm() {
        setLookAndFeel();
        setTitle("User Management");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(980, 620);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(18, 24, 50));
        setContentPane(root);

        JPanel header = buildHeader();
        JPanel sidebar = buildSidebar();
        JPanel content = buildContent();

        root.add(header, BorderLayout.NORTH);
        root.add(sidebar, BorderLayout.WEST);
        root.add(content, BorderLayout.CENTER);

        try {
            config = new DbConfig(Path.of("bai6", "db.properties").toString());
        } catch (Exception ex) {
            showError("DB config error: " + ex.getMessage());
        }

        refreshTable();
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(15, 35, 70));
        header.setBorder(new EmptyBorder(10, 16, 10, 16));
        JLabel logo = new JLabel("NGHIENTHIM");
        logo.setForeground(Color.WHITE);
        logo.setFont(logo.getFont().deriveFont(Font.BOLD, 16f));
        header.add(logo, BorderLayout.WEST);
        return header;
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(12, 20, 40));
        sidebar.setPreferredSize(new Dimension(190, 0));
        sidebar.setBorder(new EmptyBorder(16, 12, 16, 12));

        sidebar.add(navItem("Home"));
        sidebar.add(navItem("Video Management"));
        sidebar.add(navItem("User Management"));
        sidebar.add(navItem("Report Management"));
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(navItem("Logout"));
        return sidebar;
    }

    private JLabel navItem(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(180, 190, 220));
        label.setBorder(new EmptyBorder(8, 6, 8, 6));
        return label;
    }

    private JPanel buildContent() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(new EmptyBorder(20, 20, 20, 20));
        content.setBackground(new Color(18, 24, 50));

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("USER EDITION", buildEditPanel());
        tabs.addTab("USER LIST", buildListPanel());
        content.add(tabs, BorderLayout.CENTER);
        return content;
    }

    private JPanel buildEditPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(18, 24, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        addField(panel, gbc, row++, "Username", usernameField);
        addField(panel, gbc, row++, "Password", passwordField);
        addField(panel, gbc, row++, "Fullname", fullNameField);
        addField(panel, gbc, row++, "Email", emailField);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        messageLabel.setForeground(new Color(255, 120, 120));
        panel.add(messageLabel, gbc);

        gbc.gridy = row + 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(buildButtons(), gbc);
        return panel;
    }

    private JPanel buildButtons() {
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttons.setBackground(new Color(18, 24, 50));

        JButton createButton = new JButton("Create");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton resetButton = new JButton("Reset");

        stylePrimaryButton(createButton, new Color(60, 130, 255));
        stylePrimaryButton(updateButton, new Color(40, 180, 120));
        stylePrimaryButton(deleteButton, new Color(230, 90, 90));
        styleSecondaryButton(resetButton);

        createButton.addActionListener(e -> onCreate());
        updateButton.addActionListener(e -> onUpdate());
        deleteButton.addActionListener(e -> onDelete());
        resetButton.addActionListener(e -> clearForm());

        buttons.add(createButton);
        buttons.add(updateButton);
        buttons.add(deleteButton);
        buttons.add(resetButton);
        return buttons;
    }

    private JPanel buildListPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBackground(new Color(18, 24, 50));

        JTable table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);
        panel.add(scroll, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh");
        styleSecondaryButton(refreshButton);
        refreshButton.addActionListener(e -> refreshTable());
        JPanel top = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        top.setBackground(new Color(18, 24, 50));
        top.add(refreshButton);
        panel.add(top, BorderLayout.NORTH);

        return panel;
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        JLabel lbl = new JLabel(label);
        lbl.setForeground(new Color(200, 210, 230));
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        field.setPreferredSize(new Dimension(320, 28));
        styleField(field);
        panel.add(field, gbc);
    }

    private void styleField(JComponent field) {
        field.setBackground(new Color(25, 33, 66));
        field.setForeground(new Color(230, 235, 250));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 60, 95)),
                new EmptyBorder(6, 8, 6, 8)
        ));
    }

    private void stylePrimaryButton(JButton button, Color bg) {
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    private void styleSecondaryButton(JButton button) {
        button.setBackground(new Color(55, 65, 95));
        button.setForeground(new Color(220, 230, 240));
        button.setFocusPainted(false);
    }

    private void onCreate() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();

        ValidationResult result = UserValidator.validate(username, password, fullName, email);
        if (!result.isValid()) {
            showError(result.getMessage());
            return;
        }
        if (config == null) {
            showError("DB config not loaded");
            return;
        }

        try {
            UserDao dao = new UserDao(config);
            if (dao.existsByUsername(username)) {
                showError("Username already exists");
                return;
            }
            dao.insert(new User(username, password, fullName, email, null));
            showSuccess("Created successfully");
            refreshTable();
        } catch (SQLException ex) {
            showError("DB error: " + ex.getMessage());
        }
    }

    private void onUpdate() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();

        ValidationResult result = UserValidator.validate(username, password, fullName, email);
        if (!result.isValid()) {
            showError(result.getMessage());
            return;
        }
        if (config == null) {
            showError("DB config not loaded");
            return;
        }

        try {
            UserDao dao = new UserDao(config);
            boolean updated = dao.update(new User(username, password, fullName, email, null));
            if (!updated) {
                showError("Username not found");
                return;
            }
            showSuccess("Updated successfully");
            refreshTable();
        } catch (SQLException ex) {
            showError("DB error: " + ex.getMessage());
        }
    }

    private void onDelete() {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            showError("Username is required");
            return;
        }
        if (config == null) {
            showError("DB config not loaded");
            return;
        }

        try {
            UserDao dao = new UserDao(config);
            boolean deleted = dao.deleteByUsername(username);
            if (!deleted) {
                showError("Username not found");
                return;
            }
            showSuccess("Deleted successfully");
            refreshTable();
        } catch (SQLException ex) {
            showError("DB error: " + ex.getMessage());
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        if (config == null) {
            return;
        }
        try {
            UserDao dao = new UserDao(config);
            List<User> users = dao.findAll();
            for (User user : users) {
                tableModel.addRow(new Object[]{
                        user.getUsername(),
                        user.getFullName(),
                        user.getEmail(),
                        user.getCreatedAt()
                });
            }
        } catch (SQLException ex) {
            showError("DB error: " + ex.getMessage());
        }
    }

    private void clearForm() {
        usernameField.setText("");
        passwordField.setText("");
        fullNameField.setText("");
        emailField.setText("");
        messageLabel.setText(" ");
        messageLabel.setForeground(new Color(255, 120, 120));
    }

    private void showError(String message) {
        messageLabel.setForeground(new Color(255, 120, 120));
        messageLabel.setText(message);
    }

    private void showSuccess(String message) {
        messageLabel.setForeground(new Color(100, 220, 140));
        messageLabel.setText(message);
    }

    private void setLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }
    }
}
