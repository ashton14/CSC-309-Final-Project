package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

/**
 * A class to store data of type Drawable, which is to be observed
 * by observers.
 */
public class DrawableData extends Observable {
    private ArrayList<Drawable> drawables;

    /**
     * Constructor for objects of type DrawableData
     */
    public DrawableData(){
        drawables = new ArrayList<>();
    }

    /**
     * Gets a shallow copy of the list of drawables.
     * @return An ArrayList of type Drawable.
     */
    public ArrayList<Drawable> getDrawables() {
        return new ArrayList<>(drawables);
    }

    /**
     * Adds a drawable to the list of drawables and
     * notifies observers of this change.
     * @param drawable   The drawable to add to the
     *                   list of drawables.
     */
    public void addDrawable(Drawable drawable){
        if(drawable == null){
            setChanged();
            notifyObservers();
            return;
        }
        drawables.add(drawable);
        setChanged();
        notifyObservers();
    }


    /**
     * Creates a list of type Line using the list of type Drawable.
     * @return The Lines within this Drawable Data as an ArrayList of type Line.
     */
    public ArrayList<Line> getLines() {
        ArrayList<Line> lines = new ArrayList<>();
        for (Drawable drawable : drawables) {
            if (drawable.getClass() == Line.class) {
                lines.add((Line) drawable);
            }
        }
        return lines;
    }

    /**
     * Creates a list of type CodeBlock using the list of type Drawable.
     * @return The CodeBlocks within this Drawable Data as an ArrayList
     * of type Drawable.
     */
    public ArrayList<CodeBlock> getCodeBlocks() {
        ArrayList<CodeBlock> codeBlocks = new ArrayList<>();
        for (int i = 0; i < drawables.size(); ++i) {
            if (drawables.get(i) instanceof CodeBlock) {
                codeBlocks.add((CodeBlock) drawables.get(i));
            }
        }
        return codeBlocks;
    }

    /**
     * Undo last drawn CodeBlock or Line and notify observers.
     */
    public void undo() {
        Drawable lastDrawable = drawables.get(drawables.size()-1);
        if(lastDrawable.getClass() == Line.class){
            Line line = (Line) lastDrawable;
            line.getStart().removeConnection(line.getEnd());
        } else if (lastDrawable instanceof CodeBlock) {
            CodeBlock codeBlock = (CodeBlock) lastDrawable;
            codeBlock.removeAllConnections();
        }
        drawables.remove(lastDrawable);
        setChanged();
        notifyObservers();
        Drawable selected = ((StateData)(StateRepository.getInstance()
                .getData())).getCurrentlySelectedCodeBlock();
        if(selected == lastDrawable){
            ((StateData)(StateRepository.getInstance()
                    .getData())).setCurrentlySelectedCodeBlock(null);
        }
        ((StateData)(StateRepository.getInstance().getData())).setStatus("Action undone");
    }

    /**
     * Clear the List of type Drawable and notifies observers
     */
    public void clear(){
        if(!drawables.isEmpty())
            drawables.clear();

        setChanged();
        notifyObservers();
        ((StateData)(StateRepository.getInstance().getData())).setStatus("Board cleared");
    }


    /**
     * Adds all drawables from a Collection of type Drawable
     * into the internal ArrayList of type Drawable.
     * @param drawables   A Collection of type Drawable to
     *                    add to the internal ArrayList of
     *                    type Drawable.
     */
    public void addAll(Collection<Drawable> drawables){
        if(drawables == null){
            return;
        }
        for (Drawable drawable : drawables) {
            if (drawable != null)
                this.drawables.add(drawable);
        }

        setChanged();
        notifyObservers();
    }

    /**
     * Notifies observers that the list of type Drawable
     * has been changed.
     */
    public void modifiedDrawables(){
        setChanged();
        notifyObservers();
    }
}
