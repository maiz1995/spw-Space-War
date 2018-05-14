package f2.spw;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.Timer;

public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;

private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
private ArrayList<Enemy2> enemies2 = new ArrayList<Enemy2>();
private SpaceShip v;
private SpaceShip2 v2;
private Timer timer;
private long score = 0;
private double difficulty = 0.01;
private double tm = 0;

	public GameEngine(GamePanel gp, SpaceShip v, SpaceShip2 v2) {
		this.gp = gp;
		this.v = v;	
		this.v2 = v2;	
gp.sprites.add(v);	
gp.sprites.add(v2);

timer = new Timer(100, new ActionListener() {
	
		@Override
				public void actionPerformed(ActionEvent arg0) {
					process();
					tm++;
					if(tm % 60 == 0){
						 difficulty += 0.1;
					}
					
				}
			});
			timer.setRepeats(true);
}
	public void start(){
			timer.start();
		}
	private void generateEnemy(){
			int x = (int)(Math.random()*690);
			int r = (int)(Math.random()*2);
			if(r==1){
				Enemy e1 = new Enemy(x, 30);
				gp.sprites.add(e1);
				enemies.add(e1);
			}else {
				Enemy2 e2 = new Enemy2(x, 200);
				gp.sprites.add(e2);
				enemies2.add(e2);
			}
			
		}
	private void process(){
			if(Math.random() < difficulty){
				generateEnemy();
			}
	Iterator<Enemy> e_iter = enemies.iterator();
	while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
	if(!e.isAlive()){
			e_iter.remove();
			gp.sprites.remove(e);
			score += 100;
		}
	
	}
	Iterator<Enemy2> e2_iter = enemies2.iterator();
	while(e2_iter.hasNext()){
			Enemy2 e2 = e2_iter.next();
			e2.proceed();
	if(!e2.isAlive()){
			e2_iter.remove();
			gp.sprites.remove(e2);
			score += 100;
			
		}
	
	}
	gp.updateGameUI(this);

	Rectangle2D.Double vr = v.getRectangle();
	Rectangle2D.Double vr2 = v2.getRectangle();
			Rectangle2D.Double er;
				for(Enemy e : enemies){
					er = e.getRectangle();
					if(er.intersects(vr)){
						v.setAlive();
						die();
						return;
					}
					if(er.intersects(vr2)){
						v2.setAlive();
						die();
						return;
					}
				}
				for(Enemy2 e : enemies2){

					er = e.getRectangle();
					if(er.intersects(vr)){
						v.setAlive();
						die();
						return;
					}
					if(er.intersects(vr2)){
						v2.setAlive();
						die();
						return;
					}
				}
		}



	public void die(){
			if(!v.getAlive() && !v2.getAlive())
				timer.stop();
		}

void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			if(v.getAlive())
				v.move(-1);
			break;
		case KeyEvent.VK_RIGHT:
			if(v.getAlive())
				v.move(1);
			break;
		case KeyEvent.VK_W:
			difficulty += 0.1;
			break;
		case KeyEvent.VK_A:
			if(v2.getAlive())
				v2.move(-1);
			break;
		case KeyEvent.VK_D:
			if(v2.getAlive())
				v2.move(1);
			break;
		}
	}
	public long getScore(){
		return score;
	}
@Override
        public void keyPressed(KeyEvent e) {
		controlVehicle(e);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}
	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing
	}

}
