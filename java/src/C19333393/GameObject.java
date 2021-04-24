package C19333393;

import processing.core.PVector;

public abstract class GameObject {
    PVector position, direction, right;
    float rotation;
    float w, h;
    float halfW, halfH;
    float speed;
    boolean isAlive;
    DavidsGame game;

    
    public GameObject(float x, float y, float speed, DavidsGame game, float rotation) {
        this.position = new PVector(x, y);
        this.direction = new PVector(0, -1);
        this.right = new PVector(1, 0);
        this.rotation = rotation;
        this.halfW = w/2;
        this.halfH = h/2;
        this.speed = speed;
        this.isAlive = true;
        this.game = game;
    }


    void update(){

        direction.x = DavidsGame.sin(rotation);
        direction.y =  - DavidsGame.cos(rotation);
        right = PVector.fromAngle(rotation);

    }

    boolean collides(GameObject object){

        float combW = halfW + object.halfW;
        System.out.println("ROCKET X: " + position.x + "ROCKET Y: " + position.y);
        System.out.println("PLAYER X: " + object.position.x + "PLAYER Y: " + object.position.y);

        return PVector.dist(position, object.position) < combW;
    }

    boolean environmentCollides(float x, float y, float surfaceW){

        PVector p = new PVector(x,y);
        float combW = w + surfaceW;
        //(b.posX > (n - m/2)&& b.posX < (n+ m/2)) && (b.posY > (z-l/2) && b.posY < z + l/2)
        return PVector.dist(position, p) < combW;
    }

    

    public abstract void load();
    public abstract void render();


    public PVector getPosition() {
        return position;
    }


    public void setPosition(PVector position) {
        this.position = position;
    }


    public PVector getDirection() {
        return direction;
    }


    public void setDirection(PVector direction) {
        this.direction = direction;
    }


    public float getRotation() {
        return rotation;
    }


    public void setRotation(float rotation) {
        this.rotation = rotation;
    }


    public float getW() {
        return w;
    }


    public void setW(float w) {
        this.w = w;
    }


    public float getH() {
        return h;
    }


    public void setH(float h) {
        this.h = h;
    }


    public float getHalfW() {
        return halfW;
    }


    public void setHalfW(float halfW) {
        this.halfW = halfW;
    }


    public float getHalfH() {
        return halfH;
    }


    public void setHalfH(float halfH) {
        this.halfH = halfH;
    }


    public float getSpeed() {
        return speed;
    }


    public void setSpeed(float speed) {
        this.speed = speed;
    }


    public boolean isAlive() {
        return isAlive;
    }


    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }


    public DavidsGame getGame() {
        return game;
    }


    public void setGame(DavidsGame game) {
        this.game = game;
    }

    

}
