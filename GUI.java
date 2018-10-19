/**
 * Created by iains on 15/12/2016.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.OverlayLayout;

public class GUI {
    game GAME = new game();
    JFrame chooser = new JFrame("Choose size");
    JFrame board = new JFrame("Minesweeper Board");
    private JButton[] sizeButton;
    private boolean sizeIsPicked, picked;
    private boolean[][] flagged;
    private int sizePicked = 0;
    private JLabel[][] mineLabel;
    private JButton[][] mineButton;
    private JLabel title;
    private int[] squarePicked;
    ImageIcon noClick = new ImageIcon("C:/Users/iains/Pictures/MinesweeperNoClick.png");
    ImageIcon Mine = new ImageIcon("C:/Users/iains/Pictures/MinesweeperMine.png");
    ImageIcon flag = new ImageIcon("C:/Users/iains/Pictures/flagMinesweeper.jpg");




    public GUI(){

        chooser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //chooser.pack();
        chooseSize();
        chooser.setSize(600, 200);
        chooser.setVisible(true);
        while (!sizeIsPicked){}

        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.pack();
        board.setSize(500, 500);
        createBoard(sizePicked);
        board.setVisible(true);

    }

    public void chooseSize(){
        chooser.setLayout(new BorderLayout());
        JPanel top = new JPanel();
        chooser.add(top, BorderLayout.NORTH);

        JLabel title = new JLabel("Pick the Size of the Board", SwingConstants.CENTER);
        top.add(title);

        JPanel buttons = new JPanel();
        buttons.setMinimumSize(new Dimension(100, 10));
        chooser.add(buttons, BorderLayout.CENTER);
        buttons.setLayout(new GridLayout(1, 3));
        sizeButton = new JButton[3];
        for(int i =0 ; i<3;i++) {
            sizeButton[i] = new JButton("" + (i + 1) * 10 + "x" + (i + 1) * 10);
            buttons.add(sizeButton[i]);
            final int I = i;
            sizeButton[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sizeIsPicked= true;
                    sizePicked = (I + 1) * 10;
                    chooser.dispose();
                }
            });


        }

    }

    public void createBoard(int size){
        flagged = new boolean[size][size];
        board.setLayout(new BorderLayout());

        JPanel top = new JPanel();
        board.add(top, BorderLayout.NORTH);
        title = new JLabel("Pick a Square");
        top.add(title);


        JPanel grid = new JPanel();
        board.add(grid, BorderLayout.CENTER);
        grid.setLayout(new GridLayout(size, size));
        mineLabel = new JLabel[size][size];
        mineButton = new JButton[size][size];
        for(int i = 0; i<size;i++){
            for (int j =0; j<size;j++){
                mineLabel[i][j] = new JLabel(noClick, SwingConstants.CENTER);
                mineLabel[i][j].setLayout(new OverlayLayout(mineLabel[i][j]));

                mineButton[i][j] = new JButton();
                mineButton[i][j].setContentAreaFilled(false);
                mineButton[i][j].setMaximumSize(new Dimension(50*size, 10*size));

                mineLabel[i][j].add(mineButton[i][j], SwingConstants.CENTER);
                grid.add(mineLabel[i][j]);
                final int[] finalRC = {i, j};

                mineButton[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1){
                            squarePicked = finalRC;
                            picked = true;
                        }
                        if (e.getButton() == MouseEvent.BUTTON3){
                            if(flagged[finalRC[0]][finalRC[1]]){

                                mineButton[finalRC[0]][finalRC[1]].setIcon(null);
                                flagged[finalRC[0]][finalRC[1]]=false;
                            }
                            else{ mineButton[finalRC[0]][finalRC[1]].setIcon(flag);
                            flagged[finalRC[0]][finalRC[1]]=true;
                            }


                        }
                    }
                });
            }

        }
    }

    public boolean getSizeIsPicked(){

        return sizeIsPicked;
    }
    public int getSizePicked(){
        System.out.println("");
        return sizePicked;
    }
    public boolean getFlagged(int i,int j){
        return flagged[i][j];
    }

    public boolean getPicked(){
        return picked;
    }

    public int[] getSquarePicked(){
        return squarePicked;
    }

    public void setSquare(int[] place){
        mineLabel[place[0]][place[1]].setIcon(Mine);
        title.setText("Sorry! You Lose");
        picked = false;
    }
    public void setSquareWon(){
        title.setText("Congratulations! You win!");
        picked = false;
    }

    public void setSquareText(int[] place, String txt){
        mineLabel[place[0]][place[1]].setIcon(null);
        mineLabel[place[0]][place[1]].setText(txt);
        picked = false;
    }
    public Icon getIcon(int i, int j){
        return mineLabel[i][j].getIcon();
    }

}
