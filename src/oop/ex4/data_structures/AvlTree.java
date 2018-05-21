package oop.ex4.data_structures;

/**
 * This class is the complete and tested implementation of an AVL-tree.
 *
 * @author itamar108 nlux.
 */
public class AvlTree extends BinaryTree{

    private BinaryTreeNode root;
    private BinaryTreeNode smallestSon;
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
        for (int value:data) {
            if (root==null){
                root=new BinaryTreeNode(value);
            }else {
            add(value);}
        }
    }

    @Override
    public boolean add(int newValue) {
        if (super.add(newValue)){
            BinaryTreeNode currentNode=elementFinder(newValue);
            balanceVoilationChecker(currentNode);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int toDelete) {
        if (super.delete(toDelete)){
            BinaryTreeNode currentNode=fatherOfElement(toDelete);
            balanceVoilationChecker(currentNode);
            return true;
        }
        return false;
    }
    /*
     * this function checks for avl violations in the tree, and fixes them if founds.
     * @param updatedNode - the node that have been changed (deleted/added)
     */

    private void balanceVoilationChecker(BinaryTreeNode updatedNode){
        BinaryTreeNode currentNode=updatedNode;
        do {
            balanceTree(currentNode);
            currentNode = currentNode.getParent();
        }while (currentNode.getParent()!=null);

    }
    private void balanceTree(BinaryTreeNode currentNode){
        int violation= balancedViolation(currentNode);
        while (violation!=0){
            if (violation>0) {
                rotate(currentNode.getLeftChild(), currentNode);
            }else {
                rotate(currentNode.getRightChild(),currentNode);
            }
            if (Math.abs(violation)==1){
                rotate(currentNode.getParent(),currentNode.getParent().getParent());
            }
            currentNode.updateAncestorsDistanceToLeaf();
            violation= balancedViolation((currentNode));
        }
    }

    private void rotate(BinaryTreeNode child, BinaryTreeNode father){
        replaceOnlyChild(father,child);
        BinaryTreeNode childOfChildToFather;
        if (father.getValue()>child.getValue()){
            childOfChildToFather=child.getRightChild();
        }else {
            childOfChildToFather=child.getLeftChild();
        }
        father.setChild(childOfChildToFather);
        child.setChild(father);
    }
    /*
     * this function returns the AVL balance factor at a given node. it does so by caluclating the biggest
     * distance from leaf of the right son, and biggest distance from leaf of the left son, and subtracting
     * between them.
     * @param currentNode - the node to return it's balance factor
     * @return - the AVl balance factor
     */
    private int balanceFactor(BinaryTreeNode currentNode){
        int rightHigte=-1;
        int leftHight =-1;
        if (currentNode.getLeftChild()!=null){
            leftHight=currentNode.getLeftChild().getBiggestDistanceToLeaf();
        }
        if (currentNode.getRightChild()!=null){
            rightHigte=currentNode.getRightChild().getBiggestDistanceToLeaf();
        }
        return (leftHight-rightHigte);
    }
    /*
    * defind the kind of violation , 0 no violation , 2 LL , 1 RL ,-1 LR ,-2 RR.
     */
    private int balancedViolation(BinaryTreeNode perentToCheck){
        int violation = balanceFactor(perentToCheck);
        if (Math.abs(violation)<=1){
            return 0;
        }
        int leftChildViolation= balanceFactor(perentToCheck.getLeftChild());
        int rightChildViolation= balanceFactor(perentToCheck.getRightChild());
        if (violation>0){
            if (leftChildViolation>=0){
                return 2;
            }
            return -1;
        }else {//TODO megic num of the ahoshloki
            if (rightChildViolation<=0){
                return -2;
            }
            return 1;
        }
    }

    /**
     * A method that calculates the minimum numbers of nodes in an AVL tree of height h.
     * @param h - height of the tree (a non-negative number).
     * @return minimum number of nodes of height h.
     */
    public static int findMinNodes(int h){
        double sqrt5=Math.sqrt(5);
        int fibonacciHplusOne =(int) ((1/sqrt5)*(Math.pow((1+sqrt5)/2,h+3)-Math.pow((1-sqrt5)/2,h+3)));
        return fibonacciHplusOne-1;
    }//TODO added new method. refactor names

    /**
     * A method that calculates the maximum number of nodes in an AVL tree of height h,
     * @param h - height of the tree (a non-negative number).
     * @return maximum number of nodes of height h
     */
    public static int findMaxNodes(int h){
        return (int)(Math.pow(2,h)-1);
    }//TODO added new method


}



