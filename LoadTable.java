
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.HashSet;
import java.util.ArrayList;
import java.io.*;

// This class takes in the saved data as parameters and the datas are then used to initialise the saved board
// This class behaves very similarly to the BoardSetup class, the only difference being that certain values are fixed instead of randomised
public class LoadTable extends JFrame implements ActionListener, MouseListener{
    
    /**
	 *
	 */
	private static final long serialVersionUID = -3950979147460612062L;
	private JPanel maingame, bottom_panel, titlepanel, boardpanel, userpanel, playerturn, timer, chipdeck, scoreboard_panel, robotselect, arrows, enteryourbid;
    private Icon chip;
    private ArrayList<Integer> targetlist, iconlist;
    private ArrayList<RobotPosition> robotpost, originalposition;
    private HashMap<String, Integer> scoreboard;
    private String currentplayer, currentbid;
    private int visualoption, difficulty, x, y;
    private JTable scoreboardtable;
    private JButton savegame, exitgame, hint, startclock, resumeclock, pauseclock, up, down, left, right, bids, resetrobotselection, resetmove;
    private JLabel whos_turn, which_chip, timer_label, scoreboard_label, taketurn, time, visual_option, lowestbid;
    private JTextField bid, whichplayer;
    private BoardGrid[][] grid;
    private int paint, targetchip, currentrobot, targettext, savedtime, winner, rounds;
    private String lexicon;
    private HashMap<Integer, Integer> player_time;
    private HashMap<String, Integer> computer;
    private Random rand;
    private Timer newtimer;
    private ArrayList<ArrayList<Integer>> rp;
    private ArrayList<String> robottext;
    private boolean reached;

    public LoadTable(int targetchip, int targettext, ArrayList<Integer> targetlist, ArrayList<Integer> iconlist, ArrayList<RobotPosition> robotpost, ArrayList<RobotPosition> originalposition, HashMap<String, Integer> scoreboard, int savedtime, String currentplayer, String currentbid, int visualoption, int difficulty, int x, int y, HashMap<Integer, Integer> player_time){
        super("Ricochet Robot");
        this.setSize(3000,1000);

        this.targetchip = targetchip;
        this.targettext = targettext;
        this.targetlist = targetlist;
        this.iconlist = iconlist;
        this.robotpost = robotpost;
        this.originalposition = originalposition;
        this.scoreboard = scoreboard;
        this.savedtime = savedtime;
        this.currentplayer = currentplayer;
        this.currentbid = currentbid;
        this.visualoption = visualoption;
        this.difficulty = difficulty;
        this.rounds = 0;
        this.reached = false;
        this.x = x;
        this.y = y;
        this.lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        this.rp = new ArrayList<>();
        
        this.player_time = player_time;
        rand = new Random();

        String[] scoreboard_header = { "Player", "Score" };
        DefaultTableModel dtm = new DefaultTableModel(scoreboard_header, 0);
        scoreboardtable = new JTable(dtm);
        dtm.addRow(scoreboard_header);

        for (Map.Entry<String, Integer> entry : scoreboard.entrySet()) {
            dtm.addRow(new Object[] { entry.getKey(), entry.getValue() });
        }

        boardpanel = new JPanel();
        boardpanel.setLayout(new GridLayout(x, y));
        userpanel = new JPanel();
        bottom_panel = new JPanel();
        maingame = new JPanel();
        titlepanel = new JPanel();
        playerturn = new JPanel();
        timer = new JPanel();
        chipdeck = new JPanel();
        scoreboard_panel = new JPanel();
        // "chip" is declared as an instance of class "Icon" so that shapes and colours
        // and be added
        chip = new Icon(iconlist.get(this.targetchip));
        this.targetchip++;
        chip.setPreferredSize(new Dimension(100, 100));
        robotselect = new JPanel();
        robotselect.setLayout(new GridLayout(0, 4));
        arrows = new JPanel();
        arrows.setLayout(new GridLayout(0, 4));
        enteryourbid = new JPanel();

        bid = new JTextField(16);
        whichplayer = new JTextField(16);

        whos_turn = new JLabel(currentplayer);
        whos_turn.setFont(new Font("Helvetica", Font.BOLD, 20));
        whos_turn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        whos_turn.setHorizontalAlignment(SwingConstants.CENTER);
        which_chip = new JLabel("Chip Deck");
        timer_label = new JLabel("Timer");
        scoreboard_label = new JLabel("Scoreboard");
        taketurn = new JLabel("Take a turn");
        // Set the "time" JLabel to display the time for each round
        String countdown = secondsToString(Integer.valueOf(savedtime));
        time = new JLabel(countdown);
        lowestbid = new JLabel(currentbid);
        lowestbid.setFont(new Font("Helvetica", Font.BOLD, 15));
        lowestbid.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        lowestbid.setHorizontalAlignment(SwingConstants.CENTER);

        // Setting random letter as the destination chip if text option is chosen
        String s = Character.toString(lexicon.charAt(iconlist.get(this.targettext)));
        this.targettext++;
        visual_option = new JLabel(s);
        

        hint = new JButton("Hint");
        savegame = new JButton("Save");
        exitgame = new JButton("Exit");
        startclock = new JButton("Enter bid (Start)");
        pauseclock = new JButton("Pause");
        resumeclock = new JButton("Resume");
        up = new JButton("Up");
        up.setSize(10, 10);
        down = new JButton("Down");
        down.setSize(10, 10);
        left = new JButton("Left");
        left.setSize(10, 10);
        right = new JButton("Right");
        right.setSize(10, 10);
        bids = new JButton("Enter more bids");
        resetrobotselection = new JButton("Reset Robot Selection");
        resetmove = new JButton("Reset moves");

        hint.addActionListener(this);
        savegame.addActionListener(this);
        exitgame.addActionListener(this);
        startclock.addActionListener(this);
        if(Integer.valueOf(savedtime) != 60){
            startclock.setEnabled(false);
        }else{
            resumeclock.setEnabled(false);
        }
        pauseclock.addActionListener(this);
        resumeclock.addActionListener(this);
        up.addActionListener(this);
        down.addActionListener(this);
        right.addActionListener(this);
        left.addActionListener(this);
        bids.addActionListener(this);
        bids.setEnabled(false);
        resetrobotselection.addActionListener(this);
        resetmove.addActionListener(this);


        if (visualoption == 0) {
            for (int column = 0; column < 4; column++) {
                RobotPosition temp = new RobotPosition(robotpost.get(column).returnrobotx(), robotpost.get(column).returnroboty());
                temp.colourrobot(column+4);
                temp.addMouseListener(this);
                robotselect.add(temp);
            }
        } else if (visualoption == 1) {
            for (int column = 0; column < 4; column++) {
                RobotPosition temp = new RobotPosition(robotpost.get(column).returnrobotx(), robotpost.get(column).returnroboty());
                temp.colourrobot(column);
                temp.addMouseListener(this);
                robotselect.add(temp);
            }
        }else{
            for(int column = 0; column < 4; column++){
                RobotPosition temp = new RobotPosition(robotpost.get(column).returnrobotx(), robotpost.get(column).returnroboty());
                temp.colourrobot(column + 8);
                temp.addMouseListener(this);
                robotselect.add(temp);
            }
        }

        // Setting up the main game board using a 2D array instance of the class
        // "BoardGrid"
        grid = new BoardGrid[x][y];
        // Goes through the first if statement if "Simple Board" is chosen
        if (difficulty == 0) {
            for (int column = 0; column < x; column++) {
                for (int row = 0; row < y; row++) {
                    grid[column][row] = new BoardGrid(x, y, column, row);
                    grid[column][row].backgroundSet(visualoption, targetlist);
                    if (visualoption == 0) {
                        if (column == robotpost.get(0).returnrobotx() && row == robotpost.get(0).returnroboty()) {
                            grid[column][row].setrobot(22);
                        } else if (column == robotpost.get(1).returnrobotx()
                                && row == robotpost.get(1).returnroboty()) {
                            grid[column][row].setrobot(23);
                        } else if (column == robotpost.get(2).returnrobotx()
                                && row == robotpost.get(2).returnroboty()) {
                            grid[column][row].setrobot(24);
                        } else if (column == robotpost.get(3).returnrobotx()
                                && row == robotpost.get(3).returnroboty()) {
                            grid[column][row].setrobot(25);
                        }
                    } else if (visualoption == 1) {
                        if (column == robotpost.get(0).returnrobotx() && row == robotpost.get(0).returnroboty()) {
                            grid[column][row].setrobot(18);
                        } else if (column == robotpost.get(1).returnrobotx()
                                && row == robotpost.get(1).returnroboty()) {
                            grid[column][row].setrobot(19);
                        } else if (column == robotpost.get(2).returnrobotx()
                                && row == robotpost.get(2).returnroboty()) {
                            grid[column][row].setrobot(20);
                        } else if (column == robotpost.get(3).returnrobotx()
                                && row == robotpost.get(3).returnroboty()) {
                            grid[column][row].setrobot(21);
                        }
                    } else {
                        if (column == robotpost.get(0).returnrobotx() && row == robotpost.get(0).returnroboty()) {
                            grid[column][row].setrobot(43);
                        } else if (column == robotpost.get(1).returnrobotx()
                                && row == robotpost.get(1).returnroboty()) {
                            grid[column][row].setrobot(44);
                        } else if (column == robotpost.get(2).returnrobotx()
                                && row == robotpost.get(2).returnroboty()) {
                            grid[column][row].setrobot(45);
                        } else if (column == robotpost.get(3).returnrobotx()
                                && row == robotpost.get(3).returnroboty()) {
                            grid[column][row].setrobot(46);
                        }
                    }
                    grid[column][row].addMouseListener(this);

                    boardpanel.add(grid[column][row]);
                }
            }
        } else { // Goes through this statement if "Complex Board" is chosen
        for (int column = 0; column < x; column++) {
            for (int row = 0; row < y; row++) {
                grid[column][row] = new BoardGrid(x, y, column, row);
                grid[column][row].backgroundSetComplex(visualoption, targetlist);
                if (visualoption == 0) { // "Text" option displays robots in word form
                    if (column == robotpost.get(0).returnrobotx() && row == robotpost.get(0).returnroboty()) {
                        grid[column][row].setrobot(22);
                    } else if (column == robotpost.get(1).returnrobotx()
                            && row == robotpost.get(1).returnroboty()) {
                        grid[column][row].setrobot(23);
                    } else if (column == robotpost.get(2).returnrobotx()
                            && row == robotpost.get(2).returnroboty()) {
                        grid[column][row].setrobot(24);
                    } else if (column == robotpost.get(3).returnrobotx()
                            && row == robotpost.get(3).returnroboty()) {
                        grid[column][row].setrobot(25);
                    }
                } else if (visualoption == 1) { // "Color" option displays robots in colored graphics form
                    if (column == robotpost.get(0).returnrobotx() && row == robotpost.get(0).returnroboty()) {
                        grid[column][row].setrobot(18);
                    } else if (column == robotpost.get(1).returnrobotx()
                            && row == robotpost.get(1).returnroboty()) {
                        grid[column][row].setrobot(19);
                    } else if (column == robotpost.get(2).returnrobotx()
                            && row == robotpost.get(2).returnroboty()) {
                        grid[column][row].setrobot(20);
                    } else if (column == robotpost.get(3).returnrobotx()
                            && row == robotpost.get(3).returnroboty()) {
                        grid[column][row].setrobot(21);
                    }
                } else { // "Text and Color" option displays robots in both colored and word form
                    if (column == robotpost.get(0).returnrobotx() && row == robotpost.get(0).returnroboty()) {
                        grid[column][row].setrobot(43);
                    } else if (column == robotpost.get(1).returnrobotx()
                            && row == robotpost.get(1).returnroboty()) {
                        grid[column][row].setrobot(44);
                    } else if (column == robotpost.get(2).returnrobotx()
                            && row == robotpost.get(2).returnroboty()) {
                        grid[column][row].setrobot(45);
                    } else if (column == robotpost.get(3).returnrobotx()
                            && row == robotpost.get(3).returnroboty()) {
                        grid[column][row].setrobot(46);
                    }
                }

                grid[column][row].addMouseListener(this);
                boardpanel.add(grid[column][row]);
            }
        }
        }

        // The top part of the main game frame that gives player(s) options to ask for
        // hints, save game, or exit game
        titlepanel.setPreferredSize(new Dimension(3000, 100));
        titlepanel.setLayout(new FlowLayout());
        titlepanel.add(taketurn);
        titlepanel.add(hint);
        titlepanel.add(savegame);
        titlepanel.add(exitgame);

        enteryourbid.setLayout(new GridLayout(0,1));
        enteryourbid.add(new JLabel("Bid your move"));
        enteryourbid.add(bid);
        enteryourbid.add(new JLabel("Player"));
        enteryourbid.add(whichplayer);

        // Panel that shows the destination chip or letter
        chipdeck.setLayout(new GridLayout(3, 1));
        chipdeck.add(which_chip);
        chipdeck.add(chip);
        chipdeck.add(visual_option);

        // If "Text" option is chosen, the chip display will be hidden
        if (visualoption == 0) {
            chip.setVisible(false);
        } else if (visualoption == 1) { // If "Color" option is chosen, the letter display will be hidden
            visual_option.setVisible(false);
        }
        else{
            chip.setVisible(true);
            visual_option.setVisible(true);
        }

        // Setting up the timer part
        timer.setLayout(new GridLayout(5, 1));
        timer.add(timer_label);
        timer.add(time);
        timer.add(resumeclock);
        timer.add(startclock);
        timer.add(bids);
        if(!whos_turn.getText().equals("Player")){
            enableComponents(timer, false);
        }


        arrows.add(up);
        arrows.add(down);
        arrows.add(left);
        arrows.add(right);

        playerturn.setLayout(new GridLayout(5, 1));
        playerturn.add(whos_turn);
        playerturn.add(lowestbid);
        playerturn.add(robotselect);
        playerturn.add(arrows);
        playerturn.add(resetrobotselection);
        playerturn.add(resetmove);
        if(whos_turn.getText().equals("Player")){
            enableComponents(playerturn, false);
        }
        

        // Scoreboard
        scoreboard_panel.setLayout(new GridLayout(2, 1));
        scoreboard_panel.add(scoreboard_label);
        scoreboard_panel.add(scoreboardtable);

        // The information panel of the game
        userpanel.setLayout(new GridLayout(4, 1, 20, 20));
        userpanel.add(playerturn);
        userpanel.add(chipdeck);
        userpanel.add(timer);
        userpanel.add(scoreboard_panel);

        // The main game panel that features the board and the player information
        bottom_panel.setLayout(new BoxLayout(bottom_panel, BoxLayout.X_AXIS));
        bottom_panel.add(boardpanel);
        bottom_panel.add(userpanel);

        // Setting up the full frame
        maingame.setLayout(new BoxLayout(maingame, BoxLayout.Y_AXIS));
        maingame.add(titlepanel);
        maingame.add(bottom_panel);

        getContentPane().add(maingame);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);
    } 

    private String secondsToString(int pTime) {
        final int min = pTime / 60;
        final int sec = pTime - (min * 60);

        final String strMin = placeZeroIfNeede(min);
        final String strSec = placeZeroIfNeede(sec);
        return String.format("%s:%s", strMin, strSec);
    }

    // Properly format the minute string with leading zeroes if needed
    private String placeZeroIfNeede(int number) {
        return (number >= 10) ? Integer.toString(number) : String.format("0%s", Integer.toString(number));
    }

    public void actionPerformed(ActionEvent e) {
        Object click = e.getSource();

        if (click.equals(startclock)) {
            // Set up a swing timer for the time countdown for the round with one second
            // delay
            Container cont = this.getContentPane();
            int continuebidding = JOptionPane.showConfirmDialog(null, enteryourbid, "Enter bid", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(continuebidding == JOptionPane.OK_OPTION){
                int biddingplayer = Integer.valueOf(whichplayer.getText());
                int placedbid = Integer.valueOf(bid.getText());

                player_time.replace(biddingplayer, placedbid);
                bids.setEnabled(true);
                startclock.setEnabled(false);
                resumeclock.setEnabled(false);
                newtimer = new Timer(1000, new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent aev) {
                        // For each second, the "time" JLabel will update itself 
                        time.setText(secondsToString(savedtime));
                        savedtime--;
                        if(savedtime == -1){
                            newtimer.stop();
                            JOptionPane.showMessageDialog(cont, "Time's Up!");
                            int smallestbidplayer = 0;
                            ArrayList<Integer> totalbid = new ArrayList<>();
                            for(Map.Entry<Integer, Integer> entry: player_time.entrySet()){
                                totalbid.add(entry.getValue());
                            }
                            int smallestbid = 0;
                            if(totalbid.get(0) != 0){
                                smallestbid = totalbid.get(0);
                                smallestbidplayer = 1;
                            }
                            else if(totalbid.get(1) != 0){
                                smallestbid = totalbid.get(1);
                                smallestbidplayer = 2;
                            }
                            else if(totalbid.get(2) != 0){
                                smallestbid = totalbid.get(2);
                                smallestbidplayer = 3;
                            }
                            else if(totalbid.get(3) != 0){
                                smallestbid = totalbid.get(3);
                                smallestbidplayer = 4;
                            }
                            for(int i = 0; i < totalbid.size(); i++){
                                if(totalbid.get(i) != 0){
                                    if(totalbid.get(i) < smallestbid){
                                        smallestbid = totalbid.get(i);
                                        smallestbidplayer = i+1;
                                    }
                                }
                            }
                            switch(smallestbidplayer){
                                case 1:
                                    whos_turn.setText("Player 1's turn");
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("Bid: " + smallestbid);
                                    lowestbid.setText(sb.toString());
                                    break;
                                case 2:
                                    whos_turn.setText("Player 2's turn");
                                    StringBuilder sb1 = new StringBuilder();
                                    sb1.append("Bid: " + smallestbid);
                                    lowestbid.setText(sb1.toString());
                                    break;
                                case 3:
                                    whos_turn.setText("Player 3's turn");
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append("Bid: " + smallestbid);
                                    lowestbid.setText(sb2.toString());
                                    break;
                                case 4:
                                    whos_turn.setText("Player 4's turn");
                                    StringBuilder sb3 = new StringBuilder();
                                    sb3.append("Bid: " + smallestbid);
                                    lowestbid.setText(sb3.toString());
                                    break;
                                default: break;
                            }
                            enableComponents(timer, false);
                            enableComponents(playerturn, true);
                        }
                    }
                });
                newtimer.start();
            }
        }else if(click.equals(resumeclock)){
            Container cont = this.getContentPane();
            bids.setEnabled(true);
            resumeclock.setEnabled(false);
            newtimer = new Timer(1000, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent aev) {
                    // For each second, the "time" JLabel will update itself 
                    time.setText(secondsToString(savedtime));
                    savedtime--;
                    if(savedtime == -1){
                        newtimer.stop();
                        JOptionPane.showMessageDialog(cont, "Time's Up!");
                        int smallestbidplayer = 0;
                        ArrayList<Integer> totalbid = new ArrayList<>();
                        for(Map.Entry<Integer, Integer> entry: player_time.entrySet()){
                            totalbid.add(entry.getValue());
                        }
                        int smallestbid = 0;
                        if(totalbid.get(0) != 0){
                            smallestbid = totalbid.get(0);
                            smallestbidplayer = 1;
                        }
                        else if(totalbid.get(1) != 0){
                            smallestbid = totalbid.get(1);
                            smallestbidplayer = 2;
                        }
                        else if(totalbid.get(2) != 0){
                            smallestbid = totalbid.get(2);
                            smallestbidplayer = 3;
                        }
                        else if(totalbid.get(3) != 0){
                            smallestbid = totalbid.get(3);
                            smallestbidplayer = 4;
                        }
                        for(int i = 0; i < totalbid.size(); i++){
                            if(totalbid.get(i) != 0){
                                if(totalbid.get(i) < smallestbid){
                                    smallestbid = totalbid.get(i);
                                    smallestbidplayer = i+1;
                                }
                            }
                        }
                        switch(smallestbidplayer){
                            case 1:
                                whos_turn.setText("Player 1's turn");
                                StringBuilder sb = new StringBuilder();
                                sb.append("Bid: " + smallestbid);
                                lowestbid.setText(sb.toString());
                                break;
                            case 2:
                                whos_turn.setText("Player 2's turn");
                                StringBuilder sb1 = new StringBuilder();
                                sb1.append("Bid: " + smallestbid);
                                lowestbid.setText(sb1.toString());
                                break;
                            case 3:
                                whos_turn.setText("Player 3's turn");
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("Bid: " + smallestbid);
                                lowestbid.setText(sb2.toString());
                                break;
                            case 4:
                                whos_turn.setText("Player 4's turn");
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("Bid: " + smallestbid);
                                lowestbid.setText(sb3.toString());
                                break;
                            default: break;
                        }
                        enableComponents(timer, false);
                        enableComponents(playerturn, true);
                    }
                }
            });
            newtimer.start();
        } else if (click.equals(bids)) { 
            int continuebidding = JOptionPane.showConfirmDialog(null, enteryourbid, "Enter bid", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(continuebidding == JOptionPane.OK_OPTION){
                int biddingplayer = Integer.valueOf(whichplayer.getText());
                int placedbid = Integer.valueOf(bid.getText());

                player_time.replace(biddingplayer, placedbid);
            }
        }else if(click.equals(resetmove)){
            boardpanel.removeAll();
            if (difficulty == 0) {
                for (int column = 0; column < x; column++) {
                    for (int row = 0; row < y; row++) {
                        grid[column][row] = new BoardGrid(x, y, column, row);
                        grid[column][row].backgroundSet(visualoption, targetlist);
                        if (visualoption == 0) { // "Text" option displays robots in word form
                            if (column == originalposition.get(0).returnrobotx() && row == originalposition.get(0).returnroboty()) {
                                grid[column][row].setrobot(22);
                            } else if (column == originalposition.get(1).returnrobotx()
                                    && row == originalposition.get(1).returnroboty()) {
                                grid[column][row].setrobot(23);
                            } else if (column == originalposition.get(2).returnrobotx()
                                    && row == originalposition.get(2).returnroboty()) {
                                grid[column][row].setrobot(24);
                            } else if (column == originalposition.get(3).returnrobotx()
                                    && row == originalposition.get(3).returnroboty()) {
                                grid[column][row].setrobot(25);
                            }
                        } else if (visualoption == 1) { // "Color" option displays robots in colored graphics form
                            if (column == originalposition.get(0).returnrobotx() && row == originalposition.get(0).returnroboty()) {
                                grid[column][row].setrobot(18);
                            } else if (column == originalposition.get(1).returnrobotx()
                                    && row == originalposition.get(1).returnroboty()) {
                                grid[column][row].setrobot(19);
                            } else if (column == originalposition.get(2).returnrobotx()
                                    && row == originalposition.get(2).returnroboty()) {
                                grid[column][row].setrobot(20);
                            } else if (column == originalposition.get(3).returnrobotx()
                                    && row == originalposition.get(3).returnroboty()) {
                                grid[column][row].setrobot(21);
                            }
                        } else { // "Text and Color" option displays robots in both colored and word form
                            if (column == originalposition.get(0).returnrobotx() && row == originalposition.get(0).returnroboty()) {
                                grid[column][row].setrobot(43);
                            } else if (column == originalposition.get(1).returnrobotx()
                                    && row == robotpost.get(1).returnroboty()) {
                                grid[column][row].setrobot(44);
                            } else if (column == originalposition.get(2).returnrobotx()
                                    && row == originalposition.get(2).returnroboty()) {
                                grid[column][row].setrobot(45);
                            } else if (column == originalposition.get(3).returnrobotx()
                                    && row == originalposition.get(3).returnroboty()) {
                                grid[column][row].setrobot(46);
                            }
                        }
                        
                        grid[column][row].addMouseListener(this);
    
                        boardpanel.add(grid[column][row]);
                        boardpanel.validate();
                        boardpanel.repaint();
                    }
                }
            } else { // Goes through this statement if "Complex Board" is chosen
                for (int column = 0; column < x; column++) {
                    for (int row = 0; row < y; row++) {
                        grid[column][row] = new BoardGrid(x, y, column, row);
                        grid[column][row].backgroundSetComplex(visualoption, targetlist);
                        if (visualoption == 0) { // "Text" option displays robots in word form
                            if (column == originalposition.get(0).returnrobotx() && row == originalposition.get(0).returnroboty()) {
                                grid[column][row].setrobot(22);
                            } else if (column == originalposition.get(1).returnrobotx()
                                    && row == originalposition.get(1).returnroboty()) {
                                grid[column][row].setrobot(23);
                            } else if (column == originalposition.get(2).returnrobotx()
                                    && row == originalposition.get(2).returnroboty()) {
                                grid[column][row].setrobot(24);
                            } else if (column == originalposition.get(3).returnrobotx()
                                    && row == originalposition.get(3).returnroboty()) {
                                grid[column][row].setrobot(25);
                            }
                        } else if (visualoption == 1) { // "Color" option displays robots in colored graphics form
                            if (column == originalposition.get(0).returnrobotx() && row == originalposition.get(0).returnroboty()) {
                                grid[column][row].setrobot(18);
                            } else if (column == originalposition.get(1).returnrobotx()
                                    && row == originalposition.get(1).returnroboty()) {
                                grid[column][row].setrobot(19);
                            } else if (column == originalposition.get(2).returnrobotx()
                                    && row == originalposition.get(2).returnroboty()) {
                                grid[column][row].setrobot(20);
                            } else if (column == originalposition.get(3).returnrobotx()
                                    && row == originalposition.get(3).returnroboty()) {
                                grid[column][row].setrobot(21);
                            }
                        } else { // "Text and Color" option displays robots in both colored and word form
                            if (column == originalposition.get(0).returnrobotx() && row == originalposition.get(0).returnroboty()) {
                                grid[column][row].setrobot(43);
                            } else if (column == originalposition.get(1).returnrobotx()
                                    && row == originalposition.get(1).returnroboty()) {
                                grid[column][row].setrobot(44);
                            } else if (column == originalposition.get(2).returnrobotx()
                                    && row == originalposition.get(2).returnroboty()) {
                                grid[column][row].setrobot(45);
                            } else if (column == originalposition.get(3).returnrobotx()
                                    && row == originalposition.get(3).returnroboty()) {
                                grid[column][row].setrobot(46);
                            }
                        }
    
                        grid[column][row].addMouseListener(this);
                        boardpanel.add(grid[column][row]);
                        boardpanel.validate();
                        boardpanel.repaint();
                    }
                }
            }
    
        }else if (click.equals(savegame)) {
            newtimer.stop();
            File file = new File("targetchipandtext.txt");
            try { // Saves the target chip data
                FileOutputStream fos = new FileOutputStream(file);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

                StringBuilder stringbuild = new StringBuilder();
                stringbuild.append(targetchip + "," + targettext);

                bw.write(stringbuild.toString());
                bw.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            StringBuilder sb = new StringBuilder();
            for(int i = 0; i<targetlist.size(); i++){
                sb.append(targetlist.get(i));
            }
            String savestring = sb.toString();
            File file1 = new File("targetlist.txt");
            try{ // Saves the icon in form of list
                FileOutputStream fos1 = new FileOutputStream(file1);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos1));

                bw.write(savestring);
                bw.close();
            }
            catch (Exception e1){
                e1.printStackTrace();
            }

            File file2 = new File("robotposition.txt");
            try{ // Saves the current position for all four robot
                FileOutputStream fos2 = new FileOutputStream(file2);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos2));

                for(int i = 0; i<robotpost.size(); i++){
                    StringBuilder sb1 = new StringBuilder();
                    sb1.append(robotpost.get(i).returnrobotx() + "," + robotpost.get(i).returnroboty());
                    bw.write(sb1.toString());
                    bw.newLine();
                }
                bw.close();
            } catch(Exception e1){
                e1.printStackTrace();
            }

            File file3 = new File("scoreboard.txt");
            try{ // Saves the current state of the scoreboard
                FileOutputStream fos3 = new FileOutputStream(file3);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos3));

                for(int i = 1; i < 5; i++){
                    StringBuilder sb1 = new StringBuilder();
                    sb1.append(scoreboardtable.getValueAt(i, 0).toString() + "," + scoreboardtable.getValueAt(i,1).toString());
                    bw.write(sb1.toString());
                    bw.newLine();
                }
                bw.close();
            }catch(Exception e1){
                e1.printStackTrace();
            }

            File file4 = new File("timer.txt");
            try{ // Saves the remaining time when the game is saved 
                FileOutputStream fos4 = new FileOutputStream(file4);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos4));

                String remainingtime = time.getText().substring(0, 2);
                if(remainingtime.equals("00")){
                    remainingtime = time.getText().substring(3,5);
                }
                else if(remainingtime.equals("01")){
                    remainingtime = "60";
                }
                bw.write(remainingtime);
                bw.close();
            }catch(Exception e1){
                e1.printStackTrace();
            }

            File file5 = new File("currentplayer.txt");
            try{ // Saves the current player data 
                FileOutputStream fos5 = new FileOutputStream(file5);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos5));

                bw.write(whos_turn.getText());
                bw.close();
            }catch(Exception e1){
                e1.printStackTrace();
            }

            File file6 = new File("playerbids.txt");
            try{ // Saves the playertime HashMap, recording the time used for each player before saving 
                FileOutputStream fos6 = new FileOutputStream(file6);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos6));

                for(Map.Entry<Integer, Integer> entry: player_time.entrySet()){
                    StringBuilder sb1 = new StringBuilder();
                    sb1.append(entry.getKey() + "," + entry.getValue());
                    bw.write(sb1.toString());
                    bw.newLine();
                }
                bw.close();
            }catch(Exception e1){
                e1.printStackTrace();
            }

            File file7 = new File("selections.txt");
            try{ // Saves the selected visual option and difficulty 
                FileOutputStream fos7 = new FileOutputStream(file7);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos7));

                StringBuilder sb1 = new StringBuilder();
                sb1.append(visualoption + "," + difficulty);
                bw.write(sb1.toString());
                bw.close();
            }catch(Exception e1){
                e1.printStackTrace();
            }

            File file8 = new File("bidtext.txt");
            try{
                FileOutputStream fos8 = new FileOutputStream(file8);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos8));

                bw.write(lowestbid.getText());
                bw.close();
            }catch (Exception e1){
                e1.printStackTrace();
            }

            File file9 = new File("Iconlist.txt");
            try{
                FileOutputStream fos9 = new FileOutputStream(file9);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos9));

                StringBuilder stringb = new StringBuilder();
                for(int i = 0; i < iconlist.size(); i++){
                    stringb.append(iconlist.get(i));
                }
                bw.write(stringb.toString());
                bw.close();
            }catch (Exception e1){
                e1.printStackTrace();
            }

            File file10 = new File("originalposition.txt");
            try{
                FileOutputStream fos10 = new FileOutputStream(file10);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos10));

                StringBuilder sbuilder = new StringBuilder();
                for(int i = 0; i < originalposition.size(); i++){
                    sbuilder.append(originalposition.get(i).returnrobotx() + "," + originalposition.get(i).returnroboty());
                }
                bw.write(sbuilder.toString());
                bw.close();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            
            // Displays a message prompting users to continue or exit the game
            int cont = JOptionPane.showConfirmDialog(this.getContentPane(), "Saved, continue game?", "Game Saved", JOptionPane.YES_NO_OPTION);
            if(cont == JOptionPane.NO_OPTION){
                JOptionPane.showMessageDialog(this.getContentPane(), "Goodbye");
                System.exit(0);
            }
            else{
                newtimer.start();
            }
        }
        else if(click.equals(exitgame)){ // Gives user the option to save the game before exiting
            newtimer.stop();
            int save = JOptionPane.showConfirmDialog(this.getContentPane(), "Do you want to save the game before exiting?", "Exit Game", JOptionPane.YES_NO_OPTION);
            if(save == JOptionPane.YES_OPTION){
                newtimer.stop();
                File file = new File("targetchipandtext.txt");
                try { // Saves the target chip data
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

                    StringBuilder stringbuild = new StringBuilder();
                    stringbuild.append(targetchip + "," + targettext);

                    bw.write(stringbuild.toString());
                    bw.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                StringBuilder sb = new StringBuilder();
                for(int i = 0; i<targetlist.size(); i++){
                    sb.append(targetlist.get(i));
                }
                String savestring = sb.toString();
                File file1 = new File("targetlist.txt");
                try{ // Saves the icon in form of list
                    FileOutputStream fos1 = new FileOutputStream(file1);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos1));

                    bw.write(savestring);
                    bw.close();
                }
                catch (Exception e1){
                    e1.printStackTrace();
                }

                File file2 = new File("robotposition.txt");
                try{ // Saves the current position for all four robot
                    FileOutputStream fos2 = new FileOutputStream(file2);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos2));

                    for(int i = 0; i<robotpost.size(); i++){
                        StringBuilder sb1 = new StringBuilder();
                        sb1.append(robotpost.get(i).returnrobotx() + "," + robotpost.get(i).returnroboty());
                        bw.write(sb1.toString());
                        bw.newLine();
                    }
                    bw.close();
                } catch(Exception e1){
                    e1.printStackTrace();
                }

                File file3 = new File("scoreboard.txt");
                try{ // Saves the current state of the scoreboard
                    FileOutputStream fos3 = new FileOutputStream(file3);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos3));

                    for(int i = 1; i < 5; i++){
                        StringBuilder sb1 = new StringBuilder();
                        sb1.append(scoreboardtable.getValueAt(i, 0).toString() + "," + scoreboardtable.getValueAt(i,1).toString());
                        bw.write(sb1.toString());
                        bw.newLine();
                    }
                    bw.close();
                }catch(Exception e1){
                    e1.printStackTrace();
                }

                File file4 = new File("timer.txt");
                try{ // Saves the remaining time when the game is saved 
                    FileOutputStream fos4 = new FileOutputStream(file4);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos4));

                    String remainingtime = time.getText().substring(0, 2);
                    if(remainingtime.equals("00")){
                        remainingtime = time.getText().substring(3,5);
                    }
                    else if(remainingtime.equals("01")){
                        remainingtime = "60";
                    }
                    bw.write(remainingtime);
                    bw.close();
                }catch(Exception e1){
                    e1.printStackTrace();
                }

                File file5 = new File("currentplayer.txt");
                try{ // Saves the current player data 
                    FileOutputStream fos5 = new FileOutputStream(file5);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos5));

                    bw.write(whos_turn.getText());
                    bw.close();
                }catch(Exception e1){
                    e1.printStackTrace();
                }

                File file6 = new File("playerbids.txt");
                try{ // Saves the playertime HashMap, recording the time used for each player before saving 
                    FileOutputStream fos6 = new FileOutputStream(file6);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos6));

                    for(Map.Entry<Integer, Integer> entry: player_time.entrySet()){
                        StringBuilder sb1 = new StringBuilder();
                        sb1.append(entry.getKey() + "," + entry.getValue());
                        bw.write(sb1.toString());
                        bw.newLine();
                    }
                    bw.close();
                }catch(Exception e1){
                    e1.printStackTrace();
                }

                File file7 = new File("selections.txt");
                try{ // Saves the selected visual option and difficulty 
                    FileOutputStream fos7 = new FileOutputStream(file7);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos7));

                    StringBuilder sb1 = new StringBuilder();
                    sb1.append(visualoption + "," + difficulty);
                    bw.write(sb1.toString());
                    bw.close();
                }catch(Exception e1){
                    e1.printStackTrace();
                }

                File file8 = new File("bidtext.txt");
                try{
                    FileOutputStream fos8 = new FileOutputStream(file8);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos8));

                    bw.write(lowestbid.getText());
                    bw.close();
                }catch (Exception e1){
                    e1.printStackTrace();
                }

                File file9 = new File("Iconlist.txt");
                try{
                    FileOutputStream fos9 = new FileOutputStream(file9);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos9));

                    StringBuilder stringb = new StringBuilder();
                    for(int i = 0; i < iconlist.size(); i++){
                        stringb.append(iconlist.get(i));
                    }
                    bw.write(stringb.toString());
                    bw.close();
                }catch (Exception e1){
                    e1.printStackTrace();
                }

                File file10 = new File("originalposition.txt");
                try{
                    FileOutputStream fos10 = new FileOutputStream(file10);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos10));

                    StringBuilder sbuilder = new StringBuilder();
                    for(int i = 0; i < originalposition.size(); i++){
                        sbuilder.append(originalposition.get(i).returnrobotx() + "," + originalposition.get(i).returnroboty());
                    }
                    bw.write(sbuilder.toString());
                    bw.close();
                }catch(Exception e1){
                    e1.printStackTrace();
                }
            }
            else if(save == JOptionPane.NO_OPTION){
                JOptionPane.showMessageDialog(this.getContentPane(), "Goodbye");
                System.exit(0);
            }
        }
        else if(click.equals(resumeclock)){
            newtimer.start();
            resumeclock.setEnabled(false);
            bids.setEnabled(true);
        }
    }

    public void mousePressed(MouseEvent me){
        Object click = me.getSource();
        Container cont = this.getContentPane();

        if(click instanceof BoardGrid){
            int clickx = ((BoardGrid) click).getx();
            int clicky = ((BoardGrid) click).gety();

            for(Component c: grid[clickx][clicky].getComponents()){
                if(c instanceof JLabel){
                    JLabel label = (JLabel)c;
                    String v = label.getText();
                    System.out.println(v);
                }
            }

            System.out.println(grid[clickx][clicky].getGraphics().toString());
            System.out.println(visual_option.getText());
        }


        if(click instanceof RobotPosition){
            int xco = ((RobotPosition) click).returnrobotx();
            int yco = ((RobotPosition) click).returnroboty();

            System.out.println(xco);
            System.out.println(yco);

            for(int i = 0; i < robotpost.size(); i++){ // Cross reference the selected robot piece with the coordinate of the robot piece on the board 
                switch(visualoption){
                    case 0:
                        if(robotpost.get(i).returnrobotx() == xco && robotpost.get(i).returnroboty() == yco){
                            currentrobot = i;
                            if(i == 0){  // If matched, assigns the "paint" with the graphics value
                                paint = 22;
                            }
                            else if(i == 1){
                                paint = 23;
                            }
                            else if(i == 2){
                                paint = 24;
                            }
                            else if(i == 3){
                                paint = 25;
                            }
                        }
                        break;
                    case 1:
                        if(robotpost.get(i).returnrobotx() == xco && robotpost.get(i).returnroboty() == yco){
                            currentrobot = i;
                            if(i == 0){  // If matched, assigns the "paint" with the graphics value
                                paint = 18;
                            }
                            else if(i == 1){
                                paint = 19;
                            }
                            else if(i == 2){
                                paint = 20;
                            }
                            else if(i == 3){
                                paint = 21;
                            }
                        }
                        break;
                    case 2:
                        if(robotpost.get(i).returnrobotx() == xco && robotpost.get(i).returnroboty() == yco){
                            currentrobot = i;
                            if(i == 0){
                                paint = 43;
                            }
                            else if(i == 1){
                                paint = 44;
                            }
                            else if(i ==2){
                                paint = 45;
                            }
                            else if(i == 3){
                                paint = 46;
                            }
                        }
                        break;
                    default: break;
                }
            }


            ((RobotPosition) click).setBackground(Color.lightGray);


            // In each nested ActionListener, the JLabels "UR","UL","DR","DL","Mid","Right","Down" are used to represent the walls of the board
            // Whilst "R" is used to represent the robot pieces
            // "Yes" represents the board tiles in which it's okay to move across

            resetrobotselection.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    ((RobotPosition) click).setBackground(Color.WHITE);
                }
            });

            up.addActionListener(new ActionListener(){ // Nested ActionListener for "up" button
                public void actionPerformed(ActionEvent e){
                    String jc;
                    outerloop:
                    for(int column = xco; column >= 0 ; column--){
                        for(Component c : grid[column-1][yco].getComponents()){
                            if(c instanceof JLabel){
                                JLabel label = (JLabel)c;
                                jc = label.getText();
                                if(jc.equals("UR") || jc.equals("UL")){ 
                                    grid[column-1][yco].setrobot(paint);
                                    if(visualoption == 0){
                                        System.out.println("Visualoption 0");
                                        for(Component d: grid[column-1][yco].getComponents()){
                                            if(d instanceof JLabel){
                                                JLabel j = (JLabel)d;
                                                String v = j.getText();
                                                if(v.equals(visual_option.getText())){
                                                    System.out.println("Got it");
                                                    if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                        if(((RobotPosition) click).rcount == 4){
                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                            textswitch();
                                                            reached = true;
                                                        }
                                                    }
                                                    else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                        if(((RobotPosition) click).rcount == 5){
                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                            textswitch();
                                                            reached = true;
                                                        }
                                                    }
                                                    else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                        if(((RobotPosition) click).rcount == 6){
                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                            textswitch();
                                                            reached = true;
                                                        }
                                                    }
                                                    else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                        if(((RobotPosition) click).rcount == 7){
                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                            textswitch();
                                                            reached = true;
                                                        }
                                                    }
                                                    else if(v.equals("Q")){
                                                        textswitch();
                                                        reached = true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else if(visualoption == 1){
                                        for(Component d: grid[column-1][yco].getComponents()){
                                            if(c instanceof JLabel){
                                                JLabel j = (JLabel)d;
                                                String v = j.getText();
                                                for(Component comp: chip.getComponents()){
                                                    if(d instanceof JLabel){
                                                        JLabel k = (JLabel)comp;
                                                        String i = k.getText();
                                                        System.out.println(i);
                                                        if(v.equals(i)){
                                                            if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                if(((RobotPosition) click).rcount == 0){
                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                    switchplayer(0, column-1, yco);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                if(((RobotPosition) click).rcount == 1){
                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                    switchplayer(1, column-1, yco);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                if(((RobotPosition) click).rcount == 2){
                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                    switchplayer(2, column-1, yco);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                if(((RobotPosition) click).rcount == 3){
                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                    switchplayer(3, column-1, yco);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("Q")){
                                                                switchplayer(((RobotPosition) click).rcount, column-1, yco);
                                                                reached = true;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else{
                                        for(Component d: grid[column-1][yco].getComponents()){
                                            if(c instanceof JLabel){
                                                JLabel j = (JLabel)d;
                                                String v = j.getText();
                                                for(Component comp: chip.getComponents()){
                                                    if(d instanceof JLabel){
                                                        JLabel k = (JLabel)comp;
                                                        String i = k.getText();
                                                        System.out.println(i);
                                                        if(v.equals(i)){
                                                            if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                if(((RobotPosition) click).rcount == 8){
                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                    switchplayer(8, column-1, yco);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                if(((RobotPosition) click).rcount == 9){
                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                    switchplayer(9, column-1, yco);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                if(((RobotPosition) click).rcount == 10){
                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                    switchplayer(10, column-1, yco);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                if(((RobotPosition) click).rcount == 11){
                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                    switchplayer(11, column-1, yco);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("Q")){
                                                                switchplayer(((RobotPosition) click).rcount, column-1, yco);
                                                                reached = true;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    grid[xco][yco].setrobot(0);
                                    for(int ci = column-1; ci <= xco; ci++){
                                        grid[ci][yco].setBackground(new Color(255,255,153));
                                    }
                                    ((RobotPosition) click).x = column-1;
                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                    switch(currentrobot){ // Changes the coordinate values of the selected robot
                                        case 0:
                                            robotpost.get(0).x = column-1;
                                            break;
                                        case 1:
                                            robotpost.get(1).x = column-1;
                                            break;
                                        case 2:
                                            robotpost.get(2).x = column-1;
                                            break;
                                        case 3:
                                            robotpost.get(3).x = column-1;
                                            break;
                                        default: break;
                                    }
                                    if(reached){
                                        savedtime = 60;
                                        for (int col = 0; col < x; col++) {
                                            for (int row = 0; row < y; row++) {
                                                if (!grid[col][row].getBackground().equals(Color.WHITE)) {
                                                    grid[col][row].setBackground(Color.WHITE);
                                                }
                                            }
                                        }
                                        reached = false;
                                    }
                                    grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                    
                                    break outerloop;
                                }
                                else if(jc.equals("BSlash")){
                                    for(int col = yco; col < y; col++){
                                        for(Component f : grid[column-1][col+1].getComponents()){
                                            if(f instanceof JLabel){
                                                JLabel label1 = (JLabel)f;
                                                jc = label1.getText();
                                                if(jc.equals("R") || jc.equals("UL") || jc.equals("DL") || jc.equals("Mid") || jc.equals("DRR") || jc.equals("URR") || jc.equals("RRight") || jc.equals("FSlash")){
                                                    grid[column-1][col].setrobot(paint);
                                                    grid[xco][yco].setrobot(0);
                                                    for(int ci = col; ci >= yco; ci--){
                                                        grid[column-1][ci].setBackground(new Color(51,204,255));
                                                    }
                                                    ((RobotPosition) click).x = column-1;
                                                    ((RobotPosition) click).y = col;
                                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                                    switch(currentrobot){
                                                        case 0:
                                                            robotpost.get(0).x = column-1;
                                                            robotpost.get(0).y = col;
                                                            break;
                                                        case 1:
                                                            robotpost.get(1).x = column-1;
                                                            robotpost.get(1).y = col;
                                                            break;
                                                        case 2:
                                                            robotpost.get(2).x = column-1;
                                                            robotpost.get(2).y = col;
                                                            break;
                                                        case 3:
                                                            robotpost.get(3).x = column-1;
                                                            robotpost.get(3).y = col;
                                                            break;
                                                        default: break;
                                                    };
                                                    grid[16][16].setBackground(Color.white);
                                                    break outerloop;
                                                }
                                                else if(jc.equals("UR") || jc.equals("DR") || jc.equals("Right") || jc.equals("RDown") ){
                                                    grid[column-1][col+1].setrobot(paint);
                                                    if(visualoption == 0){
                                                        System.out.println("Visualoption 0");
                                                        for(Component d: grid[column-1][col+1].getComponents()){
                                                            if(d instanceof JLabel){
                                                                JLabel j = (JLabel)d;
                                                                String v = j.getText();
                                                                System.out.println(v);
                                                                if(v.equals(visual_option.getText())){
                                                                    System.out.println("G))");
                                                                    if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                        if(((RobotPosition) click).rcount == 4){
                                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                            textswitch();
                                                                            reached = true;
                                                                        }
                                                                    }
                                                                    else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                        if(((RobotPosition) click).rcount == 5){
                                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                            textswitch();
                                                                            reached = true;
                                                                        }
                                                                    }
                                                                    else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                        System.out.println("Hello from rcount 6");
                                                                        if(((RobotPosition) click).rcount == 6){
                                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                            textswitch();
                                                                            reached = true;
                                                                        }
                                                                    }
                                                                    else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                        if(((RobotPosition) click).rcount == 7){
                                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                            textswitch();
                                                                            reached = true;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    else if(visualoption == 1){
                                                        for(Component d: grid[column-1][col+1].getComponents()){
                                                            if(d instanceof JLabel){
                                                                JLabel j = (JLabel)d;
                                                                String v = j.getText();
                                                                for(Component comp: chip.getComponents()){
                                                                    if(d instanceof JLabel){
                                                                        JLabel k = (JLabel)comp;
                                                                        String i = k.getText();
                                                                        if(v.equals(i)){
                                                                            System.out.println(v);
                                                                            if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                                if(((RobotPosition) click).rcount == 0){
                                                                                    // grid[16][16].setBackground(Color.white);
                                                                                    switchplayer(0, column-1, col+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                                if(((RobotPosition) click).rcount == 1){
                                                                                    // grid[16][16].setBackground(Color.white);
                                                                                    switchplayer(1, column-1, col+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                                if(((RobotPosition) click).rcount == 2){
                                                                                    // grid[16][16].setBackground(Color.white);
                                                                                    switchplayer(2, column-1, col+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                                if(((RobotPosition) click).rcount == 3){
                                                                                    // grid[16][16].setBackground(Color.white);
                                                                                    switchplayer(3, column-1, col+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("Q")){
                                                                                switchplayer(((RobotPosition) click).rcount, column-1, col+1);
                                                                                break;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    else{
                                                        for(Component d: grid[column-1][col+1].getComponents()){
                                                            if(d instanceof JLabel){
                                                                JLabel j = (JLabel)d;
                                                                String v = j.getText();
                                                                for(Component comp: chip.getComponents()){
                                                                    if(d instanceof JLabel){
                                                                        JLabel k = (JLabel)comp;
                                                                        String i = k.getText();
                                                                        System.out.println(i);
                                                                        if(v.equals(i)){
                                                                            if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                                if(((RobotPosition) click).rcount == 8){
                                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                                    switchplayer(8, column-1, col+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                                if(((RobotPosition) click).rcount == 9){
                                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                                    switchplayer(9, column-1, col+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                                if(((RobotPosition) click).rcount == 10){
                                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                                    switchplayer(10, column-1, col+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                                if(((RobotPosition) click).rcount == 11){
                                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                                    switchplayer(11, column-1, col+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("Q")){
                                                                                switchplayer(((RobotPosition) click).rcount, column-1, col+1);
                                                                                reached = true;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    grid[xco][yco].setrobot(0);
                                                    for(int ci = col+1; ci >= yco; ci--){
                                                        grid[column-1][ci].setBackground(new Color(51,204,255));
                                                    }
                                                    switch(currentrobot){
                                                        case 0:
                                                            robotpost.get(0).x = column-1;
                                                            robotpost.get(0).y = col+1;
                                                            break;
                                                        case 1:
                                                            robotpost.get(1).x = column-1;
                                                            robotpost.get(1).y = col+1;
                                                            break;
                                                        case 2:
                                                            robotpost.get(2).x = column-1;
                                                            robotpost.get(2).y = col+1;
                                                            break;
                                                        case 3:
                                                            robotpost.get(3).x = column-1;
                                                            robotpost.get(3).y = col+1;
                                                            break;
                                                        default: break;
                                                    };
                                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                                    ((RobotPosition) click).x = column-1;
                                                    ((RobotPosition) click).y = col+1;
                                                    if(reached){
                                                        savedtime = 60;
                                                        for (int coll = 0; coll < x; coll++) {
                                                            for (int row = 0; row < y; row++) {
                                                                if (!grid[coll][row].getBackground().equals(Color.WHITE)) {
                                                                    grid[coll][row].setBackground(Color.WHITE);
                                                                }
                                                            }
                                                        }
                                                        reached = false;
                                                    }   
                                                    grid[16][16].setBackground(Color.white);
                                                    break outerloop;
                                                }
                                                else if(jc.equals("Yes")){
                                                    grid[column-1][col].setBackground(new Color(51, 204, 255));
                                                    grid[xco][yco].setrobot(0);
                                                    grid[xco][yco].setBackground(new Color(51,204,255));
                
                                                    if(col == y-2){
                                                        grid[column-1][y-1].setBackground(new Color(51,204,255));
                                                        grid[column-1][y-1].setrobot(paint);
                                                    }
                                                    switch(currentrobot){
                                                        case 0:
                                                            robotpost.get(0).x = column-1;
                                                            robotpost.get(0).y = y-1;
                                                            break;
                                                        case 1:
                                                            robotpost.get(1).x = column-1;
                                                            robotpost.get(1).y = y-1;
                                                            break;
                                                        case 2:
                                                            robotpost.get(2).x = column-1;
                                                            robotpost.get(2).y = y-1;
                                                            break;
                                                        case 3:
                                                            robotpost.get(3).x = column-1;
                                                            robotpost.get(3).y = y-1;
                                                            break;
                                                        default: break;
                                                    }
                                                    ((RobotPosition) click).x = column-1;
                                                    ((RobotPosition) click).y = y-1;
                                                    ((RobotPosition) click).setBackground(Color.WHITE);
                
                                                }
                                            }
                                        }
                                    } 
                                }
                                else if(jc.equals("FSlash")){
                                    for(int newcolumn = yco; newcolumn >= 0; newcolumn--){
                                        for(Component f: grid[column-1][newcolumn-1].getComponents()){
                                            if(f instanceof JLabel){
                                                JLabel jla = (JLabel)f;
                                                String l = jla.getText();
                                                if(l.equals("Yes")){
                                                    grid[column-1][newcolumn].setBackground(Color.yellow);
                                                    grid[xco][yco].setrobot(0);
                                                    grid[xco][yco].setBackground(Color.yellow);

                                                    if(newcolumn == 1){
                                                        grid[column-1][0].setrobot(paint);
                                                        grid[column-1][newcolumn].setBackground(Color.yellow);
                                                    }

                                                    switch(currentrobot){
                                                        case 0:
                                                            robotpost.get(0).x = column-1;
                                                            robotpost.get(0).y = 0;
                                                            break;
                                                        case 1:
                                                            robotpost.get(1).x = column-1;
                                                            robotpost.get(1).y = 0;
                                                            break;
                                                        case 2:
                                                            robotpost.get(2).x = column-1;
                                                            robotpost.get(2).y = 0;
                                                            break;
                                                        case 3:
                                                            robotpost.get(3).x = column-1;
                                                            robotpost.get(3).y = 0;
                                                            break;
                                                    }

                                                    ((RobotPosition) click).x = column-1;
                                                    ((RobotPosition) click).y = 0;
                                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                                }
                                            }
                                        }
                                    }
                                }
                                else if(jc.equals("R") || jc.equals("DR") || jc.equals("DL") || jc.equals("Down") || jc.equals("Mid") || jc.equals("URR") || jc.equals("ULR") || jc.equals("DRR") || jc.equals("DRL")){
                                    grid[column][yco].setrobot(paint);
                                    grid[xco][yco].setrobot(0);
                                    for(int ci = column; ci <= xco; ci++){
                                        grid[ci][yco].setBackground(new Color(255,255,153));
                                    }
                                    ((RobotPosition) click).x = column;
                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                    switch(currentrobot){
                                        case 0:
                                            robotpost.get(0).x = column;
                                            break;
                                        case 1:
                                            robotpost.get(1).x = column;
                                            break;
                                        case 2:
                                            robotpost.get(2).x = column;
                                            break;
                                        case 3:
                                            robotpost.get(3).x = column;
                                            break;
                                        default: break;
                                    }
                                    grid[16][16].setBackground(Color.white);
                                    break outerloop;
                                }
                                else if(jc.equals("Yes")){
                                    grid[column][yco].setBackground(new Color(255, 255, 153));
                                    grid[xco][yco].setrobot(0);
                                    grid[xco][yco].setBackground(new Color(255,255,153));

                                    if(column == 1){
                                        grid[0][yco].setBackground(new Color(255,255,153));
                                        grid[0][yco].setrobot(paint);
                                        
                                    }
                                    switch(currentrobot){
                                        case 0:
                                            robotpost.get(0).x = 0;
                                            break;
                                        case 1:
                                            robotpost.get(1).x = 0;
                                            break;
                                        case 2:
                                            robotpost.get(2).x = 0;
                                            break;
                                        case 3:
                                            robotpost.get(3).x = 0;
                                            break;
                                        default: break;
                                    };
                                    ((RobotPosition) click).x = 0;
                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                }
                            }
                        }
                        
                    }
                }
            });

            down.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae){
                    String jc;
                    outerloop:
                    for(int column = xco; column <= x ; column++){
                        for(Component c : grid[column+1][yco].getComponents()){
                            if(c instanceof JLabel){
                                JLabel label = (JLabel)c;
                                jc = label.getText();
                                if(jc.equals("R") || jc.equals("UR") || jc.equals("UL") ||  jc.equals("Mid") || jc.equals("DRR") || jc.equals("DLR") || jc.equals("RDown")){
                                    grid[column][yco].setrobot(paint);
                                    grid[xco][yco].setrobot(0);
                                    for(int ci = column; ci >= xco; ci--){
                                        grid[ci][yco].setBackground(new Color(255,255,153));
                                    }
                                    ((RobotPosition) click).x = column;
                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                    switch(currentrobot){
                                        case 0:
                                            robotpost.get(0).x = column;
                                            break;
                                        case 1:
                                            robotpost.get(1).x = column;
                                            break;
                                        case 2:
                                            robotpost.get(2).x = column;
                                            break;
                                        case 3:
                                            robotpost.get(3).x = column;
                                            break;
                                        default: break;
                                    };
                                    grid[16][16].setBackground(Color.white);
                                    break outerloop;
                                }
                                else if(jc.equals("FSlash")){
                                    for(int col = yco; col < y; col++){
                                        for(Component e : grid[column+1][col+1].getComponents()){
                                            if(e instanceof JLabel){
                                                JLabel label1 = (JLabel)e;
                                                jc = label1.getText();
                                                if(jc.equals("R") || jc.equals("UL") || jc.equals("DL") || jc.equals("Mid") || jc.equals("DRR") || jc.equals("URR") || jc.equals("RRight")){
                                                    grid[column+1][col].setrobot(paint);
                                                    grid[xco][yco].setrobot(0);
                                                    for(int ci = col; ci >= column+1; ci--){
                                                        grid[column+1][ci].setBackground(new Color(51,204,255));
                                                    }
                                                    ((RobotPosition) click).x = column+1;
                                                    ((RobotPosition) click).y = col;
                                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                                    switch(currentrobot){
                                                        case 0:
                                                            robotpost.get(0).x = column+1;
                                                            robotpost.get(0).y = col;
                                                            break;
                                                        case 1:
                                                            robotpost.get(1).x = column+1;
                                                            robotpost.get(1).y = col;
                                                            break;
                                                        case 2:
                                                            robotpost.get(2).x = column+1;
                                                            robotpost.get(2).y = col;
                                                            break;
                                                        case 3:
                                                            robotpost.get(3).x = column+1;
                                                            robotpost.get(3).y = col;
                                                            break;
                                                        default: break;
                                                    };
                                                    grid[16][16].setBackground(Color.white);
                                                    break outerloop;
                                                }
                                                else if(jc.equals("UR") || jc.equals("DR") || jc.equals("Right") || jc.equals("RDown") ){
                                                    grid[column+1][col+1].setrobot(paint);
                                                    if(visualoption == 0){
                                                        System.out.println("Visualoption 0");
                                                        for(Component d: grid[column+1][col+1].getComponents()){
                                                            if(d instanceof JLabel){
                                                                JLabel j = (JLabel)d;
                                                                String v = j.getText();
                                                                System.out.println(v);
                                                                if(v.equals(visual_option.getText())){
                                                                    System.out.println("G))");
                                                                    if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                        if(((RobotPosition) click).rcount == 4){
                                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                            textswitch();
                                                                            reached = true;
                                                                        }
                                                                    }
                                                                    else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                        if(((RobotPosition) click).rcount == 5){
                                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                            textswitch();
                                                                            reached = true;
                                                                        }
                                                                    }
                                                                    else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                        System.out.println("Hello from rcount 6");
                                                                        if(((RobotPosition) click).rcount == 6){
                                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                            textswitch();
                                                                            reached = true;
                                                                        }
                                                                    }
                                                                    else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                        if(((RobotPosition) click).rcount == 7){
                                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                            textswitch();
                                                                            reached = true;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    else if(visualoption == 1){
                                                        for(Component d: grid[column+1][col+1].getComponents()){
                                                            if(d instanceof JLabel){
                                                                JLabel j = (JLabel)d;
                                                                String v = j.getText();
                                                                for(Component comp: chip.getComponents()){
                                                                    if(d instanceof JLabel){
                                                                        JLabel k = (JLabel)comp;
                                                                        String i = k.getText();
                                                                        if(v.equals(i)){
                                                                            System.out.println(v);
                                                                            if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                                if(((RobotPosition) click).rcount == 0){
                                                                                    // grid[16][16].setBackground(Color.white);
                                                                                    switchplayer(0, column+1, col+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                                if(((RobotPosition) click).rcount == 1){
                                                                                    // grid[16][16].setBackground(Color.white);
                                                                                    switchplayer(1, column+1, col+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                                if(((RobotPosition) click).rcount == 2){
                                                                                    // grid[16][16].setBackground(Color.white);
                                                                                    switchplayer(2, column+1, col+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                                if(((RobotPosition) click).rcount == 3){
                                                                                    // grid[16][16].setBackground(Color.white);
                                                                                    switchplayer(3, column+1, col+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("Q")){
                                                                                switchplayer(((RobotPosition) click).rcount, column+1, col+1);
                                                                                break;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    else{
                                                        for(Component d: grid[column+1][col+1].getComponents()){
                                                            if(d instanceof JLabel){
                                                                JLabel j = (JLabel)d;
                                                                String v = j.getText();
                                                                for(Component comp: chip.getComponents()){
                                                                    if(d instanceof JLabel){
                                                                        JLabel k = (JLabel)comp;
                                                                        String i = k.getText();
                                                                        System.out.println(i);
                                                                        if(v.equals(i)){
                                                                            if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                                if(((RobotPosition) click).rcount == 8){
                                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                                    switchplayer(8, column+1, col+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                                if(((RobotPosition) click).rcount == 9){
                                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                                    switchplayer(9, column+1, col+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                                if(((RobotPosition) click).rcount == 10){
                                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                                    switchplayer(10, column+1, col+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                                if(((RobotPosition) click).rcount == 11){
                                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                                    switchplayer(11, column+1, col+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("Q")){
                                                                                switchplayer(((RobotPosition) click).rcount, column+1, col+1);
                                                                                reached = true;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    grid[xco][yco].setrobot(0);
                                                    for(int ci = col+1; ci >= yco; ci--){
                                                        grid[column+1][ci].setBackground(new Color(51,204,255));
                                                    }
                                                    switch(currentrobot){
                                                        case 0:
                                                            robotpost.get(0).x = column+1;
                                                            robotpost.get(0).y = col+1;
                                                            break;
                                                        case 1:
                                                            robotpost.get(1).x = column+1;
                                                            robotpost.get(1).y = col+1;
                                                            break;
                                                        case 2:
                                                            robotpost.get(2).x = column+1;
                                                            robotpost.get(2).y = col+1;
                                                            break;
                                                        case 3:
                                                            robotpost.get(3).x = column+1;
                                                            robotpost.get(3).y = col+1;
                                                            break;
                                                        default: break;
                                                    };
                                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                                    ((RobotPosition) click).x = column+1;
                                                    ((RobotPosition) click).y = col+1;
                                                    if(reached){
                                                        savedtime = 60;
                                                        for (int coll = 0; coll < x; coll++) {
                                                            for (int row = 0; row < y; row++) {
                                                                if (!grid[coll][row].getBackground().equals(Color.WHITE)) {
                                                                    grid[coll][row].setBackground(Color.WHITE);
                                                                }
                                                            }
                                                        }
                                                        reached = false;
                                                    }   
                                                    grid[16][16].setBackground(Color.white);
                                                    break outerloop;
                                                }
                                                else if(jc.equals("Yes")){
                                                    grid[column+1][col].setBackground(new Color(51, 204, 255));
                                                    grid[xco][yco].setrobot(0);
                                                    grid[xco][yco].setBackground(new Color(51,204,255));
                
                                                    if(col == y-2){
                                                        grid[column+1][y-1].setBackground(new Color(51,204,255));
                                                        grid[column+1][y-1].setrobot(paint);
                                                    }
                                                    switch(currentrobot){
                                                        case 0:
                                                            robotpost.get(0).x = column+1;
                                                            robotpost.get(0).y = y-1;
                                                            break;
                                                        case 1:
                                                            robotpost.get(1).x = column+1;
                                                            robotpost.get(1).y = y-1;
                                                            break;
                                                        case 2:
                                                            robotpost.get(2).x = column+1;
                                                            robotpost.get(2).y = y-1;
                                                            break;
                                                        case 3:
                                                            robotpost.get(3).x = column+1;
                                                            robotpost.get(3).y = y-1;
                                                            break;
                                                        default: break;
                                                    }
                                                    ((RobotPosition) click).x = column+1;
                                                    ((RobotPosition) click).y = y-1;
                                                    ((RobotPosition) click).setBackground(Color.WHITE);
                
                                                }
                                            }
                                        }
                                    } 
                                }
                                else if(jc.equals("BSlash")){
                                    for(int col = yco; col > 0; col--){
                                        for(Component e : grid[column+1][col-1].getComponents()){
                                            if(c instanceof JLabel){
                                                JLabel label1 = (JLabel)e;
                                                jc = label1.getText();
                                                if(jc.equals("UL") || jc.equals("DL")){
                                                    grid[column+1][col-1].setrobot(paint);
                                                    if(visualoption == 0){
                                                        for(Component d: grid[column+1][col-1].getComponents()){
                                                            if(d instanceof JLabel){
                                                                JLabel j = (JLabel)d;
                                                                String v = j.getText();
                                                                if(v.equals(visual_option.getText())){
                                                                    if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                        if(((RobotPosition) click).rcount == 4){
                                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                            textswitch();
                                                                            reached = true;
                                                                        }
                                                                    }
                                                                    else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                        if(((RobotPosition) click).rcount == 5){
                                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                            textswitch();
                                                                            reached = true;
                                                                        }
                                                                    }
                                                                    else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                        System.out.println("Hello from rcount 6");
                                                                        if(((RobotPosition) click).rcount == 6){
                                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                            textswitch();
                                                                            reached = true;
                                                                        }
                                                                    }
                                                                    else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                        if(((RobotPosition) click).rcount == 7){
                                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                            textswitch();
                                                                            reached = true;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    else if(visualoption == 1){
                                                        for(Component d: grid[column+1][col-1].getComponents()){
                                                            if(c instanceof JLabel){
                                                                JLabel j = (JLabel)d;
                                                                String v = j.getText();
                                                                for(Component comp: chip.getComponents()){
                                                                    if(d instanceof JLabel){
                                                                        JLabel k = (JLabel)comp;
                                                                        String i = k.getText();
                                                                        if(v.equals(i)){
                                                                            System.out.println(v);
                                                                            if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                                if(((RobotPosition) click).rcount == 0){
                                                                                    // grid[16][16].setBackground(Color.white);
                                                                                    switchplayer(0, column+1, col-1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                                if(((RobotPosition) click).rcount == 1){
                                                                                    // grid[16][16].setBackground(Color.white);
                                                                                    switchplayer(1, column+1, col-1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                                if(((RobotPosition) click).rcount == 2){
                                                                                    // grid[16][16].setBackground(Color.white);
                                                                                    switchplayer(2, column+1, col-1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                                if(((RobotPosition) click).rcount == 3){
                                                                                    switchplayer(3, column+1, col-1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("Q")){
                                                                                switchplayer(((RobotPosition) click).rcount, column+1, col-1);
                                                                                reached = true;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    else{
                                                        for(Component d: grid[column+1][col-1].getComponents()){
                                                            if(c instanceof JLabel){
                                                                JLabel j = (JLabel)d;
                                                                String v = j.getText();
                                                                for(Component comp: chip.getComponents()){
                                                                    if(d instanceof JLabel){
                                                                        JLabel k = (JLabel)comp;
                                                                        String i = k.getText();
                                                                        System.out.println(i);
                                                                        if(v.equals(i)){
                                                                            if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                                if(((RobotPosition) click).rcount == 8){
                                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                                    switchplayer(8, column+1, col-1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                                if(((RobotPosition) click).rcount == 9){
                                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                                    switchplayer(9, column+1, col-1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                                if(((RobotPosition) click).rcount == 10){
                                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                                    switchplayer(10, column+1, col-1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                                if(((RobotPosition) click).rcount == 11){
                                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                                    switchplayer(11, column+1, col-1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("Q")){
                                                                                switchplayer(((RobotPosition) click).rcount, column+1, col-1);
                                                                                reached = true;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    grid[xco][yco].setrobot(0);
                                                    for(int ci = col-1; ci <= yco; ci++){
                                                        grid[column+1][ci].setBackground(new Color(102,255,102));
                                                    }
                                                    switch(currentrobot){
                                                        case 0:
                                                            robotpost.get(0).x = column+1;
                                                            robotpost.get(0).y = col-1;
                                                            break;
                                                        case 1:
                                                            robotpost.get(1).x = column+1;
                                                            robotpost.get(1).y = col-1;
                                                            break;
                                                        case 2:
                                                            robotpost.get(2).x = column+1;
                                                            robotpost.get(2).y = col-1;
                                                            break;
                                                        case 3:
                                                            robotpost.get(3).x = column+1;
                                                            robotpost.get(3).y = col-1;
                                                            break;
                                                        default: break;
                                                    }
                                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                                    ((RobotPosition) click).x = column+1;
                                                    ((RobotPosition) click).y = col-1;
                                                    if(reached){
                                                        savedtime = 60;
                                                        for (int coll = 0; coll < x; coll++) {
                                                            for (int row = 0; row < y; row++) {
                                                                if (!grid[coll][row].getBackground().equals(Color.WHITE)) {
                                                                    grid[coll][row].setBackground(Color.WHITE);
                                                                }
                                                            }
                                                        }
                                                        reached = false;
                                                    }
                                                    grid[16][16].setBackground(Color.white);
                                                    break outerloop;
                                                
                                                }
                                                else if(jc.equals("R") || jc.equals("UR") || jc.equals("DR") || jc.equals("Mid") || jc.equals("Right") || jc.equals("ULR") || jc.equals("DLR") || jc.equals("RDown")){
                                                    grid[column+1][col].setrobot(paint);
                                                    grid[xco][yco].setrobot(0);
                                                    for(int ci = col; ci <= yco; ci++){
                                                        grid[column+1][ci].setBackground(new Color(102,255,102));
                                                    }
                                                    switch(currentrobot){
                                                        case 0:
                                                            robotpost.get(0).x = column+1;
                                                            robotpost.get(0).y = col;
                                                            break;
                                                        case 1:
                                                            robotpost.get(1).x = column+1;
                                                            robotpost.get(1).y = col;
                                                            break;
                                                        case 2:
                                                            robotpost.get(2).x = column+1;
                                                            robotpost.get(2).y = col;
                                                            break;
                                                        case 3:
                                                            robotpost.get(3).x = column+1;
                                                            robotpost.get(3).y = col;
                                                            break;
                                                        default: break;
                                                    }
                                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                                    ((RobotPosition) click).x = column+1;
                                                    ((RobotPosition) click).y = col;
                                                    grid[16][16].setBackground(Color.white);
                                                    break outerloop;
                                                }
                                                else if(jc.equals("Yes")){
                                                    grid[column+1][col].setBackground(new Color(102, 255, 102));
                                                    grid[xco][yco].setrobot(0);
                                                    grid[xco][yco].setBackground(new Color(102,255,102));
                
                                                    if(column == 1){
                                                        grid[xco][0].setBackground(new Color(102,255,102));
                                                        grid[xco][0].setrobot(paint);
                                                    }
                                                    switch(currentrobot){
                                                        case 0:
                                                            robotpost.get(0).x = column+1;
                                                            robotpost.get(0).y = 0;
                                                            break;
                                                        case 1:
                                                            robotpost.get(1).x = column+1;
                                                            robotpost.get(1).y = 0;
                                                            break;
                                                        case 2:
                                                            robotpost.get(2).x = column+1;
                                                            robotpost.get(2).y = 0;
                                                            break;
                                                        case 3:
                                                            robotpost.get(3).x = column+1;
                                                            robotpost.get(3).y = 0;
                                                            break;
                                                        default: break;
                                                    }
                                                    ((RobotPosition) click).x = column+1;
                                                    ((RobotPosition) click).y = 0;
                                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                                } 
                                            }
                                        }
                                    }
                                }
                                else if(jc.equals("DR") || jc.equals("DL") || jc.equals("Down") || jc.equals("Right")){
                                    grid[column+1][yco].setrobot(paint);
                                    if(visualoption == 0){
                                        for(Component d: grid[column+1][yco].getComponents()){
                                            if(d instanceof JLabel){
                                                JLabel j = (JLabel)d;
                                                String v = j.getText();
                                                System.out.println(v);
                                                if(v.equals(visual_option.getText())){
                                                    if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                        if(((RobotPosition) click).rcount == 4){
                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                            textswitch();
                                                            reached = true;
                                                        }
                                                    }
                                                    else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                        if(((RobotPosition) click).rcount == 5){
                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                            textswitch();
                                                            reached = true;
                                                        }
                                                    }
                                                    else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                        System.out.println("Hello from rcount 6");
                                                        if(((RobotPosition) click).rcount == 6){
                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                            textswitch();
                                                            reached = true;
                                                        }
                                                    }
                                                    else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                        if(((RobotPosition) click).rcount == 7){
                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                            textswitch();
                                                            reached = true;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else if(visualoption == 1){
                                        for(Component d: grid[column+1][yco].getComponents()){
                                            if(c instanceof JLabel){
                                                JLabel j = (JLabel)d;
                                                String v = j.getText();
                                                for(Component comp: chip.getComponents()){
                                                    if(d instanceof JLabel){
                                                        JLabel k = (JLabel)comp;
                                                        String i = k.getText();
                                                        if(v.equals(i)){
                                                            System.out.println(v);
                                                            if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                if(((RobotPosition) click).rcount == 0){
                                                                    // grid[16][16].setBackground(Color.white);
                                                                    switchplayer(0, column+1, yco);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                if(((RobotPosition) click).rcount == 1){
                                                                    // grid[16][16].setBackground(Color.white);
                                                                    switchplayer(1, column+1, yco);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                if(((RobotPosition) click).rcount == 2){
                                                                    // grid[16][16].setBackground(Color.white);
                                                                    switchplayer(2, column+1, yco);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                if(((RobotPosition) click).rcount == 3){
                                                                    // grid[16][16].setBackground(Color.white);
                                                                    switchplayer(3, column+1, yco);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("Q")){
                                                                switchplayer(((RobotPosition) click).rcount, column+1, yco);
                                                                reached = true;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else{
                                        for(Component d: grid[column+1][yco].getComponents()){
                                            if(c instanceof JLabel){
                                                JLabel j = (JLabel)d;
                                                String v = j.getText();
                                                for(Component comp: chip.getComponents()){
                                                    if(d instanceof JLabel){
                                                        JLabel k = (JLabel)comp;
                                                        String i = k.getText();
                                                        if(v.equals(i)){
                                                            if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                if(((RobotPosition) click).rcount == 8){
                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                    switchplayer(8, column+1, yco);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                if(((RobotPosition) click).rcount == 9){
                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                    switchplayer(9, column+1, yco);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                if(((RobotPosition) click).rcount == 10){
                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                    switchplayer(10, column+1, yco);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                if(((RobotPosition) click).rcount == 11){
                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                    switchplayer(11, column+1, yco);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("Q")){
                                                                switchplayer(((RobotPosition) click).rcount, column+1, yco);
                                                                reached = true;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    grid[xco][yco].setrobot(0);
                                    for(int ci = column+1; ci <= xco; ci++){
                                        grid[ci][yco].setBackground(new Color(255,255,153));
                                    }
                                    ((RobotPosition) click).x = column+1;
                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                    switch(currentrobot){
                                        case 0:
                                            robotpost.get(0).x = column+1;
                                            break;
                                        case 1:
                                            robotpost.get(1).x = column+1;
                                            break;
                                        case 2:
                                            robotpost.get(2).x = column+1;
                                            break;
                                        case 3:
                                            robotpost.get(3).x = column+1;
                                            break;
                                        default: break;
                                    };
                                    if(reached){
                                        savedtime = 60;
                                        for (int col = 0; col < x; col++) {
                                            for (int row = 0; row < y; row++) {
                                                if (!grid[col][row].getBackground().equals(Color.WHITE)) {
                                                    grid[col][row].setBackground(Color.WHITE);
                                                }
                                            }
                                        }
                                        reached = false;
                                    }
                                    grid[16][16].setBackground(Color.white);
                                    break outerloop;
                                }
                                else if(jc.equals("Yes")){
                                    grid[column][yco].setBackground(new Color(255, 102, 102));
                                    grid[xco][yco].setrobot(0);
                                    grid[xco][yco].setBackground(new Color(255,102,102));

                                    if(column == x-2){
                                        grid[x-1][yco].setBackground(new Color(255,102,102));
                                        grid[x-1][yco].setrobot(paint);
                                    }
                                    switch(currentrobot){
                                        case 0:
                                            robotpost.get(0).x = x-1;
                                            break;
                                        case 1:
                                            robotpost.get(1).x = x-1;
                                            break;
                                        case 2:
                                            robotpost.get(2).x = x-1;
                                            break;
                                        case 3:
                                            robotpost.get(3).x = x-1;
                                            break;
                                        default: break;
                                    };
                                    ((RobotPosition) click).x = x-1;
                                    ((RobotPosition) click).setBackground(Color.WHITE);

                                }
                            }
                        }
                    }
                }
            });

            right.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae){
                    String jc;
                    outerloop:
                    for(int column = yco; column <= y ; column++){
                        for(Component c : grid[xco][column+1].getComponents()){
                            if(c instanceof JLabel){
                                JLabel label = (JLabel)c;
                                jc = label.getText();
                                if(jc.equals("R") || jc.equals("UL") || jc.equals("DL") || jc.equals("Mid") || jc.equals("DRR") || jc.equals("URR")){
                                    grid[xco][column].setrobot(paint);
                                    grid[xco][yco].setrobot(0);
                                    for(int ci = column; ci >= yco; ci--){
                                        grid[xco][ci].setBackground(new Color(51,204,255));
                                    }
                                    ((RobotPosition) click).y = column;
                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                    switch(currentrobot){
                                        case 0:
                                            robotpost.get(0).y = column;
                                            break;
                                        case 1:
                                            robotpost.get(1).y = column;
                                            break;
                                        case 2:
                                            robotpost.get(2).y = column;
                                            break;
                                        case 3:
                                            robotpost.get(3).y = column;
                                            break;
                                        default: break;
                                    };
                                    grid[16][16].setBackground(Color.white);
                                    break outerloop;
                                }
                                else if(jc.equals("FSlash")){
                                    for(int row = xco; row < x; row++){
                                        for(Component e : grid[row+1][column+1].getComponents()){
                                            if(e instanceof JLabel){
                                                JLabel label1 = (JLabel)e;
                                                jc = label1.getText();
                                                if(jc.equals("R") || jc.equals("UL") || jc.equals("UR") || jc.equals("Mid") || jc.equals("DRR") || jc.equals("DLR") || jc.equals("URR") || jc.equals("ULR") || jc.equals("RRight") || jc.equals("RDown")){
                                                    grid[row][column+1].setrobot(paint);
                                                    grid[xco][yco].setrobot(0);
                                                    for(int ci = row; ci >= xco; ci--){
                                                        grid[ci][column+1].setBackground(new Color(51,204,255));
                                                    }
                                                    ((RobotPosition) click).x = row;
                                                    ((RobotPosition) click).y = column+1;
                                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                                    switch(currentrobot){
                                                        case 0:
                                                            robotpost.get(0).x = row;
                                                            robotpost.get(0).y = column+1;
                                                            break;
                                                        case 1:
                                                            robotpost.get(1).x = row;
                                                            robotpost.get(1).y = column+1;
                                                            break;
                                                        case 2:
                                                            robotpost.get(2).x = row;
                                                            robotpost.get(2).y = column+1;
                                                            break;
                                                        case 3:
                                                            robotpost.get(3).x = row;
                                                            robotpost.get(3).y = column+1;
                                                            break;
                                                        default: break;
                                                    }
                                                    grid[16][16].setBackground(Color.white);
                                                    break outerloop;
                                                }
                                                else if(jc.equals("DR") || jc.equals("DL") || jc.equals("Down") || jc.equals("Right")){
                                                    grid[row+1][column+1].setrobot(paint);
                                                    if(visualoption == 0){
                                                        for(Component d: grid[row+1][column+1].getComponents()){
                                                            if(d instanceof JLabel){
                                                                JLabel j = (JLabel)d;
                                                                String v = j.getText();
                                                                if(v.equals(visual_option.getText())){
                                                                    if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                        if(((RobotPosition) click).rcount == 4){
                                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                            textswitch();
                                                                            reached = true;
                                                                        }
                                                                    }
                                                                    else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                        if(((RobotPosition) click).rcount == 5){
                                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                            textswitch();
                                                                            reached = true;
                                                                        }
                                                                    }
                                                                    else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                        System.out.println("Hello from rcount 6");
                                                                        if(((RobotPosition) click).rcount == 6){
                                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                            textswitch();
                                                                            reached = true;
                                                                        }
                                                                    }
                                                                    else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                        if(((RobotPosition) click).rcount == 7){
                                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                            textswitch();
                                                                            reached = true;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    else if(visualoption == 1){
                                                        for(Component d: grid[row+1][column+1].getComponents()){
                                                            if(c instanceof JLabel){
                                                                JLabel j = (JLabel)d;
                                                                String v = j.getText();
                                                                for(Component comp: chip.getComponents()){
                                                                    if(d instanceof JLabel){
                                                                        JLabel k = (JLabel)comp;
                                                                        String i = k.getText();
                                                                        if(v.equals(i)){
                                                                            System.out.println(v);
                                                                            if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                                if(((RobotPosition) click).rcount == 0){
                                                                                    // grid[16][16].setBackground(Color.white);
                                                                                    switchplayer(0, row+1, column+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                                if(((RobotPosition) click).rcount == 1){
                                                                                    // grid[16][16].setBackground(Color.white);
                                                                                    switchplayer(1, row+1, column+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                                if(((RobotPosition) click).rcount == 2){
                                                                                    // grid[16][16].setBackground(Color.white);
                                                                                    switchplayer(2, row+1, column+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                                if(((RobotPosition) click).rcount == 3){
                                                                                    // grid[16][16].setBackground(Color.white);
                                                                                    switchplayer(3, row+1, column+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("Q")){
                                                                                switchplayer(((RobotPosition) click).rcount, row+1, column+1);
                                                                                reached = true;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    else{
                                                        for(Component d: grid[row+1][column+1].getComponents()){
                                                            if(c instanceof JLabel){
                                                                JLabel j = (JLabel)d;
                                                                String v = j.getText();
                                                                for(Component comp: chip.getComponents()){
                                                                    if(d instanceof JLabel){
                                                                        JLabel k = (JLabel)comp;
                                                                        String i = k.getText();
                                                                        if(v.equals(i)){
                                                                            if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                                if(((RobotPosition) click).rcount == 8){
                                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                                    switchplayer(8, row+1, column+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                                if(((RobotPosition) click).rcount == 9){
                                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                                    switchplayer(9, row+1, column+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                                if(((RobotPosition) click).rcount == 10){
                                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                                    switchplayer(10, row+1, column+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                                if(((RobotPosition) click).rcount == 11){
                                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                                    switchplayer(11, row+1, column+1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("Q")){
                                                                                switchplayer(((RobotPosition) click).rcount, row+1, column+1);
                                                                                reached = true;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    grid[xco][yco].setrobot(0);
                                                    for(int ci = row+1; ci >= xco; ci--){
                                                        grid[ci][column+1].setBackground(new Color(255,255,153));
                                                    }
                                                    ((RobotPosition) click).x = row+1;
                                                    ((RobotPosition) click).y = column+1;
                                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                                    switch(currentrobot){
                                                        case 0:
                                                            robotpost.get(0).x = row+1;
                                                            robotpost.get(0).y = column+1;
                                                            break;
                                                        case 1:
                                                            robotpost.get(1).x = row+1;
                                                            robotpost.get(1).y = column+1;
                                                            break;
                                                        case 2:
                                                            robotpost.get(2).x = row+1;
                                                            robotpost.get(2).y = column+1;
                                                            break;
                                                        case 3:
                                                            robotpost.get(3).x = row+1;
                                                            robotpost.get(3).y = column+1;
                                                            break;
                                                        default: break;
                                                    };
                                                    if(reached){
                                                        savedtime = 60;
                                                        for (int col = 0; col < x; col++) {
                                                            for (int rows = 0; rows < y; rows++) {
                                                                if (!grid[col][rows].getBackground().equals(Color.WHITE)) {
                                                                    grid[col][rows].setBackground(Color.WHITE);
                                                                }
                                                            }
                                                        }
                                                        reached = false;
                                                    }
                                                    grid[16][16].setBackground(Color.white);
                                                    break outerloop;
                                                }
                                                else if(jc.equals("Yes")){
                                                    grid[row][column+1].setBackground(new Color(255, 102, 102));
                                                    grid[xco][yco].setrobot(0);
                                                    grid[xco][yco].setBackground(new Color(255,102,102));
                
                                                    if(row == x-2){
                                                        grid[x-1][column+1].setBackground(new Color(255,102,102));
                                                        grid[x-1][column+1].setrobot(paint);
                                                    }
                                                    switch(currentrobot){
                                                        case 0:
                                                            robotpost.get(0).x = x-1;
                                                            robotpost.get(0).y = column+1;
                                                            break;
                                                        case 1:
                                                            robotpost.get(1).x = x-1;
                                                            robotpost.get(1).y = column+1;
                                                            break;
                                                        case 2:
                                                            robotpost.get(2).x = x-1;
                                                            robotpost.get(2).y = column+1;
                                                            break;
                                                        case 3:
                                                            robotpost.get(3).x = x-1;
                                                            robotpost.get(3).y = column+1;
                                                            break;
                                                        default: break;
                                                    };
                                                    ((RobotPosition) click).x = x-1;
                                                    ((RobotPosition) click).y = column+1;
                                                    ((RobotPosition) click).setBackground(Color.WHITE);
                
                                                }
                                            }
                                        }
                                    }
                                }
                                else if(jc.equals("BSlash")){
                                    for(int row = xco; row >= 0; row--){
                                        for(Component e : grid[row-1][column+1].getComponents()){
                                            if(e instanceof JLabel){
                                                JLabel jl = (JLabel)e;
                                                String l = jl.getText();
                                                if(l.equals("Yes")){
                                                    grid[row][column+1].setBackground(Color.yellow);
                                                    grid[xco][yco].setrobot(0);
                                                    grid[xco][yco].setBackground(Color.yellow);

                                                    if(row == 1){
                                                        grid[0][column+1].setrobot(paint);
                                                        grid[0][column+1].setBackground(Color.yellow);
                                                    }
                                                    switch(currentrobot){
                                                        case 0:
                                                            robotpost.get(0).x = 0;
                                                            robotpost.get(1).y = column+1;
                                                            break;
                                                        case 1:
                                                            robotpost.get(1).x = 0;
                                                            robotpost.get(1).y = column+1;
                                                            break;
                                                        case 2:
                                                            robotpost.get(2).x = 0;
                                                            robotpost.get(2).y = column+1;
                                                            break;
                                                        case 3:
                                                            robotpost.get(3).x = 0;
                                                            robotpost.get(3).y = column+1;
                                                            break;
                                                    }
                                                    ((RobotPosition) click).x = 0;
                                                    ((RobotPosition) click).y = column+1;
                                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                                }
                                                else if(l.equals("R") || l.equals("DR") || l.equals("DL")){
                                                    grid[row][column+1].setrobot(paint);
                                                    grid[xco][yco].setrobot(0);
                                                    grid[xco][yco].setBackground(Color.yellow);

                                                    switch(currentrobot){
                                                        case 0:
                                                            robotpost.get(0).x = row;
                                                            robotpost.get(0).y = column+1;
                                                            break;
                                                        case 1:
                                                            robotpost.get(1).x = row;
                                                            robotpost.get(1).y = column+1;
                                                            break;
                                                        case 2:
                                                            robotpost.get(2).x = row;
                                                            robotpost.get(2).y = column+1;
                                                            break;
                                                        case 3:
                                                            robotpost.get(3).x = row;
                                                            robotpost.get(3).y = column+1;
                                                            break;
                                                    }
                                                    ((RobotPosition) click).x = row;
                                                    ((RobotPosition) click).y = column+1;
                                                    ((RobotPosition) click).setBackground(Color.WHITE);

                                                    grid[16][16].setBackground(Color.white);
                                                }
                                            }
                                        }
                                    }
                                }
                                else if(jc.equals("UR") || jc.equals("DR") || jc.equals("Right") || jc.equals("RDown") ){
                                    grid[xco][column+1].setrobot(paint);
                                    if(visualoption == 0){
                                        System.out.println("Visualoption 0");
                                        for(Component d: grid[xco][column+1].getComponents()){
                                            if(d instanceof JLabel){
                                                JLabel j = (JLabel)d;
                                                String v = j.getText();
                                                System.out.println(v);
                                                if(v.equals(visual_option.getText())){
                                                    System.out.println("G))");
                                                    if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                        if(((RobotPosition) click).rcount == 4){
                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                            textswitch();
                                                            reached = true;
                                                        }
                                                    }
                                                    else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                        if(((RobotPosition) click).rcount == 5){
                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                            textswitch();
                                                            reached = true;
                                                        }
                                                    }
                                                    else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                        System.out.println("Hello from rcount 6");
                                                        if(((RobotPosition) click).rcount == 6){
                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                            textswitch();
                                                            reached = true;
                                                        }
                                                    }
                                                    else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                        if(((RobotPosition) click).rcount == 7){
                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                            textswitch();
                                                            reached = true;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else if(visualoption == 1){
                                        for(Component d: grid[xco][column+1].getComponents()){
                                            if(c instanceof JLabel){
                                                JLabel j = (JLabel)d;
                                                String v = j.getText();
                                                for(Component comp: chip.getComponents()){
                                                    if(d instanceof JLabel){
                                                        JLabel k = (JLabel)comp;
                                                        String i = k.getText();
                                                        if(v.equals(i)){
                                                            System.out.println(v);
                                                            if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                if(((RobotPosition) click).rcount == 0){
                                                                    // grid[16][16].setBackground(Color.white);
                                                                    switchplayer(0, xco, column+1);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                if(((RobotPosition) click).rcount == 1){
                                                                    // grid[16][16].setBackground(Color.white);
                                                                    switchplayer(1, xco, column+1);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                if(((RobotPosition) click).rcount == 2){
                                                                    // grid[16][16].setBackground(Color.white);
                                                                    switchplayer(2, xco, column+1);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                if(((RobotPosition) click).rcount == 3){
                                                                    // grid[16][16].setBackground(Color.white);
                                                                    switchplayer(3, xco, column+1);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("Q")){
                                                                switchplayer(((RobotPosition) click).rcount, xco, column+1);
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else{
                                        for(Component d: grid[xco][column+1].getComponents()){
                                            if(c instanceof JLabel){
                                                JLabel j = (JLabel)d;
                                                String v = j.getText();
                                                for(Component comp: chip.getComponents()){
                                                    if(d instanceof JLabel){
                                                        JLabel k = (JLabel)comp;
                                                        String i = k.getText();
                                                        System.out.println(i);
                                                        if(v.equals(i)){
                                                            if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                if(((RobotPosition) click).rcount == 8){
                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                    switchplayer(8, xco, column+1);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                if(((RobotPosition) click).rcount == 9){
                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                    switchplayer(9, xco, column+1);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                if(((RobotPosition) click).rcount == 10){
                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                    switchplayer(10, xco, column+1);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                if(((RobotPosition) click).rcount == 11){
                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                    switchplayer(11, xco, column+1);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("Q")){
                                                                switchplayer(((RobotPosition) click).rcount, xco, column+1);
                                                                reached = true;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    grid[xco][yco].setrobot(0);
                                    for(int ci = column+1; ci >= yco; ci--){
                                        grid[xco][ci].setBackground(new Color(51,204,255));
                                    }
                                    switch(currentrobot){
                                        case 0:
                                            robotpost.get(0).y = column+1;
                                            break;
                                        case 1:
                                            robotpost.get(1).y = column+1;
                                            break;
                                        case 2:
                                            robotpost.get(2).y = column+1;
                                            break;
                                        case 3:
                                            robotpost.get(3).y = column+1;
                                            break;
                                        default: break;
                                    };
                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                    ((RobotPosition) click).y = column+1;
                                    if(reached){
                                        savedtime = 60;
                                        for (int col = 0; col < x; col++) {
                                            for (int row = 0; row < y; row++) {
                                                if (!grid[col][row].getBackground().equals(Color.WHITE)) {
                                                    grid[col][row].setBackground(Color.WHITE);
                                                }
                                            }
                                        }
                                        reached = false;
                                    }   
                                    grid[16][16].setBackground(Color.white);
                                    break outerloop;
                                }
                                else if(jc.equals("Yes")){
                                    grid[xco][column].setBackground(new Color(51, 204, 255));
                                    grid[xco][yco].setrobot(0);
                                    grid[xco][yco].setBackground(new Color(51,204,255));

                                    if(column == y-2){
                                        grid[xco][y-1].setBackground(new Color(51,204,255));
                                        grid[xco][y-1].setrobot(paint);
                                    }
                                    switch(currentrobot){
                                        case 0:
                                            robotpost.get(0).y = y-1;
                                            break;
                                        case 1:
                                            robotpost.get(1).y = y-1;
                                            break;
                                        case 2:
                                            robotpost.get(2).y = y-1;
                                            break;
                                        case 3:
                                            robotpost.get(3).y = y-1;
                                            break;
                                        default: break;
                                    }
                                    ((RobotPosition) click).y = y-1;
                                    ((RobotPosition) click).setBackground(Color.WHITE);

                                }
                            }
                        }
                    }
                }
            });

            left.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae){
                    String jc;
                    outerloop:
                    for(int column = yco; column >= 0; column--){
                        for(Component c : grid[xco][column-1].getComponents()){
                            if(c instanceof JLabel){
                                JLabel label = (JLabel)c;
                                jc = label.getText();
                                if(jc.equals("UL") || jc.equals("DL")){
                                    grid[xco][column-1].setrobot(paint);
                                    if(visualoption == 0){
                                        for(Component d: grid[xco][column-1].getComponents()){
                                            if(d instanceof JLabel){
                                                JLabel j = (JLabel)d;
                                                String v = j.getText();
                                                if(v.equals(visual_option.getText())){
                                                    if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                        if(((RobotPosition) click).rcount == 4){
                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                            textswitch();
                                                            reached = true;
                                                        }
                                                    }
                                                    else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                        if(((RobotPosition) click).rcount == 5){
                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                            textswitch();
                                                            reached = true;
                                                        }
                                                    }
                                                    else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                        System.out.println("Hello from rcount 6");
                                                        if(((RobotPosition) click).rcount == 6){
                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                            textswitch();
                                                            reached = true;
                                                        }
                                                    }
                                                    else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                        if(((RobotPosition) click).rcount == 7){
                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                            textswitch();
                                                            reached = true;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else if(visualoption == 1){
                                        for(Component d: grid[xco][column-1].getComponents()){
                                            if(c instanceof JLabel){
                                                JLabel j = (JLabel)d;
                                                String v = j.getText();
                                                for(Component comp: chip.getComponents()){
                                                    if(d instanceof JLabel){
                                                        JLabel k = (JLabel)comp;
                                                        String i = k.getText();
                                                        if(v.equals(i)){
                                                            System.out.println(v);
                                                            if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                if(((RobotPosition) click).rcount == 0){
                                                                    // grid[16][16].setBackground(Color.white);
                                                                    switchplayer(0, xco, column-1);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                if(((RobotPosition) click).rcount == 1){
                                                                    // grid[16][16].setBackground(Color.white);
                                                                    switchplayer(1, xco, column-1);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                if(((RobotPosition) click).rcount == 2){
                                                                    // grid[16][16].setBackground(Color.white);
                                                                    switchplayer(2, xco, column-1);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                if(((RobotPosition) click).rcount == 3){
                                                                    switchplayer(3, xco, column-1);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("Q")){
                                                                switchplayer(((RobotPosition) click).rcount, xco, column-1);
                                                                reached = true;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else{
                                        for(Component d: grid[xco][column-1].getComponents()){
                                            if(c instanceof JLabel){
                                                JLabel j = (JLabel)d;
                                                String v = j.getText();
                                                for(Component comp: chip.getComponents()){
                                                    if(d instanceof JLabel){
                                                        JLabel k = (JLabel)comp;
                                                        String i = k.getText();
                                                        System.out.println(i);
                                                        if(v.equals(i)){
                                                            if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                if(((RobotPosition) click).rcount == 8){
                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                    switchplayer(8, xco, column-1);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                if(((RobotPosition) click).rcount == 9){
                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                    switchplayer(9, xco, column-1);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                if(((RobotPosition) click).rcount == 10){
                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                    switchplayer(10, xco, column-1);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                if(((RobotPosition) click).rcount == 11){
                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                    switchplayer(11, xco, column-1);
                                                                    reached = true;
                                                                }
                                                            }
                                                            else if(v.equals("Q")){
                                                                switchplayer(((RobotPosition) click).rcount, xco, column-1);
                                                                reached = true;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    grid[xco][yco].setrobot(0);
                                    for(int ci = column-1; ci <= yco; ci++){
                                        grid[xco][ci].setBackground(new Color(102,255,102));
                                    }
                                    switch(currentrobot){
                                        case 0:
                                            robotpost.get(0).y = column-1;
                                            break;
                                        case 1:
                                            robotpost.get(1).y = column-1;
                                            break;
                                        case 2:
                                            robotpost.get(2).y = column-1;
                                            break;
                                        case 3:
                                            robotpost.get(3).y = column-1;
                                            break;
                                        default: break;
                                    }
                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                    ((RobotPosition) click).y = column-1;
                                    if(reached){
                                        savedtime = 60;
                                        for (int col = 0; col < x; col++) {
                                            for (int row = 0; row < y; row++) {
                                                if (!grid[col][row].getBackground().equals(Color.WHITE)) {
                                                    grid[col][row].setBackground(Color.WHITE);
                                                }
                                            }
                                        }
                                        reached = false;
                                    }
                                    grid[16][16].setBackground(Color.white);
                                    break outerloop;
                                
                                }
                                else if(jc.equals("FSlash")){
                                    for(int row = xco; row >=0; row--){
                                        for(Component e: grid[row-1][column-1].getComponents()){
                                            if(e instanceof JLabel){
                                                JLabel j = (JLabel)e;
                                                String v = j.getText();
                                                if(v.equals("Yes")){
                                                    grid[row][column-1].setBackground(new Color(255, 255, 153));
                                                    grid[xco][yco].setrobot(0);
                                                    grid[xco][yco].setBackground(new Color(255,255,153));

                                                    if(row == 1){
                                                        grid[0][column-1].setBackground(new Color(255,255,153));
                                                        grid[0][column-1].setrobot(paint);
                                                        
                                                    }
                                                    switch(currentrobot){
                                                        case 0:
                                                            robotpost.get(0).x = 0;
                                                            robotpost.get(0).y = column-1;
                                                            break;
                                                        case 1:
                                                            robotpost.get(1).x = 0;
                                                            robotpost.get(1).y = column-1;
                                                            break;
                                                        case 2:
                                                            robotpost.get(2).x = 0;
                                                            robotpost.get(2).y = column-1;
                                                            break;
                                                        case 3:
                                                            robotpost.get(3).x = 0;
                                                            robotpost.get(3).y = column-1;
                                                            break;
                                                        default: break;
                                                    };
                                                    ((RobotPosition) click).x = 0;
                                                    ((RobotPosition) click).y = column-1;
                                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                                }
                                                else if(v.equals("R")){
                                                    grid[row][column-1].setrobot(paint);
                                                    grid[xco][yco].setrobot(0);
                                                    grid[xco][yco].setBackground(Color.yellow);

                                                    switch(currentrobot){
                                                        case 0:
                                                            robotpost.get(0).x = row;
                                                            robotpost.get(0).y = column-1;
                                                            break;
                                                        case 1:
                                                            robotpost.get(1).x = row;
                                                            robotpost.get(1).y = column-1;
                                                            break;
                                                        case 2:
                                                            robotpost.get(2).x = row;
                                                            robotpost.get(2).y = column-1;
                                                            break;
                                                        case 3:
                                                            robotpost.get(3).x = row;
                                                            robotpost.get(3).y = column-1;
                                                            break;
                                                    }
                                                    ((RobotPosition) click).x = row;
                                                    ((RobotPosition) click).y = column-1;
                                                    ((RobotPosition) click).setBackground(Color.WHITE);

                                                    grid[16][16].setBackground(Color.white);
                                                }
                                                else if(v.equals("BSlash")){
                                                    for(int newcolumn = yco; newcolumn < y; newcolumn++){
                                                        for(Component n: grid[row-1][newcolumn+1].getComponents()){
                                                            if(n instanceof JLabel){
                                                                JLabel jla = (JLabel)n;
                                                                String l = jla.getText();
                                                                if(l.equals("Yes")){
                                                                    grid[row-1][newcolumn].setBackground(new Color(51, 204, 255));
                                                                    grid[xco][yco].setrobot(0);
                                                                    grid[xco][yco].setBackground(new Color(51,204,255));

                                                                    if(newcolumn == y-2){
                                                                        grid[row-1][y-1].setBackground(new Color(51,204,255));
                                                                        grid[row-1][y-1].setrobot(paint);
                                                                    }
                                                                    switch(currentrobot){
                                                                        case 0:
                                                                            robotpost.get(0).x = row-1;
                                                                            robotpost.get(0).y = y-1;
                                                                            break;
                                                                        case 1:
                                                                            robotpost.get(1).x = row-1;
                                                                            robotpost.get(1).y = y-1;
                                                                            break;
                                                                        case 2:
                                                                            robotpost.get(2).x = row-1;
                                                                            robotpost.get(2).y = y-1;
                                                                            break;
                                                                        case 3:
                                                                            robotpost.get(3).x = row-1;
                                                                            robotpost.get(3).y = y-1;
                                                                            break;
                                                                        default: break;
                                                                    }
                                                                    ((RobotPosition) click).x = row-1;
                                                                    ((RobotPosition) click).y = y-1;
                                                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                else if(jc.equals("BSlash")){
                                    for(int row = xco; row < x; row++){
                                        for(Component e : grid[row+1][column-1].getComponents()){
                                            if(e instanceof JLabel){
                                                JLabel label1 = (JLabel)e;
                                                jc = label1.getText();
                                                if(jc.equals("R") || jc.equals("UL") || jc.equals("UR") || jc.equals("Mid") || jc.equals("DRR") || jc.equals("DLR") || jc.equals("URR") || jc.equals("ULR") || jc.equals("RDown")){
                                                    grid[row][column-1].setrobot(paint);
                                                    grid[xco][yco].setrobot(0);
                                                    for(int ci = row; ci >= xco; ci--){
                                                        grid[ci][column-1].setBackground(new Color(51,204,255));
                                                    }
                                                    ((RobotPosition) click).x = row;
                                                    ((RobotPosition) click).y = column-1;
                                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                                    switch(currentrobot){
                                                        case 0:
                                                            robotpost.get(0).x = row;
                                                            robotpost.get(0).y = column-1;
                                                            break;
                                                        case 1:
                                                            robotpost.get(1).x = row;
                                                            robotpost.get(1).y = column-1;
                                                            break;
                                                        case 2:
                                                            robotpost.get(2).x = row;
                                                            robotpost.get(2).y = column-1;
                                                            break;
                                                        case 3:
                                                            robotpost.get(3).x = row;
                                                            robotpost.get(3).y = column-1;
                                                            break;
                                                        default: break;
                                                    }
                                                    grid[16][16].setBackground(Color.white);
                                                    break outerloop;
                                                }
                                                else if(jc.equals("FSlash")){
                                                    for(int newcolumn = yco; newcolumn < y; newcolumn++){
                                                        for(Component n: grid[row+1][newcolumn+1].getComponents()){
                                                            if(n instanceof JLabel){
                                                                JLabel jla = (JLabel)n;
                                                                String l = jla.getText();
                                                                if(l.equals("Yes")){
                                                                    grid[row+1][newcolumn].setBackground(new Color(51, 204, 255));
                                                                    grid[xco][yco].setrobot(0);
                                                                    grid[xco][yco].setBackground(new Color(51,204,255));

                                                                    if(newcolumn == y-2){
                                                                        grid[row+1][y-1].setBackground(new Color(51,204,255));
                                                                        grid[row+1][y-1].setrobot(paint);
                                                                    }
                                                                    switch(currentrobot){
                                                                        case 0:
                                                                            robotpost.get(0).x = row+1;
                                                                            robotpost.get(0).y = y-1;
                                                                            break;
                                                                        case 1:
                                                                            robotpost.get(1).x = row+1;
                                                                            robotpost.get(1).y = y-1;
                                                                            break;
                                                                        case 2:
                                                                            robotpost.get(2).x = row+1;
                                                                            robotpost.get(2).y = y-1;
                                                                            break;
                                                                        case 3:
                                                                            robotpost.get(3).x = row+1;
                                                                            robotpost.get(3).y = y-1;
                                                                            break;
                                                                        default: break;
                                                                    }
                                                                    ((RobotPosition) click).x = row+1;
                                                                    ((RobotPosition) click).y = y-1;
                                                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                else if(jc.equals("DR") || jc.equals("DL") || jc.equals("Down") || jc.equals("Right")){
                                                    grid[row+1][column-1].setrobot(paint);
                                                    if(visualoption == 0){
                                                        for(Component d: grid[row+1][column-1].getComponents()){
                                                            if(d instanceof JLabel){
                                                                JLabel j = (JLabel)d;
                                                                String v = j.getText();
                                                                if(v.equals(visual_option.getText())){
                                                                    if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                        if(((RobotPosition) click).rcount == 4){
                                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                            textswitch();
                                                                            reached = true;
                                                                        }
                                                                    }
                                                                    else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                        if(((RobotPosition) click).rcount == 5){
                                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                            textswitch();
                                                                            reached = true;
                                                                        }
                                                                    }
                                                                    else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                        System.out.println("Hello from rcount 6");
                                                                        if(((RobotPosition) click).rcount == 6){
                                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                            textswitch();
                                                                            reached = true;
                                                                        }
                                                                    }
                                                                    else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                        if(((RobotPosition) click).rcount == 7){
                                                                            // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                            textswitch();
                                                                            reached = true;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    else if(visualoption == 1){
                                                        for(Component d: grid[row+1][column-1].getComponents()){
                                                            if(c instanceof JLabel){
                                                                JLabel j = (JLabel)d;
                                                                String v = j.getText();
                                                                for(Component comp: chip.getComponents()){
                                                                    if(d instanceof JLabel){
                                                                        JLabel k = (JLabel)comp;
                                                                        String i = k.getText();
                                                                        if(v.equals(i)){
                                                                            System.out.println(v);
                                                                            if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                                if(((RobotPosition) click).rcount == 0){
                                                                                    // grid[16][16].setBackground(Color.white);
                                                                                    switchplayer(0, row+1, column-1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                                if(((RobotPosition) click).rcount == 1){
                                                                                    // grid[16][16].setBackground(Color.white);
                                                                                    switchplayer(1, row+1, column-1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                                if(((RobotPosition) click).rcount == 2){
                                                                                    // grid[16][16].setBackground(Color.white);
                                                                                    switchplayer(2, row+1, column-1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                                if(((RobotPosition) click).rcount == 3){
                                                                                    // grid[16][16].setBackground(Color.white);
                                                                                    switchplayer(3, row+1, column-1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("Q")){
                                                                                switchplayer(((RobotPosition) click).rcount, row+1, column-1);
                                                                                reached = true;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    else{
                                                        for(Component d: grid[row+1][column-1].getComponents()){
                                                            if(c instanceof JLabel){
                                                                JLabel j = (JLabel)d;
                                                                String v = j.getText();
                                                                for(Component comp: chip.getComponents()){
                                                                    if(d instanceof JLabel){
                                                                        JLabel k = (JLabel)comp;
                                                                        String i = k.getText();
                                                                        if(v.equals(i)){
                                                                            if(v.equals("A") || v.equals("B") || v.equals("C") || v.equals("D")){
                                                                                if(((RobotPosition) click).rcount == 8){
                                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                                    switchplayer(8, row+1, column-1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("E") || v.equals("F") || v.equals("G") || v.equals("H")){
                                                                                if(((RobotPosition) click).rcount == 9){
                                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                                    switchplayer(9, row+1, column-1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("I") || v.equals("J") || v.equals("K") || v.equals("L")){
                                                                                if(((RobotPosition) click).rcount == 10){
                                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                                    switchplayer(10, row+1, column-1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("M") || v.equals("N") || v.equals("O") || v.equals("P")){
                                                                                if(((RobotPosition) click).rcount == 11){
                                                                                    // grid[16][16].setBackground(Color.white); // To prevent collisions from happening
                                                                                    switchplayer(11, row+1, column-1);
                                                                                    reached = true;
                                                                                }
                                                                            }
                                                                            else if(v.equals("Q")){
                                                                                switchplayer(((RobotPosition) click).rcount, row+1, column-1);
                                                                                reached = true;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    grid[xco][yco].setrobot(0);
                                                    for(int ci = row+1; ci >= xco; ci--){
                                                        grid[ci][column-1].setBackground(new Color(255,255,153));
                                                    }
                                                    ((RobotPosition) click).x = row+1;
                                                    ((RobotPosition) click).y = column-1;
                                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                                    switch(currentrobot){
                                                        case 0:
                                                            robotpost.get(0).x = row+1;
                                                            robotpost.get(0).y = column-1;
                                                            break;
                                                        case 1:
                                                            robotpost.get(1).x = row+1;
                                                            robotpost.get(1).y = column-1;
                                                            break;
                                                        case 2:
                                                            robotpost.get(2).x = row+1;
                                                            robotpost.get(2).y = column-1;
                                                            break;
                                                        case 3:
                                                            robotpost.get(3).x = row+1;
                                                            robotpost.get(3).y = column-1;
                                                            break;
                                                        default: break;
                                                    };
                                                    if(reached){
                                                        savedtime = 60;
                                                        for (int col = 0; col < x; col++) {
                                                            for (int rows = 0; rows < y; rows++) {
                                                                if (!grid[col][rows].getBackground().equals(Color.WHITE)) {
                                                                    grid[col][rows].setBackground(Color.WHITE);
                                                                }
                                                            }
                                                        }
                                                        reached = false;
                                                    }
                                                    grid[16][16].setBackground(Color.white);
                                                    break outerloop;
                                                }
                                                else if(jc.equals("Yes")){
                                                    grid[row][column-1].setBackground(new Color(255, 102, 102));
                                                    grid[xco][yco].setrobot(0);
                                                    grid[xco][yco].setBackground(new Color(255,102,102));
                
                                                    if(row == x-2){
                                                        grid[x-1][column-1].setBackground(new Color(255,102,102));
                                                        grid[x-1][column-1].setrobot(paint);
                                                    }
                                                    switch(currentrobot){
                                                        case 0:
                                                            robotpost.get(0).x = x-1;
                                                            robotpost.get(0).y = column-1;
                                                            break;
                                                        case 1:
                                                            robotpost.get(1).x = x-1;
                                                            robotpost.get(1).y = column-1;
                                                            break;
                                                        case 2:
                                                            robotpost.get(2).x = x-1;
                                                            robotpost.get(2).y = column-1;
                                                            break;
                                                        case 3:
                                                            robotpost.get(3).x = x-1;
                                                            robotpost.get(3).y = column-1;
                                                            break;
                                                        default: break;
                                                    };
                                                    ((RobotPosition) click).x = x-1;
                                                    ((RobotPosition) click).y = column-1;
                                                    ((RobotPosition) click).setBackground(Color.WHITE);
                
                                                }
                                            }
                                        }
                                    }
                                }
                                else if(jc.equals("R") || jc.equals("UR") || jc.equals("DR") || jc.equals("Mid") || jc.equals("ULR") || jc.equals("DLR") || jc.equals("RRight") || jc.equals("RDown")){
                                    grid[xco][column].setrobot(paint);
                                    grid[xco][yco].setrobot(0);
                                    for(int ci = column; ci <= yco; ci++){
                                        grid[xco][ci].setBackground(new Color(102,255,102));
                                    }
                                    switch(currentrobot){
                                        case 0:
                                            robotpost.get(0).y = column;
                                            break;
                                        case 1:
                                            robotpost.get(1).y = column;
                                            break;
                                        case 2:
                                            robotpost.get(2).y = column;
                                            break;
                                        case 3:
                                            robotpost.get(3).y = column;
                                            break;
                                        default: break;
                                    }
                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                    ((RobotPosition) click).y = column;
                                    grid[16][16].setBackground(Color.white);
                                    break outerloop;
                                }
                                else if(jc.equals("Yes")){
                                    grid[xco][column].setBackground(new Color(102, 255, 102));
                                    grid[xco][yco].setrobot(0);
                                    grid[xco][yco].setBackground(new Color(102,255,102));

                                    if(column == 1){
                                        grid[xco][0].setBackground(new Color(102,255,102));
                                        grid[xco][0].setrobot(paint);
                                    }
                                    switch(currentrobot){
                                        case 0:
                                            robotpost.get(0).y = 0;
                                            break;
                                        case 1:
                                            robotpost.get(1).y = 0;
                                            break;
                                        case 2:
                                            robotpost.get(2).y = 0;
                                            break;
                                        case 3:
                                            robotpost.get(3).y = 0;
                                            break;
                                        default: break;
                                    }
                                    ((RobotPosition) click).y = 0;
                                    ((RobotPosition) click).setBackground(Color.WHITE);
                                } 
                            }
                        }
                    }
                }
            });

        }
    }

    public void mouseClicked(MouseEvent arg0){}
    public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0) {}
    public void mouseReleased(MouseEvent arg0) {}
    
    public void enableComponents(Container container, boolean enable) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            component.setEnabled(enable);
            if (component instanceof Container) {
                enableComponents((Container)component, enable);
            }
        }
    }

    public void switchplayer(int robotcount, int column, int yrow){
        if (whos_turn.getText().equals("Player 1's turn")) {
            int s = Integer.valueOf(scoreboardtable.getValueAt(1,1).toString());
            scoreboardtable.setValueAt(s+1, 1, 1);

            JOptionPane.showMessageDialog(this.getContentPane(), "You got it!");

            originalposition.clear();
            for(int i = 0; i < robotpost.size(); i++){
                RobotPosition robotpost1 = new RobotPosition(robotpost.get(i).returnrobotx(), robotpost.get(i).returnroboty());
                originalposition.add(robotpost1);
            }

            chip.count = this.targetchip;
            repaint();
            this.targetchip++;
            visual_option.setText(Character.toString(lexicon.charAt(iconlist.get(this.targettext))));
            this.targettext++;
            whos_turn.setText("Player");
            lowestbid.setText("Bid");
            savedtime = 60;
            for (int col = 0; col < x; col++) {
                for (int row = 0; row < y; row++) {
                    for(Component c: grid[col][row].getComponents()){
                        if(c instanceof JLabel){
                            JLabel j = (JLabel)c;
                            String v = j.getText();
                            if(!v.equals("URR") || !v.equals("ULR") || !v.equals("DLR") || !v.equals("DRR") ){
                                for(Component d: grid[col][row].getComponents()){
                                    if(d instanceof JLabel){
                                        JLabel jl = (JLabel)d;
                                        String k = jl.getText();
                                        switch(k){
                                            case "A":
                                                grid[col][row].count = 1;
                                                repaint();
                                                break;
                                            case "B":
                                                grid[col][row].count = 2;
                                                repaint();
                                                break;
                                            case "C":
                                                grid[col][row].count = 3;
                                                repaint();
                                                break;
                                            case "D":
                                                grid[col][row].count = 4;
                                                repaint();
                                                break;
                                            case "E":
                                                grid[col][row].count = 5;
                                                repaint();
                                                break;
                                            case "F":
                                                grid[col][row].count = 6;
                                                repaint();
                                                break;
                                            case "G":
                                                grid[col][row].count = 7;
                                                repaint();
                                                break;
                                            case "H":
                                                grid[col][row].count = 8;
                                                repaint();
                                                break;
                                            case "I":
                                                grid[col][row].count = 9;
                                                repaint();
                                                break;
                                            case "J":
                                                grid[col][row].count = 10;
                                                repaint();
                                                break;
                                            case "K":
                                                grid[col][row].count = 11;
                                                repaint();
                                                break;
                                            case "L":
                                                grid[col][row].count = 12;
                                                repaint();
                                                break;
                                            case "M":
                                                grid[col][row].count = 13;
                                                repaint();
                                                break;
                                            case "N":
                                                grid[col][row].count = 14;
                                                repaint();
                                                break;
                                            case "O":
                                                grid[col][row].count = 15;
                                                repaint();
                                                break;
                                            case "P":
                                                grid[col][row].count = 16;
                                                repaint();
                                                break;
                                            case "Q":
                                                grid[col][row].count = 17;
                                                repaint();
                                                break;
                                            default: break;
                                        }
                                    }
                                }
                            }
                            if(col == column && row == yrow){
                                switch(robotcount){
                                    case 0:
                                        grid[col][row].count = 18;
                                        repaint();
                                        break;
                                    case 1:
                                        grid[col][row].count = 19;
                                        repaint();
                                        break;
                                    case 2:
                                        grid[col][row].count = 20;
                                        repaint();
                                        break;
                                    case 3:
                                        grid[col][row].count = 21;
                                        repaint();
                                        break;
                                    case 8:
                                        grid[col][row].count = 43;
                                        repaint();
                                        break;
                                    case 9:
                                        grid[col][row].count = 44;
                                        repaint();
                                        break;
                                    case 10:
                                        grid[col][row].count = 45;
                                        repaint();
                                        break;
                                    case 11:
                                        grid[col][row].count = 46;
                                        repaint();
                                        break;
                                    default: break;
                                }
                            }
                        }
                    }
                }
            }
            if(this.rounds != 16){
                this.rounds++;
                for(int i = 0; i < 4; i++){
                    player_time.replace(i+1, 0);
                }
            }
            else{
                int winnerscore = Integer.valueOf(scoreboardtable.getValueAt(1,1).toString());
                for(int i = 1; i <= 4; i++){
                    if(Integer.valueOf(scoreboardtable.getValueAt(i,1).toString()) > winnerscore){
                        winnerscore = Integer.valueOf(scoreboardtable.getValueAt(i,1).toString());
                        winner = i;
                    }
                }
                switch(winner){
                    case 1:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 1 wins!");
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 2 wins!");
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 3 wins!");
                        break;
                    case 4:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 4 wins!");
                        break;
                    default: break;
                }
            }
            enableComponents(timer, true);
            enableComponents(playerturn, false);
        } else if (whos_turn.getText().equals("Player 2's turn")) {
            int s = Integer.valueOf(scoreboardtable.getValueAt(2,1).toString());
            scoreboardtable.setValueAt(s+1, 2, 1);

            JOptionPane.showMessageDialog(this.getContentPane(), "You got it!");

            originalposition.clear();
            for(int i = 0; i < robotpost.size(); i++){
                RobotPosition robotpost1 = new RobotPosition(robotpost.get(i).returnrobotx(), robotpost.get(i).returnroboty());
                originalposition.add(robotpost1);
            }
            
            // enableComponents(timer, true);
            // enableComponents(playerturn, false);

            chip.count = this.targetchip;
            repaint();
            this.targetchip++;
            visual_option.setText(Character.toString(lexicon.charAt(iconlist.get(this.targettext))));
            this.targettext++;
            whos_turn.setText("Player");
            lowestbid.setText("Bid");
            savedtime = 60;
            for (int col = 0; col < x; col++) {
                for (int row = 0; row < y; row++) {
                    for(Component c: grid[col][row].getComponents()){
                        if(c instanceof JLabel){
                            JLabel j = (JLabel)c;
                            String v = j.getText();
                            if(!v.equals("URR") || !v.equals("ULR") || !v.equals("DLR") || !v.equals("DRR") ){
                                for(Component d: grid[col][row].getComponents()){
                                    if(d instanceof JLabel){
                                        JLabel jl = (JLabel)d;
                                        String k = jl.getText();
                                        switch(k){
                                            case "A":
                                                grid[col][row].count = 1;
                                                repaint();
                                                break;
                                            case "B":
                                                grid[col][row].count = 2;
                                                repaint();
                                                break;
                                            case "C":
                                                grid[col][row].count = 3;
                                                repaint();
                                                break;
                                            case "D":
                                                grid[col][row].count = 4;
                                                repaint();
                                                break;
                                            case "E":
                                                grid[col][row].count = 5;
                                                repaint();
                                                break;
                                            case "F":
                                                grid[col][row].count = 6;
                                                repaint();
                                                break;
                                            case "G":
                                                grid[col][row].count = 7;
                                                repaint();
                                                break;
                                            case "H":
                                                grid[col][row].count = 8;
                                                repaint();
                                                break;
                                            case "I":
                                                grid[col][row].count = 9;
                                                repaint();
                                                break;
                                            case "J":
                                                grid[col][row].count = 10;
                                                repaint();
                                                break;
                                            case "K":
                                                grid[col][row].count = 11;
                                                repaint();
                                                break;
                                            case "L":
                                                grid[col][row].count = 12;
                                                repaint();
                                                break;
                                            case "M":
                                                grid[col][row].count = 13;
                                                repaint();
                                                break;
                                            case "N":
                                                grid[col][row].count = 14;
                                                repaint();
                                                break;
                                            case "O":
                                                grid[col][row].count = 15;
                                                repaint();
                                                break;
                                            case "P":
                                                grid[col][row].count = 16;
                                                repaint();
                                                break;
                                            case "Q":
                                                grid[col][row].count = 17;
                                                repaint();
                                                break;
                                            default: break;
                                        }
                                    }
                                }
                            }
                            if(col == column && row == yrow){
                                switch(robotcount){
                                    case 0:
                                        grid[col][row].count = 18;
                                        repaint();
                                        break;
                                    case 1:
                                        grid[col][row].count = 19;
                                        repaint();
                                        break;
                                    case 2:
                                        grid[col][row].count = 20;
                                        repaint();
                                        break;
                                    case 3:
                                        grid[col][row].count = 21;
                                        repaint();
                                        break;
                                    case 8:
                                        grid[col][row].count = 43;
                                        repaint();
                                        break;
                                    case 9:
                                        grid[col][row].count = 44;
                                        repaint();
                                        break;
                                    case 10:
                                        grid[col][row].count = 45;
                                        repaint();
                                        break;
                                    case 11:
                                        grid[col][row].count = 46;
                                        repaint();
                                        break;
                                    default: break;
                                }
                            }
                        }
                    }
                }
            }
            if(this.rounds != 16){
                this.rounds++;
                for(int i = 0; i < 4; i++){
                    player_time.replace(i+1, 0);
                }
            }
            else{
                int winnerscore = Integer.valueOf(scoreboardtable.getValueAt(1,1).toString());
                for(int i = 1; i <= 4; i++){
                    if(Integer.valueOf(scoreboardtable.getValueAt(i,1).toString()) > winnerscore){
                        winnerscore = Integer.valueOf(scoreboardtable.getValueAt(i,1).toString());
                        winner = i;
                    }
                }
                switch(winner){
                    case 1:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 1 wins!");
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 2 wins!");
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 3 wins!");
                        break;
                    case 4:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 4 wins!");
                        break;
                    default: break;
                }
            }
            enableComponents(timer, true);
            enableComponents(playerturn, false);
        } else if (whos_turn.getText().equals("Player 3's turn")) {
            int s = Integer.valueOf(scoreboardtable.getValueAt(3,1).toString());
            scoreboardtable.setValueAt(s+1, 3, 1);

            JOptionPane.showMessageDialog(this.getContentPane(), "You got it!");

            originalposition.clear();
            for(int i = 0; i < robotpost.size(); i++){
                RobotPosition robotpost1 = new RobotPosition(robotpost.get(i).returnrobotx(), robotpost.get(i).returnroboty());
                originalposition.add(robotpost1);
            }
            
            // enableComponents(timer, true);
            // enableComponents(playerturn, false);

            chip.count = this.targetchip;
            repaint();
            this.targetchip++;
            visual_option.setText(Character.toString(lexicon.charAt(iconlist.get(this.targettext))));
            this.targettext++;
            whos_turn.setText("Player");
            lowestbid.setText("Bid");
            savedtime = 60;
            for (int col = 0; col < x; col++) {
                for (int row = 0; row < y; row++) {
                    for(Component c: grid[col][row].getComponents()){
                        if(c instanceof JLabel){
                            JLabel j = (JLabel)c;
                            String v = j.getText();
                            if(!v.equals("URR") || !v.equals("ULR") || !v.equals("DLR") || !v.equals("DRR") ){
                                for(Component d: grid[col][row].getComponents()){
                                    if(d instanceof JLabel){
                                        JLabel jl = (JLabel)d;
                                        String k = jl.getText();
                                        switch(k){
                                            case "A":
                                                grid[col][row].count = 1;
                                                repaint();
                                                break;
                                            case "B":
                                                grid[col][row].count = 2;
                                                repaint();
                                                break;
                                            case "C":
                                                grid[col][row].count = 3;
                                                repaint();
                                                break;
                                            case "D":
                                                grid[col][row].count = 4;
                                                repaint();
                                                break;
                                            case "E":
                                                grid[col][row].count = 5;
                                                repaint();
                                                break;
                                            case "F":
                                                grid[col][row].count = 6;
                                                repaint();
                                                break;
                                            case "G":
                                                grid[col][row].count = 7;
                                                repaint();
                                                break;
                                            case "H":
                                                grid[col][row].count = 8;
                                                repaint();
                                                break;
                                            case "I":
                                                grid[col][row].count = 9;
                                                repaint();
                                                break;
                                            case "J":
                                                grid[col][row].count = 10;
                                                repaint();
                                                break;
                                            case "K":
                                                grid[col][row].count = 11;
                                                repaint();
                                                break;
                                            case "L":
                                                grid[col][row].count = 12;
                                                repaint();
                                                break;
                                            case "M":
                                                grid[col][row].count = 13;
                                                repaint();
                                                break;
                                            case "N":
                                                grid[col][row].count = 14;
                                                repaint();
                                                break;
                                            case "O":
                                                grid[col][row].count = 15;
                                                repaint();
                                                break;
                                            case "P":
                                                grid[col][row].count = 16;
                                                repaint();
                                                break;
                                            case "Q":
                                                grid[col][row].count = 17;
                                                repaint();
                                                break;
                                            default: break;
                                        }
                                    }
                                }
                            }
                            if(col == column && row == yrow){
                                switch(robotcount){
                                    case 0:
                                        grid[col][row].count = 18;
                                        repaint();
                                        break;
                                    case 1:
                                        grid[col][row].count = 19;
                                        repaint();
                                        break;
                                    case 2:
                                        grid[col][row].count = 20;
                                        repaint();
                                        break;
                                    case 3:
                                        grid[col][row].count = 21;
                                        repaint();
                                        break;
                                    case 8:
                                        grid[col][row].count = 43;
                                        repaint();
                                        break;
                                    case 9:
                                        grid[col][row].count = 44;
                                        repaint();
                                        break;
                                    case 10:
                                        grid[col][row].count = 45;
                                        repaint();
                                        break;
                                    case 11:
                                        grid[col][row].count = 46;
                                        repaint();
                                        break;
                                    default: break;
                                }
                            }
                        }
                    }
                }
            }
            if(this.rounds != 16){
                this.rounds++;
                for(int i = 0; i < 4; i++){
                    player_time.replace(i+1, 0);
                }
            }
            else{
                int winnerscore = Integer.valueOf(scoreboardtable.getValueAt(1,1).toString());
                for(int i = 1; i <= 4; i++){
                    if(Integer.valueOf(scoreboardtable.getValueAt(i,1).toString()) > winnerscore){
                        winnerscore = Integer.valueOf(scoreboardtable.getValueAt(i,1).toString());
                        winner = i;
                    }
                }
                switch(winner){
                    case 1:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 1 wins!");
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 2 wins!");
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 3 wins!");
                        break;
                    case 4:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 4 wins!");
                        break;
                    default: break;
                }
            }
            enableComponents(timer, true);
            enableComponents(playerturn, false);
        } else {
            int s = Integer.valueOf(scoreboardtable.getValueAt(4,1).toString());
            scoreboardtable.setValueAt(s+1, 4, 1);

            JOptionPane.showMessageDialog(this.getContentPane(), "You got it!");

            originalposition.clear();
            for(int i = 0; i < robotpost.size(); i++){
                RobotPosition robotpost1 = new RobotPosition(robotpost.get(i).returnrobotx(), robotpost.get(i).returnroboty());
                originalposition.add(robotpost1);
            }
            
            // enableComponents(timer, true);
            // enableComponents(playerturn, false);

            chip.count = this.targetchip;
            repaint();
            this.targetchip++;
            visual_option.setText(Character.toString(lexicon.charAt(iconlist.get(this.targettext))));
            this.targettext++;
            whos_turn.setText("Player");
            lowestbid.setText("Bid");
            savedtime = 60;
            for (int col = 0; col < x; col++) {
                for (int row = 0; row < y; row++) {
                    for(Component c: grid[col][row].getComponents()){
                        if(c instanceof JLabel){
                            JLabel j = (JLabel)c;
                            String v = j.getText();
                            if(!v.equals("URR") || !v.equals("ULR") || !v.equals("DLR") || !v.equals("DRR") ){
                                for(Component d: grid[col][row].getComponents()){
                                    if(d instanceof JLabel){
                                        JLabel jl = (JLabel)d;
                                        String k = jl.getText();
                                        switch(k){
                                            case "A":
                                                grid[col][row].count = 1;
                                                repaint();
                                                break;
                                            case "B":
                                                grid[col][row].count = 2;
                                                repaint();
                                                break;
                                            case "C":
                                                grid[col][row].count = 3;
                                                repaint();
                                                break;
                                            case "D":
                                                grid[col][row].count = 4;
                                                repaint();
                                                break;
                                            case "E":
                                                grid[col][row].count = 5;
                                                repaint();
                                                break;
                                            case "F":
                                                grid[col][row].count = 6;
                                                repaint();
                                                break;
                                            case "G":
                                                grid[col][row].count = 7;
                                                repaint();
                                                break;
                                            case "H":
                                                grid[col][row].count = 8;
                                                repaint();
                                                break;
                                            case "I":
                                                grid[col][row].count = 9;
                                                repaint();
                                                break;
                                            case "J":
                                                grid[col][row].count = 10;
                                                repaint();
                                                break;
                                            case "K":
                                                grid[col][row].count = 11;
                                                repaint();
                                                break;
                                            case "L":
                                                grid[col][row].count = 12;
                                                repaint();
                                                break;
                                            case "M":
                                                grid[col][row].count = 13;
                                                repaint();
                                                break;
                                            case "N":
                                                grid[col][row].count = 14;
                                                repaint();
                                                break;
                                            case "O":
                                                grid[col][row].count = 15;
                                                repaint();
                                                break;
                                            case "P":
                                                grid[col][row].count = 16;
                                                repaint();
                                                break;
                                            case "Q":
                                                grid[col][row].count = 17;
                                                repaint();
                                                break;
                                            default: break;
                                        }
                                    }
                                }
                            }
                            if(col == column && row == yrow){
                                switch(robotcount){
                                    case 0:
                                        grid[col][row].count = 18;
                                        repaint();
                                        break;
                                    case 1:
                                        grid[col][row].count = 19;
                                        repaint();
                                        break;
                                    case 2:
                                        grid[col][row].count = 20;
                                        repaint();
                                        break;
                                    case 3:
                                        grid[col][row].count = 21;
                                        repaint();
                                        break;
                                    case 8:
                                        grid[col][row].count = 43;
                                        repaint();
                                        break;
                                    case 9:
                                        grid[col][row].count = 44;
                                        repaint();
                                        break;
                                    case 10:
                                        grid[col][row].count = 45;
                                        repaint();
                                        break;
                                    case 11:
                                        grid[col][row].count = 46;
                                        repaint();
                                        break;
                                    default: break;
                                }
                            }
                        }
                    }
                }
            }
            if(this.rounds != 16){
                this.rounds++;
                for(int i = 0; i < 4; i++){
                    player_time.replace(i+1, 0);
                }
            }
            else{
                int winnerscore = Integer.valueOf(scoreboardtable.getValueAt(1,1).toString());
                for(int i = 1; i <= 4; i++){
                    if(Integer.valueOf(scoreboardtable.getValueAt(i,1).toString()) > winnerscore){
                        winnerscore = Integer.valueOf(scoreboardtable.getValueAt(i,1).toString());
                        winner = i;
                    }
                }
                switch(winner){
                    case 1:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 1 wins!");
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 2 wins!");
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 3 wins!");
                        break;
                    case 4:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 4 wins!");
                        break;
                    default: break;
                }
            }
            enableComponents(timer, true);
            enableComponents(playerturn, false);
        }
    }

    public void textswitch(){
        if (whos_turn.getText().equals("Player 1's turn")) {
            int s = Integer.valueOf(scoreboardtable.getValueAt(1,1).toString());
            scoreboardtable.setValueAt(s+1, 1, 1);

            JOptionPane.showMessageDialog(this.getContentPane(), "You got it!");

            originalposition.clear();
            for(int i = 0; i < robotpost.size(); i++){
                RobotPosition robotpost1 = new RobotPosition(robotpost.get(i).returnrobotx(), robotpost.get(i).returnroboty());
                originalposition.add(robotpost1);
            }
            
            // enableComponents(timer, true);
            // enableComponents(playerturn, false);

            visual_option.setText(Character.toString(lexicon.charAt(iconlist.get(this.targettext))));
            this.targettext++;
            whos_turn.setText("Player");
            lowestbid.setText("Bid");
            savedtime = 60;
            for (int col = 0; col < x; col++) {
                for (int row = 0; row < y; row++) {
                    if (!grid[col][row].getBackground().equals(Color.WHITE)) {
                        grid[col][row].setBackground(Color.WHITE);
                    }
                }
            }
            if(this.rounds != 16){
                this.rounds++;
                for(int i = 0; i < 4; i++){
                    player_time.replace(i+1, 0);
                }
            }
            else{
                int winnerscore = Integer.valueOf(scoreboardtable.getValueAt(1,1).toString());
                for(int i = 1; i <= 4; i++){
                    if(Integer.valueOf(scoreboardtable.getValueAt(i,1).toString()) > winnerscore){
                        winnerscore = Integer.valueOf(scoreboardtable.getValueAt(i,1).toString());
                        winner = i;
                    }
                }
                switch(winner){
                    case 1:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 1 wins!");
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 2 wins!");
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 3 wins!");
                        break;
                    case 4:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 4 wins!");
                        break;
                    default: break;
                }
            }
            enableComponents(timer, true);
            enableComponents(playerturn, false);
        } else if (whos_turn.getText().equals("Player 2's turn")) {
            int s = Integer.valueOf(scoreboardtable.getValueAt(2,1).toString());
            scoreboardtable.setValueAt(s+1, 2, 1);

            JOptionPane.showMessageDialog(this.getContentPane(), "You got it!");

            originalposition.clear();
            for(int i = 0; i < robotpost.size(); i++){
                RobotPosition robotpost1 = new RobotPosition(robotpost.get(i).returnrobotx(), robotpost.get(i).returnroboty());
                originalposition.add(robotpost1);
            }

            visual_option.setText(Character.toString(lexicon.charAt(iconlist.get(this.targettext))));
            this.targettext++;
            whos_turn.setText("Player");
            lowestbid.setText("Bid");
            savedtime = 60;
            for (int col = 0; col < x; col++) {
                for (int row = 0; row < y; row++) {
                    if (!grid[col][row].getBackground().equals(Color.WHITE)) {
                        grid[col][row].setBackground(Color.WHITE);
                    }
                }
            }
            if(this.rounds != 16){
                this.rounds++;
                for(int i = 0; i < 4; i++){
                    player_time.replace(i+1, 0);
                }
            }
            else{
                int winnerscore = Integer.valueOf(scoreboardtable.getValueAt(1,1).toString());
                for(int i = 1; i <= 4; i++){
                    if(Integer.valueOf(scoreboardtable.getValueAt(i,1).toString()) > winnerscore){
                        winnerscore = Integer.valueOf(scoreboardtable.getValueAt(i,1).toString());
                        winner = i;
                    }
                }
                switch(winner){
                    case 1:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 1 wins!");
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 2 wins!");
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 3 wins!");
                        break;
                    case 4:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 4 wins!");
                        break;
                    default: break;
                }
            }
            enableComponents(timer, true);
            enableComponents(playerturn, false);
        } else if (whos_turn.getText().equals("Player 3's turn")) {
            int s = Integer.valueOf(scoreboardtable.getValueAt(3,1).toString());
            scoreboardtable.setValueAt(s+1, 3, 1);

            JOptionPane.showMessageDialog(this.getContentPane(), "You got it!");

            originalposition.clear();
            for(int i = 0; i < robotpost.size(); i++){
                RobotPosition robotpost1 = new RobotPosition(robotpost.get(i).returnrobotx(), robotpost.get(i).returnroboty());
                originalposition.add(robotpost1);
            }

            visual_option.setText(Character.toString(lexicon.charAt(iconlist.get(this.targettext))));
            this.targettext++;
            whos_turn.setText("Player");
            lowestbid.setText("Bid");
            savedtime = 60;
            for (int col = 0; col < x; col++) {
                for (int row = 0; row < y; row++) {
                    if (!grid[col][row].getBackground().equals(Color.WHITE)) {
                        grid[col][row].setBackground(Color.WHITE);
                    }
                }
            }
            if(this.rounds != 16){
                this.rounds++;
                for(int i = 0; i < 4; i++){
                    player_time.replace(i+1, 0);
                }
            }
            else{
                int winnerscore = Integer.valueOf(scoreboardtable.getValueAt(1,1).toString());
                for(int i = 1; i <= 4; i++){
                    if(Integer.valueOf(scoreboardtable.getValueAt(i,1).toString()) > winnerscore){
                        winnerscore = Integer.valueOf(scoreboardtable.getValueAt(i,1).toString());
                        winner = i;
                    }
                }
                switch(winner){
                    case 1:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 1 wins!");
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 2 wins!");
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 3 wins!");
                        break;
                    case 4:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 4 wins!");
                        break;
                    default: break;
                }
            }
            enableComponents(timer, true);
            enableComponents(playerturn, false);
        } else {
            int s = Integer.valueOf(scoreboardtable.getValueAt(4,1).toString());
            scoreboardtable.setValueAt(s+1, 4, 1);

            JOptionPane.showMessageDialog(this.getContentPane(), "You got it!");

            originalposition.clear();
            for(int i = 0; i < robotpost.size(); i++){
                RobotPosition robotpost1 = new RobotPosition(robotpost.get(i).returnrobotx(), robotpost.get(i).returnroboty());
                originalposition.add(robotpost1);
            }

            visual_option.setText(Character.toString(lexicon.charAt(iconlist.get(this.targettext))));
            this.targettext++;
            whos_turn.setText("Player");
            lowestbid.setText("Bid");
            savedtime = 60;
            for (int col = 0; col < x; col++) {
                for (int row = 0; row < y; row++) {
                    if (!grid[col][row].getBackground().equals(Color.WHITE)) {
                        grid[col][row].setBackground(Color.WHITE);
                    }
                }
            }
            if(this.rounds != 16){
                this.rounds++;
                for(int i = 0; i < 4; i++){
                    player_time.replace(i+1, 0);
                }
            }
            else{
                int winnerscore = Integer.valueOf(scoreboardtable.getValueAt(1,1).toString());
                for(int i = 1; i <= 4; i++){
                    if(Integer.valueOf(scoreboardtable.getValueAt(i,1).toString()) > winnerscore){
                        winnerscore = Integer.valueOf(scoreboardtable.getValueAt(i,1).toString());
                        winner = i;
                    }
                }
                switch(winner){
                    case 1:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 1 wins!");
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 2 wins!");
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 3 wins!");
                        break;
                    case 4:
                        JOptionPane.showMessageDialog(this.getContentPane(), "Player 4 wins!");
                        break;
                    default: break;
                }
            }
            enableComponents(timer, true);
            enableComponents(playerturn, false);
        }
    }
}