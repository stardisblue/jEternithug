package com.java.eternity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author Fati CHEN <chen.fati@gmail.com>
 */
public class EternityIITest {
    EternityII game;

    @Before
    public void setUp() throws Exception {
        ArrayList<Piece> pieces = new ArrayList<>();

        game = gameFactory.eternity(pieces, 3);
        game.setPiece(gameFactory.piece((short) 1, new byte[]{0, 0, 4, 6}, (byte) 1), 0, 0);
        game.setPiece(gameFactory.piece((short) 2, new byte[]{3, 1, 1, 2}), 1, 1);
        game.setPiece(gameFactory.piece((short) 3, new byte[]{1, 1, 0, 0}, (byte) 1), 2, 2);
        game.setPiece(gameFactory.piece((short) 4, new byte[]{0, 2, 3, 4}), 0, 1);
    }

    @Test
    public void testGetPiece() throws Exception {
        Assert.assertEquals(EternityII.getBORD(), game.getPiece((byte) 0, 0, 0));
        Assert.assertEquals(game.getPiece(0, 1), game.getPiece((byte) 1, 0, 0));
        Assert.assertNull(game.getPiece((byte) 2, 0, 0));
        Assert.assertEquals(EternityII.getBORD(), game.getPiece((byte) 3, 0, 0));

        Assert.assertEquals(game.getPiece(0, 1), game.getPiece((byte) 0, 1, 1));
        Assert.assertNull(game.getPiece((byte) 1, 1, 1));
        Assert.assertNull(game.getPiece((byte) 2, 1, 1));
        Assert.assertNull(game.getPiece((byte) 3, 1, 1));

        Assert.assertNull(game.getPiece((byte) 0, 2, 2));
        Assert.assertEquals(EternityII.getBORD(), game.getPiece((byte) 1, 2, 2));
        Assert.assertEquals(EternityII.getBORD(), game.getPiece((byte) 2, 2, 2));
        Assert.assertNull(game.getPiece((byte) 3, 2, 2));
    }

    @Test
    public void testSetPiece() throws Exception {

    }

    @Test
    public void testGetColorPiece() throws Exception {
        Assert.assertEquals(0, game.getColorPiece((byte) 0, 0, 0));
        Assert.assertEquals(4, game.getColorPiece((byte) 1, 0, 0));
        Assert.assertEquals(-1, game.getColorPiece((byte) 2, 0, 0));
        Assert.assertEquals(0, game.getColorPiece((byte) 3, 0, 0));

        Assert.assertEquals(3, game.getColorPiece((byte) 0, 1, 1));
        Assert.assertEquals(-1, game.getColorPiece((byte) 1, 1, 1));
        Assert.assertEquals(-1, game.getColorPiece((byte) 2, 1, 1));
        Assert.assertEquals(-1, game.getColorPiece((byte) 3, 1, 1));

        Assert.assertEquals(-1, game.getColorPiece((byte) 0, 2, 2));
        Assert.assertEquals(0, game.getColorPiece((byte) 1, 2, 2));
        Assert.assertEquals(0, game.getColorPiece((byte) 2, 2, 2));
        Assert.assertEquals(-1, game.getColorPiece((byte) 3, 2, 2));
    }

    @Test
    public void testEqual() throws Exception {
        Assert.assertTrue(game.equalColor(game.getPiece(0, 0), 0, 0, (byte) 0));
        Assert.assertTrue(game.equalColor(game.getPiece(0, 0), 0, 0, (byte) 1));
        Assert.assertTrue(game.equalColor(game.getPiece(0, 0), 0, 0, (byte) 2));
        Assert.assertTrue(game.equalColor(game.getPiece(0, 0), 0, 0, (byte) 3));

        Assert.assertTrue(game.equalColor(game.getPiece(1, 1), 1, 1, (byte) 0));
        Assert.assertTrue(game.equalColor(game.getPiece(1, 1), 1, 1, (byte) 1));
        Assert.assertTrue(game.equalColor(game.getPiece(1, 1), 1, 1, (byte) 2));
        Assert.assertTrue(game.equalColor(game.getPiece(1, 1), 1, 1, (byte) 3));

        Assert.assertTrue(game.equalColor(game.getPiece(2, 2), 2, 2, (byte) 0));
        Assert.assertTrue(game.equalColor(game.getPiece(2, 2), 2, 2, (byte) 1));
        Assert.assertTrue(game.equalColor(game.getPiece(2, 2), 2, 2, (byte) 2));
        Assert.assertTrue(game.equalColor(game.getPiece(2, 2), 2, 2, (byte) 3));

        Assert.assertFalse(game.equalColor(game.getPiece(0, 0), 2, 0, (byte) 0));
    }
}