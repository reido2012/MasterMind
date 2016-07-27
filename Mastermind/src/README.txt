MasterMind

What is it?
------------
A text based game built in Java based on the board game Mastermind invented by Mordecai Meirowitz.
Maximize your terminal/console/command prompt for best effect when playing.

Features
---------
Intelligent AI Guesses the correct color for a game of 8 colors and 8 hidden pegs in 13 guesses on average.
Player can load and save game.
You can start a new game once after you finish a game.
Text based interface and colored text is displayed for guesses. Only for the first 5 colors.
If there is a console then when user enters the code, the console treats input like a password and it isn't displayed.

You can play as:
1.) Human Code Breaker vs Human Code Maker
2.) Computer Code Maker vs Human Code Breaker
3.) Computer Code Maker vs Human Code Breaker

The user is able to specify the number (between 3-8) of colours in the game.
The user is able to specify the number (between 3-8) of pegs being hidden.
The user can also specify the number of trials (4 -30) they require to break code.
Start Menu and End Game Menu.
Ascii Art.

How to run game
-----------------
If you are using a console or terminal window you can run the game by using the following commands:
javac Mastermind.java to compile the program and java Mastermind to run the program.

Rules
-------
A human player or a computer will make a code for a Code Breaker to break.
The code is represented by colored pegs or in this case r for red, g for green, b for blue, y for yellow etc.
The letter will turn into the color with which it corresponds when displayed by the game.

The Code Breaker will then attempt to guess what the code is. After every guess the player is given feedback.
This feedback can be interpreted as follows.
A colored(c) peg for each code peg from the guess which is correct in both color and position.
A white(w) peg indicates the existence of a correct color code peg placed in the wrong position.

If the Code Breaker guesses the code correctly before they run out of trials the Code Breaker wins,
otherwise the Code Maker wins.

Credit
-------
Everything was done by Omar Reid.
Website: http://oreid.net/
Twitter: https://twitter.com/Reido2012
LinkedIn: https://www.linkedin.com/in/oreid52




