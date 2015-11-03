import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class WordCountDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JEditorPane editorPane;
    private JLabel selectedNum;
    private JLabel totalNum;
    private JLabel selectedCharNum;
    private JLabel selectedNonWhiteCharNum;
    private JLabel totalCharNum;
    private JLabel totalNonWhiteCharNum;

    public WordCountDialog(JEditorPane editorPane) {

        setResizable(false);
        setTitle("Word Count");
        setBounds(100, 100, 300, 250);
        this.editorPane = editorPane;
        this.editorPane.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                int selectionStart = editorPane.getSelectionStart();
                editorPane.select(selectionStart, selectionStart);
                update();
            }
        });
        this.editorPane.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e){
                update();
            }
        });

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.weightx = 1;
        c.weighty = 2;
        c.gridwidth = 2;

        JLabel selected = new JLabel("Selected", JLabel.CENTER);
        c.gridx = 0;
        c.gridy = 0;
        contentPanel.add(selected, c);

        c.weightx = 0.5;
        c.weighty = 1;
        c.gridwidth = 1;

        JLabel selectedLabel = new JLabel("Words: ");
        c.gridx = 0;
        c.gridy = 1;
        contentPanel.add(selectedLabel, c);
        selectedNum = new JLabel("", JLabel.RIGHT);
        c.gridx = 1;
        c.gridy = 1;
        contentPanel.add(selectedNum, c);

        JLabel selectedCharLabel = new JLabel("Characters: ");
        c.gridx = 0;
        c.gridy = 2;
        contentPanel.add(selectedCharLabel, c);
        selectedCharNum = new JLabel("", JLabel.RIGHT);
        c.gridx = 1;
        c.gridy = 2;
        contentPanel.add(selectedCharNum, c);

        JLabel selectedNonWhiteCharLabel = new JLabel("Non-Whitespace Characters: ");
        c.gridx = 0;
        c.gridy = 3;
        contentPanel.add(selectedNonWhiteCharLabel, c);
        selectedNonWhiteCharNum = new JLabel("", JLabel.RIGHT);
        c.gridx = 1;
        c.gridy = 3;
        contentPanel.add(selectedNonWhiteCharNum, c);

        c.weightx = 1;
        c.weighty = 2;
        c.gridwidth = 2;

        JLabel total = new JLabel("Total", JLabel.CENTER);
        c.gridx = 0;
        c.gridy = 4;
        contentPanel.add(total, c);

        c.weightx = 0.5;
        c.weighty = 1;
        c.gridwidth = 1;

        JLabel totalLabel = new JLabel("Words: ");
        c.gridx = 0;
        c.gridy = 5;
        contentPanel.add(totalLabel, c);
        totalNum = new JLabel("", JLabel.RIGHT);
        c.gridx = 1;
        c.gridy = 5;
        contentPanel.add(totalNum, c);

        JLabel totalCharLabel = new JLabel("Characters: ");
        c.gridx = 0;
        c.gridy = 6;
        contentPanel.add(totalCharLabel, c);
        totalCharNum = new JLabel("", JLabel.RIGHT);
        c.gridx = 1;
        c.gridy = 6;
        contentPanel.add(totalCharNum, c);

        JLabel totalNonWhiteCharLabel = new JLabel("Non-Whitespace Characters: ");
        c.gridx = 0;
        c.gridy = 7;
        contentPanel.add(totalNonWhiteCharLabel, c);
        totalNonWhiteCharNum = new JLabel("", JLabel.RIGHT);
        c.gridx = 1;
        c.gridy = 7;
        contentPanel.add(totalNonWhiteCharNum, c);

        update();

    }

    public String getWordCount(String text) {
        if (text.matches("^\\s*$")) return String.valueOf(0);
        else {
            String[] words = text.trim().split("\\s+");
            return String.valueOf(words.length);
        }
    }

    public String getCharCount(String text) {
        return String.valueOf(text.length());
    }

    public String getNonWhiteCharCount(String text) {
        if (text.matches("^\\s*$")) return String.valueOf(0);
        else {
            String[] words = text.trim().split("\\s+");
            String newtext = String.join("", words);
            return getCharCount(newtext);
        }
    }

    public void update() {
        updateSelectedNum();
        updateTotalNum();
        updateSelectedCharNum();
        updateSelectedNonWhiteCharNum();
        updateTotalCharNum();
        updateTotalNonWhiteCharNum();
    }

    public void updateSelectedNum() {
        if (editorPane.getSelectedText() == null) selectedNum.setText("0");
        else selectedNum.setText(getWordCount(editorPane.getSelectedText()));
    }

    public void updateTotalNum() {
        totalNum.setText(getWordCount(editorPane.getText()));
    }

    public void updateSelectedCharNum() {
        if (editorPane.getSelectedText() == null) selectedCharNum.setText("0");
        else selectedCharNum.setText(getCharCount(editorPane.getSelectedText()));
    }

    public void updateSelectedNonWhiteCharNum() {
        if (editorPane.getSelectedText() == null) selectedNonWhiteCharNum.setText("0");
        else selectedNonWhiteCharNum.setText(getNonWhiteCharCount(editorPane.getSelectedText()));
    }

    public void updateTotalCharNum() {
        totalCharNum.setText(getCharCount(editorPane.getText()));
    }

    public void updateTotalNonWhiteCharNum() {
        totalNonWhiteCharNum.setText(getNonWhiteCharCount(editorPane.getText()));
    }
}
