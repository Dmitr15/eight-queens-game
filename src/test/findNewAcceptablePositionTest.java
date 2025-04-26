import eightqueens.Desk;
import eightqueens.Queen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;



class TestFindNewAcceptablePosition {

    /*    аспекты тестирования:
    - количество ферзей, участвующих в поисках приемлемой позиции: один, два, несколько
    - ферзь уже занимает приемлемую позицию: да (первую, промежуточную, последнюю)/нет (еще не искал - ниже доски)
    - количество запросов к соседу слева для поиска новой приемлемой позиции: отсутствуют, один, несколько
    - результат поиска приемлемой позиции: найдена/не найдена
    - наличие соседа слева: да/нет
    */

    @Test
    void singleQueenFirstPos() {

        Point[] queen_pos = { new Point(1, Desk.rowBelowDesk() ) };
        Desk d = Desk.buildDesk(queen_pos);

        Point exp_pos = new Point(1, 1);
        boolean exp_ok = true;

        Queen q = d.getQueen(exp_pos.x);
        boolean ok = q.findNewAcceptablePosition();

        Assertions.assertEquals(exp_ok, ok);
        Assertions.assertEquals(exp_pos.x, q.col());
        Assertions.assertEquals(exp_pos.y, q.row());
    }

    @Test
    void singleQueenNextPos() {

        Point[] queen_pos = { new Point(1, 3 ) };
        Desk d = Desk.buildDesk(queen_pos);

        Point exp_pos = new Point(1, 4);
        boolean exp_ok = true;

        Queen q = d.getQueen(exp_pos.x);
        boolean ok = q.findNewAcceptablePosition();

        Assertions.assertEquals(exp_ok, ok);
        Assertions.assertEquals(exp_pos.x, q.col());
        Assertions.assertEquals(exp_pos.y, q.row());
    }

    @Test
    void singleQueenLastPos() {

        Point[] queen_pos = { new Point(1, Desk.rowCount()-1 ) };
        Desk d = Desk.buildDesk(queen_pos);

        Point exp_pos = new Point(1, Desk.rowCount());
        boolean exp_ok = true;

        Queen q = d.getQueen(exp_pos.x);
        boolean ok = q.findNewAcceptablePosition();

        Assertions.assertEquals(exp_ok, ok);
        Assertions.assertEquals(exp_pos.x, q.col());
        Assertions.assertEquals(exp_pos.y, q.row());
    }

    @Test
    void singleQueenNotPos() {

        Point[] queen_pos = { new Point(1, Desk.rowCount() ) };
        Desk d = Desk.buildDesk(queen_pos);

        Point exp_pos = new Point(1, d.rowAboveDesk() );
        boolean exp_ok = false;

        Queen q = d.getQueen(exp_pos.x);
        boolean ok = q.findNewAcceptablePosition();

        Assertions.assertEquals(exp_ok, ok);
        Assertions.assertEquals(exp_pos.x, q.col());
        Assertions.assertEquals(exp_pos.y, q.row());
    }

    @Test
    void secondQueenFirstPos() {

        Point[] queen_pos = {   new Point(1, 1 ),
                                new Point(2, Desk.rowBelowDesk() )  };
        Desk d = Desk.buildDesk(queen_pos);

        Point[] exp_pos = { new Point(1, 1), new Point(2, 3) };
        boolean exp_ok = true;

        Queen lastQueen = d.getQueen((int) exp_pos[exp_pos.length-1].getX());
        boolean ok = lastQueen.findNewAcceptablePosition();

        Assertions.assertEquals(exp_ok, ok);
        for(int i = 0; i < d.queenCount(); i++) {

            Queen q = d.getQueen(i+1);

            Assertions.assertEquals(exp_pos[i].x, q.col());
            Assertions.assertEquals(exp_pos[i].y, q.row());
        }
    }

    @Test
    void secondQueenNextPos() {

        Point[] queen_pos = {   new Point(1, 1 ),
                                new Point(2, 3 )  };
        Desk d = Desk.buildDesk(queen_pos);

        Point[] exp_pos = { new Point(1, 1), new Point(2, 4) };
        boolean exp_ok = true;

        Queen lastQueen = d.getQueen((int) exp_pos[exp_pos.length-1].getX());
        boolean ok = lastQueen.findNewAcceptablePosition();

        Assertions.assertEquals(exp_ok, ok);
        for(int i = 0; i < d.queenCount(); i++) {

            Queen q = d.getQueen(i+1);

            Assertions.assertEquals(exp_pos[i].x, q.col());
            Assertions.assertEquals(exp_pos[i].y, q.row());
        }
    }

    @Test
    void FirstQueenAssistSecondQueen() {

        Point[] queen_pos = {   new Point(1, 1 ),
                                new Point(2, Desk.rowCount() )  };
        Desk d = Desk.buildDesk(queen_pos);

        Point[] exp_pos = { new Point(1, 2), new Point(2, 4) };
        boolean exp_ok = true;

        Queen lastQueen = d.getQueen((int) exp_pos[exp_pos.length-1].getX());
        boolean ok = lastQueen.findNewAcceptablePosition();

        Assertions.assertEquals(exp_ok, ok);
        for(int i = 0; i < d.queenCount(); i++) {

            Queen q = d.getQueen(i+1);

            Assertions.assertEquals(exp_pos[i].x, q.col());
            Assertions.assertEquals(exp_pos[i].y, q.row());
        }
    }

    @Test
    void LastPosForFirstQueen() {

        Point[] queen_pos = {   new Point(1, Desk.rowCount()-1 ),
                                new Point(2, Desk.rowCount() )  };
        Desk d = Desk.buildDesk(queen_pos);

        Point[] exp_pos = { new Point(1, Desk.rowCount()), new Point(2, 1) };
        boolean exp_ok = true;

        Queen lastQueen = d.getQueen((int) exp_pos[exp_pos.length-1].getX());
        boolean ok = lastQueen.findNewAcceptablePosition();

        Assertions.assertEquals(exp_ok, ok);
        for(int i = 0; i < d.queenCount(); i++) {

            Queen q = d.getQueen(i+1);

            Assertions.assertEquals(exp_pos[i].x, q.col());
            Assertions.assertEquals(exp_pos[i].y, q.row());
        }
    }

    @Test
    void NotAcceptablePos() {

        Point[] queen_pos = {   new Point(1, Desk.rowCount() ),
                                new Point(2, Desk.rowCount() )  };
        Desk d = Desk.buildDesk(queen_pos);

        Point[] exp_pos = { new Point(1, Desk.rowAboveDesk()), new Point(2, Desk.rowAboveDesk()) };
        boolean exp_ok = false;

        Queen lastQueen = d.getQueen((int) exp_pos[exp_pos.length-1].getX());
        boolean ok = lastQueen.findNewAcceptablePosition();

        Assertions.assertEquals(exp_ok, ok);
        for(int i = 0; i < d.queenCount(); i++) {

            Queen q = d.getQueen(i+1);

            Assertions.assertEquals(exp_pos[i].x, q.col());
            Assertions.assertEquals(exp_pos[i].y, q.row());
        }
    }

    @Test
    void SixthQueenIsAssistedByTwoNeighbors() {

        Point[] queen_pos = {   new Point(1, 1 ),
                                new Point(2, 3 ),
                                new Point(3, 5 ),
                                new Point(4, 2 ),
                                new Point(5, 4 ),
                                new Point(6, Desk.rowBelowDesk() ) };
        Desk d = Desk.buildDesk(queen_pos);

        Point[] exp_pos = { new Point(1, 1 ),
                            new Point(2, 3 ),
                            new Point(3, 5 ),
                            new Point(4, 7 ),
                            new Point(5, 2 ),
                            new Point(6, 4 ) };
        boolean exp_ok = true;

        Queen lastQueen = d.getQueen((int) exp_pos[exp_pos.length-1].getX());
        boolean ok = lastQueen.findNewAcceptablePosition();

        Assertions.assertEquals(exp_ok, ok);
        for(int i = 0; i < d.queenCount(); i++) {

            Queen q = d.getQueen(i+1);

            Assertions.assertEquals(exp_pos[i].x, q.col());
            Assertions.assertEquals(exp_pos[i].y, q.row());
        }
    }

    @Test
    void EighthQueenFirstPos() {

        Point[] queen_pos = {   new Point(1, 1 ),
                                new Point(2, 3 ),
                                new Point(3, 5 ),
                                new Point(4, 7 ),
                                new Point(5, 2 ),
                                new Point(6, 4 ),
                                new Point(7, 6 ),
                                new Point(8, Desk.rowBelowDesk() ) };
        Desk d = Desk.buildDesk(queen_pos);

        Point[] exp_pos = { new Point(1, 1 ),
                            new Point(2, 5 ),
                            new Point(3, 8 ),
                            new Point(4, 6 ),
                            new Point(5, 3 ),
                            new Point(6, 7 ),
                            new Point(7, 2 ),
                            new Point(8, 4 )  };
        boolean exp_ok = true;

        Queen lastQueen = d.getQueen((int) exp_pos[exp_pos.length-1].getX());
        boolean ok = lastQueen.findNewAcceptablePosition();

        Assertions.assertEquals(exp_ok, ok);
        for(int i = 0; i < d.queenCount(); i++) {

            Queen q = d.getQueen(i+1);

            Assertions.assertEquals(exp_pos[i].x, q.col());
            Assertions.assertEquals(exp_pos[i].y, q.row());
        }
    }
    /*
    @Test
    void EighthQueenFirstPos() {

        Point[] queen_pos = {   new Point(1, 1 ),
                new Point(2, 3 ),
                new Point(3, 5 ),
                new Point(4, 7 ),
                new Point(5, 2 ),
                new Point(6, 4 ),
                new Point(7, Desk.rowBelowDesk() )};
        Desk d = Desk.buildDesk(queen_pos);

        Point[] exp_pos = { new Point(1, 1 ),
                new Point(2, 3 ),
                new Point(3, 5 ),
                new Point(4, 7 ),
                new Point(5, 2 ),
                new Point(6, 4 ),
                new Point(7, 6 ) };
        boolean exp_ok = true;

        Queen lastQueen = d.getQueen((int) exp_pos[exp_pos.length-1].getX());
        boolean ok = lastQueen.findNewAcceptablePosition();

        Assertions.assertEquals(exp_ok, ok);
        for(int i = 0; i < d.queenCount(); i++) {

            Queen q = d.getQueen(i+1);

            Assertions.assertEquals(exp_pos[i].x, q.col());
            Assertions.assertEquals(exp_pos[i].y, q.row());
        }
    }
*/
}