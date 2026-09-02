package util;

import datastructure.MyLinkedList;

/**
 * Custom search algorithms.
 */
public class SearchUtils {

    /**
     * Linear search - find items that match a keyword in a field.
     * Works on unsorted data.
     */
    public static MyLinkedList<String> linearSearch(MyLinkedList<String> lines, String keyword, int fieldIndex) {
        MyLinkedList<String> results = new MyLinkedList<>();
        String lowerKeyword = keyword.toLowerCase();

        for (String line : lines) {
            String[] parts = line.split(",");
            if (fieldIndex < parts.length) {
                if (parts[fieldIndex].trim().toLowerCase().contains(lowerKeyword)) {
                    results.add(line);
                }
            }
        }
        return results;
    }

    /**
     * Simple search through student names or IDs.
     * Returns matching indices from the linked list.
     */
    public static MyLinkedList<Integer> searchByKeyword(MyLinkedList<String[]> dataList, String keyword, int fieldIndex) {
        MyLinkedList<Integer> matchingIndices = new MyLinkedList<>();
        String lower = keyword.toLowerCase();

        for (int i = 0; i < dataList.size(); i++) {
            String[] row = dataList.get(i);
            if (fieldIndex < row.length) {
                if (row[fieldIndex].trim().toLowerCase().contains(lower)) {
                    matchingIndices.add(i);
                }
            }
        }
        return matchingIndices;
    }
}
