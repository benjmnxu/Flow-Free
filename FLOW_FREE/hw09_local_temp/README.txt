=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: benxu
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D array
  I have a 2D array that models the player's game board. Every time they draw a line, the coordinates (grid boxes) of
  the line are similarly recorded into the corresponding 2D array grid. For each different color, a different int is
  used to represent that color/spot in the grid. For example, "2" is red, "3" is blue, "1" is a spot the player needs
  to connect. Everytime a line is drawn from a spot, that "1" is reset as the color drawn from the spot. If a line is
  no longer connected, the line representation in the 2D array is also wiped into "0"s. "0" represents a blank space. If
  every spot on the board is no longer "1", we know that every spot has a line being drawn from it, and because each
  spot can only have its corresponding color draw from itself, every spot is connected. We use the grid to check this
  status. If true, the player wins the game stops.

  2. Collections
  I use three collections:
        private HashMap<org.cis1200.FlowFree.Coordinates, String> spots \\ keeps track of spots
        private HashMap<Integer, LinkedList<org.cis1200.FlowFree.Coordinates>> order \\ keeps track of order in which grid boxes are drawn
        private LinkedList<Color> colorDraw; \\ keeps track of order of what color is drawn

  I used a HashMap for spots in order to keep track of color and coordinates. I picked a HashMap for this feature
  because it allowed me the most easy access both spot colors and coordinates. I would need to use these features in
  tandem with my other methods to keep track of when the player connected spots they were supposed to as well as when
  they drew lines to spots of the incorrect color. The map also allowed me to add as many spots as I wanted without
  needing to manually resize anything. I also did not need to sort anything.

  I used a HashMap for order. Again, this is because I needed to keep track of lines and their colors. The integer as
  the key would translate to a certain color and the linked list within the map would serve as a path in which the line
  was drawn. Using a HashMap allows many lines to be drawn, without worrying about resizing, and easy access to match a
  certain color with their corresponding line.

  I used a linked list for colorDraw because I simply needed and order and a color. For every line drawn, I add that
  color to the front of the linked list. Then, when I click undo, the first color of the linked list is popped. This
  gives us the color to remove and the next time we call undo, we will be given the color drawn just before the one we
  previously undid. Linked list allows for as many lines to be drawn without needing to manually resize anything.

  3. File I/O
  This is used to save and load in game states. In the middle of every game, the player has the option to save the game
  and come back at a later time. To do this, the information stored in the grid, spots, order, and colorDraw are saved
  to a text file. Then, when the player comes back and reloads the game, all features remain exactly as when they left,
  including the undo function. We load the file in by reading the saved text file and using that information to
  reinitialize our "new" collections and arrays.

  4. JUnit Testing
  Because the game logic and graphics are separated, I extensively tested the individual parts of the game logic. I
  wrote several tests for each method, including edge cases like the singleton, empty, and null cases. Most particular
  tested was the behavior of the logic when spots and lines crossed one another. Tested core-game state

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  BoardsController - This class creates the frame and graphics for the game start screen, start menu screen, and game
  board screen. It adds all buttons and adds action listeners.
  Convert - This class custom converts between integers, strings, and colors.
  org.cis1200.FlowFree.Coordinates - This class stores an x and y value is used in the grid. Similar to point.
  FileLineIterator - Taken from TwitterBot HW 08. Used to read in lines from save/text files.
  FlowBoard - The board on which the game is played. Will draw lines, show events, provide user interface.
  FlowBoardController -The core-game state in which the game is checked for line integrity, winning, saving, and undoing.
  MenuButtonListener - Is an action listener that reads in the text of a JTextField and creates new game boards based on the input level
  Next Level - Creates a small JFrame to let the player know they have won and creates a button for them to move onto the next game.
  FlowTest - Tests the core-game state model

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

   I had significant stumbling blocks implementing the connection between the swing board and the controlling logic.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  There is a good separation of functionality and display. The private states are well encapsulated, as a copy is
  returned when needed as well as the direct reference when appropriate. The game is quite efficient and there is
  a dynamic state of function.





========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

  I downloaded the FLOW FREE game from the app store to study behavior and levels.
