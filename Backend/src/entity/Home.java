package entity;


import af.sql.annotation.AFCOLUMNS; 
import af.sql.annotation.AFTABLE; 
import java.util.Date; 

@AFTABLE(name="home")  
@AFCOLUMNS(auto=true) 
public class Home 
{ 
 
	public String pic ; 
	public String name ; 
	public String id ; 
	public String status ; 


	public Home(String pic, String name, String id, String status) {
		super();
		this.pic = pic;
		this.name = name;
		this.id = id;
		this.status = status;
	}
	public void setPic(String pic)
	{
		this.pic=pic;
	}
	public String getPic()
	{
		return this.pic;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public String getName()
	{
		return this.name;
	}
	public void setId(String id)
	{
		this.id=id;
	}
	public String getId()
	{
		return this.id;
	}
	public void setStatus(String status)
	{
		this.status=status;
	}
	public String getStatus()
	{
		return this.status;
	}

} 
 