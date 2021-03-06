import java.lang.reflect.Modifier;

public class Image {
    private int value;
    private int[][] bits;

    //Create an Image
    public Image(int[][] bits, int value) {
        this.bits = bits;
        this.value = value;
    }

    //Accesors
    public int getValue() {
        return value;
    }

    public int[][] getBits() {
        return bits;
    }

    //Modifier

    public void setValue(int value) {
        this.value = value;
    }

    public String toString() {
        String s = "";

        for(int x = 0; x<bits.length;x++) {
            for(int y = 0; y<bits[x].length;y++) {

                if(bits[x][y] > 220) {
                    s+="▓";
                } else if(bits[x][y] > 140) {
                    s+="▒";
                } else if(bits[x][y] > 65) {
                s+="░";
                } else {
                    s+=" ";
                }
            }
            s+= "\n";
        }
        s+="The Value is: " + value;
        return s;
    }
}
