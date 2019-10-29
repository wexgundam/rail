/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.tile.modal;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * what:    (这里用一句话描述这个类的作用). <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/20
 */
public class TestTileBounds {

    @Test
    public void testGetTiles() {
        int zoomLevel = 2;
        Tile topLeftTile = new Tile(zoomLevel, 0, 0);
        Tile bottomRightTile = new Tile(zoomLevel, 1, 1);
        TileBounds tileBounds = new TileBounds(zoomLevel, topLeftTile, bottomRightTile);
        List<Tile> tiles = tileBounds.getTiles();
        Assert.assertNotNull(tiles);
        Assert.assertEquals(4, tiles.size());
        Assert.assertEquals("2/0/0", tiles.get(0).getId());
        Assert.assertEquals("2/0/1", tiles.get(1).getId());
        Assert.assertEquals("2/1/0", tiles.get(2).getId());
        Assert.assertEquals("2/1/1", tiles.get(3).getId());
    }
}
