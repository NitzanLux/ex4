package oop.ex4.data_structures;


/**
 * This class is the complete and tested implementation of an AVL-tree.
 *
 * @author itamar108, nlux.
 */
public class AvlTree extends BinaryTree {

    /*
      * a constant representing the biggest distance to leaf of an un-exsisted node in an avl tree (-1).
                         */
    private static final int UNEXISTS_CHILD_BIGGEST_DISTANCE = -1;
    private static final double SQRT_5 = Math.sqrt(5);
    private static final int FIBONACCI_AVL_COEFFICIENT = 3;
    private static final int LEFT_VIOLATION = 1;
    private static final int RIGHT_VIOLATION = -1;
    private static final int RIGHT_CHILD_BALANCE_FACTOR = 1;
    private static final int LEFT_CHILD_BALANCE_FACTOR = -1;
    private static final int NO_AVL_VIOLATION = 1;

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
    AvlTree(AvlTree avlTree) {
        super(avlTree);
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


    /**
     * A method that calculates the minimum numbers of nodes in an AVL tree of height h.
     *
     * @param h - height of the tree (a non-negative number).
     * @return minimum number of nodes of height h.
     */
    public static int findMinNodes(int h) {
        return fibonacciOragn(h + FIBONACCI_AVL_COEFFICIENT) - 1;
    }

    /*
     * return fibonacci organ by formula.
     * @param organPosition - fibonacci in the n position series.
     */
    private static int fibonacciOragn(int organPosition) {
        return (int) ((1 / SQRT_5) * (Math.pow((1 + SQRT_5) / 2, organPosition) - Math.pow((1 - SQRT_5) / 2,
                organPosition)));
    }


    /**
     * A method that calculates the maximum number of nodes in an AVL tree of height h,
     *
     * @param h - height of the tree (a non-negative number).
     * @return maximum number of nodes of height h
     */
    public static int findMaxNodes(int h) {
        // calculating using the formula for a the number of complete tree nodes.
        return (int) (Math.pow(2, h + 1) - 1);

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
            treeViolationChecker(currentNode);
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
        BinaryTreeNode nodeToDelete = elementFinder(toDelete);
        if (nodeToDelete == null) {
            return false;
        }
        BinaryTreeNode nodeToDeleteParent = nodeToDelete.getParent();
        boolean hadChild = true;
        if (nodeToDelete.getChild() == null) {
            hadChild = false;
        }
        BinaryTreeNode successor = findSuccessor(nodeToDelete);
        boolean isDelete = super.delete(toDelete);
        if (isDelete) {//if is delete find minimal node for check balence.
            if (hadChild) {
                if (successor != null && successor.getRightChild() != null) {
                    successor = findSuccessor(successor);
                }
                treeViolationChecker(successor);
            } else {
                treeViolationChecker(nodeToDeleteParent);
            }
            return true;
        }
        return false;

    }

    /*
     * this function checks for avl violations in the tree, and fixes them if found.
     * @param updatedNode - the node that have been changed (deleted/added)
     */
    private void treeViolationChecker(BinaryTreeNode updatedNode) {
        BinaryTreeNode currentNode = updatedNode;
        while (currentNode != null) {
            balanceTree(currentNode);
            currentNode = currentNode.getParent();
        }
    }

    /*
     * this function receives a given node, and preform the balance operation on it (if needed)
     * @param currentNode - the given node to be balanced.
     */
    private void balanceTree(BinaryTreeNode currentNode) {
        int violation = checkViolation(currentNode);

        if (Math.abs(violation) <= NO_AVL_VIOLATION) // if no violation
        {
            return;
        }
        if (violation < RIGHT_VIOLATION) {// if right violation
            if (checkViolation(currentNode.getRightChild()) == RIGHT_CHILD_BALANCE_FACTOR) {
                rotationToLeft(currentNode.getRightChild(), false);
            }
            rotationToLeft(currentNode, true);
        } else if (violation > LEFT_VIOLATION)// if left violation
        {
            if (checkViolation(currentNode.getLeftChild()) == LEFT_CHILD_BALANCE_FACTOR) {
                rotationToLeft(currentNode.getLeftChild(), true);
            }
            rotationToLeft(currentNode, false);
        }
    }

    /*
     * this function preforms a rotation to left  correction in an avl tree.
     * @param binaryTreeNode - the node to preform rotation on.
     * @param isLeft - boolean flag indicating if it's a right/left violation.
     */

    private void rotationToLeft(BinaryTreeNode binaryTreeNode, boolean isLeft) {
        BinaryTreeNode newFather;
        if (isLeft) {
            newFather = binaryTreeNode.getRightChild();
        } else {
            newFather = binaryTreeNode.getLeftChild();
        }
        rotate(newFather, binaryTreeNode);
        binaryTreeNode.updateAncestorsDistanceToLeaf();

}

    /*  this function preforms a rotation correction in the tree.
     *
     */

    private void rotate(BinaryTreeNode child, BinaryTreeNode father) {
        replaceOnlyChild(father, child);
        if (father == root) {
            root = child;
        }
        BinaryTreeNode childOfChildOfFather;
        if (father.getValue() > child.getValue()) {
            childOfChildOfFather = child.getRightChild();
        } else {
            childOfChildOfFather = child.getLeftChild();
        }
        father.setChild(childOfChildOfFather);
        child.setChild(father);
    }
    /*
     * this function receives a node, and returns his violation factor.
     * @param currentNode - the node to be checked.
     */

    private int checkViolation(BinaryTreeNode currentNode) {
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


}



