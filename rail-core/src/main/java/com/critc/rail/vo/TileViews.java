package com.critc.rail.vo;

import com.critc.tile.modal.Tile;

import java.util.ArrayList;
import java.util.List;

public class TileViews {
    Tile tile;
    List<NodeView> nodeViews;
    List<LineView> lineViews;

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public List<NodeView> getNodeViews() {
        nodeViews = nodeViews == null ? new ArrayList<>() : nodeViews;
        return nodeViews;
    }

    public void setNodeViews(List<NodeView> nodeViews) {
        this.nodeViews = nodeViews;
    }

    public List<LineView> getLineViews() {
        lineViews = lineViews == null ? new ArrayList<>() : lineViews;
        return lineViews;
    }

    public void setLineViews(List<LineView> lineViews) {
        this.lineViews = lineViews;
    }
}