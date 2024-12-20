package ru.gil.huffman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class HuffmanExampleTest {

    private HuffmanExample hf;

    @BeforeEach
    void init() {
        hf = new HuffmanExample();
    }

    @ParameterizedTest
    @CsvSource({
            "abacabad,01001100100111",
            "sdkjfhklkjhdkjfhsdvewqweqwxqwaaaaa,01111111010000110111110010110000100001110111101000011011111001111111110010110100001100011000110011010001100101101101101101",
            "aaaaaaaaaaaaaaaaaaaaaaaaaaab,1111111111111111111111111110",
            "zzzzzz,000000",
            "abcabcabcfffffffffffffffffffffffffffffffff,010011000100110001001100111111111111111111111111111111111"
    })
    public void testCodeHuffmanCode(String input, String expected) {
        String actualValue = hf.code(input);

        assertThat(expected).isEqualTo(actualValue);
    }

    @ParameterizedTest
    @ValueSource(strings =  {
            "abacabad",
            "sdkjfhklkjhdkjfhsdvewqweqwxqwaaaaa",
            "aaaaaaaaaaaaaaaaaaaaaaaaaaab",
            "zzzzzz",
            "abcabcabcfffffffffffffffffffffffffffffffff"
    })
    public void testCodeHuffmanDecode(String input) {
        String codeValue = hf.code(input);

        String actualValue = hf.decode(codeValue);

        assertThat(input).isEqualTo(actualValue);
    }
}
