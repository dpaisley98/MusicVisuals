package C19333393;

public class Bullet extends GameObject{
    
    private float lifetime, timeAlive;


    public Bullet(float x, float y, float speed, DavidsGame game, float scaleW, float scaleH, float rotation, int hp) {
        super(x, y, speed, game, rotation, hp);
        lifetime = 5;
        timeAlive = 0;
        setW(10);
        setHalfW(getW()/2);
    }//end constructor

    
    public void render(){

        getGame().stroke(0,0,0);
        getGame().strokeWeight(3);
        getGame().pushMatrix();
        getGame().translate(getPosition().x, getPosition().y);
        getGame().rotate(getRotation());
        getGame().line(0, -5, 0, 5);
        getGame().popMatrix();

    }//method to render the bullets


    public void update(){
        getDirection().x =  DavidsGame.sin(getRotation());
        getDirection().y =  - DavidsGame.cos(getRotation());

        getPosition().x += getDirection().x * (getSpeed()*4);
        getPosition().y += getDirection().y * (getSpeed()*4);

        //setHalfW(((getPosition().y + 5) - (getPosition().y - 5))/2);

        timeAlive += (1 / 60.0f);

        if (timeAlive > lifetime){
            setAlive(false);
        }//if the bullet is around for too long it dies 

        if(!(isAlive())){
            getGame().bullets.remove(this);
            getGame().children.remove(this);
        }//end if to delete the bullet once it exceeds bounds or "dies" 

    }//end update


}//end bullet class
