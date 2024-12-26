package ru.gil.qucksort;

import java.util.List;

public class QuickSortExample {
    public static <T extends Comparable<? super T>> void quickSort(List<T> list) {
        sort(list, 0, list.size() - 1);
    }

    private static <T extends Comparable<? super T>> void sort(List<T> list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);
            sort(list, low, pi - 1);
            sort(list, pi + 1, high);
        }
    }

    private static <T extends Comparable<? super T>> int partition(List<T> list, int low, int high) {
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (compare(list.get(j),list.get(high)) < 0) {
                i++;
                T temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }

        T temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);
        return i + 1;
    }

    private static<T extends Comparable<? super T>> int compare(T x, T y) {
        if (x == null && y == null) {
            return 0;
        }
        if (x == null) {
            return 1;
        }
        if (y == null) {
            return -1;
        }
        return x.compareTo(y);
    }
}
