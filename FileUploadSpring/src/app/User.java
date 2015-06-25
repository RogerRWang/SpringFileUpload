package app;

import java.sql.Date;

public class User {
	private Long ADDRS_INFO_SID,ZIP_CODE;
	private String USR_FIRST_NAME,USR_LAST_NAME,STREET1,STREET2,CITY,STATE_CODE;
	private Date CREATED_DATE = null,MODIFIED_DATE = null;
	
	public void printUserInfo()
	{
		System.out.println(ADDRS_INFO_SID+" "+USR_FIRST_NAME+" "+USR_LAST_NAME+" "+STREET1+" "+STREET2+" "+CITY+" "+STATE_CODE+" "+ZIP_CODE+" "+CREATED_DATE+" "+MODIFIED_DATE);
	}
	
	//default constructor
	public User()
	{
		ADDRS_INFO_SID=null;
		USR_FIRST_NAME=null;
		USR_LAST_NAME=null;
		STREET1=null;
		STREET2=null;
		CITY=null;
		STATE_CODE=null;
		ZIP_CODE = null;
		CREATED_DATE=null;
		MODIFIED_DATE=null;
	}
	
	//constructor
	public User(Long ADDRS_INFO_SID,String USR_FIRST_NAME,String USR_LAST_NAME,
				String STREET1,String STREET2,String CITY,String STATE_CODE,
				Long ZIP_CODE,Date CREATED_DATE,Date MODIFIED_DATE)
	{
		this.ADDRS_INFO_SID=ADDRS_INFO_SID;
		this.USR_FIRST_NAME=USR_FIRST_NAME;
		this.USR_LAST_NAME=USR_LAST_NAME;
		this.STREET1=STREET1;
		this.STREET2=STREET2;
		this.CITY=CITY;
		this.STATE_CODE=STATE_CODE;
		this.ZIP_CODE = ZIP_CODE;
		this.CREATED_DATE=CREATED_DATE;
		this.MODIFIED_DATE=MODIFIED_DATE;
	}
	
	public Long getADDRS_INFO_SID()
	{
		return ADDRS_INFO_SID;
	}
	public String getUSR_FIRST_NAME()
	{
		return USR_FIRST_NAME;
	}
	public String getUSR_LAST_NAME()
	{
		return USR_LAST_NAME;
	}
	public String getSTREET1()
	{
		return STREET1;
	}
	public String getSTREET2()
	{
		return STREET2;
	}
	public String getCITY()
	{
		return CITY;
	}
	public String getSTATE_CODE()
	{
		return STATE_CODE;
	}
	public Long getZIP_CODE()
	{
		return ZIP_CODE;
	}
	public Date getCREATED_DATE()
	{
		return CREATED_DATE;
	}
	public Date getMODIFIED_DATE()
	{
		return MODIFIED_DATE;
	}
}
