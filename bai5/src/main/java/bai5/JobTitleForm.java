package bai5;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;

public class JobTitleForm extends JFrame {
    private final JTextField jobCodeField = new JTextField();
    private final JTextField jobTitleField = new JTextField();
    private final JTextArea descriptionArea = new JTextArea(4, 20);
    private final JTextField specificationField = new JTextField();
    private final JTextArea noteArea = new JTextArea(3, 20);
    private final JLabel messageLabel = new JLabel(" ");

    public JobTitleForm() {
        setLookAndFeel();
        setTitle("Add Job Title");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(720, 560);
        setLocationRelativeTo(null);

        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(new EmptyBorder(20, 20, 20, 20));
        content.setBackground(Color.WHITE);
        setContentPane(content);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Add Job Title");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        formPanel.add(title);
        formPanel.add(Box.createVerticalStrut(12));

        formPanel.add(labelFor("Job Code"));
        styleField(jobCodeField);
        formPanel.add(jobCodeField);
        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(labelFor("Job Title"));
        styleField(jobTitleField);
        formPanel.add(jobTitleField);
        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(labelFor("Job Description"));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        styleArea(descriptionArea);
        formPanel.add(wrapScroll(descriptionArea));
        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(labelFor("Job Specification"));
        JPanel fileRow = new JPanel(new BorderLayout(8, 0));
        fileRow.setBackground(Color.WHITE);
        specificationField.setEditable(false);
        styleField(specificationField);
        JButton browseButton = new JButton("Browse");
        styleSecondaryButton(browseButton);
        fileRow.add(specificationField, BorderLayout.CENTER);
        fileRow.add(browseButton, BorderLayout.EAST);
        formPanel.add(fileRow);
        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(labelFor("Note"));
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        styleArea(noteArea);
        formPanel.add(wrapScroll(noteArea));
        formPanel.add(Box.createVerticalStrut(8));

        JLabel required = new JLabel("* Required");
        required.setForeground(new Color(120, 120, 120));
        formPanel.add(required);

        content.add(formPanel, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(Color.WHITE);
        messageLabel.setForeground(new Color(200, 0, 0));
        footer.add(messageLabel, BorderLayout.WEST);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setBackground(Color.WHITE);
        JButton cancelButton = new JButton("Cancel");
        JButton saveButton = new JButton("Save");
        styleSecondaryButton(cancelButton);
        stylePrimaryButton(saveButton);
        buttons.add(cancelButton);
        buttons.add(saveButton);
        footer.add(buttons, BorderLayout.EAST);
        content.add(footer, BorderLayout.SOUTH);

        cancelButton.addActionListener(e -> clearForm());
        saveButton.addActionListener(e -> onSave());
        browseButton.addActionListener(e -> onBrowse());
    }

    private JLabel labelFor(String text) {
        JLabel label = new JLabel(text);
        label.setBorder(new EmptyBorder(0, 0, 4, 0));
        label.setForeground(new Color(80, 80, 80));
        return label;
    }

    private JScrollPane wrapScroll(JTextArea area) {
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        return scrollPane;
    }

    private void styleField(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(6, 8, 6, 8)));
        field.setBackground(Color.WHITE);
    }

    private void styleArea(JTextArea area) {
        area.setBorder(new EmptyBorder(6, 8, 6, 8));
        area.setBackground(Color.WHITE);
    }

    private void stylePrimaryButton(JButton button) {
        button.setBackground(new Color(76, 175, 80));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    private void styleSecondaryButton(JButton button) {
        button.setBackground(new Color(240, 240, 240));
        button.setForeground(new Color(60, 60, 60));
        button.setFocusPainted(false);
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

    private void clearForm() {
        jobCodeField.setText("");
        jobTitleField.setText("");
        descriptionArea.setText("");
        specificationField.setText("");
        noteArea.setText("");
        messageLabel.setText(" ");
        messageLabel.setForeground(new Color(200, 0, 0));
    }

    private void onBrowse() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            specificationField.setText(file.getAbsolutePath());
        }
    }

    private void onSave() {
        String code = jobCodeField.getText().trim();
        String title = jobTitleField.getText().trim();
        String description = descriptionArea.getText().trim();
        String note = noteArea.getText().trim();
        String filePath = specificationField.getText().trim();

        ValidationResult validation = JobTitleValidator.validate(code, title, description, note);
        if (!validation.isValid()) {
            messageLabel.setText(validation.getMessage());
            return;
        }

        if (!filePath.isEmpty() && !isValidFile(filePath)) {
            messageLabel.setText("Job Specification must be PDF/DOC/DOCX and <= 5MB");
            return;
        }

        try {
            Path propsPath = Path.of("bai5", "db.properties");
            DbConfig config = new DbConfig(propsPath.toString());
            JobTitleDao dao = new JobTitleDao(config);

            if (dao.existsByJobCode(code)) {
                messageLabel.setText("Job Code already exists");
                return;
            }

            JobTitle jobTitle = new JobTitle(code, title,
                    description.isEmpty() ? null : description,
                    filePath.isEmpty() ? null : filePath,
                    note.isEmpty() ? null : note);
            dao.insert(jobTitle);
            messageLabel.setForeground(new Color(0, 128, 0));
            messageLabel.setText("Saved successfully");
        } catch (Exception ex) {
            messageLabel.setForeground(new Color(200, 0, 0));
            messageLabel.setText("DB error: " + ex.getMessage());
        }
    }

    private boolean isValidFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        String name = file.getName().toLowerCase();
        boolean validExt = name.endsWith(".pdf") || name.endsWith(".doc") || name.endsWith(".docx");
        boolean validSize = file.length() <= 5L * 1024 * 1024;
        return validExt && validSize;
    }
}
