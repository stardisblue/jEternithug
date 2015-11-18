package com.java.eternity;

import java.util.ArrayList;

/**
 * @author Fati CHEN <chen.fati@gmail.com>
 */
public class gameFactory {
    public static Piece piece(short number, byte[] couleurs) {
        return new Piece(number, couleurs);
    }

    public static Piece piece(short number, String[] couleurs) {
        return new Piece(number, couleurs);
    }

    public static Piece piece(short number, byte[] couleurs, byte top) {
        return new Piece(number, couleurs, top);
    }

    public static Piece piece(short number, String[] couleurs, byte top) {
        return new Piece(number, couleurs, top);
    }

    public static EternityII eternity(ArrayList<Piece> pieces, int size) {
        return new EternityII(pieces, size);
    }

    public static EternityII eternity(ArrayList<Piece> pieces, Piece[][] plateau) {
        return new EternityII(pieces, plateau);
    }

}
