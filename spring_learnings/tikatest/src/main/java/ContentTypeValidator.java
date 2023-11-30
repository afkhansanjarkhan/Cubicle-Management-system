import java.io.*;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;

public class TestTika {
    public static void main(String[] args) {
        // Set the system property to increase the write limit
        System.setProperty("org.apache.tika.parser.recursion_limit", "10000000000000000000000000000000000000000000000000000000000000000"); // Adjust the limit as needed

        String filePath = "C:/Users/akhan1/Downloads/MN App Server1-Buffer-Log (63).csv"; // Modify the file path as needed

        try {
            File file = new File(filePath);
            FileInputStream inputStream = new FileInputStream(file);

            // Wrap the input stream in a BufferedInputStream
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            // Create a parser with automatic content type detection
            Detector detector = new DefaultDetector();
            Parser parser = new AutoDetectParser(detector);

            // Metadata object to store additional information about the file
            Metadata metadata = new Metadata();

            // Parse the file
            BodyContentHandler handler = new BodyContentHandler();
            ParseContext context = new ParseContext();
            context.set(Parser.class, parser);

            parser.parse(bufferedInputStream, handler, metadata, context);

            // Get the detected content type
            MediaType mediaType = detector.detect(bufferedInputStream, metadata);

            // Check if the detected content type is one of the allowed types
            String detectedContentType = mediaType.toString();
            String[] allowedContentTypes = {
                    "image/jpeg",
                    "application/pdf",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // XLSX
                    "text/csv", // CSV
                    // Add more allowed content types as needed
            };

            boolean isValidContentType = false;
            for (String allowedType : allowedContentTypes) {
                if (detectedContentType.startsWith(allowedType)) {
                    isValidContentType = true;
                    break;
                }
            }

            if (isValidContentType) {
                System.out.println("Valid content type: " + detectedContentType);
            } else {
                System.out.println("Invalid content type: " + detectedContentType);
            }

            bufferedInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
