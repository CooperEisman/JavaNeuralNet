public class main {

    public static void main(String[] args) {
        //Values
        int numInputs = 284;
        int numOutputs = 10;
        int numLayers = 3;
        int numRunsTrain = 1000;
        int propegationRuns = 50;
        System.out.println("Neural Net for Image->Numerical Recognition. Code by Cooper Eisman, Dataset from MNIST. Sourcing in Readme");


        //Create a net
        NeuralNet n = new NeuralNet(numInputs,numOutputs,numLayers);
        System.out.println("Initialization Complete");

        //Take all the images from the Sample Array
        ImageParser p = new ImageParser("./Resources/train-images.idx3-ubyte", "./Resources/train-labels.idx1-ubyte");
        System.out.println("Image Parsing Complete");

        //Get Initial Accuracy
        //Create Image Array
        double[][] image = new double[numRunsTrain][284];
        int[] expectedOutput = new int[p.getLength()];

        //Fill the Arrays
        for(int x = 0; x < numRunsTrain; x++) {
            expectedOutput[x] = p.getImage(x).getValue();
            for (int y = 0; y < image[x].length; y++) {
                image[x][y] = (p.getImage(x).getBits()[y / 28][y % 28]) / 256.0;
            }
        }
        System.out.println("Arrays Filled... Testing Running Now");

        //Calculate the Accuracy
        n.backPropegate(image,expectedOutput, propegationRuns);


    }
}
