# NIN2 Handin README
#### Fill out this README before turning in this project. Make sure to fill this out again for each assignment!
---
### Banner ID: B01645798
---
### Already uploaded demo onto Slack: Yes
---
## Primary Requirements:
| Requirement | Location in code or steps to view in game  |
|---|---|
| Your handin must meet all global requirements | ```Engine code: ``` |
| Your handin only crashes under exceptional circumstances (edge cases) | ```No bugs known``` |
| Your engine must correctly implement saving and loading through serialization. | ```saveComponent() method in components parses important component info into Element attributes``` |
| Your engine must correctly support raycasting for polygons and AABs. | ```Raycaster.java in /raycast``` |
| You must complete the debugger to demonstrate correct raycasting for polygons and AABs. | ```Debugger``` |
| Your player must be able to fire projectiles. | ```Clicking anywhere on the game fires a projectile in that direction``` |
| Your game must be loaded from a file. For this requirement, you can save your game using any file type, formatted as you please. You must provide at least one file that we can load in your game successfully.. | ```Loader.java in /saveload. Currently hardcoded to load test.xml inside /o2/files``` |
| The player-controller unit must be able to jump, but only when standing on top of a platform. | ```Can jump when pressing space in keyPressed inside CharacterControllerSystem. The jump() method inside PhysicsComponent checks that game object is grounded before applying upward velocity.``` |
| You must be able to save your game state, restart the game, and then load that game state. | ```Saving game state mainly just saves the player's position for now (since only the player has a SaveComponent). Pressing 'R' will bring up the save/load/restart menu.``` |
| The player must always be in view. | ```CenterComponent.java keeps player in view``` |
| It must be possible to start a new game without restarting the program. | ```Pressing 'R' will bring up the save/load/restart menu.``` |

## Secondary Requirements:
| Requirement | Location in code or steps to view in game  |
|---|---|
| Your engine must meet all primary engine requirements | ```Please see above``` |
| Your engine must correctly support raycasting for circles. | ```Line 73 in Raycaster.java``` |
| You must complete the debugger to demonstrate correct raycasting for circles. | ```Debugger``` |
| Save files must be written in XML format. This will help organize your saves, and also java has code for parsing these files. | ```Saver.java in enging/saveload parses into XML format``` |
| The player must be able to fire projectiles that travel instantly using raycasting. Projectiles must apply an impulse to whatever they hit in the direction of that ray. | ```Shooting at the enemy at the bottom of level will apply impulse in direction of ray``` |
| Your game must meet at least two of the extra game requirements. | ```The player and enemies are drawn with sprites (and animations when appropriate) instead of vector graphics. // Make a destructible environment, such as breakable blocks.``` |



--------------------------------------------------------------

Instructions on how to run
1. Go to Main.java inside Nin package and press play button on Line 11
2. WASD for player controls, SPACEBAR for jumping
3. Can only jump when player is on the platform ground
4. Can push and collide with dynamic objects.
5. Press 'R' to show save/load/restart menu


Known bugs: none

Hours spent on assignment: 15-17
