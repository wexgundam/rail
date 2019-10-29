package com.critc.rail.vo;

import com.critc.tile.modal.Tile;

import java.util.ArrayList;
import java.util.List;

public class LineViewTiles {
    LineView lineView;
    List<Tile> tiles;

    public LineView getLineView() {
        return lineView;
    }

    public void setLineView(LineView lineView) {
        this.lineView = lineView;
    }

    public List<Tile> getTiles() {
        tiles = tiles == null ? new ArrayList<>() : tiles;
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }
}