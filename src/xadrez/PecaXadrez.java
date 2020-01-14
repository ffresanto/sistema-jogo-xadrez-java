package xadrez;

import jogotabuleiro.Peca;
import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca {

    private Cor cor;
    private int movimentoContagem;

    public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }

    public int getMovimentoContagem(){
        return movimentoContagem;
    }

    public void aumentarMovimentoContagem(){
        movimentoContagem++;
    }

    public void diminuirMovimentoContagem(){
        movimentoContagem--;
    }

    public PosicaoXadrez getPosicaoXadrez(){
        return PosicaoXadrez.fromPosicao(posicao);
    }

    protected boolean temPecaOponente(Posicao posicao){
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
        return p != null  && p.getCor() != cor;
    }


}
