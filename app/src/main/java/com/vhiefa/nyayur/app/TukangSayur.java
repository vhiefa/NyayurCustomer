package com.vhiefa.nyayur.app;

/**
 * Created by afifatul on 2016-12-03.
 */

public class TukangSayur{

    private String id_ts;
    private String nama_ts;
    private String nope_ts;
    private String address_ts;

    public void setTSId (String id_ts)
    {
        this.id_ts = id_ts;
    }

    public String getTSId()
    {
        return id_ts;
    }

    public void setTSName (String nama_ts)
    {
        this.nama_ts = nama_ts;
    }

    public String getTSName()
    {
        return nama_ts;
    }

    public void setTSNope (String nope_ts)
    {
        this.nope_ts = nope_ts;
    }

    public String getTSNope()
    {
        return nope_ts;
    }

    public void setTSAddress (String address_ts)
    {
        this.address_ts = address_ts;
    }

    public String getTSAddres()
    {
        return address_ts;
    }
}
