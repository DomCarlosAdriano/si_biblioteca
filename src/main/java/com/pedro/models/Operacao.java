package com.pedro.models;

import java.util.Date;
import com.pedro.models.Pessoa;

public class Operacao {

    private int id;
    private int funcionarioLocacaoId;
    private int funcionarioDevolucaoId;
    private Pessoa pessoa;
    private int exemplarId;
    private TipoOperacao tipoOperacao;
    private Date dataLocacao;
    private Date dataDevolucao;
    private Date dataDevolvido;

    public Operacao(
            int funcionarioLocacaoId, Pessoa pessoa,
            int exemplarId, TipoOperacao tipoOperacao, Date dataLocacao,
            Date dataDevolucao
    ) {
        this.funcionarioLocacaoId = funcionarioLocacaoId;
        this.pessoa = pessoa;
        this.exemplarId = exemplarId;
        this.tipoOperacao = tipoOperacao;
        this.dataLocacao = dataLocacao;
        this.dataDevolucao = dataDevolucao;

    }

    public int getId() {
        return id;
    }

    public int getFuncionarioLocacaoId() {
        return funcionarioLocacaoId;
    }

    public void setFuncionarioLocacaoId(int funcionarioLocacaoId) {
        this.funcionarioLocacaoId = funcionarioLocacaoId;
    }

    public int getFuncionarioDevolucaoId() {
        return funcionarioDevolucaoId;
    }

    public void setFuncionarioDevolucaoId(int funcionarioDevolucaoId) {
        this.funcionarioDevolucaoId = funcionarioDevolucaoId;
    }

    public Pessoa getPessoa() {
        return this.pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public int getExemplarId() {
        return exemplarId;
    }

    public void setExemplarId(int exemplarId) {
        this.exemplarId = exemplarId;
    }

    public TipoOperacao getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(TipoOperacao tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public Date getDataLocacao() {
        return dataLocacao;
    }

    public void setDataLocacao(Date dataLocacao) {
        this.dataLocacao = dataLocacao;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Date getDataDevolvido() {
        return dataDevolvido;
    }

    public void setDataDevolvido(Date dataDevolvido) {
        this.dataDevolvido = dataDevolvido;
    }
}
