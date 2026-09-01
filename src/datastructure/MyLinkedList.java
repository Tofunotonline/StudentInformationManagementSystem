package datastructure;

import java.util.Iterator;

/**
 * Custom Singly Linked List implementation.
 * Used to store and manage collections of data (Students, Subjects, Faculties).
 * This replaces Java's built-in ArrayList/LinkedList.
 */
public class MyLinkedList<T> implements Iterable<T> {

    // Internal node class - each node holds data and a reference to the next node
    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head; // First node in the list
    private int size;  // Number of elements

    public MyLinkedList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Add element to the end of the list.
     */
    public void add(T data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    /**
     * Get element at specific index.
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    /**
     * Remove element at specific index.
     * Returns the removed element.
     */
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        T removedData;
        if (index == 0) {
            removedData = head.data;
            head = head.next;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            removedData = current.next.data;
            current.next = current.next.next;
        }
        size--;
        return removedData;
    }

    /**
     * Remove a specific element from the list.
     * Returns true if found and removed.
     */
    public boolean removeElement(T data) {
        if (head == null) return false;

        if (head.data.equals(data)) {
            head = head.next;
            size--;
            return true;
        }

        Node current = head;
        while (current.next != null) {
            if (current.next.data.equals(data)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Update element at specific index.
     */
    public void set(int index, T data) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.data = data;
    }

    /**
     * Check if the list contains a specific element.
     */
    public boolean contains(T data) {
        Node current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Find index of an element. Returns -1 if not found.
     */
    public int indexOf(T data) {
        Node current = head;
        int index = 0;
        while (current != null) {
            if (current.data.equals(data)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    /**
     * Get the number of elements in the list.
     */
    public int size() {
        return size;
    }

    /**
     * Check if the list is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clear all elements from the list.
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Convert linked list to array for sorting purposes.
     */
    @SuppressWarnings("unchecked")
    public T[] toArray(T[] array) {
        if (array.length < size) {
            array = (T[]) java.lang.reflect.Array.newInstance(
                    array.getClass().getComponentType(), size);
        }
        Node current = head;
        for (int i = 0; i < size; i++) {
            array[i] = current.data;
            current = current.next;
        }
        return array;
    }

    /**
     * Rebuild the list from an array (used after sorting).
     */
    public void fromArray(T[] array, int length) {
        clear();
        for (int i = 0; i < length; i++) {
            add(array[i]);
        }
    }

    /**
     * Iterator to support for-each loops.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
