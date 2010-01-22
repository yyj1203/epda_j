package com.kps.epda.util;

public class UserSession  {
	// 소속정보
	private String	  	groupId    		= "";		// 사용자그룹아이디		
	private String	  	groupName  		= "";		// 사용자그룹이름	
	
	private String 		plant1			= "";		//계획 플랜트
	private String 		plant2			= "";		//유지보수 플랜트
	private String 		sec1			= "";		//플랜트 섹션
	private String 		sec2			= "";		//계획자 그룹
	private String 		sec3			= "";		//작업장
	
	private String 		plant1Name			= "";		//계획 플랜트
	private String 		plant2Name			= "";		//유지보수 플랜트
	private String 		sec1Name			= "";		//플랜트 섹션
	private String 		sec2Name			= "";		//계획자 그룹
	private String 		sec3Name			= "";		//작업장

	// 사용자정보
	private String	  	account	   		= "";		// 사용자 아이디
	private String 	  	name		 	= "";		// 사용자이름
	private String 	  	mail		 	= "";		// 메일주소
	private String 	  	tel			 	= "";		// 전화번호
	private String 	  	org			 	= "";		// 부서명
	private String 	  	orgName		 	= "";		// 부서명
	
	private String     gradekid         = "";       // 직급코드
	
	//작업조-조장정보
	private String	  	bossAccount	   	= "";		// 조장아이디(사번)
	private String 	  	bossName		= "";		// 조장이름

	//작업조
	private String		workgrpcd		= "";		// 작업조코드
	
	//결재정보
	private String	  	nextEmpNo	   	= "";		// 상위 결재자 사번
	private String 	  	nextEmpName		= "";		// 상위 결재자 성명

/*	
	사용자 권한
	일반	 		: AA
	방사선 관리자 	: RD
*/	 
	private String 	  	usAuth			= "";		// 사용 권한
	
	/**
	 * 기본생성자
	 */
	public UserSession()
	{
	// do nothing
	}	
	
	/**
	 * 생성자
	 * @param groupId		사용자 그룹아이디
	 * @param groupName		사용자 그룹이름
	 * @param account		사용자 아이디
	 * @param name			사용자 이름
	 */
	public UserSession( String groupId, String groupName, 
			String account, String name)
	{
		this.groupId 	= groupId;
		this.groupName	= groupName;
		this.account	= account;
		this.name		= name;
	}
	
	/**
	 * 사용자가 속한 그룹 이이디 반환
	 */	
	public String getGroupCode()
	{
		return this.groupId;
	}
	
	/**
	 * 사용자가 속한 그룹 이이디 설정
	 */	
	public void setGroupCode( String groupId)
	{
		this.groupId = groupId;
	}		
	
	/**
	 * 사용자가 속한 그룹 이름 반환
	 */	
	public String getGroupName()
	{
		return this.groupName;
	}
	
	/**
	 * 사용자가 속한 그룹 이름 설정
	 */	
	public void setGroupName( String groupName)
	{
		this.groupName = groupName;
	}			
	
	/**
	 * 사용자 아이디 설정
	 */	
	public void setAccount(String account)
	{
		this.account = account;
	}
	
	/**
	 * 사용자 아이디 반환
	 */	
	public String getAccount()
	{
		return this.account;
	}
	
	/**
	 * 사용자 이름 설정
	 */	
	public void setName( String name)
	{
		this.name = name;
	}
	
	/**
	 * 사용자 이름 반환
	 */	
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * 메일 설정
	 */	
	public void setMail( String mail)
	{
		this.mail = mail;
	}
	
	/**
	 * 메일 반환
	 */	
	public String getMail()
	{
		return this.mail;
	}
	
	/**
	 * 전화번호 설정
	 */	
	public void setTel( String tel)
	{
		this.tel = tel;
	}
	
	/**
	 * 전화번호 반환
	 */	
	public String getTel()
	{
		return this.tel;
	}
	
	/**
	 * 부서 설정
	 */	
	public void setOrg( String org)
	{
		this.orgName = org;
	}
	
	/**
	 * 부서 반환
	 */	
	public String getOrg()
	{
		return this.org;
	}
	
	/**
	 * 부서명 설정
	 */	
	public void setOrgName( String orgName)
	{
		this.orgName = orgName;
	}
	
	/**
	 * 부서명 반환
	 */	
	public String getOrgName()
	{
		return this.orgName;
	}
	
	
    /**
     * @return the gradekid
     */
    public String getGradekid(){
        return gradekid;
    }

    
    /**
     * @param gradekid the gradekid to set
     */
    public void setGradekid(String gradekid){
        this.gradekid = gradekid;
    }

    /**
	 * 계획플랜트 설정
	 */	
	public void setPlant1( String plant1)
	{
		this.plant1 = plant1;
	}
	
	/**
	 * 계획플랜트 반환
	 */
	public String getPlant1()
	{
		return this.plant1;
	}
	
	/**
	 * 유지보수플랜트 설정
	 */	
	public void setPlant2( String plant2)
	{
		this.plant2 = plant2;
	}
	
	/**
	 * 유지보수 플랜트 반환
	 */
	public String getPlant2()
	{
		return this.plant2;
	}
	
	/**
	 * 플랜트섹션 설정
	 */	
	public void setSec1( String sec1)
	{
		this.sec1 = sec1;
	}
	
	/**
	 * 플랜트섹션 반환
	 */
	public String getSec1()
	{
		return this.sec1;
	}
	
	/**
	 * 계획자그룹 설정
	 */	
	public void setSec2( String sec2)
	{
		this.sec2 = sec2;
	}
	
	/**
	 * 계획자그룹 반환
	 */
	public String getSec2()
	{
		return this.sec2;
	}
	
	/**
	 * 작업장 설정
	 */	
	public void setSec3( String sec3)
	{
		this.sec3 = sec3;
	}
	
	/**
	 * 작업장 반환
	 */
	public String getSec3()
	{
		return this.sec3;
	}
	
	/**
	 * 계획플랜트 설정
	 */	
	public void setPlant1Name( String plant1Name)
	{
		this.plant1Name = plant1Name;
	}
	
	/**
	 * 계획플랜트 반환
	 */
	public String getPlant1Name()
	{
		return this.plant1Name;
	}
	
	/**
	 * 유지보수플랜트 설정
	 */	
	public void setPlant2Name( String plant2Name)
	{
		this.plant2Name = plant2Name;
	}
	
	/**
	 * 유지보수 플랜트 반환
	 */
	public String getPlant2Name()
	{
		return this.plant2Name;
	}
	
	/**
	 * 플랜트섹션 설정
	 */	
	public void setSec1Name( String sec1Name)
	{
		this.sec1Name = sec1Name;
	}
	
	/**
	 * 플랜트섹션 반환
	 */
	public String getSec1Name()
	{
		return this.sec1Name;
	}
	
	/**
	 * 계획자그룹 설정
	 */	
	public void setSec2Name( String sec2Name)
	{
		this.sec2Name = sec2Name;
	}
	
	/**
	 * 계획자그룹 반환
	 */
	public String getSec2Name()
	{
		return this.sec2Name;
	}
	
	/**
	 * 작업장 설정
	 */	
	public void setSec3Name( String sec3Name)
	{
		this.sec3Name = sec3Name;
	}
	
	/**
	 * 작업장 반환
	 */
	public String getSec3Name()
	{
		return this.sec3Name;
	}
	
	/**
	 * 조장아이디 설정
	 */	
	public void setBossAccount( String bossAccount)
	{
		this.bossAccount = bossAccount;
	}
	
	/**
	 * 조장아이디 반환
	 */
	public String getBossAccount()
	{
		return this.bossAccount;
	}	
	
	/**
	 * 조장이름 설정
	 */	
	public void setBossName( String bossName)
	{
		this.bossName = bossName;
	}
	
	/**
	 * 조장이름 반환
	 */
	public String getBossName()
	{
		return this.bossName;
	}	
	
	/**
	 * 상위결재자 사번 설정
	 */	
	public void setNextEmpNo( String nextEmpNo)
	{
		this.nextEmpNo = nextEmpNo;
	}
	
	/**
	 * 상위결재자 사번 반환
	 */
	public String getNextEmpNo()
	{
		return this.nextEmpNo;
	}
	
	/**
	 * 상위 결재자 성명 설정
	 */	
	public void setNextEmpName( String nextEmpName)
	{
		this.nextEmpName = nextEmpName;
	}
	
	/**
	 * 상위결재자 성명 반환
	 */
	public String getNextEmpName()
	{
		return this.nextEmpName;
	}	
	
	/**
	 * 사용 권한 설정
	 * 일반 : AA
	 * 방사선 : RD
	 */
	public void setUsAuth( String usAuth)
	{
		this.usAuth = usAuth;
	}
	
	/**
	 * 사용 권한 반환
	 */
	public String getUsAuth()
	{
		return this.usAuth;
	}
	
	/**
	 * 사용자 작업조 코드 설정
	 */	
	public void setWorkGrpCd( String workgrpcd)
	{
		this.workgrpcd = workgrpcd;
	}
	
	/**
	 * 작업조 코드 반환
	 */	
	public String getWorkGrpCd()
	{
		return this.workgrpcd;
	}	
	
}
