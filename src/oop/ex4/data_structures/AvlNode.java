package oop.ex4.data_structures;

/*
* this class represent a node which hold a node for the avl tree.
 */
class AvlNode {
    /*
    * the value of the node
     */
    private int value;
    /*
    * the left child of the node .
     */
    private AvlNode leftChild;
    /*
   * the right child of the node .
    */
    private AvlNode rightChild;
    /*
    * the parent of the node.
     */
    private AvlNode parent;

    /*
    *TODO documentation
     */
    private int biggestDistanceToLeaf=0;
    AvlNode(){

    }
    /*
     * root constructor.
     */
    AvlNode(int value){
        this.value=value;
    }
    //TODO expleine its a leaf
    AvlNode(AvlNode parent,int value){
        this.value=value;
        this.parent=parent;
        updateAncestorsDistanceToLeaf();
    }
    void updateAncestorsDistanceToLeaf(){
        if (parent==null){ // if its the root
            return;
        }
        int currentDistance=biggestDistanceToLeaf;
        setDistance();
        if (currentDistance==biggestDistanceToLeaf){
            return;
        }
        parent.updateAncestorsDistanceToLeaf();
    }
    void setDistance(){
        int biggestDistanseLeft=-1;
        int biggestDistanseRight=-1;
        if (rightChild!=null) {
            biggestDistanseRight=rightChild.biggestDistanceToLeaf;
        }
        if (leftChild!=null){
            biggestDistanseLeft=leftChild.biggestDistanceToLeaf;
        }
        if (biggestDistanseLeft>biggestDistanseRight){
            biggestDistanceToLeaf=biggestDistanseLeft+1;
        }else {
            biggestDistanceToLeaf=biggestDistanseRight+1;
        }
    }

    int getBiggestDistanceToLeaf() {
        return biggestDistanceToLeaf;
    }

    /*
        * get the left child of the current node.
         */
    AvlNode getLeftChild() {
        return leftChild;
    }

    /*
    * get the right child of the current node.
     */
    AvlNode getRightChild() {
        return rightChild;
    }

    /*
    *   get the parent of the node
     */
    AvlNode getParent() {

        return parent;
    }

    int getValue () {
        return value;
    }

    void setRightChild(AvlNode rightChild) {
        this.rightChild = rightChild;
    }

    void setLeftChild(AvlNode leftChild) {
        this.leftChild = leftChild;
    }
    AvlNode getChild(){//TODO genric child
        if (leftChild!=null){
            return leftChild;
        }else {
            return rightChild;
        }
    }
    boolean setChild(AvlNode childNode){
        if (childNode!=null&& childNode.getValue()==value){
            if (childNode.getValue()>value){
                rightChild=childNode;
            }
            if (childNode.getValue()<value){
                leftChild=childNode;
            }
            childNode.setParent(this);
            return true;
        }
        return false;
    }
    boolean removeChild(AvlNode childNode){
        if (rightChild==childNode){
            rightChild=null;
        }else if (leftChild==childNode){
            leftChild=null;
        }else {
            return false;
        }
        return true;
    }

    void setParent(AvlNode parent) {
        this.parent = parent;
    }
}
