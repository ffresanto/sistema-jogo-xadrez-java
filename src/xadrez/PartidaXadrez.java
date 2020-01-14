package xadrez;

import jogotabuleiro.Peca;
import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

import java.util.ArrayList;
import java.util.List;

public class PartidaXadrez {

    private int turno;
    private Cor atualJogador;
    private Tabuleiro tabuleiro;

    private List<Peca> pecasNoTabuleiro = new ArrayList<>();
    private List<Peca> pecasCapturadas = new ArrayList<>();

    public  PartidaXadrez() {
        tabuleiro = new Tabuleiro(8,8);
        turno = 1;
        atualJogador = Cor.BRANCO;
        configInicial();
    }

    public int getTurno(){
        return turno;
    }

    public Cor getAtualJogador(){
        return atualJogador;
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

    public boolean[][] possivelMovimentos(PosicaoXadrez sourcePosicao){
        Posicao posicao = sourcePosicao.toPosicao();
        validarSourcePosicao(posicao);
        return tabuleiro.peca(posicao).possivelMovimentos();
    }

    public PecaXadrez executarMovimentoXadrez(PosicaoXadrez sourcePosicao, PosicaoXadrez targetPosicao){
        Posicao source = sourcePosicao.toPosicao();
        Posicao target = targetPosicao.toPosicao();
        validarSourcePosicao(source);
        validarTargetPosicao(source, target);
        Peca capturarPeca = fazerMover(source, target);
        proximoTurno();
        return (PecaXadrez) capturarPeca;
    }

    private Peca fazerMover(Posicao source, Posicao target){
        Peca p = tabuleiro.removerPeca(source);
        Peca capturarPeca = tabuleiro.removerPeca(target);
        tabuleiro.lugarPeca(p, target);

        if (capturarPeca != null){
            pecasNoTabuleiro.remove(capturarPeca);
            pecasCapturadas.add(capturarPeca);
        }
        return capturarPeca;
    }

    private void validarSourcePosicao(Posicao posicao){
        if (!tabuleiro.existePeca(posicao)){
            throw new XadrezException("Nao existe peça na posição de origem");
        }
        if (atualJogador != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()){
            throw new XadrezException("A peca escolhida nao e sua");
        }
        if (!tabuleiro.peca(posicao).temPossivelMovimento()){
            throw new XadrezException("Nao existe movimento possivel para peca de origem");
        }
    }

    private void validarTargetPosicao(Posicao source, Posicao target){
        if (!tabuleiro.peca(source).possivelMovimento(target)){
            throw new XadrezException("A peca escolhida nao pode se mover para posicao de destino");
        }
    }

    private void proximoTurno(){
        turno++;
        atualJogador = (atualJogador == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }

    private void lugarNovaPeca(char coluna, int linha, PecaXadrez peca){
        tabuleiro.lugarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
        pecasNoTabuleiro.add(peca);
    }

    private void configInicial(){
        lugarNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCO));

        lugarNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETO));
        lugarNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETO));
        lugarNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETO));
        lugarNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETO));
        lugarNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETO));
        lugarNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETO));
    }
}
