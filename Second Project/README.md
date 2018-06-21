# FINAL PROJECT DELIVERY

## Architecture Design
<br />

### **Packages**
<br />


### Controller
---
<dl>
   <dt>GameController</dt>
      <dd>Logic of the game</dd>
      
   <dt>AIController</dt>
      <dd>Artificial Intelligence Logic of the game</dd>
</dl>

<br />


### Model
---
![Model UML](https://github.com/diogoyaguas/LPOO1718_T4G7/blob/master/Cluedo/UML/model.png)
<dl>
   <dtBoard</dt>
     
   <dt>Player</dt>
      
   <dt>Suggestion</dt>
   
   <dt>Card</dt>
   
   <dt>Character</dt>
   
   <dt>Division</dt>
   
   <dt>Weapon</dt>
   
   <dt>Position</dt>
   
   <dt>Tile</dt>
   
   <dt>Room</dt>
   
   <dt>RoomTile</dt>
   
   <dt>Entrance</dt>
     
</dl>
<br />

### View
---
![View UML](https://github.com/diogoyaguas/LPOO1718_T4G7/blob/master/Cluedo/UML/view.png)
<dl>
   <dt>GameView</dt>
      
   <dt>MainMenuView</dt>
      
   <dt>ModeGameView</dt>
      
   <dt>SinglePlayerView</dt>
   
   <dt>SingleplayerGameView</dt>
   
   <dt>MultiplayerView</dt>
   
   <dt>MultiplayerGameView</dt>
   
   <dt>AccusationView</dt>
   
   <dt>ChooseMenuView</dt>
   
   <dt>GameInputProcessor</dt>

### **Design Patterns**
<dl>
   <dt>SINGLETON</dt>
      
   <dt>STATE</dt>
</dl>

### **Design Decisions**
   For this project we decided to use the Model View Controller architectural pattern to help maintain the loose coupling between packages as well as providing an easy to comprehend and high mutability of the Project.
   
   Diogo Filipe da Silva Yaguas (up2016016165) 60%

   Tiago Pinho Cardoso (up201605762) 40%

## USER MANUAL

* Main Menu

![Menu](https://github.com/diogoyaguas/LPOO1718_T4G7/blob/master/Cluedo/images/mainmenu.png)

![mode](https://github.com/diogoyaguas/LPOO1718_T4G7/blob/master/Cluedo/images/mode.png)

![name](https://github.com/diogoyaguas/LPOO1718_T4G7/blob/master/Cluedo/images/name.png)

![nameInserted](https://github.com/diogoyaguas/LPOO1718_T4G7/blob/master/Cluedo/images/nameinserted.png)

![number](https://github.com/diogoyaguas/LPOO1718_T4G7/blob/master/Cluedo/images/number.png)

* PlayScreen

![Exemplo 1](https://github.com/diogoyaguas/LPOO1718_T4G7/blob/master/Cluedo/images/board.png)

![Exemplo 2](https://github.com/diogoyaguas/LPOO1718_T4G7/blob/master/Cluedo/images/showcards.png)

![Exemplo 3](https://github.com/diogoyaguas/LPOO1718_T4G7/blob/master/Cluedo/images/accusation.png)

![Exemplo 4](https://github.com/diogoyaguas/LPOO1718_T4G7/blob/master/Cluedo/images/result.png)


## SETUP/INSTALATION PROCEDURE

* Installation: download the game from this repositorie (Cluedo_com.cluedo.game.apk), go to your mobile device settings and allow installations from unknown sources and
click on the file and then click on install.

* Setup to open the project : Donwload Android Studio from [here](https://developer.android.com/studio/index.html) and then download the project from here,
open Android Studio and click on Open Existing Project and select the file with this project
