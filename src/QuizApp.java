import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import panels.HomePanel;
import panels.QuizPanel;
import panels.ScoreboardPanel;

public class QuizApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private String currentName;
    private ArrayList<QuizResult> results = new ArrayList<>();
    
    private HomePanel homePanel;
    private QuizPanel quizPanel;
    private ScoreboardPanel scoreboardPanel;

    public QuizApp() {
        setTitle("Java Swing Quiz App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        homePanel = new HomePanel(this::startQuiz);
        quizPanel = new QuizPanel(this::finishQuiz);
        scoreboardPanel = new ScoreboardPanel(this::showHome, this::clearResults);
        
        mainPanel.add(homePanel, "HOME");
        mainPanel.add(quizPanel, "QUIZ");
        mainPanel.add(scoreboardPanel, "SCOREBOARD");
        add(mainPanel);
    }
    
    private void startQuiz(String name) {
        this.currentName = name;
        quizPanel.resetQuiz();
        cardLayout.show(mainPanel, "QUIZ");
    }
    
    private void finishQuiz(int score) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        results.add(new QuizResult(currentName, score, sdf.format(new Date())));
        updateScoreboard();
        cardLayout.show(mainPanel, "SCOREBOARD");
    }
    
    private void showHome() {
        homePanel.reset();
        cardLayout.show(mainPanel, "HOME");
    }
    
    private void clearResults() {
        results.clear();
        updateScoreboard();
    }
    
    private void updateScoreboard() {
        Object[][] data = new Object[results.size()][3];
        for (int i = 0; i < results.size(); i++) {
            QuizResult r = results.get(i);
            data[i] = new Object[]{r.name, r.score + "/5", r.date};
        }
        scoreboardPanel.updateTable(data);
    }
    
    private static class QuizResult {
        String name;
        int score;
        String date;
        QuizResult(String name, int score, String date) {
            this.name = name; this.score = score; this.date = date;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UIManager.put("Panel.background", new Color(0x2b2b2b));
            UIManager.put("Label.foreground", Color.WHITE);
            new QuizApp().setVisible(true);
        });
    }
}
