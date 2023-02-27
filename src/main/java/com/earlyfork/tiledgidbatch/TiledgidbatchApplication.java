package com.earlyfork.tiledgidbatch;

import com.earlyfork.tiledgidbatch.globalid.GlobalIDController;
import com.earlyfork.tiledgidbatch.xml.XmlFileIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.w3c.dom.Document;

import java.util.ArrayList;

@SpringBootApplication
public class TiledgidbatchApplication implements CommandLineRunner {

    @Autowired
    XmlFileIO xmlFileIO;

    @Autowired
    GlobalIDController globalIDController;

    public static void main(String[] args) {
        SpringApplication.run(TiledgidbatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        ArrayList<Document> documents = xmlFileIO.readInTMXFiles();



        //update
        globalIDController.updateGlobalIds(documents);


        xmlFileIO.saveTMXFiles(documents);

    }
}
