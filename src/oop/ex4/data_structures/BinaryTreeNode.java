package oop.ex4.data_structures;

/*
* this class represent a node which hold a node for the avl tree.
 */
class BinaryTreeNode {
    /*
    * the value of the node
     */
    private int value;
    /*
    * the left child of the node .
     */
    BinaryTreeNode leftChild;
    /*
   * the right child of the node .
    */
    BinaryTreeNode rightChild;
    /*
    * the parent of the node.
     */
    BinaryTreeNode parent;

    /*
    *TODO documentation
     */
    private int biggestDistanceToLeaf=0;
    BinaryTreeNode(){

    }
    /*
     * root constructor.
     */
    BinaryTreeNode(int value){
        this.value=value;
    }
    //TODO expleine its a leaf
    BinaryTreeNode(BinaryTreeNode parent, int value){
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
    BinaryTreeNode getLeftChild() {
        return leftChild;
    }

    /*
    * get the right child of the current node.
     */
    BinaryTreeNode getRightChild() {
        return rightChild;
    }

    /*
    *   get the parent of the node
     */
    BinaryTreeNode getParent() {

        return parent;
    }

    int getValue () {
        return value;
    }

    void setRightChild(BinaryTreeNode rightChild) {
        this.rightChild = rightChild;
    }

    void setLeftChild(BinaryTreeNode leftChild) {
        this.leftChild = leftChild;
    }
    BinaryTreeNode getChild(){//TODO genric child
        if (leftChild!=null){
            return leftChild;
        }else {
            return rightChild;
        }
    }
    boolean setChild(BinaryTreeNode childNode){
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
    boolean removeChild(BinaryTreeNode childNode){
        if (rightChild==childNode){
            rightChild=null;
        }else if (leftChild==childNode){
            leftChild=null;
        }else {
            return false;
        }
        return true;
    }

    void setParent(BinaryTreeNode parent) {
        this.parent = parent;
    }
}
