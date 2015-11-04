package MinEd;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.text.NumberFormat;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.StyledEditorKit;

/**
 * An editor pane to create a "mini-editor". Includes file and edit menus,
 * and a find/replace dialog. 
 * 
 * Based on code by
 * @author brown
 * 
 * Continued/added to by
 * @author enzo
 * 
 * @version 2015-11-03
 */
public class MiniEditorFrame extends JFrame {

    private JPanel contentPane;
    private JEditorPane editorPane;
    private JFileChooser fileChooser = new JFileChooser();
    private JMenuItem saveAsItem;
    private FindReplaceDialog findAndReplace;

    //-----Assignment 6 Start-----//

    private JLabel countNum;
    private WordCountDialog wordCount;

    //------Assignment 6 End------//

    /**
     * Create the frame.
     */
    public MiniEditorFrame() {

        setTitle("MinEd");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        // Create the content pane:
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        editorPane = new JEditorPane();

        scrollPane.setViewportView(editorPane);
        findAndReplace = new FindReplaceDialog(editorPane);

        //-----Assignment 6 Start-----//

        editorPane.setEditorKit(new StyledEditorKit());

        wordCount = new WordCountDialog(editorPane);

        JPanel countPanel = new JPanel();
        countPanel.setLayout(new BorderLayout(0, 0));
        JLabel countLabel = new JLabel("Word Count: ");
        countPanel.add(countLabel, BorderLayout.WEST);
        countNum = new JLabel();
        updateWordCount();
        countPanel.add(countNum, BorderLayout.CENTER);
        contentPane.add(countPanel, BorderLayout.SOUTH);

        editorPane.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                updateWordCount();
            }
        });

        //------Assignment 6 End------//

        // Create the menu bar:
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        JMenuItem openItem = new JMenuItem("Open...", KeyEvent.VK_O);
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showOpenDialog(MiniEditorFrame.this) == JFileChooser.APPROVE_OPTION) {
                    File f = fileChooser.getSelectedFile();
                    try {
                        FileReader r = new FileReader(f);
                        int filelength = (int) f.length();
                        char[] buffer = new char[filelength];
                        r.read(buffer, 0, filelength);
                        r.close();
                        editorPane.setText(new String(buffer));
                        setTitle("MinEd (" + f.getName() + ")");
                    }
                    catch(IOException ioe) {
                        JOptionPane.showMessageDialog(MiniEditorFrame.this, "Error. Can't load file.");
                    }
                }
            }
        });
        fileMenu.add(openItem);

        JMenuItem saveItem = new JMenuItem("Save", KeyEvent.VK_S);
        
        ActionListener saveListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File f = fileChooser.getSelectedFile();
                if (e.getSource().equals(saveAsItem) || f == null) {
                    if (fileChooser.showSaveDialog(MiniEditorFrame.this) != JFileChooser.APPROVE_OPTION) {
                        return;
                    }
                    f = fileChooser.getSelectedFile();
                }
                try {
                    FileWriter writer = new FileWriter(f);
                    writer.write(editorPane.getText());
                    writer.close();
                    MiniEditorFrame.this.setTitle("MinEd (" + f.getName() + ")");
                }
                catch (IOException ioe) {
                    JOptionPane.showMessageDialog(MiniEditorFrame.this, "Error. Can't write file.");
                }
            }
        };
        saveItem.addActionListener(saveListener);
        fileMenu.add(saveItem);

        saveAsItem = new JMenuItem("Save As...", KeyEvent.VK_A);
        saveAsItem.addActionListener(saveListener);
        fileMenu.add(saveAsItem);

        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        menuBar.add(editMenu);

        JMenuItem copyItem = new JMenuItem("Copy", KeyEvent.VK_C);
        copyItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editorPane.copy();
            }
        });
        editMenu.add(copyItem);

        JMenuItem cutItem = new JMenuItem("Cut", KeyEvent.VK_U);
        cutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editorPane.cut();
            }
        });
        editMenu.add(cutItem);

        JMenuItem pasteItem = new JMenuItem("Paste", KeyEvent.VK_P);
        pasteItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editorPane.paste();
            }
        });
        editMenu.add(pasteItem);

        JMenuItem findReplaceItem = new JMenuItem("Find/Replace", KeyEvent.VK_F);
        findReplaceItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findAndReplace.setVisible(true);
            }
        });
        editMenu.add(findReplaceItem);

        //-----Assignment 6 Start-----//

        JMenu toolMenu = new JMenu("Tools");
        toolMenu.setMnemonic(KeyEvent.VK_T);
        menuBar.add(toolMenu);

        JMenuItem wordCountItem = new JMenuItem("Word Count", KeyEvent.VK_W);
        wordCountItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wordCount.setVisible(true);
            }
        });
        toolMenu.add(wordCountItem);

        //------Assignment 6 End------//

    }

    //-----Assignment 6 Start-----//

    public String getWordCount(String text) {
        if (text.matches("^\\s*$")) return "0";
        else {
            String[] words = text.trim().split("\\s+");
            return NumberFormat.getIntegerInstance().format(words.length);
        }
    }

    public void updateWordCount() {
        if (editorPane.getSelectedText() == null) {
            countNum.setText(getWordCount(editorPane.getText()));
        }
        else {
            countNum.setText(getWordCount(editorPane.getSelectedText()) + " (of " + getWordCount(editorPane.getText()) + ")");
        }
    }

    //------Assignment 6 End------//

}
