package com.java.eternity;

/**
 * @author Fati CHEN <chen.fati@gmail.com>
 */
public class Piece {

    public short number;
    /**
     * Contient les differentes couleurs de la piece
     */
    private byte[] colors;

    /**
     * Désinge l'orientation de la piece
     */
    private byte top;

    public Piece(Piece piece) {
        this(piece.number, piece.colors, piece.getTop());
    }

    public Piece(short number, byte[] colors) {
        this(number, colors, (byte) 0);
    }

    public Piece(short number, String[] colors) {
        this(number, colors, (byte) 0);
    }

    public Piece(short number, byte[] colors, byte top) {
        this.number = number;
        setTop((byte) (top % 4));
        this.colors = new byte[4];
        System.arraycopy(colors, 0, this.colors, 0, 4);
    }

    public Piece(short number, String[] colors, byte top) {
        this.number = number;
        setTop((byte) (top % 4));
        this.colors = new byte[4];
        for (int i = 0; i < 4; i++) {
            this.colors[i] = Byte.parseByte(colors[i]);
        }
    }

    /**
     * @return les couleurs de la piece orientée
     */
    public byte[] getColors() {
        byte[] couleurs_oriente = new byte[4];
        for (int i = 0; i < 4; i++) {
            couleurs_oriente[i] = colors[(i + getTop()) % 4];
        }
        return couleurs_oriente;
    }

    public byte getColor(byte position) {
        if (position < 4) return colors[(getTop() + position) % 4];
        try {
            throw new Exception("Erreur Critique de getcouleur");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -2;
    }

    /**
     * @return l'orientation de la piece (0-3)
     */
    public byte getTop() {
        return top;
    }

    public void setTop(byte top) {
        this.top = (byte) (top % 4);
    }

    @Override
    public String toString() {
        return number + "";
    }

    public String toStringJoli() {
        return number + ">" + top;
    }
}
