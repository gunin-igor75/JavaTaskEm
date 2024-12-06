package ru.gil.huffman;


import java.util.*;
import java.util.stream.Collectors;

public class HuffmanExample {

    private Map<Character, StringBuilder> mapHuffmanCode = new HashMap<>();
    private Map<String, Character> mapHuffmanDecode = new HashMap<>();
    private static final char ZERO = '0';
    private static final char ONE = '1';


    public String code(String message) {
        setupCodeHuffman(message);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            sb.append(mapHuffmanCode.get(message.charAt(i)));
        }
        return sb.toString();
    }

    public String decode(String code) {
        int start = 0;
        int end = 1;
        StringBuilder sb = new StringBuilder(code);
        while (sb.length() != 0) {
            String subSb = sb.substring(start, end);

        }
        return null;
    }

    private void setupCodeHuffman(String message) {
        Map<Character, Integer> frequencies = getFrequencies(message);
        PriorityQueue<Sheet> queue = getQueueSheet(frequencies);
        List<Sheet> listSheet = queue.stream().toList();
        Sheet three = getThree(queue);
        mapHuffmanCode = getCodeHuffman(three, listSheet);
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

    private Map<Character, StringBuilder> getCodeHuffman(Sheet three, List<Sheet> listSheet) {
        Sheet sheet;
        Map<Character, StringBuilder> mapHuffmanCode = new HashMap<>();
        Map<String, Character> mapHuffmanDecode = new HashMap<>();
        if (listSheet.size() == 1 && three.leftChild == null && three.rightChild == null) {
            mapHuffmanCode.put(listSheet.get(0).letter.charAt(0), new StringBuilder().append(ZERO));
            return mapHuffmanCode;
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
        this.mapHuffmanDecode = mapHuffmanDecode;
        return mapHuffmanCode;
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
