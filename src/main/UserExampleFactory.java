package src.main;
import java.util.ArrayList;

/**
 * This is a temporary class to test my UserExample functions
 * as well as to hold some user exercises to get started
 * @author Patrick Whitlock
 */
public class UserExampleFactory {

    public static UserExample getEx0() {
        ArrayList<String> t0 = new ArrayList<>();
        ArrayList<CodeBlock> T0 = new ArrayList<>();
        ArrayList<Line> lines = new ArrayList<>();

        t0.add("public static void main(String[] args){");
        t0.add("int a = 2;");
        t0.add("int b = 3;");
        t0.add("int c;");
        t0.add("c = a + b;");
        t0.add("System.out.println(c);");
        t0.add("}");

        String codeHTML = "<HTML> " +
                "    <p> public static void main(String[] args){\n" +
                "    <p>    &nbsp int a = 2; </p>" +
                "    <p>    &nbsp int b = 3; </p>" +
                "    <p>    &nbsp int c; </p>" +
                "    <p>    &nbsp c = a + b; </p>" +
                "    <p>    &nbsp System.out.println(c); </p>" +
                "    <p>    &nbsp return; </p>" +
                "} </HTML>";

        BlockFactory factory = new BlockFactory();

        CodeBlock cb0 = factory.makeBlock("Start", 200, 100);
        CodeBlock cb1 = factory.makeBlock("Variable", 200, 200);
        CodeBlock cb2 = factory.makeBlock("Variable", 200, 270);
        CodeBlock cb3 = factory.makeBlock("Variable", 200, 350);
        CodeBlock cb4 = factory.makeBlock("Instruction", 200, 420);
        CodeBlock cb5 = factory.makeBlock("Print", 200, 500);
        CodeBlock cb6 = factory.makeBlock("Stop", 200, 600);

        cb1.setText("int a = 2;");
        cb2.setText("int b = 3;");
        cb3.setText("int c;");
        cb4.setText("c = a + b;");
        cb5.setText("System.out.println(c);");

        cb0.addToOutbound(cb1);
        cb1.addToInbound(cb0);
        cb1.addToOutbound(cb2);
        cb2.addToInbound(cb1);
        cb2.addToOutbound(cb3);
        cb3.addToInbound(cb2);
        cb3.addToOutbound(cb4);
        cb4.addToInbound(cb3);
        cb4.addToOutbound(cb5);
        cb5.addToInbound(cb4);
        cb5.addToOutbound(cb6);
        cb6.addToInbound(cb5);

        lines.add(new Line(cb0,cb1));
        lines.add(new Line(cb1,cb2));
        lines.add(new Line(cb2,cb3));
        lines.add(new Line(cb3,cb4));
        lines.add(new Line(cb4,cb5));
        lines.add(new Line(cb5,cb6));

        T0.add(cb0);
        T0.add(cb1);
        T0.add(cb2);
        T0.add(cb3);
        T0.add(cb4);
        T0.add(cb5);
        T0.add(cb6);

        return new UserExample(t0, codeHTML,T0,lines, "Exercise 0");
    }

    public static UserExample getEx1() {
        ArrayList<String> t0 = new ArrayList<>();
        ArrayList<CodeBlock> T0 = new ArrayList<>();
        ArrayList<Line> lines = new ArrayList<>();

        t0.add("public static void main(String[] args){");
        t0.add("int y = 1;");
        t0.add("int x = 2;");
        t0.add("if(x == y){");
        t0.add("System.out.println(\"X is the same as y\");");
        t0.add("return;");
        t0.add("}");
        t0.add("System.out.println(\"X is not the same as y\");");
        t0.add("}");

        String codeHTML = "<HTML> " +
                "    <p> public static void main(String[] args){\n" +
                "    <p>    &nbsp int y = 1; </p>" +
                "    <p>    &nbsp int x = 2; </p>" +
                "    <p> &nbsp if(x == y){ </p>" +
                "    <p>    &nbsp &nbsp System.out.println(\"X is the same as y\"); </p>" +
                "    <p>    &nbsp &nbsp return; </p>" +
                "    <p>&nbsp }</p>" +
                "    <p>&nbsp System.out.println(\"X is not the same as y\"); </p>" +
                "} </HTML>";

        BlockFactory factory = new BlockFactory();

        CodeBlock cb0 = factory.makeBlock("Start", 150, 100);
        CodeBlock cb1 = factory.makeBlock("Variable", 150, 200);
        CodeBlock cb2 = factory.makeBlock("Variable", 150, 270);
        CodeBlock cb3 = factory.makeBlock("If", 150, 350);
        CodeBlock cb4 = factory.makeBlock("Print", 150, 450);
        CodeBlock cb5 = factory.makeBlock("Print", 400, 450);
        CodeBlock cb6 = factory.makeBlock("Stop", 150, 550);
        CodeBlock cb7 = factory.makeBlock("Stop", 300, 550);

        cb1.setText("int y = 1;");
        cb2.setText("int x = 2;");
        cb3.setText("if(x == y)");
        cb4.setText("System.out.println(\"X is the same as y\");");
        cb5.setText("System.out.println(\"X is not the same as y\");");


        cb0.addToOutbound(cb1);
        cb1.addToInbound(cb0);
        cb1.addToOutbound(cb2);
        cb2.addToInbound(cb1);
        cb2.addToOutbound(cb3);
        cb3.addToInbound(cb2);
        cb3.addToOutbound(cb4);
        cb3.addToOutbound(cb5);

        cb4.addToInbound(cb3);
        cb4.addToOutbound(cb6);

        cb5.addToInbound(cb3);
        cb5.addToOutbound(cb7);

        cb6.addToInbound(cb4);
        cb7.addToInbound(cb5);

        lines.add(new Line(cb0,cb1));
        lines.add(new Line(cb1,cb2));
        lines.add(new Line(cb2,cb3));
        lines.add(new Line(cb3,cb4));
        lines.add(new Line(cb3,cb5));
        lines.add(new Line(cb4,cb6));
        lines.add(new Line(cb5,cb7));

        T0.add(cb0);
        T0.add(cb1);
        T0.add(cb2);
        T0.add(cb3);
        T0.add(cb4);
        T0.add(cb5);
        T0.add(cb6);
        T0.add(cb7);

        return new UserExample(t0, codeHTML, T0,lines,"Exercise 1");
    }
    public static UserExample getEx2() {
        ArrayList<String> t0 = new ArrayList<>();
        ArrayList<CodeBlock> T0 = new ArrayList<>();
        ArrayList<Line> lines = new ArrayList<>();

        t0.add("public static void main(String[] args){");
        t0.add("int n = 100;");
        t0.add("int t1 = 0;");
        t0.add("int t2 = 1;");
        t0.add("while(t1 <= n){");
        t0.add("int sum = t1+t2;");
        t0.add("t1 = t2;");
        t0.add("t2 = sum;");
        t0.add("System.out.println(\"sum: \"+sum);");
        t0.add("}");
        t0.add("}");

        String codeHTML = "<HTML> " +
                "    <p> public static void main(String[] args){\n" +
                "    <p>    &nbsp int n = 100; </p>" +
                "    <p>    &nbsp int t1 = 0; </p>" +
                "    <p>    &nbsp int t2 = 1; </p>" +
                "    <p> &nbsp while(t1 <= n){ </p>" +
                "    <p>    &nbsp &nbsp int sum = t1+t2; </p>" +
                "    <p>    &nbsp &nbsp t1 = t2; </p>" +
                "    <p>    &nbsp &nbsp t2 = sum; </p>" +
                "    <p>    &nbsp &nbsp int sum = t1+t2; </p>" +
                "    <p>    &nbsp &nbsp System.out.println(\"sum: \"+sum); </p>" +
                "    <p>&nbsp }</p>" +
                "} </HTML>";

        BlockFactory factory = new BlockFactory();

        CodeBlock cb0 = factory.makeBlock("Start", 150, 60);
        CodeBlock cb1 = factory.makeBlock("Variable", 150, 160);
        CodeBlock cb2 = factory.makeBlock("Variable", 150, 230);
        CodeBlock cb3 = factory.makeBlock("Variable", 150, 300);
        CodeBlock cb4 = factory.makeBlock("Loop", 150, 370);
        CodeBlock cb5 = factory.makeBlock("Stop", 70, 500);
        CodeBlock cb6 = factory.makeBlock("Variable", 250, 450);
        CodeBlock cb7 = factory.makeBlock("Instruction", 250, 520);
        CodeBlock cb8 = factory.makeBlock("Instruction", 250, 590);
        CodeBlock cb9 = factory.makeBlock("Print", 250, 670);

        cb1.setText("int n = 100;");
        cb2.setText("int t1 = 0;");
        cb3.setText("int t2 = 1;");
        cb4.setText("while(t1 <= n)");
        cb6.setText("int sum = t1+t2;");
        cb7.setText("t1 = t2;");
        cb8.setText("t2 = sum;");
        cb9.setText("System.out.println(\"sum: \"+sum);");

        cb0.addToOutbound(cb1);
        cb1.addToInbound(cb0);
        cb1.addToOutbound(cb2);
        cb2.addToInbound(cb1);
        cb2.addToOutbound(cb3);
        cb3.addToInbound(cb2);
        cb3.addToOutbound(cb4);

        cb4.addToInbound(cb3);
        cb4.addToInbound(cb9);
        cb4.addToOutbound(cb5);
        cb4.addToOutbound(cb6);

        cb5.addToInbound(cb4);
        cb6.addToInbound(cb4);
        cb6.addToOutbound(cb7);
        cb7.addToInbound(cb6);
        cb7.addToOutbound(cb8);
        cb8.addToInbound(cb7);
        cb8.addToOutbound(cb9);
        cb9.addToInbound(cb8);
        cb9.addToOutbound(cb4);

        lines.add(new Line(cb0,cb1));
        lines.add(new Line(cb1,cb2));
        lines.add(new Line(cb2,cb3));
        lines.add(new Line(cb3,cb4));
        lines.add(new Line(cb4,cb5));
        lines.add(new Line(cb4,cb6));
        lines.add(new Line(cb6,cb7));
        lines.add(new Line(cb7,cb8));
        lines.add(new Line(cb8,cb9));
        lines.add(new Line(cb9,cb4));


        T0.add(cb0);
        T0.add(cb1);
        T0.add(cb2);
        T0.add(cb3);
        T0.add(cb4);
        T0.add(cb5);
        T0.add(cb6);
        T0.add(cb7);
        T0.add(cb8);
        T0.add(cb9);

        return new UserExample(t0,codeHTML,T0,lines, "Excercise 2");
    }

    public static UserExample getFutureExercise(String name,int id) {
        UserExample problem;
        if(id == 0) problem = getEx0();
        else if(id == 1) problem = getEx1();
        else problem = getEx2();

        problem.setExampleName("Exercise A"+name);
        return problem;
    }

}
