/**
 * Project Sea Ludo
 * Course: ISTE-121-800
 * Team Name: Ihor Babenko & Varenichki Inc.
 * @author Evgeniya Samsonova
 * @author Luka Crnogorac
 * @author Ihor Babenko
 */

import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MessengerClient extends JFrame {

   //=====================
   // ATTRIBUTES
   //=====================

   private JTextField messageField;    //messageField - JTextField where the messages are written
   private JButton sendButton;   //messageField - JTextField where the messages are written
   private JTextArea textAreaChat;    //textAreaChat - the textArea where the messages are displayed
   private JTextArea textAreaUser;    //textAreaUser - the textArea where the users are displayed

   /**
    * Default MessengerClient constructor
    * handles the creation of the GUI
    */

   public MessengerClient(SeaLudo sl) {
   
   
      //=====================
      // GUI CREATION
      //=====================
   
      //construct components
      JLabel jlTopTitle = new JLabel("Game chat", SwingConstants.CENTER);
      JLabel jlUsers = new JLabel("Users online", SwingConstants.CENTER);
      sendButton = new JButton ("send");
      messageField = new JTextField (5);
      textAreaChat = new JTextArea (5, 5);
      JScrollPane scrollChat = new JScrollPane(textAreaChat);
      textAreaUser = new JTextArea (5, 5);
      JScrollPane scrollUser = new JScrollPane(textAreaUser);
   
      //adjust size and set layout
      setPreferredSize (new Dimension (350, 350));
      setLayout (null);
      textAreaChat.setLineWrap(true);
      textAreaChat.setWrapStyleWord(true);
   
   
      textAreaChat.setEditable(false);
      textAreaUser.setEditable(false);
   
      //add components
      add (sendButton);
      add (jlTopTitle);
      add (messageField);
      add (scrollUser);
      add (scrollChat);
      add (jlUsers);
   
      //set component bounds (only needed by Absolute Positioning)
      sendButton.setBounds (365, 395, 105, 30);
      jlTopTitle.setBounds (0, 0, 475, 25);
      messageField.setBounds (8, 395, 340, 30);
      scrollChat.setBounds (165, 30, 305, 355);
      scrollUser.setBounds (8, 55, 150, 330);
      jlUsers.setBounds (8, 30, 150, 25);
   
   
      this.setTitle("Ludo game chat");
      this.setSize(493, 470);
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setResizable(false);
      this.setVisible(true);
   
   
      //Action Listener for clicking the send button
      sendButton.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent le) {
            //MessengerClient.this.sendMessage();//call sendMessage method which handles sending the message
            //takes text from textField and sends to Ludo
               sl.sendMessageFromChatToServer(messageField.getText());
               messageField.setText(""); //clear messageField
            }
         });
   
      //allows listen "send" button by clicking ENTER
      this.getRootPane().setDefaultButton(sendButton);
   }



   //getter of Right area (Chat)
   public JTextArea getTextAreaChat() {
      return textAreaChat;
   }

   //getter of Left area (Users)
   public JTextArea getTextAreaUser() {
      return textAreaUser;
   }

   public void updateUserList(String list){
      textAreaUser.setText(" ");
      textAreaUser.setText(list);
   }


   /*class ClientThread extends Thread {

       //input BufferedReader, represents the individual client's input
       //this is used to accept messages from the server
       BufferedReader in;
       //output PrintWriter represents the individual client's output
       //this is used to send messages to the server
       PrintWriter out;

       /**
        * parametrized ClientThread constructor, accepts in and out parameters
        *
        * @param in  - the clients input
        * @param out - the clients output


       public ClientThread(BufferedReader in, PrintWriter out) {
           this.in = in;
           this.out = out;
       }

       /**
        * run method for threads


       public void run() {
           try {
               String incomingMessage;//holds the incoming message from the server
               while ((incomingMessage = in.readLine()) != null) { //append incoming messages to textArea. They arrive into the input BufferedReader
                   textArea.append(incomingMessage + "\n");
               }
           }
           catch(java.net.ConnectException ce) {
               run();
           }
           catch (IOException ioe) {
               System.err.println(ioe.getMessage());
           }
           finally{
               JOptionPane.showMessageDialog(MessengerClient.this,"Connection to the server has been lost.","Connection lost",JOptionPane.INFORMATION_MESSAGE);
               System.exit(0);
           }
       }
   }*/

   //=====================
   // MAIN
   //=====================

/*    public static void main(String args[]) {
       new MessengerClient(); //instantiate MessengerClient, this starts the gui and program
   }*/
}