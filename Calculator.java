import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Calculator extends JFrame {
    private JTextField text;
    private ArrayList<String> array= new ArrayList<String>();
    private String num="";
    private String prev_operation = "";
    public Calculator(){

        setLayout(null);
        text = new JTextField();
        text.setEditable(false);
        text.setBackground(Color.WHITE);
        text.setHorizontalAlignment(JTextField.RIGHT);
        text.setFont(new Font("Arial", Font.BOLD, 50));
        text.setBounds(8, 10, 270, 70);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));
        buttonPanel.setBounds(8, 90, 270, 235);

        String button_names[] = {"C", "÷", "×", "=", "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "0"};
        JButton buttons[]=new JButton[button_names.length];

        for(int i=0; i< button_names.length;i++){
            buttons[i]=new JButton(button_names[i]);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 20));
            if(button_names[i]=="C") buttons[i].setBackground(Color.RED);
            else if ((i >= 4 && i <= 6) || (i >= 8 && i <= 10) || (i >= 12 && i <= 14)) buttons[i].setBackground(Color.BLACK);
            else buttons[i].setBackground(Color.GRAY);
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setBorderPainted(false);
            buttons[i].addActionListener(new PadActionListener());
            buttonPanel.add(buttons[i]);
        }

        add(text);
        add(buttonPanel);

        setTitle("계산기");
        setVisible(true);
        setSize(300,370);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    class PadActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String operation = e.getActionCommand();
            if(operation.equals("C")){
                text.setText("");
            }
            else if(operation.equals("=")) {
                String result = Double.toString(calculate(text.getText()));
                text.setText("" + result);
                num = "";
            }
//             else if (operation.equals("+") || operation.equals("-") || operation.equals("×") || operation.equals("÷")) {
//                if(text.getText().equals("") && operation.equals("-")){
//                    text.setText(text.getText() + e.getActionCommand());
//                }
//                else if (!text.getText().equals("") && !prev_operation.equals("+") && !prev_operation.equals("-")
//                        && !prev_operation.equals("×") && !prev_operation.equals("÷")) {
//                    text.setText(text.getText() + e.getActionCommand());
//                }
//            }
           else {
                text.setText(text.getText() + e.getActionCommand());
            }
            prev_operation = e.getActionCommand();
        }
    }
    private void stringParsing(String text){
        array.clear();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '-' || ch == '+' || ch == '×' || ch == '÷') {
                array.add(num);
                num = "";
                array.add(ch + "");
                array.remove("");
            } else {
                num = num + ch;
            }
        }
        array.add(num);
    }
    public double calculate(String text){
        stringParsing(text);

        double prev = 0;
        double current = 0;
        String mode = "";
        for (int i = 0; i < array.size(); i++) {
            String s = array.get(i);
            if(s.equals("×")) {
                mode = "mul";
            }
            else if(s.equals("÷")) {
                mode = "div";
            }
            else {
                System.out.println(array);
                if ((mode.equals("mul") || mode.equals("div"))) {
                    System.out.println("get = "+array.get(i));
                    double result = 0.0;
                    Double one = 0.0;
                    Double two = 0.0;
                    if (array.get(i).equals("-")) {
                        System.out.println("음수!");
                        two = -(Double.parseDouble(array.get(i + 1)));
                        try {
                            if (array.get(i - 3).equals("-")) {
                                one = -(Double.parseDouble(array.get(i - 2)));
                                array.remove(i - 3);
                                i -= 1;
                            }
                        } catch (IndexOutOfBoundsException e) {
                            one = Double.parseDouble(array.get(i - 2));
                        }
                        System.out.println("one = " + one + " two = " + two);
                        array.remove(i);
                    }
                    else {
                        System.out.println("i = "+i);
                        System.out.println("!!!!");
                        one = Double.parseDouble(array.get(i - 2));
                        two = Double.parseDouble(array.get(i));
                        System.out.println("one = " + one + " two = " + two);
                    }
                    if (mode.equals("mul")) {
                        result = one * two;
                        System.out.println("result = " + result);
                    } else {
                        result = one / two;
                        System.out.println("result = " + result);
                    }
                    array.add(i + 1, Double.toString(result));
                    for (int j = 0; j < 3; j++) {
                        array.remove(i - 2);
                    }
                    i -= 2;//결과값이 생긴 인덱스로 이동
                    mode="";
                }
            }
        }//곱셈 나눗셈 우선 계산
        System.out.println(array);
        for(String s : array){
            if(s.equals("+")){
                mode = "add";
            }
            else if(s.equals("-")) {
                mode = "sub";
            }
            else {
                current = Double.parseDouble(s);
                if (mode.equals("add")){
                    prev += current;
                }
                else if(mode.equals("sub")){
                    prev -= current;
                    System.out.println("sub prev = " + prev + " current = " + current);
                }
                else{
                    prev = current;
                }
            }
            prev= Math.round(prev *100000)/100000.0;
        }
        return prev;
    }

    public static void main(String[] args) {
        new Calculator();
    }
}

