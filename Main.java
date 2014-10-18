package pkg3102hw;

import java.util.*;
import java.io.*;

/**
 * @author Hunter Crossett and Steven Truong
 * @class CSC 3102 with Raul Shah Fall 2014
 * @purpose Implement K-ary heap and AVL Trees.
 * @naming Lots of puns. Please do not count off for pun variables.
 */
public class Main {

    public class Node // should i make something like Node(node , key)?
    {

        private int data;
        private Node right = null;
        private Node left = null;
        private Node parent = null;
        private int height;
        private int balance;
        private int size;

        Node(int data) {
            this.data = data;
            this.height = 0;
            this.balance = 0;
            this.size = 1;
        }
    }

    public class kAry {

        //public int[] heap;
        public List<Integer> poop = new ArrayList<Integer>(); // lets try arraylist to add stuff

        public void insert(int x) //key
        {
            poop.add(x); // add element to the end
            int m = poop.size() - 1; // and find the location of that element
            if (m == 0) {
                return;
            }
            int index = ((int) Math.floor(((double) m - 1) / x)); // index of parent
            //compare & swap parent until not bigger
            while (m != 0 && (poop.get(m) < poop.get(index))) // comparing nodes, maybe theres a better way
            {
                int par = ((int) Math.floor(((double) m - 1) / x)); // the new node that will be swapped
                int temp = poop.get(m);	// starting to swap stuff
                poop.set(m, poop.get(par));
                poop.set(par, temp);
                m = par;
            }
        }

        public Integer extractMin() //removes and returns the element of heap with the smallest key
        {
            int min = poop.get(0); // sets min to first node
            if (poop.size() <= 0) {
                return -9999;
            }
            if (poop.size() == 1) {
                poop.remove(0);
            } else {
                poop.set(0, poop.get(poop.size() - 1));
                poop.remove(poop.size() - 1);
            }
            // swap item/key w/ the child that has the lesser key 
            // repeat this until heap-order achieved
            // return the item originally at root                      

            return min;
        }
    }

    public class AVLtree {

        public Node root; // the 1st node
        public Node current;
        

        public void updateBalance(Node x)
        {
            int left, right;
            if (x.left == null)
                left = -1;
            else
                left = x.left.height;
            if (x.right == null)
                right = -1;
            else
                right = x.right.height;
            x.balance = left - right;
            x.height = Math.max(left, right) +1;
        }
        
        public void rotateLeft(Node x) 
        { // can you verify the pointers are correct
		Node y = x.right;
		x.right = null;
		if (y.left != null) 
                {
			Node z = y.left;
			y.left = null;
			z.parent = x;
			x.right = z;
		}
		if (x != root) 
                {
			y.parent = x.parent;
			if (x.parent.left == x) 
				y.parent.left = y;
			else
				y.parent.right = y;
			
		} else 
                {
			root = y;
			y.parent = null;
		}
		x.parent = y;
		y.left = x;
		updateBalance(x);
	}
        
        public void rotateRight(Node x) 
        {
		Node y = x.left;
		x.left = null;
		if (y.right != null) 
                {
			Node z = y.right;
			y.right = null;
			z.parent = x;
			x.left = z;
		}
		if (x != root) 
                {
			y.parent = x.parent;
			if (x.parent.left == x) 
				y.parent.left = y;
			else 
				y.parent.right = y;			
		} 
                else 
                {
			root = y;
			y.parent = null;
		}
		x.parent = y;
		y.right = x;
		updateBalance(x);
	}
        
        public void whereRotate(Node x) {
		// which subtree is tallest
		if (x.balance < 0) {// negative, so we are looking at the right subtree
			if (x.right.balance < 0) // Left Left rotation
				rotateLeft(x);
			else 
                        {
				rotateRight(x.right);
				rotateLeft(x);
			}
		} else 
                {// left subtree
			if (x.left.balance > 0) 
				rotateRight(x);
			else 
                        {
				rotateLeft(x.left);
				rotateRight(x);
			}
		}
	}

        public Node minValue(Node x) // making life ez
        {
            if (x.left != null)
                return minValue(x.left);
            else
                return x;
        }
        
        public Node maxValue(Node x) 
        {
		if (x.right != null) 
			return maxValue(x.right);
		else 
			return x;
	}

        public void insert(int data) //might need a Node*x and int somethign <= not in java!
        {
            Node newNode = new Node(data); // made a new node
            if (root == null) // if theres nothing, add the new nod
            {
                root = newNode; //updates root.
                newNode.height = 1; // root has height 1                
            } else {
                current = root;
                boolean placement = true;
                while (placement) // this keeps going until 1 of these breaks
                {
                    if (newNode.data < current.data) // doing the left
                    {
                        if (current.left == null) // nothing, then add
                        {
                            current.left = newNode;
                            placement = false;
                            newNode.parent = current;
                            newNode.height = newNode.parent.height + 1;
                        } else {
                            current = current.left;
                        }
                    } else {
                        if (current.right == null) // doing the right, nothing then add
                        {
                            current.right = newNode;
                            placement = false;
                            newNode.parent = current;
                            newNode.height = newNode.parent.height + 1;
                        } else {
                            current = current.right;
                        }
                    }
                }
            }
            ///// above might be correct, we just need to handle the 
            ///// bottom stuff now
            // time to fix & check balance factors
            int bal = balanceFact(current);
            //zig zig or left left
            if (bal > 1 && data < current.left.data) {
                return ROTATE_RIGHT(current);  /// HAVE TO RIGHT ROTATE
            }            //zig zig or right right
            if (bal < -1 && data > current.right.data) {
                return ROTATE_LEFT(current);
            }
            //zig zag || left right
            if (bal > 1 && data > current.left.data) {
                current.left = ROTATE_LEFT(current.left);
                return ROTATE_RIGHT(current);
            }
            //zig zag || right left
            if (bal < -1 && data < current.right.data) {
                current.right = ROTATE_RIGHT(current.right);
                return ROTATE_LEFT(current);
            }

        }

        public void updateSize(Node x) 
        {
            do 
            {
                int left = (x.left != null) ? x.left.size : 0; 
                // the ? just means if true first result : false second result
                // i learned this today 10/18/14 on stackoverflow
                // i hope i used this right
                int right = (x.right != null) ? x.right.size : 0;
                x.size = 1 + left + right;
                x = x.parent;
            } while (x != null);
        }

        public boolean search(Node x, int data) 
        {
            if (x != null) 
            {
                if (data > x.data) 
                    return search(x.right, data);
                else if (data < x.data)
                    return search(x.left, data);
                else 
                    return true;                
            }
            return false;
        }
        
        public int rank(int data) 
        {
		Node min = minValue(root);
		Node newNode = goGet(root, data);
		int rank = 0;
		while (newNode != null) 
                {
			Node pr = predecessor(newNode.data);
			if (newNode.left != null) 
                        {
				rank += newNode.left.size + 1;
				newNode = predecessor(minValue(newNode.left).data);
			} 
                        else if (pr != null && pr.left == null) 
                        {
				newNode = pr;
				rank++;
			} 
                        else 
                        {
				newNode = pr;
				rank++;
			}
		}
		return rank;
	}

        public Node select(Node x, int i, int r) 
        { // its just like the slides
            if (i <= root.size) {
                if (x.left != null && x.left.size + r >= i) {
                    // we're going left
                    return select(x.left, i, r);
                } else if ((x.left != null ? x.left.size : 0) + r + 1 < i) {
                    return select(x.right, i, 1 + r + x.left.size);
                } else {
                    return x;
                }
            } else {
                return null;// index i out of bounds
            }
        }

        public Node successor(int data) 
        {
		Node newNode = goGet(root, data);
		if (newNode.right != null) // go down and right
			return minValue(newNode.right);		
		Node y = newNode.parent;
		while (y != null && newNode == y.right) 
                {
			newNode = y;
			y = y.parent;
		}
		return y;
	}

	public Node predecessor(int data) // i need this as well so i can mess 
                //around with rotation
        {
		Node newNode = goGet(root, data);
		if (newNode.left != null) // go down and right
			return maxValue(newNode.left);		
		Node y = newNode.parent;
		while (y != null && newNode == y.left) 
                {
			newNode = y;
			y = y.parent;
		}
		return y;
	}
        
        private Node goGet(Node x, int data) {// i'm trying to make a function
            //where i can just reutn a pointer or somemthing
		if (x != null) 
                {
			if (data > x.data) 
				return goGet(x.right, data);
			else if (data < x.data) 
				return goGet(x.left, data);
			else 
				return x;			
		}
		return x = null;
	}
    }

    public static void main(String[] args) {
        boolean debugAVL = true;
        boolean debugKary = false;
        if (debugAVL) {
            try {
                Scanner MRIscan = new Scanner(new File("AVLtree-input.txt"));
                while (MRIscan.hasNext()) {
                    int[] treesome; //idk what I am doing
                    String[] beans = MRIscan.next().split(" ");
                    if (beans[0].equals("IN")) {
                        treesome.insert(Integer.parseInt(beans[1]));//insert call
                    } else if (beans[0].equals("SR")) {
                        treesome.search(beans[1]);
                    } else if (beans[0].equals("SC")) {
                        treesome.successor(beans[1]);
                    } else if (beans[0].equals("SE")) {
                        treesome.search(beans[1]);
                    } else {
                        treesome.rank(beans[1]);
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("This shit ain't here");
            }
        }

        if (debugKary) {
            try {
                int[] something; //idk. what is life?
                Scanner bodyScan = new Scanner(new File("KARYtree-input.txt")); //may not be right.
                while (bodyScan.hasNext()) {
                    String[] cheese = bodyScan.next().split(" ");
                    if (cheese[0].equals("IN")) {
                        something.insert(cheese[1]);
                    } else {
                        something.extractMin(cheese[1]);
                    }
                }

            } catch (FileNotFoundException f) {
                System.out.println("Now this shit ain't right.");
            }

        }
    }

}
