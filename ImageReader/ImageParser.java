import java.io.*;

//Data Source: http://yann.lecun.com/exdb/mnist/

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

        images = new Image[59999];

        try {
            read();
        } catch (Exception e) {
            System.out.println("Failed" + e.getMessage());
        }
    }

    private void read() throws IOException {
        byte[] imageBytes = new byte[(int) imageFile.length()];
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(imageFilePath)));
        dataInputStream.readFully(imageBytes);
        dataInputStream.close();


        byte[] dataBytes = new byte[(int) dataFile.length()];
        dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFilePath)));
        dataInputStream.readFully(dataBytes);
        dataInputStream.close();


        //Read Images Starting at the Top
        int temp = 0;
        int[][] image;
        for (int x = 16; x < imageBytes.length - 784; x += 784) {
            image = new int[28][28];
            for (int y = 0; y < 784; y++) {
                temp = imageBytes[x + y];

                if (temp < 0) {
                    temp = temp * -1;
                    temp += 127;
                }
                image[y / 28][y % 28] = temp;
            }

            images[(x - 16) / 784] = new Image(image, 0);
        }

        //Read their Labels
        for (int x = 8; x < images.length; x++) {
            images[x-8].setValue(dataBytes[x]);
        }
    }

    public Image getImage(int atValue) {
        return images[atValue];
    }

    public int getLength() {
        return images.length;
    }
}
