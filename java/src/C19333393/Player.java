package C19333393;

import processing.core.PImage;
//import processing.core.PApplet;
//import processing.core.PVector;

public class Player extends GameObject {
    boolean inAir;
    boolean canDoubleJump;
    boolean isShooting;
    float grav, terminalVel;
    float fireRate;
    float airFriction, friction;
    float timer;
    float prevX, prevY;
    
    

    PImage body;
    PImage threads;
    PImage turret;
    PImage backPack;


    public Player(float x, float y, float speed, DavidsGame game) {
        super(x, y, speed, game, 0);
        this.inAir = false;
        this.isShooting = false;
        this.grav = 2;
        this.terminalVel = 40;
        this.fireRate = 5.0f;
        this.friction = .1f;
        this.airFriction = .001f;
        this.timer = 0;
        
        loadImages();
        this.w = threads.width*.2f;
        this.h = body.height*.2f;
        this.halfW = w/2;
        this.halfH = h/2;
        this.prevX = position.x;
        this.prevY = position.y;

    }//end constructor

    public void load(){
        

    }

    void loadImages(){

        game.imageMode(DavidsGame.CORNER);

        body = game.loadImage("rBdy.png");
        body.resize((int)(body.width*.2f), (int)(body.height*.2f));
        
        threads = game.loadImage("threads1.png");
        threads.resize((int)(threads.width*.2f), (int)(threads.height*.2f));

        turret = game.loadImage("turretR1.png");
        turret.resize((int)(turret.width*.2f), (int)(turret.height*.2f));

        backPack = game.loadImage("thruster0.png");
        backPack.resize((int)(backPack.width*.2f), (int)(backPack.height*.2f));

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

        direction.x = DavidsGame.sin(rotation);
        direction.y =  - DavidsGame.cos(rotation);
        if(game.keyPressed){
            switch(game.key){
                case 'a': {
                    position.x -= 4;
                    break;
                }
                case 'd':{
                    position.x += 4;
                    break;
                }
                default: {
                    System.out.println("Not valid Input");
                    break;
                }

            }
        }
        if (game.checkKey(DavidsGame.LEFT)){

            position.x -= 4;
        }//end if statement 


        if (game.checkKey(DavidsGame.RIGHT)){

            position.x += 4;
        }//end if statement 


        if (game.checkKey(DavidsGame.UP) || inAir == true){

            timer++;
            inAir = true;

            if(!isShooting && timer <= 25){
                position.y -= 25;
            }

        }//end if statement 

        float angle = DavidsGame.atan2(position.x+(body.width*.4f)-game.mouseX, position.y-(body.height*.1f)-game.mouseY);
        rotation = -angle;

        
        gravity();
        shoot();

        if(isShooting && inAir){

            recoilFlight();
        }//end if statement
        
        if((game.frameCount % 2) == 0){
            prevX = position.x;
            prevY = position.y;
        }
    }
    

    void gravity(){
        speed += grav;
        if (speed > terminalVel){
            speed = terminalVel;
        }

        position.y += speed;
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

        position.x += direction.x * (speed);
        position.y += direction.y * ((grav * speed));

        timer = 25;

    }//end recoil method


    public void land(float f) {
        if(position.y + getHeight() >= f){
            inAir = false;
            timer = 0;
        }
    }

    public float getHeight() {

        return  getBottom()-position.y;
    }


    public float getBottom() {
        float bY,b,bH;

        bY = position.y+(body.height*.9f);
        bH = (threads.height);
        b = bY + bH; 
        return b;
    }

    
}
