import java.awt.*;
import javax.swing.*;
// Class for drawing
public class Icon extends JPanel{
    public int count;
    
    public Icon(int count){
        super.setPreferredSize(new Dimension(50,50));
        this.count = count;
        this.setBackground(Color.WHITE);
        this.add(new JLabel("Yes"));
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        // Draw different shapes and colours according to the count passed through as parameter
        // FillPolygon = Triangle
        // FillOval = Circle
        // FillRect = Squares
        switch(this.count){
            case 0: 
                g.setColor(Color.blue);
                g.fillPolygon(new int[] {10, 25, 40}, new int[] {40, 5, 40}, 3);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        j.setText("A");
                        j.setVisible(false);
                    }
                }
                break;
            case 1:
                g.setColor(Color.blue);
                g.fillRect(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        j.setText("B");
                        j.setVisible(false);
                    }
                }
                break;
            case 2:
                g.setColor(Color.blue);
                g.fillOval(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        j.setText("C");
                        j.setVisible(false);
                    }
                }
                break;
            case 3:
                g.setColor(Color.blue);
                g.fillRoundRect(8, 10, 30, 30, 10, 10);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        j.setText("D");
                        j.setVisible(false);
                    }
                }
                break;
            case 4:
                g.setColor(Color.red);
                g.fillRect(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        j.setText("E");
                        j.setVisible(false);
                    }
                }
                break;
            case 5:
                g.setColor(Color.red);
                g.fillPolygon(new int[] {10, 25, 40}, new int[] {40, 5, 40}, 3);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        j.setText("F");
                        j.setVisible(false);
                    }
                }
                break;
            case 6:
                g.setColor(Color.red);
                g.fillOval(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        j.setText("G");
                        j.setVisible(false);
                    }
                }
                break;
            case 7:
                g.setColor(Color.red);
                g.fillRoundRect(8, 10, 30, 30, 10, 10);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        j.setText("H");
                        j.setVisible(false);
                    }
                }
                break;
            case 8:
                g.setColor(Color.black);
                g.fillRect(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        j.setText("I");
                        j.setVisible(false);
                    }
                }
                break;
            case 9:
                g.setColor(Color.black);
                g.fillOval(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        j.setText("J");
                        j.setVisible(false);
                    }
                }
                break;
            case 10:
                g.setColor(Color.black);
                g.fillRoundRect(8,10,30,30,10,10);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        j.setText("K");
                        j.setVisible(false);
                    }
                }
                break;
            case 11:
                g.setColor(Color.black);
                g.fillPolygon(new int[] {10, 25, 40}, new int[] {40, 5, 40}, 3);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        j.setText("L");
                        j.setVisible(false);
                    }
                }
                break;
            case 12:
                g.setColor(new Color(0,102,0));
                g.fillOval(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        j.setText("M");
                        j.setVisible(false);
                    }
                }
                break;
            case 13:
                g.setColor(new Color(0,102,0));
                g.fillRect(10, 10, 30, 30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        j.setText("N");
                        j.setVisible(false);
                    }
                }
                break;
            case 14:
                g.setColor(new Color(0,102,0));
                g.fillPolygon(new int[] {10, 25, 40}, new int[] {40, 5, 40}, 3);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        j.setText("O");
                        j.setVisible(false);
                    }
                }
                break;
            case 15:
                g.setColor(new Color(0,102,0));
                g.fillRoundRect(8,10,30,30,10,10);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        j.setText("P");
                        j.setVisible(false);
                    }
                }
                break;
            case 16:
                g.setColor(Color.ORANGE);
                g.fillRect(10,10,30,30);
                for(Component c: this.getComponents()){
                    if(c instanceof JLabel){
                        JLabel j = (JLabel)c;
                        j.setText("Q");
                        j.setVisible(false);
                    }
                }
                break;
            default: break;
        }
    }
    public int getcount(){
        return this.count;
    }
}