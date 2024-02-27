package main;

import castles.CastlePanel;
import castles.SuperCastle;
import entity.Player;
import object.SuperObject;
import tile.TileManager;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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

    //OBJECTS
    public MouseHandler mouseHandler = new MouseHandler(this);
    public MouseMotionHandler mouseMotionHandler = new MouseMotionHandler(this);
    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    public PathFinding pathFinding = new PathFinding(this, tileManager);
    public Player player = new Player(this, mouseHandler);
    public ArrayList<SuperObject> obj = new ArrayList<SuperObject>();
    public ArrayList<SuperObject> objInv = new ArrayList<SuperObject>();
    public SuperCastle[] cas = new SuperCastle[1];
    public CastlePanel castlePanel = new CastlePanel(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    public int FPS = 60;

    //mouse management
    public int mouseX;
    public int mouseY;
    public int clickCounter = 0;
    public int oldX, oldY;

    //GAMESTATES
    public int gameState;
    public final int pauseState = 0;
    public final int playState = 1;
    public final int castleState = 2;
    public final int battleState = 3;
    public final int settingsState = 4;
    public final int menuState = 5;

    //OTHERS
    int m = 0;
    //public boolean finishedCampaign = false; nie do konca pamietam co tu rozkmienialem
    public boolean playerMoved = false;
    public int day = 1, week = 1, month = 1;

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
        player.countIncome();
        gameState = playState;
    }

    public void startGameThread(){
        if(m == 0){
            for (SuperCastle ca : cas) {
                if (ca != null) {
                    initialize(ca.worldX / tileSize, ca.worldY / tileSize);
                }
            }
            m++;
        }
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void initialize(int x, int y){
        tileManager.mapNode[x][y].solid = true;
        tileManager.mapNode[x+1][y].solid = true;
        tileManager.mapNode[x+2][y].solid = true;
        tileManager.mapNode[x][y+1].solid = true;
        tileManager.mapNode[x+2][y+1].solid = true;
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
        if(gameState == playState || gameState == castleState){
            player.update();
        }
        System.out.println("Gamestate: " + gameState);
    }
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);

        //tile
        tileManager.draw(g2);

        //object
        for (SuperObject superObject : obj) {
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
        if(tileManager.finalPathList.size() != 0)
            pathFinding.draw(g2);

        //ui
        ui.draw(g2);

        g2.dispose();
    }
}
