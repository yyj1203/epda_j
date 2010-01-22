/*******************************************************************
 * 한전KPS WPS
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
	 * 임시 디렉토리에 업로드된 첨부파일을 파일저장소에 저장한다.
	 * @param fileIn 임시 디렉토리에 저장된 파일 객체
	 * @param rootPath 파일 저장소 루트 디렉토리
	 * @param dirName 파일 저장소 하위 디렉토리
	 * @param ext 파일 확장자
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
