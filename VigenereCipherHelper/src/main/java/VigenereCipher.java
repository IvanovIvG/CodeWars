/**
 * @author Ivan Ivanov
 **/
public class VigenereCipher {
    private final char[] key;
    private final char[] alphabet;
    
    public VigenereCipher(String key, String abc) {
        this.key = key.toCharArray();
        this.alphabet = abc.toCharArray();
    }

    public String encode(String str) {
        char[] open = str.toCharArray();
        char[] close = new char[open.length];
        for(int i = 0; i < open.length; i++){
            if(charNotInAlphabet(open[i])){
                close[i] = open[i];
                continue;
            }

            char openChar = open[i];
            char keyChar = key[Math.floorMod(i, key.length)];
            close[i] = encrypt(openChar, keyChar);
        }
        return String.copyValueOf(close);
    }

    public String decode(String str) {
        char[] close = str.toCharArray();
        char[] open = new char[close.length];

        for(int i = 0; i < close.length; i++){
            if(charNotInAlphabet(close[i])){
                open[i] = close[i];
                continue;
            }

            char closeChar = close[i];
            char keyChar = key[Math.floorMod(i, key.length)];
            open[i] = decrypt(closeChar, keyChar);
        }
        return String.copyValueOf(open);
    }

    private boolean charNotInAlphabet(char c) {
        return !charIsInAlphabet(c);
    }

    private boolean charIsInAlphabet(char character) {
        for(char alphabetCharacter: alphabet){
            if (character == alphabetCharacter)
                return true;
        }
        return false;
    }
    private char encrypt(char openChar, char keyChar) {
        int open = charIndexInAlphabet(openChar);
        int key = charIndexInAlphabet(keyChar);
        int close = Math.floorMod(open + key, alphabet.length);
        return alphabet[close];
    }

    private char decrypt(char closeChar, char keyChar) {
        int close = charIndexInAlphabet(closeChar);
        int key = charIndexInAlphabet(keyChar);
        int open = Math.floorMod(close - key, alphabet.length);
        return alphabet[open];
    }

    private int charIndexInAlphabet(char character){
        for (int i = 0; i < alphabet.length; i++) {
            if(character == alphabet[i])
                return i;
        }
        return 0;
    }
}

