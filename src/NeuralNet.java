public class NeuralNet {
    private int numLayers;
    private int numInputs;
    private int numOutputs;
    private Neuron[][] neurons;
    
    //Initializer
    public NeuralNet(int numInputs, int numOutputs, int numLayers) {
        //If the neural network is invalid, terminate the program
        if (!(((numInputs > 1) && (numLayers > 2)) && (numOutputs > 1))) {
            System.out.println("This Neural Net is Invalid, Required at least 1 input, 1 output, and 2 layers. The program will now Terminate");
            System.exit(-1);
        }

        //Initialize Instance
        this.numInputs = numInputs;
        this.numOutputs = numOutputs;
        this.numLayers = numLayers;
        neurons = new Neuron[numLayers][];

        //Initialize each Row except the last.
        for (int x = 0; x < numLayers - 1; x++) {
            //initialize the row with appropriate length
            neurons[x] = new Neuron[numInputs];

            //initialize each neuron in the array
            for (int y = 0; y < numInputs; y++) {
                neurons[x][y] = new Neuron(numInputs);
            }
        }

        //Initialize the Last Layer
        neurons[numLayers -1] = new Neuron[numOutputs];
        for (int y = 0; y < numInputs; y++) {
            neurons[numLayers - 1][y] = new Neuron(numInputs);
        }
    }
    
    //Take Inputs and Calculate 
    public double[] calculate(double[] inputs) {
        //Check to see if length of inputs in valid. Terminate if invalid
        if (inputs.length != numInputs) {
            System.out.println("Net Expected " + numInputs + " inputs, received " + inputs.length + ". Process will halt.");
            System.exit(-1);
        }
        //Set first column
        for (int x = 0; x < numInputs; x++) {
            neurons[0][x].setInputs(inputs);
        }
        
        //Starting with the first layer, propagate outputs until conclusion is reached
        for (int x = 1; x < numLayers; x++) {
            
            //Create an Array of the Previous Rows Inputs
            double[] previousOutputs = new double[numOutputs];
            
            for (int y = 0; y < neurons[x].length; y++) {
                previousOutputs[y] = neurons[x-1][y].calculateOutput();
            }
            
            //Each Neuron Receives the Previous Rows Outputs
            for (int y = 0; y < neurons[x].length; y++) {
                neurons[x][y].setInputs(previousOutputs);
            }
        }
        
        //Generate Array of Outputs
        double[] outputs = new double[numOutputs];

        for (int x = 0; x < numOutputs; x++) {
            outputs[x] = neurons[numLayers-1][x].calculateOutput();
        }

        //return the output
        return outputs;
    }
}
