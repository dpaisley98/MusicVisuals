package C19333393;


public class Platform {
    private float x, y, w, h;
    private DavidsGame game;
    private int c;


    public Platform(float x, float y, float w, float h, DavidsGame game, int c) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.game = game;
        this.c = c;
    }//end platform constructor 


    void render(){
        game.noStroke();
        game.fill(c);
        game.rectMode(DavidsGame.CENTER);
        game.rect(x, y, w, h);
    }//end rendering


    String checkCollision(GameObject o){


        if(o instanceof Player){
            Player p = ((Player)(o));

            /*if the colour of the platform is green it is apart of the border, 
            and will be checked first before the other platform objects as they 
            have different affects to the player*/
            if(c == game.color(0, 105, 93)){


                if((p.getRight() >= x - (w/2) && p.getPosition().x <= x) && (p.getBottom() <= y + (h/2) && p.getPosition().y > y - (h/2))){

                    return "LEFT";
                }else if((p.getLeft() <= x + (w/2) && p.getPosition().x >= x ) && (p.getBottom() <= y + (h/2) && p.getPosition().y > y - (h/2))){

                    return "RIGHT";
                }else if(p.getPosition().y <= y + (h/2) && p.getPosition().y >= y - (h/2) && ((p.getLeft() < x + (w/2)) && p.getRight() > x - (w/2))){

                    return "BOTTOM";
                }//end of else if statements checking whether the player has collided with either the left of the border, the right of it, or the top
    

            }else{

                if((p.getBottom() >= y - (h/2) && p.getPosition().y <= y + (h/2)) && (p.getLeft() < x + (w/2) && p.getRight() > x - (w/2))){

                    return "TOP";
                }//if statement to check if the user is on top of a platform
            }//ends if else checking if the object is a part of the border or a platform


        }else{


            if(o.getPosition().y >= y - (h/2) && o.getPosition().y <= y && ((o.getPosition().x < x + (w/2)) && (o.getPosition().x > x - (w/2)))){

                return "TOP";
            }else if((o.getPosition().x >= x - (w/2) && o.getPosition().x <= x) && (o.getPosition().y < y + (h/2) &&  o.getPosition().y > y - (h/2))){

                return "LEFT";
            }else if((o.getPosition().x <= x + (w/2) && o.getPosition().x >= x) && (o.getPosition().y < y + (h/2) &&  o.getPosition().y > y - (h/2))){
    
                return "RIGHT";
            }else if(o.getPosition().y <= y + (h/2) && o.getPosition().y >= y && ((o.getPosition().x < x + (w/2)) && (o.getPosition().x > x - (w/2)))){
    
                return "BOTTOM";
            }//else if statements used to check if the object has hit a platform


        }//ends if/else checking if the object is a player controlled object or not 


        return "NO COLLISION";
    }//end checking collisions


    /************************************************GETTERS************************************************/

    public float getX() {
        return x;
    }//end getter


    public float getY() {
        return y;
    }//end getter


    public float getW() {
        return w;
    }//end getter


    public float getH() {
        return h;
    }//end getter


    public int getC() {
        return c;
    }//end getter

    
}//end platfrom class
