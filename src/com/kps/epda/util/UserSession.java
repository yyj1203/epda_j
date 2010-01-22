package com.kps.epda.util;

public class UserSession  {
	// �Ҽ�����
	private String	  	groupId    		= "";		// ����ڱ׷���̵�		
	private String	  	groupName  		= "";		// ����ڱ׷��̸�	
	
	private String 		plant1			= "";		//��ȹ �÷�Ʈ
	private String 		plant2			= "";		//�������� �÷�Ʈ
	private String 		sec1			= "";		//�÷�Ʈ ����
	private String 		sec2			= "";		//��ȹ�� �׷�
	private String 		sec3			= "";		//�۾���
	
	private String 		plant1Name			= "";		//��ȹ �÷�Ʈ
	private String 		plant2Name			= "";		//�������� �÷�Ʈ
	private String 		sec1Name			= "";		//�÷�Ʈ ����
	private String 		sec2Name			= "";		//��ȹ�� �׷�
	private String 		sec3Name			= "";		//�۾���

	// ���������
	private String	  	account	   		= "";		// ����� ���̵�
	private String 	  	name		 	= "";		// ������̸�
	private String 	  	mail		 	= "";		// �����ּ�
	private String 	  	tel			 	= "";		// ��ȭ��ȣ
	private String 	  	org			 	= "";		// �μ���
	private String 	  	orgName		 	= "";		// �μ���
	
	private String     gradekid         = "";       // �����ڵ�
	
	//�۾���-��������
	private String	  	bossAccount	   	= "";		// ������̵�(���)
	private String 	  	bossName		= "";		// �����̸�

	//�۾���
	private String		workgrpcd		= "";		// �۾����ڵ�
	
	//��������
	private String	  	nextEmpNo	   	= "";		// ���� ������ ���
	private String 	  	nextEmpName		= "";		// ���� ������ ����

/*	
	����� ����
	�Ϲ�	 		: AA
	��缱 ������ 	: RD
*/	 
	private String 	  	usAuth			= "";		// ��� ����
	
	/**
	 * �⺻������
	 */
	public UserSession()
	{
	// do nothing
	}	
	
	/**
	 * ������
	 * @param groupId		����� �׷���̵�
	 * @param groupName		����� �׷��̸�
	 * @param account		����� ���̵�
	 * @param name			����� �̸�
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
	 * ����ڰ� ���� �׷� ���̵� ��ȯ
	 */	
	public String getGroupCode()
	{
		return this.groupId;
	}
	
	/**
	 * ����ڰ� ���� �׷� ���̵� ����
	 */	
	public void setGroupCode( String groupId)
	{
		this.groupId = groupId;
	}		
	
	/**
	 * ����ڰ� ���� �׷� �̸� ��ȯ
	 */	
	public String getGroupName()
	{
		return this.groupName;
	}
	
	/**
	 * ����ڰ� ���� �׷� �̸� ����
	 */	
	public void setGroupName( String groupName)
	{
		this.groupName = groupName;
	}			
	
	/**
	 * ����� ���̵� ����
	 */	
	public void setAccount(String account)
	{
		this.account = account;
	}
	
	/**
	 * ����� ���̵� ��ȯ
	 */	
	public String getAccount()
	{
		return this.account;
	}
	
	/**
	 * ����� �̸� ����
	 */	
	public void setName( String name)
	{
		this.name = name;
	}
	
	/**
	 * ����� �̸� ��ȯ
	 */	
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * ���� ����
	 */	
	public void setMail( String mail)
	{
		this.mail = mail;
	}
	
	/**
	 * ���� ��ȯ
	 */	
	public String getMail()
	{
		return this.mail;
	}
	
	/**
	 * ��ȭ��ȣ ����
	 */	
	public void setTel( String tel)
	{
		this.tel = tel;
	}
	
	/**
	 * ��ȭ��ȣ ��ȯ
	 */	
	public String getTel()
	{
		return this.tel;
	}
	
	/**
	 * �μ� ����
	 */	
	public void setOrg( String org)
	{
		this.orgName = org;
	}
	
	/**
	 * �μ� ��ȯ
	 */	
	public String getOrg()
	{
		return this.org;
	}
	
	/**
	 * �μ��� ����
	 */	
	public void setOrgName( String orgName)
	{
		this.orgName = orgName;
	}
	
	/**
	 * �μ��� ��ȯ
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
	 * ��ȹ�÷�Ʈ ����
	 */	
	public void setPlant1( String plant1)
	{
		this.plant1 = plant1;
	}
	
	/**
	 * ��ȹ�÷�Ʈ ��ȯ
	 */
	public String getPlant1()
	{
		return this.plant1;
	}
	
	/**
	 * ���������÷�Ʈ ����
	 */	
	public void setPlant2( String plant2)
	{
		this.plant2 = plant2;
	}
	
	/**
	 * �������� �÷�Ʈ ��ȯ
	 */
	public String getPlant2()
	{
		return this.plant2;
	}
	
	/**
	 * �÷�Ʈ���� ����
	 */	
	public void setSec1( String sec1)
	{
		this.sec1 = sec1;
	}
	
	/**
	 * �÷�Ʈ���� ��ȯ
	 */
	public String getSec1()
	{
		return this.sec1;
	}
	
	/**
	 * ��ȹ�ڱ׷� ����
	 */	
	public void setSec2( String sec2)
	{
		this.sec2 = sec2;
	}
	
	/**
	 * ��ȹ�ڱ׷� ��ȯ
	 */
	public String getSec2()
	{
		return this.sec2;
	}
	
	/**
	 * �۾��� ����
	 */	
	public void setSec3( String sec3)
	{
		this.sec3 = sec3;
	}
	
	/**
	 * �۾��� ��ȯ
	 */
	public String getSec3()
	{
		return this.sec3;
	}
	
	/**
	 * ��ȹ�÷�Ʈ ����
	 */	
	public void setPlant1Name( String plant1Name)
	{
		this.plant1Name = plant1Name;
	}
	
	/**
	 * ��ȹ�÷�Ʈ ��ȯ
	 */
	public String getPlant1Name()
	{
		return this.plant1Name;
	}
	
	/**
	 * ���������÷�Ʈ ����
	 */	
	public void setPlant2Name( String plant2Name)
	{
		this.plant2Name = plant2Name;
	}
	
	/**
	 * �������� �÷�Ʈ ��ȯ
	 */
	public String getPlant2Name()
	{
		return this.plant2Name;
	}
	
	/**
	 * �÷�Ʈ���� ����
	 */	
	public void setSec1Name( String sec1Name)
	{
		this.sec1Name = sec1Name;
	}
	
	/**
	 * �÷�Ʈ���� ��ȯ
	 */
	public String getSec1Name()
	{
		return this.sec1Name;
	}
	
	/**
	 * ��ȹ�ڱ׷� ����
	 */	
	public void setSec2Name( String sec2Name)
	{
		this.sec2Name = sec2Name;
	}
	
	/**
	 * ��ȹ�ڱ׷� ��ȯ
	 */
	public String getSec2Name()
	{
		return this.sec2Name;
	}
	
	/**
	 * �۾��� ����
	 */	
	public void setSec3Name( String sec3Name)
	{
		this.sec3Name = sec3Name;
	}
	
	/**
	 * �۾��� ��ȯ
	 */
	public String getSec3Name()
	{
		return this.sec3Name;
	}
	
	/**
	 * ������̵� ����
	 */	
	public void setBossAccount( String bossAccount)
	{
		this.bossAccount = bossAccount;
	}
	
	/**
	 * ������̵� ��ȯ
	 */
	public String getBossAccount()
	{
		return this.bossAccount;
	}	
	
	/**
	 * �����̸� ����
	 */	
	public void setBossName( String bossName)
	{
		this.bossName = bossName;
	}
	
	/**
	 * �����̸� ��ȯ
	 */
	public String getBossName()
	{
		return this.bossName;
	}	
	
	/**
	 * ���������� ��� ����
	 */	
	public void setNextEmpNo( String nextEmpNo)
	{
		this.nextEmpNo = nextEmpNo;
	}
	
	/**
	 * ���������� ��� ��ȯ
	 */
	public String getNextEmpNo()
	{
		return this.nextEmpNo;
	}
	
	/**
	 * ���� ������ ���� ����
	 */	
	public void setNextEmpName( String nextEmpName)
	{
		this.nextEmpName = nextEmpName;
	}
	
	/**
	 * ���������� ���� ��ȯ
	 */
	public String getNextEmpName()
	{
		return this.nextEmpName;
	}	
	
	/**
	 * ��� ���� ����
	 * �Ϲ� : AA
	 * ��缱 : RD
	 */
	public void setUsAuth( String usAuth)
	{
		this.usAuth = usAuth;
	}
	
	/**
	 * ��� ���� ��ȯ
	 */
	public String getUsAuth()
	{
		return this.usAuth;
	}
	
	/**
	 * ����� �۾��� �ڵ� ����
	 */	
	public void setWorkGrpCd( String workgrpcd)
	{
		this.workgrpcd = workgrpcd;
	}
	
	/**
	 * �۾��� �ڵ� ��ȯ
	 */	
	public String getWorkGrpCd()
	{
		return this.workgrpcd;
	}	
	
}
