   
/**
 * Project Sea Ludo
 * Course: ISTE-121-800
 * Team Name: Ihor Babenko & Varenichki Inc.
 * @author Evgeniya Samsonova
 * @author Luka Crnogorac
 * @author Ihor Berehovyi
 */

import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

public class LudoServer extends JFrame {
   /**
    * attributes
    */
   private int numOfPlayers;
   private String password;
   private PrintWriter opw;
   ServerSocket ss = null;
   ServerSocket chatSocket = null;
   StartServerThread startThread = null; //thread that starts the server (opens connection)
   private Vector<ObjectOutputStream> writers = new Vector<ObjectOutputStream>(); //vector that stores the output streams for each client
   private JTextField numOfActiveClientsField = null;
   private int numOfConnectedClients; //number of connected clients
   private ArrayList<String> playerNames; //names of the players

   /**
    * first constructor
    * @param numOfPlayers number of players selected
    * @param password entered password
    */
   public LudoServer(int numOfPlayers, String password, JTextField numOfActiveClientsField) {
      this.numOfPlayers = numOfPlayers;
      this.password = password;
      playerNames = new ArrayList<String>();
   
      //starting thread for entry data validation
      ValidationThread vt = new ValidationThread(password);
      vt.start();
   
      //sockets declaration and initialization
      Socket cs = null;
   
      try {
         System.out.println("My local host: " + InetAddress.getByName("localhost"));
      
      
         ss = new ServerSocket(12345); //initializing server socket
         //waiting for clients to connect and starting the read/write thread
         while (true) {
            cs = ss.accept(); //waiting client to connect
            System.out.println("Client connected");
         
            //creating printwriter for writing to the connected client
            ObjectOutputStream oos = new ObjectOutputStream(cs.getOutputStream());
            writers.add(oos);
            numOfActiveClientsField.setText(String.valueOf(writers.size()));
         
            //thread
            ThreadServer ths = new ThreadServer(cs, playerNames.get(playerNames.size()-1));
            ths.start();
            System.out.println("Thread created and started");
         }
      } catch (UnknownHostException ue) {
         ue.printStackTrace();
      } catch (IOException ue) {
         ue.printStackTrace();
      }
   }

   /**
    * constructor
    */
   public LudoServer() {
   
      //LAYOUT
      BorderLayout layout = new BorderLayout();
   
      //HEADING panel
      JPanel head = new JPanel();
      JLabel heading = new JLabel("Welcome to SeaLudo Server!");
      head.add(heading);
      add(head, layout.NORTH);
   
      //CENTER
      JPanel body = new JPanel(new GridLayout(0, 1));
      //password section
      JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
      JLabel setPassword = new JLabel("Set password: ");
      JPasswordField passwordField = new JPasswordField(15);
      //passwordField.setEchoChar('*');
      passwordPanel.add(setPassword);
      passwordPanel.add(passwordField);
      body.add(passwordPanel);
      //number of players section
      JPanel numOfPlayersPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
      JLabel setNumOfPlayers = new JLabel("Set number of players: ");
      String[] nums = {"4", "3", "2"};
      JComboBox playerNumChoice = new JComboBox(nums);
      numOfPlayersPanel.add(setNumOfPlayers);
      numOfPlayersPanel.add(playerNumChoice);
      body.add(numOfPlayersPanel);
      //start-abort section
      JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
      JButton startButton = new JButton("START");
      JButton stopButton = new JButton("STOP");
      stopButton.setVisible(false);
      buttonsPanel.add(startButton);
      buttonsPanel.add(stopButton);
      body.add(buttonsPanel);
      add(body, layout.CENTER);
   
      //FOOTER - number of players/clients logged in (hidden until start is pressed)
      JPanel numOfActiveClientsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
      JLabel activeClientsLabel = new JLabel("Players (Clients) active: ");
      numOfActiveClientsField = new JTextField("0", 1);
      numOfActiveClientsPanel.add(activeClientsLabel);
      numOfActiveClientsPanel.add(numOfActiveClientsField);
      numOfActiveClientsPanel.setVisible(false);
      numOfActiveClientsField.setEditable(false);
      add(numOfActiveClientsPanel, layout.SOUTH);
   
      //FINALIZE GUI
      setTitle("SeaLudo Server 1.0");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setResizable(false);
      pack();
      setLocationRelativeTo(null);
      setVisible(true);
   
   
      //ACTION LISTENERS
   
      //Start button
      startButton.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               if (!String.valueOf(passwordField.getPassword()).equals("")) {
                  String numString = (String) playerNumChoice.getSelectedItem();
                  int num = Integer.parseInt(numString);
                  System.out.println("Number of players: " + num);
                  String password = String.valueOf(passwordField.getPassword());
                  stopButton.setVisible(true);
                  numOfActiveClientsPanel.setVisible(true);
                  startButton.setEnabled(false);
                  stopButton.setEnabled(true);
               
               //starting server in a separate Thread
                  startThread = new StartServerThread(num, password, numOfActiveClientsField);
                  startThread.start();
               } else {
                  JOptionPane.showMessageDialog(null, "Password required.");
               }
            }
         });
   
      //Stop button
      stopButton.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               if (!writers.isEmpty()) {
                  try {
                     ss.close();
                     JOptionPane.showMessageDialog(null, "Server terminated.");
                  } catch (IOException e) {
                     e.printStackTrace();
                  }
               } else {
                  JOptionPane.showMessageDialog(null, "Server exited.");
               }
               System.exit(0);
            }
         });
   
   }

   /**
    * inner Thread class that starts the actual server connectivity
    */
   class StartServerThread extends Thread {
      int num;
      String password;
      JTextField numOfActiveClientsField;
   
      /**
       * constructor
       * @param num number of players selected
       * @param password password entered
       */
      public StartServerThread(int num, String password, JTextField numOfActiveClientsField) {
         this.num = num;
         this.password = password;
         this.numOfActiveClientsField = numOfActiveClientsField;
      }
   
      @Override
      public void run() {
         LudoServer netServer = new LudoServer(num, password, numOfActiveClientsField);
      }
   }

   /**
    * Thread class that validates the password entered
    * by the client and sends back
    * data needed for in-game communication with the client
    */
   class ValidationThread extends Thread {
      String password;
      ServerSocket ssStart = null;
      String playerName = "";
   
      /**
       * constructor
       * @param password password that will be validated
       */
      public ValidationThread(String password) {
         this.password = password;
      }
   
      @Override
      public void run() {
         Socket csStart = null;
         BufferedReader in = null;
         PrintWriter out = null;
         try {
            ssStart = new ServerSocket(12344); //initializing socket
            while (true) {
               csStart = ssStart.accept();
               System.out.println("client accepted for validation");
               in = new BufferedReader(new InputStreamReader(csStart.getInputStream()));
               out = new PrintWriter(new OutputStreamWriter(csStart.getOutputStream()));
            
               //reading password sent by the client
               String receivedPassword = in.readLine();
               System.out.println("password received");
               //checking if it matches the password entered on server
               if (receivedPassword.equals(password)) {
                  out.println("CORRECT_PASSWORD");
                  out.flush();
                  System.out.println("password correct, flushed");
                  out.println("" + numOfPlayers); //sending the number of players
                  out.flush();
                  System.out.println("Number of players sent to client: " + numOfPlayers);
                  playerName = in.readLine(); //reading player's name
                  playerNames.add(playerName);
                  System.out.println("Welcome, " + playerName + " !");
               } else {
                  out.println("INCORRECT_PASSWORD");
                  out.flush();
                  System.out.println("password incorrect, flushed");
               }
               csStart.close(); //end of the connection
            }
         } catch (IOException e) {
            e.printStackTrace();
         }
      
      }
   }


   /************************************************************************************************
    * SERVER - CLIENT THREAD      (IMPORTANT)                                                      *
    ***********************************************************************************************/


   class ThreadServer extends Thread {
      Socket cs = null;
      String name;
   
      /**
       *
       * @param cs client socket
       * @param name player's name
       */
      public ThreadServer(Socket cs, String name) {
         this.cs = cs;
         this.name = name;
         numOfConnectedClients = writers.size(); //size of output streams array list is the number of clients connected
      }
   
      @Override
      public void run() {
         System.out.println("Server thread started");
         try {
            ObjectInputStream ois = new ObjectInputStream(cs.getInputStream()); //input stream
         
            //assigning color to each client based on the order of joining the game (connecting with the server)
            writers.get(writers.size() - 1).writeObject("" + writers.size());
            writers.get(writers.size() - 1).flush();
            System.out.println("Color assigned by server" + writers.size());
            //notifying all other clients that this one is in the game
            for (ObjectOutputStream oos : writers) {
               if (writers.indexOf(oos) != playerNames.indexOf(name)) {
                  oos.writeObject("new_player");
                  oos.writeObject(this.name);
                  oos.flush();
               }
            }
            //sending the names of all connected clients to every client
            for (ObjectOutputStream oos : writers) {
               oos.writeObject(getPlayerNames());
               oos.flush();
            }
            //initial turn is 1
            int turn = 1;
            //reading - writing loop
            while (true) {
               Object obj = ois.readObject(); //read object
               //if it's string....
               if (obj instanceof String) {
                  String o = (String) obj;
                  System.out.println("READ:   " + o);
                  //checking if the request is to order clients to increase turn
                  if (o.equals("INCREASE_TURN")) {
                     turn++;
                     if (turn > numOfPlayers) {
                        turn = 1;
                     }
                     System.out.println("Turn: " + turn);
                  
                     //ordering all other clients to increase the turn
                     for (ObjectOutputStream oos : writers) {
                        if (writers.indexOf(oos) != playerNames.indexOf(name)) {
                           oos.writeObject("increase_turn");
                           oos.flush();
                        }
                     }
                     System.out.println("Server ordered clients to increase turn successfully");
                  } else {
                     for (ObjectOutputStream oos : writers) {
                        oos.writeObject(this.name+": "+o);
                        oos.flush();
                     }
                  }
               //if the received object is of type MovementData, server will only forward that object to all other clients
               } else if (obj instanceof MovementData) {
                  for (ObjectOutputStream oos : writers) {
                     if (writers.indexOf(oos) != playerNames.indexOf(name)) {
                        oos.writeObject(obj);
                        oos.flush();
                     }
                  }
                  System.out.println("Movement data sent to clients");
               }
            
            }
         } catch (IOException e) {
            System.out.println(e.getMessage());
            try {
               cs.close(); //end of connection, closing socket
               System.out.println("Client out.");
               //decreasing the number of connected clients
               numOfConnectedClients--;
               //removing output stream for this client from the list
               writers.remove(writers.get(writers.size()-1));
            } catch (IOException ex) {
            }
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
         }
      }
   }

   /**********************************************************************************************/


   public ArrayList <String> getPlayerNames(){
      return playerNames;
   }


   public static void main(String[] args) {
      LudoServer ls = new LudoServer();
   }
}
