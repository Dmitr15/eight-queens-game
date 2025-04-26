package eightqueens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The main class of the application.
 */
public class EightQueensWindow extends JFrame {

    private JButton btnNewSolution;

    public EightQueensWindow() {       

        JPanel content = (JPanel)getContentPane();        
        content.setLayout( new BoxLayout(content, BoxLayout.Y_AXIS) );
        
        Desk d =  Desk.buildStandartDesk(this);
        boolean isOk = d.firstSolution();

        content.add(Box.createRigidArea(new Dimension(0,10)));

        btnNewSolution = new JButton();
        btnNewSolution.setAlignmentX(Component.CENTER_ALIGNMENT); 
        btnNewSolution.setText("Новое решение"); 
        add( btnNewSolution );
        
        btnNewSolution.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                d.newSolution();
            }
        });
        

        content.add(Box.createRigidArea(new Dimension(0,10)));

        // Обязательно после создания кнопки btnNewSolution
        if(!isOk)  {
            freeze();
        }

        pack(); // подгоняем размеры окна под его содержимое
        this.setResizable(false); // в играх редко приходится изменять размер окна

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public void freeze() {
        btnNewSolution.setEnabled(false);
    }


    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EightQueensWindow();
            }
        });
    }
}
