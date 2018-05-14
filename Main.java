package f2.spw;
import java.awt.BorderLayout;
import javax.swing.JFrame;
public class Main {
	public static void main(String[] args){

		JFrame frame = new JFrame("Space War");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(710, 630);
		frame.getContentPane().setLayout(new BorderLayout());

		
		SpaceShip v = new SpaceShip(600, 550, 20, 20);
		SpaceShip2 v2 = new SpaceShip2(70, 550, 20, 20);

		GamePanel gp = new GamePanel();
		GameEngine engine = new GameEngine(gp, v, v2);
		frame.addKeyListener(engine);
		frame.getContentPane().add(gp, BorderLayout.CENTER);
		frame.setVisible(true);
		engine.start();
	}
}
