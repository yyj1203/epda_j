
package com.kps.epda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import oracle.sql.DATE;

@Entity
@Table(name="TB_POINT")
public class CheckPoint implements java.io.Serializable {
    @Id
    @Column(name="POINT_SEQ")
    @SequenceGenerator(name = "id_sequence", sequenceName = "TB_POINT_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id_sequence")
    private int point_seq;
    
    @Column(name="IWERK")
    private String iwerk;
    
    @Column(name="SWERK")
    private String swerk;
    
    @Column(name="BEBER")
    private String beber;
    
    @Column(name="TPLNR")
    private String tplnr;
    
    @Column(name="POINT_CD")
    private String point_cd;
    
    @Column(name="WKCNT")
    private String wkcnt;
    
    @Column(name="POINT_NAME")
    private String point_name;
    
    @Column(name="POINT_STS")
    private String point_sts;
    
    @Column(name="POINT_IND")
    private int point_ind;
    
    @Column(name="POINT_USER")
    private String point_user;
    
    @Column(name="POINT_IDATE")
    private DATE point_idate;

    
    /**
     * @return the point_seq
     */
    public int getPoint_seq(){
        return point_seq;
    }

    
    /**
     * @param pointSeq the point_seq to set
     */
    public void setPoint_seq(int pointSeq){
        point_seq = pointSeq;
    }

    
    /**
     * @return the iwerk
     */
    public String getIwerk(){
        return iwerk;
    }

    
    /**
     * @param iwerk the iwerk to set
     */
    public void setIwerk(String iwerk){
        this.iwerk = iwerk;
    }

    
    /**
     * @return the swerk
     */
    public String getSwerk(){
        return swerk;
    }

    
    /**
     * @param swerk the swerk to set
     */
    public void setSwerk(String swerk){
        this.swerk = swerk;
    }

    
    /**
     * @return the beber
     */
    public String getBeber(){
        return beber;
    }

    
    /**
     * @param beber the beber to set
     */
    public void setBeber(String beber){
        this.beber = beber;
    }

    
    /**
     * @return the tplnr
     */
    public String getTplnr(){
        return tplnr;
    }

    
    /**
     * @param tplnr the tplnr to set
     */
    public void setTplnr(String tplnr){
        this.tplnr = tplnr;
    }

    
    /**
     * @return the point_cd
     */
    public String getPoint_cd(){
        return point_cd;
    }

    
    /**
     * @param pointCd the point_cd to set
     */
    public void setPoint_cd(String pointCd){
        point_cd = pointCd;
    }

    
    /**
     * @return the wkcnt
     */
    public String getWkcnt(){
        return wkcnt;
    }

    
    /**
     * @param wkcnt the wkcnt to set
     */
    public void setWkcnt(String wkcnt){
        this.wkcnt = wkcnt;
    }

    
    /**
     * @return the point_name
     */
    public String getPoint_name(){
        return point_name;
    }

    
    /**
     * @param pointName the point_name to set
     */
    public void setPoint_name(String pointName){
        point_name = pointName;
    }

    
    /**
     * @return the point_sts
     */
    public String getPoint_sts(){
        return point_sts;
    }

    
    /**
     * @param pointSts the point_sts to set
     */
    public void setPoint_sts(String pointSts){
        point_sts = pointSts;
    }

    
    /**
     * @return the point_ind
     */
    public int getPoint_ind(){
        return point_ind;
    }

    
    /**
     * @param pointInd the point_ind to set
     */
    public void setPoint_ind(int pointInd){
        point_ind = pointInd;
    }

    
    /**
     * @return the point_user
     */
    public String getPoint_user(){
        return point_user;
    }

    
    /**
     * @param pointUser the point_user to set
     */
    public void setPoint_user(String pointUser){
        point_user = pointUser;
    }

    
    /**
     * @return the point_idate
     */
    public DATE getPoint_idate(){
        return point_idate;
    }

    
    /**
     * @param pointIdate the point_idate to set
     */
    public void setPoint_idate(DATE pointIdate){
        point_idate = pointIdate;
    }
    
}
