package eightqueens;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;

/** Класс доски, содержащий 8 ферзей (модель и представление).
*/
public class Desk extends JPanel implements Iterable<Queen>{

    /* ========================== Константы =============================== */

    static final int CELL_SIZE = 30;
    
    /* =========================== Свойства =============================== */
    
    /** Размеры доски (в количестве клеток).
    */
    public static int colCount(){
        return 8;
    }
    public static int rowCount(){
        return 8;
    }
    
    public static int rowAboveDesk() {
        return rowCount()+1;
    }
    public static int rowBelowDesk() {
        return 0;
    }

    public static Point abovePos(Point p) {
        Point ap = null;
        if(p.y < rowAboveDesk()) {
            ap = new Point(p.x, p.y+1);
        }
        return ap;
    }

    public static Point belowPos(Point p) {
        Point bp = null;
        if(p.y > rowBelowDesk()) {
            bp = new Point(p.x, p.y-1);
        }
        return bp;
    }

    /* Список ферзей */    
    private Queen[] queens;

    public Queen getQueen(int col) {

        for(Queen q : queens) {

            if(q.col() == col ) {
                return q;
            }
        }

        return null;
    }

    public int queenCount() {
        return queens.length;
    }

    @Override
    public Iterator<Queen> iterator() {
        return Arrays.stream(queens).iterator();
    }


    /* =========================== Операции =============================== */

    /* ---------------------------- Порождение ---------------------------- */

    private EightQueensWindow owner;

    /** Порождает доску со стандартным расположением ферзей
    */     
    public static Desk buildStandartDesk(EightQueensWindow owner) {
        Desk d = new Desk();
        d.setQueens( d.initQueensPos() );

        d.owner = owner;
        d.owner.add(d);

        return d;
    }
    
    /** Порождает доску с произвольным расположением ферзей
    */    
    public static Desk buildDesk( Point[] queens_pos) {
        Desk d = new Desk();
        d.setQueens( queens_pos );
        
        return d;
    }
    
    private Desk( ) {
        // Настраиваем визуальное отображение доски
        setBounds(0, 0, colCount()*CELL_SIZE, rowCount()*CELL_SIZE);
        setPreferredSize(new Dimension(colCount()*CELL_SIZE, rowCount()*CELL_SIZE) );
        setBackground(Color.white);                
    }
    
    private Point[] initQueensPos() {
        Point[] queens_pos = new Point[ colCount() ];
        
        for(int i = 0; i < colCount(); i++) {
            queens_pos[i] = new Point( i+1, rowBelowDesk() );
        } 
        
        return queens_pos;
    }       
    
    private void setQueens(Point[] queens_pos) {
        
        // Расставляем ферзей
        queens = new Queen[queens_pos.length];
        
        // Устанавливаем первого ферзя
        queens[0] = new Queen(queens_pos[0].x, queens_pos[0].y, null);

        // Устанавливаем остальных ферзей
        for( int i = 1; i < queens_pos.length; i++  ) {
           int col = queens_pos[i].x;
           int row = queens_pos[i].y;
           
           queens[i] = new Queen(col, row, queens[i-1]);
        }
    }
    
    
    /* -------------------------- Поиск решения --------------------------- */
    
    /** Ищет первое решение и отображает его
    */
    public boolean firstSolution()
    {
        boolean isOk = true;
        for(Queen q : queens)
        {
            isOk = isOk && q.findNewAcceptablePosition();
        }
        repaint();

        return isOk;
    }

    /** Ищет новое решение и отображает его
    */
    public void newSolution()
    {
        boolean isOk = queens[queens.length-1].findNewAcceptablePosition();
        if(isOk) {
            repaint();
        } else {
            owner.freeze();
        }
    }
    
    /* -------------------------- Отрисовка --------------------------- */
    
    /** Отрисовывает доску
    */
    @Override
    public void paint(Graphics g)
    {
        // отрисовка черных клеток
        super.paint(g);
        g.setColor(Color.black);
        for(int col=0; col < colCount(); col++)
        {
            for(int row=0; row < rowCount()/2; row++)
            {
                g.fillRect(row*2*CELL_SIZE+CELL_SIZE-CELL_SIZE*(col%2), col*CELL_SIZE, 
                            CELL_SIZE, CELL_SIZE);
            }
        }
        
        // отрисовка ферзей
        for(Queen q: this.queens){
            q.paint(g);
        }
    }
}
