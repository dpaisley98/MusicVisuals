package C19333393;

import processing.core.PVector;

public abstract class GameObject {
    PVector position, direction;
    float rotation;
    float w, h;
    float halfW, halfH;
    float speed;
    boolean isAlive;
    DavidsGame game;

    
    public GameObject(float x, float y, float speed, DavidsGame game, float rotation) {
        this.position = new PVector(x, y);
        this.direction = new PVector(0, -1);
        this.rotation = rotation;
        this.halfW = w/2;
        this.halfH = h/2;
        this.speed = speed;
        this.isAlive = true;
        this.game = game;
    }//end game object constructor


    void update(){

        direction.x = DavidsGame.sin(rotation);
        direction.y =  - DavidsGame.cos(rotation);

    }

    boolean collides(GameObject object){

        float combW = halfW + object.halfW;

        return PVector.dist(position, object.position) < combW;
    }//end method to check for any collision between two objects


    public abstract void load();
    public abstract void render();


    /**********************************************************************GETTERS AND SETTERS**********************************************************************/
    public PVector getPosition() {
        return position;
    }//end getter


    public void setPosition(PVector position) {
        this.position = position;
    }//end setter


    public PVector getDirection() {
        return direction;
    }//end getter


    public void setDirection(PVector direction) {
        this.direction = direction;
    }//end setter


    public float getRotation() {
        return rotation;
    }//end getter


    public void setRotation(float rotation) {
        this.rotation = rotation;
    }//end setter


    public float getW() {
        return w;
    }//end getter


    public void setW(float w) {
        this.w = w;
    }//end setter


    public float getH() {
        return h;
    }//end getter


    public void setH(float h) {
        this.h = h;
    }//end setter


    public float getHalfW() {
        return halfW;
    }//end getter


    public void setHalfW(float halfW) {
        this.halfW = halfW;
    }//end setter


    public float getHalfH() {
        return halfH;
    }//end getter


    public void setHalfH(float halfH) {
        this.halfH = halfH;
    }//end setter


    public float getSpeed() {
        return speed;
    }//end getter


    public void setSpeed(float speed) {
        this.speed = speed;
    }//end setter


    public boolean isAlive() {
        return isAlive;
    }//end getter


    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }//end setter


    public DavidsGame getGame() {
        return game;
    }//end getter


    public void setGame(DavidsGame game) {
        this.game = game;
    }//end setter

}//end game object class
