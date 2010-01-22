
package com.kps.epda.util;

import com.kps.epda.dao.CodeSetDao;

public class CodeBuildRequest {
    private CodeSetDao codeSetDao;
    public void start() {   
        codeSetDao.init();
    }
    public void setCodeSetDao(CodeSetDao dao) {
        codeSetDao = dao;
    }
}
