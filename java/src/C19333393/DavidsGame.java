package C19333393;

import java.util.ArrayList;
import ie.tudublin.*;
import processing.core.PImage;

public class DavidsGame extends Visual{    
    boolean[] keys = new boolean[1024];

    Player p;
    Platform platform;
    PImage img;
    BoomBox b;

    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<Rocket> rockets = new ArrayList<>();
    ArrayList<Platform> plats = new ArrayList<>();
    ArrayList<GameObject> children = new ArrayList<>();

    String s;
    float centerX, centerY;

    float[] bands;
    float h;

    int state = 0;
    int timesWon = 0, timesLost = 0;;


    public void settings(){
        //size(1024, 500);
        // Use this to make fullscreen
        fullScreen();
        // Use this to make fullscreen and use P3D for 3D graphics
        //fullScreen(P3D, SPAN); 
    }//end the settings


    public void setup(){
        startMinim();
                
        // Call loadAudio to load an audio file to process 
        loadAudio("AlmostEvil.mp3");
        //getAudioPlayer().play();


        centerX = width/2;
        centerY = height/2;


        spawnPlayerAndBoss();


        img = loadImage("rocket1.png");
        img.resize((int)(img.width*(width*.1f)/1000), (int)(img.height*(width*.1f)/1000));


        level();
        textSize(width*.025f);

    }//end method setting up values and loading images


    void spawnPlayerAndBoss(){

        b = new BoomBox(centerX, centerY, 5, this, 0, (width*.1f)/175, 500);
        children.add(b);

        p = new Player(centerX, centerY, 4, this, (width*.1f)/1000, 25);
        children.add(p);

    }//method to spawn in the boombox boss and the player


    private void spawnRockets() {
        float rocketX, rocketY;

        if(p.getPosition().x < centerX){
            rocketX = random(centerX, width);
        }else{
            rocketX = random(0, centerX);
        }

        rocketY = random(height/2);

        Rocket r = new Rocket(rocketX, rocketY, 4, this, 0, p, 5);

        rockets.add(r);
        children.add(r);
        
    }//method to spwan rockets wherever the player isn't


    void level(){
        float gameHeight = height*.95f;

        float platformsXValues[] = {width*.1f, width*.9f, centerX, width*.05f, width*.95f, centerX, centerX, 0, width};
        float platformsYValues[] = {gameHeight*.25f, gameHeight*.25f, gameHeight, gameHeight*.75f, gameHeight*.75f, height, 0, centerY, centerY};
        float platformsWValues[] = {width*.25f, width*.25f, width, width*.125f, width*.125f, width, width, width*.025f, width*.025f};
        float platformsHValues[] = {height*.02f, height*.02f, gameHeight*.1f, height*.02f, height*.02f, height*.1f, height*.1f, height, height,};


        for(int i =0; i < platformsXValues.length; i++){

            if(i < 5){
                platform = new Platform(platformsXValues[i], platformsYValues[i], platformsWValues[i], platformsHValues[i], this, color(0,0,0));
            }else{
                platform = new Platform(platformsXValues[i], platformsYValues[i], platformsWValues[i], platformsHValues[i], this, color(0, 105, 93));
            }//end else if to dtermine the colour of the platforms
            plats.add(platform);

        }//end for loop to create the level


    }//end method to spawn in level


    public void draw(){
        try{
            calculateFFT(); 
        }catch(VisualException e){
            e.printStackTrace();
        }//end try catch to calculate the FFT

        calculateFrequencyBands(); 
        calculateAverageAmplitude();


        stroke(255);
        strokeWeight(2);


        switch(state){
            case 0:{
                menu();
                break;
            }//this case runs once theplayer pauses the game
            case 1:{
                cursor(CROSS);
                game();
                break;
            }//this case runs once the game is playing
            case 2:{
                gameOver();
                break;
            }//this case runs once the player is defeated
            case 3:{
                gameWon();
            }//this case only rins once the boss is defeated

        }//end switch state


    }//end draw method


    void menu(){
        background(165,214,167);

        rectMode(CENTER);
        fill(0, 105, 93);
        rect(centerX, centerY, centerX, centerY);
        fill(255,255,255);
        textAlign(CENTER);
        text("PRESS P TO PLAY AND TO PAUSE", centerX, centerY);
        text("PRESS R TO RESET", centerX, centerY+ centerY*.2f);

    }//method to show the menu


    void game(){
        background(100,15,30);


        bands = getSmoothedBands();
        h = bands[4];


        for(GameObject o : children){
            o.render();

            if(o instanceof Player){
                Player robot = ((Player)(o));

                if(!robot.isAlive()){
                    timesLost++;
                    state = 2;
                }//if the player isn't alive the game ends and the counter for player deaths is increased

                for(Platform platform : plats){
                    s = platform.checkCollision(robot);

                    switch(s){
                        case "BOTTOM":{
                            if(platform.getC() == color(0, 105, 93)){
                                robot.tapTop(platform.getY() + platform.getH()/2);
                            }//if statement to check if the platform is apart of the border as normal platforms allow you too jump up through them
                            break;
                        }//case to see if the player has hit the bottom of a platform
                        case "TOP":{
                            robot.land(platform.getY() - platform.getH()/2);
                            break;
                        }//case to see if the player has landed on the top of a platform
                        case "LEFT":{
                            robot.hitWall(platform.getX() - platform.getW()/2, s);
                            break;
                        }//case to see if the player has hit the left of a wall or platform
                        case "RIGHT":{
                            robot.hitWall(platform.getX() + platform.getW()/2, s);
                            break;
                        }//case to see if the player has hit the right of a wall or platform

                    }//end switch statement checking collision with platforms
                    
                }//end for loop to iterate through all platforms

            }//end if statement to check the player's collision


            if(o instanceof Bullet){

                for(Platform platform : plats){
                    s = platform.checkCollision(o);
                    if(s != "NO COLLISION"){
                        o.setAlive(false);
                    }//end if to destroy the bullet if it has collided with a platform
                }//end for loop to iterate through all platforms

                for(int j = 0; j < rockets.size(); j++){
                    Rocket ro = rockets.get(j);
                    if(o.collides(ro)){
                        ro.setHp(ro.getHp() - 1);
                        o.setAlive(false);
                    }//end if to check if a bullet has collided with an enemy
                }//end for loop to iterate through all enemies

                if(o.collides(b)){
                    b.setHp(b.getHp() - 1);
                    o.setAlive(false);
                }//when a bullet hists the boom box speaker it takes away some health and will destroy the bullet

            }//end if statement to check if the object is a bullet


            if(o instanceof Rocket){

                if(o.collides(p)){
                    p.setHp(p.getHp() - 1);
                    o.setAlive(false);
                }//end if statement to check if a rocket has hit the player

            }//end if statement to check if the object is a rocket


            if(o instanceof BoomBox){

                if(!o.isAlive()){
                    timesWon++;
                    state = 3;
                }//checking if the boom box has been destroyed

            }//end if statement to check if the object is the boss


        }//end for loop to iterate through all objects 


        for (Platform plat : plats) {
            plat.render();
        }//end for loop to render all platforms


        for(int i = children.size() - 1; i >= 0; i--){
            GameObject o = children.get(i);
            o.update();
        }//for loop to iterate through every game object starting from the last entry to the first


        fill(255, 255, 255);
        text("HEALTH", width*.06f, height *.99f);
        text("AMOUNT OF DEATHS:  "+ timesLost, width*.8f, height *.99f);
        rectMode(CORNER);
        fill(165,214,167);
        rect((width*.06f)*2, height*.9625f, map(p.getHp(), 0, 25, 0, width*.25f), height*0.025f, 5);

        fill(255, 255, 255);
        text("BOOM BOX", width*.08f, height*.0375f);
        rectMode(CORNER);
        fill(255, 0, 0);
        rect((width*.08f)*2, height*.009375f, map(b.getHp(), 0, 500, 0 , width*.75f), height*0.025f, 5);


        if((h)>120 && (frameCount % 30) == 0){
            spawnRockets();
        }//end if statement that will spawn enemies


        if(p.isAlive() && getAmplitude() == 0 && state == 1 && (frameCount % 60) == 0){
            timesWon++;
            state = 3;
        }//sets the game state as won if the player survives the entire song


    }//end game method


    void gameOver(){
        background(100,15,30);


        rectMode(CENTER);
        fill(0, 0, 0);
        rect(centerX, centerY, centerX, centerY);

        fill(255,255,255);
        textAlign(CENTER);

        text("GAME OVER", centerX, centerY);
        text("PRESS R TO TRY AGAIN", centerX, centerY+ centerY*.2f);
        text("TIMES LOST:   "+ timesLost, centerX, centerY- centerY*.2f);

        getAudioPlayer().close();

    }//method runs when the  player fails


    void gameWon(){
        background(165,214,167);


        rectMode(CENTER);
        fill(0, 105, 93);
        rect(centerX, centerY, centerX, centerY);

        fill(255,255,255);
        textAlign(CENTER);


        text("GAME WON", centerX, centerY);
        text("PRESS R TO PLAY AGAIN", centerX, centerY+ centerY*.2f);
        text("TIMES WON:   "+ timesWon, centerX, centerY- centerY*.2f);

        getAudioPlayer().close();

    }//method runs when the  player wins


    void gameReset(){

        for(int i = children.size()-1; i>=0; i--){
            GameObject o = children.get(i);

            if(o instanceof Player || o instanceof BoomBox){
                o.setHp(o.getMaxHp());
                o.getPosition().x = centerX;
                o.getPosition().y = centerY;
                o.setAlive(true);
            }else{
                o.setAlive(false);
                o.update();
            }
            
        }//end for loop to remove all objects

        loadAudio("AlmostEvil.mp3");
        getAudioPlayer().play();
        getAudioPlayer().rewind();


        state = 1;

    }//method to reset the game's values and allow the player another go at the game


    boolean checkKey(int k) {
        if (keys.length >= k) {
            return keys[k] || keys[Character.toUpperCase(k)];
        }
        return false;
    }//method to check the keys and which one has been pressed

    
    public void keyPressed() {
        //if a key has been pressed it is set to true in the key array
        keys[keyCode] = true;

        if(checkKey('p') || checkKey('P')){
            if(state == 0){
                state = 1;
                getAudioPlayer().play();

            }else if(state == 1){
                state = 0;
                getAudioPlayer().pause();

            }//end else if statements to check if the player has paused the game
        }//if statement to allow the user to pause the game 

        
        if(checkKey('r') || checkKey('R')){
            if(state == 0 || state == 2 || state == 3){
                gameReset();
            }//end if to reset the game
        }//if statement to allow the user to pause the game 


    }//method to check if a key has been pressed


    public void keyReleased() {
        //if a key has been released it is set to false in the key array
        keys[keyCode] = false;
    }//method to check if a key has been released


}//end the game class
