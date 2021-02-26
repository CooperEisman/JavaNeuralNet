public class main {

    public static void main(String[] args) {
        //Values
        int numInputs = 284;
        int numOutputs = 10;
        int numLayers = 25;
        System.out.println("Neural Net for Image->Numerical Recognition. Code by Cooper Eisman, Dataset from MNIST. Sourcing in Readme");


        //Create a net
        NeuralNet n = new NeuralNet(numInputs,numOutputs,numLayers);
        System.out.println("Initialization Complete");

        //Take all the images from the Sample Array
        ImageParser p = new ImageParser("./Resources/train-images.idx3-ubyte", "./Resources/train-labels.idx1-ubyte");
        System.out.println("Image Parsing Complete");

    }
}
