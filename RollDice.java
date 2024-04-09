/**
 * Project Sea Ludo
 * Course: ISTE-121-800
 * Team Name: Ihor Babenko & Varenichki Inc.
 *
 * @author Evgeniya Samsonova
 * @author Luka Crnogorac
 * @author Ihor Berehovyi
 */

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.security.SecureRandom;
import java.sql.SQLOutput;
import java.util.*;


class RollDice extends JPanel {

    private Color blue = new Color(125, 219, 226);
    private Random generator = new SecureRandom();
    private JLabel diceLabel = null;
    private JButton btnDiceRoll = null;
    private int turn = 1;
    private boolean blockDice;
    private boolean isServerFull;
    private SeaLudo seaLudo;
    private java.util.Timer timer;

    public RollDice(JPanel panelName, SeaLudo seaLudo, StartPanel sp) { //parameter of JPanel so it can be added to JPanel in SeaLudo class

        this.seaLudo = seaLudo;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //this.setBorder(BorderFactory.createEmptyBorder(20,0,0,20));
        panelName.add(this); //add this panel to panel in  another class

        this.setBackground(blue);
        diceLabel = new JLabel();
        diceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(diceLabel);
        diceLabel.setIcon(new ImageIcon("dice1.png"));
        diceLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));


        btnDiceRoll = new JButton();
        add(btnDiceRoll);
        btnDiceRoll.setIcon(new ImageIcon("dicebutton.png"));
        this.add(btnDiceRoll);

        btnDiceRoll.setOpaque(true);
        btnDiceRoll.setBorder(BorderFactory.createEmptyBorder());
        btnDiceRoll.setContentAreaFilled(false);
        btnDiceRoll.setAlignmentX(Component.CENTER_ALIGNMENT);
   
      /*
      Mouse listener for dice rolling
       */
        btnDiceRoll.addMouseListener(

                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent click) {
                        //checking if the server is full
                        if (seaLudo.getNumOfActivePlayers() == sp.getNumberOfPlayers()) {
                            blockDice = seaLudo.getBlockDice();
                            //checking if dice is unblocked AND if it's player's turn to play
                            if (!blockDice && getTurn() == seaLudo.getAssignedColorNum()) {
                                if (turn == (sp.getNumberOfPlayers() + 1)) {
                                    turn = 1;
                                }
                                int rollRes = rollDice();
                                System.out.println("Dice rolled finished, value: " + rollRes);
                                seaLudo.playTurn(turn, rollRes);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "You must wait for other players to join.");
                        }
                    }
                }
        );


    }

    /** method roll dice generates random number from 1 to 6
     * @return a number from 0 to 5 and add 1 to it to copy the dice number behaviour
     * pauses the task for animation to happen
     */


    public int rollDice() {

        int random = generator.nextInt(6);
        int num = random + 1;

        if (num == 1) {

            diceLabel.setIcon(new ImageIcon("animateddice.gif")); //this runs first and shows image for 3 seconds

            timer = new java.util.Timer();

            TimerTask switchColor =

                    new TimerTask() {

                        public void run() {

                            diceLabel.setIcon(new ImageIcon("dice1.png"));
                            //System.out.println("Image 1");
                            this.cancel();
                        }

                    };

            /**
             *  Java util Timer Class schedules the specified task for execution after the specified delay
             */

            timer.schedule(switchColor, 1000, 100);
            //this.seaLudo.setInstruction("You rolled 1!");

        } else if (num == 2) {

            diceLabel.setIcon(new ImageIcon("animateddice.gif")); //this runs first and shows image for 3 seconds

            timer = new java.util.Timer();

            TimerTask switchColor =

                    new TimerTask() {

                        public void run() {

                            diceLabel.setIcon(new ImageIcon("dice2.png"));
                            //System.out.println("Image 2");
                            this.cancel();
                        }

                    };

            /**
             *  Java util Timer Class schedules the specified task for execution after the specified delay
             */

            timer.schedule(switchColor, 1000, 100);
            //this.seaLudo.setInstruction("You rolled 2!");

        } else if (num == 3) {

            diceLabel.setIcon(new ImageIcon("animateddice.gif")); //this runs first and shows image for 3 seconds

            timer = new java.util.Timer();

            TimerTask switchColor =

                    new TimerTask() {

                        public void run() {

                            diceLabel.setIcon(new ImageIcon("dice3.png"));
                            //System.out.println("Image 3");
                            this.cancel();
                        }

                    };

            /**
             *  Java util Timer Class schedules the specified task for execution after the specified delay
             */

            timer.schedule(switchColor, 1000, 100);
            //this.seaLudo.setInstruction("You rolled 3!");

        } else if (num == 4) {
            diceLabel.setIcon(new ImageIcon("animateddice.gif")); //this runs first and shows image for 3 seconds

            timer = new java.util.Timer();

            TimerTask switchColor =

                    new TimerTask() {

                        public void run() {

                            diceLabel.setIcon(new ImageIcon("dice4.png"));
                            //System.out.println("Image 4");
                            this.cancel();
                        }

                    };

            /**
             *  Java util Timer Class schedules the specified task for execution after the specified delay
             */

            timer.schedule(switchColor, 1000, 100);
            //this.seaLudo.setInstruction("You rolled 4!");

        } else if (num == 5) {

            diceLabel.setIcon(new ImageIcon("animateddice.gif")); //this runs first and shows image for 3 seconds

            timer = new java.util.Timer();

            TimerTask switchColor =

                    new TimerTask() {

                        public void run() {

                            diceLabel.setIcon(new ImageIcon("dice5.png"));
                            //System.out.println("Image 5");
                            this.cancel();
                        }

                    };

            /**
             *  Java util Timer Class schedules the specified task for execution after the specified delay
             */

            timer.schedule(switchColor, 1000, 100);
            //this.seaLudo.setInstruction("You rolled 5!");

        } else if (num == 6) {

            diceLabel.setIcon(new ImageIcon("animateddice.gif")); //this runs first and shows image for 3 seconds

            timer = new java.util.Timer();

            TimerTask switchColor =

                    new TimerTask() {

                        public void run() {

                            diceLabel.setIcon(new ImageIcon("dice6.png"));
                            //System.out.println("Image 6");
                            this.cancel();
                        }

                    };

            /**
             *  Java util Timer Class schedules the specified task for execution after the specified delay
             */

            timer.schedule(switchColor, 1000, 100);

        }

        //System.out.println("Final num: " + num);
        return num;
    }

    /**
     * increments turn by 1
     */
    public void incrementTurn(StartPanel sp) {
        turn++;
    }

    /**
     * method to reset turns
     */
    public void resetTurn() {
        turn = 1;
    }

    /**
     * gets turn of the player
     * @return turn as in
     */
    public int getTurn() {
        return turn;
    }
}
