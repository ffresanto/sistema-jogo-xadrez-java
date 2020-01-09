package application;

import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Main {

    public static void main(String[] args) {

        PartidaXadrez partidaXadrez = new PartidaXadrez();
        UI.imprimirTabuleiro(partidaXadrez.getPecas());
    }
}
