package xadrez;

import jogotabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

    private Tabuleiro tabuleiro;

    public  PartidaXadrez() {
        tabuleiro = new Tabuleiro(8,8);
        configInicial();
    }

    public PecaXadrez[][] getPecas(){

        PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for (int i = 0 ; i < tabuleiro.getLinhas(); i++){
            for (int j = 0 ; j < tabuleiro.getColunas(); j++){
                mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
            }
        }
        return mat;
    }

    private void lugarNovaPeca(char coluna, int linha, PecaXadrez peca){
        tabuleiro.lugarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
    }
    private void configInicial(){
        lugarNovaPeca('b',6, new Torre(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO));
    }
}
