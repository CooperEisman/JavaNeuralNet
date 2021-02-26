public class Nueron {
    private int numInputs;
    private double[] inputs;
    private double[] weights;
    private double bias;
    private double output;

    //Initializer
    public Nueron(int numInputs) {
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
        //Set output to bias
        output = bias;


        //get the sum of all inputs and their weights
        for(int x = 0; x < numInputs; x++) {
            output += inputs[x]*weights[x];
        }

        //divide by amount to get average
        output = output/((double)(numInputs));

        //return the output
        return output;
    }





}
