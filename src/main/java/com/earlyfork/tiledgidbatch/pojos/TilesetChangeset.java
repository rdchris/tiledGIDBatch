package com.earlyfork.tiledgidbatch.pojos;

import lombok.Data;

@Data
public class TilesetChangeset {

    private int oldStartingGid;
    private int oldnextGid;
    private int newFirstGidValue;


    public TilesetChangeset(int oldFirstGid, int oldnextGid, int newFirstGidValue) {
        this.oldStartingGid = oldFirstGid;
        this.oldnextGid = oldnextGid;
        this.newFirstGidValue = newFirstGidValue;
    }

    public int getGidDelta() {
        return newFirstGidValue - oldnextGid;
    }
}
