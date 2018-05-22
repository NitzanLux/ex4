package oop.ex4.data_structures;

import java.util.*;


public class BTreePrinter {

    public static void printNode(BinaryTreeNode root1) {
        int maxLevel = BTreePrinter.maxLevel(root1);

        printNodeInternal(Collections.singletonList(root1), 1, maxLevel);
    }

    private static void printNodeInternal(List<BinaryTreeNode> list, int level, int maxLevel) {
        if (list.isEmpty() || BTreePrinter.isAllElementsNull(list)){
            return;
        }
        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BTreePrinter.printWhitespaces(firstSpaces);

        List<BinaryTreeNode> newNodes = new ArrayList<BinaryTreeNode>();
        for (BinaryTreeNode node : list) {
            if (node != null) {
                System.out.print(node.getValue());
                newNodes.add(node.getLeftChild());
                newNodes.add(node.getRightChild());
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            BTreePrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < list.size(); j++) {
                BTreePrinter.printWhitespaces(firstSpaces - i);
                if (list.get(j) == null) {
                    BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (list.get(j).getLeftChild() != null)
                    System.out.print("/");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(i + i - 1);

                if (list.get(j).getRightChild() != null)
                    System.out.print("\\");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static int maxLevel(BinaryTreeNode node) {
        if (node == null)
            return 0;

        return Math.max(BTreePrinter.maxLevel(node.getLeftChild()), BTreePrinter.maxLevel(node.getRightChild())) + 1;
    }

    private static <T> boolean isAllElementsNull(List<BinaryTreeNode> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }
    public static void main(String[] args) {
//        Scanner scanner=new Scanner(System.in);
//        System.out.printf("hello you dipshit plese choose how many numbers???????");
//        int times = scanner.nextInt();
//        int[] data= new int[times];
//        for (int i = 0; i <times ; i++) {
//            System.out.printf("enter number:");
//            data[i]=scanner.nextInt();
//        }
//        AvlTree avlTree=new AvlTree(data);
//        printNode(avlTree.root);
        AvlTree avt=new AvlTree();
        avt.add(1);
        avt.add(5);
        avt.add(7);
        avt.add(4);
        avt.add(2);
        avt.add(6);
        avt.add(3);
        for (int i = 0; i < 10; i++) {
            System.out.println(i+":"+avt.contains(i));
        }
        for (Iterator i = avt.iterator(); i.hasNext(); ){
            System.out.println(i.next());
        }
    }

}