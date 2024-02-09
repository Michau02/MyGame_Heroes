package tile;

public class Node {

    public Node parent;
    public int col, row, gCost, hCost, fCost;
    public boolean start, goal, solid, open, checked;

    public Node(int col, int row, boolean solid){
        this.col = col;
        this.row = row;
        this.solid = solid;
    }
    public void setAsStart(){
        start = true;
    }
    public void setAsGoal(){
        goal = true;
    }
    public void setAsOpen(){
        open = true;
    }
    public void setAsChecked(){
        checked = true;
    }
}
