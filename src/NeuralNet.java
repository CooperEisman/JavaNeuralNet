public class NeuralNet {
    private int numLayers;
    private int numInputs;
    private int numOutputs;
    private Neuron[][] neurons;
    private double learningRate = 0.1;
    
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
        for (int y = 0; y < numOutputs; y++) {
            neurons[numLayers - 1][y] = new Neuron(numInputs);
        }
    }
    
    //Take Inputs and Calculate 
    private double[] calculate(double[] inputs) {
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
            double[] previousOutputs = new double[numInputs];
            
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

    //Train the Algorithim, returns accuracy
    public double forwardPropegate(double[][] inputs, int[] outputs) {
        //Check to see if input is valid, Terminate if invalid
        if (inputs[0].length != numInputs) {
            System.out.println("Training is Invalid, inputs or outputs do not match expected. Process fails.");
            System.exit(-1);
        }

        //Tracking variable
        int correct = 0;

        //Train for Every Data Piece, Tracking Accuracy
        for(int x = 0; x < inputs.length; x++) {
            System.out.println("Running Forward Propegation Pass #" + x);
            if (getOutput(inputs[x]) == outputs[x]) {
                correct++;
            }
        }

        //Returns run-through divided by correct runs
        return correct*1.0/inputs.length;

    }

    //Get the Output
    public int getOutput(double[] inputs) {
        //Safety is Checked in calculate()
        //Get Array of Inputs
        double[] calculated = calculate(inputs);

        //Find the Greatest Output
        int index = 0;
        double maxValue = 0.0;

        for(int x = 0; x < numOutputs; x++) {
            if (calculated[x] > maxValue) {
                index = x;
                maxValue = calculated[index];
            }
        }

        //return the index of the highest value
        return index;
    }

    //Accessor to change the learning rate
    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    //Get the cost function, as mapped in a double array
    private double[] getCost(int[] expected, double[] calculated) {
        //Create and Initialize Cost Array
        double[] cost = new double[numOutputs];
        for(int x = 0; x < cost.length; x++) {
            cost[x] = 0;
        }

        //Calculate Difference
        for(int x = 0; x < calculated.length; x++) {
            cost[expected[x]] += (1 - calculated[x]);

            for(int y = 0; y < cost.length; y++) {
                //if not the correct answer, calculate difference
                if (y!=expected[x]) {
                    cost[y] += (calculated[y]);
                }
            }
        }

        return cost;
    }

    //Get an array of outputs
    private double[] forwardPropegateForOutputs(double[][] inputs) {

        //Tracking variable
        double[] outputs = new double[inputs.length];

        //Train for Every Data Piece, Tracking Accuracy
        for(int x = 0; x < inputs.length; x++) {
            outputs[x] = getOutput(inputs[x]);
        }

        //Returns run-through divided by correct runs
        return outputs;

    }

    //Gets the slope between two doubles
    private double getSlope(double initial, double finalVal, double delta) {
        return (initial-finalVal)/delta;
    }

    private void propegateChange(int valX, int valY, double[][] inputs, int[] expected) {
            //Create an Array of the Previous Rows Inputs
            double[] previousOutputs = new double[numInputs];
            for (int y = 0; y < neurons[valX].length; y++) {
                previousOutputs[y] = neurons[valX-1][y].calculateOutput();
            }

            //Find dC/dw, and make changes as such
            double[] weightChanges = new double[numInputs];
            double initialCost = 0.0;
            double finalCost = 0.0;
            double delta = 0.001;
            double[] weightChanger = new double[numInputs];
            for(int x = 0; x < numInputs; x++) {weightChanger[x]=0;}

            for(int x = 0; x < numOutputs; x++) {
                System.out.println(x);
                initialCost = getCost(expected, forwardPropegateForOutputs(inputs))[x];
                weightChanger[x] = delta;
                neurons[valX-1][x].setWeights(weightChanger);
                finalCost = getCost(expected, forwardPropegateForOutputs(inputs))[x];

                weightChanger[x] = -delta;
                neurons[valX-1][x].setWeights(weightChanger);

                weightChanges[x] = learningRate*(getSlope(initialCost, finalCost, delta));
            }

            //Make Changes to the Neuron
        neurons[valX][valY].setWeights(weightChanges);
    }

    public void backPropegate(double[][] inputs, int[] expected, int propegationRuns) {
        System.out.println("Before First Backprop:" + forwardPropegate(inputs, expected));

        for (int c = 0; c < numOutputs; c++) {
            for (int x = 0; x < numOutputs; x++) {
                propegateChange(numLayers - 1, x, inputs, expected);
            }
            System.out.println("After Back Propegation #" + c + ": " + forwardPropegate(inputs, expected));
            for (int x = 0; x < numOutputs; x++) {
                propegateChange(numLayers - 1, x, inputs, expected);
            }
        }
    }
}
