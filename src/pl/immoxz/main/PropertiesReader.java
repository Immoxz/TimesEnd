package pl.immoxz.main;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;


import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.XMPDM;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp4.MP4Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

/**
 * Created by AdminIT on 2016-07-11.
 */
public class PropertiesReader {

    public void displey(String path) {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            String filename = path;
            input = getClass().getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                System.out.println("Sorry, unable to find " + filename);
                return;
            }

            prop.load(input);

            Enumeration<?> e = prop.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = prop.getProperty(key);
                System.out.println("Key : " + key + ", Value : " + value);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void readProperty() throws IOException, TikaException, SAXException {

        //detecting the file type
        File file;
        file = new File("C:/Users/AdminIT/Documents/TestFiles/testfile.mp4");
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(file);
        ParseContext pcontext = new ParseContext();

        //Html parser
        MP4Parser MP4Parser = new MP4Parser();
        MP4Parser.parse(inputstream, handler, metadata, pcontext);
        System.out.println("Contents of the document:  :" + handler.toString());
        System.out.println("Metadata of the document:");
        String[] metadataNames = metadata.names();
        double dur = 0;
        double frameRate = 0;

        System.out.println(metadata.get(XMPDM.TIME_SIGNATURE));
        dur = (int) Double.parseDouble(metadata.get(XMPDM.DURATION));
        System.out.println(metadata.get(XMPDM.AUDIO_SAMPLE_RATE));
        frameRate = (int) Double.parseDouble(metadata.get(XMPDM.AUDIO_SAMPLE_RATE));

                System.out.println(((dur * frameRate)));
        for (String name : metadataNames) {
            System.out.println(name + ": " + metadata.get(name));
        }
    }

}
