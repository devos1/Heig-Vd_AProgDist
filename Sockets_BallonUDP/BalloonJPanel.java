
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BalloonJPanel extends JPanel implements UserInterface  {
    private BalloonPlayer balloonPlayer;
     
    public BalloonJPanel(BalloonPlayer balloonPlayer) {
    	this.balloonPlayer=balloonPlayer;
    	balloonPlayer.setUI(this);
        setPreferredSize(new Dimension(200,200));
    }
    
    public void paintComponent (Graphics g) {
    	super.paintComponent(g);
    	Balloon balloon = balloonPlayer.getBalloon();
		if (balloon != null) {
    	int rayon = balloon.getRayon();
        g.drawOval(getWidth()/2-rayon, getHeight()/2-rayon, 2*rayon, 2*rayon);
		}
    }

	@Override
	public void display() {
		repaint();	
	}

}