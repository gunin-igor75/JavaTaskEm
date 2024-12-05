package ru.gil.qucksort.huffman;


import java.util.*;
import java.util.stream.Collectors;

public class HuffmanExample {

    private final String message;
    private Map<String, StringBuilder> map = new HashMap<>();

    public HuffmanExample(String message) {
        this.message = message;
    }


    public String code() {
        setupCodeHuffman();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            sb.append(map.get(String.valueOf(message.charAt(i))));
        }
        return sb.toString();
    }

    private void setupCodeHuffman() {
        Map<Character, Integer> frequencies = getFrequencies(message);
        PriorityQueue<Sheet> queue = getQueueSheet(frequencies);
        List<Sheet> listSheet = queue.stream().toList();
        Sheet three = getThree(queue);
        map = getCodeHuffman(three, listSheet);
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

    private Map<String, StringBuilder> getCodeHuffman(Sheet three, List<Sheet> listSheet) {
        Sheet sheet;
        Map<String, StringBuilder> mapHuffman = new HashMap<>();
        if (listSheet.size() == 1 && three.leftChild == null && three.rightChild == null) {
            mapHuffman.put(listSheet.get(0).letter.toString(), new StringBuilder().append('0'));
            return mapHuffman;
        }
        for (int i = listSheet.size() - 1; i >= 0; i--) {
            sheet = three;
            StringBuilder code = new StringBuilder();
            while (sheet.leftChild != null && sheet.rightChild != null) {
                if (three.letter == listSheet.get(i).letter) {
                    break;
                } else if (sheet.leftChild.letter.toString().contains(listSheet.get(i).letter)) {
                    sheet = sheet.leftChild;
                    code.append('0');
                } else if (sheet.rightChild.letter.toString().contains(listSheet.get(i).letter)) {
                    sheet = sheet.rightChild;
                    code.append('1');
                }
            }
            mapHuffman.put(listSheet.get(i).letter.toString(), code);
        }
        return mapHuffman;
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
