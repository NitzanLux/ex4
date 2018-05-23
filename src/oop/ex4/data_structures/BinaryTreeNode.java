package oop.ex4.data_structures;

/*
 * this class represent a node which hold a node for the avl tree.
 * @authors nlux, itamar108
 */


class BinaryTreeNode {
    /*
    * the value of the node
     */
    private int value;
    /*
    * the left child of the node .
     */
    private BinaryTreeNode leftChild;
    /*
   * the right child of the node .
    */
    private BinaryTreeNode rightChild;
    /*
    * the parent of the node.
     */
    private BinaryTreeNode parent;

    /*
    *the longest distance from a node to one of the tree leaves.
     */
    private int biggestDistanceToLeaf=-1;

    /*
     * a constant representing the default biggest distance from leaf of a node
     * (in case a node doesn't exists).
     */
    private static final int DEFAULT_DISTANCE_FROM_LEAF = -1;

    /*
     * root constructor.
     */
    BinaryTreeNode(){
    }


    /*
     * constructor for a binaryTreeNode leaf, which receives a value and creates a node with that value.
     * @param value - the value to be inserted in the node.
     */
    BinaryTreeNode(int value){
        this.value=value;
        setDistance();
    }
    //TODO expleine its a leaf


    /*
     * constructor for a node, giving it's father and it's value.
     * @param parent - the new node's parent
     * @param value - the value to be inserted in the node.
     */
    BinaryTreeNode(BinaryTreeNode parent, int value){
        this.value=value;
        this.parent=parent;
        parent.setChild(this);
        updateAncestorsDistanceToLeaf();
    }

    /*
     *this function updates the distance to the leaf in the current node. then it calls the
     * ancestorsDistanceToLeaf method which recursively follows each node from the
     * node to the root, and updates it's biggestDistnaceToLeaf field.
     */
    void updateAncestorsDistanceToLeaf(){
        setDistance();
        if(parent==null){// if its the root
            return;
        }
        parent.ancestorsDistanceToLeaf();

    }

    /*
     * this function updates the current node biggest distance to leaf. then it recursively follows each
     * node from the node to the root, and updates it's biggestDistnaceToLeaf field.
     */
    void ancestorsDistanceToLeaf()
    {
        int currentDistance=biggestDistanceToLeaf;
        setDistance();
        if (currentDistance==biggestDistanceToLeaf||parent==null) //if biggest distance havn't changed, or
            // its the root
        {
            return;
        }
        parent.ancestorsDistanceToLeaf();
    }



    /* this function sets the biggestDistanceToLeaf field in the node. it does do by comparing the
     *  biggestDistanceToLeaf fields in the right and left sons (if exists).
     */
    void setDistance(){
        int biggestDistanseLeft = DEFAULT_DISTANCE_FROM_LEAF;
        int biggestDistanseRight = DEFAULT_DISTANCE_FROM_LEAF;

        if (rightChild!=null)  // if right child exists
        {
            biggestDistanseRight=rightChild.biggestDistanceToLeaf;
        }
        if (leftChild!=null) // if left child exists
        {
            biggestDistanseLeft=leftChild.biggestDistanceToLeaf;
        }

        if (biggestDistanseLeft>biggestDistanseRight)
        {
            biggestDistanceToLeaf=biggestDistanseLeft+1; //adding also father node to the count
        }else {
            biggestDistanceToLeaf=biggestDistanseRight+1; //adding also father node to the count
        }
    }


    /*
     *  BiggestDistanceToLeaf getter.
     * @return the node's biggest distance from a leaf.
     */
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


    /*
     * node's value getter.
     * @return value - the value our node contains.
     */
    int getValue () {
        return value;
    }


    /*
     * this function receives a node and sets it as our node's right child.
     * @param rightChild  - the node to be setted as right child
     */
    void setRightChild(BinaryTreeNode rightChild) {
        this.rightChild = rightChild;
    }


    /*
    * this function receives a node and sets it as our node's left child.
    * @param leftChild  - the node to be setted as left child
    */
    void setLeftChild(BinaryTreeNode leftChild) {
        this.leftChild = leftChild;
    }


    /*
     * this function returns the node's existing child- could be it's right or left.
     * (will be used on nodes that don't have 2 sons).
     * @return
     */

    BinaryTreeNode getChild(){//TODO genric child
        if (leftChild!=null){
            return leftChild;
        }else {
            return rightChild;
        }
    }


    /*
     * this function receives a node and sets it in it's right place as a child to our node. (if it has bigger
     * value than our Node - it will be placed right, otherwise left etc.)
     * @param childNode - the node to be setted as a child
     * @return - true if childNode was setted as a child, false otherwise.
     */
    boolean setChild(BinaryTreeNode childNode){

        // if we received a valid existing Node, and it's value is not as our node
        if (childNode!=null && childNode.getValue()!=value)
        {
            if (childNode.getValue()>value) {
                rightChild=childNode;
            }
            else if (childNode.getValue()<value){
                leftChild=childNode;
            }
            childNode.setParent(this);
            return true;
        }
        return false;
    }

    /*
     * this function receives a childNode and removes it (Sets that child field - left or right - as null)
     * @param childNode - the child to remove
     * @return true if child was found and removed, false otherwise
     */
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

    /*
     * parent setter. receives a node, and sets it as our node's parent.
     * @param parent - the node to be setted as parent.
     */
    void setParent(BinaryTreeNode parent) {
        this.parent = parent;
    }
}
