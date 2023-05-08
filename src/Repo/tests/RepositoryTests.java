
package Repo.tests;
import main.*;


public class RepositoryTests {

    @Test
    public void testGetInstance() {
        Repository repo1 = Repository.getInstance();
        Repository repo2 = Repository.getInstance();
        assertNotNull(repo1);
        assertNotNull(repo2);
        assertSame(repo1, repo2);
    }

    @Test
    public void testSelectedCodeBlock() {
        Repository.getInstance().setMenubarCodeBlock("Start");
        assertEquals(Repository.getInstance().getMenubarCodeBlock(), "Start");
        Repository.getInstance().setMenubarCodeBlock("Any Code Block From The List");
        assertEquals(Repository.getInstance().getMenubarCodeBlock(), "Any Code Block From The List");
    }

    @Test
    public void testSelectedMenuItem() {
        Repository.getInstance().setMenuBarAction("About");
        assertEquals(Repository.getInstance().getMenuBarAction(), "About");
        Repository.getInstance().setMenuBarAction("Any Menu Item From The List");
        assertEquals(Repository.getInstance().getMenuBarAction(), "Any Menu Item From The List");
    }

    @Test
    public void testAddingCodeBlocks() {
        Shape rect = new Rectangle(100, 100, 50, 50, Color.BLACK);
        Shape circ = new Circle(100, 100, 50, Color.BLACK);
        Shape diam = new Diamond(100, 100, 50, 50, Color.BLACK);
        CodeBlock funcBlock = new FunctionBlock(rect, "");
        CodeBlock startBlock = new StartBlock(circ, "");
        CodeBlock loopBlock = new LoopBlock(diam, "");
        ArrayList<CodeBlock> codeBlocks = new ArrayList<>(Arrays.asList(funcBlock, startBlock, loopBlock));
        Repository.getInstance().setCodeBlocks(codeBlocks);

        assertTrue(Repository.getInstance().getCodeBlocks().contains(funcBlock));
        assertTrue(Repository.getInstance().getCodeBlocks().contains(startBlock));
        assertTrue(Repository.getInstance().getCodeBlocks().contains(loopBlock));

        Repository.getInstance().setCodeBlocks(null);
        Repository.getInstance().addCodeBlock(funcBlock);
        Repository.getInstance().addCodeBlock(startBlock);
        Repository.getInstance().addCodeBlock(loopBlock);

        assertTrue(Repository.getInstance().getCodeBlocks().contains(funcBlock));
        assertTrue(Repository.getInstance().getCodeBlocks().contains(startBlock));
        assertTrue(Repository.getInstance().getCodeBlocks().contains(loopBlock));
        assertTrue(Repository.getInstance().getDrawables().contains(funcBlock));
        assertTrue(Repository.getInstance().getDrawables().contains(startBlock));
        assertTrue(Repository.getInstance().getDrawables().contains(loopBlock));

    }

    @Test
    public void testAddingLines() {
        Shape rect = new Rectangle(100, 200, 50, 50, Color.BLACK);
        Shape circ = new Circle(55, 55, 66, Color.BLACK);
        Shape diam = new Diamond(22, 333, 14, 25, Color.BLACK);
        CodeBlock funcBlock = new FunctionBlock(rect, "");
        CodeBlock startBlock = new StartBlock(circ, "");
        CodeBlock loopBlock = new LoopBlock(diam, "");
        Line line1 = new Line(startBlock, loopBlock);
        Line line2 = new Line(loopBlock, funcBlock);
        Line line3 = new Line(funcBlock, loopBlock);
        ArrayList<Line> lines = new ArrayList<>(Arrays.asList(line1, line2, line3));
        Repository.getInstance().setLines(lines);

        assertTrue(Repository.getInstance().getLines().contains(line1));
        assertTrue(Repository.getInstance().getLines().contains(line2));
        assertTrue(Repository.getInstance().getLines().contains(line3));

        Repository.getInstance().setLines(null);
        Repository.getInstance().addLine(line1);
        Repository.getInstance().addLine(line2);
        Repository.getInstance().addLine(line3);

        assertTrue(Repository.getInstance().getLines().contains(line1));
        assertTrue(Repository.getInstance().getLines().contains(line2));
        assertTrue(Repository.getInstance().getLines().contains(line2));
        assertTrue(Repository.getInstance().getDrawables().contains(line1));
        assertTrue(Repository.getInstance().getDrawables().contains(line2));
        assertTrue(Repository.getInstance().getDrawables().contains(line3));

    }

    @Test
    public void testClearAndUndo() {
        Shape rect = new Rectangle(100, 200, 50, 50, Color.BLACK);
        Shape circ = new Circle(55, 55, 66, Color.BLACK);
        Shape diam = new Diamond(22, 333, 14, 25, Color.BLACK);
        CodeBlock funcBlock = new FunctionBlock(rect, "");
        CodeBlock startBlock = new StartBlock(circ, "");
        CodeBlock loopBlock = new LoopBlock(diam, "");
        Line line1 = new Line(startBlock, loopBlock);
        Line line2 = new Line(loopBlock, funcBlock);
        Line line3 = new Line(funcBlock, loopBlock);

        Repository.getInstance().addCodeBlock(funcBlock);
        Repository.getInstance().addLine(line1);
        Repository.getInstance().addLine(line2);
        Repository.getInstance().addCodeBlock(startBlock);
        Repository.getInstance().addCodeBlock(loopBlock);
        Repository.getInstance().addLine(line3);

        assertTrue(Repository.getInstance().getCodeBlocks().contains(funcBlock));
        assertTrue(Repository.getInstance().getLines().contains(line1));
        assertTrue(Repository.getInstance().getLines().contains(line2));
        assertTrue(Repository.getInstance().getCodeBlocks().contains(startBlock));
        assertTrue(Repository.getInstance().getCodeBlocks().contains(loopBlock));
        assertTrue(Repository.getInstance().getLines().contains(line3));

        Repository.getInstance().undo();

        assertFalse(Repository.getInstance().getLines().contains(line3));

        Repository.getInstance().undo();
        Repository.getInstance().undo();

        assertFalse(Repository.getInstance().getCodeBlocks().contains(loopBlock));
        assertFalse(Repository.getInstance().getCodeBlocks().contains(startBlock));
        assertTrue(Repository.getInstance().getCodeBlocks().contains(funcBlock));
        assertTrue(Repository.getInstance().getLines().contains(line1));
        assertTrue(Repository.getInstance().getLines().contains(line2));

        Repository.getInstance().clear();
        assertTrue(Repository.getInstance().getCodeBlocks().isEmpty());

        Repository.getInstance().addLine(line1);
        assertFalse(Repository.getInstance().getLines().isEmpty());
        assertTrue(Repository.getInstance().getLines().contains(line1));

    }

}
