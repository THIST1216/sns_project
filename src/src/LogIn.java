package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogIn {
    public static void main(String[] args) {
        // Create the Login frame
        JFrame loginFrame = new JFrame("Log In");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 600);
        loginFrame.setLayout(null);
        loginFrame.getContentPane().setBackground(Color.BLACK);

        // Title label
        JLabel titleLabel = new JLabel("Log In");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(150, 50, 100, 30);
        loginFrame.add(titleLabel);

        // ID label and input field
        JLabel idLabel = new JLabel("ID");
        idLabel.setForeground(Color.WHITE);
        idLabel.setBounds(50, 150, 300, 20);
        idLabel.setFont(new Font("Arial", Font.BOLD, 14));
        loginFrame.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(50, 175, 300, 30);
        loginFrame.add(idField);

        // Password label and input field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(50, 220, 300, 20);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        loginFrame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(50, 245, 300, 30);
        loginFrame.add(passwordField);

        // Log In button
        JButton loginButton = new JButton("Log In");
        loginButton.setBounds(50, 300, 300, 40);
        loginButton.setBackground(new Color(138, 43, 226));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginFrame.add(loginButton);

        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(50, 360, 300, 40);
        registerButton.setBackground(new Color(72, 61, 139));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginFrame.add(registerButton);

        // ActionListener for Register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose(); // Close the login frame
                new Register(); // Open the Register screen
            }
        });

        // Show the Login frame
        loginFrame.setVisible(true);
    }
}
