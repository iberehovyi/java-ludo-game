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
import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.*;
import java.util.*;
import java.awt.Point;


public class SeaLudo extends JFrame {
   //=============================
   // ATTRIBUTES
   //=============================

   //colors to be used as attributes
   private Color blue = new Color(125, 219, 226);
   private Color green = new Color(193, 214, 98);
   private Color iceBlue = new Color(159, 220, 224);

   //background panel  and start panel
   private BackgroundPanel backgroundPanel = null;
   private StartPanel panelStarting = null;

   //buttons for turtles to be clicked on and played with
   private JButton red1 = null;
   private JButton red2 = null;
   private JButton red3 = null;
   private JButton red4 = null;

   private JButton green1 = null;
   private JButton green2 = null;
   private JButton green3 = null;
   private JButton green4 = null;

   private JButton yellow1 = null;
   private JButton yellow2 = null;
   private JButton yellow3 = null;
   private JButton yellow4 = null;

   private JButton blue1 = null;
   private JButton blue2 = null;
   private JButton blue3 = null;
   private JButton blue4 = null;

   //points for paths
   private ArrayList<Point> pathYellow = new ArrayList<Point>();
   private ArrayList<Point> pathGreen = new ArrayList<Point>();
   private ArrayList<Point> pathBlue = new ArrayList<Point>();
   private ArrayList<Point> pathRed = new ArrayList<Point>();

   private JPanel menuPanel = null;
   private JButton btnExit = null;
   private JButton btnRestart = null;

   Player playerRed;
   Player playerYellow;
   Player playerGreen;
   Player playerBlue;

   RollDice diceRoll = null;
   boolean redNotClicked = true;
   boolean yellowNotClicked = true;
   boolean greenNotClicked = true;
   boolean blueNotClicked = true;

   private int diceValue = -1;

   //walking and skipping logic

   boolean red0skip = true;
   boolean red1skip = true;
   boolean red2skip = true;
   boolean red3skip = true;

   boolean redOverlapFinal0 = true;
   boolean redOverlapFinal1 = true;
   boolean redOverlapFinal2 = true;
   boolean redOverlapFinal3 = true;

   boolean yellow0skip = true;
   boolean yellow1skip = true;
   boolean yellow2skip = true;
   boolean yellow3skip = true;

   boolean yellowOverlapFinal0 = true;
   boolean yellowOverlapFinal1 = true;
   boolean yellowOverlapFinal2 = true;
   boolean yellowOverlapFinal3 = true;

   boolean green0skip = true;
   boolean green1skip = true;
   boolean green2skip = true;
   boolean green3skip = true;

   boolean greenOverlapFinal0 = true;
   boolean greenOverlapFinal1 = true;
   boolean greenOverlapFinal2 = true;
   boolean greenOverlapFinal3 = true;

   boolean blue0skip = true;
   boolean blue1skip = true;
   boolean blue2skip = true;
   boolean blue3skip = true;

   boolean blueOverlapFinal0 = true;
   boolean blueOverlapFinal1 = true;
   boolean blueOverlapFinal2 = true;
   boolean blueOverlapFinal3 = true;

   boolean blockDice = false;


   //MENU PANEL
   private JPanel leftMenuPanel = new JPanel();

   private JButton btnSeaTheme;
   private JButton btnForesttheme;
   private JButton btnIceTheme;

   private JButton btnLadybugIcon;
   private JButton btnTurtleIcon;
   private JButton btnPenguinIcon;
   private JButton btnBabenkoIcon;


   private JTextArea txtInstructions;

   private JLabel lblHeader;
   private JPanel jpHeader;
   private JPanel jpChangeIcon;
   private JPanel buttonsPanelChangeIcon;
   private JPanel jpRestartExit;
   private JLabel lblChangeIcon;

   private JPanel jpInstructions;
   private JLabel lblInstructions;




   //=======================================
   //networking attributes
   private MessengerClient mc;
   private Socket socket = null; //client socket
   private ObjectInputStream ois = null; //input stream
   private ObjectOutputStream oos = null; //output stream

   private String assignedColor = ""; //color that will be assigned by the server
   private int assignedColorNum; //number that represents the color
   private int numOfActivePlayers = 1; //number of active players
   private ArrayList<String> playersList; //list of names of active players
   //=======================================



   //=============================
   // CONSTRUCTOR
   //=============================

   /**
    * constructor sea ludo for the main logic of the game, handles methods for game running and player
    */

   SeaLudo() {
   
   
      //create background and tokens
   
      try {
         this.setVisible(false);
         mc = new MessengerClient(this);
         mc.setVisible(false);
      
         panelStarting = new StartPanel(this, this, mc);
      
         //action listener for start button from start panel
      
      
         //create background panel
         this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
         backgroundPanel = new BackgroundPanel("seaboard.png");
      
         //=====================
         // LEFT MENU
         //=====================
      
      
      /*
      panels for the left menu
      creates panel, labels and buttons for header, theme changing and icon changing
      */
      
         add(leftMenuPanel); //add right menu panel on the right
         leftMenuPanel.setBackground(blue);
         leftMenuPanel.setPreferredSize(new Dimension(300, 750));
         leftMenuPanel.setLayout(new BoxLayout(leftMenuPanel, BoxLayout.Y_AXIS));
         leftMenuPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      
         //panel header
         jpHeader = new JPanel();
         leftMenuPanel.add(jpHeader);
         jpHeader.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
      
         //header label
         lblHeader = new JLabel();
         jpHeader.add(lblHeader);
         jpHeader.setBackground(blue);
         jpHeader.add(lblHeader);
         lblHeader.setIcon(new ImageIcon("nameSea.png"));
         lblHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
      
      
         // THEME PANEL
         JPanel jpChangeTheme = new JPanel();
         leftMenuPanel.add(jpChangeTheme);
         jpChangeTheme.setBackground(blue);
      
         JLabel lblChangeTheme = new JLabel();
         lblChangeTheme.setAlignmentX(Component.CENTER_ALIGNMENT);
         jpChangeTheme.add(lblChangeTheme);
         lblChangeTheme.setIcon(new ImageIcon("changetheme.png"));
      
         JPanel buttonsPanelChangeTheme = new JPanel();
         leftMenuPanel.add(buttonsPanelChangeTheme);
         buttonsPanelChangeTheme.setBackground(blue);
         buttonsPanelChangeTheme.setLayout(new GridLayout(0, 1, 0, 0));
      
      /*
      buttons for playing
      */
      
         btnSeaTheme = new JButton();
         buttonsPanelChangeTheme.add(btnSeaTheme);
         btnSeaTheme.setIcon(new ImageIcon("seathemebutton.png"));
         btnSeaTheme.setOpaque(true);
         btnSeaTheme.setContentAreaFilled(false);
         btnSeaTheme.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      
         btnForesttheme = new JButton();
         buttonsPanelChangeTheme.add(btnForesttheme);
         btnForesttheme.setIcon(new ImageIcon("forestthemebutton.png"));
         btnForesttheme.setOpaque(true);
         btnForesttheme.setContentAreaFilled(false);
         btnForesttheme.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      
         btnIceTheme = new JButton();
         buttonsPanelChangeTheme.add(btnIceTheme);
         btnIceTheme.setIcon(new ImageIcon("icethemebutton.png"));
         btnIceTheme.setOpaque(true);
         btnIceTheme.setContentAreaFilled(false);
         btnIceTheme.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      
         //===================================
         // CHANGE THEME ACTION LISTENERS
         //===================================
      
         btnSeaTheme.addActionListener(
                    new ActionListener() {
                    
                       @Override
                       public void actionPerformed(ActionEvent e) {
                       
                          backgroundPanel.setImage("seaboard.png");
                          lblHeader.setIcon(new ImageIcon("nameSea.png"));
                          leftMenuPanel.setBackground(blue);
                          menuPanel.setBackground(blue);
                          jpHeader.setBackground(blue);
                          jpChangeTheme.setBackground(blue);
                          jpInstructions.setBackground(blue);
                          jpChangeIcon.setBackground(blue);
                          diceRoll.setBackground(blue);
                          jpChangeTheme.setBackground(blue);
                          buttonsPanelChangeTheme.setBackground(blue);
                          buttonsPanelChangeIcon.setBackground(blue);
                          jpRestartExit.setBackground(blue);
                       
                       }
                    });
      
      
         btnForesttheme.addActionListener(
                    new ActionListener() {
                       @Override
                       public void actionPerformed(ActionEvent e) {
                          backgroundPanel.setImage("forestboard.png");
                          lblHeader.setIcon(new ImageIcon("nameForest.png"));
                          leftMenuPanel.setBackground(green);
                          jpInstructions.setBackground(green);
                          jpChangeTheme.setBackground(green);
                          buttonsPanelChangeTheme.setBackground(green);
                          menuPanel.setBackground(green);
                          jpHeader.setBackground(green);
                          jpChangeIcon.setBackground(green);
                          diceRoll.setBackground(green);
                          buttonsPanelChangeIcon.setBackground(green);
                          jpRestartExit.setBackground(green);
                          jpChangeIcon.setBackground(green);
                       
                       
                       }
                    });
      
      
         btnIceTheme.addActionListener(
                    new ActionListener() {
                       @Override
                       public void actionPerformed(ActionEvent e) {
                          backgroundPanel.setImage("iceboard.png");
                          lblHeader.setIcon(new ImageIcon("nameIce.png"));
                          leftMenuPanel.setBackground(iceBlue);
                          menuPanel.setBackground(iceBlue);
                          jpHeader.setBackground(iceBlue);
                          jpChangeTheme.setBackground(iceBlue);
                          jpInstructions.setBackground(iceBlue);
                          diceRoll.setBackground(iceBlue);
                          buttonsPanelChangeTheme.setBackground(iceBlue);
                          buttonsPanelChangeIcon.setBackground(iceBlue);
                          jpRestartExit.setBackground(iceBlue);
                          jpChangeIcon.setBackground(iceBlue);
                       
                       }
                    });
      
         // ICONS PANEL
         jpChangeIcon = new JPanel();
         leftMenuPanel.add(jpChangeIcon);
         jpChangeIcon.setBackground(blue);
         lblChangeIcon = new JLabel();
         lblChangeIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
         jpChangeIcon.add(lblChangeIcon);
         lblChangeIcon.setIcon(new ImageIcon("selectplayers.png"));
      
         buttonsPanelChangeIcon = new JPanel();
         leftMenuPanel.add(buttonsPanelChangeIcon);
         buttonsPanelChangeIcon.setBackground(blue);
         buttonsPanelChangeIcon.setLayout(new GridLayout(4, 1, 5, 5));
      
      /*
        buttons for playing
       */
      
         btnTurtleIcon = new JButton();
         buttonsPanelChangeIcon.add(btnTurtleIcon);
         btnTurtleIcon.setIcon(new ImageIcon("selectTurtle.png"));
         btnTurtleIcon.setOpaque(true);
         btnTurtleIcon.setContentAreaFilled(false);
         btnTurtleIcon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      
         btnLadybugIcon = new JButton();
         buttonsPanelChangeIcon.add(btnLadybugIcon);
         btnLadybugIcon.setIcon(new ImageIcon("selectBug.png"));
         btnLadybugIcon.setOpaque(true);
         btnLadybugIcon.setContentAreaFilled(false);
         btnLadybugIcon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      
         btnPenguinIcon = new JButton();
         buttonsPanelChangeIcon.add(btnPenguinIcon);
         btnPenguinIcon.setIcon(new ImageIcon("selectPenguin.png"));
         btnPenguinIcon.setOpaque(true);
         btnPenguinIcon.setContentAreaFilled(false);
         btnPenguinIcon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      
         btnBabenkoIcon = new JButton();
         buttonsPanelChangeIcon.add(btnBabenkoIcon);
         btnBabenkoIcon.setIcon(new ImageIcon("selectBabenko.png"));
         btnBabenkoIcon.setOpaque(true);
         btnBabenkoIcon.setContentAreaFilled(false);
         btnBabenkoIcon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      
         /**
          * action listeners to change the icon on click to the set icon
          */
      
         btnBabenkoIcon.addActionListener(
                    new ActionListener() {
                       @Override
                       public void actionPerformed(ActionEvent e) {
                       
                          red1.setIcon(new ImageIcon("babenkored.png"));
                          red2.setIcon(new ImageIcon("babenkored.png"));
                          red3.setIcon(new ImageIcon("babenkored.png"));
                          red4.setIcon(new ImageIcon("babenkored.png"));
                       
                          blue1.setIcon(new ImageIcon("babenkoblue.png"));
                          blue2.setIcon(new ImageIcon("babenkoblue.png"));
                          blue3.setIcon(new ImageIcon("babenkoblue.png"));
                          blue4.setIcon(new ImageIcon("babenkoblue.png"));
                       
                          yellow1.setIcon(new ImageIcon("babenkoyellow.png"));
                          yellow2.setIcon(new ImageIcon("babenkoyellow.png"));
                          yellow3.setIcon(new ImageIcon("babenkoyellow.png"));
                          yellow4.setIcon(new ImageIcon("babenkoyellow.png"));
                       
                          green1.setIcon(new ImageIcon("babenkogreen.png"));
                          green2.setIcon(new ImageIcon("babenkogreen.png"));
                          green3.setIcon(new ImageIcon("babenkogreen.png"));
                          green4.setIcon(new ImageIcon("babenkogreen.png"));
                       
                       }
                    });
      
         /**
          * action listeners to change the icon on click to the set icon
          */
      
      
         btnLadybugIcon.addActionListener(
                    new ActionListener() {
                       @Override
                       public void actionPerformed(ActionEvent e) {
                       
                          red1.setIcon(new ImageIcon("lbred.png"));
                          red2.setIcon(new ImageIcon("lbred.png"));
                          red3.setIcon(new ImageIcon("lbred.png"));
                          red4.setIcon(new ImageIcon("lbred.png"));
                       
                          blue1.setIcon(new ImageIcon("lbblue.png"));
                          blue2.setIcon(new ImageIcon("lbblue.png"));
                          blue3.setIcon(new ImageIcon("lbblue.png"));
                          blue4.setIcon(new ImageIcon("lbblue.png"));
                       
                          yellow1.setIcon(new ImageIcon("lbyellow.png"));
                          yellow2.setIcon(new ImageIcon("lbyellow.png"));
                          yellow3.setIcon(new ImageIcon("lbyellow.png"));
                          yellow4.setIcon(new ImageIcon("lbyellow.png"));
                       
                          green1.setIcon(new ImageIcon("lbgreen.png"));
                          green2.setIcon(new ImageIcon("lbgreen.png"));
                          green3.setIcon(new ImageIcon("lbgreen.png"));
                          green4.setIcon(new ImageIcon("lbgreen.png"));
                       
                       }
                    });
      
         /**
          * action listeners to change the icon on click to the set icon
          */
      
         btnTurtleIcon.addActionListener(
                    new ActionListener() {
                       @Override
                       public void actionPerformed(ActionEvent e) {
                       
                          red1.setIcon(new ImageIcon("red.png"));
                          red2.setIcon(new ImageIcon("red.png"));
                          red3.setIcon(new ImageIcon("red.png"));
                          red4.setIcon(new ImageIcon("red.png"));
                       
                          blue1.setIcon(new ImageIcon("blue.png"));
                          blue2.setIcon(new ImageIcon("blue.png"));
                          blue3.setIcon(new ImageIcon("blue.png"));
                          blue4.setIcon(new ImageIcon("blue.png"));
                       
                          yellow1.setIcon(new ImageIcon("yellow.png"));
                          yellow2.setIcon(new ImageIcon("yellow.png"));
                          yellow3.setIcon(new ImageIcon("yellow.png"));
                          yellow4.setIcon(new ImageIcon("yellow.png"));
                       
                          green1.setIcon(new ImageIcon("green.png"));
                          green2.setIcon(new ImageIcon("green.png"));
                          green3.setIcon(new ImageIcon("green.png"));
                          green4.setIcon(new ImageIcon("green.png"));
                       
                       //backgroundPanel = new BackgroundPanel("seaboard.jpg");
                       }
                    });
      
         /**
          * action listeners to change the icon on click to the set icon
          */
      
         btnPenguinIcon.addActionListener(
                    new ActionListener() {
                       @Override
                       public void actionPerformed(ActionEvent e) {
                       
                          red1.setIcon(new ImageIcon("penguinRed.png"));
                          red2.setIcon(new ImageIcon("penguinRed.png"));
                          red3.setIcon(new ImageIcon("penguinRed.png"));
                          red4.setIcon(new ImageIcon("penguinRed.png"));
                       
                          blue1.setIcon(new ImageIcon("penguinBlue.png"));
                          blue2.setIcon(new ImageIcon("penguinBlue.png"));
                          blue3.setIcon(new ImageIcon("penguinBlue.png"));
                          blue4.setIcon(new ImageIcon("penguinBlue.png"));
                       
                          yellow1.setIcon(new ImageIcon("penguinYellow.png"));
                          yellow2.setIcon(new ImageIcon("penguinYellow.png"));
                          yellow3.setIcon(new ImageIcon("penguinYellow.png"));
                          yellow4.setIcon(new ImageIcon("penguinYellow.png"));
                       
                          green1.setIcon(new ImageIcon("penguinGreen.png"));
                          green2.setIcon(new ImageIcon("penguinGreen.png"));
                          green3.setIcon(new ImageIcon("penguinGreen.png"));
                          green4.setIcon(new ImageIcon("penguinGreen.png"));
                       
                       }
                    });
      
      
         /*
          * sets layout to null
          * layout null removes ANY default layout
          * enables absolute positioning of elements on background panel
          * enables usage of point object for point creation
          */
      
         backgroundPanel.setLayout(null); //sets layout for absolute positioning and object point creation
         add(backgroundPanel); //add background panel on the left
         backgroundPanel.setPreferredSize(new Dimension(750, 750));
      
         //=============================
         // MENU PANEL
         //=============================
      
         menuPanel = new JPanel();
         add(menuPanel); //add right menu panel on the right
         menuPanel.setBackground(blue);
         menuPanel.setPreferredSize(new Dimension(350, 750));
         menuPanel.setLayout(new GridLayout(0, 1, 5, 5));
      
      /*
      ads main points to the board to be used for pawn moving
      enables the game movement logic to work
       */
      
         // -----  Main points
      
         Point p1 = new Point(300, 600);
         Point p2 = new Point(300, 550);
         Point p3 = new Point(300, 500);
         Point p4 = new Point(300, 450);
      
         Point p5 = new Point(250, 400);
         Point p6 = new Point(200, 400);
         Point p7 = new Point(150, 400);
         Point p8 = new Point(100, 400);
         Point p9 = new Point(50, 400);
      
         Point p10 = new Point(50, 350);
      
         Point p11 = new Point(50, 300);
         Point p12 = new Point(100, 300);
         Point p13 = new Point(150, 300);
         Point p14 = new Point(200, 300);
         Point p15 = new Point(250, 300);
      
         Point p16 = new Point(300, 250);
         Point p17 = new Point(300, 200);
         Point p18 = new Point(300, 150);
         Point p19 = new Point(300, 100);
         Point p20 = new Point(300, 50);
      
         Point p21 = new Point(350, 50);
      
         Point p22 = new Point(400, 50);
         Point p23 = new Point(400, 100);
         Point p24 = new Point(400, 150);
         Point p25 = new Point(400, 200);
         Point p26 = new Point(400, 250);
      
         Point p27 = new Point(450, 300);
         Point p28 = new Point(500, 300);
         Point p29 = new Point(550, 300);
         Point p30 = new Point(600, 300);
         Point p31 = new Point(650, 300);
      
         Point p32 = new Point(650, 350);
      
         Point p33 = new Point(650, 400);
         Point p34 = new Point(600, 400);
         Point p35 = new Point(550, 400);
         Point p36 = new Point(500, 400);
         Point p37 = new Point(450, 400);
      
         Point p38 = new Point(400, 450);
         Point p39 = new Point(400, 500);
         Point p40 = new Point(400, 550);
         Point p41 = new Point(400, 600);
         Point p42 = new Point(400, 650);
      
         Point p43 = new Point(350, 650);
         Point p44 = new Point(300, 650);
      
         //==================================
         // MAKE POINTS AND ADD THEM TO PATHS
         //==================================
      
      
         // -----  Finish points
      
         Point pR1 = new Point(350, 600);
         Point pR2 = new Point(350, 550);
         Point pR3 = new Point(350, 500);
         Point pR4 = new Point(350, 450);
      
         Point pY1 = new Point(100, 350);
         Point pY2 = new Point(150, 350);
         Point pY3 = new Point(200, 350);
         Point pY4 = new Point(250, 350);
      
         Point pG1 = new Point(350, 100);
         Point pG2 = new Point(350, 150);
         Point pG3 = new Point(350, 200);
         Point pG4 = new Point(350, 250);
      
         Point pB1 = new Point(600, 350);
         Point pB2 = new Point(550, 350);
         Point pB3 = new Point(500, 350);
         Point pB4 = new Point(450, 350);
      
         // -----  Adding points to the paths
      
         // -------- R E D
      
         pathRed.add(p1);
         pathRed.add(p2);
         pathRed.add(p3);
         pathRed.add(p4);
         pathRed.add(p5);
         pathRed.add(p6);
         pathRed.add(p7);
         pathRed.add(p8);
         pathRed.add(p9);
         pathRed.add(p10);
         pathRed.add(p11);
         pathRed.add(p12);
         pathRed.add(p13);
         pathRed.add(p14);
         pathRed.add(p15);
         pathRed.add(p16);
         pathRed.add(p17);
         pathRed.add(p18);
         pathRed.add(p19);
         pathRed.add(p20);
         pathRed.add(p21);
         pathRed.add(p22);
         pathRed.add(p23);
         pathRed.add(p24);
         pathRed.add(p25);
         pathRed.add(p26);
         pathRed.add(p27);
         pathRed.add(p28);
         pathRed.add(p29);
         pathRed.add(p30);
         pathRed.add(p31);
         pathRed.add(p32);
         pathRed.add(p33);
         pathRed.add(p34);
         pathRed.add(p35);
         pathRed.add(p36);
         pathRed.add(p37);
         pathRed.add(p38);
         pathRed.add(p39);
         pathRed.add(p40);
         pathRed.add(p41);
         pathRed.add(p42);
         pathRed.add(p43);
      
         pathRed.add(pR1);
         pathRed.add(pR2);
         pathRed.add(pR3);
         pathRed.add(pR4);
      
         // -------- Y E L L O W
      
         pathYellow.add(p12);
         pathYellow.add(p13);
         pathYellow.add(p14);
         pathYellow.add(p15);
         pathYellow.add(p16);
         pathYellow.add(p17);
         pathYellow.add(p18);
         pathYellow.add(p19);
         pathYellow.add(p20);
         pathYellow.add(p21);
         pathYellow.add(p22);
         pathYellow.add(p23);
         pathYellow.add(p24);
         pathYellow.add(p25);
         pathYellow.add(p26);
         pathYellow.add(p27);
         pathYellow.add(p28);
         pathYellow.add(p29);
         pathYellow.add(p30);
         pathYellow.add(p31);
         pathYellow.add(p32);
         pathYellow.add(p33);
         pathYellow.add(p34);
         pathYellow.add(p35);
         pathYellow.add(p36);
         pathYellow.add(p37);
         pathYellow.add(p38);
         pathYellow.add(p39);
         pathYellow.add(p40);
         pathYellow.add(p41);
         pathYellow.add(p42);
         pathYellow.add(p43);
         pathYellow.add(p44);
         pathYellow.add(p1);
         pathYellow.add(p2);
         pathYellow.add(p3);
         pathYellow.add(p4);
         pathYellow.add(p5);
         pathYellow.add(p6);
         pathYellow.add(p7);
         pathYellow.add(p8);
         pathYellow.add(p9);
         pathYellow.add(p10);
      
         pathYellow.add(pY1);
         pathYellow.add(pY2);
         pathYellow.add(pY3);
         pathYellow.add(pY4);
      
         // -------- G R E E N
      
         pathGreen.add(p23);
         pathGreen.add(p24);
         pathGreen.add(p25);
         pathGreen.add(p26);
         pathGreen.add(p27);
         pathGreen.add(p28);
         pathGreen.add(p29);
         pathGreen.add(p30);
         pathGreen.add(p31);
         pathGreen.add(p32);
         pathGreen.add(p33);
         pathGreen.add(p34);
         pathGreen.add(p35);
         pathGreen.add(p36);
         pathGreen.add(p37);
         pathGreen.add(p38);
         pathGreen.add(p39);
         pathGreen.add(p40);
         pathGreen.add(p41);
         pathGreen.add(p42);
         pathGreen.add(p43);
         pathGreen.add(p44);
         pathGreen.add(p1);
         pathGreen.add(p2);
         pathGreen.add(p3);
         pathGreen.add(p4);
         pathGreen.add(p5);
         pathGreen.add(p6);
         pathGreen.add(p7);
         pathGreen.add(p8);
         pathGreen.add(p9);
         pathGreen.add(p10);
         pathGreen.add(p11);
         pathGreen.add(p12);
         pathGreen.add(p13);
         pathGreen.add(p14);
         pathGreen.add(p15);
         pathGreen.add(p16);
         pathGreen.add(p17);
         pathGreen.add(p18);
         pathGreen.add(p19);
         pathGreen.add(p20);
         pathGreen.add(p21);
      
         pathGreen.add(pG1);
         pathGreen.add(pG2);
         pathGreen.add(pG3);
         pathGreen.add(pG4);
      
         // -------- B L U E
      
      
         pathBlue.add(p34);
         pathBlue.add(p35);
         pathBlue.add(p36);
         pathBlue.add(p37);
         pathBlue.add(p38);
         pathBlue.add(p39);
         pathBlue.add(p40);
         pathBlue.add(p41);
         pathBlue.add(p42);
         pathBlue.add(p43);
         pathBlue.add(p44);
         pathBlue.add(p1);
         pathBlue.add(p2);
         pathBlue.add(p3);
         pathBlue.add(p4);
         pathBlue.add(p5);
         pathBlue.add(p6);
         pathBlue.add(p7);
         pathBlue.add(p8);
         pathBlue.add(p9);
         pathBlue.add(p10);
         pathBlue.add(p11);
         pathBlue.add(p12);
         pathBlue.add(p13);
         pathBlue.add(p14);
         pathBlue.add(p15);
         pathBlue.add(p16);
         pathBlue.add(p17);
         pathBlue.add(p18);
         pathBlue.add(p19);
         pathBlue.add(p20);
         pathBlue.add(p21);
         pathBlue.add(p22);
         pathBlue.add(p23);
         pathBlue.add(p24);
         pathBlue.add(p25);
         pathBlue.add(p26);
         pathBlue.add(p27);
         pathBlue.add(p28);
         pathBlue.add(p29);
         pathBlue.add(p30);
         pathBlue.add(p31);
         pathBlue.add(p32);
      
         pathBlue.add(pB1);
         pathBlue.add(pB2);
         pathBlue.add(pB3);
         pathBlue.add(pB4);
      
      
      /*
      starts the dice class to show it on panel
       */
      
         //=============================
         // DICE CLASS
         //=============================
      
         diceRoll = new RollDice(menuPanel, this, panelStarting); //add dice roll class and panel with it
         menuPanel.add(diceRoll);
         menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
      
         //=============================
         // HEADER
         //=============================
      
      /*
      header panel and right menu adds dice box, instructions and buttons to start and reset the game
      */
      
         jpInstructions = new JPanel();
         menuPanel.add(jpInstructions);
         jpInstructions.setBackground(blue);
         jpInstructions.setLayout(new BoxLayout(jpInstructions, BoxLayout.Y_AXIS));
         jpInstructions.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
      
         lblInstructions = new JLabel();
         lblInstructions.setAlignmentX(Component.CENTER_ALIGNMENT);
         jpInstructions.add(lblInstructions);
         lblInstructions.setIcon(new ImageIcon("instructions.png"));
      
         //instructions for the user to play the game
         txtInstructions = new JTextArea("Player RED starts the game! Roll the dice!");
         txtInstructions.setAlignmentX(Component.CENTER_ALIGNMENT);
         jpInstructions.add(txtInstructions);
      
         txtInstructions.setPreferredSize(new Dimension(150, 20));
         txtInstructions.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
      
         txtInstructions.setEditable(false);
         txtInstructions.setLineWrap(true);
         txtInstructions.setWrapStyleWord(true);
      
         //===================================
         // CHANGE THEME ACTION LISTENERS
         //===================================
      
         /**
          * action listeners change the theme to set theme
          * also change colors of the background panels to make the theme fit the style
          */
      
      
         btnSeaTheme.addActionListener(
                    new ActionListener() {
                       @Override
                       public void actionPerformed(ActionEvent e) {
                       
                          backgroundPanel.setImage("seaboard.png");
                          lblHeader.setIcon(new ImageIcon("nameSea.png"));
                          leftMenuPanel.setBackground(blue);
                          menuPanel.setBackground(blue);
                          jpHeader.setBackground(blue);
                          jpChangeTheme.setBackground(blue);
                          jpInstructions.setBackground(blue);
                          jpChangeIcon.setBackground(blue);
                          diceRoll.setBackground(blue);
                          jpChangeTheme.setBackground(blue);
                          buttonsPanelChangeTheme.setBackground(blue);
                          buttonsPanelChangeIcon.setBackground(blue);
                          jpRestartExit.setBackground(blue);
                       
                       }
                    });
      
         /**
          * action listeners change the theme to set theme
          * also change colors of the background panels to make the theme fit the style
          */
      
         btnForesttheme.addActionListener(
                    new ActionListener() {
                       @Override
                       public void actionPerformed(ActionEvent e) {
                          backgroundPanel.setImage("forestboard.png");
                          lblHeader.setIcon(new ImageIcon("nameForest.png"));
                          leftMenuPanel.setBackground(green);
                          jpInstructions.setBackground(green);
                          jpChangeTheme.setBackground(green);
                          buttonsPanelChangeTheme.setBackground(green);
                          menuPanel.setBackground(green);
                          jpHeader.setBackground(green);
                          jpChangeIcon.setBackground(green);
                          diceRoll.setBackground(green);
                          buttonsPanelChangeIcon.setBackground(green);
                          jpRestartExit.setBackground(green);
                          jpChangeIcon.setBackground(green);
                       
                       
                       }
                    });
      
         /**
          * action listeners change the theme to set theme
          * also change colors of the background panels to make the theme fit the style
          */
      
         btnIceTheme.addActionListener(
                    new ActionListener() {
                       @Override
                       public void actionPerformed(ActionEvent e) {
                          backgroundPanel.setImage("iceboard.png");
                          lblHeader.setIcon(new ImageIcon("nameIce.png"));
                          leftMenuPanel.setBackground(iceBlue);
                          menuPanel.setBackground(iceBlue);
                          jpHeader.setBackground(iceBlue);
                          jpChangeTheme.setBackground(iceBlue);
                          jpInstructions.setBackground(iceBlue);
                          diceRoll.setBackground(iceBlue);
                          buttonsPanelChangeTheme.setBackground(iceBlue);
                          buttonsPanelChangeIcon.setBackground(iceBlue);
                          jpRestartExit.setBackground(iceBlue);
                          jpChangeIcon.setBackground(iceBlue);
                       
                       
                       }
                    });
      
      
         //===================================
         // PANEL FOR ExIT AND RESTART BUTTONs
         //===================================
      
         jpRestartExit = new JPanel();
         menuPanel.add(jpRestartExit);
         jpRestartExit.setBackground(blue);
         jpRestartExit.setLayout(new GridLayout(2, 1, 5, 5));
      
      
         //============================
         // RESTART GAME
         //============================
      
         btnRestart = new JButton();
         jpRestartExit.add(btnRestart);
         btnRestart.setAlignmentX(Component.CENTER_ALIGNMENT);
         btnRestart.setIcon(new ImageIcon("restartbutton.png"));
         btnRestart.setOpaque(true);
         btnRestart.setContentAreaFilled(false);
         btnRestart.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
      
         /**
          * action listeners restarts the game
          */
      
         btnRestart.addActionListener(
                    new ActionListener() {
                       @Override
                       public void actionPerformed(ActionEvent actionEvent) {
                          for (int i = 0; i < 4; i++) {
                             setInstruction("Instruction: Game was restarted.\nPlayer RED - roll the dice!");
                             diceRoll.resetTurn();
                             playerRed.getTurtle(i).restartLocation();
                             playerYellow.getTurtle(i).restartLocation();
                             playerGreen.getTurtle(i).restartLocation();
                             playerBlue.getTurtle(i).restartLocation();
                          
                          }
                       }
                    }
            );
      
         //=============================
         // EXIT BUTTON AND RESTART
         //=============================
      
         btnExit = new JButton();
         btnExit.setIcon(new ImageIcon("exitbutton.png"));
      
         jpRestartExit.add(btnExit);
         btnExit.setOpaque(true);
         btnExit.setContentAreaFilled(false);
         btnExit.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
         btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);
      
         /**
          * action listeners to end game on button click
          */
      
         btnExit.addMouseListener(
                    new MouseAdapter() {
                       @Override
                       public void mouseClicked(MouseEvent e) {
                       
                          System.exit(-2);
                       }
                    
                    });
      
      /*
      sets the turtle positions to initial positions
      starts the turtle buttons to enter the game
      sets images of the players
       */
      
         //LOAD IMAGES
         BufferedImage tRed = ImageIO.read(new File("red.png"));
         BufferedImage tGreen = ImageIO.read(new File("green.png"));
         BufferedImage tYellow = ImageIO.read(new File("yellow.png"));
         BufferedImage tBlue = ImageIO.read(new File("blue.png"));
      
         //TURTLES INITIALIZATION
      
         //RED TURTLE
         Point ptInitRed1 = new Point(100, 550);
         Point ptInitRed2 = new Point(150, 550);
         Point ptInitRed3 = new Point(100, 600);
         Point ptInitRed4 = new Point(150, 600);
         red1 = new JButton(new ImageIcon(tRed));
         red2 = new JButton(new ImageIcon(tRed));
         red3 = new JButton(new ImageIcon(tRed));
         red4 = new JButton(new ImageIcon(tRed));
         Turtle turtleRed1 = new Turtle(red1, ptInitRed1);
         Turtle turtleRed2 = new Turtle(red2, ptInitRed2);
         Turtle turtleRed3 = new Turtle(red3, ptInitRed3);
         Turtle turtleRed4 = new Turtle(red4, ptInitRed4);
      
         playerRed = new Player(turtleRed1, turtleRed2, turtleRed3, turtleRed4, pathRed);
      
      
         //BLUE TURTLE
         Point ptInitBlue1 = new Point(550, 550);
         Point ptInitBlue2 = new Point(600, 550);
         Point ptInitBlue3 = new Point(550, 600);
         Point ptInitBlue4 = new Point(600, 600);
         blue1 = new JButton(new ImageIcon(tBlue));
         blue2 = new JButton(new ImageIcon(tBlue));
         blue3 = new JButton(new ImageIcon(tBlue));
         blue4 = new JButton(new ImageIcon(tBlue));
         Turtle turtleBlue1 = new Turtle(blue1, ptInitBlue1);
         Turtle turtleBlue2 = new Turtle(blue2, ptInitBlue2);
         Turtle turtleBlue3 = new Turtle(blue3, ptInitBlue3);
         Turtle turtleBlue4 = new Turtle(blue4, ptInitBlue4);
      
         playerBlue = new Player(turtleBlue1, turtleBlue2, turtleBlue3, turtleBlue4, pathBlue);
      
         //GREEN TURTLE
         Point ptInitGreen1 = new Point(550, 100);
         Point ptInitGreen2 = new Point(600, 100);
         Point ptInitGreen3 = new Point(550, 150);
         Point ptInitGreen4 = new Point(600, 150);
         green1 = new JButton(new ImageIcon(tGreen));
         green2 = new JButton(new ImageIcon(tGreen));
         green3 = new JButton(new ImageIcon(tGreen));
         green4 = new JButton(new ImageIcon(tGreen));
         Turtle turtleGreen1 = new Turtle(green1, ptInitGreen1);
         Turtle turtleGreen2 = new Turtle(green2, ptInitGreen2);
         Turtle turtleGreen3 = new Turtle(green3, ptInitGreen3);
         Turtle turtleGreen4 = new Turtle(green4, ptInitGreen4);
      
         playerGreen = new Player(turtleGreen1, turtleGreen2, turtleGreen3, turtleGreen4, pathGreen);
      
      
         //YELLOW TURTLE
         Point ptInitYellow1 = new Point(100, 100);
         Point ptInitYellow2 = new Point(150, 100);
         Point ptInitYellow3 = new Point(100, 150);
         Point ptInitYellow4 = new Point(150, 150);
      
         yellow1 = new JButton(new ImageIcon(tYellow));
         yellow2 = new JButton(new ImageIcon(tYellow));
         yellow3 = new JButton(new ImageIcon(tYellow));
         yellow4 = new JButton(new ImageIcon(tYellow));
      
         Turtle turtleYellow1 = new Turtle(yellow1, ptInitYellow1);
         Turtle turtleYellow2 = new Turtle(yellow2, ptInitYellow2);
         Turtle turtleYellow3 = new Turtle(yellow3, ptInitYellow3);
         Turtle turtleYellow4 = new Turtle(yellow4, ptInitYellow4);
      
         playerYellow = new Player(turtleYellow1, turtleYellow2, turtleYellow3, turtleYellow4, pathYellow);
      
      
      /*
      add turtles to panels and set backgrounds and color
      */
      
         //add turtles to panels
         for (int i = 0; i < 4; i++) {
            backgroundPanel.add(playerRed.getTurtleLabel(i));
            backgroundPanel.add(playerYellow.getTurtleLabel(i));
            backgroundPanel.add(playerGreen.getTurtleLabel(i));
            backgroundPanel.add(playerBlue.getTurtleLabel(i));
         
            playerRed.getTurtleLabel(i).setOpaque(true);
            playerRed.getTurtleLabel(i).setContentAreaFilled(false);
            playerRed.getTurtleLabel(i).setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
         
            playerYellow.getTurtleLabel(i).setOpaque(true);
            playerYellow.getTurtleLabel(i).setContentAreaFilled(false);
            playerYellow.getTurtleLabel(i).setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
         
            playerGreen.getTurtleLabel(i).setOpaque(true);
            playerGreen.getTurtleLabel(i).setContentAreaFilled(false);
            playerGreen.getTurtleLabel(i).setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
         
            playerBlue.getTurtleLabel(i).setOpaque(true);
            playerBlue.getTurtleLabel(i).setContentAreaFilled(false);
            playerBlue.getTurtleLabel(i).setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
         }
      
         //==================================
         // SET ORIGINAL LOCATION OF TURTLES
         //==================================
      
       /*
        sets original location of turtles at start of game
       */
      
         red1.setLocation(ptInitRed1);
         red2.setLocation(ptInitRed2);
         red3.setLocation(ptInitRed3);
         red4.setLocation(ptInitRed4);
      
         blue1.setLocation(ptInitBlue1);
         blue2.setLocation(ptInitBlue2);
         blue3.setLocation(ptInitBlue3);
         blue4.setLocation(ptInitBlue4);
      
         green1.setLocation(ptInitGreen1);
         green2.setLocation(ptInitGreen2);
         green3.setLocation(ptInitGreen3);
         green4.setLocation(ptInitGreen4);
      
         yellow1.setLocation(ptInitYellow1);
         yellow2.setLocation(ptInitYellow2);
         yellow3.setLocation(ptInitYellow3);
         yellow4.setLocation(ptInitYellow4);
      
      
      
      } catch (IOException ioe) {
         System.out.println("The background image cannot be loaded...");
      }
   
      //==================================
      // FINALIZE GUI
      //==================================
   
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(1200, 750); //each square is 50 px
      pack();
      setLocation(200, 200);
      setResizable(false);
   
   
   
   }



   /**
    * ==================================================================================================================================================================
    * //       THREAD for CLIENT - SERVER communication                                                                                                                   //
    * ====================================================================================================================================================================
    */



   class ClientThread extends Thread {
      String ip; //ip
      SeaLudo sl; //SeaLudo class reference
   
      public ClientThread(SeaLudo sl) {
         ip = panelStarting.getValidIP(); //takes ip from starting panel
         this.sl = sl;
      }
   
      @Override
      public void run() {
      
         try {
            socket = new Socket(ip, 12345);    // socket initialization
            System.out.println("Socket initialized");
            //streams
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("i/o streams initialized");
            String colorNum = (String) ois.readObject(); //reading String received from the server
            //assigning color based on the number
            if (colorNum.equals("1")) {
               assignedColor = "red";
               assignedColorNum = 1;
            } else if (colorNum.equals("2")) {
               assignedColor = "yellow";
               assignedColorNum = 2;
               numOfActivePlayers = 2; //if the color number is 2, it means this client logged the second
            } else if (colorNum.equals("3")) {
               assignedColor = "green";
               assignedColorNum = 3;
               numOfActivePlayers = 3;
            } else if (colorNum.equals("4")) {
               assignedColor = "blue";
               assignedColorNum = 4;
               numOfActivePlayers = 4;
            } else {
               System.out.println("ERROR: Color NOT assigned");
            }
            //if all players logged in and another one logs in, that one will shut down automatically after the warning message
            if (assignedColorNum > panelStarting.getNumberOfPlayers()) {
               JOptionPane.showMessageDialog(null, "The server is full. The game will close. ", "Warning", JOptionPane.WARNING_MESSAGE);
               try {
                  socket.close();
               } catch (IOException e) {
                  System.out.println("Client out.");
                  System.exit(-1);
               }
            }
            System.out.println("Assigned: " + assignedColor);
            if (assignedColorNum != 1) {
               txtInstructions.setText("Player RED starts the game. You must wait ...");
            }
            //reading loop
            while (true) {
               Object o = ois.readObject(); //reading object
               String nameUser = "";
               //if the object is string ...
               if (o instanceof String) {
                  String command = (String) o;
                  if (command.equals("increase_turn")) {
                     System.out.println("Turn: " + diceRoll.getTurn());
                     increaseTurn();
                     System.out.println("Turn after increasing as ordered from server: " + diceRoll.getTurn());
                  } else if (command.equals("new_player")) {
                     numOfActivePlayers++;
                     playersList.add((String) ois.readObject());
                     String namesChat="";
                     for (int i = 0; i < playersList.size(); i++) {
                        namesChat +=  playersList.get(i) + "\n";
                     
                     }
                     mc.updateUserList(namesChat);
                  }
                  //New added for setting text to textAreaChat in MessengerClient.java
                  else {
                     mc.getTextAreaChat().append(o + "\n");
                  }
               //if it's an object of type MovementData
               } else if (o instanceof MovementData) {
                  System.out.println("Order to update players position received.");
                  System.out.println("Turn after receiving order: " + diceRoll.getTurn());
                  //extracting index and color number from the object
                  int index = ((MovementData) o).getIndex();
                  int color = ((MovementData) o).getColor();
                  //if extracting point causes an error, it means the object was created with other constructor that doesn't have point parameter
                  //and that indicates a request for client to reset the eaten token. In other case, it's just a request to update token's position
                  try {
                     Point point = ((MovementData) o).getPoint();
                  
                     //depending on received color, player of that color will be called and position of its moved token will be updated
                     if (color == 1) {
                        getRedPlayer().getTurtle(index).setPawnLocation(point);
                        System.out.println("Updated position of RED token: " + getRedPlayer().getTurtle(index).getCurrentPosition().toString());
                     } else if (color == 2) {
                        getYellowPlayer().getTurtle(index).setPawnLocation(point);
                        System.out.println("Updated position of YELLOW token: " + getYellowPlayer().getTurtle(index).getCurrentPosition().toString());
                     } else if (color == 3) {
                        getGreenPlayer().getTurtle(index).setPawnLocation(point);
                        System.out.println("Updated position of GREEN token: " + getGreenPlayer().getTurtle(index).getCurrentPosition().toString());
                     } else {
                        getBluePlayer().getTurtle(index).setPawnLocation(point);
                        System.out.println("Updated position of BLUE token: " + getBluePlayer().getTurtle(index).getCurrentPosition().toString());
                     }
                  //exception means it's an order to reset location of the eaten token
                  } catch (Exception e) {
                     System.out.println("Exception caught.");
                     //color indicates the player(color) which the eaten token belongs to
                     if (color == 1) {
                        getRedPlayer().getTurtle(index).restartLocation();
                     } else if (color == 2) {
                        getYellowPlayer().getTurtle(index).restartLocation();
                     } else if (color == 3) {
                        getGreenPlayer().getTurtle(index).restartLocation();
                     } else {
                        getBluePlayer().getTurtle(index).restartLocation();
                     }
                  }
                  System.out.println("Movement updated.");
               //if the received object is an array list...
               } else if (o instanceof ArrayList) {
                  playersList = (ArrayList<String>) o; //casting
                  String names = "";
                  String namesChat = "";
                  String verb = "";
                  //depending on wether is one or more players in the game, different verb will be used
                  if (playersList.size() > 1) {
                     verb = " are ";
                  } else {
                     verb = " is ";
                  }
                  //concatenating the string of names
                  for (int i = 0; i < playersList.size(); i++) {
                     names += playersList.get(i);
                     if (i < playersList.size() - 1) {
                        names += ", ";
                     }
                  }
               
                  //New added for setting user names to textAreaUser in MessengerClient.java
               
                  for (int i = 0; i < playersList.size(); i++) {
                     namesChat +=  playersList.get(i) + "\n";
                  
                  }
               
                  mc.updateUserList(namesChat);
                  System.out.println(namesChat);
               
                  //notifying the client about the players that are currently in the game
                  JOptionPane.showMessageDialog(null, names + verb + "in the game.", "Notification", JOptionPane.INFORMATION_MESSAGE);
               }
            }
         //if the server shuts down, warning will be shown and game will close
         } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "The server is down. The game will close. ", "Warning", JOptionPane.WARNING_MESSAGE);
            try {
               socket.close();
            } catch (IOException ex) {
               System.exit(-1);
            }
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
         }
      }
   }


   //==========================================================
   // METHODS RELATED TO NETWORKING
   //=============================================================

   /**
    * method that starts a reading thread for client
    */
   public void startConnection() {
      ClientThread thread = new ClientThread(this);
      thread.start();
      System.out.println("Thread started");
   }

   /**
    * sends a request to server to order other clients to increase turn locally
    */
   public void increaseTurnOnServer() {
      try {
         oos.writeObject("INCREASE_TURN");
         oos.flush();
      } catch (IOException e) {
         System.out.println("ERROR: Increase turn request NOT sent to server");
      }
   }

   /**
    * sends a request to server to order other clients to update recently moved token's position
    * @param index index of the token (turtle)
    * @param player player (color) which token is moved
    * @param point point as a new location of the token
    */
   public void updateMovementOnServer(int index, Player player, Point point) {
      try {
         //creating object from MovementData (1st constructor)
         MovementData movementData = new MovementData(index, assignedColorNum, player, point);
         oos.writeObject(movementData);
         oos.flush();
         System.out.println("Data required for updating boards sent to server SUCCESSFULLY.");
      } catch (IOException e) {
         System.out.println("ERROR: Sending data needed to update movement on server failed.");
      }
   }

   /**
    * @return number that represents the assigned color
    */
   public int getAssignedColorNum() {
      return assignedColorNum;
   }

   /**
    * @return number of players currently in the game
    */
   public int getNumOfActivePlayers() {
      return numOfActivePlayers;
   }

   /**
    * sends a request to server to order other clients to restart the token (turtle)
    * @param index index of the token
    * @param eatenColorNum number that represents the color of the token that needs to be restarted
    */
   public void restartTurtleOnServer(int index, int eatenColorNum) {
      //creating object from MovementData (2nd constructor)
      MovementData restartData = new MovementData(index, eatenColorNum);
      try {
         oos.writeObject(restartData);
         oos.flush();
      } catch (IOException e) {
         e.printStackTrace();
      }
      System.out.println("Data required for restarting eaten turtle's position sent to server SUCCESSFULLY.");
   }

   public void sendMessageFromChatToServer(String message){
   
      try {
         oos.writeObject(message + "\n");
         oos.flush();
      } catch (IOException e) {
         e.printStackTrace();
      }
   
      System.out.println("Message from chat sent to server SUCCESSFULLY");
   
   }

   /**
    * gets red player
    *
    * @return Player object
    */
   public Player getRedPlayer() {
      return playerRed;
   }

   /**
    * gets yellow player
    *
    * @return Player object
    */
   public Player getYellowPlayer() {
      return playerYellow;
   }

   /**
    * gets green player
    *
    * @return Player object
    */
   public Player getGreenPlayer() {
      return playerGreen;
   }

   /**
    * gets blue player
    *
    * @return Player object
    */
   public Player getBluePlayer() {
      return playerBlue;
   }

   /**
    * gets background panel
    *
    * @return JPanel object as background panel
    */
   public JPanel getBackgroundPanel() {
      return backgroundPanel;
   }

   /**
    * gets instruction value from instruction label
    *
    * @return String as instruction
    */
   public String getInstruction() {
      return txtInstructions.getText();
   }

   /**
    * sets instruction to the Instruction label
    *
    * @param _instructions
    */
   public void setInstruction(String _instructions) {
      this.txtInstructions.setText(_instructions);
   }

   /**
    * appends instruction to the Instruction label
    *
    * @param _appendedInstruction
    */
   public void appendInstruction(String _appendedInstruction) {
      this.txtInstructions.append(_appendedInstruction);
   }

   /**
    * gets start panel
    *
    * @return StartPanel object
    */
   public StartPanel getStartPanel() {
      return panelStarting;
   }

   /**
    * method to increment turn
    */
   public void increaseTurn() {
      String[] colorsAllCaps = {"RED", "YELLOW", "GREEN", "BLUE"};
      diceRoll.incrementTurn(panelStarting);
      if (diceRoll.getTurn() == panelStarting.getNumberOfPlayers() + 1) {
         diceRoll.resetTurn();
      }
      //notifying clients whether it's their turn to play or not, and if not, who's
      if (diceRoll.getTurn() != assignedColorNum) {
         setInstruction("Wait for your turn...\nNow plays " + playersList.get(diceRoll.getTurn() - 1) + " as " + colorsAllCaps[diceRoll.getTurn() - 1] + ".");
         System.out.println("After incrementing: " + diceRoll.getTurn());
      } else {
         setInstruction(playersList.get(diceRoll.getTurn() - 1) + ", it's your turn to roll the dice!");
      }
   }

   /**
    * method to get new turn
    *
    * @return int of new turn move
    */

   public int getNewTurn() {
      return diceRoll.getTurn();
   }

   /**
    * method to block dice if no move can be done
    *
    * @param blockDice blocks the dice and sets the dice to blocked
    */
   public void setBlockDice(boolean blockDice) {
      this.blockDice = blockDice;
   }

   /**
    * gets blocked dice
    *
    * @return dice as blocked or not blocked
    */
   public boolean getBlockDice() {
      return this.blockDice;
   }

   /**
    * checks if overlapping is there and eats the player if so
    * if new position of element is same as the current position of other token
    * sets token of opponent back to its base
    * the code repeats multiple times
    *
    * @param player as Player object
    */

   public void eatIfOverlap(Player player) {
      if (player == playerRed) {
         System.out.println("Loop triggered : RED");
         for (int i = 0; i < 4; i++) {
            if (playerRed.getTurtle(i).getCurrentPosition().getX() == playerYellow.getTurtle(0).getCurrentPosition().getX() && playerRed.getTurtle(i).getCurrentPosition().getY() == playerYellow.getTurtle(0).getCurrentPosition().getY()) {
               playerYellow.resetTurtle(0);
               restartTurtleOnServer(0, 2);
               System.out.println("Token eaten.");
            } else if (playerRed.getTurtle(i).getCurrentPosition().getX() == playerYellow.getTurtle(1).getCurrentPosition().getX() && playerRed.getTurtle(i).getCurrentPosition().getY() == playerYellow.getTurtle(1).getCurrentPosition().getY()) {
               playerYellow.resetTurtle(1);
               restartTurtleOnServer(1, 2);
               System.out.println("Token eaten.");
            } else if (playerRed.getTurtle(i).getCurrentPosition().getX() == playerYellow.getTurtle(2).getCurrentPosition().getX() && playerRed.getTurtle(i).getCurrentPosition().getY() == playerYellow.getTurtle(2).getCurrentPosition().getY()) {
               playerYellow.resetTurtle(2);
               restartTurtleOnServer(2, 2);
               System.out.println("Token eaten.");
            } else if (playerRed.getTurtle(i).getCurrentPosition().getX() == playerYellow.getTurtle(3).getCurrentPosition().getX() && playerRed.getTurtle(i).getCurrentPosition().getY() == playerYellow.getTurtle(3).getCurrentPosition().getY()) {
               playerYellow.resetTurtle(3);
               restartTurtleOnServer(3, 2);
               System.out.println("Token eaten.");
            } else if (playerRed.getTurtle(i).getCurrentPosition().getX() == playerBlue.getTurtle(0).getCurrentPosition().getX() && playerRed.getTurtle(i).getCurrentPosition().getY() == playerBlue.getTurtle(0).getCurrentPosition().getY()) {
               playerBlue.resetTurtle(0);
               restartTurtleOnServer(0, 4);
               System.out.println("Token eaten.");
            } else if (playerRed.getTurtle(i).getCurrentPosition().getX() == playerBlue.getTurtle(1).getCurrentPosition().getX() && playerRed.getTurtle(i).getCurrentPosition().getY() == playerBlue.getTurtle(1).getCurrentPosition().getY()) {
               playerBlue.resetTurtle(1);
               restartTurtleOnServer(1, 4);
               System.out.println("Token eaten.");
            } else if (playerRed.getTurtle(i).getCurrentPosition().getX() == playerBlue.getTurtle(2).getCurrentPosition().getX() && playerRed.getTurtle(i).getCurrentPosition().getY() == playerBlue.getTurtle(2).getCurrentPosition().getY()) {
               playerBlue.resetTurtle(2);
               restartTurtleOnServer(2, 4);
               System.out.println("Token eaten.");
            } else if (playerRed.getTurtle(i).getCurrentPosition().getX() == playerBlue.getTurtle(3).getCurrentPosition().getX() && playerRed.getTurtle(i).getCurrentPosition().getY() == playerBlue.getTurtle(3).getCurrentPosition().getY()) {
               playerBlue.resetTurtle(3);
               restartTurtleOnServer(3, 4);
               System.out.println("Token eaten.");
            } else if (playerRed.getTurtle(i).getCurrentPosition().getX() == playerGreen.getTurtle(0).getCurrentPosition().getX() && playerRed.getTurtle(i).getCurrentPosition().getY() == playerGreen.getTurtle(0).getCurrentPosition().getY()) {
               playerGreen.resetTurtle(0);
               restartTurtleOnServer(0, 3);
               System.out.println("Token eaten.");
            } else if (playerRed.getTurtle(i).getCurrentPosition().getX() == playerGreen.getTurtle(1).getCurrentPosition().getX() && playerRed.getTurtle(i).getCurrentPosition().getY() == playerGreen.getTurtle(1).getCurrentPosition().getY()) {
               playerGreen.resetTurtle(1);
               restartTurtleOnServer(1, 3);
               System.out.println("Token eaten.");
            } else if (playerRed.getTurtle(i).getCurrentPosition().getX() == playerGreen.getTurtle(2).getCurrentPosition().getX() && playerRed.getTurtle(i).getCurrentPosition().getY() == playerGreen.getTurtle(2).getCurrentPosition().getY()) {
               playerGreen.resetTurtle(2);
               restartTurtleOnServer(2, 3);
               System.out.println("Token eaten.");
            } else if (playerRed.getTurtle(i).getCurrentPosition().getX() == playerGreen.getTurtle(3).getCurrentPosition().getX() && playerRed.getTurtle(i).getCurrentPosition().getY() == playerGreen.getTurtle(3).getCurrentPosition().getY()) {
               playerGreen.resetTurtle(3);
               restartTurtleOnServer(3, 3);
               System.out.println("Token eaten.");
            }
         }
      } else if (player == playerYellow) {
         System.out.println("Loop triggered : YELLOW");
         for (int i = 0; i < 4; i++) {
            if (playerYellow.getTurtle(i).getCurrentPosition().getX() == playerRed.getTurtle(0).getCurrentPosition().getX() && playerYellow.getTurtle(i).getCurrentPosition().getY() == playerRed.getTurtle(0).getCurrentPosition().getY()) {
               playerRed.resetTurtle(0);
               restartTurtleOnServer(0, 1);
               System.out.println("Token eaten.");
            } else if (playerYellow.getTurtle(i).getCurrentPosition().getX() == playerRed.getTurtle(1).getCurrentPosition().getX() && playerYellow.getTurtle(i).getCurrentPosition().getY() == playerRed.getTurtle(1).getCurrentPosition().getY()) {
               playerRed.resetTurtle(1);
               restartTurtleOnServer(1, 1);
               System.out.println("Token eaten.");
            } else if (playerYellow.getTurtle(i).getCurrentPosition().getX() == playerRed.getTurtle(2).getCurrentPosition().getX() && playerYellow.getTurtle(i).getCurrentPosition().getY() == playerRed.getTurtle(2).getCurrentPosition().getY()) {
               playerRed.resetTurtle(2);
               restartTurtleOnServer(2, 1);
               System.out.println("Token eaten.");
            } else if (playerYellow.getTurtle(i).getCurrentPosition().getX() == playerRed.getTurtle(3).getCurrentPosition().getX() && playerYellow.getTurtle(i).getCurrentPosition().getY() == playerRed.getTurtle(3).getCurrentPosition().getY()) {
               playerRed.resetTurtle(3);
               restartTurtleOnServer(3, 1);
               System.out.println("Token eaten.");
            } else if (playerYellow.getTurtle(i).getCurrentPosition().getX() == playerBlue.getTurtle(0).getCurrentPosition().getX() && playerYellow.getTurtle(i).getCurrentPosition().getY() == playerBlue.getTurtle(0).getCurrentPosition().getX()) {
               playerBlue.resetTurtle(0);
               restartTurtleOnServer(0, 4);
               System.out.println("Token eaten.");
            } else if (playerYellow.getTurtle(i).getCurrentPosition().getX() == playerBlue.getTurtle(1).getCurrentPosition().getX() && playerYellow.getTurtle(i).getCurrentPosition().getY() == playerBlue.getTurtle(1).getCurrentPosition().getX()) {
               playerBlue.resetTurtle(1);
               restartTurtleOnServer(1, 4);
               System.out.println("Token eaten.");
            } else if (playerYellow.getTurtle(i).getCurrentPosition().getX() == playerBlue.getTurtle(2).getCurrentPosition().getX() && playerYellow.getTurtle(i).getCurrentPosition().getY() == playerBlue.getTurtle(2).getCurrentPosition().getX()) {
               playerBlue.resetTurtle(2);
               restartTurtleOnServer(2, 4);
               System.out.println("Token eaten.");
            } else if (playerYellow.getTurtle(i).getCurrentPosition().getX() == playerBlue.getTurtle(3).getCurrentPosition().getX() && playerYellow.getTurtle(i).getCurrentPosition().getY() == playerBlue.getTurtle(3).getCurrentPosition().getX()) {
               playerBlue.resetTurtle(3);
               restartTurtleOnServer(3, 4);
               System.out.println("Token eaten.");
            } else if (playerYellow.getTurtle(i).getCurrentPosition().getX() == playerGreen.getTurtle(0).getCurrentPosition().getX() && playerYellow.getTurtle(i).getCurrentPosition().getY() == playerGreen.getTurtle(0).getCurrentPosition().getX()) {
               playerGreen.resetTurtle(0);
               restartTurtleOnServer(0, 3);
               System.out.println("Token eaten.");
            } else if (playerYellow.getTurtle(i).getCurrentPosition().getX() == playerGreen.getTurtle(1).getCurrentPosition().getX() && playerYellow.getTurtle(i).getCurrentPosition().getY() == playerGreen.getTurtle(1).getCurrentPosition().getX()) {
               playerGreen.resetTurtle(1);
               restartTurtleOnServer(1, 3);
               System.out.println("Token eaten.");
            } else if (playerYellow.getTurtle(i).getCurrentPosition().getX() == playerGreen.getTurtle(2).getCurrentPosition().getX() && playerYellow.getTurtle(i).getCurrentPosition().getY() == playerGreen.getTurtle(2).getCurrentPosition().getX()) {
               playerGreen.resetTurtle(2);
               restartTurtleOnServer(2, 3);
               System.out.println("Token eaten.");
            } else if (playerYellow.getTurtle(i).getCurrentPosition().getX() == playerGreen.getTurtle(3).getCurrentPosition().getX() && playerYellow.getTurtle(i).getCurrentPosition().getY() == playerGreen.getTurtle(3).getCurrentPosition().getX()) {
               playerGreen.resetTurtle(3);
               restartTurtleOnServer(3, 3);
               System.out.println("Token eaten.");
            }
         }
      } else if (player == playerGreen) {
         for (int i = 0; i < 4; i++) {
            if (playerGreen.getTurtle(i).getCurrentPosition().getX() == playerRed.getTurtle(0).getCurrentPosition().getX() && playerGreen.getTurtle(i).getCurrentPosition().getY() == playerRed.getTurtle(0).getCurrentPosition().getY()) {
               playerRed.resetTurtle(0);
               restartTurtleOnServer(0, 1);
               System.out.println("Token eaten.");
            } else if (playerGreen.getTurtle(i).getCurrentPosition().getX() == playerRed.getTurtle(1).getCurrentPosition().getX() && playerGreen.getTurtle(i).getCurrentPosition().getY() == playerRed.getTurtle(1).getCurrentPosition().getY()) {
               playerRed.resetTurtle(1);
               restartTurtleOnServer(1, 1);
               System.out.println("Token eaten.");
            } else if (playerGreen.getTurtle(i).getCurrentPosition().getX() == playerRed.getTurtle(2).getCurrentPosition().getX() && playerGreen.getTurtle(i).getCurrentPosition().getY() == playerRed.getTurtle(2).getCurrentPosition().getY()) {
               playerRed.resetTurtle(2);
               restartTurtleOnServer(2, 1);
               System.out.println("Token eaten.");
            } else if (playerGreen.getTurtle(i).getCurrentPosition().getX() == playerRed.getTurtle(3).getCurrentPosition().getX() && playerGreen.getTurtle(i).getCurrentPosition().getY() == playerRed.getTurtle(3).getCurrentPosition().getY()) {
               playerRed.resetTurtle(3);
               restartTurtleOnServer(3, 1);
               System.out.println("Token eaten.");
            } else if (playerGreen.getTurtle(i).getCurrentPosition().getX() == playerYellow.getTurtle(0).getCurrentPosition().getX() && playerGreen.getTurtle(i).getCurrentPosition().getY() == playerYellow.getTurtle(0).getCurrentPosition().getY()) {
               playerYellow.resetTurtle(0);
               restartTurtleOnServer(0, 2);
               System.out.println("Token eaten.");
            } else if (playerGreen.getTurtle(i).getCurrentPosition().getX() == playerYellow.getTurtle(1).getCurrentPosition().getX() && playerGreen.getTurtle(i).getCurrentPosition().getY() == playerYellow.getTurtle(1).getCurrentPosition().getY()) {
               playerYellow.resetTurtle(1);
               restartTurtleOnServer(1, 2);
               System.out.println("Token eaten.");
            } else if (playerGreen.getTurtle(i).getCurrentPosition().getX() == playerYellow.getTurtle(2).getCurrentPosition().getX() && playerGreen.getTurtle(i).getCurrentPosition().getY() == playerYellow.getTurtle(2).getCurrentPosition().getY()) {
               playerYellow.resetTurtle(2);
               restartTurtleOnServer(2, 2);
               System.out.println("Token eaten.");
            } else if (playerGreen.getTurtle(i).getCurrentPosition().getX() == playerYellow.getTurtle(3).getCurrentPosition().getX() && playerGreen.getTurtle(i).getCurrentPosition().getY() == playerYellow.getTurtle(3).getCurrentPosition().getY()) {
               playerYellow.resetTurtle(3);
               restartTurtleOnServer(3, 2);
               System.out.println("Token eaten.");
            } else if (playerGreen.getTurtle(i).getCurrentPosition().getX() == playerBlue.getTurtle(0).getCurrentPosition().getX() && playerGreen.getTurtle(i).getCurrentPosition().getY() == playerBlue.getTurtle(0).getCurrentPosition().getY()) {
               playerBlue.resetTurtle(0);
               restartTurtleOnServer(0, 4);
               System.out.println("Token eaten.");
            } else if (playerGreen.getTurtle(i).getCurrentPosition().getX() == playerBlue.getTurtle(1).getCurrentPosition().getX() && playerGreen.getTurtle(i).getCurrentPosition().getY() == playerBlue.getTurtle(1).getCurrentPosition().getY()) {
               playerBlue.resetTurtle(1);
               restartTurtleOnServer(1, 4);
               System.out.println("Token eaten.");
            } else if (playerGreen.getTurtle(i).getCurrentPosition().getX() == playerBlue.getTurtle(2).getCurrentPosition().getX() && playerGreen.getTurtle(i).getCurrentPosition().getY() == playerBlue.getTurtle(2).getCurrentPosition().getY()) {
               playerBlue.resetTurtle(2);
               restartTurtleOnServer(2, 4);
               System.out.println("Token eaten.");
            } else if (playerGreen.getTurtle(i).getCurrentPosition().getX() == playerBlue.getTurtle(3).getCurrentPosition().getX() && playerGreen.getTurtle(i).getCurrentPosition().getY() == playerBlue.getTurtle(3).getCurrentPosition().getY()) {
               playerBlue.resetTurtle(3);
               restartTurtleOnServer(3, 4);
               System.out.println("Token eaten.");
            }
         }
      } else if (player == playerBlue) {
      
         for (int i = 0; i < 4; i++) {
            if (playerBlue.getTurtle(i).getCurrentPosition().getX() == playerRed.getTurtle(0).getCurrentPosition().getX() && playerBlue.getTurtle(i).getCurrentPosition().getY() == playerRed.getTurtle(0).getCurrentPosition().getY()) {
               playerRed.resetTurtle(0);
               restartTurtleOnServer(0, 1);
               System.out.println("Token eaten.");
            } else if (playerBlue.getTurtle(i).getCurrentPosition().getX() == playerRed.getTurtle(1).getCurrentPosition().getX() && playerBlue.getTurtle(i).getCurrentPosition().getY() == playerRed.getTurtle(1).getCurrentPosition().getY()) {
               playerRed.resetTurtle(1);
               restartTurtleOnServer(1, 1);
               System.out.println("Token eaten.");
            } else if (playerBlue.getTurtle(i).getCurrentPosition().getX() == playerRed.getTurtle(2).getCurrentPosition().getX() && playerBlue.getTurtle(i).getCurrentPosition().getY() == playerRed.getTurtle(2).getCurrentPosition().getY()) {
               playerRed.resetTurtle(2);
               restartTurtleOnServer(2, 1);
               System.out.println("Token eaten.");
            } else if (playerBlue.getTurtle(i).getCurrentPosition().getX() == playerRed.getTurtle(3).getCurrentPosition().getX() && playerBlue.getTurtle(i).getCurrentPosition().getY() == playerRed.getTurtle(3).getCurrentPosition().getY()) {
               playerRed.resetTurtle(3);
               restartTurtleOnServer(3, 1);
               System.out.println("Token eaten.");
            } else if (playerBlue.getTurtle(i).getCurrentPosition().getX() == playerYellow.getTurtle(0).getCurrentPosition().getX() && playerBlue.getTurtle(i).getCurrentPosition().getY() == playerYellow.getTurtle(0).getCurrentPosition().getY()) {
               playerYellow.resetTurtle(0);
               restartTurtleOnServer(0, 2);
               System.out.println("Token eaten.");
            } else if (playerBlue.getTurtle(i).getCurrentPosition().getX() == playerYellow.getTurtle(1).getCurrentPosition().getX() && playerBlue.getTurtle(i).getCurrentPosition().getY() == playerYellow.getTurtle(1).getCurrentPosition().getY()) {
               playerYellow.resetTurtle(1);
               restartTurtleOnServer(1, 2);
               System.out.println("Token eaten.");
            } else if (playerBlue.getTurtle(i).getCurrentPosition().getX() == playerYellow.getTurtle(2).getCurrentPosition().getX() && playerBlue.getTurtle(i).getCurrentPosition().getY() == playerYellow.getTurtle(2).getCurrentPosition().getY()) {
               playerYellow.resetTurtle(2);
               restartTurtleOnServer(2, 2);
               System.out.println("Token eaten.");
            } else if (playerBlue.getTurtle(i).getCurrentPosition().getX() == playerYellow.getTurtle(3).getCurrentPosition().getX() && playerBlue.getTurtle(i).getCurrentPosition().getY() == playerYellow.getTurtle(3).getCurrentPosition().getY()) {
               playerYellow.resetTurtle(3);
               restartTurtleOnServer(3, 2);
               System.out.println("Token eaten.");
            } else if (playerBlue.getTurtle(i).getCurrentPosition().getX() == playerGreen.getTurtle(0).getCurrentPosition().getX() && playerBlue.getTurtle(i).getCurrentPosition().getY() == playerGreen.getTurtle(0).getCurrentPosition().getY()) {
               playerGreen.resetTurtle(0);
               restartTurtleOnServer(0, 4);
               System.out.println("Token eaten.");
            } else if (playerBlue.getTurtle(i).getCurrentPosition().getX() == playerGreen.getTurtle(1).getCurrentPosition().getX() && playerBlue.getTurtle(i).getCurrentPosition().getY() == playerGreen.getTurtle(1).getCurrentPosition().getY()) {
               playerGreen.resetTurtle(1);
               restartTurtleOnServer(1, 4);
               System.out.println("Token eaten.");
            } else if (playerBlue.getTurtle(i).getCurrentPosition().getX() == playerGreen.getTurtle(2).getCurrentPosition().getX() && playerBlue.getTurtle(i).getCurrentPosition().getY() == playerGreen.getTurtle(2).getCurrentPosition().getY()) {
               playerGreen.resetTurtle(2);
               restartTurtleOnServer(2, 4);
               System.out.println("Token eaten.");
            } else if (playerBlue.getTurtle(i).getCurrentPosition().getX() == playerGreen.getTurtle(3).getCurrentPosition().getX() && playerBlue.getTurtle(i).getCurrentPosition().getY() == playerGreen.getTurtle(3).getCurrentPosition().getY()) {
               playerGreen.resetTurtle(3);
               restartTurtleOnServer(3, 4);
               System.out.println("Token eaten.");
            }
         }
      }
   }

   /**
    * playTurn methods handles the game logic
    *
    * @param turn          to decide on the turn
    * @param diceValueTemp takes the  current dice value
    */
   public void playTurn(int turn, int diceValueTemp) {
      this.diceValue = diceValueTemp;
      SeaLudo sealudo = this;
   
      switch (turn) { //check which turn
      
      /*
      if it is red player turn, this code will execute
      */
         case 1:
         
            blockDice = true;
         
            if (!assignedColor.equals("red")) {
               break;
            }
         
            System.out.println("Dice value before making actionlisteners: " + diceValue);
            red0skip = true;
            red1skip = true;
            red2skip = true;
            red3skip = true;
         
            //if next move is available, this will execute
            boolean movementAvailableR = false;
         
            //checks if two tokens overlap at final position
            redOverlapFinal0 = true;
            redOverlapFinal1 = true;
            redOverlapFinal2 = true;
            redOverlapFinal3 = true;
         
         /*
         sets current position of the player as int
         adds dice value to current position
         */
         
            int red0CurrPosition = playerRed.getPath().indexOf(playerRed.getTurtle(0).getCurrentPosition());
            int red1CurrPosition = playerRed.getPath().indexOf(playerRed.getTurtle(1).getCurrentPosition());
            int red2CurrPosition = playerRed.getPath().indexOf(playerRed.getTurtle(2).getCurrentPosition());
            int red3CurrPosition = playerRed.getPath().indexOf(playerRed.getTurtle(3).getCurrentPosition());
         
            int red0NewPosition = red0CurrPosition + diceValueTemp;
            int red1NewPosition = red1CurrPosition + diceValueTemp;
            int red2NewPosition = red2CurrPosition + diceValueTemp;
            int red3NewPosition = red3CurrPosition + diceValueTemp;
            ArrayList<Turtle> movableTokensRed = new ArrayList<>();
            boolean allMovableInBaseRed = true;
         
         /*
         checks if token new position exceeds the path length
         if yes, disables the action listener for the token to make it unclickable
         */
         
            if (red0NewPosition >= playerRed.getPathLength()) {
               red0skip = false;
            } else movableTokensRed.add(playerRed.getTurtle(0));
            if (red1NewPosition >= playerRed.getPathLength()) {
               red1skip = false;
            } else movableTokensRed.add(playerRed.getTurtle(1));
            if (red2NewPosition >= playerRed.getPathLength()) {
               red2skip = false;
            } else movableTokensRed.add(playerRed.getTurtle(2));
            if (red3NewPosition >= playerRed.getPathLength()) {
               red3skip = false;
            } else movableTokensRed.add(playerRed.getTurtle(3));
         
            for (Turtle movableToken : movableTokensRed) {
               if (movableToken.getCurrentPosition() != movableToken.getStartPosition()) {
                  allMovableInBaseRed = false;
               }
            }
            if (red0skip || red1skip || red2skip || red3skip) {
               movementAvailableR = true;
               if (allMovableInBaseRed) {
                  movementAvailableR = false;
               }
            }
         
         
            if (!red0skip) {
               if ((red0NewPosition == red1CurrPosition || red0NewPosition == red2CurrPosition || red0NewPosition == red3CurrPosition) && red0CurrPosition != -1) {
                  redOverlapFinal0 = false;
               }
            }
            if (!red1skip) {
               if ((red1NewPosition == red0CurrPosition || red1NewPosition == red2CurrPosition || red1NewPosition == red3CurrPosition) && red1CurrPosition != -1) {
                  redOverlapFinal1 = false;
               }
            }
            if (!red2skip) {
               if ((red2NewPosition == red0CurrPosition || red2NewPosition == red1CurrPosition || red2NewPosition == red3CurrPosition) && red2CurrPosition != -1) {
                  redOverlapFinal2 = false;
               }
            }
            if (!red3skip) {
               if ((red3NewPosition == red0CurrPosition || red3NewPosition == red1CurrPosition || red3NewPosition == red2CurrPosition) && red3CurrPosition != -1) {
                  redOverlapFinal3 = false;
               }
            }
         
         /*
         if there is one token clicked, all other are disabled until 6 is rolled
         */
         
            redNotClicked = true;
            if (playerRed.hasPlayersOnBoard()) {
               if (!movementAvailableR) {
                  setInstruction("Instruction: No moves available for tokens on path for player RED.");
                  if (diceValue == 6 && playerRed.hasPlayersInBase())
                     appendInstruction("\nYou got a 6 and can take one token from the base!");
                  else appendInstruction("\nPlayer YELLOW - roll the dice!");
                  increaseTurn();
                  increaseTurnOnServer();
                  setBlockDice(false);
                  break;
               }
            
            /*
            sets instructions to the panel for user to read
            */
            
               setInstruction("Instruction: Player RED - you rolled " + diceValue + ".\nClick on a pawn to move!");
               System.out.println("Next position: " + (playerRed.getPath().indexOf(playerRed.getTurtle(0).getCurrentPosition()) + diceValueTemp) + playerRed.getPathLength());
            
               /**
                * action listener gets turtle label and listens to user to click on token
                * if no tokens clicked before, and if no overlapping, and new position will not exceed the path length
                * then clicking is enabled
                * repeats the same functionality for all tokens
                */
            
            
               playerRed.getTurtleLabel(0).addMouseListener(
                            new MouseAdapter() {
                               @Override
                               public void mouseClicked(MouseEvent click) {
                                  if (redNotClicked && red0skip && redOverlapFinal0) {
                                     setInstruction("Instruction: Player RED moved pawn 1 by " + diceValue + ".");
                                     if (diceValue == 6) {
                                        appendInstruction("\nYou rolled 6! Roll again!");
                                     } else {
                                        appendInstruction("\nPlayer YELLOW - roll dice!");
                                     }
                                     System.out.println("Clicked");
                                     System.out.println("When clicked: " + diceValue);
                                     playerRed.moveTurtleForward(0, diceValue, sealudo);
                                     redNotClicked = false;
                                  }
                               }
                            }
                  );
               playerRed.getTurtleLabel(1).addMouseListener(
                            new MouseAdapter() {
                               @Override
                               public void mouseClicked(MouseEvent click) {
                                  if (redNotClicked && red1skip && redOverlapFinal1) {
                                     setInstruction("Instruction: Player RED moved pawn 2 by " + diceValue + ".");
                                     if (diceValue == 6) {
                                        appendInstruction("\nYou rolled 6! Roll again!");
                                     } else {
                                        appendInstruction("\nPlayer YELLOW - roll dice!");
                                     }
                                     System.out.println("Clicked");
                                     System.out.println("When clicked: " + diceValue);
                                     playerRed.moveTurtleForward(1, diceValue, sealudo);
                                     redNotClicked = false;
                                  }
                               }
                            }
                  );
               playerRed.getTurtleLabel(2).addMouseListener(
                            new MouseAdapter() {
                               @Override
                               public void mouseClicked(MouseEvent click) {
                                  if (redNotClicked && red2skip && redOverlapFinal2) {
                                     setInstruction("Instruction: Player RED moved pawn 3 by " + diceValue + ".");
                                     if (diceValue == 6) {
                                        appendInstruction("\nYou rolled 6! Roll again!");
                                     } else {
                                        appendInstruction("\nPlayer YELLOW - roll dice!");
                                     }
                                     System.out.println("Clicked");
                                     System.out.println("When clicked: " + diceValue);
                                     playerRed.moveTurtleForward(2, diceValue, sealudo);
                                     redNotClicked = false;
                                  }
                               
                               }
                            }
                  );
            
               playerRed.getTurtleLabel(3).addMouseListener(
                            new MouseAdapter() {
                               @Override
                               public void mouseClicked(MouseEvent click) {
                                  setInstruction("Instruction: Player RED moved pawn 4 by " + diceValue + ".");
                                  if (diceValue == 6) {
                                     appendInstruction("\nYou rolled 6! Roll again!");
                                  } else {
                                     appendInstruction("\nPlayer YELLOW - roll dice!");
                                  }
                                  if (redNotClicked && red3skip && redOverlapFinal3) {
                                     System.out.println("Clicked");
                                     System.out.println("When clicked: " + diceValue);
                                     playerRed.moveTurtleForward(3, diceValue, sealudo);
                                     redNotClicked = false;
                                  }
                               }
                            }
                  );
            /*
            if there are no players on the board, it will move the player to starting position
            prints information to the user to go another turn
            */
            
            } else {
               if (diceValue == 6) {
                  setInstruction("Instruction: Player RED, you got 6 and left home! Roll Again!");
               } else {
                  setInstruction("Instruction: Darn tootn'! Player RED you didn't get a 6.\nPlayer YELLOW - roll the dice!");
               }
               playerRed.moveTurtleForward(0, diceValue, sealudo);
            }
         
            break;
      
        /*
          execute the above functionality for yellow player
           */
      
         case 2:
         
            blockDice = true;
         
            if (!assignedColor.equals("yellow")) {
               break;
            }
         
            System.out.println("Dice value before making action listeners: " + diceValue);
            yellow0skip = true;
            yellow1skip = true;
            yellow2skip = true;
            yellow3skip = true;
            boolean movementAvailableY = false;
            yellowOverlapFinal0 = true;
            yellowOverlapFinal1 = true;
            yellowOverlapFinal2 = true;
            yellowOverlapFinal3 = true;
         
            int yellow0CurrPosition = playerYellow.getPath().indexOf(playerYellow.getTurtle(0).getCurrentPosition());
            int yellow1CurrPosition = playerYellow.getPath().indexOf(playerYellow.getTurtle(1).getCurrentPosition());
            int yellow2CurrPosition = playerYellow.getPath().indexOf(playerYellow.getTurtle(2).getCurrentPosition());
            int yellow3CurrPosition = playerYellow.getPath().indexOf(playerYellow.getTurtle(3).getCurrentPosition());
         
            int yellow0NewPosition = yellow0CurrPosition + diceValueTemp;
            int yellow1NewPosition = yellow1CurrPosition + diceValueTemp;
            int yellow2NewPosition = yellow2CurrPosition + diceValueTemp;
            int yellow3NewPosition = yellow3CurrPosition + diceValueTemp;
            ArrayList<Turtle> movableTokensYellow = new ArrayList<>();
            boolean allMovableInBaseYellow = true;
         
            if (yellow0NewPosition >= playerYellow.getPathLength()) {
               yellow0skip = false;
            } else movableTokensYellow.add(playerYellow.getTurtle(0));
            if (yellow1NewPosition >= playerYellow.getPathLength()) {
               yellow1skip = false;
            } else movableTokensYellow.add(playerYellow.getTurtle(1));
            if (yellow2NewPosition >= playerYellow.getPathLength()) {
               yellow2skip = false;
            } else movableTokensYellow.add(playerYellow.getTurtle(2));
            if (yellow3NewPosition >= playerYellow.getPathLength()) {
               yellow3skip = false;
            } else movableTokensYellow.add(playerYellow.getTurtle(3));
         
            for (Turtle movableToken : movableTokensYellow) {
               if (movableToken.getCurrentPosition() != movableToken.getStartPosition()) {
                  allMovableInBaseYellow = false;
               }
            }
            if (yellow0skip || yellow1skip || yellow2skip || yellow3skip) {
               movementAvailableY = true;
               if (allMovableInBaseYellow) {
                  movementAvailableY = false;
               }
            }
         
            if (yellow0NewPosition == (playerYellow.getPathLength() - 4) ||
                   yellow0NewPosition == (playerYellow.getPathLength() - 3) ||
                   yellow0NewPosition == (playerYellow.getPathLength() - 2) ||
                   yellow0NewPosition == (playerYellow.getPathLength() - 1)) {
               if ((yellow0NewPosition == yellow1CurrPosition || yellow0NewPosition == yellow2CurrPosition || yellow0NewPosition == yellow3CurrPosition) && yellow0CurrPosition != -1) {
                  redOverlapFinal0 = false;
               }
            }
            if (yellow1NewPosition == (playerYellow.getPathLength() - 4) ||
                   yellow1NewPosition == (playerYellow.getPathLength() - 3) ||
                   yellow1NewPosition == (playerYellow.getPathLength() - 2) ||
                   yellow1NewPosition == (playerYellow.getPathLength() - 1)) {
               if ((yellow1NewPosition == yellow0CurrPosition || yellow1NewPosition == yellow2CurrPosition || yellow1NewPosition == yellow3CurrPosition) && yellow1CurrPosition != -1) {
                  redOverlapFinal1 = false;
               }
            }
            if (yellow2NewPosition == (playerYellow.getPathLength() - 4) ||
                   yellow2NewPosition == (playerYellow.getPathLength() - 3) ||
                   yellow2NewPosition == (playerYellow.getPathLength() - 2) ||
                   yellow2NewPosition == (playerYellow.getPathLength() - 1)) {
               if ((yellow2NewPosition == yellow0CurrPosition || yellow2NewPosition == yellow1CurrPosition || yellow2NewPosition == yellow3CurrPosition) && yellow2CurrPosition != -1) {
                  redOverlapFinal2 = false;
               }
            }
            if (yellow3NewPosition == (playerYellow.getPathLength() - 4) ||
                   yellow3NewPosition == (playerYellow.getPathLength() - 3) ||
                   yellow3NewPosition == (playerYellow.getPathLength() - 2) ||
                   yellow3NewPosition == (playerYellow.getPathLength() - 1)) {
               if ((yellow3NewPosition == yellow0CurrPosition || yellow3NewPosition == yellow1CurrPosition || yellow3NewPosition == yellow2CurrPosition) && yellow3CurrPosition != -1) {
                  redOverlapFinal3 = false;
               }
            }
         
            yellowNotClicked = true;
            if (playerYellow.hasPlayersOnBoard()) {
               if (!movementAvailableY) {
                  setInstruction("Instruction: no moves available for tokens on path for player YELLOW.");
                  if (diceValue == 6 && playerYellow.hasPlayersInBase())
                     appendInstruction("\nYou got a 6 and can take one token from the base!");
                  else {
                     if (getStartPanel().getNumberOfPlayers() == 2) {
                        appendInstruction("\nPlayer RED - roll dice!");
                     } else {
                        appendInstruction("\nPlayer GREEN - roll dice!");
                     }
                  }
                  increaseTurn();
                  increaseTurnOnServer();
                  setBlockDice(false);
                  break;
               }
               setInstruction("Instruction: Player YELLOW - you rolled " + diceValue + ".\nClick on a pawn to move!");
               System.out.println("Dice value after checking hasPlayersOnBoard " + diceValue);
               playerYellow.getTurtleLabel(0).addMouseListener(
                            new MouseAdapter() {
                               @Override
                               public void mouseClicked(MouseEvent click) {
                                  if (yellowNotClicked && yellow0skip && yellowOverlapFinal0) {
                                     setInstruction("Instruction: Player YELLOW moved pawn 1 by " + diceValue + ".");
                                     if (diceValue == 6) {
                                        appendInstruction("\nYou rolled 6! Roll again!");
                                     } else {
                                        if (getStartPanel().getNumberOfPlayers() == 2) {
                                           appendInstruction("\nPlayer RED - roll dice!");
                                        } else {
                                           appendInstruction("\nPlayer GREEN - roll dice!");
                                        }
                                     }
                                     System.out.println("Clicked");
                                     System.out.println("When clicked: " + diceValue);
                                     playerYellow.moveTurtleForward(0, diceValue, sealudo);
                                     yellowNotClicked = false;
                                  }
                               }
                            }
                  );
            
               playerYellow.getTurtleLabel(1).addMouseListener(
                            new MouseAdapter() {
                               @Override
                               public void mouseClicked(MouseEvent click) {
                                  if (yellowNotClicked && yellow1skip && yellowOverlapFinal1) {
                                     setInstruction("Instruction: Player YELLOW moved pawn 2 by " + diceValue + ".");
                                     if (diceValue == 6) {
                                        appendInstruction("\nYou rolled 6! Roll again!");
                                     } else {
                                        if (getStartPanel().getNumberOfPlayers() == 2) {
                                           appendInstruction("\nPlayer RED - roll dice!");
                                        } else {
                                           appendInstruction("\nPlayer GREEN - roll dice!");
                                        }
                                     }
                                     System.out.println("Clicked");
                                     System.out.println("When clicked: " + diceValue);
                                     playerYellow.moveTurtleForward(1, diceValue, sealudo);
                                     yellowNotClicked = false;
                                  }
                               }
                            }
                  );
            
               playerYellow.getTurtleLabel(2).addMouseListener(
                            new MouseAdapter() {
                               @Override
                               public void mouseClicked(MouseEvent click) {
                                  if (yellowNotClicked && yellow2skip && yellowOverlapFinal2) {
                                     setInstruction("Instruction: Player YELLOW moved pawn 3 by " + diceValue + ".");
                                     if (diceValue == 6) {
                                        appendInstruction("\nYou rolled 6! Roll again!");
                                     } else {
                                        if (getStartPanel().getNumberOfPlayers() == 2) {
                                           appendInstruction("\nPlayer RED - roll dice!");
                                        } else {
                                           appendInstruction("\nPlayer GREEN - roll dice!");
                                        }
                                     }
                                     System.out.println("Clicked");
                                     System.out.println("When clicked: " + diceValue);
                                     playerYellow.moveTurtleForward(2, diceValue, sealudo);
                                     yellowNotClicked = false;
                                  }
                               }
                            }
                  );
            
               playerYellow.getTurtleLabel(3).addMouseListener(
                            new MouseAdapter() {
                               @Override
                               public void mouseClicked(MouseEvent click) {
                                  if (yellowNotClicked && yellow3skip && yellowOverlapFinal3) {
                                     setInstruction("Instruction: Player YELLOW moved pawn 4 by " + diceValue + ".");
                                     if (diceValue == 6) {
                                        appendInstruction("\nYou rolled 6! Roll again!");
                                     } else {
                                        if (getStartPanel().getNumberOfPlayers() == 2) {
                                           appendInstruction("\nPlayer RED - roll dice!");
                                        } else {
                                           appendInstruction("\nPlayer GREEN - roll dice!");
                                        }
                                     }
                                     System.out.println("Clicked");
                                     System.out.println("When clicked: " + diceValue);
                                     playerYellow.moveTurtleForward(3, diceValue, sealudo);
                                     yellowNotClicked = false;
                                  }
                               }
                            }
                  );
            
            } else {
               if (diceValue == 6) {
                  setInstruction("Instruction: Player YELLOW, you got 6 and left home! Roll Again!");
               } else {
                  setInstruction("Instruction: Darn tootn'! Player YELLOW, you didn't get a 6.");
                  if (getStartPanel().getNumberOfPlayers() == 2) {
                     appendInstruction("\nPlayer RED - roll dice!");
                  } else {
                     appendInstruction("\nPlayer GREEN - roll dice!");
                  }
               }
               playerYellow.moveTurtleForward(0, diceValue, sealudo);
            }
         
            break;
      /*
          execute the above functionality for green player
           */
         case 3:
         
            blockDice = true;
         
            if (!assignedColor.equals("green")) {
               break;
            }
         
            System.out.println("Dice value before making actionlisteners: " + diceValue);
         
            green0skip = true;
            green1skip = true;
            green2skip = true;
            green3skip = true;
            boolean movementAvailableG = false;
            greenOverlapFinal0 = true;
            greenOverlapFinal1 = true;
            greenOverlapFinal2 = true;
            greenOverlapFinal3 = true;
         
            int green0CurrPosition = playerGreen.getPath().indexOf(playerGreen.getTurtle(0).getCurrentPosition());
            int green1CurrPosition = playerGreen.getPath().indexOf(playerGreen.getTurtle(1).getCurrentPosition());
            int green2CurrPosition = playerGreen.getPath().indexOf(playerGreen.getTurtle(2).getCurrentPosition());
            int green3CurrPosition = playerGreen.getPath().indexOf(playerGreen.getTurtle(3).getCurrentPosition());
         
            int green0NewPosition = green0CurrPosition + diceValueTemp;
            int green1NewPosition = green1CurrPosition + diceValueTemp;
            int green2NewPosition = green2CurrPosition + diceValueTemp;
            int green3NewPosition = green3CurrPosition + diceValueTemp;
            ArrayList<Turtle> movableTokensGreen = new ArrayList<>();
            boolean allMovableInBaseGreen = true;
         
            if (green0NewPosition >= playerGreen.getPathLength()) {
               green0skip = false;
            } else movableTokensGreen.add(playerGreen.getTurtle(0));
            if (green1NewPosition >= playerGreen.getPathLength()) {
               green1skip = false;
            } else movableTokensGreen.add(playerGreen.getTurtle(1));
            if (green2NewPosition >= playerGreen.getPathLength()) {
               green2skip = false;
            } else movableTokensGreen.add(playerGreen.getTurtle(2));
            if (green3NewPosition >= playerGreen.getPathLength()) {
               green3skip = false;
            } else movableTokensGreen.add(playerGreen.getTurtle(3));
         
            for (Turtle movableToken : movableTokensGreen) {
               if (movableToken.getCurrentPosition() != movableToken.getStartPosition()) {
                  allMovableInBaseGreen = false;
               }
            }
         
            if (green0skip || green1skip || green2skip || green3skip) {
               movementAvailableG = true;
               if (allMovableInBaseGreen) {
                  movementAvailableG = false;
               }
            }
         
            if (green0NewPosition == (playerGreen.getPathLength() - 4) ||
                   green0NewPosition == (playerGreen.getPathLength() - 3) ||
                   green0NewPosition == (playerGreen.getPathLength() - 2) ||
                   green0NewPosition == (playerGreen.getPathLength() - 1)) {
               if ((green0NewPosition == green1CurrPosition || green0NewPosition == green2CurrPosition || green0NewPosition == green3CurrPosition) && green0CurrPosition != -1) {
                  greenOverlapFinal0 = false;
               }
            }
            if (green1NewPosition == (playerGreen.getPathLength() - 4) ||
                   green1NewPosition == (playerGreen.getPathLength() - 3) ||
                   green1NewPosition == (playerGreen.getPathLength() - 2) ||
                   green1NewPosition == (playerGreen.getPathLength() - 1)) {
               if ((green1NewPosition == green0CurrPosition || green1NewPosition == green2CurrPosition || green1NewPosition == green3CurrPosition) && green1CurrPosition != -1) {
                  greenOverlapFinal1 = false;
               }
            }
            if (green2NewPosition == (playerGreen.getPathLength() - 4) ||
                   green2NewPosition == (playerGreen.getPathLength() - 3) ||
                   green2NewPosition == (playerGreen.getPathLength() - 2) ||
                   green2NewPosition == (playerGreen.getPathLength() - 1)) {
               if ((green2NewPosition == green0CurrPosition || green2NewPosition == green1CurrPosition || green2NewPosition == green3CurrPosition) && green2CurrPosition != -1) {
                  greenOverlapFinal2 = false;
               }
            }
            if (green3NewPosition == (playerGreen.getPathLength() - 4) ||
                   green3NewPosition == (playerGreen.getPathLength() - 3) ||
                   green3NewPosition == (playerGreen.getPathLength() - 2) ||
                   green3NewPosition == (playerGreen.getPathLength() - 1)) {
               if ((green3NewPosition == green0CurrPosition || green3NewPosition == green1CurrPosition || green3NewPosition == green2CurrPosition) && green3CurrPosition != -1) {
                  greenOverlapFinal3 = false;
               }
            }
         
            greenNotClicked = true;
            if (playerGreen.hasPlayersOnBoard()) {
               System.out.println("Dice value after checking hasPlayersOnBoard " + diceValue);
               if (!movementAvailableG) {
                  setInstruction("Instruction: No moves available for tokens on path for player GREEN.");
                  if (diceValue == 6 && playerGreen.hasPlayersInBase())
                     appendInstruction("\nYou got a 6 and can take one token from the base!");
                  else {
                     if (getStartPanel().getNumberOfPlayers() == 3) {
                        appendInstruction("\nPlayer RED - roll dice!");
                     } else {
                        appendInstruction("\nPlayer BLUE - roll dice!");
                     }
                  }
                  increaseTurn();
                  increaseTurnOnServer();
                  setBlockDice(false);
                  break;
               }
               setInstruction("Instruction: Player GREEN - you rolled " + diceValue + ".\nClick on a pawn to move!");
               playerGreen.getTurtleLabel(0).addMouseListener(
                            new MouseAdapter() {
                               @Override
                               public void mouseClicked(MouseEvent click) {
                                  if (greenNotClicked && green0skip && greenOverlapFinal0) {
                                     setInstruction("Instruction: Player GREEN moved pawn 1 by " + diceValue + ".");
                                     if (diceValue == 6) {
                                        appendInstruction("\nYou rolled 6! Roll again!");
                                     } else {
                                        if (getStartPanel().getNumberOfPlayers() == 3) {
                                           appendInstruction("\nPlayer RED - roll dice!");
                                        } else {
                                           appendInstruction("\nPlayer GREEN - roll dice!");
                                        }
                                     }
                                     System.out.println("Clicked");
                                     System.out.println("When clicked: " + diceValue);
                                     playerGreen.moveTurtleForward(0, diceValue, sealudo);
                                     greenNotClicked = false;
                                  }
                               }
                            }
                  );
               playerGreen.getTurtleLabel(1).addMouseListener(
                            new MouseAdapter() {
                               @Override
                               public void mouseClicked(MouseEvent click) {
                                  if (greenNotClicked && green1skip && greenOverlapFinal1) {
                                     setInstruction("Instruction: Player GREEN moved pawn 2 by " + diceValue + ".");
                                     if (diceValue == 6) {
                                        appendInstruction("\nYou rolled 6! Roll again!");
                                     } else {
                                        if (getStartPanel().getNumberOfPlayers() == 3) {
                                           appendInstruction("\nPlayer RED - roll dice!");
                                        } else {
                                           appendInstruction("\nPlayer GREEN - roll dice!");
                                        }
                                     }
                                     System.out.println("Clicked");
                                     System.out.println("When clicked: " + diceValue);
                                     playerGreen.moveTurtleForward(1, diceValue, sealudo);
                                     greenNotClicked = false;
                                  }
                               }
                            }
                  );
               playerGreen.getTurtleLabel(2).addMouseListener(
                            new MouseAdapter() {
                               @Override
                               public void mouseClicked(MouseEvent click) {
                                  if (greenNotClicked && green2skip && greenOverlapFinal2) {
                                     setInstruction("Instruction: Player GREEN moved pawn 3 by " + diceValue + ".");
                                     if (diceValue == 6) {
                                        appendInstruction("\nYou rolled 6! Roll again!");
                                     } else {
                                        if (getStartPanel().getNumberOfPlayers() == 3) {
                                           appendInstruction("\nPlayer RED - roll dice!");
                                        } else {
                                           appendInstruction("\nPlayer GREEN - roll dice!");
                                        }
                                     }
                                     System.out.println("Clicked");
                                     System.out.println("When clicked: " + diceValue);
                                     playerGreen.moveTurtleForward(2, diceValue, sealudo);
                                     greenNotClicked = false;
                                  }
                               }
                            }
                  );
               //if((playerGreen.getPath().indexOf(playerGreen.getTurtle(3).getCurrentPosition()) + diceValueTemp) < playerGreen.getPathLength()) {
               playerGreen.getTurtleLabel(3).addMouseListener(
                            new MouseAdapter() {
                               @Override
                               public void mouseClicked(MouseEvent click) {
                                  if (greenNotClicked && green3skip && greenOverlapFinal3) {
                                     setInstruction("Instruction: Player GREEN moved pawn 4 by " + diceValue + ".");
                                     if (diceValue == 6) {
                                        appendInstruction("\nYou rolled 6! Roll again!");
                                     } else {
                                        if (getStartPanel().getNumberOfPlayers() == 3) {
                                           appendInstruction("\nPlayer RED - roll dice!");
                                        } else {
                                           appendInstruction("\nPlayer GREEN - roll dice!");
                                        }
                                     }
                                     System.out.println("Clicked");
                                     System.out.println("When clicked: " + diceValue);
                                     playerGreen.moveTurtleForward(3, diceValue, sealudo);
                                     greenNotClicked = false;
                                  }
                               }
                            }
                  );
            } else {
               if (diceValue == 6) {
                  setInstruction("Instruction: Player GREEN, you got 6 and left home! Roll Again!");
               } else {
                  setInstruction("Instruction: Darn tootn'! Player GREEN, you didn't get a 6.");
                  if (getStartPanel().getNumberOfPlayers() == 3) {
                     appendInstruction("\nPlayer RED - roll dice!");
                  } else {
                     appendInstruction("\nPlayer BLUE - roll dice!");
                  }
               }
               playerGreen.moveTurtleForward(0, diceValue, sealudo);
            }
         
            break;
      
          /*
          execute the above functionality for blue player
           */
      
         case 4:
         
            blockDice = true;
         
            if (!assignedColor.equals("blue")) {
               break;
            }
         
            System.out.println("Dice value before making actionlisteners: " + diceValue);
         
            blue0skip = true;
            blue1skip = true;
            blue2skip = true;
            blue3skip = true;
            boolean movementAvailableB = false;
            blueOverlapFinal0 = true;
            blueOverlapFinal1 = true;
            blueOverlapFinal2 = true;
            blueOverlapFinal3 = true;
         
            int blue0CurrPosition = playerBlue.getPath().indexOf(playerBlue.getTurtle(0).getCurrentPosition());
            int blue1CurrPosition = playerBlue.getPath().indexOf(playerBlue.getTurtle(1).getCurrentPosition());
            int blue2CurrPosition = playerBlue.getPath().indexOf(playerBlue.getTurtle(2).getCurrentPosition());
            int blue3CurrPosition = playerBlue.getPath().indexOf(playerBlue.getTurtle(3).getCurrentPosition());
         
            int blue0NewPosition = blue0CurrPosition + diceValueTemp;
            int blue1NewPosition = blue1CurrPosition + diceValueTemp;
            int blue2NewPosition = blue2CurrPosition + diceValueTemp;
            int blue3NewPosition = blue3CurrPosition + diceValueTemp;
            ArrayList<Turtle> movableTokensBlue = new ArrayList<>();
            boolean allMovableInBaseBlue = true;
         
            if (blue0NewPosition >= playerBlue.getPathLength()) {
               blue0skip = false;
            } else movableTokensBlue.add(playerBlue.getTurtle(0));
            if (blue1NewPosition >= playerBlue.getPathLength()) {
               blue1skip = false;
            } else movableTokensBlue.add(playerBlue.getTurtle(1));
            if (blue2NewPosition >= playerBlue.getPathLength()) {
               blue2skip = false;
            } else movableTokensBlue.add(playerBlue.getTurtle(2));
            if (blue3NewPosition >= playerBlue.getPathLength()) {
               blue3skip = false;
            } else movableTokensBlue.add(playerBlue.getTurtle(3));
         
            for (Turtle movableToken : movableTokensBlue) {
               if (movableToken.getCurrentPosition() != movableToken.getStartPosition()) {
                  allMovableInBaseBlue = false;
               }
            }
         
            if (blue0skip || blue1skip || blue2skip || blue3skip) {
               movementAvailableB = true;
               if (allMovableInBaseBlue) {
                  movementAvailableB = false;
               }
            }
         
            if (blue0NewPosition == (playerBlue.getPathLength() - 4) ||
                   blue0NewPosition == (playerBlue.getPathLength() - 3) ||
                   blue0NewPosition == (playerBlue.getPathLength() - 2) ||
                   blue0NewPosition == (playerBlue.getPathLength() - 1)) {
               if ((blue0NewPosition == blue1CurrPosition || blue0NewPosition == blue2CurrPosition || blue0NewPosition == blue3CurrPosition) && blue0CurrPosition != -1) {
                  blueOverlapFinal0 = false;
               }
            }
            if (blue1NewPosition == (playerBlue.getPathLength() - 4) ||
                   blue1NewPosition == (playerBlue.getPathLength() - 3) ||
                   blue1NewPosition == (playerBlue.getPathLength() - 2) ||
                   blue1NewPosition == (playerBlue.getPathLength() - 1)) {
               if ((blue1NewPosition == blue0CurrPosition || blue1NewPosition == blue2CurrPosition || blue1NewPosition == blue3CurrPosition) && blue1CurrPosition != -1) {
                  blueOverlapFinal1 = false;
               }
            }
            if (blue2NewPosition == (playerBlue.getPathLength() - 4) ||
                   blue2NewPosition == (playerBlue.getPathLength() - 3) ||
                   blue2NewPosition == (playerBlue.getPathLength() - 2) ||
                   blue2NewPosition == (playerBlue.getPathLength() - 1)) {
               if ((blue2NewPosition == blue0CurrPosition || blue2NewPosition == blue1CurrPosition || blue2NewPosition == blue3CurrPosition) && blue2CurrPosition != -1) {
                  blueOverlapFinal2 = false;
               }
            }
            if (blue3NewPosition == (playerBlue.getPathLength() - 4) ||
                   blue3NewPosition == (playerBlue.getPathLength() - 3) ||
                   blue3NewPosition == (playerBlue.getPathLength() - 2) ||
                   blue3NewPosition == (playerBlue.getPathLength() - 1)) {
               if ((blue3NewPosition == blue0CurrPosition || blue3NewPosition == blue1CurrPosition || blue3NewPosition == blue2CurrPosition) && blue3CurrPosition != -1) {
                  blueOverlapFinal3 = false;
               }
            }
         
            blueNotClicked = true;
            if (playerBlue.hasPlayersOnBoard()) {
               System.out.println("Dice value after checking hasPlayersOnBoard " + diceValue);
               if (!movementAvailableB) {
                  setInstruction("Instruction: no moves available for tokens on path for player BLUE.");
                  if (diceValue == 6 && playerBlue.hasPlayersInBase())
                     appendInstruction("\nYou got a 6 and can take one token from the base!");
                  else appendInstruction("\nPlayer RED - roll the dice!");
                  increaseTurn();
                  increaseTurnOnServer();
                  setBlockDice(false);
                  break;
               }
               setInstruction("Instruction: Player BLUE - you rolled " + diceValue + ".\nClick on a pawn to move!");
               playerBlue.getTurtleLabel(0).addMouseListener(
                            new MouseAdapter() {
                               @Override
                               public void mouseClicked(MouseEvent click) {
                                  if (blueNotClicked && blue0skip && blueOverlapFinal0) {
                                     setInstruction("Instruction: Player BLUE moved pawn 1 by " + diceValue + ".");
                                     if (diceValue == 6) {
                                        appendInstruction("\nYou rolled 6! Roll again!");
                                     } else {
                                        appendInstruction("\nPlayer RED - roll dice!");
                                     }
                                     System.out.println("Clicked");
                                     System.out.println("When clicked: " + diceValue);
                                     playerBlue.moveTurtleForward(0, diceValue, sealudo);
                                     blueNotClicked = false;
                                  
                                  }
                               }
                            }
                  );
               playerBlue.getTurtleLabel(1).addMouseListener(
                            new MouseAdapter() {
                               @Override
                               public void mouseClicked(MouseEvent click) {
                                  if (blueNotClicked && blue1skip && blueOverlapFinal1) {
                                     setInstruction("Instruction: Player BLUE moved pawn 2 by " + diceValue + ".");
                                     if (diceValue == 6) {
                                        appendInstruction("\nYou rolled 6! Roll again!");
                                     } else {
                                        appendInstruction("\nPlayer RED - roll dice!");
                                     }
                                     System.out.println("Clicked");
                                     System.out.println("When clicked: " + diceValue);
                                     playerBlue.moveTurtleForward(1, diceValue, sealudo);
                                     blueNotClicked = false;
                                  }
                               }
                            }
                  );
               playerBlue.getTurtleLabel(2).addMouseListener(
                            new MouseAdapter() {
                               @Override
                               public void mouseClicked(MouseEvent click) {
                                  if (blueNotClicked && blue2skip && blueOverlapFinal2) {
                                     setInstruction("Instruction: Player BLUE moved pawn 3 by " + diceValue + ".");
                                     if (diceValue == 6) {
                                        appendInstruction("\nYou rolled 6! Roll again!");
                                     } else {
                                        appendInstruction("\nPlayer RED - roll dice!");
                                     }
                                     System.out.println("Clicked");
                                     System.out.println("When clicked: " + diceValue);
                                     playerBlue.moveTurtleForward(2, diceValue, sealudo);
                                     blueNotClicked = false;
                                  }
                               }
                            }
                  );
               playerBlue.getTurtleLabel(3).addMouseListener(
                            new MouseAdapter() {
                               @Override
                               public void mouseClicked(MouseEvent click) {
                                  if (blueNotClicked && blue3skip && blueOverlapFinal3) {
                                     setInstruction("Instruction: Player BLUE moved pawn 4 by " + diceValue + ".");
                                     if (diceValue == 6) {
                                        appendInstruction("\nYou rolled 6! Roll again!");
                                     } else {
                                        appendInstruction("\nPlayer RED - roll dice!");
                                     }
                                     System.out.println("Clicked");
                                     System.out.println("When clicked: " + diceValue);
                                     playerBlue.moveTurtleForward(3, diceValue, sealudo);
                                     blueNotClicked = false;
                                  }
                               }
                            }
                  );
            } else {
               if (diceValue == 6) {
                  setInstruction("Instruction: Player BLUE, you got 6 and left home! Roll Again!");
               } else {
                  setInstruction("Instruction: Darn tootn'! Player BLUE you didn't get a 6.\nPlayer RED - roll the dice!");
               }
               playerBlue.moveTurtleForward(0, diceValue, sealudo);
            }
         
            break;
      }
   
   }


}


//==================================
// CLASS PLAYER
//==================================


/**
 * Class Player handles the player of each game participant
 * Handles methods for locations and restarting
 */

class Player implements Serializable {
   private ArrayList<Turtle> pawns = new ArrayList<Turtle>(); //array list of  tokens for each player
   private ArrayList<Point> path = new ArrayList<Point>(); //array list of Points for paths for each player with
   private static final long serialVersionUID = -5109010579654190627L;


   /**
    * constructor for Player class
    *
    * @param turtle1 as player token 1
    * @param turtle2 as player token 2
    * @param turtle3 as player token 3
    * @param turtle4 as player token 4
    * @param _path   as path of each player
    */

   public Player(Turtle turtle1, Turtle turtle2, Turtle turtle3, Turtle turtle4, ArrayList<Point> _path) {
      this.pawns.add(turtle1);
      this.pawns.add(turtle2);
      this.pawns.add(turtle3);
      this.pawns.add(turtle4);
      this.path = _path;
   }

   /**
    * resets pawn to starting position
    *
    * @param i as starting location
    */

   public void resetTurtle(int i) {
      pawns.get(i).restartLocation();
   }

   /**
    * gets turtle label
    *
    * @param i as location of the label
    * @return location as int i
    */
   public JButton getTurtleLabel(int i) {
      return this.getTurtle(i).getTurtleLabel();
   }

   /**
    * gets Turtle object
    *
    * @param i for location of Turtle Object
    * @return i as location of Turtle object
    */

   public Turtle getTurtle(int i) {
      return this.pawns.get(i);
   }

   /**
    * gets path of the player
    *
    * @return path of each player
    */
   public ArrayList getPath() {
      return this.path;
   }

   /**
    * gets path length of each player
    *
    * @return int as length of the path for each player
    */
   public int getPathLength() {
      return this.path.size();
   }

   /**
    * tests if there are players on board
    *
    * @return true if at least one active player is on board
    */

   public boolean hasPlayersOnBoard() {
      for (int i = 0; i < 4; i++) {
         if (pawns.get(i).getCurrentPosition() != pawns.get(i).getStartPosition()) {
            return true;
         }
      }
      return false;
   }

   /**
    * tests if there are players in base
    *
    * @return true if at least one pawn is on base
    */

   public boolean hasPlayersInBase() {
      int num = 0;
      for (int i = 0; i < 4; i++) {
         if (pawns.get(i).getCurrentPosition() == pawns.get(i).getStartPosition()) {
            num++;
         }
      }
      if (num == 0) {
         return false;
      } else {
         return true;
      }
   }

   /**
    * announces the winner, checks if all tokens of a certain player are placed on the final 4 points.
    * declares the winnner if that is the case
    *
    * @param sl to reference the SeaLudo class for winning printing
    */

   public void announceWinner(SeaLudo sl) {
      String winnerName = sl.getStartPanel().getPlayerName();
      int goal = 0;
      for (int i = 0; i < 4; i++) {
         if (pawns.get(i).getCurrentPosition() == path.get(46) ||
                pawns.get(i).getCurrentPosition() == path.get(45) ||
                pawns.get(i).getCurrentPosition() == path.get(44) ||
                pawns.get(i).getCurrentPosition() == path.get(43)) {
            goal++;
         }
      }
      if (goal == 4) {
         JOptionPane.showMessageDialog(null, "CONGRATS " + winnerName + "!!! Victory is yours !");
         System.exit(0);
      }
   }

   public ArrayList<Turtle> getPawns() {
      return pawns;
   }

//==================================
// MOVE TURTLE FORWARD
//==================================


   /**
    * moves the turtle / token forward
    * creates current location of token and uses it to set the new location based on the dice value
    * checks if token is in the base.
    * if it is, checks the dice value. If value is 6, puts the token on first position in the path.
    * Checks if overlapping is there and sets that token to new positon, and sends opponent to base if needed.
    * If value is something other than six, increases the turn.
    * If the token is out of the base, the new location is set as current location + dice value.
    *
    * @param turtleIndex as int number of 4 tokens options
    * @param diceValue   as returned dice value
    * @param sl          as reference to sea ludo main class
    */


   public void moveTurtleForward(int turtleIndex, int diceValue, SeaLudo sl) {
   //        if (sl.getUpdateMoveOnServerBoolean()) {
   //            sl.updateMovementOnServer(turtleIndex, diceValue, this);
   //        }
      Point currLocation = pawns.get(turtleIndex).getCurrentPosition();
      int indexOfCurrLocation = path.indexOf(currLocation);
      Point newLocation;
      //System.out.println("Dice: " + diceValue);
   
      // if ((indexOfCurrLocation + diceValue) < this.path.size()) {
      if (indexOfCurrLocation == -1) {
         if (diceValue == 6) { //int dice value from the throw dice method
            newLocation = path.get(0);
            pawns.get(turtleIndex).setPawnLocation(newLocation);
         
            sl.updateMovementOnServer(turtleIndex, this, newLocation);
            sl.eatIfOverlap(this);
         } else {
            //System.out.println("Before incrementing: " + sl.getNewTurn());
            sl.increaseTurn();
         //                if (sl.getIncreaseTurnAfterMove()) {
            sl.increaseTurnOnServer();
         //                }
         //                sl.setIncreaseTurnAfterMove(true);
            //return;
         }
      } else {
         newLocation = path.get(indexOfCurrLocation + diceValue);
      
         if (diceValue != 6) {
            pawns.get(turtleIndex).setPawnLocation(newLocation);
            announceWinner(sl);
         
            //System.out.println("Before incrementing: " + sl.getNewTurn());
            sl.increaseTurn();
         //                if (sl.getIncreaseTurnAfterMove()) {
            sl.increaseTurnOnServer();
         //                }
         //                sl.setIncreaseTurnAfterMove(true);
            sl.updateMovementOnServer(turtleIndex, this, newLocation);
            sl.eatIfOverlap(this);
         } else {
            //sumDiceValues = sumDiceValues + 6;
            pawns.get(turtleIndex).setPawnLocation(newLocation);
            announceWinner(sl);
         
            sl.updateMovementOnServer(turtleIndex, this, newLocation);
            sl.eatIfOverlap(this);
            //System.out.println("Before incrementing: " + sl.getNewTurn());
         
         
         }
      }
      sl.setBlockDice(false);
   //        sl.setUpdateMoveOnServerBoolean(true);
   
   
   }


}


//==================================
// CLASS TURTLE
//==================================

/**
 * Class Turtle a class that  handles the positioning of the turtles and pawns
 */


class Turtle implements Serializable {

   private JButton turtle;
   private Point startPosition;
   private Point currentPosition;
   private static final long serialVersionUID = -5109010579654190627L;

   /**
    * param constructor that constructs the Turtle object
    *
    * @param _turtle        as JButton of the turtle
    * @param _startPosition as beginning position at the game starting for token
    */

   public Turtle(JButton _turtle, Point _startPosition) {
      this.turtle = _turtle;
      this.turtle.setSize(50, 50);
      this.startPosition = _startPosition;
      this.setPawnLocation(startPosition);
   
   }

   /**
    * gets turtle Label
    *
    * @return JButton
    */
   public JButton getTurtleLabel() {
      return this.turtle;
   }

   /**
    * sets location of the pawn
    * assigns position to current position
    *
    * @param newPosition as Point locationn
    */
   public void setPawnLocation(Point newPosition) {
      this.currentPosition = newPosition;
      this.turtle.setLocation(currentPosition);
   }

   /**
    * restarts location of the pawn
    * sets token position to its its initial position
    */
   public void restartLocation() {
      this.setPawnLocation(this.startPosition);
   }

   /**
    * gets starting position of the token
    *
    * @return Point
    */
   public Point getStartPosition() {
      return this.startPosition;
   }

   /**
    * gets curreent positiion of the token
    *
    * @return Point
    */
   public Point getCurrentPosition() {
      return this.currentPosition;
   }

}


/**
 * class BackgroundPanel that extends JPanele and draws the image on background panel
 */

//JPanel with image background
class BackgroundPanel extends JPanel {
   Image image;

   public BackgroundPanel(String imageName) {         //loading image from the file
   
      try {
         image = javax.imageio.ImageIO.read(new java.net.URL(getClass().getResource(imageName), imageName));
      } catch (Exception e) {
      
      }
   }

   /**
    * method paint from Graphics to paint the image
    * sets the image to the given image from file
    */
   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (image != null)
         g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
   }


   /**
    * changes image to new image background to change the theme
    *
    * @param imageName as name of image
    */

   public void setImage(String imageName) {
      try {
         image = ImageIO.read(new java.net.URL(getClass().getResource(imageName), imageName));
         this.revalidate();
         this.repaint();
      
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}

