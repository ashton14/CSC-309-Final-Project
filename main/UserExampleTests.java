package main;

import java.util.ArrayList;

/**
 * This is a temporary class to test my main.UserExample functions
 * as well as to hold some user exercises to get started
 * @author Patrick Whitlock
 */
public class UserExampleTests {

    public static UserExample getEx0() {
        ArrayList<String> t0 = new ArrayList<>();
        ArrayList<CodeBlock> T0 = new ArrayList<>();
        ArrayList<Line> lines = new ArrayList<>();

        t0.add("int a = 2;");
        t0.add("int b = 3;");
        t0.add("int c;");
        t0.add("c = a + b;");
        t0.add("System.out.println(c);");

        BlockFactory factory = new BlockFactory();

        CodeBlock cb0 = factory.makeBlock("Start", 200, 100);
        CodeBlock cb1 = factory.makeBlock("Variable", 200, 200);
        CodeBlock cb2 = factory.makeBlock("Variable", 200, 270);
        CodeBlock cb3 = factory.makeBlock("Variable", 200, 350);
        CodeBlock cb4 = factory.makeBlock("Instruction", 200, 420);
        CodeBlock cb5 = factory.makeBlock("Print", 200, 500);
        CodeBlock cb6 = factory.makeBlock("Stop", 200, 600);

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

        return new UserExample(t0, T0,lines, "ex0");
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

        BlockFactory factory = new BlockFactory();

        CodeBlock cb0 = factory.makeBlock("Start", 150, 100);
        CodeBlock cb1 = factory.makeBlock("Variable", 150, 200);
        CodeBlock cb2 = factory.makeBlock("Variable", 150, 270);
        CodeBlock cb3 = factory.makeBlock("If", 150, 350);
        CodeBlock cb4 = factory.makeBlock("Print", 150, 450);
        CodeBlock cb5 = factory.makeBlock("Print", 300, 450);
        CodeBlock cb6 = factory.makeBlock("Stop", 150, 550);
        CodeBlock cb7 = factory.makeBlock("Stop", 300, 550);

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

        return new UserExample(t0,T0,lines,"ex1");
    }

    public static void main(String [] args) {

        //User code Test
        ArrayList<String> usersCode = new ArrayList<>();
        usersCode.add("int a = 2;");
        usersCode.add("int b = 3;");
        usersCode.add("int c;");
        usersCode.add("c = a + b;");
        //usersCode.add("System.out.println(c);");

        UserExample ex0 = getEx0();
        int result = ex0.gradeUserCode(usersCode);
        System.out.println(result);
    }
}
