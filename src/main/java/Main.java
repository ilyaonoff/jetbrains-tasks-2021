import java.util.Objects;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class Main {
    static final String IMPOSSIBLE_MESSAGE = "Impossible";

    public static void main(String[] args) {
        //read data
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        String[] names = new String[n + 1];
        scan.useDelimiter("(%|\\s+)");
        for (int i = 0; i < n; i++) {
            String name = scan.next();
            int pos = scan.nextInt();
            if (names[pos] != null) {
                System.out.println(IMPOSSIBLE_MESSAGE);
                return;
            }
            names[pos] = name;
        }

        scan.close();

        //build a graph of constraints
        AlphabetGraph graph = new AlphabetGraph();

        for (int i = 1; i < n; i++) {
            boolean allEquals = true;
            for (int j = 0; j < Math.min(names[i].length(), names[i + 1].length()); j++) {
                char first = names[i].charAt(j);
                char second = names[i + 1].charAt(j);
                if (first != second) {
                    graph.addEdge(first, second);
                    allEquals = false;
                    break;
                }
            }
            if (allEquals && names[i].length() > names[i + 1].length()) {
                System.out.println(IMPOSSIBLE_MESSAGE);
                return;
            }
        }

        //If topological order doesn't exist, then the answer is "Impossible"
        //otherwise answer is the topological order
        System.out.println(Objects.requireNonNullElse(graph.topSort(), IMPOSSIBLE_MESSAGE));
    }

    static private class AlphabetGraph {
        private enum Color {
            UNUSED,
            USING,
            USED
        }

        Map<Character, Set<Character>> graph = new HashMap<>();

        public AlphabetGraph() {
            for (char i = 'a'; i <= 'z'; i++) {
                graph.put(i, new HashSet<>());
            }
        }

        public void addEdge(Character from, Character to) {
            graph.get(from).add(to);
        }

        public String topSort() {
            Map<Character, Color> used = new HashMap<>();
            for (char i = 'a'; i <= 'z'; i++) {
                used.put(i, Color.UNUSED);
            }
            StringBuilder builder = new StringBuilder();
            for (char i = 'a'; i <= 'z'; i++) {
                if (used.get(i) == Color.UNUSED) {
                    if (!topSortImpl(i, used, builder)) {
                        return null;
                    }
                }
            }
            return builder.reverse().toString();
        }

        private boolean topSortImpl(Character vertex, Map<Character, Color> used, StringBuilder res) {
            used.put(vertex, Color.USING);
            for (Character nextVertex : graph.get(vertex)) {
                if (used.get(nextVertex) == Color.USING) {
                    return false;
                }
                if (used.get(nextVertex) == Color.UNUSED) {
                    if (!topSortImpl(nextVertex, used, res)) {
                        return false;
                    }
                }
            }
            used.put(vertex, Color.USED);
            res.append(vertex);
            return true;
        }
    }
}
