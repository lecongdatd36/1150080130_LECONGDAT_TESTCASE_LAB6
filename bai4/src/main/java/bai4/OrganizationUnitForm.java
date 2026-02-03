package bai4;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.nio.file.Path;

public class OrganizationUnitForm extends JFrame {
    private final JTextField unitIdField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JTextArea descriptionArea = new JTextArea(4, 20);
    private final JLabel messageLabel = new JLabel(" ");

    public OrganizationUnitForm() {
        setTitle("Add Organization Unit");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(560, 420);
        setLocationRelativeTo(null);

        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(new EmptyBorder(16, 16, 16, 16));
        setContentPane(content);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Add Organization Unit");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        formPanel.add(title);
        formPanel.add(Box.createVerticalStrut(12));

        formPanel.add(labelFor("Unit Id"));
        formPanel.add(unitIdField);
        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(labelFor("Name*"));
        formPanel.add(nameField);
        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(labelFor("Description"));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        formPanel.add(scrollPane);

        formPanel.add(Box.createVerticalStrut(10));
        JLabel hint = new JLabel("This unit will be added under Organization");
        hint.setForeground(new Color(120, 120, 120));
        formPanel.add(hint);

        formPanel.add(Box.createVerticalStrut(6));
        JLabel required = new JLabel("* Required");
        required.setForeground(new Color(120, 120, 120));
        formPanel.add(required);

        content.add(formPanel, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        messageLabel.setForeground(new Color(200, 0, 0));
        footer.add(messageLabel, BorderLayout.WEST);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Cancel");
        JButton saveButton = new JButton("Save");
        buttons.add(cancelButton);
        buttons.add(saveButton);
        footer.add(buttons, BorderLayout.EAST);
        content.add(footer, BorderLayout.SOUTH);

        cancelButton.addActionListener(e -> clearForm());
        saveButton.addActionListener(e -> onSave());
    }

    private JLabel labelFor(String text) {
        JLabel label = new JLabel(text);
        label.setBorder(new EmptyBorder(0, 0, 4, 0));
        return label;
    }

    private void clearForm() {
        unitIdField.setText("");
        nameField.setText("");
        descriptionArea.setText("");
        messageLabel.setText(" ");
    }

    private void onSave() {
        String unitId = unitIdField.getText().trim();
        String name = nameField.getText().trim();
        String description = descriptionArea.getText().trim();

        ValidationResult validation = OrganizationUnitValidator.validate(unitId, name, description);
        if (!validation.isValid()) {
            messageLabel.setForeground(new Color(200, 0, 0));
            messageLabel.setText(validation.getMessage());
            return;
        }

        try {
            Path propsPath = Path.of("bai4", "db.properties");
            DbConfig config = new DbConfig(propsPath.toString());
            OrganizationUnitDao dao = new OrganizationUnitDao(config);

            if (dao.existsByUnitId(unitId)) {
                messageLabel.setText("Unit Id already exists");
                return;
            }

            dao.insert(new OrganizationUnit(unitId, name, description.isEmpty() ? null : description));
            messageLabel.setForeground(new Color(0, 128, 0));
            messageLabel.setText("Saved successfully");
        } catch (Exception ex) {
            messageLabel.setForeground(new Color(200, 0, 0));
            messageLabel.setText("DB error: " + ex.getMessage());
        }
    }
}
