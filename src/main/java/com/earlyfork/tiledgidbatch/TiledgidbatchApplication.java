package com.earlyfork.tiledgidbatch;

import com.earlyfork.tiledgidbatch.tmxmodding.TilesetFirstGlobalIDController;
import com.earlyfork.tiledgidbatch.tmxmodding.DataNodesController;
import com.earlyfork.tiledgidbatch.pojos.TilesetChangeset;
import com.earlyfork.tiledgidbatch.xml.XmlFileIO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.w3c.dom.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

@SpringBootApplication
public class TiledgidbatchApplication implements CommandLineRunner {

    @Autowired
    private XmlFileIO xmlFileIO;

    @Autowired
    private TilesetFirstGlobalIDController tilesetFirstGlobalIDController;

    @Autowired
    private DataNodesController dataNodesController;

    private String mapDirectory = "C:/s3Test/maps/active";

    public static void main(String[] args) {
        SpringApplication.run(TiledgidbatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // get all tmxFiles
        Collection<File> tmxFiles = FileUtils.listFiles(new File(mapDirectory), new RegexFileFilter("^(.*tmx)"), DirectoryFileFilter.DIRECTORY);

        // Grab all TMX files
        ArrayList<Document> documents = xmlFileIO.readInTMXFiles(tmxFiles);

        //update FirstGIDs
        Map<String, LinkedList<TilesetChangeset>> tilesetChangesets = tilesetFirstGlobalIDController.updateGlobalIds(documents);

        //update data values
        dataNodesController.updateDataNodes(documents,tilesetChangesets);

        // save files
        xmlFileIO.saveTMXFiles(tmxFiles,documents);

    }
}
