package oop.ex4.data_structures;
public class AvlTree {


    private AvlNode smallestSon;
    private AvlNode root;

    /**
     * A default constructor.
     */
    public AvlTree()
    {

    }


    /**
     * A constructor that builds the tree by adding the elements in the input array one-by-one If the same
     * values appears twice (or more) in the list, it is ignored.
     * @param data - values to add to tree
     */
    public AvlTree(int [] data)
    {


    }

    /**
     * Add a new node with key newValue into the tree.
     * @param newValue - new value to add to the tree.
     * @return - false if newValue already exist in the tree
     */
    public boolean add(int newValue)
    {

        if (smallestSon.getValue()== newValue) // in our smallest value is the requested value
            return false;

        if (checkNode.getLeftChild()) {
            AvlNode checkNode = this.root;
            while (checkNode != null) {
                if (checkNode.getValue() == newVal) {
                    return false;
                }

                if (checkNode.getValue() > newVal)  // if current node value is bigger the given one
                {
                    checkNode = checkNode.getLeftChild();
                } else {
                    checkNode = checkNode.getRightChild();
                }
            }

        }
    }

    /*
     * this function searches the tree for a certain given value, and returns the node that contains it.
     * @param - searchVal - the value to be searched.
     * @return - the node containing the value, null if value is not in the tree.
     */
    private AvlNode elementFInder(int searchVal)
    {
        if (smallestSon.getValue()== searchVal) // in our smallest value is the requested value
            return smallestSon;

        AvlNode checkNode = this.root;
        while (checkNode!=null)
        {
            if (checkNode.getValue()==searchVal)
            {return checkNode;}

            if (checkNode.getValue()> searchVal)  // if current node value is bigger the given one
            {checkNode = checkNode.getLeftChild();}

            else
            {checkNode= checkNode.getRightChild();}
        }
        return null;
    }



    /**
     * Does tree contain a given input value.
     * @param searchVal - value to search for
     * @return if val is found in the tree, return the depth of its node (where 0 is the root).
     * Otherwise -- return -1.
     */
    public int contains(int searchVal)
    {
        AvlNode requstedNode = elementFInder(searchVal);
        if (requstedNode!= null)
            return requstedNode.getHeight();
        return -1;

    }


    /**
     * @return the number of nodes in the tree.
     */
    public int size()
    {
        return this.root.getNumberOfDecendens();
    }



}
