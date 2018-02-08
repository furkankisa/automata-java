import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

public class fileReader {
	
	Vector states=new Vector();
	int circles[][]={{100,180},
					{645,332},
					{475,95},
					{480,435},
					{275,430},
					{642,198},
					{270,80},
					{110,310}};
	BufferedReader in=null;
	String temp, path="", line="";
	State newState,tmp;
	int firstLine=0;	
	
	public fileReader(String fileName){
		
		try{
			in = new BufferedReader ( new FileReader (fileName)); //getting first argument as an input text

			while((line=in.readLine())!=null){ //read a line from input
				path = line;
				StringTokenizer ST = new StringTokenizer(line); //to parse the read line string	
				if(!ST.hasMoreTokens())
					continue;
				String state = ST.nextToken();
				char symbol=' ';
				if(!state.contains("q"))
					continue;
				else{
					if(state.startsWith("q")){
						tmp=findState(state,states);
						if(tmp==null){
							newState=new State(state);
							newState.setKind("n");
							states.add(newState);
							while(ST.hasMoreTokens()){
								temp=ST.nextToken();
								if(temp.startsWith("{") && firstLine==0)
									newState.setKind("first");
								if(temp.contains("q"))
									if(firstLine==0){
										if(temp.startsWith("{")){
											temp= temp.substring(1);
											if(temp.contains("}"))
												temp= temp.substring(0,(temp.length()-1));
										}else if(temp.contains("}"))
												temp= temp.substring(0,(temp.length()-1));
										
									tmp=findState(temp,states);
										if(tmp==null){
											newState=new State(temp);
											newState.setKind("last");
											states.add(newState);
										}
										else
											tmp.setKind("firstlast");
									}else{
										if(temp.startsWith("{")){
											temp= temp.substring(1);
											if(temp.contains("}"))
												temp= temp.substring(0,(temp.length()-1));														
										}else if(temp.contains("}"))
												temp= temp.substring(0,(temp.length()-1));
										newState.addState(temp,symbol);
									}
								else
									symbol=temp.charAt(0);
							}
						}
						else
							while(ST.hasMoreTokens()){
								temp=ST.nextToken();
								if(temp.contains("q")){
									if(temp.startsWith("{")){
										temp= temp.substring(1);
										if(temp.contains("}"))
											temp= temp.substring(0,(temp.length()-1));		
									}else if(temp.contains("}"))
											temp= temp.substring(0,(temp.length()-1));
									tmp.addState(temp,symbol);
								}else
									symbol=temp.charAt(0);
							}
						firstLine=1;
					}
				}
				
			}
			
			for(int i=0; i<states.size();++i){
				
				State newState = (State) states.elementAt(i);
				newState.setX(circles[i][0]);
				newState.setY(circles[i][1]);
			}
			
		}
		
				catch(IOException e){}				
				finally{
					try {
						in.close();
					} catch (IOException e1) {}
				}
		
	}
	
	public Vector getStates() {
		return states;
	}
	public String getPath() {
		return path;
	}

	private static State findState(String state, Vector states) {
		for(int i=0;i<states.size();++i){
			if(((State)states.elementAt(i)).getStateName().equals(state)){
				return ((State)states.elementAt(i));
			}
		}
		return null;
	}
}
