package application;

import jogotabuleiro.Peca;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PartidaXadrez partidaXadrez = new PartidaXadrez();
        List<PecaXadrez> capturado = new ArrayList<>();

        while (!partidaXadrez.getCheckMate()) {
            try {
                UI.clearScreen();
                UI.imprimirPartida(partidaXadrez, capturado);
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

                if (capturarPeca != null){
                    capturado.add(capturarPeca);
                }
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
        UI.clearScreen();
        UI.imprimirPartida(partidaXadrez, capturado);
    }
}
