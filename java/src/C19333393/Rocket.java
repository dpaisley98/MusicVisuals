package C19333393;

public class Rocket extends GameObject{
    //global variables for this class
    private Player p;
    private float moveToX, moveToY;
    private float diffX, diffY;


    public Rocket(float x, float y, float speed, DavidsGame game, float rotation, Player p, int hp) {
        super(x, y, speed, game, rotation, hp);
        this.p = p;
        //followOffSet = posi

        update();
        loadImages();


    }//end constructor
    

    private void loadImages(){
        getGame().imageMode(DavidsGame.CENTER);

        setW(getGame().img.width);
        setH(getGame().img.height);
        setHalfW(getW()/2);
        setHalfH(getH()/2);
        //game.img.resize((int)(w), (int)(h));

        getGame().imageMode(DavidsGame.CORNER);

    }//end load images


    public void render(){

        getGame().pushMatrix();
        getGame().translate(getPosition().x, getPosition().y);
        getGame().rotate(getRotation());
        getGame().image(getGame().img, 0 ,0);
        getGame().popMatrix();
        
    }//end render

    public void update(){
        
        //will calculate the value the rocket needs to move to by geting the value between the rocket and the player
        moveToX = DavidsGame.lerp(p.getPosition().x + p.getW(), getPosition().x, .01f);
        moveToY = DavidsGame.lerp(p.getPosition().y, getPosition().y, .01f);

        diffX = moveToX - getPosition().x;
        diffY = moveToY - getPosition().y;
       
        float angle = (float)DavidsGame.atan2(diffY, diffX);
        setRotation(angle);
        

        getDirection().x =  DavidsGame.cos(getRotation());
        getDirection().y =  DavidsGame.sin(getRotation());
       

        getPosition().x += getSpeed() * getDirection().x;
        getPosition().y += getSpeed() * getDirection().y;


        if(getHp() < 1){
            setAlive(false);
        }//if the rocket loses all of its health it dies


        if(!isAlive()){
            getGame().rockets.remove(this);
            getGame().children.remove(this);
        }//delete the rocket if it is no longer alive


    }//end update


}//end rocket method
