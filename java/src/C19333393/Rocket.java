package C19333393;

import processing.core.PImage;
import processing.core.PVector;

public class Rocket extends GameObject{
    //global variables for this class
    PImage body;
    Player p;
    PVector followOffSet;
    float moveToX, moveToY;
    float diffX, diffY;

    public Rocket(float x, float y, float speed, DavidsGame game, float rotation, Player p) {
        super(x, y, speed, game, rotation);
        this.p = p;
        loadImages();
        //followOffSet = posi

        moveToX = p.position.x + p.w;
        moveToY = p.position.y + p.getHeight();
        diffX = moveToX - position.x;
        diffY = moveToY - position.y;
        rotation = (float)DavidsGame.atan2(diffY, diffX);

    }//end constructor
    
    public void load(){}//end load

    private void loadImages(){
        game.imageMode(DavidsGame.CENTER);

        body = game.loadImage("rocket1.png");
        w = game.img.width;
        h = game.img.height;
        halfW = w/2;
        halfH = h/2;
        //game.img.resize((int)(w), (int)(h));

        game.imageMode(DavidsGame.CORNER);

    }//end load images


    public void render(){

        game.pushMatrix();
        game.translate(position.x, position.y);
        game.rotate(rotation);
        game.image(game.img, 0 ,0);
        game.popMatrix();
        
    }//end render

    public void update(){
        
        moveToX = DavidsGame.lerp(p.prevX + p.w, p.position.x, .01f);
        moveToY = DavidsGame.lerp(p.prevY, p.position.y, .01f);;

        diffX = moveToX - position.x;
        diffY = moveToY - position.y;
       
        float angle = (float)DavidsGame.atan2(diffY, diffX);
        rotation = angle;

        
        /*if(rotation < 0){
            rotation = prevRotation;
           // System.out.println("HTRSHGERSGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG " + rotation);

        }*/
        //position = PVector.lerp(position, p.position, .01f);

        

        direction.x =  DavidsGame.cos(rotation);
        direction.y =  DavidsGame.sin(rotation);
       

        position.x += speed * direction.x;
        position.y += speed * direction.y;

        //System.out.println("ROTATION " + rotation);
        //System.out.println("HTRSHGERSGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG " + rotation * DavidsGame.PI / 180);

        if(!isAlive){
            game.rockets.remove(this);
            game.children.remove(this);
        }

    }//end update
}
