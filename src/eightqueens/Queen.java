package eightqueens;

import java.awt.*;

/** Ферзь, который самостоятельно ищет приемлемую позицию.
 */
public class Queen {  
    
    /* =========================== Свойства =============================== */
    
    /* ------------------------ Позиция на доске --------------------------- */
    private Point pos;

    public int row() {
        return pos.y;
    }

    public int col() {
        return pos.x;
    }

    public Point pos() {
        return pos;
    }

    /* --------------------------- Сосед слева ---------------------------- */
    private final Queen neighbor; 
    
    

    /* =========================== Операции =============================== */

    /* ---------------------------- Порождение ---------------------------- */
    public Queen(int col, int row, Queen neighbor) {
        pos = new Point(col, row);
        this.neighbor = neighbor;
    }
    
    /* -------------------------- Поиск решения --------------------------- */
    
    /** Ищет НОВУЮ приемлемую позицию для себя.
    *
    * @return признак того, что позиция найдена
    */
    public boolean findNewAcceptablePosition() {
        boolean isFind = false;

        // Ищем позицию собственными силами...
        // Пока позиция не найдена и не вышли за пределы доски
        while (!isFind && this.pos.y < Desk.rowCount() ) {
            // Делаем шаг "вверх"
            this.pos.y += 1;
            if (this.neighbor == null || !neighbor.canAttack(this.pos.x, this.pos.y)) {
                isFind = true;
            }
        }
        // Если не удалось найти позицию собственными силами
        if ( !isFind && this.neighbor != null ) {

            this.pos.y=Desk.rowAboveDesk();
            // Просим соседа слева найти приемлемую позицию для себя, если он имеется
            if(this.neighbor.findNewAcceptablePosition()){
                // Переходим в позицию под доской и снова ищем собственную позицию, если сосед нашел новую позицию
                this.pos.y=Desk.rowBelowDesk();
                isFind = this.findNewAcceptablePosition();
            }
        }
        if(!isFind &&this.neighbor == null && this.pos.y==8){this.pos.y=Desk.rowAboveDesk();}

        return isFind;
    }


    /** Атакует ли ферзь или его соседи слева указанную позицию.
     * 
     * @param col номер столбца (1...owner.colCount())
     * @param row номер строки (1...owner.rowCount(), owner.posAboveDesk(), owner.posBelowDesk())
     * @return признак того, что ферзь и его соседи слева атакуют указанную позицию
     */ 
    public boolean canAttack(int col, int row) {
        boolean isAttack;
        
        // Атакует ферзь
        isAttack = (this.row() != Desk.rowAboveDesk())
                    && (row != Desk.rowBelowDesk())
                    && (this.col() == col || this.row() == row
                        || Math.abs(this.col()-col) == Math.abs(this.row() - row));
        
        // Атакуют ли соседи, если ферзь не атакует
        if(!isAttack && neighbor != null)
        { isAttack = neighbor.canAttack(col, row); }
        
        return isAttack;
    }
    
    
    
    /* -------------------------- Отрисовка --------------------------- */
    
    /** Отрисовка ферзя
     * @param g графический контекст, в котором происходит отрисовка ферзя.
    */
    public void paint(Graphics g) {
        g.setColor(Color.red);
        g.fillOval((col()-1)*Desk.CELL_SIZE, (Desk.rowCount() - row())*Desk.CELL_SIZE,
                    Desk.CELL_SIZE, Desk.CELL_SIZE);
    }
}