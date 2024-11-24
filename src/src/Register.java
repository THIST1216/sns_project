package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register {
    public Register() {
        // Create the Register frame
        JFrame registerFrame = new JFrame("Register");
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerFrame.setSize(400, 600);
        registerFrame.setLayout(null);
        registerFrame.getContentPane().setBackground(Color.BLACK);

        // Title label
        JLabel titleLabel = new JLabel("Register");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(140, 50, 200, 30);
        registerFrame.add(titleLabel);

        // Name label and input
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(50, 120, 300, 20);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        registerFrame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(50, 145, 300, 30);
        registerFrame.add(nameField);

        // ID label and input
        JLabel idLabel = new JLabel("ID");
        idLabel.setForeground(Color.WHITE);
        idLabel.setBounds(50, 190, 300, 20);
        idLabel.setFont(new Font("Arial", Font.BOLD, 14));
        registerFrame.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(50, 215, 300, 30);
        registerFrame.add(idField);

        // Password label and input
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(50, 260, 300, 20);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        registerFrame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(50, 285, 300, 30);
        registerFrame.add(passwordField);

        // Register button
        JButton submitButton = new JButton("Register");
        submitButton.setBounds(50, 340, 300, 40);
        submitButton.setBackground(new Color(138, 43, 226)); // Purple color
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerFrame.add(submitButton);

        // Back to Login button
        JButton backButton = new JButton("Back to Login");
        backButton.setBounds(50, 400, 300, 40);
        backButton.setBackground(new Color(72, 61, 139)); // Dark purple color
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerFrame.add(backButton);

        // ActionListener for Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerFrame.dispose(); // Close the register frame
                new LogIn(); // Reopen the Login frame
            }
        });

        // Show the Register frame
        registerFrame.setVisible(true);
    }
}
