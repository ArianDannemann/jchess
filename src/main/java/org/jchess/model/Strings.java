package org.jchess.model;

/**
 * This class contains a list of all strings used inside the application
 */
public class Strings
{
    public static String APP_NAME = "jchess";
    public static String APP_VERSION = "0.2";
    public static String APP_PREFIX = "[" + APP_NAME + "-" + APP_VERSION + "] ";

    public static String INVALID_FEN = "Invalid FEN. No board could be created from the input FEN, which means that either an incorrect format or an illegal character were used.";
    public static String PIECE_OUT_OF_BOUNDS = "A piece was placed outside of the play area";
    public static String PIECE_NOT_FOUND = "There is no piece at the specified position";
}
