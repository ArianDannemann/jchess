package org.jchess.model;

/**
 * This enum is only used when trying to figure out a move from the standard chess notation
 * If the piece that should be moved isn't clear, and its exact position isn't given there will be a hint at either its file or rank attached
 */
public enum FileRankHintType
{
    NONE,
    FILE,
    RANK
}
