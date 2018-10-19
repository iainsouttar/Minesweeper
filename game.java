import java.util.Random;

/**
 * Created by iains on 15/12/2016.
 */
public class game{
    private boolean[][] mineBoard, playerBoard, indicator;

    static GUI gui = new GUI();
    public game() {

    }

    public static void main(String[] args) {
        game game = new game();
        int x = gui.getSizePicked();
        double v = Math.ceil(x*x/6.4);
        int mines = (int) v ;
        game.createMines(mines, x);
        while (!game.checkWin(x)) {
            int[] place = gui.getSquarePicked();

            if (gui.getPicked() && game.mineBoard[place[0]][place[1]]) {
                gui.setSquare(place);
            }
            if (gui.getPicked() && !game.mineBoard[place[0]][place[1]]) {
                int i = place[0], j = place[1];
                game.checkSurroundings(i, j, x);
                game.checkOthers(i, j, x);
            }
        }
        gui.setSquareWon();


    }

    public boolean[][] createMines(int number, int size) {
        Random random = new Random();
        playerBoard = new boolean[size][size];
        mineBoard = new boolean[size][size];
        indicator = new boolean[size][size];

        for (int i = 0; i < number; i++) {
            mineBoard[random.nextInt(size)][random.nextInt(size)] = true;

        }
        return mineBoard;

    }

    public int checkSurroundings(int i, int j, int size) {
        if (i >= size || j >= size || i < 0 || j < 0) {
            return -1;
        }
        int counter = 0;
        int[] place = {i, j};
        for (int a = i - 1; a <= i + 1; a++) {
            for (int b = j - 1; b <= j + 1; b++) {
                if (a >= size || b >= size || a < 0 || b < 0) {
                    continue;
                } else if (mineBoard[a][b]) {
                    counter++;
                }
            }
        }
        gui.setSquareText(place, "" + counter);
        return counter;


    }

    public void checkOthers(int i, int j, int size) {
        int[] place = {i, j};

        if (i >= size || j >= size || i < 0 || j < 0) {
            return;
        }

        else if (checkSurroundings(i, j, size)==0 && !indicator[i][j]) {
            checkSurroundings(i, j, size);
            indicator[i][j] = true;
            checkOthers(i + 1, j, size);
            checkOthers(i - 1, j, size);
            checkOthers(i, j - 1, size);
            checkOthers(i, j + 1, size);

            checkOthers(i + 1, j+1, size);
            checkOthers(i - 1, j-1, size);
            checkOthers(i+1, j - 1, size);
            checkOthers(i-1, j + 1, size);

        }
    }

    public boolean checkWin(int size){
        boolean won = false;
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                if (!mineBoard[i][j] && gui.getFlagged(i, j)){
                    won = false;
                    return won;
                }
                if (mineBoard[i][j] && !gui.getFlagged(i, j)){
                    won = false;
                    return won;
                }
                else won = true;
            }
        }
        return won;
    }
}
