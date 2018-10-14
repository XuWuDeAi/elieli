package entity;

import af.sql.annotation.AFCOLUMNS; 
import af.sql.annotation.AFTABLE; 
import java.util.Date; 

@AFTABLE(name="leaderboard")  
@AFCOLUMNS(auto=true) 
public class Leaderboard 
{ 
 
	public String name ; 
	public Integer postion ; 
	public Integer id ; 
	public String pic ; 
	public String status ; 
	public String newstatus ; 
	public String attributes ; 


	public Leaderboard(String name, Integer postion, Integer id, String pic, String status, String newstatus,
			String attributes) {
		super();
		this.name = name;
		this.postion = postion;
		this.id = id;
		this.pic = pic;
		this.status = status;
		this.newstatus = newstatus;
		this.attributes = attributes;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public String getName()
	{
		return this.name;
	}
	public void setPostion(Integer postion)
	{
		this.postion=postion;
	}
	public Integer getPostion()
	{
		return this.postion;
	}
	public void setId(Integer id)
	{
		this.id=id;
	}
	public Integer getId()
	{
		return this.id;
	}
	public void setPic(String pic)
	{
		this.pic=pic;
	}
	public String getPic()
	{
		return this.pic;
	}
	public void setStatus(String status)
	{
		this.status=status;
	}
	public String getStatus()
	{
		return this.status;
	}
	public void setNewstatus(String newstatus)
	{
		this.newstatus=newstatus;
	}
	public String getNewstatus()
	{
		return this.newstatus;
	}
	public void setAttributes(String attributes)
	{
		this.attributes=attributes;
	}
	public String getAttributes()
	{
		return this.attributes;
	}

} 
 