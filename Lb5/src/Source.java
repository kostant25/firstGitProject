import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EmptyStackException;
import java.util.Stack;

public class Source {

    private static int count = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] strPol;
        System.out.println("Введите выражение");
        String str = reader.readLine();
        StringBuffer st = new StringBuffer(str);

        if (check(str)) {
            checkVoid(st);
            str = st.toString();
            strPol = writePol(str);
            int a = 0;
            try {
                if (strPol != null) {
                    a = sum(strPol);
                    for (String s : strPol) System.out.print(s + " ");
                }
            } catch (EmptyStackException e) {
                System.out.println("\nВыражение записано не верно");
            } catch (ArithmeticException e){
                System.out.println("\nДелить на ноль нельзя");
            }
            System.out.println("\n" + a);
        }
        else
            System.out.println("Скобки раставленны не верно");
    }

    private static int sum (String[] strPol) throws EmptyStackException, ArithmeticException{                       //метод считающий результат выражения
        Stack<String> stack = new Stack<>();
        int b;
        for (String s : strPol) {
            if (checkString(s))
                stack.push(s);
            else {
                switch (s) {
                    case "+":
                        b = Integer.parseInt(stack.peek());
                        stack.pop();
                        b += Integer.parseInt(stack.peek());
                        stack.pop();
                        stack.push(Integer.toString(b));
                        break;
                    case "-":
                        b = Integer.parseInt(stack.peek());
                        stack.pop();
                        int a = Integer.parseInt(stack.peek()) - b;
                        stack.pop();
                        stack.push(Integer.toString(a));
                        break;
                    case "*":
                        b = Integer.parseInt(stack.peek());
                        stack.pop();
                        b *= Integer.parseInt(stack.peek());
                        stack.pop();
                        stack.push(Integer.toString(b));
                        break;
                    case "/":
                        b = Integer.parseInt(stack.peek());
                        stack.pop();
                        int c = Integer.parseInt(stack.peek()) / b;
                        stack.pop();
                        stack.push(Integer.toString(c));
                        break;
                }
            }
        }
        b = Integer.parseInt(stack.peek());
        stack.clear();
        return b;
    }

    private static String[] writePol (String str1){              //метод преобразования инфиксной формы в Польскую

        String[] str = str1.split(" ");
        String[] strPol = new String[str.length - 2 - count];
        Stack<String> stack = new Stack<>();
        int i = 1;
        int j = 0;
        stack.push(str[0]);
        int h = checkPol(stack.peek(), str[i]);
        while (h != 5 && h != 6){
            switch (h)
            {
                case 1:
                    strPol[j] = str[i];
                    j++;
                    break;
                case 2:
                    stack.push(str[i]);
                    break;
                case 3:
                    strPol[j] = stack.peek();
                    stack.pop();
                    j++;
                    i--;
                    break;
                case 4:
                    stack.push(str[i]);
                    stack.pop();
                    stack.pop();
                    break;
            }
            i++;
            h = checkPol(stack.peek(), str[i]);
        }
        if (h == 6) {
            System.out.println("Скобки раставленны не верно");
            return null;
        }
        else
            return strPol;
    }

    private static int checkPol (String stack, String str) {                           //вспомогательный метод преобразования инфиксной формы в Польскую
        if ((stack.equals("@") && checkString(str)) ||                                 //определяет номер операции
                (stack.equals("+") && checkString(str)) ||
                (stack.equals("-") && checkString(str)) ||
                (stack.equals("*") && checkString(str)) ||
                (stack.equals("/") && checkString(str)) ||
                (stack.equals("(") && checkString(str)))
            return 1;
        else if ((stack.equals("@") && str.equals("+")) ||
                (stack.equals("@") && str.equals("-")) ||
                (stack.equals("@") && str.equals("*")) ||
                (stack.equals("@") && str.equals("/")) ||
                (stack.equals("@") && str.equals("(")) ||
                (stack.equals("+") && str.equals("*")) ||
                (stack.equals("+") && str.equals("/")) ||
                (stack.equals("+") && str.equals("(")) ||
                (stack.equals("-") && str.equals("*")) ||
                (stack.equals("-") && str.equals("/")) ||
                (stack.equals("-") && str.equals("(")) ||
                (stack.equals("*") && str.equals("(")) ||
                (stack.equals("/") && str.equals("(")) ||
                (stack.equals("(") && str.equals("+")) ||
                (stack.equals("(") && str.equals("-")) ||
                (stack.equals("(") && str.equals("*")) ||
                (stack.equals("(") && str.equals("/")) ||
                (stack.equals("(") && str.equals("(")))
            return 2;
        else if ((stack.equals("+") && str.equals("@")) ||
                (stack.equals("+") && str.equals("+")) ||
                (stack.equals("+") && str.equals("-")) ||
                (stack.equals("+") && str.equals(")")) ||
                (stack.equals("-") && str.equals("@")) ||
                (stack.equals("-") && str.equals("+")) ||
                (stack.equals("-") && str.equals("-")) ||
                (stack.equals("-") && str.equals(")")) ||
                (stack.equals("*") && str.equals("@")) ||
                (stack.equals("*") && str.equals("+")) ||
                (stack.equals("*") && str.equals("-")) ||
                (stack.equals("*") && str.equals("*")) ||
                (stack.equals("*") && str.equals("/")) ||
                (stack.equals("*") && str.equals(")")) ||
                (stack.equals("/") && str.equals("@")) ||
                (stack.equals("/") && str.equals("+")) ||
                (stack.equals("/") && str.equals("-")) ||
                (stack.equals("/") && str.equals("*")) ||
                (stack.equals("/") && str.equals("/")) ||
                (stack.equals("/") && str.equals(")")))
            return 3;
        else if ((stack.equals("(") && str.equals(")")))
            return 4;
        else if (stack.equals("@") && str.equals("@"))
            return 5;
        else if ((stack.equals("@") && str.equals(")")) ||
                (stack.equals("(") && str.equals("@")))
            return 6;
        else
            return 0;
    }

    private static boolean check (String str){                                //проверка правильности скобок и подсчет скобок

        int k = 0;
        int l = 0;

        for (int i = 0; i < str.length(); i++)
        {
            if (str.charAt(i) == '('){
                k++;
                l++;
                count++;
            }
            if (str.charAt(i) == ')'){
                k--;
                l--;
                count++;
            }
            if (l < 0)
                break;
        }

        return k == 0;
    }

    private static void checkVoid (StringBuffer st){                        //метод расстановки пробелов междусимволами и числами
        int l = st.length();
        for (int i = 0; i < l; i++)
        {
            if (i == l - 1){
                if ((st.charAt(i) == '(' || st.charAt(i) == ')' || st.charAt(i) == '+' || st.charAt(i) == '-' || st.charAt(i) == '*' || st.charAt(i) == '/') && st.charAt(i - 1) != ' ')
                {
                    st.insert(i, ' ');
                    l = st.length();
                }
            }
            else if (i == 0){
                if ((st.charAt(i) == '(' || st.charAt(i) == ')' || st.charAt(i) == '+' || st.charAt(i) == '-' || st.charAt(i) == '*' || st.charAt(i) == '/') && st.charAt(i + 1) != ' ')
                {
                    st.insert(i + 1, ' ');
                    l = st.length();
                }
            }
            else {
                if ((st.charAt(i) == '(' || st.charAt(i) == ')' || st.charAt(i) == '+' || st.charAt(i) == '-' || st.charAt(i) == '*' || st.charAt(i) == '/') && st.charAt(i + 1) != ' ')
                {
                    if (st.charAt(i - 1) != ' ' && st.charAt(i + 1) != ' ')
                    {
                        st.insert(i, ' ');
                        st.insert(i + 2, ' ');
                        l = st.length();
                    }
                    else{
                        st.insert(i + 1, ' ');
                        l = st.length();
                    }
                }
            }
        }
        st.insert(0, "@ ");
        st.insert(st.length(), " @");
    }

    private static boolean checkString(String string) {                           //проверка является ли строка числом
        try {
            Integer.parseInt(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
