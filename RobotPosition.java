
import java.util.*;
import java.awt.*;
import javax.swing.*;
// This class is created for users to be able to click on individual robot pieces and move them accordingly
public class RobotPosition extends JPanel{
    public int x,y,rcount;
    public RobotPosition(int x, int y){
        super();
        this.setSize(30,30);
        this.setBackground(Color.white);
        this.x = x;
        this.y = y;
    }
    public void colourrobot(int count){ // "Color" option
        this.rcount = count;
        repaint();
    }
    public int returnrobotx(){
        return this.x;
    }
    public int returnroboty(){
        return this.y;
    }
    @Override
    public void paintComponent(Graphics g){ // Setting up the color and shape for each robot piece
        super.paintComponent(g);
        switch(this.rcount){
            case 0:
                g.setColor(Color.BLUE);
                g.fillRect(15,10,15,25);
                g.fillRect(5,30,35,10);
                break;
            case 1:
                g.setColor(Color.RED);
                g.fillRect(15,10,15,25);
                g.fillRect(5,30,35,10);
                break;
            case 2:
                g.setColor(Color.BLACK);
                g.fillRect(15,10,15,25);
                g.fillRect(5,30,35,10);
                break;
            case 3:
                g.setColor(new Color(0,102,0));
                g.fillRect(15,10,15,25);
                g.fillRect(5,30,35,10);
                break;
            case 4:
                g.drawString("Alpha", 25, 25);
                break;
            case 5:
                g.drawString("Beta", 25, 25);
                break;
            case 6:
                g.drawString("Charlie", 25, 25);
                break;
            case 7:
                g.drawString("Delta", 25, 25);
                break;
            case 8:
                g.setColor(Color.BLUE);
                g.fillRect(15,10,15,25);
                g.fillRect(5,30,35,10);
                g.drawString("Alpha",50, 20);
                break;
            case 9:
                g.setColor(Color.RED);
                g.fillRect(15,10,15,25);
                g.fillRect(5,30,35,10);
                g.drawString("Beta", 50, 20);
                break;
            case 10:
                g.setColor(Color.BLACK);
                g.fillRect(15,10,15,25);
                g.fillRect(5,30,35,10);
                g.drawString("Charlie", 50, 20);
                break;
            case 11:
                g.setColor(new Color(0,102,0));
                g.fillRect(15,10,15,25);
                g.fillRect(5,30,35,10);
                g.drawString("Delta", 50, 20);
                break;
            default: break;
        }
    }
}