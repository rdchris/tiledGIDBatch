package com.earlyfork.tiledgidbatch.pojos;

import lombok.Data;

@Data
public class TilesetChangeset {

    private int oldStartingGID;
    private int oldnextGID;
    private int incrementValue;


    public TilesetChangeset(int oldStartingGID, int oldnextGID, int incrementValue) {
        this.oldStartingGID = oldStartingGID;
        this.oldnextGID = oldnextGID;
        this.incrementValue = incrementValue;
    }
}
