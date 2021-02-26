import java.io.*;

public class ImageParser {
    private Image[] images;
    private File imageFile;
    private File dataFile;
    private String imageFilePath;
    private String dataFilePath;

    //Init
    public ImageParser(String imageFilePath, String dataFilePath) {
        this.imageFilePath = imageFilePath;
        imageFile = new File(imageFilePath);

        this.dataFilePath = dataFilePath;
        dataFile = new File(dataFilePath);

        images = new Image[60000];

        try {
            read();
        } catch (Exception e) {
            System.out.println("Failed" + e.getMessage());
        }
    }

    private void read() throws IOException {
        byte[] imageBytes = new byte[(int)imageFile.length()];
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(imageFilePath)));
        dataInputStream.readFully(imageBytes);
        dataInputStream.close();


        byte[] dataBytes = new byte[(int)dataFile.length()];
        dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFilePath)));
        dataInputStream.readFully(dataBytes);
        dataInputStream.close();



        //Read Images Starting at the Top
        int temp = 0;
        int[][] image = new int[28][28];
        for (int x = 16; x < imageBytes.length - 784; x+=784) {
            for(int y = 0; y < 784; y++) {
                temp = imageBytes[x+y];

                if (temp < 0) {temp = temp*-1; temp +=127;}
                image[y/28][y%28] = temp;
            }

            images[(x-16)/784] = new Image(image, 0);
        }

        //read their respective numbers



    }

    public Image getImage(int atValue) {
        return images[atValue];
    }
}
