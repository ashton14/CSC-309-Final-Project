package src.test;

import org.junit.Test;
import org.mockito.Mockito;
import src.main.StateRepository;
import src.main.*;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static src.test.DrawablesMock.mockFlowchartWithDrawables;

public class DataStateRepositoryIntegrationTest {

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

        ArrayList<Drawable> mockFlowchart = mockFlowchartWithDrawables();
        CodeBlock start = (CodeBlock) mockFlowchart.get(0);
        CodeBlock end = (CodeBlock) mockFlowchart.get(1);

        stateRepository.setCurrentlySelectedDrawable(start);
        repository.addDrawable(start);
        repository.addDrawable(end);
        repository.undo();
        assertEquals(start, stateRepository.getCurrentlySelectedCodeBlock());
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
        repository.clearFlowchart();

        Shape mockShape = Mockito.mock(Shape.class);
        repository.addDrawable(mockShape);
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

        repository.clearFlowchart();
        Shape mockShape = Mockito.mock(Shape.class);
        repository.addDrawable(mockShape);
        repository.clearFlowchart();
        assertEquals("Board cleared", stateRepository.getStatus());
    }

    /**
     * Ensures that DataRepository.deleteSelectedItem() removes
     * only the selected item regardless of position and
     * does not crash when no item is selected.
     *
     */
    @Test
    public void deleteSelectedItem() {
        DataRepository dataRepository = (DataRepository) DataRepository.getInstance();
        dataRepository.clearFlowchart();
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
        stateRepository.reset();

        ArrayList<Drawable> mockFlowchart = mockFlowchartWithDrawables();
        CodeBlock codeBlock = (CodeBlock) mockFlowchart.get(0);
        CodeBlock codeBlock2 = (CodeBlock) mockFlowchart.get(1);
        Line line = (Line) mockFlowchart.get(2);

        dataRepository.addDrawable(codeBlock);
        dataRepository.addDrawable(codeBlock2);
        dataRepository.addDrawable(line);

        // remove middle CodeBlock, should also remove line
        stateRepository.setCurrentlySelectedDrawable(codeBlock2);
        stateRepository.deleteSelectedItem();

        assertFalse(dataRepository.getDrawables().contains(codeBlock2));
        assertFalse(dataRepository.getDrawables().contains(line));
        assertTrue(dataRepository.getDrawables().contains(codeBlock));
        assertNull(stateRepository.getCurrentlySelectedDrawable());

        //remove left last CodeBlock
        stateRepository.setCurrentlySelectedDrawable(codeBlock);
        stateRepository.deleteSelectedItem();
        assertFalse(dataRepository.getDrawables().contains(codeBlock));
        assertFalse(dataRepository.getDrawables().contains(line));
        assertNull(stateRepository.getCurrentlySelectedDrawable());

        //remove none (avoid crash)
        stateRepository.deleteSelectedItem();
    }
}
