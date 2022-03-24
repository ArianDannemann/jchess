# JChess 0.3

JChess is a simple Java library that allows the user to create digital chess boards and play games according to the standard chess rules.
Its main purpose is to be an easy starting point for anyone trying to interact with chess using Java, so they don't have to code the entire game from scratch.

By itself it will simply start a game of chess in the console, but its real purpose is to be used by other applications.

## Changelog

You can view a list of the most recent changes here [here](https://github.com/ArianDannemann/jchess/blob/master/changelog.md)

## Getting started

All you need to do in order to start using JChess is to download the latest build either from the git repo or directly via this link [here](https://github.com/ArianDannemann/jchess/blob/master/builds/jchess-0.3.jar).

After that you can create a chess board in your java application by creating a new instance of the `Board` class like this:

`Board my_board = new Board();`

You can always show the current state of the chess board in the console by calling

`UI.printBoard(my_board);`

Since there are no pieces on a board by default, it makes sence to generate the board using the `BoardManager` class, which will also allow you to interact with the board in all kinds of ways.
Let's print out the board afterwards to see the change:

`my_board = BoardManager.generateBoard();`

`UI.printBoard(my_board);`

To move pieces around, you can simply enter standard chess notated moves like this:

`BoardManager.movePiece(Board board, new Position("e4"));`
