package com.kps.epda.vo;

import java.util.List;

public class Classification {
    private int id;
    private String name;
    private String type;
    private String description;
    private List children;
    private Boolean hasChildren;
        
    private String iwerk;
    private String system_code;
    private String beber;
    private String pdm_ingrp;
    private String pdm_pmt_code;
    private String pdm_gwerk;
    private String tplnr;
    private String point_seq;
    private String isbranch;
    private String treelevel;
    
    /**
     * @return the id
     */
    public int getId(){
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(int id){
        this.id = id;
    }
    
    /**
     * @return the name
     */
    public String getName(){
        return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * @return the type
     */
    public String getType(){
        return type;
    }
    
    /**
     * @param type the type to set
     */
    public void setType(String type){
        this.type = type;
    }
    
    /**
     * @return the description
     */
    public String getDescription(){
        return description;
    }
    
    /**
     * @param description the description to set
     */
    public void setDescription(String description){
        this.description = description;
    }
    
    /**
     * @return the children
     */
    public List getChildren(){
        return children;
    }
    
    /**
     * @param children the children to set
     */
    public void setChildren(List children){
        this.children = children;
    }
    
    /**
     * @return the hasChildren
     */
    public Boolean getHasChildren(){
        return hasChildren;
    }
    
    /**
     * @param hasChildren the hasChildren to set
     */
    public void setHasChildren(Boolean hasChildren){
        this.hasChildren = hasChildren;
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
     * @return the system_code
     */
    public String getSystem_code(){
        return system_code;
    }
    
    /**
     * @param systemCode the system_code to set
     */
    public void setSystem_code(String systemCode){
        system_code = systemCode;
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
     * @return the pdm_ingrp
     */
    public String getPdm_ingrp(){
        return pdm_ingrp;
    }

    
    /**
     * @param pdmIngrp the pdm_ingrp to set
     */
    public void setPdm_ingrp(String pdmIngrp){
        pdm_ingrp = pdmIngrp;
    }

    
    
    /**
     * @return the pdm_pmt_code
     */
    public String getPdm_pmt_code(){
        return pdm_pmt_code;
    }

    
    /**
     * @param pdmPmtCode the pdm_pmt_code to set
     */
    public void setPdm_pmt_code(String pdmPmtCode){
        pdm_pmt_code = pdmPmtCode;
    }
   
    
    /**
     * @return the pdm_gwerk
     */
    public String getPdm_gwerk(){
        return pdm_gwerk;
    }

    
    /**
     * @param pdmGwerk the pdm_gwerk to set
     */
    public void setPdm_gwerk(String pdmGwerk){
        pdm_gwerk = pdmGwerk;
    }

    /**
     * @param tplnr the tplnr to set
     */
    public void setTplnr(String tplnr){
        this.tplnr = tplnr;
    }

    /**
     * @return the tplnr
     */
    public String getTplnr(){
        return tplnr;
    }

    /**
     * @param point_seq the point_seq to set
     */
    public void setPoint_seq(String point_seq){
        this.point_seq = point_seq;
    }

    /**
     * @return the point_seq
     */
    public String getPoint_seq(){
        return point_seq;
    }

    /**
     * @return the isbranch
     */
    public String getIsbranch(){
        return isbranch;
    }
    
    /**
     * @param isbranch the isbranch to set
     */
    public void setIsbranch(String isbranch){
        this.isbranch = isbranch;
    }

    
    /**
     * @return the treelevel
     */
    public String getTreelevel(){
        return treelevel;
    }

    
    /**
     * @param treelevel the treelevel to set
     */
    public void setTreelevel(String treelevel){
        this.treelevel = treelevel;
    }
        
    public String toString() {
        return this.pdm_gwerk;
    }
  
      
}
