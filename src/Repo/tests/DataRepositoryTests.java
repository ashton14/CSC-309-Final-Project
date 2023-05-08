package Repo.tests;
import static org.junit.Assert.*;

import main.*;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

/**
 * Test cases for DataRepository and
 * DrawableData classes. 100% statement coverage.
 */
public class DataRepositoryTests {

    /**
     * Ensures that multiple instances of the DataRepository
     * can be gotten from DataRepository.getInstance().
     */
    @Test
    public void testInstance(){
        Repository repository1 = DataRepository.getInstance();
        assertNotEquals(repository1, null);
        assertEquals(repository1.getClass(), DataRepository.class);
        Repository repository2 = DataRepository.getInstance();
        assertNotEquals(repository2, null);
        assertEquals(repository2.getClass(), DataRepository.class);
    }

    /**
     * Ensures that the DataRepository instance
     * stores and returns data of type DrawableData.
     */
    @Test
    public void testDataType(){
        Repository repository = DataRepository.getInstance();
        assertEquals(repository.getData().getClass(), DrawableData.class);
    }

    /**
     * Ensures the DrawableData from the DataRepository contains an
     * ArrayList of type Drawable that is initially empty.
     */
    @Test
    public void testDataInitial(){
        Repository repository = DataRepository.getInstance();
        DrawableData data = (DrawableData) repository.getData();
        assertEquals(data.getDrawables().size(), 0);
    }


    /**
     * Ensures that DrawableData can add one drawable object.
     */
    @Test
    public void testDataAddOne(){
        Repository repository = DataRepository.getInstance();
        DrawableData data = (DrawableData) repository.getData();
        data.clear();
        BlockFactory blockFactory = new BlockFactory();
        CodeBlock varBlock = blockFactory.makeBlock("Variable",0,0);
        data.addDrawable(varBlock);
        assertEquals(data.getDrawables().size(), 1);
        assertEquals(data.getDrawables().get(0), varBlock);
    }

    /**
     * Ensures that DrawableData can add three drawable objects
     * and that the order is preserved.
     */
    @Test
    public void testDataAddThree(){
        Repository repository = DataRepository.getInstance();
        DrawableData data = (DrawableData) repository.getData();
        data.clear();
        BlockFactory blockFactory = new BlockFactory();
        CodeBlock start = blockFactory.makeBlock("Variable", 0,0);
        CodeBlock end = blockFactory.makeBlock("Variable", 0,0);
        Line line = new Line(start, end);
        data.addDrawable(start);
        data.addDrawable(end);
        data.addDrawable(line);
        assertEquals(data.getDrawables().size(), 3);
        assertEquals(data.getDrawables().get(0), start);
        assertEquals(data.getDrawables().get(1), end);
        assertEquals(data.getDrawables().get(2), line);
    }

    /**
     * Ensures that DrawableData does not support the
     * addition of null references.
     */
    @Test
    public void testDataAddNull(){
        Repository repository = DataRepository.getInstance();
        DrawableData data = (DrawableData) repository.getData();
        data.clear();
        data.addDrawable(null);
        assertEquals(0, data.getDrawables().size());
    }

    /**
     * Ensures that DrawableData.getLines() returns an
     * ArrayList of type Line containing all lines
     * within the StateData object with the order
     * preserved.
     */
    @Test
    public void testDataGetLines(){
        Repository repository = DataRepository.getInstance();
        DrawableData data = (DrawableData) repository.getData();
        data.clear();
        BlockFactory blockFactory = new BlockFactory();
        CodeBlock start = blockFactory.makeBlock("Variable", 0,0);
        CodeBlock end = blockFactory.makeBlock("Variable", 0,0);
        Line line1 = new Line(start, end);
        Line line2 = new Line(start, end);
        data.addDrawable(start);
        data.addDrawable(line1);
        data.addDrawable(end);
        data.addDrawable(line2);
        assertEquals(data.getLines().size(), 2);
        assertEquals(data.getLines().get(0), line1);
        assertEquals(data.getLines().get(1), line2);
    }

    /**
     * Ensures that DrawableData.getCodeBlocks() returns an
     * ArrayList of type CodeBlock containing all CodeBlocks
     * within the StateData object with the order
     * preserved.
     */
    @Test
    public void testDataGetBlocks(){
        Repository repository = DataRepository.getInstance();
        DrawableData data = (DrawableData) repository.getData();
        data.clear();
        BlockFactory blockFactory = new BlockFactory();
        CodeBlock start = blockFactory.makeBlock("Variable", 0,0);
        CodeBlock end = blockFactory.makeBlock("Variable", 0,0);
        Line line1 = new Line(start, end);
        Line line2 = new Line(start, end);
        data.addDrawable(start);
        data.addDrawable(line1);
        data.addDrawable(end);
        data.addDrawable(line2);
        assertEquals(data.getCodeBlocks().size(), 2);
        assertEquals(data.getCodeBlocks().get(0), start);
        assertEquals(data.getCodeBlocks().get(1), end);
    }

    /**
     * Ensures that DrawableData.Undo removes the
     * last added Drawable and that when a Line
     * gets undone its associated connections are removed.
     */
    @Test
    public void testDataUndo(){
        Repository repository = DataRepository.getInstance();
        DrawableData data = (DrawableData) repository.getData();
        data.clear();
        BlockFactory blockFactory = new BlockFactory();
        CodeBlock start = blockFactory.makeBlock("Variable", 0,0);
        CodeBlock end = blockFactory.makeBlock("Variable", 0,0);
        Line line = new Line(start, end);
        data.addDrawable(start);
        data.addDrawable(end);
        data.addDrawable(line);
        data.undo();
        // see if line and connections gets undone
        assertEquals(0, data.getLines().size());
        assertEquals(end.getInboundCodeBlocks().size(), 0);
        assertEquals(start.getOutboundCodeBlocks().size(), 0);
        // see if all CodeBlocks remain
        assertEquals(2, data.getDrawables().size());
        data.undo();
        // see if end is removed
        assertEquals(1, data.getDrawables().size());
        assertEquals(start, data.getDrawables().get(0));
        data.undo();
        // see if start is removed
        assertEquals(0, data.getDrawables().size());
        // ensure program doesn't crash;
        data.undo();
    }

    /**
     * Ensures that DrawableData.Undo only changes the
     * currently selected CodeBlock in the StateRepository
     * to null if the undone CodeBlock is the
     * currently selected CodeBlock.
     */
    @Test
    public void testDataUndoSelected(){
        Repository repository = DataRepository.getInstance();
        Repository stateRepository = StateRepository.getInstance();
        DrawableData data = (DrawableData) repository.getData();
        StateData state = (StateData) stateRepository.getData();
        BlockFactory blockFactory = new BlockFactory();
        CodeBlock varBlock1 = blockFactory.makeBlock("Variable", 0,0);
        CodeBlock varBlock2 = blockFactory.makeBlock("Variable", 0,0);
        data.clear();
        state.setCurrentlySelectedCodeBlock(varBlock1);
        data.addDrawable(varBlock1);
        data.addDrawable(varBlock2);
        data.undo();
        assertEquals(varBlock1, state.getCurrentlySelectedCodeBlock());
        data.undo();
        assertEquals(data.getDrawables().size(), 0);
        assertNull(state.getCurrentlySelectedCodeBlock());
    }

    /**
     * Ensures that the StateRepository has its status
     * changed to "Action undone" after calling DrawableData.undo().
     */
    @Test
    public void testDataUndoStatus(){
        Repository repository = DataRepository.getInstance();
        Repository stateRepository = StateRepository.getInstance();
        DrawableData data = (DrawableData) repository.getData();
        StateData state = (StateData) stateRepository.getData();

        data.clear();
        data.addDrawable(new Circle(0,0,0,Color.BLACK));
        data.undo();
        assertEquals("Action undone",state.getStatus());
    }

    /**
     * Ensures that the StateRepository has its status
     * changed to "Board cleared" after calling DrawableData.clear().
     */
    @Test
    public void testDataClearStatus(){
        Repository repository = DataRepository.getInstance();
        Repository stateRepository = StateRepository.getInstance();
        DrawableData data = (DrawableData) repository.getData();
        StateData state = (StateData) stateRepository.getData();

        data.clear();
        data.addDrawable(new Circle(0,0,0,Color.BLACK));
        data.clear();
        assertEquals("Board cleared", state.getStatus());
    }

    /**
     * Ensures that the DrawableData addAll method
     * adds all Drawables within a given collection.
     */
    @Test
    public void testDataAddAll(){
        Repository repository = DataRepository.getInstance();
        DrawableData data = (DrawableData) repository.getData();
        data.clear();
        BlockFactory blockFactory = new BlockFactory();
        CodeBlock start = blockFactory.makeBlock("Variable", 0,0);
        CodeBlock end = blockFactory.makeBlock("Variable", 0,0);
        Line line = new Line(start, end);

        ArrayList<Drawable> localDrawables = new ArrayList<>();
        localDrawables.add(start);
        localDrawables.add(line);
        localDrawables.add(end);
        data.addAll(localDrawables);
        ArrayList<Drawable> drawables = data.getDrawables();
        assertEquals(localDrawables.size(), drawables.size());
        for(int i = 0; i < localDrawables.size(); ++i){
            assertEquals(localDrawables.get(i), drawables.get(i));
        }
    }

    /**
     * Ensures that the DrawableData addAll method
     * behaves as expected when given null references.
     */
    @Test
    public void testAddAllNull(){
        Repository repository = DataRepository.getInstance();
        DrawableData data = (DrawableData) repository.getData();
        data.clear();
        data.addAll(null);
        assertEquals(0, data.getDrawables().size());

        ArrayList<Drawable> drawables = new ArrayList<>();
        drawables.add(null);
        data.addAll(drawables);
        assertEquals(0, data.getDrawables().size());
    }

    /**
     * DrawableData.modifiedDrawables just notifies observers,
     * no testing can be done here. This is just included for
     * 100% coverage.
     */
    @Test
    public void testModified(){
        Repository repository = DataRepository.getInstance();
        DrawableData data = (DrawableData) repository.getData();
        data.clear();
        data.modifiedDrawables();
    }

}
