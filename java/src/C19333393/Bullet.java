package C19333393;

public class Bullet extends GameObject{
    
    float lifetime, timeAlive;
    float scaleW, scaleH;
    float posX, posY;

    public Bullet(float x, float y, float speed, DavidsGame game, float scaleW, float scaleH, float rotation) {
        super(x, y, speed, game, rotation);
        this.scaleW = scaleW;
        this.scaleH = scaleH;
        lifetime = 5;
        timeAlive = 0;
        w = 10;
        halfW = w/2;
    }

    public void render(){
        game.stroke(0,0,0);
        game.pushMatrix();
        game.translate(position.x, position.y);
        game.rotate(rotation);
        game.line(0, -5, 0, 5);
        game.popMatrix();
    }

    public void update(){
        direction.x =  DavidsGame.sin(rotation);
        direction.y =  - DavidsGame.cos(rotation);

        position.x += direction.x * (speed*4);
        position.y += direction.y * (speed*4);

        halfW = ((position.y + 5) - (position.y - 5))/2;

        timeAlive += (1 / 60.0f);

        if (timeAlive > lifetime){
            game.bullets.remove(this);
            game.children.remove(this);

        }
        if (position.x < 0){
          //  x = yasc.width;
            game.bullets.remove(this);
            game.children.remove(this);

        }
        if (position.x > game.width){
        //    x = 0;
            game.bullets.remove(this);
            game.children.remove(this);

        }
        if (position.y < 0 ){
        //    y = yasc.height;
            game.bullets.remove(this);
            game.children.remove(this);

        }
        if (position.y > game.height){
        //    y = 0;
            game.bullets.remove(this);
            game.children.remove(this);

        }

       // yasc.line(posX, posY-5, posX, posY+5);


    }

    public void load(){}


}
