package ru.gil.huffman;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class HuffmanExample {
    private final Map<Character, StringBuilder> mapHuffmanCode = new HashMap<>();
    private final Map<String, Character> mapHuffmanDecode = new HashMap<>();
    private final char ZERO = '0';
    private final char ONE = '1';
    private final int START_STRING = 0;
    private String EMPTY = "";

    public String code(String message) {
        setupCodeHuffman(message);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            sb.append(mapHuffmanCode.get(message.charAt(i)));
        }
        return sb.toString();
    }

    public String decode(String code) {
        int end = 1;
        StringBuilder sb = new StringBuilder(code);
        StringBuilder ans = new StringBuilder();
        while (!sb.isEmpty()) {
            String currentKey = sb.substring(START_STRING, end);
            if (mapHuffmanDecode.containsKey(currentKey)) {
                ans.append(mapHuffmanDecode.get(currentKey));
                sb.replace(START_STRING, end, EMPTY);
                end = 1;
            } else {
                end++;
            }
        }
        return ans.toString();
    }

    private void setupCodeHuffman(String message) {
        Map<Character, Integer> frequencies = getFrequencies(message);
        PriorityQueue<Sheet> queue = getQueueSheet(frequencies);
        List<Sheet> listSheet = queue.stream().toList();
        Sheet three = getThree(queue);
        createMapsCodeHuffman(three, listSheet);
    }

    private Map<Character, Integer> getFrequencies(String data) {
        return data.chars()
                .mapToObj(obj -> (char) obj)
                .collect(Collectors.toMap(key -> key, val -> 1, Integer::sum));
    }

    private PriorityQueue<Sheet> getQueueSheet(Map<Character, Integer> map) {
        return map.entrySet()
                .stream()
                .map(entry -> new Sheet(
                        entry.getValue(),
                        new StringBuilder().append(entry.getKey()),
                        null,
                        null
                ))
                .collect(Collectors.toCollection(PriorityQueue::new));
    }

    private Sheet getThree(PriorityQueue<Sheet> queue) {
        while (queue.size() != 1) {
            Sheet first = queue.remove();
            Sheet second = queue.remove();
            queue.add(new Sheet(
                    first.value + second.value,
                    new StringBuilder().append(first.letter).append(second.letter),
                    first,
                    second
            ));
        }
        return queue.remove();
    }

    private void createMapsCodeHuffman(Sheet three, List<Sheet> listSheet) {
        Sheet sheet;
        if (listSheet.size() == 1 && three.leftChild == null && three.rightChild == null) {
            mapHuffmanCode.put(listSheet.get(0).letter.charAt(0), new StringBuilder().append(ZERO));
            mapHuffmanDecode.put(String.valueOf(ZERO), listSheet.get(0).letter.charAt(0));
            return;
        }
        for (int i = listSheet.size() - 1; i >= 0; i--) {
            sheet = three;
            StringBuilder code = new StringBuilder();
            while (sheet.leftChild != null && sheet.rightChild != null) {
                if (three.letter == listSheet.get(i).letter) {
                    break;
                } else if (sheet.leftChild.letter.toString().contains(listSheet.get(i).letter)) {
                    sheet = sheet.leftChild;
                    code.append(ZERO);
                } else if (sheet.rightChild.letter.toString().contains(listSheet.get(i).letter)) {
                    sheet = sheet.rightChild;
                    code.append(ONE);
                }
            }
            mapHuffmanCode.put(listSheet.get(i).letter.charAt(0), code);
            mapHuffmanDecode.put(code.toString(), listSheet.get(i).letter.charAt(0));
        }
    }

    private static class Sheet implements Comparable<Sheet> {
        private final Integer value;
        private final StringBuilder letter;
        private final Sheet leftChild;
        private final Sheet rightChild;

        public Sheet(int value, StringBuilder letter, Sheet leftChild, Sheet rightChild) {
            this.value = value;
            this.letter = letter;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }

        @Override
        public int compareTo(Sheet o) {
            return this.value - o.value;
        }

        @Override
        public String toString() {
            return "Sheet{" +
                    "value=" + value;
        }
    }
}
