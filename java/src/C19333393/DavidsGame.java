package C19333393;

import java.util.ArrayList;
import ie.tudublin.*;
import processing.core.PImage;

public class DavidsGame extends Visual{    
    boolean[] keys = new boolean[1024];
    Player p;
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<Rocket> rockets = new ArrayList<>();
    ArrayList<GameObject> children = new ArrayList<>();
    boolean test = false;
    PImage img;
    float c, z, n, m, l;
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
        p = new Player(width / 2, height / 2, 5, this);
        //wf = new WaveForm(this);
        //abv = new AudioBandsVisual(this);

        z = height/2;
        n = width/2;
        m = 30;
        l=  height/5;

        img = loadImage("rocket1.png");
        img.resize((int)(img.width*.2f), (int)(img.height*.2f));

        //spawnRockets();
    }

    private void spawnRockets() {

        Rocket r = new Rocket(random(width), random(height/2), 4, this, 0, p);
        rockets.add(r);
        children.add(r);
        
    }

    public void draw(){
        background(220,20,60);
        try
        {
            // Call this if you want to use FFT data
            calculateFFT(); 
        }
        catch(VisualException e)
        {
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

        float[] bands = getSmoothedBands();
        float h = bands[4];


        /*for(int i = bullets.size() - 1; i >= 0; i--){
            Bullet b = bullets.get(i);
            b.render();
            b.update();
        }

        for(int i = rockets.size() - 1; i >= 0; i--){
            Rocket r = rockets.get(i);
            r.render();
            r.update();


            if(r.collides(p)){
                test = true;
                rockets.remove(i);
            }
        }*/

        for(int i = children.size() - 1; i >= 0; i--){
            GameObject o = children.get(i);
            o.render();
            o.update();
        }//end loop to render objects
        
        for(int i = children.size() - 1; i >= 0; i--){
            GameObject o = children.get(i);
            if(o instanceof Bullet){

                for(int j = 0; j < rockets.size(); j++){
                    Rocket ro = rockets.get(j);
                    if(o.collides(ro)){
                       ro.isAlive = false;
                    }
                }
            }
        }
           
        
        p.update();
        p.render();
        System.out.println("AMP "+ getSmoothedAmplitude()*100);
        if((h)>120 && (frameCount % 30) == 0){
            spawnRockets();
        }

       // checkCollisions();

        line(p.position.x,p.getBottom() ,width,p.getBottom() );   
        line(p.position.x, height-(height*.06f)-p.getHeight() ,width, height-(height*.06f)-p.getHeight() );   
        //line(p.position.x,p.getTop() ,width,p.getTop() );   


        stroke(0,255,0); 
        line(0,height-(height*.06f) ,width,height-(height*.06f)  );    


        c = 255;
        checkCollisions();
        fill(c,0,0);
        rectMode(CENTER);
        rect(n, z,m,l);

        //line((n - m), height, (n - m), 0);
        line((n - m/2), 0 , (n - m/2), height);
        line((n + m/2), 0, (n+ m/2), height );
        line(0, (z-l/2) ,width , z - l/2 );
        line(0, (z+l/2) ,width , z + l/2 );
    }

    private void checkCollisions() {
        if (p.getBottom() >= height-(height*.06f) )
        {
            p.land(height-(height*.06f));
            p.speed=0;
            p.position.y=height-(height*.06f)-p.getHeight();
            p.position.y -=p.grav;
        }
        for (Bullet b : bullets) {
            //System.out.println("GREGDRGDGRDNYHNHNHNH " + b.position.x +" " + b.position.y);
            
            if(b.environmentCollides(n, z, m) ){
                c = 0;
                //System.out.println("GREGSREGRSGRSE " + b.x +" " + b.y);
               // System.out.println("GREGSREGRSGRSE " + b.posX +" " + b.posY);
               // System.out.println("MOUSE X: "+ mouseX + "MOUSE Y: "+ mouseY);
               // System.out.println("RECT LEFT " + (width/2 - 15) );
            }
            
        }

    }

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
