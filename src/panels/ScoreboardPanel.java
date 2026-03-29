package panels;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ScoreboardPanel extends JPanel {
    private DefaultTableModel model;
    
    public ScoreboardPanel(Runnable onHome, Runnable onClear) {
        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(0x2b2b2b));
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        JLabel title = new JLabel("Scoreboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        add(title, BorderLayout.NORTH);
        
        String[] cols = {"Name", "Score", "Date/Time"};
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        JTable table = new JTable(model) {
            public Component prepareRenderer(javax.swing.table.TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(0x3c3c3c) : new Color(0x454545));
                }
                return c;
            }
        };
        table.setFillsViewportHeight(true);
        table.setBackground(new Color(0x3c3c3c));
        table.setForeground(Color.WHITE);
        table.setGridColor(Color.DARK_GRAY);
        table.setRowHeight(35);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setAutoCreateRowSorter(true);
        
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(0x4a90e2));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(new Color(0x2b2b2b));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(0x3c3c3c), 2));
        add(scroll, BorderLayout.CENTER);
        
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        bottom.setOpaque(false);
        
        JButton clearBtn = new JButton("Clear All Results");
        JButton newBtn = new JButton("New Quiz");
        
        HomePanel.styleButton(clearBtn, new Color(0xd9534f), new Color(0xc9302c));
        HomePanel.styleButton(newBtn, new Color(0x4a90e2), new Color(0x357abd));
        
        clearBtn.addActionListener(e -> onClear.run());
        newBtn.addActionListener(e -> onHome.run());
        
        bottom.add(clearBtn);
        bottom.add(newBtn);
        add(bottom, BorderLayout.SOUTH);
    }
    
    public void updateTable(Object[][] data) {
        model.setRowCount(0);
        for (Object[] row : data) {
            model.addRow(row);
        }
    }
}
