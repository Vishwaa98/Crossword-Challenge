package com.example.crosswordchallenge;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    boolean[][] selectedChars;
    String selectedWord = "";
    int totalCols;
    int colSize;
    int rowSize;
    int horizontalSpacing;
    int verticalSpacing;
    int leftPadding;
    int topPadding;
    char[][] chars;
    int[] firstIndex = {-1, -1};
    boolean[][] foundChars;
    Direction direction = null;
    GridView wordsGridView;
    public final int WIDTH = 10;
    public final int HEIGHT = 10;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView)findViewById(R.id.gridview);
        wordsGridView = (GridView) findViewById(R.id.gridview2);

        Grid grid = new Grid(HEIGHT, WIDTH);
        GridAdapter gridAdapter = new GridAdapter(this, grid.getListGrid());
        final WordsAdapter wordsAdapter = new WordsAdapter(this, Words.words);

        gridView.setAdapter(gridAdapter);
        wordsGridView.setAdapter(wordsAdapter);

        selectedChars = new boolean[WIDTH][HEIGHT];
        foundChars = new boolean[WIDTH][HEIGHT];
        chars = grid.getGrid();

        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                totalCols = gridView.getNumColumns();
                horizontalSpacing = gridView.getHorizontalSpacing();
                verticalSpacing = gridView.getVerticalSpacing();
                colSize = gridView.getColumnWidth();
                rowSize = gridView.getLayoutParams().height/totalCols - verticalSpacing;
                leftPadding = gridView.getPaddingLeft();
                topPadding = gridView.getPaddingTop();

                /*System.out.println("Vishwaa  height " + gridView.getLayoutParams().height);
                System.out.println("Vishwaa cols " + totalCols);
                System.out.println("Vishwaa colSize " + colSize);
                System.out.println("Vishwaa horizontal spacing " + gridView.getHorizontalSpacing());
                System.out.println("Vishwaa vertical spacing " + gridView.getVerticalSpacing());
                System.out.println("Vishwaa  rowsize " + rowSize);*/

                int actionMasked = event.getActionMasked();
                int[] cellIndex = getGridIndex(event);
                if (cellIndex[0] == -1 || cellIndex[1] == -1){
                    return true;
                }
                //gridView.getChildAt(convertArrayToListIndex(cellIndex)).setBackgroundColor(Color.parseColor("#18A608"));
                System.out.println(chars[cellIndex[0]][cellIndex[1]]);

                switch(actionMasked){
                    case MotionEvent.ACTION_DOWN:
                        firstIndex = cellIndex;
                        addCharToList(cellIndex);
                        break;

                    case MotionEvent.ACTION_UP:
                        firstIndex = new int[] {-1, -1};
                        direction = null;
                        System.out.println("Vishwaa selected word    " + selectedWord);
                        if (Words.checkIfValidWord(selectedWord)){
                            Words.addWordToFoundList(selectedWord);
                            setFoundChars();
                            wordsAdapter.notifyDataSetChanged();
                        } else {
                            removeHighlightedCells();
                        }
                        selectedWord = "";
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (!selectedChars[cellIndex[0]][cellIndex[1]] && firstIndex != cellIndex) {

                            if (direction == null) {
                                System.out.println("Vishwaa set direction  " + chars[cellIndex[0]][cellIndex[1]]);
                                setDirection(firstIndex, cellIndex);
                                addCharToList(cellIndex);
                            } else {
                                //System.out.println("VISHWAA DIRECTION   " + direction);
                                if (direction == getDirection(firstIndex, cellIndex)) {
                                    addCharToList(cellIndex);
                                } else {
                                    removeHighlightedCells();
                                    System.out.println("Vishwaa wrong direction");
                                }
                            }
                            firstIndex = cellIndex;
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void removeHighlightedCells(){
        for (int i=0; i<selectedChars.length; i++){
            for (int j=0; j<selectedChars[0].length; j++){
                if (selectedChars[i][j]){
                    selectedChars[i][j] = false;
                    if (!foundChars[i][j]){
                        gridView.getChildAt(i*totalCols+j).setBackgroundResource(0);
                    }

                }
            }
        }
    }

    public void setFoundChars(){
        for (int i=0; i<foundChars.length; i++){
            for (int j=0; j<foundChars[0].length; j++){
                if (selectedChars[i][j]){
                    selectedChars[i][j] = false;
                    foundChars[i][j] = true;
                }
            }
        }
    }

    public void setDirection(int[] firstIndex, int[] secondIndex){
        this.direction = getDirection(firstIndex, secondIndex);
    }


    public Direction getDirection(int[] firstIndex, int[] secondIndex){
        if (firstIndex[0] - secondIndex[0] == 0){
            if (firstIndex[1] < secondIndex[1]){
                return Direction.RIGHT;
            } else {
                return Direction.LEFT;
            }
        } else if (firstIndex[1] - secondIndex[1] == 0){
            if (firstIndex[0] < secondIndex[0]){
                return Direction.DOWN;
            } else {
                return Direction.UP;
            }
        } else {
            if (firstIndex[0] < secondIndex[0] && firstIndex[1] < secondIndex[1]){
                return Direction.DOWN_RIGHT_DIAGONAL;
            } else if (firstIndex[0] < secondIndex[0] && firstIndex[1] > secondIndex[1]) {
                return Direction.DOWN_LEFT_DIAGONAL;
            } else if (firstIndex[0] > secondIndex[0] && firstIndex[1] > secondIndex[1]) {
                return Direction.UP_LEFT_DIAGONAL;
            } else if (firstIndex[0] > secondIndex[0] && firstIndex[1] < secondIndex[1]) {
                return Direction.UP_RIGHT_DIAGONAL;
            }
        }
        // default value
        return null;
    }

    public int[] getGridIndex(MotionEvent event){
        int row = ((int)event.getY()-topPadding)/(rowSize+verticalSpacing);
        int col = ((int)event.getX()-leftPadding)/(colSize+horizontalSpacing);
        int colRem = ((int)event.getX()-leftPadding)%(colSize+horizontalSpacing);
        int rowRem = ((int)event.getY()-topPadding)%(rowSize+verticalSpacing);

        if (colRem > colSize){
            return new int[] {-1, -1};
        }

        if (rowRem > rowSize){
            return new int[] {-1, -1};
        }

        if (row >= 10 || col >= 10) {
            return new int[] {-1, -1};
        }

        return new int[] {row, col};
    }


    public void addCharToList(int[] index){
        if (!selectedChars[index[0]][index[1]]){
            selectedChars[index[0]][index[1]] = true;
            selectedWord = selectedWord + chars[index[0]][index[1]];
            selectedChars[index[0]][index[1]] = true;
            gridView.getChildAt(convertArrayToListIndex(index)).setBackgroundResource(R.drawable.select_shape);
        }
    }

    public int convertArrayToListIndex(int[] index){
        return index[0]*totalCols + index[1];

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // restart the activity
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
