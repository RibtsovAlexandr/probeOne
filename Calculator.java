import java.util.Scanner;
import java.io.IOException;

/** Author by Ribtsov Alexandr
 *  A.Ribtsov@gmail.com
 */
public class Calculator {
    private static boolean CheckOperand(String chkOp){
        if ((chkOp.charAt(0)=='I')|(chkOp.charAt(0)=='V')|(chkOp.charAt(0)=='X')) {
            //System.out.println("Что-то римское?");
            return true;
        } else return false;
    }

    //конверташка в числовые.
    public static int convertOP(String chkOp) throws NumberFormatException{
        int value = -1;
        switch (chkOp) {
            case ("I"): value = 1; break;
            case ("II"): value = 2; break;
            case ("III"): value = 3; break;
            case ("IV"): value = 4; break;
            case ("V"): value = 5; break;
            case ("VI"): value = 6; break;
            case ("VII"): value = 7; break;
            case ("VIII"): value = 8; break;
            case ("IX"): value = 9; break;
            case ("X"): value = 10; break;
            default:
                try {
                    value = Integer.parseInt(chkOp);    // именно здесь String преобразуется в int
                }
                catch (NumberFormatException nfe)
                {
                    System.out.println("Ошибка конвертации в натуральное число: " + nfe.getMessage());
                    value = -64;
                };
        }
        return value;
    }

    public static String showInRims(int intShow){
        String result = "";
        int tmp = intShow;

        while (tmp >= 100) {
            result += "C";
            tmp -= 100;
        }
        while (tmp >= 90) {
            result += "XC";
            tmp -= 90;
        }
        while (tmp >= 50) {
            result += "L";
            tmp -= 50;
        }
        while (tmp >= 40) {
            result += "XL";
            tmp -= 40;
        }
        while (tmp >= 10) {
            result += "X";
            tmp -= 10;
        }
        while (tmp >= 9) {
            result += "IX";
            tmp -= 9;
        }
        while (tmp >= 5) {
            result += "V";
            tmp -= 5;
        }
        while (tmp >= 4) {
            result += "IV";
            tmp -= 4;
        }
        while (tmp >= 1) {
            result += "I";
            tmp -= 1;
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Калькулятор натуральных положительных чисел до 10-ти.");
        System.out.println("Введите разделённое пробелами выражение вида a + b, a - b, a * b, a / b. Для остановки работы калькулятора - введите просто точку.");

        Scanner std1 = new Scanner(System.in);
        String input;
        String[] operands;
        int opA, opB;
        boolean rimOpA, rimOpB, RimOperation;

        do {
            System.out.print(">");
            input = std1.nextLine();    // чтение очередной строки из консоли
            operands = input.split(" ");    //разбиение на подстроки
            if (operands.length == 3) {

                // секция проверок:
                rimOpA=CheckOperand(operands[0]);
                rimOpB=CheckOperand(operands[2]);
                // проверка на принадлежность операндов к разным системам исчисления
                if (rimOpA ^ rimOpB) throw new IOException("Обнаружены опернды из разных систем исчисления.");
                // проверка на принадлежность операндов к Римской системам исчисления
                if (rimOpA & rimOpB){
                    RimOperation= true;
                }else RimOperation= false;

                if (RimOperation) System.out.println("Запрос в римском отображении:");

                opA=convertOP(operands[0]);
                opB=convertOP(operands[2]);
                if ( (opA<1) | (opB<1) ){                // проверка на негативность
                    throw new IOException("обнаружены операнды не подходящие под определение положительных натуральных чисел до 10-и.");
                };
                if ( (opA>10) | (opB>10) ){              // проверка на превышение 10-ти
                    throw new IOException("обнаружены операнды величиной более 10-ти.");
                };
                // обработка орифметики
                switch (operands[1]) {
                    case ("+"):
                        System.out.print("результат сложения = ");
                        if (RimOperation){
                            System.out.println(showInRims(opA + opB));
                        }else System.out.println(opA + opB);
                        break;
                    case ("-"):
                        System.out.print("результат вычитания = ");
                        if (RimOperation){
                            System.out.println(showInRims(opA - opB));
                        }else System.out.println(opA - opB);
                        break;
                    case ("*"):
                        System.out.print("результат умножения = ");
                        if (RimOperation){
                            System.out.println(showInRims(opA * opB));
                        }else System.out.println(opA * opB);
                        break;
                    case ("/"):
                        System.out.print("результат целочисленного деления = ");
                        if (RimOperation){
                            System.out.println(showInRims(opA / opB));
                        }else System.out.println(opA / opB);
                        break;
                    default:
                        throw new IOException("Введена недопустимая математическая операция.");
                }
            } else if ((operands.length==2)|(operands.length > 3)) {
                throw new IOException("Введена недопустимая конструкция.");
            } else if (!input.equals(".")) {
                System.out.println("Недостаточная конструкция. Попробуйте ввод ещё раз.");
            }else System.out.println("Калькулятор выключен.");

        } while (!input.equals("."));
    }
}
