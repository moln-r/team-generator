import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class TeamGenerator {

    // tierOne list will contain players who must not be in the same team, because they would be too powerful together
    // tierTwo is the rest
    public static void generateTeam(List<String> tierOne, List<String> tierTwo) {
        if (isPlayerCountOdd(tierOne, tierTwo)) {
            System.out.println("Get one more player (the total count of players is odd)!");
        }

        List<Team> teams = new ArrayList<>();

        ArrayList<String> largerCollection;
        ArrayList<String> smallerCollection;
        if (tierOne.size() > tierTwo.size()) {
            largerCollection = new ArrayList<>(tierOne);
            smallerCollection = new ArrayList<>(tierTwo);
        } else {
            largerCollection = new ArrayList<>(tierTwo);
            smallerCollection = new ArrayList<>(tierOne);
        }

        // first we find teammates for players from the list with fewer elements
        while (!smallerCollection.isEmpty()) {
            String player1 = randomPlayer(smallerCollection);
            String player2 = randomPlayer(largerCollection);
            smallerCollection.remove(player1);
            largerCollection.remove(player2);
            teams.add(new Team(player1, player2));
        }

        // then we pair the rest of the players in the other list
        while (largerCollection.size() >= 2) {
            String player1 = randomPlayer(largerCollection);
            String player2 = randomPlayer(largerCollection);
            if (player1.equals(player2)) {
                continue;
            }
            largerCollection.remove(player1);
            largerCollection.remove(player2);
            teams.add(new Team(player1, player2));
        }

        System.out.println("Teams:");
        teams.forEach(System.out::println);
    }

    private static boolean isPlayerCountOdd(Collection x, Collection y) {
        return ((x.size() + y.size()) % 2) != 0;
    }

    private static String randomPlayer(List<String> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    private record Team(String player1, String player2) {
        @Override
        public String toString() {
            return player1 + '-' + player2;
        }

    }

}
