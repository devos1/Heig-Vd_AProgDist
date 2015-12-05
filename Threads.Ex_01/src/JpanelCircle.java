import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

// PANEL QUI SERA UTILISE DANS LA FENETRE
public class JpanelCircle extends JPanel implements Runnable{
	
	// CONSTANTE
	private final int DELAY = 500;

	// VARIABLES
	private static final long serialVersionUID = 1L;
	private int rayon = 50;
	private boolean showRond = true;
	//private boolean startStop = true;
	// MUST be volatile to ensure changes visible to other threads.
	protected volatile boolean stop;

	// CONSTRUCTEUR
	public JpanelCircle(){
		setPreferredSize(new Dimension(200,200));		
	}
	
	
	public void paintComponent(Graphics g) {
		if (showRond) {
			super.paintComponent(g);
			g.drawOval(getWidth()/2-rayon, getHeight()/2-rayon, 2*rayon, 2*rayon);
			g.setColor(Color.GREEN);
			g.fillOval(getWidth()/2-rayon, getHeight()/2-rayon, 2*rayon, 2*rayon);
		}
	}



	@Override
	public void run() {
		long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (!stop) {
        	if (showRond) {
				showRond = false;
			}else {
				showRond = true;
			}
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0)
                sleep = 1;
            
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            	Thread.currentThread().interrupt();
            	break;
            }

            beforeTime = System.currentTimeMillis();
        }
		
	};
		
	// GETTERS AND SETTER
	/**
	 * @param showRond the showRond to set
	 */
	public void setStop(boolean s) {
		this.stop = s;
	}

	/**
	 * @return the startStop
	 */
	public boolean isStop() {
		return stop;
	}
}
