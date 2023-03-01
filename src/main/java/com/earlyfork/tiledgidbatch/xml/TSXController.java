package com.earlyfork.tiledgidbatch.xml;

import com.earlyfork.tiledgidbatch.pojos.TilesetChangeset;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
@Data
public class TSXController {

    LinkedList<TilesetChangeset> tilesetChangesetLinkedList = new LinkedList<TilesetChangeset>();


}
