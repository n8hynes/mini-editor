import java.awt.EventQueue;

/**
 * A main routine for a simple text editor.
 * 
 * Based off code by
 * @author brown
 * 
 * Continued/added to by
 * @author enzo
 * 
 * @version 2015-11-02
 */
public class MiniEditor{
    /**
     * Launch the application
     */
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                try{
                    MiniEditorFrame frame = new MiniEditorFrame();
                    frame.setVisible(true);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
