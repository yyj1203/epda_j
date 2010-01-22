
package com.kps.common.dto;

public class CodeMap {
    
    public CodeMap() {
        
    }
    
    public CodeMap(String label, String data) {
        this.label = label;
        this.data = data;
    }
    
    private Boolean isSelected;
    
    /**
     * @return the isSelected
     */
    public Boolean getIsSelected(){
        return isSelected;
    }

    
    /**
     * @param isSelected the isSelected to set
     */
    public void setIsSelected(Boolean isSelected){
        this.isSelected = isSelected;
    }

    private String data;
    private String label;
    
    /**
     * @return the data
     */
    public String getData(){
        return data;
    }
    
    /**
     * @param data the data to set
     */
    public void setData(String data){
        this.data = data;
    }
    
    /**
     * @return the label
     */
    public String getLabel(){
        return label;
    }
    
    /**
     * @param label the label to set
     */
    public void setLabel(String label){
        this.label = label;
    }  
    
    @Override public String toString() {
        return data;
    }
  

}
