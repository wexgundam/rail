package com.critc.rail.vo;

import com.critc.tile.modal.Tile;

import java.util.ArrayList;
import java.util.List;

public class NodeViewTiles {
    NodeView nodeView;
    List<Tile> tiles;

    public NodeView getNodeView() {
        return nodeView;
    }

    public void setNodeView(NodeView nodeView) {
        this.nodeView = nodeView;
    }

    public List<Tile> getTiles() {
        tiles = tiles == null ? new ArrayList<>() : tiles;
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }
}