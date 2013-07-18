package com.yjw.ws;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

@WebService(targetNamespace="http://tempuri.org/")
public class SendSoapMessage {
	public static void buildSoapMessage() {
		try {
			SOAPConnectionFactory conntools = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = conntools.createConnection();
			
			// 创建消息工厂
			MessageFactory factory = MessageFactory.newInstance();
			
			// 创建soap消息reqMsg
			SOAPMessage reqMsg = factory.createMessage();
			reqMsg.setProperty("Content-Type", "text/xml;charset=utf-8");
			// 创建soap消息的部分reqMsgpart
			SOAPPart part = reqMsg.getSOAPPart();
			// 创建sope信封envelope，要开始写信了
			SOAPEnvelope envelope = part.getEnvelope();
			envelope.setPrefix("soap");
			envelope.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
			envelope.setAttribute("xmlns:xsd","http://www.w3.org/2001/XMLSchema");
			envelope.setAttribute("xmlns:soap","http://schemas.xmlsoap.org/soap/envelope/");
			envelope.removeAttribute("xmlns:SOAP-ENV");
			
			// 写header
			envelope.getHeader().recycleNode();

			// 写BODY
			SOAPBody body = envelope.getBody();
			body.setPrefix("soap");
			SOAPElement bodyElement = body.addChildElement("SendMessages","","http://tempuri.org/");
			bodyElement.addChildElement("uid").addTextNode("tjadri");
			bodyElement.addChildElement("pwd").addTextNode("zhaoping");
			bodyElement.addChildElement("tos").addTextNode("13917774194");
			bodyElement.addChildElement("uid").addTextNode("dsadsadsa");
			bodyElement.addChildElement("otime").addTextNode("");
			
			reqMsg.saveChanges();
			
			try {
				URL url =new URL("http://service2.winic.org/Service.asmx");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
				conn.setRequestProperty("Content-Length",String.valueOf(reqMsg.toString().length()));
				conn.setRequestProperty("SOAPAction", "http://tempuri.org/SendMessages");
				conn.setDoOutput(true);
				
				OutputStream out = conn.getOutputStream();
				OutputStreamWriter outw = new OutputStreamWriter(out,"utf-8");
				outw.write(reqMsg.toString());
				outw.flush();
				outw.close();
				InputStream is = conn.getInputStream();;
				System.out.println(is);
			//	connection.call(reqMsg,url);
				try {
					reqMsg.writeTo(System.out);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("\n====================发送的消息:");
				
				// SOAPMessage respMsg = connection.call(reqMsg, endPoint);
				// System.out.println("\n服务端返回的信息- : " + getResult(respMsg));
				System.out.println("\n\n====================接收的消忿");

				System.out.print("Success!");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void sendByUrl(){
/*		URL url;
		try {
			url = new URL("http://service2.winic.org/Service.asmx/SendMessages");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("uid", "tjadri");
			conn.setRequestProperty("pwd", "zhaoping");
			conn.setRequestProperty("tos", "13917774194");
			conn.setRequestProperty("msg", "dasdasda");
			conn.setRequestProperty("otime", "");
			conn.setDoOutput(true);
			OutputStream out = conn.getOutputStream();
			OutputStreamWriter outw = new OutputStreamWriter(out,"utf-8");
			outw.flush();
			outw.close();
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Map<String, String> map = new HashMap<String, String>();
		map.put("uid", "tjadri");
		map.put("pwd", "zhaoping");
		map.put("tos", "13917774194");
		map.put("msg", "你好");
		map.put("otime", "");
		System.out.println("map:"+map);
		new HttpPostUtils();
		String aString = HttpPostUtils.httpPost("http://service2.winic.org/Service.asmx/SendMessages", map);
		System.out.println(aString);
	}
	
    public static void main(String[] args){
    	new SendSoapMessage().sendByUrl();
    }
}
