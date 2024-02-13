package main;

import castles.CastlePanel;
import castles.SuperCastle;
import entity.Player;
import object.SuperObject;
import tile.TileManager;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {

    //SCREEN SETTINGS
    public final int tileSize = 48;

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    public Random random = new Random();

    public final int screenWidth = (int) toolkit.getScreenSize().getWidth();
    public final int screenHeight = (int) toolkit.getScreenSize().getHeight();
    public final int maxWorldCol = screenWidth/tileSize;
    public final int maxWorldRow = screenHeight/tileSize;

    public MouseHandler mouseHandler = new MouseHandler(this);
    public MouseMotionHandler mouseMotionHandler = new MouseMotionHandler(this);
    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    public PathFinding pathFinding = new PathFinding(this, tileManager);
    public Player player = new Player(this, mouseHandler);
    public SuperObject[] obj = new SuperObject[10];
    public SuperObject[] objInv = new SuperObject[2];
    public SuperCastle[] cas = new SuperCastle[1];
    public CastlePanel castlePanel = new CastlePanel(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    Thread gameThread;

    public int FPS = 60;

    //mouse management
    public int mouseX;
    public int mouseY;
    public int clickCounter = 0;
    public int oldX, oldY;


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseMotionHandler);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        System.out.println(screenWidth + "x" + screenHeight);
    }

    public void setUpGame(){
        assetSetter.setObject();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        // Game loop
        long lastTime = System.nanoTime();
        double delta = 0;
        double nsPerFrame = 1.0 / FPS * 1e9; // Czas na jedną klatkę w nanosekundach

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / nsPerFrame;
            lastTime = currentTime;

            while (delta >= 1) {
                update();
                repaint();
                delta--;
            }

            // Dynamiczne dostosowanie docelowego FPS
            long elapsedNs = System.nanoTime() - currentTime;
            double actualFPS = 1.0 / (elapsedNs / 1e9);

            // Dostosowanie docelowego FPS w oparciu o aktualne FPS
            if (actualFPS > FPS) {
                FPS++;
            } else {
                FPS--;
            }

            // Ograniczenie wartości FPS do odpowiedniego zakresu
            FPS = Math.max(10, Math.min(FPS, 60));

            // Dynamiczne dostosowanie czasu na jedną klatkę
            nsPerFrame = 1.0 / FPS * 1e9;

            // Usypianie wątku na krótko, aby nie obciążać procesora
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void update(){
        player.update();

    }
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);

        //tile
        tileManager.draw(g2);

        //object
        for (SuperObject superObject : obj) {
            if (superObject != null)
                superObject.draw(g2, this);
        }

        //castles
        for (SuperCastle ca : cas) {
            if (ca != null)
                ca.draw(g2, this);
        }

        //player
        player.draw(g2);

        //path
        pathFinding.draw(g2);

        //panel for resources
        g2.setColor(new Color(100,75,60,130));
        g2.fillRoundRect(-1, (maxWorldRow-1)*tileSize, 27*tileSize, tileSize, 45,45);

        if(player.inTheTown){//do zmiany poteznie xD
            castlePanel.draw(g2);
        }

        //current resources
        for (SuperObject superObject : objInv) {
            if (superObject != null)
                superObject.draw(g2, this);
        }
        g2.setColor(Color.white);
        g2.setFont(new Font("Arial", Font.ITALIC, 18));
        g2.drawString("x " + player.wood, tileSize + tileSize/2, (int)(17.75*(double)tileSize));
        g2.drawString("x " + player.gold, 3*tileSize + tileSize/2, (int)(17.75*(double)tileSize));

        //FPSy
        String s = "FPS: " + FPS;
        g2.setColor(Color.BLUE);
        g2.setFont(new Font("Italic", Font.BOLD, 20) );
        g2.drawString(s, screenWidth - g2.getFontMetrics().stringWidth(s), g2.getFontMetrics().getAscent());

        g2.dispose();
    }
}
