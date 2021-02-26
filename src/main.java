public class main {

    public static void main(String[] args) {
        int numInputs = 100;
        int numOutputs = 100;
        int numLayers = 100;


        //Create a net
        NeuralNet n = new NeuralNet(numInputs,numOutputs,numLayers);
        System.out.println("Initialization Complete");

        //Generate an Array
        double[] inputs = new double[numInputs];
        for (int x = 0; x < inputs.length; x++ ) {
            inputs[x] = Math.random();
        }
        System.out.println("Array Generation Complete");

        //Calculate the Output
        double[] outputs = n.calculate(inputs);

        for(int x = 0; x < outputs.length; x++) {
            System.out.println("Output " + x + ": " + outputs[x]);
        }
        System.out.println("Array Output Creation Complete");


        ImageParser p = new ImageParser("./Resources/train-images.idx3-ubyte", "./Resources/train-labels.idx3-ubyte");
    }
}
