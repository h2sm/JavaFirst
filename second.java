import java.util.Random;

public class second {
    public static void main(String[] args) {
        Complex x = new Complex(2, 3);
        Complex y = new Complex(4, -2);

        System.out.println("Sum is " + Complex.toSum(x,y));
        System.out.println("Subtract is "+ Complex.toSubtract(x,y));
        System.out.println("Multiply is " + Complex.toMultiply(x,y));
        System.out.println("Divide is " + Complex.toDivide(x,y));
        }

}
class Complex{
    private double real;
    private double imaginary;
    Complex y = new Complex(new Random().nextInt(10), new Random().nextInt(10));
    public Complex(double real, double imaginary) {
       this.real = real;
       this.imaginary = imaginary;
    }
    //не статические(a.+b)
    public static Complex toSum(Complex x, Complex y) {
        double realPart = x.real + y.real;
        double imaginaryPart = x.imaginary + y.imaginary;
        return new Complex(realPart, imaginaryPart);
    }
    public static Complex toSubtract(Complex x, Complex y){
        return new Complex(x.real - y.real, x.imaginary - y.imaginary);
    }
    public static Complex toMultiply(Complex x, Complex y){
        return new Complex(x.real * y.real, x.imaginary * y.imaginary);
    }
    public static Complex toDivide(Complex x, Complex y){
        Complex num = new Complex(y.real, (-1)* y.imaginary);
        num = Complex.toMultiply(x, num);
        double znamenatel = y.real * y.real + y.imaginary * y.imaginary;
        return new Complex(num.real/znamenatel, num.imaginary/znamenatel);
    }
    @Override
    public String toString() {
        String realString = this.real+"";
        String imaginareString = "";
        if(this.imaginary < 0) imaginareString = this.imaginary+"i";
        else
            imaginareString = "+"+this.imaginary+"i";
        return realString+imaginareString;
    }
}
