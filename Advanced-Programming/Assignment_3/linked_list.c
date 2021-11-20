/*
	Simple linked list that stores integers
	
	Gabriel Schlam
*/

#include <stdio.h>
#include "linked_list.h"

// Function declarations

node_t * insertHead(node_t * head, int data) {
	node_t * newNode; // Initialize new node
	
	newNode = createNode(data); // Create new node

	newNode->data = data; // Assign data
	newNode->next = head; // Assign head as next
	head = newNode; // Assign new node as head

	return head;
}

node_t * insertAtPosition(node_t * head, int data, int pos) {
	int size = getLength(head); // Store size of list for future validation

	node_t * newNode = createNode(data); // Create new node

	// Check if position given is valid (not less than 0 or bigger than size)
	if (pos < 0 || pos > size) {
		printf("Invalid position for insertion\n");
		return head;
	}

	// If position given is 0, insert at the head
	else if(pos == 0) {
		newNode->next = head; // Assign head as next
		head = newNode; // Assign new node as head
	}

	else {
		node_t * current = head; // Start from head
		int cur_pos = 1;

		while(cur_pos != pos) {
			current = current->next; // Assign to current the next node
			cur_pos++;
		}

		newNode->next = current->next; // New node's next is current's next
		current->next = newNode; // Current's next is new node

	}
	return head;
}

node_t * deleteHead(node_t * head) {
	// If the head is NULL
	if(head == NULL){
		return NULL;
	}

	node_t * temp = head; // Assign to temp the head
	head = head->next; // Head is the actual head's next

	free(temp); // Delete the old head
	return head;
}

node_t * deleteFromPosition(node_t * head, int pos) {
	int size = getLength(head); // Store size of list for future validation

	// Check if position given is valid (not less than 0 or bigger than size)
	if (pos < 0 || pos > size) {
		printf("Invalid position for delete\n");
		return head;
	}

	// If position given is 0, delete head
	else if(pos == 0) {
		head = deleteHead(head);
		return head;
	}

	else {
		node_t * temp = head;
		node_t * current = head;
		int cur_pos = 0;

		while(cur_pos != pos) {
			temp = current;
			current = current->next;
			cur_pos++;
		}

		// The next of the previous node is the next of the node in this pos
		temp->next = current->next; 

		free(current); // Delete the old node in that position

		return head;
	}
}

int searchIterative(node_t * head, int data) {
	int pos = 0;
	node_t * current = head; // Start from head

	// Find the position wanted by getting trought list
	while(current!=NULL) {
		if(current->data == data) { // If you find data, return position
			return pos;
		}
		current = current->next;
		pos++;
	}
	return -1; // If not found, return -1
}

node_t * clear(node_t * head) {
	node_t * current = head; // Start from head
	node_t * next;

	while (current != NULL) {
		next = current->next; // Assign next node of list
		free(current); // Delete node in current position
		current = next; // Current becomes next
	}  
	
	head = NULL; // Clear also the head

	return head;
}

void printList(node_t * head) {
	node_t * current = head; // Start from head

	if (current == NULL) { // If current is null, list is empty
		printf("Linked list is empty\n");
		return;
	}

	while (current->next != NULL) { // Get through the list until there is no more elements
		printf("%d\n", current->data);
		current = current->next;
	}

	printf("%d\n", current->data);
}

void printRecursive(node_t * head) {
	printf("%d\n", head->data); // Print the head, or the current element

	if (head->next == NULL) { // If there is no more elements, stop
		return;
	}
	printRecursive(head->next); // Re enter the function with the next element in list
}

int getLength(node_t * head) {
	int length = 0; 
	node_t * current = head;  // Start from head

	while (current != NULL) { // Get through the list until there is no more elements
		length++; 
		current = current->next; 
	}

	return length; 
}

node_t * createNode(int data) {
	node_t * newNode = (node_t *) malloc(sizeof(node_t)); // Assign memory space for node

	if(newNode == NULL) {
		printf("Error creating a new node\n");
		exit(EXIT_FAILURE);
	}

	newNode->data = data; // Assign data
	newNode->next = NULL; // Assign next to NULL
 
	return newNode;
}
