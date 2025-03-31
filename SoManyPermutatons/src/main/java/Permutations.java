import java.util.*;

/**
 * @author Ivan Ivanov
 **/
public class Permutations {
    private static final List<Solution> solutions = new ArrayList<>();

    public static List<String> singlePermutations(String s) {
        solutions.clear();
        Map<Character, Integer> symbols = splitSymbols(s);
        solutions.add(Solution.createRootSolution(symbols));

        while (solutionsCanBeBorn()) {
            List<Solution> newBornSolutions = bornSolutions();
            solutions.clear();
            solutions.addAll(newBornSolutions);
        }

        return createPermutationsFromSolutions();
    }

    private static Map<Character, Integer> splitSymbols(String string){
        char[] characters = string.toCharArray();
        Map<Character, Integer> charWithFrequency = new HashMap<>();
        for(char character: characters){
            if(charWithFrequency.containsKey(character)){
                int charFrequency = charWithFrequency.get(character);
                charWithFrequency.replace(character, charFrequency + 1);
            }
            else {
                charWithFrequency.put(character, 1);
            }
        }
        return charWithFrequency;
    }

    private static boolean solutionsCanBeBorn() {
        return solutions.getFirst().canBorn();
    }

    private static List<Solution> bornSolutions() {
        List<Solution> newSolutionsIteration = new ArrayList<>();
        for(Solution solution: solutions){
            newSolutionsIteration.addAll(solution.bornSolutions());
        }
        return newSolutionsIteration;
    }

    private static List<String> createPermutationsFromSolutions() {
        List<String> permutations = new ArrayList<>();
        for(Solution solution: solutions){
            permutations.add(solution.getPermutation());
        }
        return permutations;
    }
}

class Solution {
    private final String permutation;
    //char represent symbol, int its frequency
    private final Map<Character, Integer> restSymbols;

    public static Solution createRootSolution(Map<Character, Integer> symbols){
        return new Solution(symbols);
    }

    public Solution(Map<Character, Integer> symbols) {
        this.permutation = "";
        this.restSymbols = new HashMap<>(symbols);
    }

    public Solution(String permutation, Map<Character, Integer> symbols) {
        this.permutation = permutation;
        this.restSymbols = new HashMap<>(symbols);
    }

    public Set<Solution> bornSolutions(){
        Set<Solution> bornSolutions = new HashSet<>();
        for(Character symbol: restSymbols.keySet()){
            Solution bornSolution = bornSolution(symbol);
            bornSolutions.add(bornSolution);
        }
        return bornSolutions;
    }

    private Solution bornSolution(Character symbol) {
        String bornPermutation = permutation + symbol;
        Map<Character, Integer> bornRestSymbols = new HashMap<>(restSymbols);
        int symbolFrequency = bornRestSymbols.get(symbol);
        if(symbolFrequency == 1){
            bornRestSymbols.remove(symbol);
        }
        else {
            bornRestSymbols.replace(symbol, symbolFrequency - 1);
        }
        return new Solution(bornPermutation, bornRestSymbols);
    }

    public boolean canBorn() {
        return !restSymbols.keySet().isEmpty();
    }

    public String getPermutation() {
        return permutation;
    }
}

