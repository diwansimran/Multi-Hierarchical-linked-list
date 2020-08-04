/*
 * Author: Simranbanu Roshansha Diwan
 * Dal ID: B00833562
 */

import java.io.*;
import java.util.*;

public class ListHierarchy {
	static int count=0;		//to keep track that it is the first ever element being added to structure or not
	static int levels=1;	//to keep track number of levels created
	Coin randomCoin;	//to store an object of coin implementation class
	LinkedList lbottom;		//bottom Linked list
	LinkedList currentLL;		//current Linked list
	LinkedList ltemp;		//temporary linked list
	LinkedList ltop;		//topmost linked list

	//constructor of ListHierarchy
	public ListHierarchy(Coin flip)
	{	try {
			randomCoin=flip;
		//	flipValue=randomCoin.flip();	
		}catch(Exception e) {
			
		}
	}
	
	/*function to add value into hierarchy*/
	boolean add(String key){
		try {
			Boolean addedAtBottom=false;	//to check that value is added at the bottom most list or not
			LinkedList.Node newNode=null;	//new Node to add
			LinkedList.Node current=null;	//works as temporary node
			
			//initialize newNode and current nodes 
			if(lbottom!=null)
			{
				newNode=lbottom.newNode(key);
				current=lbottom.levelHead;
			}
			//if bottom is empty create bottom list and create head
			if(lbottom==null){
				lbottom = new LinkedList();
				lbottom.levelHead=lbottom.newNode(key);
				addedAtBottom=true;
				if(lbottom.up==null) {
					 ltop=lbottom;
				 }
				newNode=lbottom.levelHead;
			}//if new value to be added is smaller than current head value then make new value head
			else if(lbottom.levelHead.data.compareToIgnoreCase(newNode.data)>0){
				 lbottom.levelHead.prev=newNode;
				 newNode.next=lbottom.levelHead;
				 lbottom.levelHead=newNode;
				 newNode=lbottom.levelHead;
				 addedAtBottom=true;
				 if(lbottom.up==null) {
					 ltop=lbottom;
				 }
			}//if new value to be added is already in the head of list then don't add it and return true
			else if(lbottom.levelHead.data.compareToIgnoreCase(newNode.data)==0)
			{
				return true;
			}//if value to be added in other than head position
			else {
				current=lbottom.levelHead;
				while(current.next!=null && current.next.data.compareToIgnoreCase(newNode.data)<=0)
				{
					current=current.next;
				}
				//return true if value is already in the list
				if(current.data.compareToIgnoreCase(newNode.data)==0)
				{
					return true;
				}//add value at last position
				else if(current.next==null){
					newNode.prev=current;
					current.next=newNode;
					addedAtBottom=true;
				}//add value in some middle position
				else
				{
					newNode.prev=current;
					newNode.next=current.next;
					current.next.prev=newNode;
					current.next=newNode;
					addedAtBottom=true;
				}
				if(lbottom.up==null) {
					 ltop=lbottom;
					 addedAtBottom=true;
				}
			}
			currentLL = new LinkedList();
			//if value is added in bottom list then add it to upper level according to random value 	
			if(addedAtBottom==true) {
				int levelCount=0;
				LinkedList ll=lbottom;
				//count number of levels
				while(ll!=null) {
					levelCount++;
					ll=ll.up;
				}
				int putUp; //to store random value of coin flip
				putUp=randomCoin.flip();	//flip to get next random value
				//giving proper links between bottom list and it's upper list
				if(putUp==1 && levelCount==1) {
					lbottom.up=currentLL;
					currentLL.down=lbottom;
				}
				currentLL=lbottom.up;
				int keyCount=1;   //number of times key has been added to upper levels
				LinkedList.Node newnode;	//temporary node for upperList
				LinkedList.Node temp;	//temporary node
				//repeat until coin gives value 1 while flip
				while(putUp==1){
					//adding new level to hierarchy 
					//if number of times key has been added to upper levels increases number of levels then we need to add new level
					if(keyCount>=levelCount) {
						newnode=currentLL.newNode(key);
						currentLL.levelHead=newnode;
						temp=currentLL.levelHead;
						while(temp.next!=null)
						{
							if(temp.data.compareToIgnoreCase(key)==0) {
								temp.down=newNode;
								newNode.up=temp;
								break;
							}
							temp=temp.next;
						}
						//providing proper links between upper level elements and lower level elements
						ltemp=new LinkedList();
						ltemp=currentLL.down;
						ltemp.up=currentLL;
						newnode.down=newNode;
						newNode.up=newNode;
						ltop=currentLL;
						levelCount++;
						keyCount++;
						break;  //we are only allowed to add one new level after reaching  at top most level
					}//if we done need to add new level, just add value to upper level
					else {
						newnode=currentLL.newNode(newNode.data);
						temp=currentLL.levelHead;
						//if value needs to be put in head position
						if(currentLL.levelHead.data.compareToIgnoreCase(newnode.data)>0){
							newnode.next=currentLL.levelHead;
							currentLL.levelHead.prev=newnode;
							newnode.prev=null;
							currentLL.levelHead=newnode;
							newnode.down=newNode;
							newNode.up=newNode;
						}//value needs to be put other position than head
						else {
							while(temp.next!=null && temp.next.data.compareToIgnoreCase(newnode.data)<0) {
								temp=temp.next;
							}
							//add value at the end
							if(temp.next==null) {	
								temp.next=newnode;
								newnode.prev=temp;
								newnode.next=null;
								newnode.down=newNode;
								newNode.up=newnode;
							}//add value in somewhere middle
							else {
								newnode.next=temp.next;
								newnode.prev=temp;
								temp.next.prev=newnode;
								temp.next=newnode;
								newnode.down=newNode;
								newNode.up=newnode;
							}
						}
						currentLL.down.up=currentLL;
						keyCount++;
					}
					//linking levels properly
					if(levelCount==2) {
						lbottom.up=currentLL;
						currentLL.down=lbottom;
					}
					//managing head
					if(currentLL.down.levelHead.data.compareToIgnoreCase(key)==0)
					{
						currentLL.down.levelHead=newNode;
					}
					//to again get return value of flip to decide to move value to upper level or not
					putUp=randomCoin.flip();
					//if we don't need to add value to upper level then break
					if(putUp==0) {
						break;
					}
					//managing links between levels
					LinkedList oldList=new LinkedList();
					if(keyCount>=levelCount) {
						oldList.down=currentLL;
						currentLL.up=oldList;
					}
					newNode=newnode;
					currentLL=currentLL.up;	
				}
			}else {
				return false;
			}
			return true;
		}catch(Exception e) {
			return false;
		}
	}
		
	/*printing structure*/
	void print(){
		try {
			LinkedList.Node current=lbottom.levelHead; //temporary node for traversing purpose
			LinkedList tempList=ltop; //temporary list for traversing purpose
			while(tempList!=null)
			{
				current=tempList.levelHead;
				while(current!=null)
				{
					System.out.print(current.data+" ");
					current=current.next;
				}
				tempList=tempList.down;
				System.out.println();
			}
		}catch(Exception e) {
			
		}
	}
	
	/*method to find key in the list*/
	Boolean find(String key) {
		try {
			Boolean found=false;	//variable to keep track that value has been found or not
			LinkedList currentList=ltop;	//we need to start search from top so assigning top level to temporary linked list
			LinkedList.Node currentNode=currentList.levelHead;	//assigning head to temporary node
			while(found==false) {
				//if key has been found
				if(currentNode.data.compareToIgnoreCase(key)==0) {
					found=true;
					break;
				} //if we are in bottom most list
				//logics for traversing and finding in proper direction
				if(currentList.down==null) {
					if(currentNode.prev==null && currentNode.data.compareToIgnoreCase(key)>0) {
						found=false;
						break;
					}else if(currentNode.next==null && currentNode.data.compareToIgnoreCase(key)<0) {
						found=false;
						break;
					}else if(currentNode.prev.data.compareToIgnoreCase(key)<0 && currentNode.data.compareToIgnoreCase(key)>0) {
						found=false;
						break;
					}else if(currentNode.next.data.compareToIgnoreCase(key)>0 && currentNode.data.compareToIgnoreCase(key)<0) {
						found=false;
						break;
					}else if(currentNode.next.data.compareToIgnoreCase(key)<=0) {
						currentNode=currentNode.next;
					}else if(currentNode.prev.data.compareToIgnoreCase(key)>=0) {
						currentNode=currentNode.prev;
					}
				}//if we are on any other list then bottom most
				//logics for traversing and finding in proper direction
				else {
					if(currentNode.prev==null && currentNode.data.compareToIgnoreCase(key)>0) {
						currentList=currentList.down;
						currentNode=currentNode.down;
					}else if(currentNode.next==null && currentNode.data.compareToIgnoreCase(key)<0) {
						currentList=currentList.down;
						currentNode=currentNode.down;
					}else if(currentNode.data.compareToIgnoreCase(key)<0 && currentNode.next!=null && currentNode.next.data.compareToIgnoreCase(key)>0) {
						currentList=currentList.down;
						currentNode=currentNode.down;
					}else if(currentNode.data.compareToIgnoreCase(key)>0 && currentNode.prev!=null &&currentNode.prev.data.compareToIgnoreCase(key)<0) {
						currentList=currentList.down;
						currentNode=currentNode.down;
					}else if(currentNode.next==null && currentNode.data.compareToIgnoreCase(key)>0) {
						currentNode=currentNode.prev;
					}else if(currentNode.prev==null && currentNode.data.compareToIgnoreCase(key)<0) {
						currentNode=currentNode.next;
					}else if(currentNode.next.data.compareToIgnoreCase(key)<=0 ) {
						currentNode=currentNode.next;
					}else if(currentNode.prev.data.compareToIgnoreCase(key)>=0 ) {
						currentNode=currentNode.prev;
					}
				}
			}
			return found;
		}catch(Exception e) {
			return false;
		}
	}
}