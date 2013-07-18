package com.yjw.test;

import java.util.ArrayList;

public class TesterGroup extends Tester {
	
	private static final long serialVersionUID = -2630470622280357019L;
	private ArrayList<Tester> testers;
	/**0为并发执行所有，1为不并发顺序执行，>1为执行指定并发数*/
	private int count;
	
	@Override
	public String getPath() {
		if (path.equals("")) path+=getAction();
		return path;
	}
	
	public void addTester(Tester tester){
		testers.add(tester);
		tester.setPath(getPath()+"/"+tester.getAction());
	}

	public TesterGroup(int count,Tester...testers) {
		super(".");
		this.count=count;
		this.testers=new ArrayList<Tester>();
		for (Tester t:testers) addTester(t);
	}
	
	public TesterGroup(String action,int count,Tester...testers){
		super(action);
		this.count=count;
		this.testers=new ArrayList<Tester>();
		for (Tester t:testers) addTester(t);
	}
	
	@Override
	public void test() {
		System.out.println("### "+getClass().getSimpleName()+" Entered ###");
		init();
		if (count==0){
			try {
				Holder.pool.invokeAll(testers);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else if (count==1) {
			for (Tester e:testers) e.run();
		}else{
			ArrayList<Tester> threads = new ArrayList<Tester>();
			for(int i = 0; i < count; i++)
				threads.add(Tester.clone(testers.get((int)(Math.random()*testers.size()))));
			try{
				Holder.pool.invokeAll(threads);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		System.out.println("### "+getClass().getSimpleName()+" Left ###");
	}
}
