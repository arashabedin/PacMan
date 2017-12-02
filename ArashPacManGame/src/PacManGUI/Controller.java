package PacManGUI;

import PacManLogic.*;
import PacManLogic.Ghosts.BlueGhost;
import PacManLogic.Ghosts.OrangeGhost;
import PacManLogic.Ghosts.PinkGhost;
import PacManLogic.Ghosts.RedGhost;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Arash Abedin on 9-03-2017.
 */

public class Controller {


    @FXML
    Label labelStatus;
    @FXML
    Canvas canvas;

    @FXML
    Label myPoints = new Label();

    @FXML
    Button restartBtn = new Button();

    @FXML
    Label blueGhostSearches = new Label();

    @FXML
    Label pinkGhostSearches = new Label();

    @FXML
    Label orangeGhostSearches = new Label();

    @FXML
    Label redGhostSearches = new Label();



    private SceneInfo sceneInfo;
    private float refreshRate = 50;
    private KeyCode keyPressed = KeyCode.ALT;

    ArrayList < GameObject > gameObjects = new ArrayList < GameObject > ();
    private Player player;

    private MapOutline map = new MapOutline();
    private Biscuits biscuits;
    private PinkGhost pinkGhost;
    private BlueGhost blueGhost;
    private OrangeGhost orangeGhost;
    private RedGhost redGhost;


    MazePlayGround mazePlayGround = new MazePlayGround(javafx.scene.paint.Color.rgb(0, 0, 0), 1, 1, player, map);

    public void addStuff() {

        sceneInfo = new SceneInfo(canvas);
        player = new Player(new Point(20, 60), map);

        biscuits = new Biscuits(player, map);
        biscuits.setTotalEatenBiscuits(0);
        biscuits.setTotalEatenBigBiscuits(0);
        pinkGhost = new PinkGhost(new Point(280, 300), map, player, biscuits);
        blueGhost = new BlueGhost(new Point(220, 300), map, player, biscuits);
        orangeGhost = new OrangeGhost(new Point(260, 260), map, player, biscuits);
        redGhost = new RedGhost(new Point(260, 300), map, player, biscuits);

        gameObjects.add(biscuits);
        gameObjects.add(player);
        gameObjects.add(pinkGhost);
        gameObjects.add(blueGhost);
        gameObjects.add(orangeGhost);
        gameObjects.add(redGhost);
        gameObjects.add(mazePlayGround);




    }
    public void restartGame() {
        pinkGhost.setEscapeTimeCount(0);
        pinkGhost.setEscape(false);
        gameObjects = new ArrayList < GameObject > ();
        keyPressed = KeyCode.ALT;


        addStuff();

    }
    public void initialize() {
        restartBtn.setOnAction(new EventHandler < ActionEvent > () {
            @Override
            public void handle(ActionEvent event) {
                restartGame();

            }
        });



        addStuff();

        new AnimationTimer() {
            long lastUpdate;
            public void handle(long now) {
                if (now > lastUpdate + refreshRate * 1690000) {
                    lastUpdate = now;
                    update(now);
                }
            }
        }.start();

    }



    public void keyPressed(KeyCode keyCode) {
        this.keyPressed = keyCode;

    }

    private void update(long now) {


        for (int i = 0; i < gameObjects.size(); i++) {

            gameObjects.get(i).update(keyPressed);

        }

        // keyPressed = KeyCode.ALT;
        if (pinkGhost.isEscape() == false)
            if (((player.getX() == pinkGhost.getX()) && (player.getY() == pinkGhost.getY())) || ((player.getX() == blueGhost.getX()) && (player.getY() == blueGhost.getY())) || ((player.getX() == orangeGhost.getX()) && (player.getY() == orangeGhost.getY())) || ((player.getX() == redGhost.getX()) && (player.getY() == redGhost.getY())))

            {
                System.out.println("You lost");
                restartGame();
            }
        drawCanvas();
        myPoints.setText("Points : " + String.valueOf((biscuits.getTotalEatenBiscuits() * 100) - 100));
        blueGhostSearches.setText((blueGhost.getTotalSearched() > 0 ? String.valueOf(blueGhost.getTotalSearched()) : "Random mode"));
        pinkGhostSearches.setText((pinkGhost.getTotalSearched() > 0 ? String.valueOf(pinkGhost.getTotalSearched()) : "Random mode"));
        orangeGhostSearches.setText((orangeGhost.getTotalSearched() > 0 ? String.valueOf(orangeGhost.getTotalSearched()) : "Random mode"));
        redGhostSearches.setText((redGhost.getTotalSearched() > 0 ? String.valueOf(redGhost.getTotalSearched()) : "Random mode"));
    }


    /**
     * Draw the canvas - used in the gameloop
     */
    private void drawCanvas() {
        GraphicsContext g = canvas.getGraphicsContext2D();

        // clear canvas
        g.clearRect(0, 0, sceneInfo.getWidth() * sceneInfo.getFieldWidth(), sceneInfo.getHeight() * sceneInfo.getFieldHeight());

        // draw gameObjects
        for (GameObject item: gameObjects) {
            item.draw(g, sceneInfo);
        }

        // biscuits.clear(g,sceneInfo);
    }






}