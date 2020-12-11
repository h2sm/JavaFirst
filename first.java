    import java.math.BigInteger;
    import java.util.ArrayList;
    import java.util.Random;
    import java.util.Scanner;

    public class Main {
        public static void main(String[] args) {
            bigInt x = new bigInt();
    //        x.AND();
    //        x.delBit();
    //        x.NOT();
    //        x.OR();
    //        x.setBit();
            x.shiftBit(2);
    //        x.XOR();

        }
    }
    class bigInt{
        BigInteger number = BigInteger.probablePrime(128, new Random());
        BigInteger number2 = BigInteger.probablePrime(128, new Random());
        void AND (){
            System.out.println("Операция И");
            BigInteger firstNumber = number.and(number2);
            System.out.println(number.toString(2) + " & ");
            System.out.println(number2.toString(2));
            System.out.println( firstNumber.toString(2));
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
        void shiftBit(int shift){//СМЕЩЕНИЕ ТОЛЬКО ВЛЕВО!
            int[] bitInteger = new int[128];//здесь хранится 128битное число
            ArrayList<Integer>answer = new ArrayList<>();
            char[] digits = number.toString(2).toCharArray();
            int x =0;
            for (char digit : digits) {//преобразовываю в int char-ы
                digit -= '0';
                bitInteger[x] = digit;
                x++;
            }

            int[] shiftingBits = new int[shift];//массив под смещаемые биты
            for (int i = 0; i < shift; i++) {
                shiftingBits[i]=bitInteger[i];//получаем смещаемые биты
            }
            for (int i = 0; i<128;i++) {
                int iter = 0;
                if(i<128-shift) answer.add(i, bitInteger[i + shift]);//переносим наш bitinteger
                else answer.add(i,shiftingBits[iter++]);//вставляем в конец смещенные биты
            }
            for (int i = 0; i < 128; i++) {
                System.out.println(bitInteger[i]);
            }
            System.out.println(answer + " size " + answer.size());
        }
    }
