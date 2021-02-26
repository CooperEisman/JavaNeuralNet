public class NeuralNet {
    private int numLayers;
    private int numInputs;
    private int numOutputs;
    private Neuron[][] neurons;

    public NeuralNet(int numInputs, int numOutputs, int numLayers) {
        //If the neural network is invalid, terminate the program
        if (!(((numInputs < 1) && (numLayers < 2)) && (numOutputs < 1))) {
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
}
