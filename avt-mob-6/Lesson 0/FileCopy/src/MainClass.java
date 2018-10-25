import org.junit.Test;

import java.io.*;

public class MainClass {


    @Test
    public void MainClass () throws IOException {
        String fileLocation = "/Users/apalnov/Desktop/Images/FilesForCopy";
        String newFileName, newFilePath;
        String fileName = "Original.jpg";
        File file = new File(fileLocation, fileName);


        for (int i = 1; i < 10; i++) {
            Integer x = i;
            newFileName = "Original" + "_" + x.toString() + ".jpg";
            newFilePath = "/Users/apalnov/Desktop/Images/FilesForCopy/Copied";
            File fileCopied = new File(newFilePath, newFileName);
            try {
            copyFileUsingStream(file, fileCopied); }
            catch (IOException e) {
                System.out.println("Something went wrong!");
            }
        }

    }




    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }


}
