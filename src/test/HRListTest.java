/**
 * 
 */
package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.kps.epda.biz.AdminBiz;
import com.kps.epda.service.PlantService;
import com.kps.pm.saprfc.Z_PM_GET_TPLNRLIST_G;

public class HRListTest {
    
    //@Test
    public void getHRList() throws Exception {
        System.out.println("Start testing...");
        Map map = new HashMap();
        map.put("I_BEGDA", "20081201");
        AdminBiz biz = new AdminBiz();
        Map result = biz.getHR_IBSALIST((HashMap)map);
        System.out.println(result.toString());
    } 
    
    //@Test
    public void getTlnrList() throws Exception {
        System.out.println("Start testing...");
        Map map = new HashMap();
        map.put("I_GUBUN", "G1");
        map.put("I_IWERK", "3420");
        map.put("I_SWERK", "3420");
        map.put("I_BEBER", "224");
        map.put("I_FLTYP", "M");
        map.put("I_EQART", "E-MTR");
        AdminBiz biz = new AdminBiz();
        Map result = biz.getTplnrList((HashMap)map);
        System.out.println(result.toString());
    }
    
    
    public void getTplnr() {
        System.out.println("Start testing...");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("I_GUBUN", "G1");
        map.put("I_IWERK", "3420");
        map.put("I_SWERK", "3420");
        map.put("I_BEBER", "224");
        map.put("I_FLTYP", "M");        
        map.put("I_EQART", "E-MTR");
        //Z_PM_GET_TPLNRLIST rfc = new Z_PM_GET_TPLNRLIST();
        //Map result = rfc.execute((HashMap<String,Object>)map);
        //System.out.println(result.toString());
        PlantService service = new PlantService();
        service.getTplnrList((HashMap<String,Object>)map);
    }
    
    @Test
    public void getTplnr2()  {
        System.out.println("Start testing2...");
        Map<String,Object> map = new HashMap<String,Object>();         
        map.put("I_TPLNR", "312-3-KA-C001");        
        Z_PM_GET_TPLNRLIST_G rfc = new Z_PM_GET_TPLNRLIST_G();        
        Map result;
        try {
            result = rfc.execute((HashMap)map);
            System.out.println(result.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
       
    }
    
    public void getPlantList() throws Exception {
        System.out.println("Start testing...");      
        AdminBiz biz = new AdminBiz();
        Map result = biz.getPlantListFromSAP();
        List list = (List)result.get("list");
        System.out.println("stop testing..." + list.size());
        System.out.println(list.toString() );
        
        
    }
    
   
    
}
