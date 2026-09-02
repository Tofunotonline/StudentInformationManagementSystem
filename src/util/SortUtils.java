package util;

import java.util.Comparator;
import datastructure.MyLinkedList;

/**
 * Custom sorting algorithms.
 * Implements Bubble Sort and Insertion Sort manually.
 */
public class SortUtils {

    /**
     * Bubble Sort - sort linked list using comparator.
     * Simple but O(n^2) complexity.
     */
    public static <T> void bubbleSort(MyLinkedList<T> list, Comparator<T> comparator) {
        int n = list.size();
        if (n <= 1) return;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    // Swap elements
                    T temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swapped = true;
                }
            }
            // If no swap happened, list is already sorted
            if (!swapped) break;
        }
    }

    /**
     * Insertion Sort - sort linked list using comparator.
     * Good for small or nearly sorted data.
     */
    @SuppressWarnings("unchecked")
    public static <T> void insertionSort(MyLinkedList<T> list, Comparator<T> comparator) {
        int n = list.size();
        if (n <= 1) return;

        // Convert to array for easier manipulation
        Object[] arr = new Object[n];
        for (int i = 0; i < n; i++) {
            arr[i] = list.get(i);
        }

        // Insertion sort on array
        for (int i = 1; i < n; i++) {
            Object key = arr[i];
            int j = i - 1;
            while (j >= 0 && comparator.compare((T) arr[j], (T) key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }

        // Put sorted elements back into linked list
        for (int i = 0; i < n; i++) {
            list.set(i, (T) arr[i]);
        }
    }
}
