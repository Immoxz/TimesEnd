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
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
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


    public void VideoInfo(String filename) {
/*
        private static final String filename = "c:/myvideo.mp4";*/

        // first we create a Xuggler container object

        IContainer container = IContainer.make();

        // we attempt to open up the container

        int result = container.open(filename, IContainer.Type.READ, null);

        // check if the operation was successful

        if (result < 0)

            throw new RuntimeException("Failed to open media file");

        // query how many streams the call to open found

        int numStreams = container.getNumStreams();

        // query for the total duration

        long duration = container.getDuration();

        // query for the file size

        long fileSize = container.getFileSize();

        // query for the bit rate

        long bitRate = container.getBitRate();

        System.out.println("Number of streams: " + numStreams);

        System.out.println("Duration (ms): " + duration);

        System.out.println("File Size (bytes): " + fileSize);

        System.out.println("Bit Rate: " + bitRate);

        // iterate through the streams to print their meta data

        for (int i = 0; i < numStreams; i++) {

// find the stream object

            IStream stream = container.getStream(i);

// get the pre-configured decoder that can decode this stream;

            IStreamCoder coder = stream.getStreamCoder();

            System.out.println("*** Start of Stream Info ***");

            System.out.printf("stream %d: ", i);

            System.out.printf("type: %s; ", coder.getCodecType());

            System.out.printf("codec: %s; ", coder.getCodecID());

            System.out.printf("duration: %s; ", stream.getDuration());

            System.out.printf("start time: %s; ", container.getStartTime());

            System.out.printf("timebase: %d/%d; ",

                    stream.getTimeBase().getNumerator(),

                    stream.getTimeBase().getDenominator());

            System.out.printf("coder tb: %d/%d; ",

                    coder.getTimeBase().getNumerator(),

                    coder.getTimeBase().getDenominator());

            System.out.println();

            if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {

                System.out.printf("sample rate: %d; ", coder.getSampleRate());

                System.out.printf("channels: %d; ", coder.getChannels());

                System.out.printf("format: %s", coder.getSampleFormat());

            } else if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {

                System.out.printf("width: %d; ", coder.getWidth());

                System.out.printf("height: %d; ", coder.getHeight());

                System.out.printf("format: %s; ", coder.getPixelType());

                System.out.printf("frame-rate: %5.2f; ", coder.getFrameRate().getDouble());

            }

            System.out.println();

            System.out.println("*** End of Stream Info ***");

        }


    }
}