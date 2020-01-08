package jogotabuleiro;

public class Tabuleiro {

    private int linhas;
    private int coluna;
    private Peca[][] pecas;

    public Tabuleiro(int linhas, int coluna) {
        this.linhas = linhas;
        this.coluna = coluna;
        pecas = new Peca[linhas][coluna];
    }

    public int getLinhas() {
        return linhas;
    }

    public void setLinhas(int linhas) {
        this.linhas = linhas;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }
}
