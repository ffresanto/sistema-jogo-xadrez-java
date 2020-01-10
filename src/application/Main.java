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
                UI.imprimirTabuleiro(partidaXadrez.getPecas());
                System.out.println();
                System.out.print("Source: ");
                PosicaoXadrez source = UI.lerPosicaoXadrez(sc);

                System.out.println();
                System.out.print("Target: ");
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
            catch (NullPointerException e){
                System.out.println("Nao existe peca na posicao de origem");
                sc.nextLine();
            }

        }
    }
}
