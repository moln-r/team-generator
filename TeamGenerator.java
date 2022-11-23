import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class TeamGenerator {

    // tierOne list will contain players who must not be in the same team, because they would be too powerful together
    // tierTwo are players who, when put together with a t1 would make it unfair
    // tierThree are players that almost never play
    // the idea is to match t1 with t3 until one of them is fully consumed and then match t2 with t3 or t1 or each other?
    public static void generateTeam(List<String> tierOne, List<String> tierTwo, List<String> tierThree) {
        if (isPlayerCountOdd(tierOne, tierTwo, tierThree)) {
            System.out.println("Get one more player (the total count of players is odd)!");
            return;
        }

        List<Team> teams = new ArrayList<>();
        int expectedAmountOfTeams = (totalAmountOfPlayers(tierOne, tierTwo, tierThree) / 2);

        while (teams.size() < expectedAmountOfTeams) {
            String player1 = firstRandomPlayer(tierOne, tierTwo, tierThree);
            String player2 = firstRandomPlayer(tierThree, tierTwo, tierOne);
            teams.add(new Team(player1, player2));
        }

        System.out.println("Teams:");
        teams.forEach(System.out::println);
    }

    private static boolean isPlayerCountOdd(Collection... collections) {
        return (totalAmountOfPlayers(collections) % 2) != 0;
    }

    private static int totalAmountOfPlayers(Collection... collections) {
        return Arrays.stream(collections).map(Collection::size).mapToInt(Integer::valueOf).sum();
    }

    private static String firstRandomPlayer(List<String>... lists) {
        List<String> list = Arrays.stream(lists)
                .filter(possibleList -> !possibleList.isEmpty())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No suitable player found"));

        return list.remove(new Random().nextInt(list.size()));
    }

    private record Team(String player1, String player2) {
        @Override
        public String toString() {
            return player1 + '-' + player2;
        }

    }

}
