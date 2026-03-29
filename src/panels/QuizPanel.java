package panels;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class QuizPanel extends JPanel {
    private int currIndex = 0;
    private int score = 0;
    private Consumer<Integer> onFinish;
    
    private JLabel questionLabel;
    private JLabel progressLabel;
    private JRadioButton[] opts = new JRadioButton[4];
    private ButtonGroup bg = new ButtonGroup();
    private JButton nextBtn;
    
    private String[] questions = {
        "What is polymorphism?",
        "Swing uses which threading model?",
        "Best collection for unique elements?",
        "JFrame extends what class?",
        "LayoutManager purpose?"
    };
    
    private String[][] options = {
        {"Multiple inheritance", "Many forms", "Encapsulation", "Data hiding"},
        {"Multi-threaded", "Single-threaded", "Actor model", "Coroutines"},
        {"ArrayList", "LinkedList", "HashSet", "Vector"},
        {"java.awt.Frame", "java.awt.Window", "java.awt.Component", "javax.swing.JPanel"},
        {"Draw graphics", "Handle events", "Arrange components", "Manage memory"}
    };
    
    private int[] answers = {1, 1, 2, 0, 2};
    
    public QuizPanel(Consumer<Integer> onFinish) {
        this.onFinish = onFinish;
        setLayout(new BorderLayout());
        setBackground(new Color(0x2b2b2b));
        setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        
        progressLabel = new JLabel("", SwingConstants.RIGHT);
        progressLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(progressLabel, BorderLayout.NORTH);
        
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);
        center.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        
        questionLabel = new JLabel("");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 22));
        center.add(questionLabel);
        center.add(Box.createVerticalStrut(30));
        
        for (int i = 0; i < 4; i++) {
            opts[i] = new JRadioButton();
            opts[i].setFont(new Font("Arial", Font.PLAIN, 18));
            opts[i].setForeground(Color.WHITE);
            opts[i].setOpaque(false);
            opts[i].setFocusPainted(false);
            bg.add(opts[i]);
            center.add(opts[i]);
            center.add(Box.createVerticalStrut(15));
            opts[i].addActionListener(e -> nextBtn.setEnabled(true));
        }
        add(center, BorderLayout.CENTER);
        
        nextBtn = new JButton("Next");
        HomePanel.styleButton(nextBtn, new Color(0x4a90e2), new Color(0x357abd));
        
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setOpaque(false);
        bottom.add(nextBtn);
        add(bottom, BorderLayout.SOUTH);
        
        nextBtn.addActionListener(e -> processAnswer());
    }
    
    public void resetQuiz() {
        currIndex = 0;
        score = 0;
        loadQuestion();
    }
    
    private void loadQuestion() {
        bg.clearSelection();
        nextBtn.setEnabled(false);
        questionLabel.setText("Q" + (currIndex + 1) + ". " + questions[currIndex]);
        for (int i = 0; i < 4; i++) {
            opts[i].setText(options[currIndex][i]);
        }
        progressLabel.setText("Question " + (currIndex + 1) + "/5 - Score: " + score + "/5");
        nextBtn.setText(currIndex == 4 ? "Submit Quiz" : "Next");
    }
    
    private void processAnswer() {
        for (int i = 0; i < 4; i++) {
            if (opts[i].isSelected() && i == answers[currIndex]) score++;
        }
        currIndex++;
        if (currIndex < 5) loadQuestion();
        else onFinish.accept(score);
    }
}
