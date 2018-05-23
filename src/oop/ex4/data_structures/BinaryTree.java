package oop.ex4.data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static oop.ex4.data_structures.BTreePrinter.printNode;

/*
 * @authors itamar108, nlux
 */


//TODO - WHAT THE FUCK IS GOING ON WITH THE CLASSIFICATION OF FUNCTION. SOME PUBLIC, SOME PRIVATE, SOME DEFAULT. WE NEED TO CHECK IT.


abstract class BinaryTree implements Iterable<Integer> {
    BinaryTreeNode smallestNode;
    BinaryTreeNode root;
    private int treeSize = 0;
    int temp=0 ;//todo oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo

    /*
     * constructor from an array. buiilds a tree based of the values given in the array.
     * @param data -  an array of value typed int.
     */
    BinaryTree(int[] data) {
        if (data != null) {
            for (int value : data) {
                add(value);
            }
        }
    }

    BinaryTree() {
    }

    /*
     * this function searches for a given value in a tree, and if found it returns it's father.
     * @param searchVal - the value to search.
     * @return the father if exists. null otherwise.
     */
    BinaryTreeNode fatherOfElement(int searchVal) {
        BinaryTreeNode nextPath = root;
        BinaryTreeNode lastPath = root;
        // while we haven't reached null and havn't found our value already in the tree.
        while (nextPath != null && nextPath.getValue() != searchVal) {
            lastPath = nextPath;
            nextPath = getNextPath(lastPath, searchVal);
        }
        return lastPath;
    }


    //tODO ==== SHOULD NOT BE PUBLIC

    /**
     * Add a new node with key newValue into the tree.
     *
     * @param newValue - new value to add to the tree.
     * @return - false if newValue already exist in the tree
     */
    public boolean add(int newValue) {
        if (root == null) {
            root = new BinaryTreeNode(newValue);
            smallestNode = root;
            treeSize++;
            return true;
        }
        if (smallestNode != null && smallestNode.getValue() >= newValue) {
            if (smallestNode.getValue() > newValue) {
                BinaryTreeNode newChild = new BinaryTreeNode(smallestNode, newValue);
                smallestNode=newChild;
                treeSize++;
                return true;
            }
            return false;//TODO blance tree and update  higte
        }
        BinaryTreeNode lastFather = fatherOfElement(newValue);
        if (setNextPath(lastFather, newValue)){
            treeSize++;
            return true;
        }
        return false;
    }


    //todo - deleted gray parts.
    /*
     * this function receives an appropriate father (using getNextPath method),
     * and a value to insert to the tree, and puts the value
     * in it's right place in relation to the father (right/left son). .
     * @param father - the suitable father
     * @param newValue - the value to be added
     * @return true if value was added, and false if it already existed.
     */
    private boolean setNextPath(BinaryTreeNode father, int newValue) {
        BinaryTreeNode nextPath = getNextPath(father, newValue);
        if (nextPath != null) {
            return false;
        }
        BinaryTreeNode son = new BinaryTreeNode(father, newValue);
        father.setChild(son);
        son.updateAncestorsDistanceToLeaf();
        return true;


    }


    /*
     * this function helps us run over the tree nodes in a binary search tree. it
     * commits a transition from a given father into the suitable son, according to the given value:
     * if value is smaller than father it returns the left child, if bigger - right child. otherwise it
     * returns the father.
     * @param father - a given father node
     * @param value - the value we look after
     * @return - the suitable node.
     */
    BinaryTreeNode getNextPath(BinaryTreeNode father, int value) {
        if (father != null) {
            if (value > father.getValue())  // if current node value is bigger the given one
            {
                return father.getRightChild();
            }
            if (value < father.getValue()) {
                return father.getLeftChild();
            }
            if (value == father.getValue()) {
                return father;
            }
        }
        return null;

    }


    /*
     * this function searches the tree for a certain given value, and returns the node that contains it.
     * @param - searchVal - the value to be searched.
     * @return - the node containing the value, null if value is not in the tree.
     */
    BinaryTreeNode elementFinder(int searchVal) {
        return getNextPath(fatherOfElement(searchVal), searchVal);

    }


    /*
     * Does tree contain a given input value.
     *
     * @param searchVal - value to search for
     * @return if val is found in the tree, return the depth of its node (where 0 is the root).
     * Otherwise return -1.
     */
    int contains(int searchVal) {
        int depth = 0;
        BinaryTreeNode currentNode = root;
        while (currentNode != null) {
            if (currentNode.getValue() ==searchVal )
            {
                return depth;
            }
            currentNode = getNextPath(currentNode, searchVal);
            depth++;
        }
        return -1;

    }

    //TODO SHOULD NOT BE PUBLIC

    /**
     * Remove a node from the tree, if it exists.
     *
     * @param toDelete - value to delete.
     * @return true iff toDelete found and deleted.
     */
    public boolean delete(int toDelete) {
        BinaryTreeNode nodeToDelete = smallestNode;
        if (root == null) {
            return false;
        }
        if (toDelete == smallestNode.getValue()&&toDelete!=root.getValue()) {
            smallestNode = smallestNode.getParent();
        } else if (toDelete == smallestNode.getValue()&&toDelete==root.getValue()) {
            root=null;
            treeSize=0;
        }else {
                nodeToDelete = elementFinder(toDelete);
            }
        if (nodeToDelete != null) {
            removeNode(nodeToDelete);
            return true;
        }
        return false;

    }

    private void removeNode(BinaryTreeNode nodeToDelete) {
        BinaryTreeNode fatherToDelete = nodeToDelete.getParent();
        if (nodeToDelete.getLeftChild() == null || nodeToDelete.getRightChild() == null) {
            if (nodeToDelete.getLeftChild() == null ^ nodeToDelete.getRightChild() == null) {
                BinaryTreeNode onlyChildOfToDelete = nodeToDelete.getChild();
                replaceOnlyChild(nodeToDelete, onlyChildOfToDelete);
                onlyChildOfToDelete.updateAncestorsDistanceToLeaf();
                if (fatherToDelete==null){
                    root=onlyChildOfToDelete;
                    smallestNode=onlyChildOfToDelete;
                }
                treeSize--;
            } else {
                if (fatherToDelete != null) {
                    fatherToDelete.removeChild(nodeToDelete);
                    fatherToDelete.updateAncestorsDistanceToLeaf();
                }
                if (nodeToDelete==smallestNode){
                    if (fatherToDelete!=null){
                        smallestNode=fatherToDelete;
                    }else {
                        smallestNode=null;
                        root=null;
                    }
                }
                treeSize--;
            }
        } else {
           removeNodeWithTwoSons(nodeToDelete);
        }
    }
    private void removeNodeWithTwoSons(BinaryTreeNode nodeToDelete){
         BinaryTreeNode nodeToReplace = findSuccessor(nodeToDelete);
        if (nodeToDelete==root){
            nodeToReplace=root;
        }else {
            nodeToDelete.getParent().setChild(nodeToReplace);
        }
        if (nodeToDelete.getRightChild()!=nodeToReplace){
            nodeToReplace.getParent().setChild(nodeToReplace.getChild());
            nodeToReplace.setChild(nodeToDelete.getRightChild());
        }
        nodeToReplace.setChild(nodeToDelete.getLeftChild());

    }

    /*
     * this function receives
     * @param nodeToReplace
     * @param replaceNode
     */

    //TODO WHAT IS THIS NAME - REPLACE NODE/NODE TO REPLACE. WE NEED TO CHANGE IT.

    void replaceOnlyChild(BinaryTreeNode nodeToReplace, BinaryTreeNode replaceNode) {
        BinaryTreeNode nodeToReplaceParent = nodeToReplace.getParent();
        {
            BinaryTreeNode replaceNodeParent = replaceNode.getParent();//TODO remove scoaping(smartly)
            if (replaceNodeParent != null) {
                replaceNodeParent.removeChild(replaceNode);
            }
        }
        if (nodeToReplaceParent != null) {
            nodeToReplaceParent.setChild(replaceNode);
//            replaceNode.getParent().removeChild(replaceNode);//TODO rais exeption;
//            if (nodeToReplace.getParent()!=null) {//TODO should we move it to the other class??
//                nodeToReplace.getParent().setChild(replaceNode);
//            }
        } else {
            replaceNode.setParent(null);
        }
    }


    /*
     * this function finds the successor (when values in ascending order) to a given node in the tree, and returns
     * it's node, if found.
     * @param baseNode - the node to checks for it's successor.
     * @return the successor's node.
     */
    BinaryTreeNode findSuccessor(BinaryTreeNode baseNode) {
        if (baseNode==null){
            return null;
        }
        BinaryTreeNode successor = minRight(baseNode);
        if (successor != null && successor.getValue() != baseNode.getValue()) {//if this is itself the minRight.
            return successor;
        }
        while (successor.getParent() != null) {
            successor = successor.getParent();
            if (successor.getValue() > baseNode.getValue()) {
                return successor;
            }
        }
        return null;
    }


    /*
     * this function receives a node and returns the minimum value Node in it's right subtree.
     * @param baseNode - the given node.
     * @return the node that contains the minium value in the right subtree.
     */
    private BinaryTreeNode minRight(BinaryTreeNode baseNode) {
        if (baseNode==null){
            return null;
        }
        BinaryTreeNode minRightNode = baseNode.getRightChild();
        if (minRightNode == null)  // if no right subtree exists
        {
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
    }//TODO no pablic overide

    //TODO documents the iterator.

    /**
     * this function creates the iterator for binarytree.
     *
     * @return an iterator.
     */
    public java.util.Iterator<java.lang.Integer> iterator() {//TODO no pablic overide
        return new Iterator<Integer>() {
            private BinaryTreeNode currentNode = smallestNode;

            @Override
            public boolean hasNext() {
                return (currentNode!=null);
            }

            @Override
            public Integer next() {
                if(hasNext()){
                    Integer valueToReturn = currentNode.getValue();
                    currentNode=findSuccessor(currentNode);
                    return valueToReturn;
                } else {
                    throw new NoSuchElementException();
                }
            }

        };
    }

}
