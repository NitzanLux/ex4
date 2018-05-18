package oop.ex4.data_structures;

/**
 * This class is the complete and tested implementation of an AVL-tree.
 *
 * @author itamar108 nlux.
 */
public class AvlTree {

    private AvlNode smallestSon;
    private AvlNode root;
    private int treeSize = 0;

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
        AvlNode lastFather = fatherOfElement(newValue);
        return setNextPath(lastFather, newValue);
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
        AvlNode son = new AvlNode(father, newValue);
        if (father.getValue() < newValue) {
            father.setRightChild(son);
        } else {
            father.setLeftChild(son);
        }
        treeSize++;
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
        return getNextPath(fatherOfElement(searchVal), searchVal);

    }

    private AvlNode fatherOfElement(int searchVal) {
        AvlNode nextPath = root;
        AvlNode lastPath = root;
        while (nextPath != null && nextPath.getValue() != searchVal) {
            lastPath = nextPath;
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
     * Remove a node from the tree, if it exists.
     *
     * @param toDelete - value to delete.
     * @return true iff toDelete found and deleted.
     */
    public boolean delete(int toDelete) {
        if (toDelete == smallestSon.getValue()) {
            smallestSon = smallestSon.getParent();
            smallestSon.setLeftChild(null);
            treeSize--;
            return true;
        }
        AvlNode fatherToDelete = fatherOfElement(toDelete);
        AvlNode nodeToDelete = getNextPath(fatherToDelete, toDelete);
        if (nodeToDelete != null) {
            if (nodeToDelete.getLeftChild() != null || nodeToDelete.getRightChild() != null) {
                if (nodeToDelete.getLeftChild() == null ^ nodeToDelete.getRightChild() == null) {
                    replaceOnlyChild(nodeToDelete,nodeToDelete.getChild());
                } else {
                    AvlNode replaceNode = findSucsseor(nodeToDelete);
                    if (replaceNode==null){
                        return false;
                    }
                    replaceOnlyChild(nodeToDelete,replaceNode);
                    replaceNode.setChild(replaceNode.getRightChild());
                    replaceNode.setChild(replaceNode.getLeftChild());
                }
            } else {
                nodeToDelete.getParent().removeChild(nodeToDelete);
            }
            treeSize--;
            return true;
        }
        return false;
    }
    private void replaceOnlyChild(AvlNode nodeToReplace,AvlNode replaceNode){
        replaceNode.getParent().removeChild(replaceNode);//TODO rais exeption;
        nodeToReplace.getParent().setChild(replaceNode);//TODO should we move it to the other class??

    }
    private AvlNode findSucsseor(AvlNode baseNode) {
        AvlNode succsseor = minRight(baseNode);
        if (succsseor!=null){
            return succsseor;
        }
        succsseor=baseNode;
        while (succsseor.getParent()!=null){
            succsseor=succsseor.getParent();
            if (succsseor.getValue()>baseNode.getValue()){
                return succsseor;
            }
        }
        return null;
    }

    private AvlNode minRight(AvlNode baseNode) {
        AvlNode minRightNode = baseNode.getRightChild();
        if (minRightNode == null) {
            return baseNode;
        }
        while (minRightNode.getLeftChild() != null) {
            minRightNode = minRightNode.getLeftChild();
        }
        return minRightNode;
    }


    /**
     * @return the number of nodes in the tree.
     */
    public int size() {
        return treeSize;
    }


}
