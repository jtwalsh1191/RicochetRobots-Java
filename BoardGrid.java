import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.util.HashSet;
import java.util.Random;
import java.util.ArrayList;
import java.awt.*;

// Class for the intialisation of the game board GUI
public class BoardGrid extends JPanel{

    private int xcoord, ycoord, xcolumn, yrow;
    public int count;
    private Random rand;

    public BoardGrid(int xcoord, int ycoord, int xcolumn, int yrow){
        super();
        this.setSize(20,20);
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.xcolumn = xcolumn;
        this.yrow = yrow;
        rand = new Random();
    }

    public void setrobot(int count){ // Setting up the robot pieces
        this.count = count;
        if(count == 0){
            for(Component c: this.getComponents()){ // Should the JLabel hold "R", it will be changed to "Yes" to allow other robot pieces to pass through
                if(c instanceof JLabel){
                    JLabel j = (JLabel)c;
                    String v = j.getText();
                    if(v.equals("R")){
                        j.setText("Yes");
                    }
                    else if(v.equals("URR")){
                        j.setText("UR");
                    }
                    else if(v.equals("ULR")){
                        j.setText("UL");
                    }
                    else if(v.equals("DRR")){
                        j.setText("DR");
                    }
                    else if(v.equals("DLR")){
                        j.setText("DL");
                    }
                    else if(v.equals("RRight")){
                        j.setText("Right");
                    }
                    else if(v.equals("RDown")){
                        j.setText("Down");
                    }
                }
            }
            repaint();
        }
        else{
            for(Component c: this.getComponents()){
                if(c instanceof JLabel){
                    JLabel j = (JLabel)c;
                    String v = j.getText();
                    if(v.equals("UR")){
                        j.setText("URR");
                        repaint();
                    }
                    else if(v.equals("UL")){
                        j.setText("ULR");
                        repaint();
                    }
                    else if(v.equals("DR")){
                        j.setText("DRR");
                        repaint();
                    }
                    else if(v.equals("DL")){
                        j.setText("DLR");
                        repaint();
                    }
                    else if(v.equals("Right")){
                        j.setText("RRight");
                        repaint();
                    }
                    else if(v.equals("Down")){
                        j.setText("RDown");
                        repaint();
                    }
                    else if(v.equals("Yes")){
                        j.setText("R");
                        repaint();
                    }
                }
            }
            
        }
        
    }

    // Setting the value of robots given "Text" option, to be further implemented
    public void setrobottext(int count){
        switch(count){
            case 1:
                JLabel label = new JLabel("R");
                this.add(new JLabel("Alpha"));
                this.add(label);
                label.setVisible(false);
                break;
            case 2:
                JLabel label1 = new JLabel("R");
                this.add(new JLabel("Beta"));
                this.add(label1);
                label1.setVisible(false);
                break;
            case 3:
                JLabel label2 = new JLabel("R");
                this.add(new JLabel("Charlie"));
                this.add(label2);
                label2.setVisible(false);
                break;
            case 4:
                JLabel label3 = new JLabel("R");
                this.add(new JLabel("Delta"));
                this.add(label3);
                label3.setVisible(false);
                break;
            default:break;
        }
    }


    public void backgroundSet(int visual, ArrayList<Integer> targetpiece){
        this.setBackground(Color.WHITE);
        this.setBorder(new LineBorder(Color.lightGray));
        JLabel jl = new JLabel("Yes"); //"Yes" Jlabel is added to ensure robots are able to move across the board
        this.add(jl);
        jl.setVisible(false);
        

        // Tiles with border will have JLabels in accordance to the walls
        // "UR" = Up Right
        // "UL" = Up Left
        // "DR" = Down Right
        // "DL" = Down Left
        // "Right" = Right
        // "Down" = Down
        // "Mid" = Four tiles in the middle of the board
        if(visual == 1){ // If "Color" is chosen, it will only feature shapes and colors, no text
            switch(this.xcolumn){
                case 0:
                    switch(this.yrow){
                        case 3:
                            this.setBorder(new CompoundBorder(new MatteBorder(0,0,0,3,Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Right"); 
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 10:
                            this.setBorder(new CompoundBorder(new MatteBorder(0,0,0,3,Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no1 = new JLabel("Right");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 1:
                    switch(this.yrow){
                        case 5:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            this.count = targetpiece.get(0);
                            repaint();
                            JLabel no = new JLabel("UR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 12:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes1 = new Icon(targetpiece.get(1));
                            // shapes1.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes1);
                            this.count = targetpiece.get(1);
                            repaint();
                            JLabel no1 = new JLabel("UL");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 2:
                    switch(this.yrow){
                        case 1:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(2));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(2);
                            repaint();
                            JLabel no = new JLabel("DL");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 14:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes1 = new Icon(targetpiece.get(3));
                            // shapes1.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes1);
                            this.count = targetpiece.get(3);
                            repaint();
                            JLabel no1 = new JLabel("DL");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 3:
                    switch(this.yrow){
                        case 0:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Down");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(4));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(4);
                            repaint();
                            JLabel no1 = new JLabel("DL");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                        
                    }
                    break;
                case 4:
                    switch(this.yrow){
                        case 6:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(5));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(5);
                            repaint();
                            JLabel no = new JLabel("DR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 15:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no1 = new JLabel("Down");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 5:
                    switch(this.yrow){
                        case 9:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(6));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(6);
                            repaint();
                            JLabel no = new JLabel("DR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                    }
                    break;
                case 6:
                    switch(this.yrow){
                        case 2:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(7));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(7);
                            repaint();
                            JLabel no = new JLabel("DR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 11:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes1 = new Icon(targetpiece.get(8));
                            // shapes1.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes1);
                            this.count = targetpiece.get(8);
                            repaint();
                            JLabel no1 = new JLabel("UR");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 7:
                    switch(this.yrow){
                        case 7:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), null));
                            JLabel no = new JLabel("Mid");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), null));
                            JLabel no1 = new JLabel("Mid");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 8:
                    switch(this.yrow){
                        case 7:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), null));
                            JLabel no = new JLabel("Mid");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), null));
                            JLabel no1 = new JLabel("Mid");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 9:
                    switch(this.yrow){
                        case 1:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(9));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(9);
                            repaint();
                            JLabel no = new JLabel("UR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 5:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes1 = new Icon(targetpiece.get(10));
                            // shapes1.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes1);
                            this.count = targetpiece.get(10);
                            repaint();
                            JLabel no1 = new JLabel("UL");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                        case 15:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no2 = new JLabel("Down");
                            this.add(no2);
                            no2.setVisible(false);
                            break;
                    }
                    break;
                case 10:
                    switch(this.yrow){
                        case 0:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Down");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(11));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(11);
                            repaint();
                            JLabel no1 = new JLabel("DR");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 11:
                    switch(this.yrow){
                        case 13:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(12));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(12);
                            repaint();
                            JLabel no = new JLabel("UL");
                            this.add(no);
                            no.setVisible(false);
                            break;
                    }
                    break;
                case 12:
                    switch(this.yrow){
                        case 6:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(13));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(13);
                            repaint();
                            JLabel no = new JLabel("DR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                    }
                    break;
                case 13:
                    switch(this.yrow){
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(14));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(14);
                            repaint();
                            JLabel no = new JLabel("DL");
                            this.add(no);
                            no.setVisible(false);
                            break;
                    }
                    break;
                case 14:
                    switch(this.yrow){
                        case 2:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(15));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(15);
                            repaint();
                            JLabel no = new JLabel("DL");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 14:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes1 = new Icon(targetpiece.get(16));
                            // shapes1.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes1);
                            this.count = targetpiece.get(16);
                            repaint();
                            JLabel no1 = new JLabel("UR");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 15:
                    switch(this.yrow){
                        case 5:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Right");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 11:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no1 = new JLabel("Right");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                default: break;
            }
        }
        else if(visual == 0){ // If "Text" was chosen, it will only feature letters but not shapes or colors, to be further implemented
            switch(this.xcolumn){
                case 0:
                    switch(this.yrow){
                        case 3:
                            this.setBorder(new CompoundBorder(new MatteBorder(0,0,0,3,Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Right"); 
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 10:
                            this.setBorder(new CompoundBorder(new MatteBorder(0,0,0,3,Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no1 = new JLabel("Right"); 
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 1:
                    switch(this.yrow){
                        case 5:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("A");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no = new JLabel("UR"); 
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 12:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("B");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no1 = new JLabel("UL"); 
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 2:
                    switch(this.yrow){
                        case 1:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("C");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no = new JLabel("DL"); 
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 14:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("D");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no1 = new JLabel("DL"); 
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 3:
                    switch(this.yrow){
                        case 0:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Down"); 
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("E");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no1 = new JLabel("DL"); 
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                        
                    }
                    break;
                case 4:
                    switch(this.yrow){
                        case 6:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("F");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no = new JLabel("DR"); 
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 15:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no1 = new JLabel("Down"); 
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 5:
                    switch(this.yrow){
                        case 9:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("G");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no = new JLabel("DR"); 
                            this.add(no);
                            no.setVisible(false);
                            break;
                    }
                    break;
                case 6:
                    switch(this.yrow){
                        case 2:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("H");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no = new JLabel("DR"); 
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 11:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("I");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no1 = new JLabel("UR"); 
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 7:
                    switch(this.yrow){
                        case 7:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), null));
                            JLabel no = new JLabel("Mid"); 
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), null));
                            JLabel no1 = new JLabel("Mid"); 
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 8:
                    switch(this.yrow){
                        case 7:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), null));
                            JLabel no = new JLabel("Mid"); 
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), null));
                            JLabel no1 = new JLabel("Mid"); 
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 9:
                    switch(this.yrow){
                        case 1:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("J");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no = new JLabel("UR"); 
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 5:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("K");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no1 = new JLabel("UL"); 
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                        case 15:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no2 = new JLabel("Down"); 
                            this.add(no2);
                            no2.setVisible(false);
                            break;
                    }
                    break;
                case 10:
                    switch(this.yrow){
                        case 0:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Down"); 
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("L");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no1 = new JLabel("DR"); 
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 11:
                    switch(this.yrow){
                        case 13:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("M");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no = new JLabel("UL"); 
                            this.add(no);
                            no.setVisible(false);
                            break;
                    }
                    break;
                case 12:
                    switch(this.yrow){
                        case 6:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("N");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no = new JLabel("DR"); 
                            this.add(no);
                            no.setVisible(false);
                            break;
                    }
                    break;
                case 13:
                    switch(this.yrow){
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("O");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no = new JLabel("DL"); 
                            this.add(no);
                            no.setVisible(false);
                            break;
                    }
                    break;
                case 14:
                    switch(this.yrow){
                        case 2:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("P");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no = new JLabel("DL"); 
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 14:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("Q");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no1 = new JLabel("UR"); 
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 15:
                    switch(this.yrow){
                        case 5:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Right"); 
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 11:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no1 = new JLabel("Right"); 
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                default: break;
            }
            
        }
        else{ // Features both colored shapes and letters, to be further implemented
            switch(this.xcolumn){
                case 0:
                    switch(this.yrow){
                        case 3:
                            this.setBorder(new CompoundBorder(new MatteBorder(0,0,0,3,Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Right"); 
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 10:
                            this.setBorder(new CompoundBorder(new MatteBorder(0,0,0,3,Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no1 = new JLabel("Right");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 1:
                    switch(this.yrow){
                        case 5:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            this.count = targetpiece.get(0) + 25;
                            repaint();
                            JLabel no = new JLabel("UR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 12:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes1 = new Icon(targetpiece.get(1));
                            // shapes1.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes1);
                            this.count = targetpiece.get(1) + 25;
                            repaint();
                            JLabel no1 = new JLabel("UL");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 2:
                    switch(this.yrow){
                        case 1:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(2));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(2) + 25;
                            repaint();
                            JLabel no = new JLabel("DL");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 14:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes1 = new Icon(targetpiece.get(3));
                            // shapes1.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes1);
                            this.count = targetpiece.get(3) + 25;
                            repaint();
                            JLabel no1 = new JLabel("DL");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 3:
                    switch(this.yrow){
                        case 0:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Down");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(4));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(4) + 25;
                            repaint();
                            JLabel no1 = new JLabel("DL");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                        
                    }
                    break;
                case 4:
                    switch(this.yrow){
                        case 6:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(5));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(5) + 25;
                            repaint();
                            JLabel no = new JLabel("DR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 15:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no1 = new JLabel("Down");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 5:
                    switch(this.yrow){
                        case 9:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(6));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(6) + 25;
                            repaint();
                            JLabel no = new JLabel("DR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                    }
                    break;
                case 6:
                    switch(this.yrow){
                        case 2:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(7));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(7) + 25;
                            repaint();
                            JLabel no = new JLabel("DR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 11:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes1 = new Icon(targetpiece.get(8));
                            // shapes1.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes1);
                            this.count = targetpiece.get(8) + 25;
                            repaint();
                            JLabel no1 = new JLabel("UR");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 7:
                    switch(this.yrow){
                        case 7:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), null));
                            JLabel no = new JLabel("Mid");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), null));
                            JLabel no1 = new JLabel("Mid");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 8:
                    switch(this.yrow){
                        case 7:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), null));
                            JLabel no = new JLabel("Mid");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), null));
                            JLabel no1 = new JLabel("Mid");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 9:
                    switch(this.yrow){
                        case 1:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(9));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(9) + 25;
                            repaint();
                            JLabel no = new JLabel("UR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 5:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes1 = new Icon(targetpiece.get(10));
                            // shapes1.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes1);
                            this.count = targetpiece.get(10) + 25;
                            repaint();
                            JLabel no1 = new JLabel("UL");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                        case 15:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no2 = new JLabel("Down");
                            this.add(no2);
                            no2.setVisible(false);
                            break;
                    }
                    break;
                case 10:
                    switch(this.yrow){
                        case 0:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Down");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(11));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(11) + 25;
                            repaint();
                            JLabel no1 = new JLabel("DR");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 11:
                    switch(this.yrow){
                        case 13:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(12));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(12) + 25;
                            repaint();
                            JLabel no = new JLabel("UL");
                            this.add(no);
                            no.setVisible(false);
                            break;
                    }
                    break;
                case 12:
                    switch(this.yrow){
                        case 6:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(13));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(13) + 25;
                            repaint();
                            JLabel no = new JLabel("DR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                    }
                    break;
                case 13:
                    switch(this.yrow){
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(14));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(14) + 25;
                            repaint();
                            JLabel no = new JLabel("DL");
                            this.add(no);
                            no.setVisible(false);
                            break;
                    }
                    break;
                case 14:
                    switch(this.yrow){
                        case 2:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(15));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(15) + 25;
                            repaint();
                            JLabel no = new JLabel("DL");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 14:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes1 = new Icon(targetpiece.get(16));
                            // shapes1.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes1);
                            this.count = targetpiece.get(16) + 25;
                            repaint();
                            JLabel no1 = new JLabel("UR");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 15:
                    switch(this.yrow){
                        case 5:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Right");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 11:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no1 = new JLabel("Right");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                default: break;
            }
        }

    }

    public void backgroundSetComplex(int visual, ArrayList<Integer> targetpiece){
        this.setBackground(Color.WHITE);
        this.setBorder(new LineBorder(Color.lightGray));
        JLabel jl = new JLabel("Yes"); //"Yes" Jlabel is added to ensure robots are able to move across the board
        this.add(jl);
        jl.setVisible(false);
        if(visual == 1){ // If "Color" is chosen, it will only feature shapes and colors, no text
            switch(this.xcolumn){
                case 0:
                    switch(this.yrow){
                        case 5:
                            this.setBorder(new CompoundBorder(new MatteBorder(0,0,0,3,Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Right"); 
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(0,0,0,3,Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no1 = new JLabel("Right");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 1:
                    switch(this.yrow){
                        case 4:
                            this.count = 47;
                            repaint();
                            break;
                        case 12:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            this.count = targetpiece.get(0);
                            repaint();
                            JLabel no1 = new JLabel("UL");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 2:
                    switch(this.yrow){
                        case 14:
                            this.count = 48;
                            repaint();
                            break;
                        case 15:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no1 = new JLabel("Down");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 3:
                    switch(this.yrow){
                        case 1:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            this.count = targetpiece.get(1);
                            repaint();
                            JLabel no = new JLabel("UL");
                            this.add(no);
                            no.setVisible(false);
                            break;
                    }
                    break;
                case 4:
                    switch(this.yrow){
                        case 6:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(5));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(2);
                            repaint();
                            JLabel no = new JLabel("DR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            this.count = targetpiece.get(3);
                            repaint();
                            JLabel no1 = new JLabel("DR");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 5:
                    switch(this.yrow){
                        case 0:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Down");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 13:
                            this.setBorder(new CompoundBorder(new MatteBorder(0,3,3,0,Color.BLACK), new LineBorder(Color.lightGray)));
                            this.count = targetpiece.get(4);
                            repaint();
                            JLabel no1 = new JLabel("DL");
                            this.add(no1);
                            no1.setVisible(false);
                    }
                    break;
                case 6:
                    switch(this.yrow){
                        case 2:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(7));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(5);
                            repaint();
                            JLabel no = new JLabel("UR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 3:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(7));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(6);
                            repaint();
                            JLabel no1 = new JLabel("DL");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                        case 13:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes1 = new Icon(targetpiece.get(8));
                            // shapes1.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes1);
                            this.count = targetpiece.get(7);
                            repaint();
                            JLabel no2 = new JLabel("UR");
                            this.add(no2);
                            no2.setVisible(false);
                            break;
                    }
                    break;
                case 7:
                    switch(this.yrow){
                        case 5:
                            this.count = 48;
                            repaint();
                            break;
                        case 7:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), null));
                            JLabel no1 = new JLabel("Mid");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), null));
                            JLabel no2 = new JLabel("Mid");
                            this.add(no2);
                            no2.setVisible(false);
                            break;
                        case 11:
                            this.count = 47;
                            repaint();
                            break;
                    }
                    break;
                case 8:
                    switch(this.yrow){
                        case 7:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), null));
                            JLabel no = new JLabel("Mid");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), null));
                            JLabel no1 = new JLabel("Mid");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 9:
                    switch(this.yrow){
                        case 2:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(9));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(8);
                            repaint();
                            JLabel no = new JLabel("DL");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 10:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes1 = new Icon(targetpiece.get(10));
                            // shapes1.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes1);
                            this.count = targetpiece.get(9);
                            repaint();
                            JLabel no1 = new JLabel("DR");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 10:
                    switch(this.yrow){
                        case 0:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Down");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 7:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(11));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(10);
                            repaint();
                            JLabel no1 = new JLabel("UR");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 11:
                    switch(this.yrow){
                        case 12:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(12));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(11);
                            repaint();
                            JLabel no = new JLabel("UR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 13:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(12));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(12);
                            repaint();
                            JLabel no1 = new JLabel("DL");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 12:
                    switch(this.yrow){
                        case 3:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(12));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(13);
                            repaint();
                            JLabel no = new JLabel("DR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 6:
                            this.count = 47;
                            repaint();
                            break;
                        case 9:
                            this.count = 48;
                            repaint();
                            break;
                        case 15:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no3 = new JLabel("Down");
                            this.add(no3);
                            no3.setVisible(false);
                            break;
                    }
                    break;
                case 13:
                    switch(this.yrow){
                        case 1:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Down");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 3:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(14));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(14);
                            repaint();
                            JLabel no1 = new JLabel("UL");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                        case 9:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(14));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(15);
                            repaint();
                            JLabel no2 = new JLabel("UL");
                            this.add(no2);
                            no2.setVisible(false);
                            break;
                    }
                    break;
                case 14:
                    switch(this.yrow){
                        case 5:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(15));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(16);
                            repaint();
                            JLabel no = new JLabel("UR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 11:
                            this.count = 48;
                            repaint();
                            break;
                    }
                    break;
                case 15:
                    switch(this.yrow){
                        case 6:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Right");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 10:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no1 = new JLabel("Right");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                default: break;
            }
        }
        else if(visual == 0){ // If "Text" is chosen, it will only feature text
            switch(this.xcolumn){
                case 0:
                    switch(this.yrow){
                        case 5:
                            this.setBorder(new CompoundBorder(new MatteBorder(0,0,0,3,Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Right"); 
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(0,0,0,3,Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no1 = new JLabel("Right");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 1:
                    switch(this.yrow){
                        case 4:
                            this.count = 47;
                            repaint();
                            break;
                        case 12:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("A");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no1 = new JLabel("UL");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 2:
                    switch(this.yrow){
                        case 14:
                            this.count = 48;
                            repaint();
                            break;
                        case 15:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no1 = new JLabel("Down");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 3:
                    switch(this.yrow){
                        case 1:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("B");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no = new JLabel("UL");
                            this.add(no);
                            no.setVisible(false);
                            break;
                    }
                    break;
                case 4:
                    switch(this.yrow){
                        case 6:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(5));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("C");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no = new JLabel("DR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("D");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no1 = new JLabel("DR");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 5:
                    switch(this.yrow){
                        case 0:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Down");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 13:
                            this.setBorder(new CompoundBorder(new MatteBorder(0,3,3,0,Color.BLACK), new LineBorder(Color.lightGray)));
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("E");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no1 = new JLabel("DL");
                            this.add(no1);
                            no1.setVisible(false);
                    }
                    break;
                case 6:
                    switch(this.yrow){
                        case 2:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(7));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("F");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no = new JLabel("UR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 3:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(7));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("G");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no1 = new JLabel("DL");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                        case 13:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes1 = new Icon(targetpiece.get(8));
                            // shapes1.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes1);
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("H");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no2 = new JLabel("UR");
                            this.add(no2);
                            no2.setVisible(false);
                            break;
                    }
                    break;
                case 7:
                    switch(this.yrow){
                        case 5:
                            this.count = 48;
                            repaint();
                            break;
                        case 7:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), null));
                            JLabel no1 = new JLabel("Mid");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), null));
                            JLabel no2 = new JLabel("Mid");
                            this.add(no2);
                            no2.setVisible(false);
                            break;
                        case 11:
                            this.count = 47;
                            repaint();
                            break;
                    }
                    break;
                case 8:
                    switch(this.yrow){
                        case 7:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), null));
                            JLabel no = new JLabel("Mid");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), null));
                            JLabel no1 = new JLabel("Mid");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 9:
                    switch(this.yrow){
                        case 2:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(9));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("I");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no = new JLabel("DL");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 10:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes1 = new Icon(targetpiece.get(10));
                            // shapes1.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes1);
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("J");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no1 = new JLabel("DR");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 10:
                    switch(this.yrow){
                        case 0:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Down");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 7:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(11));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("K");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no1 = new JLabel("UR");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 11:
                    switch(this.yrow){
                        case 12:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(12));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("L");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no = new JLabel("UR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 13:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(12));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("M");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no1 = new JLabel("DL");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 12:
                    switch(this.yrow){
                        case 3:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(12));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("N");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no = new JLabel("DR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 6:
                            this.count = 47;
                            repaint();
                            break;
                        case 9:
                            this.count = 48;
                            repaint();
                            break;
                        case 15:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no3 = new JLabel("Down");
                            this.add(no3);
                            no3.setVisible(false);
                            break;
                    }
                    break;
                case 13:
                    switch(this.yrow){
                        case 1:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Down");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 3:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(14));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("O");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no1 = new JLabel("UL");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                        case 9:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(14));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("P");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no2 = new JLabel("UL");
                            this.add(no2);
                            no2.setVisible(false);
                            break;
                    }
                    break;
                case 14:
                    switch(this.yrow){
                        case 5:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(15));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            for(Component c: this.getComponents()){
                                if(c instanceof JLabel){
                                    JLabel j = (JLabel)c;
                                    j.setText("Q");
                                    j.setVisible(true);
                                }
                            }
                            JLabel no = new JLabel("UR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 11:
                            this.count = 48;
                            repaint();
                            break;
                    }
                    break;
                case 15:
                    switch(this.yrow){
                        case 6:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Right");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 10:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no1 = new JLabel("Right");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                default: break;
            }
        }
        if(visual == 2){ // If "Text and Color" is chosen, it will feature both colored shapes with text
            switch(this.xcolumn){
                case 0:
                    switch(this.yrow){
                        case 5:
                            this.setBorder(new CompoundBorder(new MatteBorder(0,0,0,3,Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Right"); 
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(0,0,0,3,Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no1 = new JLabel("Right");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 1:
                    switch(this.yrow){
                        case 4:
                            this.count = 47;
                            repaint();
                            break;
                        case 12:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            this.count = targetpiece.get(0) + 25;
                            repaint();
                            JLabel no1 = new JLabel("UL");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 2:
                    switch(this.yrow){
                        case 14:
                            this.count = 48;
                            repaint();
                            break;
                        case 15:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no1 = new JLabel("Down");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 3:
                    switch(this.yrow){
                        case 1:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            this.count = targetpiece.get(1) + 25;
                            repaint();
                            JLabel no = new JLabel("UL");
                            this.add(no);
                            no.setVisible(false);
                            break;
                    }
                    break;
                case 4:
                    switch(this.yrow){
                        case 6:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(5));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(2) + 25;
                            repaint();
                            JLabel no = new JLabel("DR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            this.count = targetpiece.get(3) + 25;
                            repaint();
                            JLabel no1 = new JLabel("DR");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 5:
                    switch(this.yrow){
                        case 0:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Down");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 13:
                            this.setBorder(new CompoundBorder(new MatteBorder(0,3,3,0,Color.BLACK), new LineBorder(Color.lightGray)));
                            this.count = targetpiece.get(4) + 25;
                            repaint();
                            JLabel no1 = new JLabel("DL");
                            this.add(no1);
                            no1.setVisible(false);
                    }
                    break;
                case 6:
                    switch(this.yrow){
                        case 2:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(7));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(5) + 25;
                            repaint();
                            JLabel no = new JLabel("UR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 3:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(7));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(6) + 25;
                            repaint();
                            JLabel no1 = new JLabel("DL");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                        case 13:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes1 = new Icon(targetpiece.get(8));
                            // shapes1.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes1);
                            this.count = targetpiece.get(7) + 25;
                            repaint();
                            JLabel no2 = new JLabel("UR");
                            this.add(no2);
                            no2.setVisible(false);
                            break;
                    }
                    break;
                case 7:
                    switch(this.yrow){
                        case 5:
                            this.count = 48;
                            repaint();
                            break;
                        case 7:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), null));
                            JLabel no1 = new JLabel("Mid");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), null));
                            JLabel no2 = new JLabel("Mid");
                            this.add(no2);
                            no2.setVisible(false);
                            break;
                        case 11:
                            this.count = 47;
                            repaint();
                            break;
                    }
                    break;
                case 8:
                    switch(this.yrow){
                        case 7:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), null));
                            JLabel no = new JLabel("Mid");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 8:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), null));
                            JLabel no1 = new JLabel("Mid");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 9:
                    switch(this.yrow){
                        case 2:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(9));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(8) + 25;
                            repaint();
                            JLabel no = new JLabel("DL");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 10:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes1 = new Icon(targetpiece.get(10));
                            // shapes1.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes1);
                            this.count = targetpiece.get(9) + 25;
                            repaint();
                            JLabel no1 = new JLabel("DR");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 10:
                    switch(this.yrow){
                        case 0:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Down");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 7:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(11));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(10) + 25;
                            repaint();
                            JLabel no1 = new JLabel("UR");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 11:
                    switch(this.yrow){
                        case 12:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(12));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(11) + 25;
                            repaint();
                            JLabel no = new JLabel("UR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 13:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 3, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(12));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(12) + 25;
                            repaint();
                            JLabel no1 = new JLabel("DL");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                case 12:
                    switch(this.yrow){
                        case 3:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(12));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(13) + 25;
                            repaint();
                            JLabel no = new JLabel("DR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 6:
                            this.count = 47;
                            repaint();
                            break;
                        case 9:
                            this.count = 48;
                            repaint();
                            break;
                        case 15:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no3 = new JLabel("Down");
                            this.add(no3);
                            no3.setVisible(false);
                            break;
                    }
                    break;
                case 13:
                    switch(this.yrow){
                        case 1:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Down");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 3:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(14));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(14) + 25;
                            repaint();
                            JLabel no1 = new JLabel("UL");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                        case 9:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 3, 0, 0, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(14));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(15) + 25;
                            repaint();
                            JLabel no2 = new JLabel("UL");
                            this.add(no2);
                            no2.setVisible(false);
                            break;
                    }
                    break;
                case 14:
                    switch(this.yrow){
                        case 5:
                            this.setBorder(new CompoundBorder(new MatteBorder(3, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            // Icon shapes = new Icon(targetpiece.get(15));
                            // shapes.setPreferredSize(new Dimension(30,30));
                            // this.add(shapes);
                            this.count = targetpiece.get(16) + 25;
                            repaint();
                            JLabel no = new JLabel("UR");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 11:
                            this.count = 48;
                            repaint();
                            break;
                    }
                    break;
                case 15:
                    switch(this.yrow){
                        case 6:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no = new JLabel("Right");
                            this.add(no);
                            no.setVisible(false);
                            break;
                        case 10:
                            this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 3, Color.BLACK), new LineBorder(Color.lightGray)));
                            JLabel no1 = new JLabel("Right");
                            this.add(no1);
                            no1.setVisible(false);
                            break;
                    }
                    break;
                default: break;
            }
        }
    }

    public int getx(){
        return this.xcolumn;
    }

    public int gety(){
        return this.yrow;
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        switch(count){
            case 0:
                break;
            case 1: 
                g.setColor(Color.blue);
                g.fillPolygon(new int[] {10, 25, 40}, new int[] {40, 5, 40}, 3);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("A");
                        }
                    }
                }
                break;
            case 2:
                g.setColor(Color.blue);
                g.fillRect(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("B");
                        }
                    }
                }
                break;
            case 3:
                g.setColor(Color.blue);
                g.fillOval(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("C");
                        }
                    }
                }
                break;
            case 4:
                g.setColor(Color.blue);
                g.fillRoundRect(8, 10, 30, 30, 10, 10);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("D");
                        }
                    }
                }
                break;
            case 5:
                g.setColor(Color.red);
                g.fillRect(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("E");
                        }
                    }
                }
                break;
            case 6:
                g.setColor(Color.red);
                g.fillPolygon(new int[] {10, 25, 40}, new int[] {40, 5, 40}, 3);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("F");
                        }
                    }
                }
                break;
            case 7:
                g.setColor(Color.red);
                g.fillOval(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("G");
                        }
                    }
                }
                break;
            case 8:
                g.setColor(Color.red);
                g.fillRoundRect(8, 10, 30, 30, 10, 10);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("H");
                        }
                    }
                }
                break;
            case 9:
                g.setColor(Color.black);
                g.fillRect(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("I");
                        }
                    }
                }
                break;
            case 10:
                g.setColor(Color.black);
                g.fillOval(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("J");
                        }
                    }
                }
                break;
            case 11:
                g.setColor(Color.black);
                g.fillRoundRect(8,10,30,30,10,10);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("K");
                        }
                    }
                }
                break;
            case 12:
                g.setColor(Color.black);
                g.fillPolygon(new int[] {10, 25, 40}, new int[] {40, 5, 40}, 3);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("L");
                        }
                    }
                }
                break;
            case 13:
                g.setColor(new Color(0,102,0));
                g.fillOval(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("M");
                        }
                    }
                }
                break;
            case 14:
                g.setColor(new Color(0,102,0));
                g.fillRect(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("N");
                        }
                    }
                }
                break;
            case 15:
                g.setColor(new Color(0,102,0));
                g.fillPolygon(new int[] {10, 25, 40}, new int[] {40, 5, 40}, 3);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("O");
                        }
                    }
                }
                break;
            case 16:
                g.setColor(new Color(0,102,0));
                g.fillRoundRect(8,10,30,30,10,10);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("P");
                        }
                    }
                }
                break;
            case 17:
                g.setColor(Color.ORANGE);
                g.fillRect(10,10,30,30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("Q");
                        }
                    }
                }
                break;
            case 18:
                g.setColor(Color.BLUE);
                g.fillRect(15,10,15,25);
                g.fillRect(5,30,35,10);
                break;
            case 19:
                g.setColor(Color.RED);
                g.fillRect(15,10,15,25);
                g.fillRect(5,30,35,10);
                break;
            case 20:
                g.setColor(Color.BLACK);
                g.fillRect(15,10,15,25);
                g.fillRect(5,30,35,10);
                break;
            case 21:
                g.setColor(new Color(0,102,0));
                g.fillRect(15,10,15,25);
                g.fillRect(5,30,35,10);
                break;
            case 22:
                g.drawString("Alpha", 5, 15);
                break;
            case 23:
                g.drawString("Beta", 5, 15);
                break;
            case 24:
                g.drawString("Charlie", 5, 15);
                break;
            case 25:
                g.drawString("Delta", 5, 15);
                break;
            case 26: 
                g.setColor(Color.blue);
                g.fillPolygon(new int[] {10, 25, 40}, new int[] {40, 5, 40}, 3);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("A");
                            j.setForeground(Color.CYAN);
                            j.setVisible(true);
                        }
                    }
                }
                break;
            case 27:
                g.setColor(Color.blue);
                g.fillRect(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("B");
                            j.setForeground(Color.CYAN);
                            j.setVisible(true);
                        }
                    }
                }
                break;
            case 28:
                g.setColor(Color.blue);
                g.fillOval(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("C");
                            j.setForeground(Color.CYAN);
                            j.setVisible(true);
                        }
                    }
                }
                break;
            case 29:
                g.setColor(Color.blue);
                g.fillRoundRect(8, 10, 30, 30, 10, 10);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("D");
                            j.setForeground(Color.CYAN);
                            j.setVisible(true);
                        }
                    }
                }
                break;
            case 30:
                g.setColor(Color.red);
                g.fillRect(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("E");
                            j.setForeground(Color.CYAN);
                            j.setVisible(true);
                        }
                    }
                }
                break;
            case 31:
                g.setColor(Color.red);
                g.fillPolygon(new int[] {10, 25, 40}, new int[] {40, 5, 40}, 3);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("F");
                            j.setForeground(Color.CYAN);
                            j.setVisible(true);
                        }
                    }
                }
                break;
            case 32:
                g.setColor(Color.red);
                g.fillOval(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("G");
                            j.setForeground(Color.CYAN);
                            j.setVisible(true);
                        }
                    }
                }
                break;
            case 33:
                g.setColor(Color.red);
                g.fillRoundRect(8, 10, 30, 30, 10, 10);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("H");
                            j.setForeground(Color.CYAN);
                            j.setVisible(true);
                        }
                    }
                }
                break;
            case 34:
                g.setColor(Color.black);
                g.fillRect(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("I");
                            j.setForeground(Color.CYAN);
                            j.setVisible(true);
                        }
                    }
                }
                break;
            case 35:
                g.setColor(Color.black);
                g.fillOval(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("J");
                            j.setForeground(Color.CYAN);
                            j.setVisible(true);
                        }
                    }
                }
                break;
            case 36:
                g.setColor(Color.black);
                g.fillRoundRect(8,10,30,30,10,10);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("K");
                            j.setForeground(Color.CYAN);
                            j.setVisible(true);
                        }
                    }
                }
                break;
            case 37:
                g.setColor(Color.black);
                g.fillPolygon(new int[] {10, 25, 40}, new int[] {40, 5, 40}, 3);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("L");
                            j.setForeground(Color.CYAN);
                            j.setVisible(true);
                        }
                    }
                }
                break;
            case 38:
                g.setColor(new Color(0,102,0));
                g.fillOval(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("M");
                            j.setForeground(Color.CYAN);
                            j.setVisible(true);
                        }
                    }
                }
                break;
            case 39:
                g.setColor(new Color(0,102,0));
                g.fillRect(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("N");
                            j.setForeground(Color.CYAN);
                            j.setVisible(true);
                        }
                    }
                }
                break;
            case 40:
                g.setColor(new Color(0,102,0));
                g.fillPolygon(new int[] {10, 25, 40}, new int[] {40, 5, 40}, 3);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("O");
                            j.setForeground(Color.CYAN);
                            j.setVisible(true);
                        }
                    }
                }
                break;
            case 41:
                g.setColor(new Color(0,102,0));
                g.fillRoundRect(8,10,30,30,10,10);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("P");
                            j.setForeground(Color.CYAN);
                            j.setVisible(true);
                        }
                    }
                }
                break;
            case 42:
                g.setColor(Color.ORANGE);
                g.fillRect(10,10,30,30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("Q");
                            j.setForeground(Color.CYAN);
                            j.setVisible(true);
                        }
                    }
                }
                break;
            case 43:
                g.setColor(Color.BLUE);
                g.fillRect(15,10,15,25);
                g.fillRect(5,30,35,10);
                g.drawString("Alpha", 5, 10);
                break;
            case 44:
                g.setColor(Color.RED);
                g.fillRect(15,10,15,25);
                g.fillRect(5,30,35,10);
                g.drawString("Beta", 5, 10);
                break;
            case 45:
                g.setColor(Color.BLACK);
                g.fillRect(15,10,15,25);
                g.fillRect(5,30,35,10);
                g.drawString("Charlie", 5, 10);
                break;
            case 46:
                g.setColor(new Color(0,102,0));
                g.fillRect(15,10,15,25);
                g.fillRect(5,30,35,10);
                g.drawString("Delta", 5, 10);
                break;
            case 47:
                g.setColor(Color.black);
                g.drawLine(3,40,40,3);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("BSlash");
                        }
                    }
                }
                break;
            case 48:
                g.setColor(Color.black);
                g.drawLine(3,3,40,40);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        String v = j.getText();
                        if(v.equals("Yes")){
                            j.setText("FSlash");
                        }
                    }
                }
                break;
            default: break;
        }
    }


}