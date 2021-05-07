package C19333393;

import processing.core.PImage;

public class BoomBox extends GameObject {
    private PImage bWoofer[], sWoofer[], boomBox;
    private float bIndex = 0, sIndex = 0;
    private float resScale;

    
    public BoomBox(float x, float y, float speed, DavidsGame game, float rotation, float scale, int hp) {
        super(x, y, speed, game, rotation, hp);
        this.resScale = scale;
        load();
    }//end constructor 

    
    public void update(){


        if(getGame().h > 80){
            sIndex = DavidsGame.map(getGame().h, 80, 800, 0, sWoofer.length - 1);
        }//end if to update the scale for the small speaker

        if(getGame().getSmoothedAmplitude()*100  > 10){
            bIndex = DavidsGame.map(getGame().getSmoothedAmplitude()*100, 10 ,50 ,0 ,bWoofer.length - 1);
        }//end if to update the scale for the big speaker


        setW(bWoofer[(int)(bIndex)].width *0.5f);
        setH(bWoofer[(int)(bIndex)].height *0.5f);
        setHalfW(getW()/2);
        setHalfH(getH()/2);

        if(getHp() <= 0){
            setAlive(false);
        }

    }//end update method


    public void load(){
        bWoofer = new PImage[10];
        sWoofer = new PImage[10];


        for(int i =0; i < bWoofer.length; i++){
            bWoofer[i] = getGame().loadImage("bigWoofer.png");
            float scale = DavidsGame.map(i, 0, bWoofer.length-1, 0.3f,0.5f);
            bWoofer[i].resize((int)(bWoofer[i].width*scale*resScale), (int)(bWoofer[i].height*scale*resScale));
        }//end for loop to poulate the woofer array


        for(int i =0; i < sWoofer.length; i++){
            sWoofer[i] = getGame().loadImage("smallWoofer.png");
            float scale = DavidsGame.map(i, 0, sWoofer.length-1, 0.25f,0.5f);
            sWoofer[i].resize((int)(sWoofer[i].width*scale*resScale), (int)(sWoofer[i].height*scale*resScale));
        }//end for loop to poulate the woofer array


        boomBox = getGame().loadImage("boomBox.png");
        boomBox.resize((int)(boomBox.width*0.5f*resScale), (int)(boomBox.height*0.5f*resScale));

    }//end the method for the loading of images


    public void render(){
        getGame().imageMode(DavidsGame.CENTER);
        getGame().rectMode(DavidsGame.CENTER);

        getGame().strokeWeight(25);
        getGame().fill(140,2,2);
        getGame().stroke(0);
        getGame().rect(getPosition().x + getPosition().x * .004f, getGame().height, boomBox.width*.6f, boomBox.height * .5f, 5 );
        getGame().line((getPosition().x + getPosition().x * .004f) -  (boomBox.width*.6f/2), getGame().height - boomBox.height * .175f, (getPosition().x + getPosition().x * .004f) +  (boomBox.width*.6f/2), getGame().height - boomBox.height * .175f);

        getGame().image(boomBox, getPosition().x, getPosition().y);
        getGame().image(bWoofer[(int)(bIndex)], getPosition().x, getPosition().y);
        getGame().image(sWoofer[(int)(sIndex)], getPosition().x, getPosition().y - boomBox.height/5);


    }//ends the render for the boom box
   
    
}//end boom box class
