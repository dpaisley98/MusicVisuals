package C19333393;


import processing.core.PImage;

public class Player extends GameObject {
    private boolean inAir;
    private boolean jump;
    private boolean isShooting, isMovingLeft, isMovingRight;

    private float grav, terminalVel, velocity;
    private float acceleration, maxAcceleration;
    private float jumpHeight, jumpPower;
    private float fireRate;
    private float timer;
    private float scale;
    
    private PImage body;
    private PImage threads[];
    private PImage turret[];
    private PImage backPack[];

    private int delay = 0;
    private int threadFrame, turretFrame, backPackFrame, in;


    public Player(float x, float y, float speed, DavidsGame game, float scale, int hp) {
        super(x, y, speed, game, 0, hp);

        this.inAir = false;
        this.isShooting = false;
        this.isMovingLeft = false;
        this.isMovingRight = false;
        this.jump = false; 

        this.grav = 2;
        this.terminalVel = 40;
        this.velocity = speed;
        this.acceleration = 0.2f;
        this.maxAcceleration = 8;
        this.jumpHeight = jumpPower = game.height *.03f;

        this.fireRate = 5.0f;
        this.timer = 0;
        this.scale = scale;
        threadFrame = turretFrame = backPackFrame = in = 0;

        
        loadImages();
        setW(threads[0].width*.2f);
        setH(body.height*.2f);
        setHalfW(getW()/2);
        setHalfH(getH()/2);


    }//end player constructor


    public void load(){

        if(isMovingLeft){
            if(delay == 0){
                threadFrame--;
                if(threadFrame<0){
                    threadFrame = threads.length-1;
                }
            }
        }else if (isMovingRight){
            if(delay == 0){
                threadFrame = (threadFrame + 1) % threads.length;
            }
        }//else if statement to animate the tankl threads


        if(isShooting){
            if (delay == 0){
                turretFrame = (turretFrame +1) % turret.length;
            }
        }//if statement to animate the turret


        if(jump && in < 6){
            if (delay == 0){
                backPackFrame = (backPackFrame + 1) % turret.length;
                in++;
            }
        }//animate thruster when the player jumps


        delay = (delay + 1) % 3;

    }//end method to animate the player


    void loadImages(){
        threads = new PImage[8];
        backPack = new PImage[4];
        turret = new PImage[3];

        body = getGame().loadImage("rBdy.png");
        body.resize((int)(body.width*scale), (int)(body.height*scale));
        
        for(int i =0; i< threads.length ; i++){
            threads[i] = getGame().loadImage("threads" + i + ".png");
            threads[i].resize((int)(threads[i].width*scale), (int)(threads[i].height*scale));
        }//end for loop to load the images for the tank threads

        for(int i =0; i< turret.length ; i++){
            turret[i] = getGame().loadImage("turretR" + i + ".png");
            turret[i].resize((int)(turret[i].width*scale), (int)(turret[i].height*scale));
        }//end for loop to load the images for the turret

        for(int i =0; i< backPack.length ; i++){
            backPack[i] = getGame().loadImage("thruster" + i + ".png");
            backPack[i].resize((int)(backPack[i].width*scale), (int)(backPack[i].height*scale));
        }//end for loop to load the images for the thruster pack


    }//end loading images


    public void render(){

        getGame().imageMode(DavidsGame.CORNER);


        getGame().image(body, getPosition().x, getPosition().y);
        getGame().image(threads[threadFrame], getPosition().x-(body.width*.4f), getPosition().y+(body.height*.9f));
        getGame().image(backPack[backPackFrame], getPosition().x-(body.width*.2f) ,getPosition().y+(body.height*.35f));


        getGame().pushMatrix();
        getGame().translate(getPosition().x+(body.width*.4f), getPosition().y-(body.height*.1f));
        getGame().imageMode(DavidsGame.CENTER);
        getGame().rotate(getRotation());
        getGame().image(turret[turretFrame], 0 ,0);
        getGame().popMatrix();


        getGame().imageMode(DavidsGame.CORNER);

    }//method to render the player 


    void update(){
        if(getHp() <= 0){
            setAlive(false);
        }

        //code to rotate the player's turret based on the location of the mouse
        float angle = DavidsGame.atan2(getPosition().x+(body.width*.4f) - getGame().mouseX, getPosition().y - (body.height*.1f) - getGame().mouseY);
        setRotation(-angle);

        getDirection().x = DavidsGame.sin(getRotation());
        getDirection().y =  - DavidsGame.cos(getRotation());
        

        if(getGame().checkKey('a') || getGame().checkKey('A')){
            getPosition().x -= 1 + getSpeed();
            isMovingLeft = true;
        }else{
            isMovingLeft = false;
        }//end the else if statement to see if the player is moving left

        if(getGame().checkKey('d') || getGame().checkKey('D')){
            getPosition().x += 1 + getSpeed();
            isMovingRight = true;
        }else{
            isMovingRight = false;
        }//end the else if statement to see if the player is moving right

        if(getGame().checkKey(' ')){
            jump = true;
        }//end the else if statement to see if the player is jumping


        jump();
        accelerate();
        gravity();
        shoot();
        load();


        if(isShooting && inAir){

            recoilFlight();
        }//end if statement
        

        inAir=true;
    }//end update of player character
    

    void gravity(){
        velocity += grav;
        if (velocity > terminalVel){
            velocity = terminalVel;
        }

        getPosition().y += velocity;
    }//end method to apply gravity to player


    void accelerate(){

        if((getGame().frameCount % 25) == 0){
            if(isMovingLeft || isMovingRight){
                setSpeed(getSpeed() + acceleration);
                acceleration += acceleration;

                if(acceleration > 2){
                    acceleration = 2;
                }//end if statement to ensure accelaration doesn't exceed 2

                if (getSpeed() > maxAcceleration){
                    setSpeed(maxAcceleration);
                }//end if to make sure the speed of the player doesn't exceed the max speed

            }else{
                acceleration -= acceleration;
                if(acceleration <= 0.2f){
                    acceleration = 0.2f;
                }
                setSpeed(5); 
            }//end else if to check if the player has reached maximum speed
        }//end if to make sure that the speed doesn't instanly accelerate immediately 

    }//end method to apply gravity to player


    void shoot(){

        if (getGame().mousePressed){
            isShooting = true;
            if((getGame().frameCount % fireRate) == 0){
                float dist = 10;
               
                float scaleW = body.width/2;
                float scaleH = body.height/2;
                
                Bullet b = new Bullet(getPosition().x + (getDirection().x * dist)+(body.width*.4f), getPosition().y + (getDirection().y * dist)-(body.height*.1f), 
                10, getGame(), scaleW, scaleH, getRotation(), 1);
                
                getGame().bullets.add(b);
                getGame().children.add(b);
                terminalVel = 20;
            }
        }else{

            isShooting = false;
            terminalVel = 60;
        }//end else if statement 

    }//end shooting method


    void recoilFlight(){

        getDirection().x = - DavidsGame.sin(getRotation());
        getDirection().y =   DavidsGame.cos(getRotation());

        getPosition().x += getDirection().x * (velocity);
        getPosition().y += getDirection().y * ((grav * velocity));

        timer = 25;

    }//end recoil method


    void jump(){

        
        if(jump || (inAir && jump)){
            timer++;
            inAir = true;

            if(!isShooting && timer <= jumpHeight){
                getPosition().y -= jumpHeight;
            }

        }//jump check to make sure the player is having its y values decreased over time


    }//end jump method 


    public void land(float f) {

        if(getBottom() >= f){

            jump = false;
            jumpHeight = jumpPower;
            inAir = false;
            timer = 0;
            in = 0;
            backPackFrame = 0;
            velocity = 0;
            getPosition().y = f - getHeight();
            getPosition().y -= grav;

        }//if statement that when the player hits land that the player own't fall through the map

    }//method to allow the player to land


    public void tapTop(float f) {

        if(getPosition().y  <= f){
            getPosition().y = f;
        }else if(isShooting && inAir){
            getPosition().y += (grav * velocity);
        }

    }//checks if the player has hit its head


    public void hitWall(float f, String s) {
        setSpeed(5);

        if(s == "RIGHT"){
            getPosition().x += 6;
        }else if(s == "LEFT"){
            getPosition().x -= 6;
        }//end else if statement to see if the player has hit the left or right side of a wall

        if(isShooting && inAir){
            if(s == "RIGHT"){
                getPosition().x = f + (getPosition().x - getLeft());
                getPosition().x += velocity;
            }else if(s == "LEFT"){
                getPosition().x = f - (getRight() - getPosition().x);
                getPosition().x -= velocity;            
            }//end else if statement to see if the player has hit the left or right side of a wall

        }//end if to check if the robot is flying due to recoil

    }//makes it so the player will hit a wall and not phase through


    public float getHeight() {

        return  getBottom()-getPosition().y;
    }//end getter


    public float getBottom() {
        float bY,b,bH;

        bY = getPosition().y+(body.height*.85f);
        bH = (threads[0].height);
        b = bY + bH; 
        return b;
    }//end getter


    public float getLeft(){
        float threadHW = (threads[0].width)/4;

        return getPosition().x - threadHW;
    }//end getter


    public float getRight(){
        float threadHW = (threads[0].width)*.75f;

        return getPosition().x + threadHW;
    }//end getter


}//end player class
