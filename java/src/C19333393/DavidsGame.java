package C19333393;

import java.util.ArrayList;
import ie.tudublin.*;

public class DavidsGame extends Visual{    
    boolean[] keys = new boolean[1024];
    Player p;
    ArrayList<Bullet> bullets = new ArrayList<>();
    float c, z, n, m, l;
    //WaveForm wf;
    //AudioBandsVisual abv;

    public void settings(){
        size(1024, 500);
        
        // Use this to make fullscreen
        //fullScreen();

        // Use this to make fullscreen and use P3D for 3D graphics
        //fullScreen(P3D, SPAN); 
    }

    public void setup(){
        startMinim();
                
        // Call loadAudio to load an audio file to process 
        //loadAudio("heroplanet.mp3");   

        
        // Call this instead to read audio from the microphone
        startListening(); 
        p = new Player(width / 2, height / 2, 5, this);
        //wf = new WaveForm(this);
        //abv = new AudioBandsVisual(this);

        z = height/2;
        n = width/2;
        m = 30;
        l=  height/5;
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
        p.update();
        p.render();
        
        for(int i = bullets.size() - 1; i >= 0; i--){
            Bullet b = bullets.get(i);
            b.render();
            b.update();
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
            System.out.println("GREGDRGDGRDNYHNHNHNH " + b.position.x +" " + b.position.y);
            //line(b.position.x, (b.position.y)-5, b.position.x, (b.position.y)+5);

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
