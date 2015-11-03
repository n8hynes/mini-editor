import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import javax.swing.border.EmptyBorder;

public class WordCountDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JEditorPane editorPane;

    public WordCountDialog(JEditorPane editorPane) {

        setResizable(true);
        setTitle("Word Count");
        setBounds(100, 100, 400, 130);
        this.editorPane = editorPane;

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new GridLayout(4, 1, 0, 0));

        JPanel selectedPanel = new JPanel();
        selectedPanel.setLayout(new BorderLayout());
        JLabel selectedLabel = new JLabel("Selected Words: ");
        selectedPanel.add(selectedLabel, BorderLayout.WEST);
        JLabel selectedNum = new JLabel();
        if (editorPane.getSelectedText() == null) selectedNum.setText("0");
        else selectedNum.setText(String.valueOf(getWordCount(editorPane.getSelectedText())));
        selectedPanel.add(selectedNum, BorderLayout.CENTER);

        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new BorderLayout());
        JLabel totalLabel = new JLabel("Total Words: ");
        totalPanel.add(totalLabel, BorderLayout.WEST);
        JLabel totalNum = new JLabel();
        totalNum.setText(String.valueOf(getWordCount(editorPane.getText())));
        totalPanel.add(totalNum, BorderLayout.CENTER);

        JPanel allCharPanel = new JPanel();
        allCharPanel.setLayout(new BorderLayout());
        JLabel allCharLabel = new JLabel("Total Characters: ");
        allCharPanel.add(allCharLabel, BorderLayout.WEST);
        JLabel allCharNum = new JLabel();
        allCharNum.setText("TODO"); // TODO
        allCharPanel.add(allCharNum, BorderLayout.CENTER);

        JPanel nonWhiteCharPanel = new JPanel();
        nonWhiteCharPanel.setLayout(new BorderLayout());
        JLabel nonWhiteCharLabel = new JLabel("Total Non-Whitespace Characters: ");
        nonWhiteCharPanel.add(nonWhiteCharLabel, BorderLayout.WEST);
        JLabel nonWhiteCharNum = new JLabel();
        nonWhiteCharNum.setText("TODO"); // TODO
        nonWhiteCharPanel.add(nonWhiteCharNum, BorderLayout.CENTER);

        contentPanel.add(selectedPanel);
        contentPanel.add(totalPanel);
        contentPanel.add(allCharPanel);
        contentPanel.add(nonWhiteCharPanel);
    }

    public int getWordCount(String text) {
        if (text.matches("^\\s*$")) return 0;
        else {
            String[] words = text.trim().split("\\s+");
            return words.length;
        }
    }

}
