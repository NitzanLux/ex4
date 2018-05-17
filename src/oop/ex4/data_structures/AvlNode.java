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
    * the depth of the node from the fares desendence.
     */
    private int depth =0;
    /*
    * number of decendens of the node.
     */
    private int numberOfDecendens=0;

    AvlNode(){

    }
    /*
     * root constractor.
     */
    AvlNode(int value){
        this.value=value;
    }
    AvlNode(AvlNode parent,int value){
        this.value=value;
        this.parent=parent;
        parent.addChildCount();
        depth=parent.depth+1;
    }

    /*
    * add child to the currant node count and all of his fathers.
     */
    private void addChildCount(){
        numberOfDecendens++;
        if (parent!=null){
            parent.addChildCount();
        }
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
    /*
    * get the current depth of the node.
     */
    int getDepth() {
        return depth;
    }

    int getNumberOfDecendens ()
    {
        return numberOfDecendens;
    }

    int getValue ()
    {return value;}

    void setRightChild(AvlNode rightChild) {
        this.rightChild = rightChild;
    }

    void setLeftChild(AvlNode leftChild) {
        this.leftChild = leftChild;
    }
}
