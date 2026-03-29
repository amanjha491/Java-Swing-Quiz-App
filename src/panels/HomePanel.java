package panels;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.function.Consumer;

public class HomePanel extends JPanel {
    private JTextField nameField;
    private JButton startBtn;
    
    public HomePanel(Consumer<String> onStart) {
        setLayout(new BorderLayout());
        setBackground(new Color(0x2b2b2b));
        
        JLabel title = new JLabel("Java Swing Quiz App", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setBorder(BorderFactory.createEmptyBorder(60, 0, 40, 0));
        add(title, BorderLayout.NORTH);
        
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        
        JLabel nameLabel = new JLabel("Enter your name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridy = 0;
        center.add(nameLabel, gbc);
        
        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 1;
        center.add(nameField, gbc);
        
        startBtn = new JButton("Start Quiz");
        styleButton(startBtn, new Color(0x4a90e2), new Color(0x357abd));
        startBtn.setEnabled(false);
        gbc.gridy = 2;
        center.add(startBtn, gbc);
        
        add(center, BorderLayout.CENTER);
        
        JLabel footer = new JLabel("5 Java questions - Built by Aman Kumar Jha", SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.ITALIC, 14));
        footer.setForeground(Color.LIGHT_GRAY);
        footer.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        add(footer, BorderLayout.SOUTH);
        
        nameField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { check(); }
            public void removeUpdate(DocumentEvent e) { check(); }
            public void changedUpdate(DocumentEvent e) { check(); }
            private void check() { startBtn.setEnabled(!nameField.getText().trim().isEmpty()); }
        });
        
        startBtn.addActionListener(e -> onStart.accept(nameField.getText().trim()));
    }
    
    public void reset() {
        nameField.setText("");
        startBtn.setEnabled(false);
    }
    
    public static void styleButton(JButton b, Color bg, Color hover) {
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusable(false);
        b.setFont(new Font("Arial", Font.BOLD, 16));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { if(b.isEnabled()) b.setBackground(hover); }
            public void mouseExited(java.awt.event.MouseEvent e) { if(b.isEnabled()) b.setBackground(bg); }
        });
    }
}
