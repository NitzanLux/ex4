package oop.ex4.data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * @authors itamar108, nlux
 */





abstract class BinaryTree implements Iterable<Integer> {

    /*a pointer to that smallest value node in our tree*/
    private BinaryTreeNode smallestNode;

    /*a pointer to our tree's root node*/
    BinaryTreeNode root;

    /*the number of values in the tree*/
    private int treeSize = 0;


    /*
     * a default constructor of a binary tree.
     */
    BinaryTree() {
    }


    /*
     * constructor from an array. builds a tree based of the values given in the array.
     * @param data -  an array of value typed int.
     */

    BinaryTree(int[] data) {
        if (data != null) {
            for (int value : data) {
                add(value);
            }
        }
    }

    /*
     * A copy-constructor that builds the tree from existing tree.
     * @param binaryTree - tree to be copied
     */
    BinaryTree(BinaryTree binaryTree) {
        if (binaryTree != null) {
            for (int value : binaryTree) {
                add(value);

            }
        }
    }


    /*
     * Add a new node with key newValue into the tree.
     *
     * @param newValue - new value to add to the tree.
     * @return - false if newValue already exist in the tree
     */
    boolean add(int newValue) {
        if (root == null) {
            root = new BinaryTreeNode(newValue);
            smallestNode = root;
            treeSize++;
            return true;
        }
        // if smallest node is bigger then the given value
        if (smallestNode != null && smallestNode.getValue() >= newValue) {
            if (smallestNode.getValue() > newValue) {
                smallestNode = new BinaryTreeNode(smallestNode, newValue);
                treeSize++;
                return true;
            }
            return false;
        }

        BinaryTreeNode lastFather = fatherOfElement(newValue);
        if (setNextPath(lastFather, newValue)) {
            treeSize++;
            return true;
        }
        return false;
    }



    /*
     * this function checks if a tree contain a given input value.
     * @param searchVal - value to search for
     * @return if val is found in the tree, return the depth of its node (where 0 is the root).
     * Otherwise return -1.
     */

    int contains(int searchVal) {
        int depth = 0;
        BinaryTreeNode currentNode = root;
        while (currentNode != null) {
            if (currentNode.getValue() == searchVal) {
                return depth;
            }
            currentNode = getNextPath(currentNode, searchVal);
            depth++;
        }
        return -1;

    }


    /*
     * Remove a node from the tree, if it exists.
     * @param toDelete - value to delete.
     * @return true iff toDelete found and deleted.
     */
    boolean delete(int toDelete) {
        BinaryTreeNode nodeToDelete = elementFinder(toDelete);
        if (nodeToDelete == null) {
            return false;
        }
        if (root == null) {
            return false;
        }
        // if value to delete is our smallest value node (and not the root)
        if (toDelete == smallestNode.getValue() && toDelete != root.getValue()) {
            smallestNode = smallestNode.getParent();
        }
        // in case our value to delete is our smallest node
        else if (toDelete == smallestNode.getValue() && toDelete == root.getValue()) {
            root = root.getChild();
            smallestNode = smallestNode.getChild();
            treeSize--;
            return true;
        }
        removeNode(nodeToDelete);
        treeSize--;
        return true;
    }

    /*
     *  this function excutes the removal of a given node, (including the connecting the different nodes
     * and updating the biggest distances from leaf).
     * @param nodeToDelete - the node we want to delete
     */
    private void removeNode(BinaryTreeNode nodeToDelete) {
        if (nodeToDelete.getLeftChild() == null || nodeToDelete.getRightChild() == null) {
            if (nodeToDelete.getLeftChild() == null ^ nodeToDelete.getRightChild() == null) {
                removeNodeWithOneSon(nodeToDelete);
            }
            else {
                removeNodeWithNoSons(nodeToDelete);
            }
        } else {
            removeNodeWithTwoSons(nodeToDelete);
        }
    }

    /*
     * this function executes the removal a node with no sons (including the connecting the different nodes
     * and updating the biggest distances from leaf).
     * @param nodeToDelete - the node to be deleted.
     */
    private void removeNodeWithNoSons(BinaryTreeNode nodeToDelete){
        BinaryTreeNode fatherToDelete = nodeToDelete.getParent();
        // the only node with no parent is the root
        if (nodeToDelete != root)
        {
            fatherToDelete.removeChild(nodeToDelete);
            fatherToDelete.updateAncestorsDistanceToLeaf();
        }
        if (nodeToDelete == smallestNode) {
            if (fatherToDelete != null) {
                smallestNode = fatherToDelete;
            } else {
                smallestNode = null;
                root = null;
            }
        }
    }

    /*
     * this function executes the removal a node with exactly one son (including the connecting the different nodes
     * and updating the biggest distances from leaf).
     * @param nodeToDelete - the node to be deleted
     */

    private void removeNodeWithOneSon(BinaryTreeNode nodeToDelete){
        BinaryTreeNode onlyChildOfToDelete = nodeToDelete.getChild();
        if (nodeToDelete == root) {
            root = onlyChildOfToDelete;
            smallestNode = onlyChildOfToDelete;
        } else {
            replaceOnlyChild(nodeToDelete, onlyChildOfToDelete);
        }
        onlyChildOfToDelete.updateAncestorsDistanceToLeaf();
    }

    /*
     * this function executes the removal a node with exactly 2 sons (including the connecting the different nodes
     * and updating the biggest distances from leaf).
     * @param nodeToDelete - the node to be deleted
     */
    private void removeNodeWithTwoSons(BinaryTreeNode nodeToDelete) {
        BinaryTreeNode nodeToReplace = findSuccessor(nodeToDelete);
        BinaryTreeNode nodeToDeleteRigthChild = nodeToDelete.getRightChild();
        BinaryTreeNode nodeToReplaceChild = nodeToReplace.getChild();
        BinaryTreeNode nodeToReplaceParent = nodeToReplace.getParent();
        if (nodeToDelete.getParent() == null) {//set nodeToReplace as child of nodeToDelete parent.
            root = nodeToReplace;
            nodeToReplace.setParent(null);
        } else {
            nodeToDelete.getParent().setChild(nodeToReplace);
        }
        if (nodeToDeleteRigthChild != nodeToReplace) { // set right child of nodeToReplace,
            // making sure node to replace won't be a child of himself
            if (nodeToReplaceChild != null) {
                nodeToReplaceParent.setChild(nodeToReplaceChild);
            } else {
                nodeToReplaceParent.setLeftChildToNull();
            }
            nodeToReplace.setChild(nodeToDeleteRigthChild);
        }
        nodeToReplace.setChild(nodeToDelete.getLeftChild());
        nodeToDelete.setParent(null); // disconnecting nodeToDelete from tree
        if (nodeToReplaceChild != null) {// update ancestors according to the minimal node that had changed.
            nodeToReplaceChild.updateAncestorsDistanceToLeaf();
        } else if (nodeToReplaceParent != nodeToDelete) {
            nodeToReplaceParent.updateAncestorsDistanceToLeaf();
        } else {
            nodeToReplace.updateAncestorsDistanceToLeaf();
        }
    }


    /*
     * this function searches for a given value in a tree, and if found it returns it's father.
     * @param searchVal - the value to search.
     * @return the father if exists. null otherwise.
     */
    private BinaryTreeNode fatherOfElement(int searchVal) {
        BinaryTreeNode nextPath = root;
        BinaryTreeNode lastPath = root;
        while (nextPath != null && nextPath.getValue() != searchVal) {
            lastPath = nextPath;
            nextPath = getNextPath(lastPath, searchVal);
        }
        return lastPath;
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
     * this function helps us run over the tree nodes in a binary search tree. it
     * commits a transition from a given father into the suitable son, according to the given value:
     * if value is smaller than father it returns the left child, if bigger - right child. otherwise it
     * returns the father.
     * @param father - a given father node
     * @param value - the value we look after
     * @return - the suitable node.
     */

    private BinaryTreeNode getNextPath(BinaryTreeNode father, int value) {
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
     * this function receives an appropriate father (using getNextPath method),
     * and a value to insert to the tree, and puts the value
     * in it's right place in relation to the father (right/left son).
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
     * this function replaces the given node in the another given node, and updates the corresponding parents.
     * @param nodeToReplace - the node we wish to replaces.
     * @param newNode - the new node that will take the given's nodes position.
     */

    void replaceOnlyChild(BinaryTreeNode nodeToReplace, BinaryTreeNode newNode) {
        BinaryTreeNode nodeToReplaceParent = nodeToReplace.getParent();
        { // scoaping for unneeded variables
            BinaryTreeNode replaceNodeParent = newNode.getParent();
            if (replaceNodeParent != null) {
                replaceNodeParent.removeChild(newNode);
            }
        }
        if (nodeToReplaceParent != null) {
            nodeToReplaceParent.setChild(newNode);
        } else {
            newNode.setParent(null);
        }
    }


    /*
     * this function finds the successor (the next value in ascending order) to a given node in the tree, and returns
     * it's node, if found.
     * @param baseNode - the node to checks for it's successor.
     * @return the successor's node.
     */

    BinaryTreeNode findSuccessor(BinaryTreeNode baseNode) {
        if (baseNode == null) {
            return null;
        }
        BinaryTreeNode successor = minRight(baseNode);
        if (successor==null){
            return null;
        }
        if ( successor.getValue() != baseNode.getValue()) {//if this is itself the minRight.
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
     * @return the node that contains the minimum value in the right subtree.
     */
    private BinaryTreeNode minRight(BinaryTreeNode baseNode) {
        if (baseNode == null) {
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


    /*
     * @return the number of nodes in the tree.
     */
     int size() {
        return treeSize;
    }



    /*
     * this function creates and returns an Integers iterator for binarytree.
     * @return an Integers iterator for our tree.
     */
    public java.util.Iterator<java.lang.Integer> iterator() {
        return new Iterator<Integer>() {
            /*the current node pointer*/
            private BinaryTreeNode currentNode = smallestNode;

            /**
             * checks if our iterator has a next value.
             * @return - the next true if it has, false otherwise.
             */
            @Override
            public boolean hasNext() {
                return (currentNode != null);
            }

            /**
             * returns the next value (integer) in the iteration.
             * @return - the next value.
             * @throws  NoSuchElementException if iterator has no next value.
             */
            @Override
            public Integer next() {
                if (hasNext()) {
                    Integer valueToReturn = currentNode.getValue();
                    currentNode = findSuccessor(currentNode);
                    return valueToReturn;
                } else {
                    throw new NoSuchElementException();
                }
            }

        };
    }

}
