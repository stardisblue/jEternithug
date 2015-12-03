package com.java.eternity;

import java.util.ArrayList;

/**
 * @author Fati CHEN <chen.fati@gmail.com>
 */
public class EternityII {

    /**
     * Piece grise désignant le bord
     */
    private static final Piece BORD = new Piece((short) 0, new byte[]{0, 0, 0, 0});
    private ArrayList<Piece> pieces;
    private Piece[][] table;

    /**
     * @param x int taille du tableau
     */
    public EternityII(ArrayList<Piece> pieces, int x) {
        this(pieces, x, x);
    }

    /**
     * @param x int largeur du tableau
     * @param y int hauteur du tableau
     */
    public EternityII(ArrayList<Piece> pieces, int x, int y) {
        this.pieces = pieces;
        this.table = new Piece[y][x];
    }

    public EternityII(ArrayList<Piece> pieces, Piece[][] table) {
        this.pieces = pieces;
        this.table = table;
    }

    public static Piece getBORD() {
        return BORD;
    }

    public Piece[][] getTable() {
        return table;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    /**
     * @param x int colonne
     * @param y int ligne
     * @return Piece grise ou la piece à la position x,y
     */
    public Piece getPiece(int y, int x) {
        return table[y][x];
    }

    /**
     * @param lookup String position relative
     * @param y      int Linge
     * @param x      int Colonne
     * @return La pièce positionnée relativement
     */
    public Piece getPiece(int lookup, int y, int x) {
        switch (lookup) {
            case 0:
                if (y > 0) return getPiece(y - 1, x);
                break;

            case 1:
                if (x + 1 < table[y].length) return getPiece(y, x + 1);
                break;

            case 2:
                if (y + 1 < table.length) return getPiece(y + 1, x);
                break;

            case 3:
                if (x > 0) return getPiece(y, x - 1);
                break;
        }

        return BORD;
    }

    /**
     * @param piece Piece à placer sur le table
     * @param x     int colonne
     * @param y     int ligne
     */
    public void setPiece(Piece piece, int y, int x) {
        table[y][x] = piece;
    }

    /**
     * @param piece Piece à placer sur le table
     * @param x     int colonne
     * @param y     int linge
     * @param top   int orientation de la piece
     */
    public void setPiece(Piece piece, int y, int x, byte top) {
        piece.setTop(top);
        setPiece(piece, y, x);
    }

    /**
     * @param y int colonne
     * @param x int ligne
     * @return retourne les pieces adjacente à la piece en x,y
     */
    public Piece[] GetNearPieces(int y, int x) {
        return new Piece[]{
                getPiece(0, y, x), getPiece(1, y, x), getPiece(2, y, x), getPiece(3, y, x)};
    }

    public byte[] getNearColors(int y, int x) {
        return new byte[]{
                getColorPiece((byte) 0, y, x), getColorPiece((byte) 1, y, x), getColorPiece((byte) 2, y, x),
                getColorPiece((byte) 3, y, x)};
    }

    public byte getColorPiece(byte lookup, int y, int x) {
        if (lookup < 4)
            if (getPiece(lookup, y, x) != null) return getPiece(lookup, y, x).getColor((byte) ((lookup + 2) % 4));
            else return -1;

        try {
            throw new Exception("Mauvaise recherche de couleur");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -2;
    }

    @Override
    public String toString() {
        String output = "";

        for (int i = 0; i < table.length; i++) {
            Piece[] aTable = table[i];
            for (Piece anATable : aTable) {
                if (anATable != null) {
                    output += anATable.toString() + ";";
                } else {
                    output += " ;";
                }
            }
        }

        return output;
    }

    public String toStringTriangle() {
        String output = "";

        int[][] coordonnees = new int[table.length * table.length][2];
        for (int i = 0; i < table.length * table.length; i++) {
            coordonnees[i] = Main.calculyx(i + 1, table.length);
        }

        for (int i = 0; i < table.length; i++) {
            if (table[coordonnees[i][0]][coordonnees[i][1]] == null) break;
            output += table[coordonnees[i][0]][coordonnees[i][1]].toString() + ";";
        }


        return output;
    }

    public String toStringJoli() {
        String output = "" + '\n';

        for (Piece[] lignes_pieces : table) {
            String output1 = "", output2 = "", output3 = "";

            for (Piece pieces : lignes_pieces) {
                if (pieces != null) {
                    int top = pieces.getColor((byte) 0),
                            right = pieces.getColor((byte) 1),
                            bot = pieces.getColor((byte) 2),
                            left = pieces.getColor((byte) 3);

                    output1 += " " + (top > 9 ? "" : " ") + top + "  |";
                    output2 += left + (left > 9 ? "" : " ") + " " + (right > 9 ? "" : " ") + right + "|";
                    output3 += " " + (bot > 9 ? "" : " ") + bot + "  |";
                } else {
                    output1 += "     |";
                    output2 += "     |";
                    output3 += "     |";
                }
            }
            output += output1 + '\n' + output2 + '\n' + output3 + '\n';
        }

        return output;
    }

    /**
     * @param piece   Piece a comparer
     * @param ligne   int position a verifier
     * @param colonne int position a verifier
     * @param lookup  sens de verification
     * @return true if the colors are equal
     */
    public boolean equalColor(Piece piece, int ligne, int colonne, byte lookup) {
        byte side = this.getColorPiece(lookup, ligne, colonne);
        byte piece_side = piece.getColor(lookup);

        return (side == -1 && piece_side != 0) || side == piece_side;
    }

}
