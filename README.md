# JChess 0.3

JChess is a simple Java library that allows the user to create digital chess boards and play games according to the standard chess rules.
Its main purpose is to be an easy starting point for anyone trying to interact with chess using Java, so they don't have to code the entire game from scratch.

By itself it will simply start a game of chess in the console, but its real purpose is to be used by other applications.

## Changelog

You can view a list of the most recent changes here [here](https://github.com/ArianDannemann/jchess/blob/master/changelog.md)

## Getting started

### Setting up the library

All you need to do in order to start using JChess is to download the latest build either from the git repo or directly via this link [here](https://github.com/ArianDannemann/jchess/blob/master/builds/jchess-0.3.jar).

After that you can create a chess board in your java application by creating a new instance of the `Board` class like this:

```
Board my_board = new Board();
```

You can always show the current state of the chess board in the console by calling

```
UI.printBoard(my_board);
```

Since there are no pieces on a board by default, it makes sence to generate the board using the `BoardManager` class, which will also allow you to interact with the board in all kinds of ways.
Let's print out the board afterwards to see the change:

```
my_board = BoardManager.generateBoard();
UI.printBoard(my_board);
```


To move pieces around, you can simply enter standard chess notated moves like this:

```
BoardManager.movePiece(Board board, new Position("e4"));
```

### Setting up a game

If you want to play an entire game, you can take a look at the `GameManager` class.
In there you will find a simple example to start a game and let the user input moves for both sides.

First it initializes a game by generating a board in the default position:

```
/**
 * Initializes our game
 */
public static void startGame()
{
    UI.println("Starting game...");
    board = BoardManager.generateBoard();
    gameLoop();
}
```

Then, you can simply get the user input repeatidly an let the `BoardManager` move the pieces for you:

```
/**
 * The core game loop
 */
private static void gameLoop()
{
    try (Scanner scanner = new Scanner(System.in))
    {
        // Print the current state of the board
        UI.printBoard(board);

        while (true)
        {
            String input;

            // Get the user input
            UI.print("Your input: ");
            input = scanner.nextLine();

            // Try to move a piece according to the user input
            try
            {
                BoardManager.movePiece(board, input);
            }
            catch (PieceNotFoundException exception)
            {
                UI.println("There is no piece at the specified position!");
                continue;
            }

            // Show the new position of the board
            UI.printBoard(board);
        }
    }
}
```
