/*******************************************************************
 * ����KPS WPS
 *
 * Copyright (c) 2007 by LG CNS, Inc.
 * All rights reserved.
 *******************************************************************
 * $Id: DateUtil.java,v 1.7 2008/05/20 05:24:05 ksm Exp $
 * 
 * @author $Author: ksm $
 * @version $Revision: 1.7 $
 */

package com.kps.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DateUtil
{
	public static final String					SZ_NULL_STRING = "";

	public static final SimpleDateFormat		SDF_YYYY = new SimpleDateFormat( "yyyy");
	public static final SimpleDateFormat		SDF_YYYYMM = new SimpleDateFormat( "yyyyMM");
	public static final SimpleDateFormat		SDF_YYYYMMDD = new SimpleDateFormat( "yyyyMMdd");
	public static final SimpleDateFormat		SDF_YYYYMM_DASH = new SimpleDateFormat( "yyyy-MM");
	public static final SimpleDateFormat		SDF_YYYYMMDD_DASH = new SimpleDateFormat( "yyyy-MM-dd");
	public static final SimpleDateFormat		SDF_YYYYMMDD_SLASH= new SimpleDateFormat("yyyy/MM/dd");
	public static final SimpleDateFormat		SDF_YYYYMMDD_DOT = new SimpleDateFormat( "yyyy.MM.dd");
	public static final SimpleDateFormat		SDF_YYYYMMDDHHMM = new SimpleDateFormat( "yyyyMMddhhmm");
	public static final SimpleDateFormat		SDF_YYYYMMDDHHMMSS = new SimpleDateFormat( "yyyyMMddHHmmss");
	public static final SimpleDateFormat		SDF_YYYYMMDDHHMM_DASH_DOT = new SimpleDateFormat( "yyyy-MM-dd HH:mm");
	public static final SimpleDateFormat		SDF_YYYYMMDDHHMMM_DASH_DOT = new SimpleDateFormat( "yyyy-MM-dd hh:mm a");
	public static final SimpleDateFormat		SDF_YYYYMMDDHHMMSS_NO_MARK = new SimpleDateFormat( "yyyyMMddHHmmss");
	public static final SimpleDateFormat		SDF_YYYYMMDDHHMMSSM_DASH_DOT = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss a");
	public static final SimpleDateFormat		SDF_YYYYMMDDHHMMSSM_DASH = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat		SDF_YYYYMMDDHHMMSS_SSS_DASH = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss SSS");

	/**
	 * srcSDF Type���� �Ǿ��ִ� ��¥ String�� dstSDF Type�� ��¥ String���� ��ȯ�Ѵ�.
	 *
	 * @return		String�� Ÿ���ϸ� ��ȯ�� Date�� �ƴϸ� null�� �����Ѵ�.
	 */
	public static String convertString( String szDate, SimpleDateFormat srcSDF, SimpleDateFormat dstSDF)
	{
		if ( szDate == null || szDate.length() <= 0 ) { return SZ_NULL_STRING;}

		try
		{
			Date dt = srcSDF.parse( szDate);
			return dstSDF.format( dt);
		}
		catch ( Exception e )
		{
			return SZ_NULL_STRING;
		}
	}

	/**
	 * �־��� Date�� sdf Type���� �Ǿ��ִ� ��¥ String���� ��ȯ�Ѵ�.
	 *
	 * @return		Date�� Ÿ���ϸ� ��ȯ�� Date�� �ƴϸ� null�� �����Ѵ�.
	 */
	public static String convertDate( Date dt, SimpleDateFormat sdf)
	{
		if ( dt == null ) { return SZ_NULL_STRING;}

		try
		{
			return sdf.format( dt);
		}
		catch ( Exception e )
		{
			return SZ_NULL_STRING;
		}
	}

	/**
	 * �־��� Timestamp�� sdf Type���� �Ǿ��ִ� ��¥ String���� ��ȯ�Ѵ�.
	 *
	 * @return		Date�� Ÿ���ϸ� ��ȯ�� Date�� �ƴϸ� null�� �����Ѵ�.
	 */
	public static String convertDate( Timestamp ts, SimpleDateFormat sdf)
	{
		if ( ts == null ) { return SZ_NULL_STRING;}

		return convertDate( new Date( ts.getTime()), sdf);
	}

	/**
	 * sdf�������� �Ǿ��ִ� ��¥String�� Date Type���� �����Ѵ�.
	 */
	public static Date convertString( String szString, SimpleDateFormat sdf)
	{
		if ( szString == null || szString.length() <= 0 ) { return null;}

		try
		{
			return sdf.parse( szString);
		}
		catch (Exception e)
		{
//			DebugUtil.printMSG_1( e);
			return null;
		}
	}

	/**
		* ������ �ð��� ���� ������ ��� Method.
		* @parameter nDay : ����κ��� ������
		* @parameter sdf : Date Format
		* @return : ���� nDay���� Date String
	*/
	public static String lastDayToString(int nDay, SimpleDateFormat sdf )
	{
		Calendar rightNow = Calendar.getInstance();

		rightNow.add(Calendar.DATE, -nDay);

		try
		{
			return sdf.format(rightNow.getTime());
		}
		catch ( Exception e )
		{
			return SZ_NULL_STRING;
		}
	}

	 /**
	  * ������ �ð��� ���� ������ ��� Method.
	  * @parameter nMonth : ����� ���� �����
	  * @parameter sdf : Date Format
	  * @return : ���� nMonth���� Date String
	  */
	public static String lastMonthToString(int nMonth, SimpleDateFormat sdf )
	{
		Calendar rightNow = Calendar.getInstance();

		rightNow.add(Calendar.MONTH, -nMonth);

		try
		{
			return sdf.format(rightNow.getTime());
		}
		catch ( Exception e )
		{
			return SZ_NULL_STRING;
		}
	}

	 /**
	  * �̷��� �ð��� ���� ������ ��� Method.
	  * @parameter nMonth : ����� ���� �����
	  * @parameter sdf : Date Format
	  * @return : �̷�  nMonth ���� Date String
	  */
	public static String futureMonthToString(int nMonth, SimpleDateFormat sdf )
	{
		Calendar rightNow = Calendar.getInstance();

		rightNow.add(Calendar.MONTH, +nMonth);

		try
		{
			return sdf.format(rightNow.getTime());
		}
		catch ( Exception e )
		{
			return SZ_NULL_STRING;
		}
	}
	
	 /**
	  * ������ �ð��� ���� ������ ��� Method.
	  * @parameter sdf : Date Format
	  * @return : ���� Time�� Date String
	  */

	public static String currentTimeToString( SimpleDateFormat sdf )
	{
		Calendar rightNow = Calendar.getInstance();

		try
		{
			return sdf.format(rightNow.getTime());
		}
		catch ( Exception e )
		{
			return SZ_NULL_STRING;
		}
	}


	/**
	 * @param : Source Tmestamp, Target Timestamp
	 * @return : Source�� Target���� ���̸� '1', ���̸� '-1', ������ '0'
	 */
	public static int compareTimestamp( java.sql.Timestamp tsSource, java.sql.Timestamp tsTarget )
	{
		if( tsSource == null )
		{
			return ( tsTarget == null ) ? 0 : -1;
		}

		if( tsTarget == null )
		{
			return 1;
		}

		return tsSource.equals( tsTarget ) ? 0 : tsSource.before( tsTarget ) ? 1 : -1;
	}
	
	/**
	 * @param : Source Date, Target Date
	 * @return : Source�� Target�� ��¥ ���̸� ��ȯ�Ѵ�.
	 *           0���� ũ�� Source > Taget, 
	 *           0�̸� Source = Target, 
	 *           0���� ������ Source < Taget
	 */
	public static int getDateDiff( String sourceDate, String targetDate )
	{
		Calendar sourceCalendar = null;
		Calendar targetCalendar = null;
		String regDate = "^\\d{4}[\\-](0[1-9]|1[0-2])[\\-](0[1-9]|[1-2][0-9]|3[0-1])$";
		int year = 0;
		int month = 0;
		int day = 0;

		if(!sourceDate.matches(regDate) || !targetDate.matches(regDate))
		{
			return -9999;
		}
		
		//Source 
		year = Integer.parseInt(sourceDate.substring(0, 4));
		month = Integer.parseInt(sourceDate.substring(5,7));
		day = Integer.parseInt(sourceDate.substring(8));
		sourceCalendar = Calendar.getInstance();
		sourceCalendar.set(year, month, day);
		
		//Target
		year = Integer.parseInt(targetDate.substring(0, 4));
		month = Integer.parseInt(targetDate.substring(5,7));
		day = Integer.parseInt(targetDate.substring(8));
		targetCalendar = Calendar.getInstance();
		targetCalendar.set(year, month, day);

		return getDateDiff(sourceCalendar.getTime(), targetCalendar.getTime());
	}
	
	/**
	 * @param : Source Date, Target Date
	 * @return : Source�� Target�� ��¥ ���̸� ��ȯ�Ѵ�.
	 *           0���� ũ�� Source > Taget, 
	 *           0�̸� Source = Target, 
	 *           0���� ������ Source < Taget
	 */
	public static int getDateDiff( Date sourceDate, Date targetDate )
	{
		long dateDiff = 0;
		long msOfDay = 24 * 60 * 60 * 1000;	//1���� �и��ʷ� ��ȯ

		dateDiff = targetDate.getTime() - sourceDate.getTime();
		
		return (int)Math.ceil((double)dateDiff / msOfDay);
	}
	
	public static int calaWeekday( int nYear, int nMonth, int nDay)
	{
        if(nMonth >= 3)
        {        
            nMonth -= 2;
	    }
	    else 
	    {
	    	nMonth += 10;
	    }
	
	    if( ( nMonth == 11) || ( nMonth == 12) )
	    {
	    	nYear--;
	    }
	    
	    int nCentNum  = (int)( nYear / 100);
	    int nDYearNum = nYear % 100;
	    
	    int g = (int)( 2.6 * nMonth - 0.2);

        g +=  (int)( nDay + nDYearNum);
        g +=  (int)( nDYearNum / 4);        
        g +=  (int)( nCentNum / 4);
        g -=  (int)( 2 * nCentNum);
        g %= 7;
        
        if(g < 0){
                g += 7;
        }	    
        
        return g;
	}

	public static int getLastDay( int nYear, int nMonth)
	{
		int[] nMonthLen = { 31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		
		if ( nMonth == 2)
		{
			if ( ((nYear % 4 == 0) && (nYear % 100 != 0)) || (nYear % 400 == 0) ) 
			{
				return 29;
			} else 
			{
				return 28;
			}		
		}
		else
		{
			if ( nMonth < 1 || nMonth > 12)
			{
				return 31;
			}
			else  
			{
				return nMonthLen[ nMonth-1];
			}
		}		
	}
	
	/**
	 * �� ���� ������ ���ڸ� ���� �Ѵ�.
	 * ������ üũ �ϴ� �κп� �°� 'YYYYMMDD'������ ���ڿ��ε� ���ڸ� �����Ѵ�.
	 * ���� - �ð��� ������ ���� ���ڸ��� üũ��
	 * 
	 * �� ������ ���̰� ���� �ϰ�� �θ� ��ȯ �ϵ��� ��
	 * 
	 * @param dateOne
	 * @param dateTwo
	 * @return
	 */
	public static String[] getDateYYYYMMDD( Date dateOne, Date dateTwo)
	{
		String szDateOne = DateUtil.convertDate( dateOne, SDF_YYYYMMDD);
		String szDateTwo = DateUtil.convertDate( dateTwo, SDF_YYYYMMDD);
		
		Date convertDateOne = DateUtil.convertString( szDateOne, DateUtil.SDF_YYYYMMDD);
		Date convertDateTwo = DateUtil.convertString( szDateTwo, DateUtil.SDF_YYYYMMDD);
		
		long lDayCount = (convertDateTwo.getTime() - convertDateOne.getTime()) / ( 1000*60*60*24);

		if ( lDayCount < 1)
		{
			return null;
		}
		
		//�������� ũ�Ⱑ  Ʋ���� ��... �������� �ʹ� ũ�� �װ͵�  ��..
		int nDay = (int)lDayCount;
		
		// 1��10�� , 1�� 14�� ���� 4��, ���۳��ڵ� ���Խ��Ѽ� �����ؾ� �ϹǷ� 
		String[] szArrDate = new String[ nDay + 1];
		szArrDate[0] = szDateOne;
		for ( int i=1; i < nDay+1; i++)
		{
			long lTime = convertDateOne.getTime() + ( 1000*60*60*24);
			convertDateOne.setTime( lTime);
			szArrDate[i] = DateUtil.convertDate( convertDateOne, DateUtil.SDF_YYYYMMDD);
		}
		
		return szArrDate;
	}
	
	/**
	 * Date�� �Է¹޾� ������ ����.
	 * �Ͽ���~������� 1~7���� ���ε�
	 * @param date
	 * @return ���Ͽ� �ش��ϴ� ���ڻ��( Calendar.DAY_OF_WEEK)
	 */
	public static int getDayOfWeek( Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( date);
		
		return calendar.get( Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * �ð�(Hour)�� ����	 , 0��~23�÷� ǥ�� ��
	 * @param date
	 * @return
	 */
	public static int getHourOfDay( Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( date);
		
		return calendar.get( Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * ��(Minute)�� ����	 , 0~59������ ǥ�� ��
	 * @param date
	 * @return
	 */
	public static int getMinuteOfDay( Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( date);
		
		return calendar.get( Calendar.MINUTE);
	}
	
	/**
	 * ��(Second)�� ���� , 0 ~59
	 * @param date
	 * @return
	 */
	public static int getSecondOfDay( Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( date);
		
		return calendar.get( Calendar.SECOND);
	}
	
	/**
	 * �ش���� ���������ڸ� ��ȯ  1���� 31, 2�� 28�� �Ǵ� 29��
	 * @param date
	 * @return
	 */
	public static int getLastDay( Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( date);
		
		return calendar.getActualMaximum( Calendar.SECOND);		
	}
		
	public static Date getNextDay( Date date, int nDay)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( date);
		calendar.add( Calendar.DAY_OF_WEEK, nDay);
		
		return calendar.getTime();
	}
	
	public static Date addMinuteToDate( Date date, int nMinute)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( date);
		calendar.add( Calendar.MINUTE, nMinute);
		
		return calendar.getTime();
	}	
	
	public static class Test
	{
		public static void main( String[] args)
		{
			//�� ~ ����� , 1~7�� ���ε�
			Date date1 = DateUtil.convertString("2006-01-10", DateUtil.SDF_YYYYMMDD_DASH);			
			Date date2 = DateUtil.convertString("2006-02-10", DateUtil.SDF_YYYYMMDD_DASH);
			
			long cntTime = (date2.getTime()-date1.getTime())/(1000*60*60*24);
			
			if ( cntTime > 0)
			{
				cntTime += 1;
			}
			
			Long.toString(cntTime);
			
			
			
//			String[] szArrDate = DateUtil.getDateYYYYMMDD( date1, date2);
//
//			for ( int i=0; i < szArrDate.length; i++)
//			{
//				System.out.println("" + szArrDate[i]);
//			}
//			Date date1 = DateUtil.convertString("2006-02-08 00:23:12", DateUtil.SDF_YYYYMMDDHHMMSSM_DASH);			
//			Date date2 = DateUtil.convertString("2006-01-09 13:23:12", DateUtil.SDF_YYYYMMDDHHMMSSM_DASH);
//			Date date3 = DateUtil.convertString("2006-01-14 09:23:12", DateUtil.SDF_YYYYMMDDHHMMSSM_DASH);
	
//			System.out.println("Result:" + DateUtil.calaWeekday(2005, 9, 25) );

//			Date szDate = new Date( System.currentTimeMillis());
//			String szTDate = DateUtil.convertDate( szDate, DateUtil.SDF_YYYYMMDD);
//			System.out.println("Result:" + szTDate.substring(0, 4) );
//			System.out.println("Result:" + szTDate.substring(4, 6) );
//			System.out.println("Result:" + szTDate.substring(6, 8) );
//			System.out.println("Result:" + Integer.parseInt( "01") );
		}
	}
}