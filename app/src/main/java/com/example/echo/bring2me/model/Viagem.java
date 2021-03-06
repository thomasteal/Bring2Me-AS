package com.example.echo.bring2me.model;

/**
 * Created by thomas on 16/09/16.
 */

public class Viagem {
    private String origem;
    private String destino;
    private String thumbnailUrl;
    private String id;
    private int avaliacaoViajante;
    private double recompensaMin;
    private String data;

    private double precoMaxProduto;

    public Viagem() {
    }

    public Viagem(String origem, String destino, String thumbnailUrl, int avaliacaoViajante,
                 double precoBase, String id) {
        this.origem = origem;
        this.destino = destino;
        this.thumbnailUrl = thumbnailUrl;
        this.avaliacaoViajante = avaliacaoViajante;
        this.recompensaMin = precoBase;
        this.id = id;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getAvaliacaoViajante() {
        return avaliacaoViajante;
    }

    public void setAvaliacaoViajante(int avaliacaoViajante) { this.avaliacaoViajante = avaliacaoViajante; }

    public void setYear(int avaliacaoViajante) {
        this.avaliacaoViajante = avaliacaoViajante;
    }

    public double getRecompensaMinima() {
        return recompensaMin;
    }

    public void setRecompensaMin(double precoBase) {
        this.recompensaMin = precoBase;
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}


    public double getPrecoMaxProduto() { return this.precoMaxProduto; }

    public void setPrecoMaxProduto(double precoMaxProduto) {this.precoMaxProduto = precoMaxProduto; }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
