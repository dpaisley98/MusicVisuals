package C19333393;

import processing.core.PImage;
import processing.core.PVector;

public class Rocket extends GameObject{
    //gloabal variables for this class
    PImage body;
    Player p;
    PVector followOffSet;
    float followSharpness;
    float prevRotation;
    float moveToX, moveToY;
    float diffX, diffY;

    public Rocket(float x, float y, float speed, DavidsGame game, float rotation, Player p) {
        super(x, y, speed, game, rotation);
        this.p = p;
        loadImages();
        followSharpness = .05f;
        //followOffSet = posi
        moveToX = p.position.x + p.w/2;
        moveToY = p.position.y + p.getHeight();
    }//end constructor
    
    public void load(){}//end load

    private void loadImages(){
        game.imageMode(DavidsGame.CENTER);

        body = game.loadImage("rocket1.png");
        w = body.width*.2f;
        h = body.height*.2f;
        halfW = w/2;
        halfH = h/2;
        body.resize((int)(w), (int)(h));

    }//end load images


    public void render(){

        game.pushMatrix();
        game.translate(position.x, position.y);
        game.rotate(rotation);
        game.image(body, 0 ,0);
        game.popMatrix();
        
    }//end render

    public void update(){
      
        moveToX = p.position.x + p.w/2;
        moveToY = p.position.y + p.h/2;

        diffX = moveToX - position.x;
        diffY = moveToY - position.y;

        float angle = (float)DavidsGame.atan2(diffY, diffX);
        rotation = angle;

        /*if(rotation < 0){
            rotation = prevRotation;
           // System.out.println("HTRSHGERSGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG " + rotation);

        }*/
        position = PVector.lerp(position, p.position, .01f);

        if((game.frameCount % 5) == 0){

            direction.x =  DavidsGame.cos(rotation);
            direction.y =  DavidsGame.sin(rotation);
        }//end if statement

        //position.x += speed * direction.x;
        //position.y += speed * direction.y;

        //System.out.println("ROTATION " + rotation);
        //System.out.println("HTRSHGERSGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG " + rotation * DavidsGame.PI / 180);
        prevRotation = rotation;

        if(!isAlive){
            game.rockets.remove(this);
        }

    }//end update
}
