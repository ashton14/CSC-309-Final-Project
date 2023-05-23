package src.test;

import src.main.*;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test cases for DataRepository and
 * DrawableData classes. 100% statement coverage.
 */
public class DataRepositoryTest {

    public CodeBlock makeTestBlock(){
        BlockFactory blockFactory = new BlockFactory();
        return blockFactory.makeBlock("Variable", 0,0);
    }

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
     * Ensures the Drawables from the DataRepository contains an
     * ArrayList of type Drawable that is initially empty.
     */
    @Test
    public void testDataInitial(){
        DataRepository repository = (DataRepository) DataRepository.getInstance();
        assertEquals(repository.getDrawables().size(), 0);
    }


    /**
     * Ensures that DataRepository can add one drawable object.
     */
    @Test
    public void testDataAddOne(){
        DataRepository repository = (DataRepository) DataRepository.getInstance();
        repository.clear();
        BlockFactory blockFactory = new BlockFactory();
        CodeBlock varBlock = blockFactory.makeBlock("Variable",0,0);
        repository.addDrawable(varBlock);
        assertEquals(repository.getDrawables().size(), 1);
        assertEquals(repository.getDrawables().get(0), varBlock);
    }

    /**
     * Ensures that DataRepository can add three drawable objects
     * and that the order is preserved.
     */
    @Test
    public void testDataAddThree(){
        DataRepository repository = (DataRepository) DataRepository.getInstance();
        BlockFactory blockFactory = new BlockFactory();
        CodeBlock start = blockFactory.makeBlock("Variable", 0,0);
        CodeBlock end = blockFactory.makeBlock("Variable", 0,0);
        Line line = new Line(start, end);
        repository.addDrawable(start);
        repository.addDrawable(end);
        repository.addDrawable(line);
        assertEquals(repository.getDrawables().size(), 3);
        assertEquals(repository.getDrawables().get(0), start);
        assertEquals(repository.getDrawables().get(1), end);
        assertEquals(repository.getDrawables().get(2), line);
    }

    /**
     * Ensures that DataRepository does not support the
     * addition of null references.
     */
    @Test
    public void testDataAddNull(){
        DataRepository repository = (DataRepository) DataRepository.getInstance();
        repository.clear();
        repository.addDrawable(null);
        assertEquals(0, repository.getDrawables().size());
    }

    /**
     * Ensures that DataRepository.getLines() returns an
     * ArrayList of type Line containing all lines
     * within the StateData object with the order
     * preserved.
     */
    @Test
    public void testDataGetLines(){
        DataRepository repository = (DataRepository) DataRepository.getInstance();
        repository.clear();
        BlockFactory blockFactory = new BlockFactory();
        CodeBlock start = blockFactory.makeBlock("Variable", 0,0);
        CodeBlock end = blockFactory.makeBlock("Variable", 0,0);
        Line line1 = new Line(start, end);
        Line line2 = new Line(start, end);
        repository.addDrawable(start);
        repository.addDrawable(line1);
        repository.addDrawable(end);
        repository.addDrawable(line2);
        assertEquals(repository.getLines().size(), 2);
        assertEquals(repository.getLines().get(0), line1);
        assertEquals(repository.getLines().get(1), line2);
    }

    /**
     * Ensures that DataRepository.getCodeBlocks() returns an
     * ArrayList of type CodeBlock containing all CodeBlocks
     * within the StateData object with the order
     * preserved.
     */
    @Test
    public void testDataGetBlocks(){
        DataRepository repository = (DataRepository) DataRepository.getInstance();
        repository.clear();
        BlockFactory blockFactory = new BlockFactory();
        CodeBlock start = blockFactory.makeBlock("Variable", 0,0);
        CodeBlock end = blockFactory.makeBlock("Variable", 0,0);
        Line line1 = new Line(start, end);
        Line line2 = new Line(start, end);
        repository.addDrawable(start);
        repository.addDrawable(line1);
        repository.addDrawable(end);
        repository.addDrawable(line2);
        assertEquals(repository.getCodeBlocks().size(), 2);
        assertEquals(repository.getCodeBlocks().get(0), start);
        assertEquals(repository.getCodeBlocks().get(1), end);
    }

    /**
     * Ensures that DataRepository.undo() removes the
     * last added Drawable and that when a Line
     * gets undone its associated connections are removed.
     */
    @Test
    public void testDataUndo(){
        DataRepository repository = (DataRepository) DataRepository.getInstance();
        repository.clear();
        BlockFactory blockFactory = new BlockFactory();
        CodeBlock start = blockFactory.makeBlock("Variable", 0,0);
        CodeBlock end = blockFactory.makeBlock("Variable", 0,0);
        Line line = new Line(start, end);
        repository.addDrawable(start);
        repository.addDrawable(end);
        repository.addDrawable(line);
        repository.undo();
        // see if line and connections gets undone
        assertEquals(0, repository.getLines().size());
        assertEquals(end.getInboundCodeBlocks().size(), 0);
        assertEquals(start.getOutboundCodeBlocks().size(), 0);
        // see if all CodeBlocks remain
        assertEquals(2, repository.getDrawables().size());
        repository.undo();
        // see if end is removed
        assertEquals(1, repository.getDrawables().size());
        assertEquals(start, repository.getDrawables().get(0));
        repository.undo();
        // see if start is removed
        assertEquals(0, repository.getDrawables().size());
        // ensure program doesn't crash;
        repository.undo();
    }

    /**
     * Ensures that DataRepository.undo() only changes the
     * currently selected CodeBlock in the StateRepository
     * to null if the undone CodeBlock is the
     * currently selected CodeBlock.
     */
    @Test
    public void testDataUndoSelected(){
        DataRepository repository = (DataRepository) DataRepository.getInstance();
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();

        BlockFactory blockFactory = new BlockFactory();
        CodeBlock varBlock1 = blockFactory.makeBlock("Variable", 0,0);
        CodeBlock varBlock2 = blockFactory.makeBlock("Variable", 0,0);
        repository.clear();
        stateRepository.setCurrentlySelectedDrawable(varBlock1);
        repository.addDrawable(varBlock1);
        repository.addDrawable(varBlock2);
        repository.undo();
        assertEquals(varBlock1, stateRepository.getCurrentlySelectedCodeBlock());
        repository.undo();
        assertEquals(repository.getDrawables().size(), 0);
        assertNull(stateRepository.getCurrentlySelectedCodeBlock());
    }

    /**
     * Ensures that the StateRepository has its status
     * changed to "Action undone" after calling DataRepository.undo().
     */
    @Test
    public void testDataUndoStatus(){
        DataRepository repository = (DataRepository) DataRepository.getInstance();
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();

        repository.clear();
        repository.addDrawable(new Circle(0,0,0,Color.BLACK));
        repository.undo();
        assertEquals("Action undone", stateRepository.getStatus());
    }

    /**
     * Ensures that the StateRepository has its status
     * changed to "Board cleared" after calling DataRepository.clear().
     */
    @Test
    public void testDataClearStatus(){
        DataRepository repository = (DataRepository) DataRepository.getInstance();
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();

        repository.clear();
        repository.addDrawable(new Circle(0,0,0,Color.BLACK));
        repository.clear();
        assertEquals("Board cleared", stateRepository.getStatus());
    }

    /**
     * Ensures that the DataRepository addAll method
     * adds all Drawables within a given collection.
     */
    @Test
    public void testDataAddAll(){
        DataRepository repository = (DataRepository) DataRepository.getInstance();

        repository.clear();
        BlockFactory blockFactory = new BlockFactory();
        CodeBlock start = blockFactory.makeBlock("Variable", 0,0);
        CodeBlock end = blockFactory.makeBlock("Variable", 0,0);
        Line line = new Line(start, end);

        ArrayList<Drawable> localDrawables = new ArrayList<>();
        localDrawables.add(start);
        localDrawables.add(line);
        localDrawables.add(end);
        repository.addAll(localDrawables);
        ArrayList<Drawable> drawables = repository.getDrawables();
        assertEquals(localDrawables.size(), drawables.size());
        for(int i = 0; i < localDrawables.size(); ++i){
            assertEquals(localDrawables.get(i), drawables.get(i));
        }
    }

    /**
     * Ensures that the DataRepository addAll method
     * behaves as expected when given null references.
     */
    @Test
    public void testAddAllNull(){
        DataRepository repository = (DataRepository) DataRepository.getInstance();

        repository.clear();
        repository.addAll(null);
        assertEquals(0, repository.getDrawables().size());

        ArrayList<Drawable> drawables = new ArrayList<>();
        drawables.add(null);
        repository.addAll(drawables);
        assertEquals(0, repository.getDrawables().size());
    }

    /**
     * DataRepository.modifiedDrawables just notifies observers,
     * no testing can be done here. This is just included for
     * 100% coverage.
     */
    @Test
    public void testModified(){
        DataRepository repository = (DataRepository) DataRepository.getInstance();
        repository.clear();
        repository.modifiedDrawables();
    }

    /**
     * Tests the removeDrawable method in the DataRepository
     * to ensure that only selected elements get removed.
     */
    @Test
    public void remove(){
        DataRepository repository = (DataRepository) DataRepository.getInstance();
        ArrayList<Drawable> drawables = new ArrayList<>();
        for(int i = 0; i < 10; ++i){
            drawables.add(makeTestBlock());
            repository.addDrawable(drawables.get(i));
        }
        repository.removeDrawable(drawables.get(0));
        repository.removeDrawable(drawables.get(5));
        repository.removeDrawable(drawables.get(9));
        for (int i = 0; i < 10; i++) {
            boolean result = repository.getDrawables().contains(drawables.get(i));
            if(i == 0 || i == 5 || i == 9){
                assertFalse(result);
            } else {
                assertTrue(result);
            }
        }
    }

    /**
     * Tests the removeDrawable method in the DataRepository
     * to ensure that passing null does not crash the program.
     */
    @Test
    public void removeNull(){
        DataRepository repository = (DataRepository) DataRepository.getInstance();
        repository.removeDrawable(null);
    }

    /**
     * Tests the removeDrawable method in the DataRepository
     * to ensure that passing a Drawable that is not in the
     * DataRepository does not crash the program.
     */
    @Test
    public void removeNotIn(){
        DataRepository repository = (DataRepository) DataRepository.getInstance();
        CodeBlock codeBlock = makeTestBlock();
        repository.removeDrawable(codeBlock);
    }



}