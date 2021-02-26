public class Neuron {
    private int numInputs;
    private double[] inputs;
    private double[] weights;
    private double bias;
    private double output;

    //Initializer
    public Neuron(int numInputs) {
        //Initialize instance vars
        this.numInputs = numInputs;
        inputs = new double[numInputs];
        weights  = new double[numInputs];
        bias = 0.0;

        //initialize values as random doubles
        for (int x = 0; x < numInputs; x++ ) {
            inputs[x] = Math.random();
            weights[x] = Math.random();
        }
    }

    //Calculate the Output
    public double calculateOutput() {
        //Set output to 0
        output = 0.0;


        //get the sum of all inputs and their weights
        for(int x = 0; x < numInputs; x++) {
            output += inputs[x]*weights[x];
        }

        //divide by amount to get average
        output = (10*output)/((double)(numInputs));

        output = (1/( 1 + Math.pow(Math.E,(-1*output))));

        //Add Bias
        output += bias;

        //return the output
        return output;
    }

    //Method for Getting Input
    public void setInputs(double[] inputs) {
        //If there is a correct number of outputs, then set the inputs. Otherwise, terminate running
        if (this.numInputs == inputs.length) {
            this.inputs = inputs;
        } else {
            System.out.println("Neuron Expected " + numInputs + " inputs, received " + inputs.length + ". Process will halt.");
            System.exit(-1);
        }
    }
}
