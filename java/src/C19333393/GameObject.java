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

}
