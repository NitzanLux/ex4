package oop.ex4.data_structures;

import java.util.Random;

/**
 * This class is the complete and tested implementation of an AVL-tree.
 *
 * @author itamar108, nlux.
 */
public class AvlTree extends BinaryTree {
    //TODO CANOT BE PUBLIC
    /*a magic number representing a left-left violation */
    private static final int LL = 2;
    /*a magic number representing a right-left violation */
    private static final int RL = 1;
    /*a magic number representing a right-right violation */
    private static final int RR = -2;
    /*a magic number representing a left-right violation */
    private static final int LR = -1;


    /*
     * a constant representing the biggest distance to leaf of an un-exsisted node in an avl tree (-1).
     */
    private static final int UNEXISTS_CHILD_BIGGEST_DISTANCE = -1;

    /**
     * A default constructor.
     */
    public AvlTree() {
        super();
    }

    /**
     * A constructor that builds the tree by adding the elements in the input array one-by-one If the same
     * values appears twice (or more) in the list, it is ignored.
     *
     * @param data - values to add to tree
     */
    public AvlTree(int[] data) {
        super(data);
    }


    /**
     * A copy-constructor that builds the tree from existing tree
     *
     * @param avlTree - tree to be copied
     */
    AvlTree(BinaryTree avlTree) {
        for (int value : avlTree) {
                add(value);
        }

    }


    /**
     * Add a new node with key newValue into the tree.
     *
     * @param newValue - new value to add to the tree.
     * @return false iff newValue already exist in the tree
     */
    @Override
    public boolean add(int newValue) {
        boolean isAdded = super.add(newValue);
        if (isAdded) {
            BinaryTreeNode currentNode = elementFinder(newValue);
            balanceViolationChecker(currentNode);
            return true;
        }
        return false;
    }

    /**
     * Remove a node from the tree, if it exists.
     *
     * @param toDelete - value to delete.
     * @return true iff toDelete found and deleted
     */
    @Override
    public boolean delete(int toDelete) {
        BinaryTreeNode currentNode = fatherOfElement(toDelete);
        boolean isDelete=super.delete(toDelete);
        if (isDelete&&currentNode!=null) {
            balanceViolationChecker(currentNode.getLeftChild());
            balanceViolationChecker(currentNode.getRightChild());
            return true;
        }
        return false;
    }

    /**
     * Does tree contain a given input value.
     *
     * @param searchVal - value to search for
     * @return if val is found in the tree, return the depth of its node (where 0 is the root).
     * Otherwise -- return -1.
     */
    @Override
    public int contains(int searchVal) {
        return super.contains(searchVal);
    }

    /*
     * this function checks for avl violations in the tree, and fixes them if found.
     * @param updatedNode - the node that have been changed (deleted/added)
     */
    private void balanceViolationChecker(BinaryTreeNode updatedNode) {

        BinaryTreeNode currentNode = updatedNode;
        while (currentNode != null){
            balanceTree(currentNode);
            currentNode = currentNode.getParent();
        }
    }

    /*
     * this function receives a given node, and preform the balance operation on it (if needed)
     * @param currentNode - the given node to be balanced.
     */
    private void balanceTree(BinaryTreeNode currentNode) {
        int violation = cheackViolation(currentNode);

        if (Math.abs(violation) <= 1) // if no violation
        {
            return;
        }
        if (violation < 0) {// if right violation
            BinaryTreeNode rootNode = currentNode.getRightChild();
            if (cheackViolation(currentNode.getRightChild()) == 1) {
                rorationToLeft(currentNode.getRightChild(), false);
            }
            rorationToLeft(currentNode, true);
        } else // if left violation
        {
            if (cheackViolation(currentNode.getLeftChild()) == -1) {
                rorationToLeft(currentNode.getLeftChild(), true);
            }
            rorationToLeft(currentNode, false);
        }
    }


    //TODO = what is rotation to left and rotation.

    /*
     * this function preforms a rotation to left  correction in an avl tree.
     * @param binaryTreeNode - the node to preform rotation on.
     * @param isLeft - boolean flag indicating if it's a right/left violation.
     */
    private void rorationToLeft(BinaryTreeNode binaryTreeNode, boolean isLeft) {
        if (isLeft) {
            rotate(binaryTreeNode.getRightChild(), binaryTreeNode);
        } else {
            rotate(binaryTreeNode.getLeftChild(), binaryTreeNode);
        }
        binaryTreeNode.updateAncestorsDistanceToLeaf();
    }

    /*
     * this function receives a node, and returns his violation factor.
     * @param currentNode - the node to be checked.
     */

    private int cheackViolation(BinaryTreeNode currentNode) {
        //setting default heights of sons before checking actual one
        int leftChildHeight = UNEXISTS_CHILD_BIGGEST_DISTANCE;
        int rightChildHeight = UNEXISTS_CHILD_BIGGEST_DISTANCE;
        if (currentNode.getRightChild() != null) {
            rightChildHeight = currentNode.getRightChild().getBiggestDistanceToLeaf();
        }
        if (currentNode.getLeftChild() != null) {
            leftChildHeight = currentNode.getLeftChild().getBiggestDistanceToLeaf();
        }
        return leftChildHeight - rightChildHeight;
    }


    /*  this function preforms the rotatin
     *
     */

    private void rotate(BinaryTreeNode child, BinaryTreeNode father) {//shouldweRemoveFather
        replaceOnlyChild(father, child);
        if (father == root) {
            root = child;
        }
        BinaryTreeNode childOfChildToFather;
        if (father.getValue() > child.getValue()) {
            childOfChildToFather = child.getRightChild();
        } else {
            childOfChildToFather = child.getLeftChild();
        }
        father.setChild(childOfChildToFather);
        child.setChild(father);
    }


//    private void balanceTree(BinaryTreeNode currentNode){
//        int violation= balancedViolation(currentNode);
//        while (violation!=0){
//            if (violation<0) {
//                rotate(currentNode.getRightChild(), currentNode);
//            }else {
//                rotate(currentNode.getLeftChild(),currentNode);
//            }
//            if (Math.abs(violation)==1){
//                rotate(currentNode.getParent(),currentNode.getParent().getParent());
//                currentNode.getParent().getLeftChild().updateAncestorsDistanceToLeaf();
//                currentNode.getParent().getRightChild().updateAncestorsDistanceToLeaf();
//            }
//            currentNode.updateAncestorsDistanceToLeaf();
//            violation= balancedViolation((currentNode));
//        }
//    }
//    /*
//     * this function returns the AVL balance factor at a given node. it does so by caluclating the biggest
//     * distance from leaf of the right son, and biggest distance from leaf of the left son, and subtracting
//     * between them.
//     * @param currentNode - the node to return it's balance factor
//     * @return - the AVl balance factor
//     */
//    private int balanceFactor(BinaryTreeNode currentNode){
//        int rightHigte=-1;
//        int leftHight =-1;
//        if (currentNode.getLeftChild()!=null){
//            leftHight=currentNode.getLeftChild().getBiggestDistanceToLeaf();
//        }
//        if (currentNode.getRightChild()!=null){
//            rightHigte=currentNode.getRightChild().getBiggestDistanceToLeaf();
//        }
//        return (leftHight-rightHigte);
//    }
//    /*
//    * defind the kind of violation , 0 no violation , 2 LL , 1 RL ,-1 LR ,-2 LL.
//     */
//    private int balancedViolation(BinaryTreeNode nodeToCheck){//TODO fucking bug i shit you not
//        int violation = balanceFactor(nodeToCheck);
//        if (Math.abs(violation)<=1){
//            return 0;
//        }
//        int leftChildViolation=0;
//        int rightChildViolation=0;
//        if (nodeToCheck.getLeftChild()!=null){
//            leftChildViolation= balanceFactor(nodeToCheck.getLeftChild());}
//        if (nodeToCheck.getRightChild()!=null){
//            rightChildViolation=balanceFactor(nodeToCheck.getRightChild());
//        }
//        if (violation<0){
//            if (leftChildViolation==-1){
//                return LR;
//            }
//            return RR;
//        }else {//TODO megic num of the ahoshloki
//            if (rightChildViolation==1){
//                return RL;
//            }
//            return LL;
//        }
//    }

    /**
     * A method that calculates the minimum numbers of nodes in an AVL tree of height h.
     *
     * @param h - height of the tree (a non-negative number).
     * @return minimum number of nodes of height h.
     */
    public static int findMinNodes(int h) {
        double sqrt5 = Math.sqrt(5);
        int fibonacciHplusOne = (int) ((1 / sqrt5) * (Math.pow((1 + sqrt5) / 2, h + 3) - Math.pow((1 - sqrt5) / 2, h + 3)));
        return fibonacciHplusOne - 1;
    }//TODO added new method. refactor names

    /**
     * A method that calculates the maximum number of nodes in an AVL tree of height h,
     *
     * @param h - height of the tree (a non-negative number).
     * @return maximum number of nodes of height h
     */
    public static int findMaxNodes(int h) {
        return (int) (Math.pow(2, h) - 1);
    }//TODO added new method
    public static void main(String[] args) {
        Random random=new Random();
        AvlTree avlTree=new AvlTree();
        int size=0;
        for (int i = 0; i <10; i++) {
            avlTree.add(i/2);
        }
        for (int i = 0; i <10; i++) {
            if (avlTree.contains(i)!=-1){
                System.out.println(avlTree.contains(i)+"     :    "+i);
                size++;
            }
        }
        for (int i = 0; i <10 ; i++) {
            avlTree.delete(i);
        }
        for (int i = 0; i <1000; i++) {
            if (avlTree.contains(i)!=-1){
                size--;
            }
        }
        System.out.println("ffffff");
    }

}



