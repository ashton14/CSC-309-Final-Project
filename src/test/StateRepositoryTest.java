package src.test;

import src.main.*;
import org.junit.Test;
import src.main.Shape;


import static org.junit.Assert.*;

public class StateRepositoryTest {


    /**
     * Helper method to make a CodeBlock for testing.
     * @return A CodeBlock that happens to be of type
     * Variable at coordinates 0,0.
     */
    public CodeBlock makeTestBlock(){
        BlockFactory blockFactory = new BlockFactory();
        return blockFactory.makeBlock("Variable", 0,0);
    }

    /**
     * Helper method to make a Line for testing.
     * @return A Line consisting between two CodeBlocks
     * created with makeTestBlock().
     */
    public Line makeTestLine(){
        return new Line(makeTestBlock(), makeTestBlock());
    }

    /**
     * Ensures that StateRepository.getInstance() returns an instance of StateRepository.
     */
    @Test
    public void getInstanceTest() {
        assertSame(StateRepository.getInstance().getClass(), StateRepository.class);
    }


    /**
     * Ensures that the currently selected line
     * is initially null.
     */
    @Test
    public void getCurrentlySelectedLineTestNull() {
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
        stateRepository.reset();
        assertNull(stateRepository.getCurrentlySelectedLine());
    }

    /**
     * Ensures that setting a Line as currently selected
     * allows it to be retrieved as a Drawable or Line
     * and not a CodeBlock.
     */
    @Test
    public void getCurrentlySelectedLineTest() {
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
        stateRepository.reset();
        Line line = makeTestLine();
        stateRepository.setCurrentlySelectedDrawable(line);
        assertEquals(stateRepository.getCurrentlySelectedLine(), line);
        assertEquals(stateRepository.getCurrentlySelectedDrawable(), line);
        assertNull(stateRepository.getCurrentlySelectedCodeBlock());
    }

    /**
     * Ensures that the currently selected CodeBlock
     * is initially null.
     */
    @Test
    public void setCurrentlySelectedCodeBlockTestNull() {
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
        stateRepository.reset();
        assertNull(stateRepository.getCurrentlySelectedCodeBlock());
    }

    /**
     * Ensures that setting a CodeBlock as currently selected
     * allows it to be retrieved as a Drawable or CodeBlock
     * and not a Line.
     */
    @Test
    public void setCurrentlySelectedCodeBlockTest() {
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
        stateRepository.reset();
        CodeBlock testBlock = makeTestBlock();
        stateRepository.setCurrentlySelectedDrawable(testBlock);
        assertEquals(stateRepository.getCurrentlySelectedCodeBlock(), testBlock);
        assertEquals(stateRepository.getCurrentlySelectedDrawable(), testBlock);
        assertNull(stateRepository.getCurrentlySelectedLine());
    }

    /**
     * Ensures that the StateRepository.getCurrentlySelectedDrawable()
     * and its associated CodeBlock and Line methods initially return null.
     */
    @Test
    public void getCurrentlySelectedOutlineNullTest() {
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
        stateRepository.reset();
        assertNull(stateRepository.getCurrentlySelectedLineOutline());
        assertNull(stateRepository.getCurrentlySelectedCodeBlockOutline());
        assertNull(stateRepository.getCurrentlySelectedDrawable());
    }

    /**
     * Ensures that the StateRepository.getCurrentlySelectedDrawable()
     * and its associated CodeBlock method return outlines of the
     * same shape of a given CodeBlock at the same position when
     * a CodeBlock is selected and null when one is not.
     */
    @Test
    public void getCurrentlySelectedOutlineCodeBlockTest() {
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
        stateRepository.reset();
        CodeBlock testBlock = makeTestBlock();
        stateRepository.setCurrentlySelectedDrawable(testBlock);
        assertNull(stateRepository.getCurrentlySelectedLineOutline());
        Shape testShape = testBlock.copyShape();

        Shape repoDrawable = (Shape) stateRepository.getCurrentlySelectedDrawableOutline();
        assertEquals(testShape.getClass(), repoDrawable.getClass());
        assertNotEquals(testShape.getColor(), repoDrawable.getColor());
        assertEquals(testShape.getYCenter(), repoDrawable.getYCenter());
        assertEquals(testShape.getXCenter(), repoDrawable.getXCenter());

        Shape repoShape = stateRepository.getCurrentlySelectedCodeBlockOutline();
        assertEquals(testShape.getClass(), repoShape.getClass());
        assertNotEquals(testShape.getColor(), repoShape.getColor());
        assertEquals(testShape.getYCenter(), repoShape.getYCenter());
        assertEquals(testShape.getXCenter(), repoShape.getXCenter());
    }

    /**
     * Ensures that the StateRepository.getCurrentlySelectedDrawable()
     * and its associated Line method return outlines of the
     * given Line (starts and ends at same points) and that
     * getCurrentlySelectedCodeBlock() returns null when a Line
     * is selected.
     */
    @Test
    public void getCurrentlySelectedOutlineLineTest() {
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
        stateRepository.reset();
        Line testLine = makeTestLine();
        stateRepository.setCurrentlySelectedDrawable(testLine);
        Line testDrawable = (Line) stateRepository.getCurrentlySelectedDrawable();
        assertNull(stateRepository.getCurrentlySelectedCodeBlockOutline());
        Line repoLine = stateRepository.getCurrentlySelectedLineOutline();
        assertEquals(repoLine.getStart(), testLine.getStart());
        assertEquals(repoLine.getEnd(), testLine.getEnd());
        assertEquals(repoLine.getStart(), testDrawable.getStart());
        assertEquals(repoLine.getEnd(), testDrawable.getEnd());
    }

    /**
     * Validates getters and setters for StateRepository.MenuBarCodeBlock. Also
     * ensures that the initial value is "Start".
     */
    @Test
    public void setMenuBarCodeBlockTest() {
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
        stateRepository.reset();
        assertEquals("Start", stateRepository.getMenuBarCodeBlock());
        String codeBlockString = "Variable";
        stateRepository.setMenuBarCodeBlock(codeBlockString);
        assertEquals(codeBlockString, stateRepository.getMenuBarCodeBlock());
    }

    /**
     * Validates getters and setters for StateRepository.selectedMenuItem. Also
     * ensures that the initial value is null and this method works with null values.
     */
    @Test
    public void setSelectedMenuItem() {
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
        stateRepository.reset();
        assertNull(stateRepository.getSelectedMenuItem());
        stateRepository.setSelectedMenuItem("test item");
        assertEquals(stateRepository.getSelectedMenuItem(), "test item");
        stateRepository.setSelectedMenuItem(null);
        assertNull(stateRepository.getSelectedMenuItem());
    }

    /**
     * Ensures that DataRepository.deleteSelectedItem() removes
     * only the selected item regardless of position and
     * does not crash when no item is selected.
     */
    @Test
    public void deleteSelectedItem() {
        DataRepository dataRepository = (DataRepository) DataRepository.getInstance();
        dataRepository.clear();
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
        stateRepository.reset();

        CodeBlock codeBlock = makeTestBlock();
        CodeBlock codeBlock2 = makeTestBlock();
        Line line = makeTestLine();
        dataRepository.addDrawable(codeBlock);
        dataRepository.addDrawable(codeBlock2);
        dataRepository.addDrawable(line);

        // remove middle
        stateRepository.setCurrentlySelectedDrawable(codeBlock2);
        stateRepository.deleteSelectedItem();

        assertFalse(dataRepository.getDrawables().contains(codeBlock2));
        assertTrue(dataRepository.getDrawables().contains(line));
        assertTrue(dataRepository.getDrawables().contains(codeBlock));
        assertNull(stateRepository.getCurrentlySelectedDrawable());
        //remove left
        stateRepository.setCurrentlySelectedDrawable(codeBlock);
        stateRepository.deleteSelectedItem();
        assertFalse(dataRepository.getDrawables().contains(codeBlock));
        assertTrue(dataRepository.getDrawables().contains(line));
        assertNull(stateRepository.getCurrentlySelectedDrawable());
        //remove right
        stateRepository.setCurrentlySelectedDrawable(line);
        stateRepository.deleteSelectedItem();
        assertFalse(dataRepository.getDrawables().contains(line));
        assertEquals(0, dataRepository.getDrawables().size());
        //remove none (avoid crash)
        stateRepository.deleteSelectedItem();
    }

    /**
     * Validates getters and setters for StateRepository.status. Also
     * ensures that the initial value is "Placing Start Blocks"
     * and works with null.
     */
    @Test
    public void setStatus() {
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
        stateRepository.reset();
        assertEquals("Placing Start Blocks", stateRepository.getStatus());
        stateRepository.setStatus("New Status");
        assertEquals("New Status", stateRepository.getStatus());
        stateRepository.setStatus(null);
        assertNull(stateRepository.getStatus());
    }
}