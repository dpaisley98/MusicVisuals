package C19333393;


public class Platform {
    float x, y, w, h;
    boolean hasCollided;
    DavidsGame game;
    int c;


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
            if(c == game.color(165,214,167)){


                if((p.getRight() >= x - (w/2) && p.position.x <= x) && (p.getBottom() <= y + (h/2) && p.position.y > y - (h/2))){

                    hasCollided = true;
                    return "LEFT";
                }else if((p.getLeft() <= x + (w/2) && p.position.x >= x ) && (p.getBottom() <= y + (h/2) && p.position.y > y - (h/2))){

                    hasCollided = true;
                    return "RIGHT";
                }else if(p.position.y <= y + (h/2) && p.position.y >= y && ((p.getLeft() < x + (w/2)) && p.getRight() > x - (w/2))){
                    hasCollided = true;
                    return "BOTTOM";
                }//end of else if statements checking whether the player has collided with either the left of the border, the right of it, or the top
    

            }else{


                if((p.getBottom() >= y - (h/2) && p.position.y <= y) && (p.getLeft() < x + (w/2) && p.getRight() > x - (w/2))){

                    hasCollided = true;
                    return "TOP";
                }//if statement to check if the user is on top of a platform
            }//ends if else checking if the object is a part of the border or a platform


        }else{


            if(o.position.y >= y - (h/2) && o.position.y <= y && ((o.position.x < x + (w/2)) && (o.position.x > x - (w/2)))){

                hasCollided = true;
                return "TOP";
            }else if((o.position.x >= x - (w/2) && o.position.x <= x) && (o.position.y < y + (h/2) &&  o.position.y > y - (h/2))){

                hasCollided = true;
                return "LEFT";
            }else if((o.position.x <= x + (w/2) && o.position.x >= x) && (o.position.y < y + (h/2) &&  o.position.y > y - (h/2))){
    
                hasCollided = true;
                return "RIGHT";
            }else if(o.position.y <= y + (h/2) && o.position.y >= y && ((o.position.x < x + (w/2)) && (o.position.x > x - (w/2)))){
    
                hasCollided = true;
                return "BOTTOM";
            }


        }//ends if/else checking if the object is a player controlled object or not 


        hasCollided = false;
        return "NO COLLISION";
    }//end checking collisions

    
}//end platfrom class
