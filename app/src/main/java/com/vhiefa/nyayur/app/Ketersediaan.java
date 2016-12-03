package com.vhiefa.nyayur.app;

/**
 * Created by eqwiphubs on 2016-12-03.
 */

public class Ketersediaan {

    private String id_sayuran;
    private String harga_sayuran;

    public void setTSId (String id_ts)
    {
        this.id_sayuran = id_ts;
    }

    public String getTSId()
    {
        return id_sayuran;
    }

    public void setTSName (String nama_ts)
    {
        this.harga_sayuran = nama_ts;
    }

    public String getTSName()
    {
        return harga_sayuran;
    }
}
