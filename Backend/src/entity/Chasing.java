package entity;



import af.sql.annotation.AFCOLUMNS; 
import af.sql.annotation.AFTABLE; 
import java.util.Date; 

@AFTABLE(name="chasing")  
@AFCOLUMNS(auto=true) 
public class Chasing 
{ 
 
	public String name ; 
	public String pic ; 
	public String status ; 
	public Integer id ; 
	public String week ; 


	public Chasing(String name, String pic, String status, Integer id, String week) {
		super();
		this.name = name;
		this.pic = pic;
		this.status = status;
		this.id = id;
		this.week = week;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public String getName()
	{
		return this.name;
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
	public void setId(Integer id)
	{
		this.id=id;
	}
	public Integer getId()
	{
		return this.id;
	}
	public void setWeek(String week)
	{
		this.week=week;
	}
	public String getWeek()
	{
		return this.week;
	}

} 
 