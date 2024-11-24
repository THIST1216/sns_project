package src;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WriteArticle extends JPanel {
    private JTextArea articleInput;
    private JComboBox<String> visibilitySelector;
    private JLabel photoLabel;
    private JLabel previewImageLabel;
    private JLabel charCountLabel;
    private String photoPath = null;

    public WriteArticle(MainUI mainFrame, JPanel mainContentPanel, CardLayout cardLayout) {
        JFrame frame = new JFrame("★ Create a New Article ★");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(15, 20, 25));
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel headerLabel = new JLabel("♣ Create New Article");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.WEST);

        JButton backButton = new JButton("Back");
        styleButton(backButton);
        backButton.addActionListener(e -> {
            cardLayout.show(mainContentPanel, "Home");
            frame.dispose();
        });
        headerPanel.add(backButton, BorderLayout.EAST);
        frame.add(headerPanel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(25, 39, 52));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Center Panel for Inputs
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(new Color(30, 30, 30));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Article Input
        articleInput = new JTextArea(5, 30);
        articleInput.setFont(new Font("Arial", Font.PLAIN, 16));
        articleInput.setForeground(Color.WHITE);
        articleInput.setBackground(new Color(50, 50, 50));
        articleInput.setLineWrap(true);
        articleInput.setWrapStyleWord(true);
        articleInput.setBorder(BorderFactory.createTitledBorder("♣ Write your article"));

        // Character Count Label
        charCountLabel = new JLabel("0 / 500 characters");
        charCountLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        charCountLabel.setForeground(new Color(200, 200, 200));

        articleInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int charCount = articleInput.getText().length();
                charCountLabel.setText(charCount + " / 500 characters");
                if (charCount >= 500) {
                    e.consume(); // Prevent further typing
                    charCountLabel.setForeground(Color.RED);
                } else {
                    charCountLabel.setForeground(new Color(200, 200, 200));
                }
            }
        });

        inputPanel.add(articleInput);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(charCountLabel);

        // Visibility Selector
        visibilitySelector = new JComboBox<>(new String[]{"♣ Public", "♣ Private"});
        visibilitySelector.setFont(new Font("Arial", Font.PLAIN, 14));
        visibilitySelector.setBackground(new Color(29, 155, 240));
        visibilitySelector.setForeground(Color.WHITE);
        visibilitySelector.setBorder(BorderFactory.createTitledBorder("Visibility"));
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(visibilitySelector);

        contentPanel.add(inputPanel, BorderLayout.CENTER);

        // Photo Upload Section
        JPanel photoPanel = new JPanel(new BorderLayout());
        photoPanel.setBackground(new Color(25, 39, 52));

        JButton uploadPhotoButton = new JButton("♣ Upload Photo");
        styleButton(uploadPhotoButton);
        uploadPhotoButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                photoPath = fileChooser.getSelectedFile().getAbsolutePath();
                photoLabel.setText("Photo: " + fileChooser.getSelectedFile().getName());
                setPreviewImage(photoPath);
            }
        });

        photoLabel = new JLabel("No photo uploaded");
        photoLabel.setForeground(Color.LIGHT_GRAY);

        previewImageLabel = new JLabel();
        previewImageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        photoPanel.add(uploadPhotoButton, BorderLayout.NORTH);
        photoPanel.add(previewImageLabel, BorderLayout.CENTER);
        photoPanel.add(photoLabel, BorderLayout.SOUTH);

        contentPanel.add(photoPanel, BorderLayout.EAST);

        frame.add(contentPanel, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(15, 20, 25));
        JButton postButton = new JButton("★ Post Article ★");
        styleButton(postButton);

        postButton.addActionListener(e -> {
            String articleText = articleInput.getText().trim();
            String visibility = (String) visibilitySelector.getSelectedItem();

            if (articleText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Article cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                mainFrame.addArticleToHome(
                        "You (" + visibility + ")",
                        articleText,
                        mainFrame.getCurrentTimestamp(),
                        photoPath // 사진 경로 추가
                );
                articleInput.setText("");
                photoLabel.setText("No photo uploaded");
                previewImageLabel.setIcon(null);
                photoPath = null;
                charCountLabel.setText("0 / 500 characters");
                cardLayout.show(mainContentPanel, "Home");
                frame.dispose();
            }
        });

        footerPanel.add(postButton);
        frame.add(footerPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    /**
     * 버튼 스타일 설정
     */
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(29, 155, 240));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(26, 145, 218));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(29, 155, 240));
            }
        });
    }

    /**
     * 사진 미리보기 설정
     */
    private void setPreviewImage(String imagePath) {
        ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        previewImageLabel.setIcon(icon);
    }
}
