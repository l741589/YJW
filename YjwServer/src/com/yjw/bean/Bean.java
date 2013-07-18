package com.yjw.bean;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yjw.util.BaseBean;
import com.yjw.util.Util;

/**所有Bean的基类,各种实用包含工具，<br>
 * <i>* 注意不要引入get,set函数</i>
 */
public class Bean  extends BaseBean implements Cloneable,Serializable{
	
	private static final long serialVersionUID = 1834690976115069573L;
	static private final String packageName=Bean.class.getPackage().getName();
	
	/**构造部分*/
	/**通过map构造Bean
	 * @param map Bean所需的参数
	 * @param cls Bean的具体类型
	 * @return 构造完成的Bean
	 */
	@SuppressWarnings("unchecked")
	public static<T extends Bean> T Pack(Map<String,?> map, Class<T> cls){
		try {
			return (T)cls.newInstance().pack(map);
		} catch (InstantiationException e) {
			e.printStackTrace();		
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**通过Object构造Bean
	 * @param s Bean的描述字符串，可由toString()函数得到。
	 * @return 构造完成的Bean
	 */
	public static Bean Pack(Object s){
		if (s instanceof String){
			return Pack((String)s);
		}else{
			return new NotBean(s);
		}
	}
	
	/**通过String构造Bean
	 * @param s Bean的描述字符串，可由toString()函数得到。
	 * @return 构造完成的Bean
	 */
	@SuppressWarnings("unchecked")
	public static Bean Pack(String s){
		try {
			int i=s.indexOf('{');
			String name=s.substring(0,i);
			//System.out.println(packageName+"."+name);
			Class<? extends Bean> cls=(Class<? extends Bean>)Class.forName(packageName+"."+name);
			return Pack(s,cls);
		} catch (Exception e) {
			return new NotBean(s);
		}
	}
	
	public static Bean valueOf(String s){
		return Bean.Pack(s);
	}
	
	/**通过String构造Bean
	 * @param s Bean的描述字符串，可由Bean.toString()函数得到。
	 * @param cls Bean的具体类型
	 * @return 构造完成的Bean
	 */
	@SuppressWarnings("unchecked")
	public static<T extends Bean> T Pack(String s, Class<T> cls){ 
		try {
			return (T)cls.newInstance().pack(s);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**通过Map构造Bean
	 * @param map Bean所需的参数
	 * @return 构造完成的Bean
	 */
	public Bean pack(Map<String,?> map){
		Method[] methods=getClass().getMethods();
		try{
			for (Method method:methods){
				String name=method.getName();
				if (name.length()>3&&name.substring(0,3).equals("set")){
					Object value=map.get(method.getName().substring(3).toLowerCase());
					if (value==null) continue;
					Class<?> parType=method.getParameterTypes()[0];
					method.invoke(this,cast(value,parType));
				}
			}
		}catch(Exception e){
			System.err.println("UnknownPackException");
			map.clear();
			e.printStackTrace();
		}
		return this;
	}
	
	/**通过String构造Bean
	 * @param s Bean的描述字符串，可由toString()函数得到。
	 * @return 构造完成的Bean
	 */
	public Bean pack(String s){
		String sub=s.substring(s.indexOf('{')+1,s.length()-1);
		List<String> pairs=split(sub, ',');
		Map<String,String> map=new HashMap<String, String>();
		for (String pair:pairs){
			int i=pair.indexOf('=');
			String key=pair.substring(0,i);
			String value=pair.substring(i+1);
			map.put(key, value);
		}
		return pack(map);
	}
	
	/**工具部分*/
	static public List<String> split(String params,char ch) {
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
	
	static public<T> T[] strToArr(String value,Class<T> cls){
		value=value.substring(1,value.length()-1);
		List<String> ss=split(value,',');
		List<Object> list=new ArrayList<Object>();
		for (String s:ss){
			list.add(Bean.Pack(s).toObject());
		}
		return Util.list2Arr(list, cls);
	}
	
	static public List<Bean> strToArr(String value){
		value=value.substring(1,value.length()-1);
		List<String> ss=split(value,',');
		List<Bean> list=new ArrayList<Bean>();
		for (String s:ss){
			list.add(Bean.Pack(s));
		}
		return list;
	}
	
	static public String arrToStr(Object[] arr){
		if (arr.length==0) return "{}";
		String value="";
		boolean first = true;
		for (Object e:arr){ 
			if (first){ first=false; value+="{";} else value+=","; 
			if (e instanceof Bean){			
				value+=e;
			}else{
				value+=cast(e);
			}
		}
		value+="}";
		return value;
	}
	
	
	
	/**将实例o转化为cls类型
	 * 请保证o的类型与cls当中至少有一个是String类 或者 两个类型相同(即不需要转换)
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static public <T> T cast(Object o,Class<T> cls){
		if (cls.getSimpleName().equals("int")) cls=(Class<T>)Integer.class; else
		if (cls.getSimpleName().equals("long")) cls=(Class<T>)Long.class; else
		if (cls.getSimpleName().equals("float")) cls=(Class<T>)Float.class; else
		if (cls.getSimpleName().equals("double")) cls=(Class<T>)Double.class;
		if (cls.isInstance(o)) return (T)o; else
		if (o instanceof String){
			String s=(String)o;
			if (Integer.class.equals(cls)){
				return (T)Integer.valueOf(s);
			}else if (Long.class.equals(cls)){
				return (T)Long.valueOf(s);
			}else if (Float.class.equals(cls)){
				return (T)Float.valueOf(s);
			}else if (Double.class.equals(cls)){
				return (T)Double.valueOf(s);
			}else if (Date.class.equals(cls)){
				return (T)Date.valueOf(s);
			}else if (Timestamp.class.equals(cls)){
				return (T)Timestamp.valueOf(s);
			}else if (cls.isEnum()){
				return (T)Enum.valueOf((Class<? extends Enum>)cls, s);
			}else if (Bean.class.isAssignableFrom(cls)){
				return (T)Bean.valueOf(s);
			}else if (cls.isArray()){
				return (T)strToArr(s,cls.getComponentType());
			}
		}else if (cls.equals(String.class)){ 
			return (T)cast(o);
		}else if (Integer.class.equals(cls)&&Long.class.isInstance(o)){
			return (T)Integer.valueOf(o.toString());
		}else if (Long.class.equals(cls)&&Integer.class.isInstance(o)){
			return (T)(Integer)o;
		}else if (Float.class.equals(cls)&&Double.class.isInstance(o)){
			return (T)Float.valueOf(o.toString());
		}else if (Double.class.equals(cls)&&Float.class.isInstance(o)){
			return (T)o;
		}
		System.err.println("cast: UnknowType: "+o.getClass().getName()+" to Type "+cls.getName());
		return null;
	}
	
	static public String cast(Object o){
		try{
			if (o==null) return null;
			if (o instanceof String){	
				return(String)o;						
			}else if (o instanceof Integer){ 
				return((Integer)o).toString();
			}else if (o instanceof Long){ 
				return((Long)o).toString();
			}else if (o instanceof Float){ 
				return((Float)o).toString();
			}else if (o instanceof Double){ 
				return((Double)o).toString();
			}else if (o instanceof byte[]){
				return "?";
			}else if (o.getClass().isArray()){
				return arrToStr((Object[])o);
			}else{
				return o.toString();
			}
		}catch(Exception e){
			e.printStackTrace();
			return "?";
		}		
	}
	
	/**输出部分*/
	
	@Override
	public String toString() {
		Map<String,Object> map=toMap();
		String ret="";
		boolean start=true;
		Set<String> keys=map.keySet();
		for (String key:keys){
			String value=cast(map.get(key));
			if (value!=null) {
				if (start) start=false; else ret+=",";
				ret+=key+"="+value;
			}
		}
		return getClass().getSimpleName()+"{"+ret+"}";
	}
	
	public Object toObject(){
		return this;
	}
	
	public Map<String,Object> toMap(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.clear();
		Method[] methods=getClass().getMethods();
		try{
			for (Method method:methods){
				String name=method.getName();
				if (name.length()>3&&name.substring(0,3).equals("get")&&!name.equals("getClass")){
					Object value=method.invoke(this);
					if (value==null) continue;
					String key=method.getName().substring(3).toLowerCase();
					map.put(key, value);
				}
			}
		}catch(Exception e){
			System.err.println("UnknownPackerException");
			map.clear();
			e.printStackTrace();
		}
		return map;
	}
	
	public<T extends Bean> T to(Class<T> cls){
		return Bean.Pack(toMap(),cls);
	}
	
	public Bean clone(){
		return Bean.Pack(toMap(),getClass());
	}
	
	public boolean isBean(){
		return true;
	}
	
	public String insert(String table){
		Map<String,?> map=toMap();
		boolean start=true;
		String field="";
		String value="";
		Set<String> keys=map.keySet();
		for (String key:keys){
			if (start) start=false; else {
				field+=",";
				value+=",";
			}
			field+=key;			
			if (key.equals("password")){
				value+=passwordValue(cast(map.get(key)));
			}else{
				value+="'"+cast(map.get(key))+"'";
			}
		}
		String ret="INSERT INTO "+table+"("+field+") VALUES("+value+")";
		//System.out.println(ret);
		return ret;
	}
	
	public String update(String table){
		String s="UPDATE "+table+" SET ";
		Map<String,?> map=toMap();
		boolean start=true;		
		Set<String> keys=map.keySet();
		for (String key:keys){
			if (start) start=false; else s+=","; 
			String value;
			if (key.equals("password")){
				value=passwordValue(cast(map.get(key)));
			}else{
				value="'"+cast(map.get(key))+"'";
			}
			s+=key+"="+value;
		}
		s+=" WHERE id='"+cast(map.get("id"))+"'";
		//System.out.println(s);
		return s;
	}
}
