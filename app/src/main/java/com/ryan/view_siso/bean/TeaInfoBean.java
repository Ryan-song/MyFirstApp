package com.ryan.view_siso.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by air on 15/11/27.
 */
public class TeaInfoBean implements Parcelable {

    /**
     * zhigonghao : 20080526004
     * xingmin : 王冬莉
     * youjian : wangdl
     * xingbie : 女
     * bumen : 公共学科部
     * done : 0
     * officephone : 2610
     * cellphone : 13584895567
     */

    private String zhigonghao;
    private String xingmin;
    private String youjian;
    private String xingbie;
    private String bumen;
    private int done;
    private String officephone;
    private String cellphone;

    public void setZhigonghao(String zhigonghao) {
        this.zhigonghao = zhigonghao;
    }

    public void setXingmin(String xingmin) {
        this.xingmin = xingmin;
    }

    public void setYoujian(String youjian) {
        this.youjian = youjian;
    }

    public void setXingbie(String xingbie) {
        this.xingbie = xingbie;
    }

    public void setBumen(String bumen) {
        this.bumen = bumen;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public void setOfficephone(String officephone) {
        this.officephone = officephone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getZhigonghao() {
        return zhigonghao;
    }

    public String getXingmin() {
        return xingmin;
    }

    public String getYoujian() {
        return youjian;
    }

    public String getXingbie() {
        return xingbie;
    }

    public String getBumen() {
        return bumen;
    }

    public int getDone() {
        return done;
    }

    public String getOfficephone() {
        return officephone;
    }

    public String getCellphone() {
        return cellphone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.zhigonghao);
        dest.writeString(this.xingmin);
        dest.writeString(this.youjian);
        dest.writeString(this.xingbie);
        dest.writeString(this.bumen);
        dest.writeInt(this.done);
        dest.writeString(this.officephone);
        dest.writeString(this.cellphone);
    }

    public TeaInfoBean() {
    }

    protected TeaInfoBean(Parcel in) {
        this.zhigonghao = in.readString();
        this.xingmin = in.readString();
        this.youjian = in.readString();
        this.xingbie = in.readString();
        this.bumen = in.readString();
        this.done = in.readInt();
        this.officephone = in.readString();
        this.cellphone = in.readString();
    }

    public static final Creator<TeaInfoBean> CREATOR = new Creator<TeaInfoBean>() {
        public TeaInfoBean createFromParcel(Parcel source) {
            return new TeaInfoBean(source);
        }

        public TeaInfoBean[] newArray(int size) {
            return new TeaInfoBean[size];
        }
    };
}
