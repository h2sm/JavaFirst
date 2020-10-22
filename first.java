import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //long a, b;//64+64 - одно число
        bigInt x = new bigInt();
        x.AND();
        x.delBit();
        x.NOT();
        x.OR();
        x.setBit();
        x.shiftBit();
        x.XOR();
    }


}
class bigInt{
    BigInteger number = BigInteger.probablePrime(128, new Random());
    BigInteger number2 = BigInteger.probablePrime(128, new Random());
    void AND (){
        System.out.println("Операция И");
        BigInteger firstNumber = number.and(number2);
        System.out.println(number + " & " + number2 + " = " + firstNumber);

    }
    void OR(){
        System.out.println("Операция ИЛИ");
        BigInteger secondNumber = number.or(number2);
        System.out.println(number + " | " + number2 + " = " + secondNumber);
    }
    void XOR(){
        System.out.println("Операция ИСКЛЮЧАЮЩЕЕ ИЛИ");
        BigInteger thirdNumber = number.xor(number2);
        System.out.println(number + " ⊕ " + number2 + " = " + thirdNumber);
    }
    void NOT(){
        System.out.println("Операция НЕ");
        BigInteger fouthNumber = number.andNot(number2);
        System.out.println(number + " ¬ " + number2 + " = " + fouthNumber);
    }
    void setBit(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Установка бита. Введите номер бита, который нужно установить (1<=n<128)");
        int x = scan.nextInt();
        BigInteger fifthNumber = number.setBit(x);
        System.out.println("Начальный: " + number.toString(2));
        System.out.println("Конечный:  " + fifthNumber.toString(2));
    }
    void delBit(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Удаление бита. Введите номер бита, который нужно удалить (1<=n<128)");
        int x = scan.nextInt();
        BigInteger sixthNumber = number.clearBit(x);
        System.out.println("Начальный: " + number.toString(2));
        System.out.println("Конечный:  " + sixthNumber.toString(2));
    }
    void shiftBit() {
        Scanner scan = new Scanner(System.in);
        String answer;
        int x;
        //long - разбить на 4 части
        //
        System.out.println("Сдвиг бита. Влево или Вправо?");
        if ((answer = scan.next()).equals("Влево")) {
            System.out.println("На сколько бит сдвинуть?");
            x = scan.nextInt();
            BigInteger seventhNumber = number.shiftLeft(x);
            System.out.println("Было: " + number.toString(2));
            System.out.println("Стало: " + seventhNumber.toString(2));
        }
        if (answer.equals("Вправо")) {
            System.out.println("На сколько бит сдвинуть?");
            x = scan.nextInt();
            BigInteger seventhNumber = number.shiftRight(x);
            System.out.println("Было: " + number.toString(2));
            System.out.println("Стало: " + seventhNumber.toString(2));
        } else {
            System.out.println("Ладно.");
        }
    }
}
