package com.earlyfork.tiledgidbatch;

import com.earlyfork.tiledgidbatch.globalid.GlobalIDController;
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

@SpringBootApplication
public class TiledgidbatchApplication implements CommandLineRunner {

    @Autowired
    XmlFileIO xmlFileIO;

    @Autowired
    GlobalIDController globalIDController;

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

        //update GIDs
        globalIDController.updateGlobalIds(documents);

        // save files
        xmlFileIO.saveTMXFiles(tmxFiles,documents);

    }
}
