package com.java.eternity;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class Main {

    public static final String ASSETS = "assets/";
    public static int[][] coordonnees;

    public static int[] calculyx(int position, int size) {
        int sum = 0;
        for (int i = 1; i <= size; i++) {
            sum += i;
            if (sum >= position) {
                int diff = sum - position;
                return new int[]{i - diff - 1, diff};
            }
        }

        int pos_inverse = (size * size) - (position - 1);
        sum = 0;

        for (int i = 1; i <= size; i++) {
            sum += i;
            if (sum >= pos_inverse) {
                int diff = sum - pos_inverse;
                return new int[]{(size - 1) - (i - diff - 1), (size - 1) - diff};
            }
        }

        return new int[]{0, 0};
    }


    /**
     * @param file_path String
     * @return ArrayList pieces
     * @throws IOException
     */
    public static EternityII createFromFile(String file_path) throws IOException {
        InputStream fis;
        fis = new FileInputStream(file_path);
        InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(isr);

        int line_number = 1;
        int table_size = 0;
        ArrayList<Piece> pieces = new ArrayList<>();
        String line;

        while ((line = br.readLine()) != null) {
            if (line_number == 1) {
                table_size = Integer.parseInt(line.split(" ")[0]);
            } else if (line_number > 4) {
                String[] numbers = line.split(" ");
                if (numbers.length == 4) {
                    pieces.add(gameFactory.piece((short) (line_number - 4), numbers));
                }
            }
            line_number++;
        }

        return gameFactory.eternity(pieces, table_size);
    }

    /**
     * Compte le nombre de cas possibles
     *
     * @param pieces ArrayList Pieces du jeu
     * @param size   int Taille du trianble recherché
     */
    public static void calculBruteForce(ArrayList<Piece> pieces, int size, int triangle) {

        File file_output = new File(ASSETS + "t_" + triangle + "-pieces_" + size + ".txt");
        try {
            FileUtils.write(file_output, "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Piece[][] plateau = new Piece[size][size];
        EternityII jeu = gameFactory.eternity(pieces, plateau);

        coordonnees = new int[size * size][2];
        for (int i = 0; i < size * size; i++) {
            coordonnees[i] = calculyx(i + 1, size);
        }

        ArrayList<String> possibles = new ArrayList<>();
        // jeu.setPiece(pieces.remove(0), 0, 0, (byte) 1);
        calculRecursifTriangle(triangle, 1, jeu, possibles);
        System.out.println(possibles.size());

        String string_output = "";
        try {
            for (int i = 0; i < possibles.size(); i++) {
                string_output += possibles.get(i) + '\n';
                if (i % 1024 == 0) {
                    FileUtils.write(file_output, string_output, true);
                    string_output = "";
                }
            }

            FileUtils.write(file_output, string_output, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void calculRecursif(int size, int position, EternityII jeu, ArrayList<String> possible)
    {
        if (position <= (size * size)) {
            int[] xy = coordonnees[position - 1];
            int col = xy[1];
            int line = xy[0];

            ArrayList<Piece> pieces = jeu.getPieces();
            for (int i = 0, piecesSize = pieces.size(); i < piecesSize; i++) {
                Piece piece = pieces.get(i);
                for (byte j = 0; j < 4; j++) {
                    piece.setTop(j);
                    if (jeu.equalColor(piece, line, col, (byte) 0) && jeu.equalColor(piece, line, col, (byte) 1) &&
                        jeu.equalColor(piece, line, col, (byte) 2) && jeu.equalColor(piece, line, col, (byte) 3))
                    {
                        Piece[][] plateau = new Piece[size][size];
                        for (int k = 0; k < size; k++)
                            System.arraycopy(jeu.getTable()[k], 0, plateau[k], 0, size);

                        ArrayList<Piece> pieces_recur = new ArrayList<>(pieces);
                        pieces_recur.remove(i);

                        EternityII jeu_recur = gameFactory.eternity(pieces_recur, plateau);
                        jeu_recur.setPiece(piece, line, col);

                        calculRecursif(size, position + 1, jeu_recur, possible);
                        jeu_recur = null;
                    }
                }
            }

            jeu = null;
        } else if (position == (size * size) + 1) {
            possible.add(jeu.toString());
        }
    }

    public static void calculRecursifAmeliore(int size, int position, EternityII jeu, ArrayList<String> possible)
    {
        if (position <= (size * size)) {
            int[] xy = coordonnees[position - 1];
            int col = xy[1];
            int line = xy[0];

            ArrayList<Piece> pieces = jeu.getPieces();
            for (int i = 0, piecesSize = pieces.size(); i < piecesSize; i++) {
                Piece piece = pieces.get(i);
                for (byte j = 0; j < 4; j++) {
                    piece.setTop(j);
                    if (jeu.equalColor(piece, line, col, (byte) 0) && jeu.equalColor(piece, line, col, (byte) 1) &&
                        jeu.equalColor(piece, line, col, (byte) 2) && jeu.equalColor(piece, line, col, (byte) 3))
                    {
                        pieces.remove(i);
                        jeu.setPiece(piece, line, col);

                        calculRecursifAmeliore(size, position + 1, jeu, possible);

                        jeu.setPiece(null, line, col);
                        pieces.add(i, piece);
                    }
                }
            }
            jeu = null;
        } else if (position == (size * size) + 1) {
            possible.add(jeu.toString());
        }
    }

    public static void calculRecursifTriangle(int triangle, int position, EternityII jeu, ArrayList<String> possible)
    {
        if (position <= triangle * (triangle + 1) / 2) {
            int[] xy = coordonnees[position - 1];
            int col = xy[1];
            int line = xy[0];

            ArrayList<Piece> pieces = jeu.getPieces();
            for (int i = 0, piecesSize = pieces.size(); i < piecesSize; i++) {
                Piece piece = pieces.get(i);
                for (byte j = 0; j < 4; j++) {
                    piece.setTop(j);
                    if (jeu.equalColor(piece, line, col, (byte) 0) && jeu.equalColor(piece, line, col, (byte) 1) &&
                        jeu.equalColor(piece, line, col, (byte) 2) && jeu.equalColor(piece, line, col, (byte) 3))
                    {
                        pieces.remove(i);
                        jeu.setPiece(piece, line, col);

                        calculRecursifTriangle(triangle, position + 1, jeu, possible);

                        jeu.setPiece(null, line, col);
                        pieces.add(i, piece);
                    }
                }
            }
            jeu = null;
        } else if (position == (triangle * (triangle + 1) / 2) + 1) {
            possible.add(jeu.toStringTriangle());
        }
    }

    public static void main(String[] args) {
        try {

// 4X4
/*            for (int taille = 4; taille < 10; taille++) {
                for (int triangle = 2; triangle <= 4; triangle++) {
                    EternityII jeu = createFromFile(ASSETS + "pieces_0" + taille + "x0" + taille + " bis.txt");
                    calculBruteForce(jeu.getPieces(), taille, triangle);
                    jeu = null;
                }
            }*/

            for (int triangle = 2; triangle <= 4; triangle++) {
                EternityII jeu = createFromFile(ASSETS + "pieces_10x10 bis.txt");
                calculBruteForce(jeu.getPieces(), 10, triangle);
                jeu = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
