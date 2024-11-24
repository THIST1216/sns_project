package src;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainUI {
    private JPanel homePanel; // Home 화면
    private JPanel explorePanel; // Explore 화면
    private JLabel headerLabel; // Header Label
    private final Random random = new Random(); // 작성자 랜덤 선택
    private final String[] authors = {"User1", "User2", "User3", "User4", "User5"}; // 작성자 목록
    private final Map<JButton, Integer> likeCounts = new HashMap<>(); // Like 버튼 카운트 저장
    private JFrame mainFrame;
    private JPanel mainContentPanel; // CardLayout Panel
    private CardLayout cardLayout; // 화면 전환용 CardLayout
    private JScrollPane homeScrollPane; // Home ScrollPane

    public MainUI() {
        JFrame mainFrame = new JFrame("Twitter-Like SNS");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 700);
        mainFrame.setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(15, 20, 25));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Header Label
        headerLabel = new JLabel("SNS Home");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.WEST);

        // Navigation Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(new Color(15, 20, 25));

        String[] buttons = {"Home", "Notification", "Explore", "Write Article", "Logout"};
        for (String name : buttons) {
            JButton button = new JButton(name);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setBackground(new Color(29, 155, 240));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(26, 145, 218));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(29, 155, 240));
                }
            });

            button.addActionListener(e -> handleNavigation(name));
            buttonPanel.add(button);
        }

        headerPanel.add(buttonPanel, BorderLayout.EAST);
        mainFrame.add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel with CardLayout
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);

        // Initialize Home and Explore Panels
        initializeHomePanel();
        initializeExplorePanel();

        // Add panels to CardLayout
        mainContentPanel.add(homeScrollPane, "Home");
        mainContentPanel.add(explorePanel, "Explore");

        mainFrame.add(mainContentPanel, BorderLayout.CENTER);

        mainFrame.setVisible(true);
    }

    private void handleNavigation(String name) {
        switch (name) {
            case "Home":
                headerLabel.setText("SNS Home");
                cardLayout.show(mainContentPanel, "Home");
                break;
            case "Notification":
                headerLabel.setText("SNS Notifications");
                JOptionPane.showMessageDialog(mainContentPanel, "No new notifications!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "Explore":
                headerLabel.setText("Friend Suggestions");
                Explore explore = new Explore(mainContentPanel, cardLayout);
                mainContentPanel.add(explore.getExplorePanel(), "Explore");
                cardLayout.show(mainContentPanel, "Explore");
                break;
            case "Write Article":
                headerLabel.setText("Write New Article");
                new WriteArticle(this, mainContentPanel, cardLayout);
                break;
            case "Logout":
                JOptionPane.showMessageDialog(mainContentPanel, "You have been logged out.", "Logout", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
                break;
        }
    }

    private void initializeHomePanel() {
        homePanel = new JPanel();
        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));
        homePanel.setBackground(new Color(25, 39, 52));

        homeScrollPane = new JScrollPane(homePanel);
        homeScrollPane.setBorder(null);
        homeScrollPane.setBackground(new Color(25, 39, 52));

        // Add initial articles
        for (int i = 0; i < 3; i++) {
            String author = getRandomAuthor();
            String content = getRandomArticle();
            String timestamp = getCurrentTimestamp();
            addArticleToHome(author, content, timestamp, null);
        }

        // Start auto-updating articles every 5 seconds
        startAutoPostingArticles();
    }
    
    /**
     * Method to start auto-posting articles every 5 seconds.
     */
    private void startAutoPostingArticles() {
        Timer timer = new Timer(5000, e -> {
            String author = getRandomAuthor();
            String content = getRandomArticle();
            String timestamp = getCurrentTimestamp();
            addArticleToHome(author, content, timestamp, null);
        });
        timer.setRepeats(true); // Ensure it repeats every 5 seconds
        timer.start(); // Start the timer
    }
    
    private void initializeExplorePanel() {
        explorePanel = new JPanel();
        explorePanel.setLayout(new BoxLayout(explorePanel, BoxLayout.Y_AXIS));
        explorePanel.setBackground(new Color(25, 39, 52));

        String[] users = {"User1", "User2", "User3", "User4", "User5"};

        for (String user : users) {
            JPanel userPanel = new JPanel(new BorderLayout());
            userPanel.setBackground(new Color(30, 30, 30));
            userPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

            JLabel userLabel = new JLabel(user);
            userLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            userLabel.setForeground(Color.WHITE);

            JButton followButton = new JButton("Follow");
            followButton.setBackground(new Color(29, 155, 240));
            followButton.setForeground(Color.WHITE);

            userPanel.add(userLabel, BorderLayout.WEST);
            userPanel.add(followButton, BorderLayout.EAST);

            explorePanel.add(userPanel);

            followButton.addActionListener(e -> {
                if ("Follow".equals(followButton.getText())) {
                    followButton.setText("Unfollow");
                    followButton.setBackground(Color.RED);
                    JButton dmButton = new JButton("DM");
                    dmButton.setBackground(new Color(50, 50, 200));
                    dmButton.setForeground(Color.WHITE);
                    dmButton.setFont(new Font("Arial", Font.BOLD, 12));
                    userPanel.add(dmButton, BorderLayout.CENTER);

                    dmButton.addActionListener(dmEvent -> JOptionPane.showMessageDialog(
                            explorePanel,
                            "Send a message to " + user,
                            "Direct Message",
                            JOptionPane.INFORMATION_MESSAGE
                    ));
                    explorePanel.revalidate();
                    explorePanel.repaint();
                } else {
                    followButton.setText("Follow");
                    followButton.setBackground(new Color(29, 155, 240));
                    userPanel.remove(2); // DM 버튼 제거
                    explorePanel.revalidate();
                    explorePanel.repaint();
                }
            });
        }
    }

    // 수정된 메서드 정의
    public void addArticleToHome(String author, String content, String timestamp, String photoPath) {
        JPanel article = new JPanel();
        article.setLayout(new BorderLayout());
        article.setBackground(new Color(30, 30, 30));
        article.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        JLabel articleHeader = new JLabel(author + " - " + timestamp);
        articleHeader.setFont(new Font("Arial", Font.BOLD, 14));
        articleHeader.setForeground(Color.WHITE);

        JTextArea articleContent = new JTextArea(content);
        articleContent.setFont(new Font("Arial", Font.PLAIN, 14));
        articleContent.setForeground(Color.LIGHT_GRAY);
        articleContent.setBackground(new Color(30, 30, 30));
        articleContent.setLineWrap(true);
        articleContent.setWrapStyleWord(true);
        articleContent.setEditable(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(30, 30, 30));

     	// 좋아요 버튼 및 카운터
        JButton likeButton = new JButton("Like (0)");
        likeButton.setBackground(new Color(29, 155, 240));
        likeButton.setForeground(Color.WHITE);
        JButton commentButton = new JButton("Comment");

        likeButton.setBackground(new Color(29, 155, 240));
        likeButton.setForeground(Color.WHITE);

        commentButton.setBackground(new Color(29, 155, 240));
        commentButton.setForeground(Color.WHITE);
        
        // 좋아요 상태 저장
        final int[] likeCount = {0}; // 좋아요 카운터
        likeButton.addActionListener(e -> {
            if (likeButton.getText().contains("(0)")) {
                likeCount[0]++;
                likeButton.setText("Like (" + likeCount[0] + ")");
            } else if (likeButton.getText().contains("(" + likeCount[0] + ")")) {
                likeCount[0]--;
                likeButton.setText("Like (" + likeCount[0] + ")");
            }
        });

        commentButton.addActionListener(e -> {
            String comment = JOptionPane.showInputDialog(article, "Write your comment:", "Comment", JOptionPane.PLAIN_MESSAGE);
            if (comment != null && !comment.trim().isEmpty()) {
                articleContent.append("\n[Comment] " + getCurrentTimestamp() + ": " + comment);
                articleContent.setCaretPosition(articleContent.getDocument().getLength()); // 댓글 작성 후 자동 스크롤
            }
        });
        
        buttonPanel.add(likeButton);
        buttonPanel.add(commentButton);

        article.add(articleHeader, BorderLayout.NORTH);
        article.add(new JScrollPane(articleContent), BorderLayout.CENTER);
        article.add(buttonPanel, BorderLayout.SOUTH);

        // photoPath가 null이 아닌 경우 이미지 추가
        if (photoPath != null && !photoPath.isEmpty()) {
            try {
                ImageIcon imageIcon = new ImageIcon(photoPath);
                Image scaledImage = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                article.add(imageLabel, BorderLayout.WEST);
            } catch (Exception ex) {
                System.out.println("Error loading image: " + ex.getMessage());
            }
        }

        homePanel.add(article);
        homePanel.revalidate();
        homePanel.repaint();
    }



   
    private String getRandomAuthor() {
        return authors[random.nextInt(authors.length)];
    }

    public String getCurrentTimestamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }

    private String getRandomArticle() {
        String[] articles = {
                        "Can RFK Jr make America's diet healthy again? "
                        + "Robert F Kennedy Jr has set his sights on changing how Americans eat and drink.\r\n"
                        + "\r\n"
                        + "From the dyes in Fruit Loops cereal to seed oils in chicken nuggets, Kennedy - who is President-elect Trump's choice to lead the Department of Health and Human Services (DHHS) - has long spoken out against ingredients that he says hurt Americans' health.\r\n"
                        + "\r\n"
                        + "\"We are betraying our children by letting [food] industries poison them,” Kennedy said at a rally in November, after he had ended his independent presidential bid and backed Donald Trump.\r\n"
                        + "\r\n"
                        + "But if Kennedy hopes to target junk food, he will first have to shake up the country's food regulations - and run up against Big Food.\r\n"
                        + "\r\n"
                        + "What he's suggesting is taking on the food industry,” said former New York University nutrition professor Marion Nestle. “Will Trump back him up on that? I’ll believe it when I see it."
                        + "#RobertFKennedyJr #HealthReform #FoodRegulations #BigFood #HealthyEating #JunkFoodCrisis #NutritionPolicy #SeedOilsDebate #FoodIndustryReform #DHHSLeadership #HealthyFuture #FoodSafety #TrumpAdministration #ChildHealth #HealthAdvocacy",
                        "Top Republican says Trump nominees are ‘disruptors’"
                        + "Washington's highest-ranking Republican has said that President-elect Donald Trump is tapping \"disruptors\" to lead his incoming administration.\r\n"
                        + "\r\n"
                        + "\"They are persons who will shake up the status quo,\" House Speaker Mike Johnson said Sunday on CNN's State of the Union. \"I think that's by design.\"\r\n"
                        + "\r\n"
                        + "Trump continues to announce officials who he wants to fill high-ranking positions in his administration, seeming to favour close allies over those with related policy experience.\r\n"
                        + "\r\n"
                        + "Some of those picks have sent shockwaves through Washington and caused bipartisan concern. But those close to Trump say there are back-up plans in place if these nominees can't muster the support needed to be approved.",
                        "Daily Life \r\n"
                        + "This morning, I woke up earlier than usual. The crisp dawn air felt refreshing, so I took a short walk in the neighborhood park. It was the perfect moment to feel the last whispers of autumn. Listening to the crunch of fallen leaves while sipping a cup of coffee felt truly special. \r\n"
                        + "\r\n"
                        + "For lunch, I met up with some friends at a cozy restaurant for pasta :). It had been a while since we last got together, and the conversation made time fly. Delicious food and heartfelt laughter — the perfect combination! \r\n"
                        + "\r\n"
                        + "In the evening, I enjoyed a quiet night watching a movie by myself. There's something relaxing about unwinding with a good film to end the day. :D\r\n"
                        + "\r\n"
                        + "How was your day? ",
                        "Evening Walks :)\r\n"
                        + "Tonight, I decided to take a walk under the stars. The crisp evening air was refreshing, and the quietness of the streets felt almost therapeutic. \r\n"
                        + "\r\n"
                        + "I stumbled upon a small street vendor selling roasted chestnuts. The warm, nutty aroma was too tempting to resist, so I grabbed a bag and snacked while strolling.  The simple joy of holding something warm in my hands on a chilly night felt so comforting.\r\n"
                        + "\r\n"
                        + "Life feels a little lighter during moments like these. :D How do you like to spend your evenings?",
                        "I wanna go home right now :("
        			};

        return articles[random.nextInt(articles.length)];
    }
    
    
    public static void main(String[] args) {
        new MainUI();
    }
}