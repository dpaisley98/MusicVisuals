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
        position = new PVector(x, y);
        direction = new PVector(0, -1);
        right = new PVector(1, 0);
        this.rotation = rotation;
        halfW = w/2;
        halfH = h/2;
        this.speed = speed;
        isAlive = true;
        this.game = game;
    }


    void update(){

        direction = PVector.fromAngle(rotation - DavidsGame.HALF_PI);
        right = PVector.fromAngle(rotation);

    }

    boolean collides(GameObject object){

        float combW = halfW + object.halfW;

        return PVector.dist(position, object.position) < combW;
    }

    boolean environmentCollides(float x, float y, float surfaceW){

        PVector p = new PVector(x,y);
        float combW = halfW + surfaceW;

        return PVector.dist(p, position) < combW;
    }

    public abstract void load();
    public abstract void render();

}
