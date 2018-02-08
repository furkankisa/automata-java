import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import javax.swing.*;
import java.util.*;

public class chartPanel extends JPanel{
	

	private Vector running, ss;
	
	public  chartPanel(Vector running, Vector states){
		this.running = running;
		this.ss = states;
		
	}
	
	public void paint(Graphics g, String situation){
		State tmp,nextState;
	    double angle = 0,w = 0,h = 0;
	    double length=0;
	    String acr;
		
		super.paint(g);
		g.setColor(Color.yellow);
		
		if(situation.equals("Accepted"))		
			for(int i=0;i<running.size();++i){
				State temp = (State)running.elementAt(i);
				if(temp.getKind().contains("last"))
					g.setColor(Color.green);
				else
					g.setColor(Color.yellow);
				g.fillOval(temp.getX()+1, temp.getY()+1, 49, 49);
			}
		else
			for(int i=0;i<running.size();++i){
				State temp = (State)running.elementAt(i);
				g.fillOval(temp.getX()+1, temp.getY()+1, 49, 49);
			}
		g.setColor(Color.BLACK);
		g.drawLine(1,500,800,500);
		
		for(int i=0;i< ss.size();++i){

			tmp = (State)ss.elementAt(i);
			int x= tmp.getX();
			int y= tmp.getY();
			
			String name= tmp.getStateName();
			g.drawOval(x,y,50,50);
			g.drawString(name,x+20,y+30);
			
			Enumeration enum = tmp.table.keys();
			while(enum.hasMoreElements()){
				
				Character key = (Character)enum.nextElement();
				Vector tmpStd = (Vector) tmp.table.get(key);
				for(int j=0;j<tmpStd.size();++j){
					
					acr         = key+"";
					nextState = (State)getState((String)tmpStd.elementAt(j));
					
					w = nextState.getX()-x;
				    h = nextState.getY()-y;
				    angle = Math.atan(h/w);
				    if(h>0&&w>0){
				    	length=Point2D.distance(x+50,y+20,nextState.getX(),nextState.getY()+25);
				    	angle= angle-(angle*2);
				    	g.drawString(acr,(x+55+nextState.getX())/2,(y+45+nextState.getY())/2);
				    	x=x+50;y=y+20;
				    }else if(w<0&&h>0){
				    	length=Point2D.distance(x,y+20,nextState.getX()+50,nextState.getY()+25);
				    	g.drawString(acr,(x+30+nextState.getX())/2,(y+30+nextState.getY())/2);
				    	angle= angle+(Math.PI-(angle*2));
				    	y=y+20;
				    }else if(h<0&&w>0){
				    	length=Point2D.distance(x+50,y+25,nextState.getX(),nextState.getY()+25);
				    	g.drawString(acr,(x+100+nextState.getX())/2,(y+60+nextState.getY())/2);
				    	angle= angle+ (((Math.PI*2)-angle)*2);
				    	x=x+50;y=y+25;
				    }else if(w<0&&h<0){ 
				    	length=Point2D.distance(x,y+25,nextState.getX()+50,nextState.getY()+25);
				    	g.drawString(acr,(x+30+nextState.getX())/2,(y+110+nextState.getY())/2);
				    	angle= angle+(Math.PI-(angle*2));
				    	y=y+25;
				    }else if(h<0&&w==0){
				    	length=Point2D.distance(x+10,y+4,nextState.getX()+30,nextState.getY()+45);
				    	g.drawString(acr,(x+20+nextState.getX())/2,(y+35+nextState.getY())/2);
				    	angle= angle-(angle*2);
				    	x=x+10;y=y+4;
				    }else if(h>0&&w==0){
				    	length=Point2D.distance(x+40,y+46,nextState.getX()+30,nextState.getY()+5);
				    	g.drawString(acr,(x+65+nextState.getX())/2,(y+50+nextState.getY())/2);
				    	angle= angle+ (((Math.PI*2)-angle)*2);
				    	x=x+40;y=y+46;
				    }else if(h==0&&w>0){
				    	length=Point2D.distance(x+50,y+15,nextState.getX(),nextState.getY()+25);
				    	g.drawString(acr,(x+50+nextState.getX())/2,(y+40+nextState.getY())/2);
				    	angle= angle+ (((Math.PI*2)-angle)*2);;
				    	x=x+50;y=y+15;
				    }else if(h==0&&w<0){
				    	length=Point2D.distance(x,y+35,nextState.getX()+50,nextState.getY()+25);
				    	g.drawString(acr,(x+50+nextState.getX())/2,(y+75+nextState.getY())/2);
				    	angle= angle+(Math.PI-(angle*2));
				    	y=y+35;
				    }if(h!=0&&w!=0){	
				    	drawArrow(g,x,y,angle,(int)length,0);
				    	x= tmp.getX();
				    	y= tmp.getY();
				    }
				}				
			}
			
			if(tmp.kind.contains("last"))
				g.drawOval(x+5,y+5,40,40);
			if(tmp.kind.contains("first"))
				g.drawLine(x-20,y+25,x,y+25);
		}
	}
	public void drawArrow(Graphics g,int x,int y,double theta,int length,int side) {		
		if (length < 0) { 
			theta+=Math.PI;
			length*=-1;
		}
		int x1,y1;
		x1=(int)Math.ceil(x + length*Math.cos(theta));
		y1=(int)Math.ceil(y - length*Math.sin(theta));
		g.drawLine(x,y,x1,y1);
			
		if(side== 0) {
			drawArrow(g,x1,y1,theta+5*Math.PI/4,5,3);
			drawArrow(g,x1,y1,theta+3*Math.PI/4,5,3);
		}
	}
	public State getState(String name){
		
		String stateName;
		
		for(int i=0;i<ss.size();++i){
			stateName = ((State)ss.elementAt(i)).getStateName();
			
			if(stateName.equals(name))
				return (State)ss.elementAt(i);
		}
		
		return null;
	}
}
