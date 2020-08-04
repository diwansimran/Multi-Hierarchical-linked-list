/*
 * Author: Simranbanu Roshansha Diwan
 * Dal ID: B00833562
 */
//class linked list
public class LinkedList {
	LinkedList up;	//upper linked list
	LinkedList down;	//down linked list
	Node levelHead;	 //to keep track of head of every level
	//class node
	class Node{
		String data;	//variable to store key
		Node up;	//upper node
		Node down;	//down node
		Node next;	//next node
		Node prev;	//previous node
		
		//constructor for node class with one parameter
		Node(String s){
			data=s;
		}
		//constructor for node class with zero parameter
		Node(){
			
		}
		}
	/*method to create new Node with data initializing functionality*/
	Node newNode(String s){
		return new Node(s);
	}/*method to create new node*/
	Node newNode() {
		return new Node();
	}
}
