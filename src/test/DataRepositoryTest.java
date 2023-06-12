package src.test;

import org.mockito.Mockito;
import src.main.*;
import org.junit.Test;
import src.main.Shape;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static src.test.DrawablesMock.*;

/**
 * Test cases for DataRepository class.
 * 65% statement coverage as of 6/11/2023
 * 93% statement coverage when run with DataStateRepositoryIntegrationTest
 * All external CodeBlock and Line Dependencies are mocked.
 */
public class DataRepositoryTest {

    /**
     * Creates a simple mock of a CodeBlock
     * that does not support any operations.
     * @return  A simple CodeBlock mock.
     */
    public CodeBlock mockTestBlock(){
        CodeBlock mockBlock = Mockito.mock(CodeBlock.class);
        return mockBlock;
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
        repository.clearFlowchart();
        assertEquals(repository.getDrawables().size(), 0);
    }


    /**
     * Ensures that DataRepository can add one drawable object.
     */
    @Test
    public void testDataAddOne(){
        DataRepository repository = (DataRepository) DataRepository.getInstance();
        repository.clearFlowchart();

        Shape mockRectangle = Mockito.mock(Shape.class);
        CodeBlock  varBlock = new VariableBlock(mockRectangle,"");
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

        ArrayList<Drawable> mockFlowchart = mockFlowchartWithDrawables();
        CodeBlock start = (CodeBlock) mockFlowchart.get(0);
        CodeBlock end = (CodeBlock) mockFlowchart.get(1);
        Line line = (Line) mockFlowchart.get(2);

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
        repository.clearFlowchart();
        repository.addDrawable(null);
        assertEquals(0, repository.getDrawables().size());
    }

    /**
     * Ensures that DataRepository.getLines() returns an
     * ArrayList of type Line containing all Drawables
     * that are Line instances.
     */
    @Test
    public void testDataGetLines(){
        DataRepository repository = (DataRepository) DataRepository.getInstance();
        repository.clearFlowchart();

        ArrayList<Drawable> mockFlowchart = mockFlowchartWithDrawables();
        CodeBlock start = (CodeBlock) mockFlowchart.get(0);
        CodeBlock end = (CodeBlock) mockFlowchart.get(1);
        Line line = (Line) mockFlowchart.get(2);

        repository.addDrawable(start);
        repository.addDrawable(line);
        repository.addDrawable(end);

        assertEquals(repository.getLines().size(), 1);
        assertEquals(repository.getLines().get(0), line);
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
        repository.clearFlowchart();

        Shape mockShape = Mockito.mock(Shape.class);
        CodeBlock var1 = new VariableBlock(mockShape,"");
        CodeBlock var2 = new VariableBlock(mockShape,"");

        Line mockLine1 = Mockito.mock(Line.class);
        when(mockLine1.getStart()).thenReturn(var1);
        when(mockLine1.getEnd()).thenReturn(var2);

        Line mockLine2 = Mockito.mock(Line.class);
        when(mockLine2.getStart()).thenReturn(var2);
        when(mockLine2.getEnd()).thenReturn(var1);

        repository.addDrawable(var1);
        repository.addDrawable(mockLine1);
        repository.addDrawable(var2);
        repository.addDrawable(mockLine2);

        assertEquals(repository.getCodeBlocks().size(), 2);
        assertEquals(repository.getCodeBlocks().get(0), var1);
        assertEquals(repository.getCodeBlocks().get(1), var2);
    }

    /**
     * Ensures that DataRepository.undo() removes the
     * last added Drawable and that when a Line
     * gets undone its associated connections are removed.
     */
    @Test
    public void testDataUndo(){
        DataRepository repository = (DataRepository) DataRepository.getInstance();
        repository.clearFlowchart();

        ArrayList<Drawable> mockFlowchart = mockFlowchartWithDrawables();
        CodeBlock start = (CodeBlock) mockFlowchart.get(0);
        CodeBlock end = (CodeBlock) mockFlowchart.get(1);
        Line line = (Line) mockFlowchart.get(2);

        repository.addDrawable(start);
        repository.addDrawable(end);
        repository.addDrawable(line);
        repository.undo();

        // see if line and connections gets undone
        assertEquals(0, repository.getLines().size());
        assertEquals(start.getInboundCodeBlocks().size(), 0);
        assertEquals(start.getOutboundCodeBlocks().size(), 0);
        assertEquals(end.getOutboundCodeBlocks().size(), 0);
        assertEquals(end.getInboundCodeBlocks().size(), 0);
    }

    /**
     * Ensures that the DataRepository addAll method
     * adds all Drawables within a given collection.
     */
    @Test
    public void testDataAddAll(){
        DataRepository repository = (DataRepository) DataRepository.getInstance();
        repository.clearFlowchart();

        ArrayList<Drawable> localDrawables = mockFlowchartWithDrawables();
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

        repository.clearFlowchart();
        repository.addAll(null);
        assertEquals(0, repository.getDrawables().size());

        ArrayList<Drawable> drawables = new ArrayList<>();
        drawables.add(null);
        repository.addAll(drawables);
        assertEquals(0, repository.getDrawables().size());
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
            drawables.add(mockTestBlock());
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
        CodeBlock codeBlock = mockTestBlock();
        repository.removeDrawable(codeBlock);
    }



}