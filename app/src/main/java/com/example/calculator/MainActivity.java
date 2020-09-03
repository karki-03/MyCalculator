package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;


public class MainActivity extends AppCompatActivity {


    TextView data, output;
    Button num1,num2,num3,num4,num5,num6,num7,num8,num9,num0,dot,brackclose,brackopen,percentage;
    Button clr, minus, add, multiply, divide, equal;
    String previous="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Views
        data=findViewById(R.id.data);
        output=findViewById(R.id.output);
        //numbers
        num1=findViewById(R.id.number1);
        num2=findViewById(R.id.number2);
        num3=findViewById(R.id.number3);
        num4=findViewById(R.id.number4);
        num5=findViewById(R.id.number5);
        num6=findViewById(R.id.number6);
        num7=findViewById(R.id.number7);
        num8=findViewById(R.id.number8);
        num9=findViewById(R.id.number9);
        num0=findViewById(R.id.number0);
        //operations
        clr=findViewById(R.id.clear);
        equal=findViewById(R.id.equal);
        minus=findViewById(R.id.substraction);
        add=findViewById(R.id.Addition);
        multiply=findViewById(R.id.multiplication);
        divide=findViewById(R.id.division);
        dot=findViewById(R.id.dot);
        brackclose=findViewById(R.id.bracketclose);
        brackopen=findViewById(R.id.bracketopen);
        percentage=findViewById(R.id.percentage);



        num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous=previous+"1 ";
                data.setText(previous);
            }
        });
        num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous=previous+"2 ";
                data.setText(previous);
            }
        });
        num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous=previous+"3 ";
                data.setText(previous);
            }
        });

        num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous=previous+"4 ";
                data.setText(previous);
            }
        });
        num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous=previous+"5 ";
                data.setText(previous);
            }
        });
        num6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous=previous+"6 ";
                data.setText(previous);
            }
        });

        num7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous=previous+"7 ";
                data.setText(previous);
            }
        });
        num8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous=previous+"8 ";
                data.setText(previous);
            }
        });
        num9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous=previous+"9 ";
                data.setText(previous);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous=previous+"+ ";
                data.setText(previous);
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous=previous+"- ";
                data.setText(previous);
            }
        });
        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous=previous+"/ ";
                data.setText(previous);
            }
        });
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous=previous+"* ";
                data.setText(previous);
            }
        });
        brackopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous=previous+"( ";
                data.setText(previous);
            }
        });
        brackclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous=previous+") ";
                data.setText(previous);
            }
        });
        percentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous=previous+"% ";
                data.setText(previous);
            }
        });
        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous=previous+". ";
                data.setText(previous);
            }
        });
        clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous="";
                data.setText("0");
                output.setText("0");
            }
        });

        //Evaluating

        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expression= data.getText().toString();
                int k=MainActivity.evaluate(expression);

                output.setText(k+"");


            }
        });


    }

    public static int evaluate(String expression)
    {
        char[] tokens = expression.toCharArray();

        // Stack for numbers: 'values'
        Stack<Integer> values = new Stack<Integer>();

        // Stack for Operators: 'ops'
        Stack<Character> ops = new Stack<Character>();

        for (int i = 0; i < tokens.length; i++)
        {
            // Current token is a whitespace, skip it
            if (tokens[i] == ' ')
                continue;

            // Current token is a number, push it to stack for numbers
            if (tokens[i] >= '0' && tokens[i] <= '9')
            {
                StringBuffer sbuf = new StringBuffer();
                // There may be more than one digits in number
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
                    sbuf.append(tokens[i++]);
                values.push(Integer.parseInt(sbuf.toString()));
            }

            // Current token is an opening brace, push it to 'ops'
            else if (tokens[i] == '(')
                ops.push(tokens[i]);

                // Closing brace encountered, solve entire brace
            else if (tokens[i] == ')')
            {
                while (ops.peek() != '(')
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.pop();
            }

            // Current token is an operator.
            else if (tokens[i] == '+' || tokens[i] == '-' ||
                    tokens[i] == '*' || tokens[i] == '/')
            {
                // While top of 'ops' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'ops'
                // to top two elements in values stack
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));

                // Push current token to 'ops'.
                ops.push(tokens[i]);
            }
        }

        // Entire expression has been parsed at this point, apply remaining
        // ops to remaining values
        while (!ops.empty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));

        // Top of 'values' contains result, return it
        return values.pop();
    }

    // Returns true if 'op2' has higher or same precedence as 'op1',
    // otherwise returns false.
    public static boolean hasPrecedence(char op1, char op2)
    {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    // A utility method to apply an operator 'op' on operands 'a'
    // and 'b'. Return the result.
    public static int applyOp(char op, int b, int a)
    {
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new
                            UnsupportedOperationException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }
}
