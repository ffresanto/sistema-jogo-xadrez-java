package application;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PartidaXadrez partidaXadrez = new PartidaXadrez();

        while (true) {
            try {
                UI.clearScreen();
                UI.imprimirPartida(partidaXadrez);
                System.out.println();
                System.out.print("Mover: ");
                PosicaoXadrez source = UI.lerPosicaoXadrez(sc);

                boolean[][] possivelMovimentos = partidaXadrez.possivelMovimentos(source);
                UI.clearScreen();
                UI.imprimirTabuleiro(partidaXadrez.getPecas(), possivelMovimentos);
                System.out.println();
                System.out.print("Para: ");
                PosicaoXadrez target = UI.lerPosicaoXadrez(sc);

                PecaXadrez capturarPeca = partidaXadrez.executarMovimentoXadrez(source, target);
            }
            catch (XadrezException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }
            catch (InputMismatchException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }

        }
    }
}
