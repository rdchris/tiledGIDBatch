package com.earlyfork.tiledgidbatch.pojos;

public class TilesetChangeset {

    private int oldStartingGID;
    private int oldnextGID;
    private int oldGID;
    private int nearGIDRangeStarting;

    public TilesetChangeset(int oldStartingGID, int oldnextGID, int oldGID, int nearGIDRangeStarting) {
        this.oldStartingGID = oldStartingGID;
        this.oldnextGID = oldnextGID;
        this.oldGID = oldGID;
        this.nearGIDRangeStarting = nearGIDRangeStarting;
    }
}
