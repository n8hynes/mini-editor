import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javax.swing.border.EmptyBorder;

/**
 * Dialog find/replace for edit menu of mini-editor application. 
 * 
 * Based on code by
 * @author brown
 * 
 * Continued/added to by
 * @author enzo
 * 
 * @version 2015-11-02
 */
public class FindReplaceDialog extends JDialog{

    private final JPanel contentPanel = new JPanel();
    private JTextField findField;
    private JTextField replaceField;
    private JEditorPane editorPane;

    /**
     * Create the dialog.
     * 
     * @param editorPane editor pane instance from the main editor JFrame
     *                   so that FindReplaceDialog can make text changes
     *                   directly on the other Container.
     */
    public FindReplaceDialog(JEditorPane editorPane){

        setResizable(false);
        this.editorPane = editorPane;
        ActionListener findReplaceActionListener = new FindReplaceActionListener();

        setTitle("Find / Replace");
        setBounds(100, 100, 446, 126);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new GridLayout(2, 2, 0, 0));

        JLabel findLabel = new JLabel("Find:");
        findLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        findLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        findLabel.setAlignmentY(Component.TOP_ALIGNMENT);
        contentPanel.add(findLabel);

        findField = new JTextField();
        findField.setAlignmentY(Component.TOP_ALIGNMENT);
        findField.setAlignmentX(Component.LEFT_ALIGNMENT);
        findField.setText("");
        contentPanel.add(findField);
        findField.setColumns(10);

        JLabel replaceLabel = new JLabel("Replace With:");
        replaceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        replaceLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        replaceLabel.setAlignmentY(Component.TOP_ALIGNMENT);
        contentPanel.add(replaceLabel);

        replaceField = new JTextField();
        replaceField.setAlignmentY(Component.TOP_ALIGNMENT);
        replaceField.setAlignmentX(Component.LEFT_ALIGNMENT);
        replaceField.setText("");
        contentPanel.add(replaceField);
        replaceField.setColumns(10);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton findButton = new JButton("Find");
        findButton.addActionListener(findReplaceActionListener);
        buttonPane.add(findButton);
        getRootPane().setDefaultButton(findButton);

        JButton replaceButton = new JButton("Replace");
        replaceButton.addActionListener(findReplaceActionListener);
        buttonPane.add(replaceButton);

        JButton replaceAllButton = new JButton("Replace All");
        replaceAllButton.addActionListener(findReplaceActionListener);
        buttonPane.add(replaceAllButton);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                setVisible(false);
            }
        });
        buttonPane.add(closeButton);

    }

    /**
     * Common listener for Find/Replace/ReplaceAll action buttons.
     * 
     * @author brown
     * @author enzo
     */
    class FindReplaceActionListener implements ActionListener{
        private String findItem;
        private String replaceItem;
        @Override
        public void actionPerformed(ActionEvent e){
            findItem = findField.getText();
            replaceItem = replaceField.getText();
            switch(e.getActionCommand()){
                case "Replace All":
                    editorPane.setText(editorPane.getText().replaceAll(findItem, replaceItem));
                    break;
                case "Find":
                    findnext();
                    break;
                case "Replace":
                    if(editorPane.getSelectedText() != null){
                        if(editorPane.getSelectedText().equals(findItem)){
                            editorPane.replaceSelection(replaceItem);
                        }
                    }
                    findnext();
                    break;
            }
        }

        /**
         * Find the location of the next occurrence of the findItem string in the editorpane
         * and make it the editorPane's current selection. Searches from the current selection
         * until the end of text, with a notification on hitting the end of the text.
         */
        private void findnext(){
            int startIndex = editorPane.getSelectionEnd();
            int foundIndex = editorPane.getText().indexOf(findItem, startIndex);
            if (foundIndex<0){
                JOptionPane.showMessageDialog(FindReplaceDialog.this, findItem + ": End of text reached");
                editorPane.select(0,0);
            }
            else{
                editorPane.select(foundIndex, foundIndex + findItem.length());
                editorPane.setFocusable(true);
            }
        }
    }

}
