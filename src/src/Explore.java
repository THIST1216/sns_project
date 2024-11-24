package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class Explore {
    private final JPanel explorePanel;
    private final Set<String> followedUsers; // 팔로우 상태를 관리하기 위한 Set

    public Explore(JPanel mainContentPanel, CardLayout cardLayout) {
        explorePanel = new JPanel();
        explorePanel.setLayout(new BorderLayout());
        explorePanel.setBackground(new Color(25, 39, 52));
        followedUsers = new HashSet<>(); // 팔로우 상태 관리

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(15, 20, 25));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel headerLabel = new JLabel("✨ Discover New Friends ✨");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.WEST);

        JButton backButton = new JButton("Back");
        styleButton(backButton);
        backButton.addActionListener(e -> cardLayout.show(mainContentPanel, "Home"));
        headerPanel.add(backButton, BorderLayout.EAST);

        explorePanel.add(headerPanel, BorderLayout.NORTH);

        // Recommended Users Scrollable Panel
        JPanel usersPanel = new JPanel();
        usersPanel.setLayout(new GridLayout(0, 1, 10, 10));
        usersPanel.setBackground(new Color(25, 39, 52));

        JScrollPane scrollPane = new JScrollPane(usersPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());

        // 유저 추천 목록 생성
        String[][] suggestedUsers = {
                {"Alice", "Graphics Designer", "50"},
                {"Bob", "Software Engineer", "30"},
                {"Charlie", "Freelancer", "45"},
                {"Dave", "Game Developer", "25"},
                {"Eve", "AI Researcher", "10"}
        };

        for (String[] user : suggestedUsers) {
            addUserSuggestion(usersPanel, user[0], user[1], user[2]);
        }

        explorePanel.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * 유저 추천 컴포넌트를 추가하는 메서드
     */
    private void addUserSuggestion(JPanel parentPanel, String userName, String profession, String mutualFriends) {
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout());
        userPanel.setBackground(new Color(30, 30, 30));
        userPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Hover 효과 추가
        userPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                userPanel.setBackground(new Color(40, 40, 40));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                userPanel.setBackground(new Color(30, 30, 30));
            }
        });

        // 프로필 사진
        JLabel profilePic = new JLabel();
        profilePic.setIcon(new ImageIcon(new ImageIcon("Asset\\user-profile.png")
                .getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH)));

        // 사용자 정보
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(30, 30, 30));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JLabel nameLabel = new JLabel(userName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE);

        JLabel professionLabel = new JLabel(profession);
        professionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        professionLabel.setForeground(new Color(200, 200, 200));

        JLabel mutualFriendsLabel = new JLabel(mutualFriends + " mutual friends");
        mutualFriendsLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        mutualFriendsLabel.setForeground(new Color(150, 150, 150));

        infoPanel.add(nameLabel);
        infoPanel.add(professionLabel);
        infoPanel.add(mutualFriendsLabel);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(30, 30, 30));

        JButton followButton = new JButton("Follow");
        styleButton(followButton);
        followButton.addActionListener(e -> handleFollowButton(followButton, userName));

        JButton messageButton = new JButton("Message");
        styleButton(messageButton);
        messageButton.addActionListener(e -> openMessageDialog(userName));

        buttonPanel.add(followButton);
        buttonPanel.add(messageButton);

        userPanel.add(profilePic, BorderLayout.WEST);
        userPanel.add(infoPanel, BorderLayout.CENTER);
        userPanel.add(buttonPanel, BorderLayout.EAST);

        parentPanel.add(userPanel);
    }

    /**
     * 팔로우 버튼의 동작 처리
     */
    private void handleFollowButton(JButton followButton, String userName) {
        if (followedUsers.contains(userName)) {
            followedUsers.remove(userName);
            followButton.setText("Follow");
            followButton.setBackground(new Color(29, 155, 240));
        } else {
            followedUsers.add(userName);
            followButton.setText("Unfollow");
            followButton.setBackground(Color.RED);
        }
    }

    /**
     * 메시지 보내기 다이얼로그 창 열기
     */
    private void openMessageDialog(String userName) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Send a Message to " + userName);
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());
        dialog.setModal(true);

        // 메시지 입력 영역
        JTextArea messageArea = new JTextArea();
        messageArea.setFont(new Font("Arial", Font.PLAIN, 14));
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setBorder(BorderFactory.createTitledBorder("Write your message"));

        // 전송 버튼
        JButton sendButton = new JButton("Send");
        styleButton(sendButton);
        sendButton.addActionListener(e -> {
            String message = messageArea.getText().trim();
            if (message.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Message cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialog, "Message sent to " + userName + ": " + message, "Message Sent", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            }
        });

        dialog.add(new JScrollPane(messageArea), BorderLayout.CENTER);
        dialog.add(sendButton, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(null); // 화면 중앙에 다이얼로그 표시
        dialog.setVisible(true);
    }

    /**
     * 버튼 스타일 설정
     */
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(29, 155, 240));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    /**
     * Explore 패널 반환
     */
    public JPanel getExplorePanel() {
        return explorePanel;
    }

    /**
     * Custom ScrollBar UI
     */
    static class CustomScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = new Color(29, 155, 240);
        }
    }
}
