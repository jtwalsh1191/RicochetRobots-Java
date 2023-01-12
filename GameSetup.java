import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

// This class renders the Setup Page use case and the GUI is implemented in accordance with the UI Prototype Sketch

public class GameSetup extends JFrame implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 6866674062069549229L;
    private JLabel ricochet, newgame, visual_settings, board_settings, numberofplayer;
    private JPanel main, top, middle, bottom, input, boardsettings, visualsettings, ricochet_label, top_buttons, newgame_label;
    private JButton resume, exit, help, startgame;
    private JRadioButton text, color, text_color, simpleboard, complexboard;
    private JComboBox<Integer> numberofusers;
    private ButtonGroup bs, vs;
    private String currentplayer, currentbid;
    private ArrayList<Integer> targetlist, iconlist;
    private ArrayList<RobotPosition> robotpost, originalposition;
    private HashMap<String,Integer> scoreboard;
    private HashMap<Integer, Integer> player_time;
    private int visualoption, difficulty, targetchip, targettext, savedtime;

    public GameSetup(){
        super("Setting up game");
        this.setSize(500,800);


        // ArrayLists and HashMaps used to pass the save data on to the LoadTable class which loads the saved game
        this.targetlist = new ArrayList<>();
        this.iconlist = new ArrayList<>();
        this.robotpost = new ArrayList<>();
        this.originalposition = new ArrayList<>();
        this.scoreboard = new HashMap<>();
        this.player_time = new HashMap<>();
        Integer[] users = {1,2,3,4};

        //Sections where all JLabels are declared
        ricochet = new JLabel("RICOCHET");
        ricochet.setBorder(BorderFactory.createLineBorder(Color.black));
        ricochet.setFont(new Font("Helvetica", Font.BOLD, 30));
        ricochet.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        ricochet.setHorizontalAlignment(SwingConstants.CENTER);
        numberofplayer = new JLabel("Enter the number of players ");
        newgame = new JLabel("New Game");
        newgame.setBorder(BorderFactory.createLineBorder(Color.black));
        newgame.setFont(new Font("Helvetica", Font.BOLD, 30));
        newgame.setBackground(Color.RED);
        newgame.setOpaque(true);
        newgame.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        newgame.setHorizontalAlignment(SwingConstants.CENTER);
        visual_settings = new JLabel("Choose your visual settings");
        board_settings = new JLabel("Choose your board settings");

        top_buttons = new JPanel();
        main = new JPanel();
        top = new JPanel();
        middle = new JPanel();
        bottom = new JPanel();
        //JPanel for the user input section
        input = new JPanel();
        //JPanel for the Visual Settings section
        visualsettings = new JPanel();
        //JPanel for the Board Settngs section
        boardsettings = new JPanel();

        //Both ButtonGroup declared to incorporate the RadioButtons for both Visual Settings and Board Settings respectively
        this.vs = new ButtonGroup();
        this.bs = new ButtonGroup();

        resume = new JButton("Resume");
        resume.addActionListener(this);
        exit = new JButton("Exit");
        exit.addActionListener(this);
        help = new JButton("Help");
        help.addActionListener(this);
        startgame = new JButton("Start Game");
        startgame.addActionListener(this);
        startgame.setAlignmentX(Component.CENTER_ALIGNMENT);

        // RadioButton options for Visual settings
        text = new JRadioButton("Text");
        text.setActionCommand("Text");
        color = new JRadioButton("Color");
        color.setActionCommand("Color");
        text_color = new JRadioButton("Text & Color");
        text_color.setActionCommand("tnc");

        // RadioButton options for Board settings
        simpleboard = new JRadioButton("Simple Board");
        simpleboard.setActionCommand("simple");
        complexboard = new JRadioButton("Complex Board");
        complexboard.setActionCommand("complex");

        //This combo box is for player(s) to choose the number of human player(s)
        numberofusers = new JComboBox<>(users);

        // Board Settings' RadioButtons are added to the ButtonGroup to ensure no duplicate selection
        bs.add(simpleboard);
        bs.add(complexboard);

        // Visual Setting's RadioButtons are added to the ButtonGroup to ensure no duplicate selection
        vs.add(text);
        vs.add(color);
        vs.add(text_color);

        // Panel that features the Resume, Exit, and Help buttons
        top_buttons.setLayout(new FlowLayout());
        top_buttons.add(resume);
        top_buttons.add(exit);
        top_buttons.add(help);
        
        // Top part of the frame that holds the title of the game and the three buttons in the "top_buttons" JPanel
        top.setLayout(new GridLayout(2,1,0,0));
        top.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        top.add(ricochet);
        top.add(top_buttons);

        // Panel for input of players 
        input.setLayout(new FlowLayout());
        input.add(numberofplayer);
        input.add(numberofusers);

        // Panel for visual settings selection
        visualsettings.setLayout(new GridLayout(4,1,0,0));
        visualsettings.setBorder(BorderFactory.createLineBorder(Color.black));
        visualsettings.add(visual_settings);
        visualsettings.add(text);
        visualsettings.add(color);
        visualsettings.add(text_color);

        // Panel for board settings selection
        boardsettings.setLayout(new GridLayout(3,1,0,0));
        boardsettings.setBorder(BorderFactory.createLineBorder(Color.black));
        boardsettings.add(board_settings);
        boardsettings.add(simpleboard);
        boardsettings.add(complexboard);

        // The middle part of the GUI frame that features User input and selections
        middle.setLayout(new GridLayout(4,1,10,10));
        middle.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        middle.add(newgame);
        middle.add(input);
        middle.add(visualsettings);
        middle.add(boardsettings);

        // Setting up the main JPanel 
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(top);
        main.add(Box.createRigidArea(new Dimension(5, 20)));
        main.add(middle);
        main.add(Box.createRigidArea(new Dimension(5, 20)));
        main.add(startgame);

        getContentPane().add(main);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Start Game")){
            // Get the visual settings selection
            String visualoption = this.vs.getSelection().getActionCommand();
            // Get the board settings selection
            String boardoption = this.bs.getSelection().getActionCommand();
            // Get the number of human player(s)
            Integer o = (Integer)numberofusers.getSelectedItem();
            int playersselection = o.intValue();
            StringBuilder sb = new StringBuilder();
            sb.append("Number of player(s): " + playersselection + "\n");
            sb.append("Board Settings: " + boardoption + "\n");
            sb.append("Visual Settings: " + visualoption);

            // Shows a confirmation dialog to the user, if it's correct it will bring player(s) to the main game, allows user to choose again if incorrect
            int option = JOptionPane.showConfirmDialog(this.getContentPane(), sb.toString(), "Confirmation", JOptionPane.YES_NO_OPTION);
            if(option == JOptionPane.YES_OPTION){
                if(boardoption.equals("simple")){ // 0 represents Simple settings
                    if(visualoption.equals("Text")){ // 0 represents Text settings
                        BoardSetup b = new BoardSetup(16, 16, playersselection, 0, 0); //BoardSetup(int x, int y, int players, int visualoption, int difficulty)
                    }
                    else if(visualoption.equals("Color")){ // 1 represents Color settings
                        BoardSetup b = new BoardSetup(16, 16, playersselection, 1, 0);
                    }
                    else{ // 2 represents Text and Color settings
                        BoardSetup b = new BoardSetup(16, 16, playersselection, 2, 0);
                    }
                }
                else{ // 1 represents complex settings
                    if(visualoption.equals("Text")){
                        BoardSetup b = new BoardSetup(16, 16, playersselection, 0, 1);
                    }
                    else if(visualoption.equals("Color")){
                        BoardSetup b = new BoardSetup(16, 16, playersselection, 1, 1);
                    }
                    else{
                        BoardSetup b = new BoardSetup(16, 16, playersselection, 2, 1);
                    }
                }
            }
        }
        else if(e.getActionCommand().equals("Resume")){ //Read data from each files containing the saved data
            try{ // Saved target chip
                File file = new File("targetchipandtext.txt");
                BufferedReader br = new BufferedReader(new FileReader(file));

                String[] st = br.readLine().split(",");
                this.targetchip = Integer.valueOf(st[0]);
                this.targettext = Integer.valueOf(st[1]);
                br.close();
            }catch(Exception e1){
                e1.printStackTrace();
            }

            try{ // Saved icon list
                File file = new File("targetlist.txt");
                BufferedReader br = new BufferedReader(new FileReader(file));

                String s = br.readLine();
                for(int i = 0; i<s.length(); i++){
                    this.targetlist.add(Integer.valueOf(Character.toString(s.charAt(i))));
                }
                br.close();
            }catch(Exception e1){
                e1.printStackTrace();
            }

            try{
                File file = new File("Iconlist.txt");
                BufferedReader br = new BufferedReader(new FileReader(file));

                String s = br.readLine();
                for(int i = 0; i < s.length(); i++){
                    this.iconlist.add(Integer.valueOf(Character.toString(s.charAt(i))));
                }
                br.close();
            }catch(Exception e1){
                e1.printStackTrace();
            }

            try{ // Saved Robot data
                File file = new File("robotposition.txt");
                BufferedReader br = new BufferedReader(new FileReader(file));

                String line = null;
                while((line = br.readLine()) != null){
                    String[] robotcoord = line.split(",");
                    RobotPosition newrobot = new RobotPosition(Integer.valueOf(robotcoord[0]), Integer.valueOf(robotcoord[1]));
                    this.robotpost.add(newrobot);
                }
                br.close();
            }catch(Exception e1){
                e1.printStackTrace();
            }

            try{ // Saved Scoreboard data
                File file = new File("scoreboard.txt");
                BufferedReader br = new BufferedReader(new FileReader(file));

                String line = null;
                while((line = br.readLine()) != null){
                    String[] scoreboardstring = line.split(",");
                    this.scoreboard.put(scoreboardstring[0], Integer.valueOf(scoreboardstring[1]));
                }
                br.close();
            }catch(Exception e1){
                e1.printStackTrace();
            }

            try{ // Save file of remaining time
                File file = new File("timer.txt");
                BufferedReader br = new BufferedReader(new FileReader(file));

                this.savedtime = Integer.valueOf(br.readLine());
                br.close();
            }catch(Exception e1){
                e1.printStackTrace();
            }

            try{ // Save file of the current player details
                File file = new File("currentplayer.txt");
                BufferedReader br = new BufferedReader(new FileReader(file));

                this.currentplayer = br.readLine();
                br.close();
            }catch(Exception e1){
                e1.printStackTrace();
            }

            try{
                File file = new File("bidtext.txt");
                BufferedReader br = new BufferedReader(new FileReader(file));

                this.currentbid = br.readLine();
                br.close();
            }catch(Exception e1){
                e1.printStackTrace();
            }

            try{ // Save file of the round history 
                File file = new File("playertime.txt");
                BufferedReader br = new BufferedReader(new FileReader(file));

                String line = null;
                while((line = br.readLine()) != null){
                    String[] playertime = line.split(",");
                    this.player_time.put(Integer.valueOf(playertime[0]), Integer.valueOf(playertime[1]));
                }
                br.close();
            }catch(Exception e1){
                e1.printStackTrace();
            }

            try{ // Save file of the visual option as well as difficulty
                File file = new File("selections.txt");
                BufferedReader br = new BufferedReader(new FileReader(file));

                String[] selections = br.readLine().split(",");
                this.visualoption = Integer.valueOf(selections[0]);
                this.difficulty = Integer.valueOf(selections[1]);

                br.close();
            }catch(Exception e1){
                e1.printStackTrace();
            }

            try{
                File file = new File("originalposition.txt");
                BufferedReader br = new BufferedReader(new FileReader(file));

                String line = null;
                while((line = br.readLine()) != null){
                    String[] robotcoord = line.split(",");
                    RobotPosition newrobot = new RobotPosition(Integer.valueOf(robotcoord[0]), Integer.valueOf(robotcoord[1]));
                    this.originalposition.add(newrobot);
                }
                br.close();
            }catch(Exception e1){
                e1.printStackTrace();
            }

            LoadTable l = new LoadTable(this.targetchip, this.targettext, this.targetlist, this.iconlist, this.robotpost, this.originalposition, this.scoreboard, this.savedtime, this.currentplayer, this.currentbid, this.visualoption, this.difficulty, 16, 16, this.player_time);
        }
        else if(e.getActionCommand().equals("Exit")){
            System.exit(0);
        }
        else if(e.getActionCommand().equals("Help")){ // Displays the instructions on setting up the game
            StringBuilder sb = new StringBuilder();

            sb.append("Instructions on setting up the game\n");
            sb.append("1) Select the number of human player(s)\n");
            sb.append("2) Choose the visual option for the game (Color, Text, Color & Text)\n");
            sb.append("3) Choose the board option for the game (Simple, Complex)\n");
            sb.append("4) Click 'Start' and you are all set to go! Have fun playing!\n");
            sb.append("--------------------------------------------------------------\n");
            sb.append("The system will show a confirmation dialog to make sure everything's is selected as inteneded!");

            JOptionPane.showMessageDialog(this.getContentPane(), sb.toString());
        }
    }
}
