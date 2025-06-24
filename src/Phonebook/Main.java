package Phonebook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    public static void main(String[] args) {
        PhonebookManager manager = new PhonebookManager();

        // Frame setup
        JFrame frame = new JFrame("📞 Colorful Phonebook App");
        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(240, 248, 255)); // AliceBlue

        // ---------- Input Panel ----------
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Contact Details"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nameField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField emailField = new JTextField(20);

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font inputFont = new Font("Arial", Font.PLAIN, 14);

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel nameLbl = new JLabel("Name:");
        nameLbl.setFont(labelFont);
        inputPanel.add(nameLbl, gbc);
        gbc.gridx = 1;
        nameField.setFont(inputFont);
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel phoneLbl = new JLabel("Phone:");
        phoneLbl.setFont(labelFont);
        inputPanel.add(phoneLbl, gbc);
        gbc.gridx = 1;
        phoneField.setFont(inputFont);
        inputPanel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel emailLbl = new JLabel("Email:");
        emailLbl.setFont(labelFont);
        inputPanel.add(emailLbl, gbc);
        gbc.gridx = 1;
        emailField.setFont(inputFont);
        inputPanel.add(emailField, gbc);

        frame.add(inputPanel, BorderLayout.NORTH);

        // ---------- Button Panel ----------
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(224, 255, 255)); // LightCyan

        JButton addBtn = new JButton("Add");
        JButton searchBtn = new JButton("Search");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        JButton showAllBtn = new JButton("Show All");

        Font btnFont = new Font("Arial", Font.BOLD, 13);
        for (JButton btn : new JButton[]{addBtn, searchBtn, editBtn, deleteBtn, showAllBtn}) {
            btn.setBackground(new Color(173, 216, 230)); // LightBlue
            btn.setFont(btnFont);
            btn.setFocusPainted(false);
            buttonPanel.add(btn);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);

        // ---------- Output Area ----------
        JTextArea output = new JTextArea(12, 50);
        output.setEditable(false);
        output.setFont(new Font("Monospaced", Font.PLAIN, 14));
        output.setBackground(new Color(255, 253, 208)); // Cream
        output.setForeground(Color.DARK_GRAY);
        output.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(output);
        scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "📋 Output"));
        scroll.setPreferredSize(new Dimension(600, 200));

        frame.add(scroll, BorderLayout.SOUTH);

        // ---------- Functionality ----------
        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();

            if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "⚠️ Please fill in all fields.");
            } else if (!phone.matches("\\d{11}")) {
                JOptionPane.showMessageDialog(frame, "❌ Invalid phone number.\nPhone must be 11 digits.");
            } else if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
                JOptionPane.showMessageDialog(frame, "❌ Invalid email address.");
            } else {
                manager.addContact(new Contact(name, phone, email));
                JOptionPane.showMessageDialog(frame, "✅ Contact Added.");
                nameField.setText(""); phoneField.setText(""); emailField.setText("");
            }

        });

        searchBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            Contact c = manager.search(name);
            if (c != null) {
                output.setText("🔎 Contact Found:\n\n" +
                        "Name : " + c.name + "\n" +
                        "Phone: " + c.phone + "\n" +
                        "Email: " + c.email);
            } else {
                output.setText("❌ Contact not found.");
            }
        });

        editBtn.addActionListener(e -> {
            manager.edit(nameField.getText().trim(), phoneField.getText().trim(), emailField.getText().trim());
            JOptionPane.showMessageDialog(frame, "✏️ Contact Updated.");
        });

        deleteBtn.addActionListener(e -> {
            manager.delete(nameField.getText().trim());
            JOptionPane.showMessageDialog(frame, "🗑️ Contact Deleted.");
            nameField.setText(""); phoneField.setText(""); emailField.setText("");
        });

        showAllBtn.addActionListener(e -> {
            String all = manager.getAllContacts();
            if (all.isEmpty()) {
                output.setText("📭 No contacts yet.");
            } else {
                output.setText("📃 All Contacts:\n\n" + all);
            }
        });

        frame.setLocationRelativeTo(null); // center window
        frame.setVisible(true);
    }
}
