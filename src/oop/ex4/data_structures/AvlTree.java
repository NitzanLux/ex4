package oop.ex4.data_structures;

public class AvlTree {


    private AvlNode smallestSon;
    private AvlNode root;

    /**
     * A default constructor.
     */
    public AvlTree() {

    }


    /**
     * A constructor that builds the tree by adding the elements in the input array one-by-one If the same
     * values appears twice (or more) in the list, it is ignored.
     *
     * @param data - values to add to tree
     */
    public AvlTree(int[] data) {


    }

    /**
     * Add a new node with key newValue into the tree.
     *
     * @param newValue - new value to add to the tree.
     * @return - false if newValue already exist in the tree
     */
    public boolean add(int newValue) {

        if (smallestSon.getValue() >= newValue) {
            return setNextPath(smallestSon, newValue);//TODO blance tree and update  higte
        }
        AvlNode lastFather=fatherOfElement(newValue);
        return setNextPath(lastFather,newValue);
    }


    /*
     * this function receives an appropriate father (using getNextPath method),
      * and a value to insert to the tree, and puts the value
     * in it's right place in relation to the father (right/left son). .
     * @param father - the suitable father
     * @param newValue - the value to be added
     * @return true if value was added, and false if it already existed.
     */

    private boolean setNextPath(AvlNode father, int newValue) {
        if (getNextPath(father, newValue) != null) {
            return false;
        }
        AvlNode son = new AvlNode(father,newValue);
        if (father.getValue() < newValue) {
            father.setRightChild(son);
        } else {
            father.setLeftChild(son);
        }
        return true;
    }

    private AvlNode getNextPath(AvlNode father, int value) {
        if (value > father.getValue())  // if current node value is bigger the given one
        {
            return father.getRightChild();
        }
        if (value < father.getValue()) {
            return father.getLeftChild();
        }
        return father;

    }


    /*
     * this function searches the tree for a certain given value, and returns the node that contains it.
     * @param - searchVal - the value to be searched.
     * @return - the node containing the value, null if value is not in the tree.
     */
    private AvlNode elementFinder(int searchVal) {
        return getNextPath(fatherOfElement(searchVal),searchVal);

    }

    private AvlNode fatherOfElement(int searchVal){
        AvlNode nextPath=root;
        AvlNode lastPath=root;
        while (nextPath!=null && nextPath.getValue()!=searchVal) {
            lastPath=nextPath;
            nextPath = getNextPath(root, searchVal);
        }
        return lastPath;
    }


    /**
     * Does tree contain a given input value.
     *
     * @param searchVal - value to search for
     * @return if val is found in the tree, return the depth of its node (where 0 is the root).
     * Otherwise -- return -1.
     */
    public int contains(int searchVal) {
        AvlNode requstedNode = elementFinder(searchVal);
        if (requstedNode != null)
            return requstedNode.getDepth();
        return -1;

    }


    /**
     * @return the number of nodes in the tree.
     */
    public int size() {
        return this.root.getNumberOfDecendens();
    }


}
