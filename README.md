# Music Visualiser Project

Name: David Paisley

Student Number: C19333393

# Description of the assignment
This assignment was to create a Java program utilizing music, it could be a game or music visualizer, as long as it interacted with the music. I decided to program a video game which would spawn enemies dynamically based on the frequency obands of the music. Initially the gam had a much wider scope but this assignment has taught me how to lower the scope and complexity of my project based on time. The game has you control a small green robot with a turret so powerful the recoil will send you flying if you're in the air, and are tasked on destroying a stereo playing music to spawn in rockets to kill the player. To achieve a win state in the game you must either kill the boss or survive the duration of the song. 

I learnt a lot of different technioques and have gained a deeper understanding of various OOP ideas such as polymorphism which was very useful in programming my game as I could use one for loop to update every instance of an object and then specify inside the loop various if statements to check for specific instyances of an object i.e. looking for the player object in the array when checking for collision to output a different outcome than if a bullet has collidied with an object. I also learnt a small amount of how to draw pixel art and to aniumate which weas my first time doing anything like that and was pleasantly syrpriused the outcome wasn't the worst thing in the whole wide world. It was fun to come up with different designs for the player and enemies, though speaking of the cutting room floor there were a lot more enemies I had to cut due to not quite having the skill nor the time to implement them in an engaging way so I stuck to just the rockets. 

# Instructions
When you load the game you will be greeted with a pause meny telliong you to press the 'P' key to play the game. The controls are to press either 'A' to go left or 'D' to go right and SPACE to jump. You aim with the mouse and click the mouse button to fire. Yopu have a finite amount of health and must defeat the boss before you take too much damage, the game will track how many times you have died each time you play through it. To fly with the recoil to gain a height advantage you you jump and then aim in the opposite direction of where you want to go and fire the gun which will send you flying in the opposite direction of your bullets.

## Controls
- SPACE : Jump
- A : Move Left
- D : Move Right
- Mouse Cursor : Aim
- Click Mouse Button : Fire Turret 
- P : Pause/Play
- R : Reset the Game/Play Again

# How it works
The game works by using a combination of various OOP principles such as encapsulation, inheritance, and polymorphism. Imported packages from the Java library such as Java Processing used to draw the game, and DDF Minim used to play the music. The main file loads a class called 'DavidsGame' which extends a class called 'Visual' inheriting all of its methods and attributes. This makes the 'DavidsGame' class the focal point which will be executing the code, drawing the game, playing the audio, and updating values. It houses a number of variables, as well as four ArrayLists, which are arrays which can be dynamically updated whenever something has been added or removed. These will be object arrays of the various classes created for the game, such as the Rocket class holding every rocket created which is used to check every rocket against every bullet to see if a collision has occurred. A 'GameObject' array which all other classes except the platform class inherit aloowing every object in the game to be tracked within one array, a 'Platform' array which will keep track of all the terrain the player can stand on, and finally a 'Bullet' array for when I need to iterate through all the bullets only.

The game calls in a library called 'PImage' which will load in various images and will be used import in my sprites as well as animate the player character, all of these images are loaded in within the setup so that the game doesn't have its performance tank while running constantly having to load in new images. The image for the rocket is loaded in 'DavidsGame' since otherwise it'd be loaded when an object was created and the rocket obejcts are made based on the music so they needed to have their images loaded separately from their class. 

The draw method has a switch state which is used to determine what to show to the player, if the game is paused the state will be '0' which will call the pause menu, or if the state is '1' it will call the game method which is where all of the values are upadated and the objects are rendered. Inside the game method there are three for loops, as well as a where the game gets the values which will be used to spawn enemeis and check the win/lose state of the game. The for loop will iterate over every object that is inheriting the 'GameObject' class and will render each one as well as check for any collision for every object, for the player this means running through the 'Platform' array to compare the co-ordinates of the player to each platform and see if they collide, if they do the player will either stand on top of the platform or hit his head on the underside depending on the colour of the object. For bullets it's similar but they also check if they have collided with any rockets and if they have the willl take some of the rocket's health away. The rockets just check if they have hit the player and will take some of the player's health and the 'BoomBox' ob have its health taken away if a bullet hits it. 

The a for loop to render the platforms, these are not apart of the 'GameObject' array because they don't need to be updated nor do they have to move and have to be rendered after every other object. The last loop will update every object, deleting any from the array that have died. If the player has died the game ends and the player loses, if the boombox dies the game ends and the player wins, or if they survive the duration of the song. 

# What I am most proud of in the assignment
What I'm most proud of when it comes to this assignment is the fact that I was able to manage having every obejct be updated and displayed within one or two loops, as well as the fact I was able to have the game visually and mechanically interact with the music. The player character was also surprisingly good once I finished drawing it, I had no expectations on the outcome as the last time I ever drew something was when I didn't know my times tables, so I was proud I managed to  create something that isn't terrible to look at. 

# Explanation of Code
I'll explain some of the crucial parts of the program below with snippets of the Java to show how the program works. 

## Draw Method
The draw method calculates the frequency bands and uses a switch to determine what to draw to the screen

```Java
public void draw(){
	try{
	    calculateFFT(); 
	}catch(VisualException e){
	    e.printStackTrace();
	}//end try catch to calculate the FFT

	calculateFrequencyBands(); 
	calculateAverageAmplitude();


	stroke(255);
	strokeWeight(2);


	switch(state){
	    case 0:{
		menu();
		break;
	    }//this case runs once theplayer pauses the game
	    case 1:{
		cursor(CROSS);
		game();
		break;
	    }//this case runs once the game is playing
	    case 2:{
		gameOver();
		break;
	    }//this case runs once the player is defeated
	    case 3:{
		gameWon();
	    }//this case only rins once the boss is defeated

	}//end switch state


}//end draw method
```

## Checking Collision of Each Bullet
This is an if statement within the for loop of game objects to see if a bullet has hit another object. The first for loop will check it against every platform which will return a string of either "LEFT", "RIGHT", "TOP", "BOTTOM", or "NO COLLISION" if nothing has been hit. If it has hit any piece of terrain then the bullet dies. The next for loop will check the bullet against every rocket and if the method collides returns a true then the object the bullet hits will take damage and the bullet will die. This code is similar for rockets, though they don't detect collision with platforms as that would make the game too easy since you could simply stand on the top left or right platform and they'd all die hitting the terrain. 

```Java
if(o instanceof Bullet){

	for(Platform platform : plats){
	    s = platform.checkCollision(o);
	    if(s != "NO COLLISION"){
		o.setAlive(false);
	    }//end if to destroy the bullet if it has collided with a platform
	}//end for loop to iterate through all platforms

	for(int j = 0; j < rockets.size(); j++){
	    Rocket ro = rockets.get(j);
	    if(o.collides(ro)){
		ro.setHp(ro.getHp() - 1);
		o.setAlive(false);
	    }//end if to check if a bullet has collided with an enemy
	}//end for loop to iterate through all enemies

	if(o.collides(b)){
	    b.setHp(b.getHp() - 1);
	    o.setAlive(false);
	}//when a bullet hists the boom box speaker it takes away some health and will destroy the bullet

}//end if statement to check if the object is a bullet
```

## The Method Every Object Uses to Detect Collision with Other Game Objects
This method creates a circumference around each object and if the combined radii is less than the distance between the two objects then that would mean the two objects have collided. 
```Java
boolean collides(GameObject object){

	float combW = halfW + object.halfW;

	return PVector.dist(position, object.position) < combW;
}//end method to check for any collision between two objects
```

## Checking Collision of the Player in Regards to Platforms
This code is similar to the collsion for a bullet but will only track the player's collision with the terrain, the reason for this is because the player doesn't need to keep track of the other objects as the only one that will have an effect upon colliding with the player are the rockets which will check against the player in their own if statemnent since the loop will iterate through them as well. 
```Java
if(o instanceof Player){
	Player robot = ((Player)(o));

	if(!robot.isAlive()){
	    timesLost++;
	    state = 2;
	}//if the player isn't alive the game ends and the counter for player deaths is increased

	for(Platform platform : plats){
	    s = platform.checkCollision(robot);

	    switch(s){
		case "BOTTOM":{
		    if(platform.getC() == color(0, 105, 93)){
			robot.tapTop(platform.getY() + platform.getH()/2);
		    }//if statement to check if the platform is apart of the border as normal platforms allow you too jump up through them
		    break;
		}//case to see if the player has hit the bottom of a platform
		case "TOP":{
		    robot.land(platform.getY() - platform.getH()/2);
		    break;
		}//case to see if the player has landed on the top of a platform
		case "LEFT":{
		    robot.hitWall(platform.getX() - platform.getW()/2, s);
		    break;
		}//case to see if the player has hit the left of a wall or platform
		case "RIGHT":{
		    robot.hitWall(platform.getX() + platform.getW()/2, s);
		    break;
		}//case to see if the player has hit the right of a wall or platform

	    }//end switch statement checking collision with platforms

	}//end for loop to iterate through all platforms

}//end if statement to check the player's collision
```

## Method to Spawn Rockets
Once the frequency bands meet a certain level this method is called randomly spawning in a rocket somehwere on the level, as long as it's on the opposite side of the level in regards to the player's x co-ordinate. 
```Java
private void spawnRockets() {
	float rocketX, rocketY;

	if(p.getPosition().x < centerX){
	    rocketX = random(centerX, width);
	}else{
	    rocketX = random(0, centerX);
	}

	rocketY = random(height/2);

	Rocket r = new Rocket(rocketX, rocketY, 4, this, 0, p, 5);

	rockets.add(r);
	children.add(r);

}//method to spwan rockets wherever the player isn't
```

## Method to Reset the Game
When either the game is paused or the player has won/lost the game they will be given the opportunity to restart the game from the beginning which will wipe all objects from the array except for the player and the boom box which will have their values reset to the default ones and the song will start from the beginning once again. 
```Java
void gameReset(){

	for(int i = children.size()-1; i>=0; i--){
	    GameObject o = children.get(i);

	    if(o instanceof Player || o instanceof BoomBox){
		o.setHp(o.getMaxHp());
		o.getPosition().x = centerX;
		o.getPosition().y = centerY;
		o.setAlive(true);
	    }else{
		o.setAlive(false);
		o.update();
	    }

	}//end for loop to remove all objects

	loadAudio("AlmostEvil.mp3");
	getAudioPlayer().play();
	getAudioPlayer().rewind();


	state = 1;

}//method to reset the game's values and allow the player another go at the game
```

## Code to Animate the Player
When the player is first created it loads in the images it will need, these are placed in respoective arrays for each individual part of the robot, such as the tank threads, the gun, the thruster pack. Then if the player jumps the index will move through each record in the array causing it to animate through a set of frames, whenever the player moves right it will iterate through the array by one element, and the same if you move left but in reverse. 
```Java
public void load(){

	if(isMovingLeft){
	    if(delay == 0){
		threadFrame--;
		if(threadFrame<0){
		    threadFrame = threads.length-1;
		}
	    }
	}else if (isMovingRight){
	    if(delay == 0){
		threadFrame = (threadFrame + 1) % threads.length;
	    }
	}//else if statement to animate the tankl threads


	if(isShooting){
	    if (delay == 0){
		turretFrame = (turretFrame +1) % turret.length;
	    }
	}//if statement to animate the turret


	if(jump && in < 6){
	    if (delay == 0){
		backPackFrame = (backPackFrame + 1) % turret.length;
		in++;
	    }
	}//animate thruster when the player jumps


	delay = (delay + 1) % 3;

}//end method to animate the player
```

## Code to Allow the Player to Shoot
Whenever the player is holding down the mouse a bullet will be created, its size is based on the size of the screen so as to allow for scaling depending on the monitor being used. It then takes these values as well as the rotation of the turret to know where to fire the bullet from. The recoild flight method will run code causing the player to fly if they are in the air, aloowing for the power of the gun's recoil to send the player flying into the air. It does this by getting the angle or direction the bullet is heading and inversing these values and adding them to the player's ax and y values. 
```Java
void shoot(){

	if (getGame().mousePressed){
	    isShooting = true;
	    if((getGame().frameCount % fireRate) == 0){
		float dist = 10;

		float scaleW = body.width/2;
		float scaleH = body.height/2;

		Bullet b = new Bullet(getPosition().x + (getDirection().x * dist)+(body.width*.4f), getPosition().y + (getDirection().y * dist)-(body.height*.1f), 
		10, getGame(), scaleW, scaleH, getRotation(), 1);

		getGame().bullets.add(b);
		getGame().children.add(b);
		terminalVel = 20;
	    }
	}else{

	    isShooting = false;
	    terminalVel = 60;
	}//end else if statement 

}//end shooting method


void recoilFlight(){

	getDirection().x = - DavidsGame.sin(getRotation());
	getDirection().y =   DavidsGame.cos(getRotation());

	getPosition().x += getDirection().x * (velocity);
	getPosition().y += getDirection().y * ((grav * velocity));

	timer = 25;

}//end recoil method

```


Here is a video showing the game in action.

[![YouTube](https://i.ytimg.com/vi/DxMbM73kMeU/maxresdefault.jpg)](https://youtu.be/DxMbM73kMeU)
