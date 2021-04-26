package C19333393;

import java.util.ArrayList;
import ie.tudublin.*;
import processing.core.PImage;

public class DavidsGame extends Visual{    
    boolean[] keys = new boolean[1024];

    Player p;
    Platform platform;

    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<Rocket> rockets = new ArrayList<>();
    ArrayList<Platform> plats = new ArrayList<>();
    ArrayList<GameObject> children = new ArrayList<>();

    boolean test = false;
    PImage img;
    float c;

    String s;

    //WaveForm wf;
    //AudioBandsVisual abv;


    public void settings(){
        size(1024, 500);
        // Use this to make fullscreen
        fullScreen();
        // Use this to make fullscreen and use P3D for 3D graphics
        //fullScreen(P3D, SPAN); 
    }


    public void setup(){
        startMinim();
                
        // Call loadAudio to load an audio file to process 
        loadAudio("AlmostEvil.mp3");
        getAudioPlayer().play();

        //controll = ControlIO.getInstance(this);
        // Call this instead to read audio from the microphone
        //startListening(); 
        p = new Player(width / 2, height / 2, 4, this, (width*.1f)/1000);

        children.add(p);
        //wf = new WaveForm(this);
        //abv = new AudioBandsVisual(this); 


        img = loadImage("rocket1.png");
        img.resize((int)(img.width*.2f), (int)(img.height*.2f));

        //spawnRockets();
        level();
        textSize(width*.025f);

    }


    private void spawnRockets() {
        Rocket r = new Rocket(random(width), random(height/2), 4, this, 0, p);

        rockets.add(r);
        children.add(r);
        
    }


    void level(){
        float gameHeight = height*.95f;


        platform = new Platform(width*.1f, gameHeight/4, width*.25f, 20, this, color(0,0,0));
        plats.add(platform);        
        platform = new Platform(width*.9f, gameHeight/4, width*.25f, 20, this, color(0,0,0));
        plats.add(platform);   
        platform = new Platform(width/2, gameHeight, width, gameHeight*.1f, this, color(0,0,0));     
        plats.add(platform);   
        platform = new Platform(width*.05f, gameHeight*.75f, width*.125f, 20, this, color(0,0,0));
        plats.add(platform);        
        platform = new Platform(width*.95f, gameHeight*.75f, width*.125f, 20, this, color(0,0,0));
        plats.add(platform);  
        platform = new Platform(width/2, height, width, height*.1f, this, color(165,214,167));     
        plats.add(platform);  
        platform = new Platform(width/2, 0, width, height*.1f, this, color(165,214,167));     
        plats.add(platform);  
        platform = new Platform(0, height/2, width*.025f, height, this, color(165,214,167));     
        plats.add(platform); 
        platform = new Platform(width, height/2, width*.025f, height, this, color(165,214,167));     
        plats.add(platform); 


    }//end method to spawn in level


    public void draw(){
        background(150,20,40);


        try{
            // Call this if you want to use FFT data
            calculateFFT(); 
        }catch(VisualException e){
            e.printStackTrace();
        }


        // Call this is you want to use frequency bands
        calculateFrequencyBands(); 

        // Call this is you want to get the average amplitude
        calculateAverageAmplitude();        
        //wf.render();
        //abv.render();


        cursor(CROSS);
        stroke(255);


        game();
        //rectMode(CENTER);

    }


    void game(){

        float[] bands = getSmoothedBands();
        float h = bands[4];

        for (Platform plat : plats) {
            plat.render();
        }//end for loop to render all platforms

        fill(0,0,0);
        text("HEALTH", width*.0125f, height*.0375f);

        for(int i = children.size() - 1; i >= 0; i--){
            GameObject o = children.get(i);
            o.render();
            o.update();
        }//end loop to render objects
        

        for(int i = children.size() - 1; i >= 0; i--){
            GameObject o = children.get(i);


            if(o instanceof Player){
                Player robot = ((Player)(o));

                for(Platform platform : plats){
                    s = platform.checkCollision(robot);
                    switch(s){
                        case "BOTTOM":{
                            if(platform.c == color(165,214,167)){
                                robot.tapTop(platform.y + platform.h/2);
                            }
                            break;
                        }
                        case "TOP":{
                            robot.land(platform.y - platform.h/2);
                            break;
                        }
                        case "LEFT":{
                            robot.hitWall(platform.x - platform.w/2, s);
                            break;
                        }
                        case "RIGHT":{
                            robot.hitWall(platform.x + platform.w/2, s);
                            break;
                        }
                    }
                    /*
                    if(platform.c == color(165,214,167) && s == "BOTTOM"){
                        robot.tapTop(platform.y + platform.h/2);
                    }//end if statement to ensure that the player will not phase through objects
                    if(s == "TOP"){
                        robot.land(platform.y - platform.h/2);
                    }else if(s == "LEFT"){
                        robot.hitWall(platform.x - platform.w/2, s);
                    }else if(s == "RIGHT"){
                        robot.hitWall(platform.x + platform.w/2, s);
                    }*/
                    
                }//end for loop to iterate through all platforms


            }else if(o instanceof Bullet){
               
                for(Platform platform : plats){
                    s = platform.checkCollision(o);
                    if(s != "NO COLLISION"){
                        o.isAlive = false;
                    }//end if to destroy the bullet if it has collided with a platform

                }//end for loop to iterate through all platforms
                
            }//end else if to check collisions on platforms


            if(o instanceof Bullet){

                for(int j = 0; j < rockets.size(); j++){
                    Rocket ro = rockets.get(j);
                    if(o.collides(ro)){
                       ro.isAlive = false;
                    }//end if to check if a bullet has collided with an enemy
                }//end for loop to iterate through all enemies
            }//end if statement to check if the object is a bullet


            if(o instanceof Rocket){

                if(o.collides(p)){
                    o.isAlive = false;
                }//end if statement to check if a rocket has hit the player

            }//end if statement to check if the object is a rocket


        }//end for loop to iterate through all objects 
           

        if((h)>120 && (frameCount % 30) == 0){
            spawnRockets();
        }//end if statement that will spawn enemies


    }//end game method


    /*private void checkCollisions() {
        s = platform.checkCollision(p);
        println(s);

        if(s == "TOP"){
            p.land(platform.y - platform.h/2);
        }

        if (p.getBottom() >= height-(height*.06f) )
        {
            p.land(height-(height*.06f));
            p.velocity=0;
            p.position.y=height-(height*.06f)-p.getHeight();
            p.position.y -=p.grav;
        }


    }*/


    boolean checkKey(int k) {
        if (keys.length >= k) {
            return keys[k] || keys[Character.toUpperCase(k)];
        }
        return false;
    }


    public void mousePressed() {
        //p.shoot();
    }


    public void mouseMoved() {
        
        
    }


    public void keyPressed() {
        keys[keyCode] = true;

        /*if (key == ' ')
        {
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
        }*/
    }


    public void keyReleased() {
        keys[keyCode] = false;
    }

}
