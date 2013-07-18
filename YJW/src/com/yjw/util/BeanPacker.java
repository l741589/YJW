package com.yjw.util;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**通过调用一个bean中的get函数构造一个map*/
/**可以通过该map构造SQL插入语句*/
@Deprecated
public class BeanPacker {
	private Class<?> beanclass;
	private Object bean;
	private Map<String,String> map;
	
	public Map<String,String> getMap(){
		return map;
	}
	
	public static boolean isBean(Object o){
		return isBean(o.getClass());
	}
	public static boolean isBean(Class<?> c){
		String classname=c.getSimpleName();
		return classname.substring(classname.length()-4).equals("Bean");
	}
	private String listToStr(List<?> list){
		String value="";
		boolean first = true;
		for (Object e:list){ 
			if (isBean(e)){
				if (first){ first=false; value+="{";} else value+=","; 
				value+=new BeanPacker(e).toString();
			}
		}
		value+="}";
		return value;
	}
	private List<Object> StrToList(String value){
		value=value.substring(1,value.length()-1);
		List<String> ss=split(value,',');
		List<Object> list=new ArrayList<Object>();
		for (String s:ss) list.add(new BeanPacker(s).getBean());
		return list;
	}
	
	private List<String> split(String params,char ch) {
		ArrayList<String> ret=new ArrayList<String>();
		int p=0;
		int bc=0;
		for (int i=0;i<params.length();++i){
			int c=params.charAt(i);
			switch(c){
			case '{':++bc;break;
			case '}':--bc;break;
			default:if (ch==c){
				if (bc==0){
					ret.add(params.substring(p,i));
					p=i+1;
				}
			}break;
			}
		}
		if (p<params.length()) ret.add(params.substring(p,params.length()));
		return ret;
	}
	
	private String ObjToStr(Object o){
		String value = null;
		if (o==null) return null;
		if (o instanceof String){	
			value=(String)o;						
		}else if (o instanceof String[]){
			value=((String[])o)[0];
		}else if (o instanceof Integer){ 
			value = ((Integer)o).toString();
		}else if (o instanceof Long){ 
			value = ((Long)o).toString();
		}else if (o instanceof Float){ 
			value = ((Float)o).toString();
		}else if (o instanceof Timestamp){
			value =((Timestamp)o).toString();
		}else if (o instanceof Date){
			value=((Date)o).toString();
		}else if (o instanceof List){
			value=listToStr((List<?>)o);
		}else if (isBean(o)){
			value = new BeanPacker(o).toString();
		}else{
			System.err.println("InvertMap: UnknowType: "+o.getClass().getName()+"\n");
		}		
		return value;
	}
	
	//Map-Bean,List-Array,
	private void InvertMap(Map<String,?> map){
		Set<String> keys=map.keySet();
		for (String key:keys){
			Object o=map.get(key);
			String value=ObjToStr(o);
			if (value==null) continue;
			this.map.put(key, value);
		}		
	}
	
	private void initByMap(){		
		Method[] methods=beanclass.getMethods();
		try{
			bean = beanclass.newInstance();
			for (Method method:methods){
				if (method.getName().substring(0,3).equals("set")){
					String value=map.get(method.getName().substring(3).toLowerCase());
					if (value==null) continue;
					Class<?> parType=method.getParameterTypes()[0];
					if (parType.getSimpleName().equals("String"))	method.invoke(bean,value); else
					if (parType.getSimpleName().equals("Integer")||parType.getSimpleName().equals("int"))method.invoke(bean,Integer.valueOf(value)); else
					if (parType.getSimpleName().equals("Float")||parType.getSimpleName().equals("float")) method.invoke(bean,Float.valueOf(value)); else
					if (parType.getSimpleName().equals("Long")||parType.getSimpleName().equals("long")) method.invoke(bean,Long.valueOf(value)); else
					if (parType.getSimpleName().equals("Timestamp")) method.invoke(bean,Timestamp.valueOf(value)); else
					if (parType.getSimpleName().equals("Date")) method.invoke(bean,Date.valueOf(value)); else
					if (parType.isEnum()) method.invoke(bean,Enum.valueOf((Class<? extends Enum>)parType, value)); else
					if (isBean(parType)) method.invoke(bean,new BeanPacker(value).getBean()); else						
					if (parType.isArray()){
						List<Object> list=StrToList(value);
						Object[] o=(Object[])Array.newInstance(parType.getComponentType(), list.size());					
						list.toArray(o);
						method.invoke(bean,(Object)o); 
					}else
						System.err.println("UnknownType");
				}
			}
		}catch(Exception e){
			System.err.println("UnknownPackerException");
			map.clear();
			e.printStackTrace();
		}		
	}
	
	public BeanPacker(Object bean) {
		this.bean=bean;
		if (!isBean(bean)) return;
		map = new HashMap<String, String>();
		map.clear();
		beanclass=this.bean.getClass();
		Method[] methods=beanclass.getMethods();
		try{
			for (Method method:methods){
				if (method.getName().substring(0,3).equals("get")&&!method.getName().equals("getClass")){
					Object ret=method.invoke(bean);
					//System.out.print("return:"+ret);
					if (!Util.isEmpty(ret)){
						if (ret instanceof List){
							if (((List<?>)ret).size()>0)
								map.put(method.getName().substring(3).toLowerCase(), listToStr((List<?>)ret));
						}else if (ret instanceof Object[]){	
							if (((Object[])ret).length>0)
								map.put(method.getName().substring(3).toLowerCase(), listToStr(Arrays.asList((Object[])ret)));
						}else if (isBean(ret)){
							map.put(method.getName().substring(3).toLowerCase(),new BeanPacker(ret).toString());
						}else{
							map.put(method.getName().substring(3).toLowerCase(),ret.toString());
						}
					}
				}
			}
		}catch(Exception e){
			System.err.println("UnknownPackerException");
			map.clear();
			e.printStackTrace();
		}
	}
	
	public BeanPacker(Map<String,?> map, Class<?> beanclass){
		this.map = new HashMap<String, String>();
		InvertMap(map);
		this.beanclass = beanclass;
		initByMap();
	}
	
	public BeanPacker(String params){
		try{
			String className="com.yjw.bean."+params.substring(0,params.indexOf('{'));
			params=params.substring(params.indexOf('{')+1,params.length()-1);
			Class<?> beanclass=Class.forName(className);
			map = new HashMap<String, String>();
			List<String> fields=split(params,',');
			for (String field:fields){
				List<String> s=split(field,'=');
				map.put(s.get(0), s.get(1));				
			}
			this.beanclass=beanclass;
			initByMap();
		} catch(Exception e){
			System.err.println("UnknownPackerException");
			if (map!=null) map.clear();
			e.printStackTrace();
		}
	}
	
	

	public Object getBean(){
		return bean;
	}
	
	public <T> T getBean(Class<T> cls){
		return (T)bean;
	}
	/**构造SQL插入语句
	 * @param table 要插入的表
	 * @return SQL语句
	 * */
	public String insert(String table){
		if (map==null) return "insert into "+table+"() values()";
		String field="";
		String value="";
		Set<String> keys=map.keySet();
		for (String key:keys){
			if (!field.equals("")) field+=",";
			field+=key;
			
			if (!value.equals("")) value+=",";
			value+="'";
			value+=map.get(key);
			value+="'";
		}
		String ret="insert into "+table+"("+field+") values("+value+")";
		System.out.println(ret);
		return ret;
	}
	
	public String update(String table){
		String s="UPDATE "+table+" SET ";
		boolean start=true;
		Set<String> keys=map.keySet();
		for (String key:keys){
			if (start){
				s+=key+"='"+map.get(key)+"'";						
				start=false;
			}else{
				s+=",";
				s+=key+"='"+map.get(key)+"'";
			}
		}
		s+=" WHERE id='"+map.get("id")+"'";
		System.out.println(s);
		return s;
	}
	
	@SuppressWarnings("unchecked")
	public<T> T transTo(Class<T> targetClass){
		return (T)new BeanPacker(map,targetClass).getBean();
	}
	
	public String toString(){
		if (map==null) return ObjToStr(bean);
		String ret="";
		Set<String> keys=map.keySet();
		for (String key:keys){
			if (!ret.equals("")) ret+=",";
			String value=""+map.get(key);
			if (value!=null){
				ret+=key+"="+value;
			}
		}
		return bean.getClass().getSimpleName()+"{"+ret+"}";
	}
}
