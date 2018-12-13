package helpers;

import problem.Automata;
import searchers.utils.Solution;

public class Logger {

    public static void printAndSaveSolution(Solution data, Automata automate) {
        int nFire = 0;
        String solFileName = "random";
        String path = "/home/nicolas-doyer/IdeaProjects/RO_Java/";

        System.out.println(data.getFitness());
        for (int i = 2; i <= 30; i++) {

            nFire = automate.evol(data.getRules(), i);

            // affichage du nombre de fusiliers ayant tiré
            System.out.println("longueur " + i + " : " + nFire);

            // affiche la dynamique dans un fichier au format svg
            automate.exportSVG(i, 2 * i - 2, path + "svg/" + solFileName + "_" + i + ".svg");
        }
    }
}
