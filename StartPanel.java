/**
 * Project Sea Ludo
 * Course: ISTE-121-800
 * Team Name: Ihor Babenko & Varenichki Inc.
 * @author Evgeniya Samsonova
 * @author Luka Crnogorac
 * @author Ihor Babenko
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Class start panel used for the panel that starts before the game loads
 */
public class StartPanel extends JFrame {

   private int numberOfPlayers = 0;

   private JButton startButton = null;

   private JTextField playerNameField;
   private JPasswordField passwordField;
   private JTextField ipField;
   private boolean connectApproved = false;
   private String validIP;
   private String playerName;

   private Color blue = new Color(125, 219, 226); //color to be reused

   /**
    * constructor starts the panel and sets the Swing layout
    * @param mainframe as main frame to send it back to
    * @param sl as sea ludo main class
    */
   public StartPanel(JFrame mainframe, SeaLudo sl, MessengerClient mc){
   
      /**
       * sets the layout of the frame to include header
       * changes color
       * adds text fields and and labels
       * adds buttons
       */
   
      this.setLayout(new GridLayout(0,1));
      this.setBackground(blue);
      /*TOP PANEL with logo*/
      JPanel logo = new JPanel();
      this.add(logo);
   
      JLabel lblHeader = new JLabel();
      logo.add(lblHeader);
      logo.setBackground(blue);
      lblHeader.setIcon(new ImageIcon("ludologo.png"));
      lblHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
   
      /*CENTER PANEL*/
      JPanel startM = new JPanel();
      startM.setBackground(blue);
      this.add(startM);
   
      //center is included to JPanel startM
      JPanel center = new JPanel();
      center.setBackground(blue);
      center.setLayout(new GridLayout(0,2,5,5));
      center.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));//empty border
      startM.add(center);
   
   
   
   
      /** ---   UPDATED GUI    ----------------------------------------*/
   
      JLabel playerNameLabel = new JLabel("Player name:");
      center.add(playerNameLabel);
   
      playerNameField = new JTextField(15);
      center.add(playerNameField);
   
      JLabel ipLabel = new JLabel("Server IP Address:");
      center.add(ipLabel);
   
      ipField = new JTextField(15);
      center.add(ipField);
   
      JLabel passwordLabel = new JLabel("Password:");
      center.add(passwordLabel);
   
      passwordField = new JPasswordField(15);
      passwordField.setEchoChar('*');
      center.add(passwordField);
   
      /**--------------------------------------------------------------  */
   
   
   
   
   
   
      //panel for exit button and play
      JPanel bottom = new JPanel();
      //JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER,50, 0));
      bottom.setBackground(blue);
      bottom.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));//empty border
   
      JButton btnInstructions = new JButton(); //Submit button
      btnInstructions.setOpaque(true);
      btnInstructions.setContentAreaFilled(false);
      btnInstructions.setIcon(new ImageIcon("btninstructions.png"));
      btnInstructions.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));//empty border
   
      //button play
      startButton = new JButton(); //Submit button
      startButton.setOpaque(true);
      startButton.setContentAreaFilled(false);
      startButton.setIcon(new ImageIcon("startbutton.png"));
      startButton.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));//empty border
   
   
      //button exit
      JButton btnExit = new JButton(); //Submit button
      btnExit.setOpaque(true);
      btnExit.setContentAreaFilled(false);
      btnExit.setIcon(new ImageIcon("exitbutton.png"));
      btnExit.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));//empty border
   
      //action listener for game rules
      btnInstructions.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent ae) {
                   
                      JOptionPane.showMessageDialog(null, "To start the game, run the server, add credentials, then run the client." +
                          "\nGAME RULES" +
                          "\n\n" +
                          "\n" +
                          "Players take turns in a clockwise order; highest throw of the die starts.\n" +
                          "\n" +
                          "Each throw, the player decides which piece to move. A piece simply moves in a clockwise direction around the track given by the number thrown. If no piece can legally move according to the number thrown, play passes to the next player.\n" +
                          "\n" +
                          "A throw of 6 gives another turn.\n" +
                          "\n" +
                          "A player must throw a 6 to move a piece from the starting circle onto the first square on the track. The piece moves 6 squares around the circuit beginning with the appropriately coloured start square (and the player then has another turn).\n" +
                          "\n" +
                          "If a piece lands on a piece of a different colour, the piece jumped upon is returned to its starting circle.\n" +
                          "\n" +
                          "If a piece lands upon a piece of the same colour, this forms a block. This block cannot be passed or landed on by any opposing piece.\n" +
                          "\n" +
                          "Winning\n" +
                          "\n" +
                          "When a piece has circumnavigated the board, it proceeds up the home column. A piece can only be moved onto the home triangle by an exact throw.\n" +
                          "\n" +
                          "The first person to move all 4 pieces into the home triangle wins." +
                          "\n\n" +
                          "SOURCE: https://www.mastersofgames.com/rules/ludo-rules-instructions-guide.htm");
                   
                   }
                });
   
   /**
   * action listener for button starting
   */
      startButton.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent ae)
                   {
                      playerName = playerNameField.getText(); //gets the name from the field
                   //name input validation
                      if (playerName.equals("") || playerName.contains(" ")) {
                         JOptionPane.showMessageDialog(null, "Player's name required. Cannot contain space.");
                      } else {
                         sl.setTitle("SEA LUDO | " + getPlayerName());
                      //varables for validation thread
                         Socket s = null;
                         BufferedReader in = null;
                         PrintWriter out = null;
                         String passwordClient = ""; //password
                         try {
                         //starting the connection with the server to validate the ip address and the password
                            s = new Socket(ipField.getText(), 12344);
                            System.out.println("Socket initialized");
                            out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
                            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                            System.out.println("writer/reader initialized");
                            passwordClient = String.valueOf(passwordField.getPassword());
                            out.println(passwordClient); //sending password to server
                            System.out.println("password sent.");
                            out.flush();
                            System.out.println("flushed");
                         
                            String response = in.readLine(); //reading server response
                            System.out.println("line read");
                            numberOfPlayers = Integer.parseInt(in.readLine()); //reading number of players sent by server
                            System.out.println("number of players read : " + numberOfPlayers);
                         
                         //if the password is correct, connection is approved and player name is sent to server
                            if (response.equals("CORRECT_PASSWORD")) {
                               connectApproved = true;
                               validIP = ipField.getText(); //ip from the field
                               out.println(playerName);
                               out.flush();
                            } else {
                            //incorrect password message
                               JOptionPane.showMessageDialog(null, "Invalid password. Please type in valid password.");
                            }
                            s.close(); //ending the connection
                            System.out.println("Socket closed");
                         } catch (UnknownHostException uhe) {
                         //wrong ip address message
                            JOptionPane.showMessageDialog(null, "Unknown host server. Please type in valid ip address.");
                            try {
                               s.close();
                            } catch (IOException e) {
                               e.printStackTrace();
                            }
                         } catch (IOException ie) {
                            JOptionPane.showMessageDialog(null, "Error communicating with the server.");
                            try {
                               s.close();
                            } catch (IOException e) {
                               e.printStackTrace();
                            }
                         }
                      
                      //if the boolean for approved connection is true, it will make the board frame visible
                         if (connectApproved) {
                            setVisible(false); //closing the Current frame
                            if (getNumberOfPlayers() < 4) {
                               for (int i = 0; i < 4; i++) {
                                  sl.getBackgroundPanel().remove(sl.getBluePlayer().getTurtleLabel(i));
                                  if (getNumberOfPlayers() < 3) {
                                     sl.getBackgroundPanel().remove(sl.getGreenPlayer().getTurtleLabel(i));
                                  }
                               }
                            }
                            mainframe.setVisible(true);
                         //after pressing start the chat activates
                            mc.setVisible(true);
                            sl.startConnection();
                         
                         }
                      }
                   }
                });
   
      bottom.add(btnExit);
      bottom.add(startButton);
      bottom.add(btnInstructions);
   
   
      add(bottom);
   
      //add bottom panel
   
      this.setTitle("My Menu");
      this.setSize(500, 700);
      this.setResizable(false);
      this.setLocationRelativeTo( null );		// Center JFrame
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setVisible(true);
   
   
   
      //exit as anonymous inner class
   
      btnExit.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent ae)
                   {
                      JOptionPane.showMessageDialog(null, "See you next time!");
                      System.exit(0);
                   }
                });
   
      JPanel babenkoLogo = new JPanel();
      this.add(babenkoLogo);
      babenkoLogo.setBackground(blue);
   
      JLabel babenko = new JLabel();
      babenko.setAlignmentX(Component.CENTER_ALIGNMENT);
      babenkoLogo.add(babenko);
      babenko.setIcon(new ImageIcon("babenkologo.png"));
      babenko.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
   
   }



   //method that returns the number of players chosen from start panel

   /**
    * gets number of players
    * @return int as player number
    */
   public int getNumberOfPlayers()
   {
      return numberOfPlayers;
   }

   /**
    * @return the player name
    */
   public String getPlayerName() {
      return playerName;
   }

   /**
    * @return validated ip address for further connecting
    */
   public String getValidIP() {
      return validIP;
   }

}





