package C19333393;

import processing.core.PImage;
//import processing.core.PApplet;
//import processing.core.PVector;

public class Player extends GameObject {
    boolean inAir;
    boolean canDoubleJump;
    boolean isShooting, isMoving;
    float grav, terminalVel, velocity;
    float acceleration, maxAcceleration;
    float jumpHeight;
    float fireRate;
    float airFriction, friction;
    float timer;
    float prevX, prevY;
    float scale;
    
    

    PImage body;
    PImage threads;
    PImage turret;
    PImage backPack;


    public Player(float x, float y, float speed, DavidsGame game, float scale) {
        super(x, y, speed, game, 0);
        this.inAir = false;
        this.isShooting = false;
        this.isMoving = false;
        this.grav = 2;
        this.terminalVel = 40;
        this.velocity = speed;
        this.acceleration = 0.2f;
        this.maxAcceleration = 8;
        this.jumpHeight = 30;
        this.fireRate = 5.0f;
        this.friction = .1f;
        this.airFriction = .001f;
        this.timer = 0;
        this.scale = scale;
        
        loadImages();
        this.w = threads.width*.2f;
        this.h = body.height*.2f;
        this.halfW = w/2;
        this.halfH = h/2;
        this.prevX = position.x;
        this.prevY = position.y;


    }//end player constructor


    public void load(){
        

    }


    void loadImages(){

        game.imageMode(DavidsGame.CORNER);

        body = game.loadImage("rBdy.png");
        body.resize((int)(body.width*scale), (int)(body.height*scale));
        
        threads = game.loadImage("threads1.png");
        threads.resize((int)(threads.width*scale), (int)(threads.height*scale));

        turret = game.loadImage("turretR1.png");
        turret.resize((int)(turret.width*scale), (int)(turret.height*scale));

        backPack = game.loadImage("thruster0.png");
        backPack.resize((int)(backPack.width*scale), (int)(backPack.height*scale));

    }


    public void render(){

        game.image(body, position.x, position.y);
        game.image(threads, position.x-(body.width*.4f), position.y+(body.height*.9f));
        game.image(backPack, position.x-(body.width*.2f) ,position.y+(body.height*.35f));

        game.pushMatrix();
        game.translate(position.x+(body.width*.4f), position.y-(body.height*.1f));
        game.imageMode(DavidsGame.CENTER);
        game.rotate(rotation);
        game.image(turret, 0 ,0);
        game.popMatrix();

        game.imageMode(DavidsGame.CORNER);

    }


    void update(){

        float angle = DavidsGame.atan2(position.x+(body.width*.4f)-game.mouseX, position.y-(body.height*.1f)-game.mouseY);
        rotation = -angle;

        direction.x = DavidsGame.sin(rotation);
        direction.y =  - DavidsGame.cos(rotation);
        
        if(game.checkKey('a') ||game.checkKey('A')){
            position.x -= 1 + speed;
            isMoving = true;
        }
        if(game.checkKey('d') ||game.checkKey('D')){
            position.x += 1 + speed;
            isMoving = true;
        }
        if(game.checkKey(' ') || inAir == true){
            jump();
        }

        accelerate();
        gravity();
        shoot();

        if(isShooting && inAir){

            recoilFlight();
        }//end if statement
        

        isMoving = false;
    }//end update of player character
    

    void gravity(){
        velocity += grav;
        if (velocity > terminalVel){
            velocity = terminalVel;
        }

        position.y += velocity;
    }//end method to apply gravity to player


    void accelerate(){
        if((game.frameCount % 25) == 0){
            if(isMoving){
                speed += acceleration;
                acceleration += acceleration;

                if(acceleration > 2){
                    acceleration = 2;
                }

                if (speed > maxAcceleration){
                    speed = maxAcceleration;
                }
            }else{
                acceleration -= acceleration;
                if(acceleration <= 0.2f){
                    acceleration = 0.2f;
                }
                speed = 5; 
            }
        }
    }//end method to apply gravity to player


    void shoot(){
        if (game.mousePressed){
            isShooting = true;
            if((game.frameCount % fireRate) == 0){
                float dist = 10;
               
                float scaleW = body.width/2;
                float scaleH = body.height/2;
                
                Bullet b = new Bullet(position.x + (direction.x * dist)+(body.width*.4f), position.y + (direction.y * dist)-(body.height*.1f), 
                10, game, scaleW, scaleH, rotation);
                
                game.bullets.add(b);
                game.children.add(b);
                terminalVel = 20;
            }
        }else{

            isShooting = false;
            terminalVel = 60;
        }
    }//end shooting method


    void recoilFlight(){

        direction.x = - DavidsGame.sin(rotation);
        direction.y =   DavidsGame.cos(rotation);

        position.x += direction.x * (velocity);
        position.y += direction.y * ((grav * velocity));

        timer = 25;

    }//end recoil method


    void jump(){

        timer++;
        inAir = true;

        if(!isShooting && timer <= jumpHeight){
            position.y -= jumpHeight;
        }

    }


    public void land(float f) {
        if(position.y + getHeight() >= f){
            inAir = false;
            timer = 0;
            velocity = 0;
            position.y= f - getHeight();
            position.y -= grav;
        }
    }


    public void tapTop(float f) {
        if(position.y + getHeight() >= f){
            position.y = f;
            position.y -= grav;
        }
    }


    public void hitWall(float f, String s) {
        speed = 5;

        if(s == "RIGHT"){
            position.x += 6;
        }else if(s == "LEFT"){
            position.x -= 6;
        }

        if(isShooting && inAir){
            if(s == "RIGHT"){
                position.x = f + (position.x - getLeft());
                position.x += velocity;
            }else if(s == "LEFT"){
                //position.x = f - (position.x - getLeft());
                position.x = f - (getRight() - position.x);
                position.x -= velocity;            
            }

        }//end if to check if the robot is flying due to recoil
    }


    public float getHeight() {

        return  getBottom()-position.y;
    }


    public float getBottom() {
        float bY,b,bH;

        bY = position.y+(body.height*.85f);
        bH = (threads.height);
        b = bY + bH; 
        return b;
    }


    public float getLeft(){
        float threadHW = (threads.width)/4;

        return position.x - threadHW;
    }


    public float getRight(){
        float threadHW = (threads.width)*.75f;

        return position.x + threadHW;
    }

}
