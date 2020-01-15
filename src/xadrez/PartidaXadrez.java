package xadrez;

import jogotabuleiro.Peca;
import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PartidaXadrez {

    private int turno;
    private Cor atualJogador;
    private Tabuleiro tabuleiro;
    private boolean check;
    private boolean checkMate;

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

    public boolean getCheck(){
        return check;
    }

    public boolean getCheckMate(){
        return checkMate;
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

        if (testeCheck(atualJogador)){
            desfazerMovimento(source, target, capturarPeca);
            throw new XadrezException("Voce nao pode se colocar em xeque");
        }

        check = (testeCheck(oponente(atualJogador))) ? true : false;

        if (testeCheckMate(oponente(atualJogador))){
            checkMate = true;
        }
        else {
        proximoTurno();
        }
        return (PecaXadrez) capturarPeca;
    }

    private Peca fazerMover(Posicao source, Posicao target){
        PecaXadrez p =(PecaXadrez) tabuleiro.removerPeca(source);
        p.aumentarMovimentoContagem();
        Peca capturarPeca = tabuleiro.removerPeca(target);
        tabuleiro.lugarPeca(p, target);

        if (capturarPeca != null){
            pecasNoTabuleiro.remove(capturarPeca);
            pecasCapturadas.add(capturarPeca);
        }
        return capturarPeca;
    }

    private void desfazerMovimento(Posicao source, Posicao target, Peca capturarPeca){
        PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(target);
        p.diminuirMovimentoContagem();
        tabuleiro.lugarPeca(p, source);

        if (capturarPeca != null){
            tabuleiro.lugarPeca(capturarPeca, target);
            pecasCapturadas.remove(capturarPeca);
            pecasNoTabuleiro.add(capturarPeca);
        }
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

    private Cor oponente(Cor cor){
        return (cor == Cor.BRANCO) ?  Cor.PRETO : Cor.BRANCO;
    }

    private PecaXadrez rei (Cor cor){
        List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
        for (Peca p : list){
            if (p instanceof Rei) {
                return (PecaXadrez) p;
            }
        }
        throw new IllegalStateException("Nao existe o " + cor + " Rei no tabuleiro");
    }

    private boolean testeCheck(Cor cor){
        Posicao reiPosicao = rei(cor).getPosicaoXadrez().toPosicao();
        List<Peca> oponentePecas = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
        for (Peca p : oponentePecas){
            boolean[][] mat = p.possivelMovimentos();
            if (mat[reiPosicao.getLinha()][reiPosicao.getColuna()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testeCheckMate(Cor cor){
        if (!testeCheck(cor)){
            return false;
        }
        List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
        for (Peca p : list){
            boolean[][] mat = p.possivelMovimentos();
            for (int i = 0 ; i < tabuleiro.getLinhas(); i++){
                for (int j = 0 ; j < tabuleiro.getColunas(); j++){
                    if (mat[i][j]){
                        Posicao source = ((PecaXadrez)p).getPosicaoXadrez().toPosicao();
                        Posicao target = new Posicao(i, j);
                        Peca capturadaPeca = fazerMover(source, target);
                        boolean testeCheck = testeCheck(cor);
                        desfazerMovimento(source, target, capturadaPeca);
                        if (!testeCheck){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void lugarNovaPeca(char coluna, int linha, PecaXadrez peca){
        tabuleiro.lugarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
        pecasNoTabuleiro.add(peca);
    }

    private void configInicial(){
        lugarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO));

        lugarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
        lugarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
        lugarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO));
        lugarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
        lugarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
        lugarNovaPeca('a',7, new Peao(tabuleiro, Cor.PRETO));
        lugarNovaPeca('b',7, new Peao(tabuleiro, Cor.PRETO));
        lugarNovaPeca('c',7, new Peao(tabuleiro, Cor.PRETO));
        lugarNovaPeca('d',7, new Peao(tabuleiro, Cor.PRETO));
        lugarNovaPeca('e',7, new Peao(tabuleiro, Cor.PRETO));
        lugarNovaPeca('f',7, new Peao(tabuleiro, Cor.PRETO));
        lugarNovaPeca('g',7, new Peao(tabuleiro, Cor.PRETO));
        lugarNovaPeca('h',7, new Peao(tabuleiro, Cor.PRETO));
    }
}
