package xadrez.pecas;

import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Cavalo extends PecaXadrez {

    public Cavalo(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString() {
        return "C";
    }

    private boolean podeMover(Posicao posicao){
        PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
        return p == null || p.getCor() != getCor();
    }

    @Override
    public boolean[][] possivelMovimentos() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 2);
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(posicao.getLinha() - 2 , posicao.getColuna() - 1);
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(posicao.getLinha() - 2, posicao.getColuna() + 1);
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 2);
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 2);
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(posicao.getLinha() + 2, posicao.getColuna() + 1);
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(posicao.getLinha() + 2, posicao.getColuna() - 1);
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }


        p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 2);
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        return mat;
    }

}
