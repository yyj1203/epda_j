/*******************************************************************
 * ����KPS WPS
 *
 * Copyright (c) 2007 by LG CNS, Inc.
 * All rights reserved.
 *******************************************************************
 * $Id: AttachFile.java,v 1.4 2008/03/10 05:43:04 ksm Exp $
 * 
 * @author $Author: ksm $
 * @version $Revision: 1.4 $
 */

package com.kps.epda.util;

import java.io.File;
import java.util.UUID;

public class AttachFile {
	/**
	 * �ӽ� ���丮�� ���ε�� ÷�������� ��������ҿ� �����Ѵ�.
	 * @param fileIn �ӽ� ���丮�� ����� ���� ��ü
	 * @param rootPath ���� ����� ��Ʈ ���丮
	 * @param dirName ���� ����� ���� ���丮
	 * @param ext ���� Ȯ����
	 * @throws Exception
	 */ 
    public File saveToRepository(File fileIn, String rootPath, String dirName, String ext) throws Exception 
    {
    	UUID uuid = new UUID(1,2);
    	String dirPath = rootPath + fileIn.separator + dirName;
    	String fullPath = dirPath + fileIn.separator + uuid.randomUUID().toString() + "." + ext;
    	
    	File fileDirPath = new File(dirPath);
    	File fileFullPath = new File(fullPath);
    	
    	if(!fileDirPath.exists())
    	{
    		fileDirPath.mkdirs();
    	}
    	
    	fileIn.renameTo(fileFullPath);
    	
    	return fileFullPath;
	}
}
