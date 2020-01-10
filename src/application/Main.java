package application;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PartidaXadrez partidaXadrez = new PartidaXadrez();

        while (true) {
            UI.imprimirTabuleiro(partidaXadrez.getPecas());
            System.out.println();
            System.out.print("Source: ");
            PosicaoXadrez source = UI.lerPosicaoXadrez(sc);

            System.out.println();
            System.out.print("Target: ");
            PosicaoXadrez target = UI.lerPosicaoXadrez(sc);

            PecaXadrez capturarPeca = partidaXadrez.executarMovimentoXadrez(source, target);
        }
    }
}
