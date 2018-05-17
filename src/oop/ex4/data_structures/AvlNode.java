package oop.ex4.data_structures;

/*
* this class represent a node which hold a node for the avl tree.
 */
class AvlNode {
    /*
    * the value of the node
     */
    int value;
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
    * the height of the node from the fares desendence.
     */
    private int height=0;
    /*
    * number of decendens of the node.
     */
    private int numberOfDecendens=0;

    AvlNode(){

    }
    AvlNode(AvlNode parent,int value){
        this.value=value;
        this.parent=parent;
        parent.addChild();
    }

    /*
    * add child to the currnt node count and all of his fathers.
     */
    void addChild(){
        numberOfDecendens++;
        if (parent!=null){
            parent.addChild();
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
    * get the current height of the node.
     */
    int getHeight() {
        return height;
    }

    int getNumberOfDecendens ()
    {
        return numberOfDecendens;
    }

    int getValue ()
    {return value;}

}
