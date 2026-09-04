package datastructure;

// custom stack (dung de luu lich su Undo/Redo)
// LIFO (Last In, First Out)
public class MyStack<T> {

    // Internal node class
    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node top;  // Top of the stack
    private int size;  // Number of elements

    public MyStack() {
        this.top = null;
        this.size = 0;
    }

    /**
     * Push element onto the top of the stack.
     */
    public void push(T data) {
        Node newNode = new Node(data);
        newNode.next = top;
        top = newNode;
        size++;
    }

    /**
     * Remove and return the top element.
     * Throws exception if stack is empty.
     */
    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty! Cannot pop.");
        }
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    /**
     * Return the top element without removing it.
     * Throws exception if stack is empty.
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty! Cannot peek.");
        }
        return top.data;
    }

    /**
     * Check if the stack is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Get the number of elements in the stack.
     */
    public int size() {
        return size;
    }

    /**
     * Clear all elements from the stack.
     */
    public void clear() {
        top = null;
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Stack[top -> ");
        Node current = top;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(" -> ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
