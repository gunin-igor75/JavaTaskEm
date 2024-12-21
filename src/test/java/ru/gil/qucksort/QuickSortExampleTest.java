package ru.gil.qucksort;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuickSortExampleTest {


    @TestFactory
    Collection<DynamicTest> testQuickSort() {
        List<List<Integer>> lists = List.of(
                Arrays.asList(null,4, 0, 1,null, 2, 3, -1),
                Arrays.asList(1, 0, 4, 2, -1, 3, null, null),
                Arrays.asList(null, null, 2, 3, 1, 4, 0, -1)
        );

        List<Integer> expected = Arrays.asList(-1, 0, 1, 2, 3, 4, null, null);
        return lists.stream()
                .map(list ->
                        DynamicTest.dynamicTest(
                                "quick_sort_example",
                                () -> {
                                    QuickSortExample.quickSort(list);
                                    assertThat(list).containsExactlyElementsOf(expected);
                                }
                        )
                ).toList();
    }
}