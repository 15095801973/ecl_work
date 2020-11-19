package mycore;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import servlet.Constant;
import test.savePngtest;
 
public class MulitpartData_pred { // 仅包含必须的数据
 
	public String boundary=null;
	public String filename=null;
	public HashMap<String, String>mmMap= new HashMap<String, String>();
	public HashMap<String, byte[]>dtMap= new HashMap<String, byte[]>();
    // 当前循环执行的动作类型
    enum ActionType{
        TO_FIND_BOUNDARY_START, // 要找其实占位符 "--zhanweifu"
        TO_FIND_BOUNDARY_DESC_END, // 要找描述头的计数标志 "\r\n\r\n"
        TO_FIND_CONTENT_END // 要找数据的结束标志 "\r\n"
    }
 
    // 当前取到的数据类型
    enum DataType {
        JSON_TYPE,
        AUDIO_TYPE,
        ZERO_TYPE
    }
 
    public void parse(Request request, InputStream inputStream) throws IOException {
        // 读取出来的数据存储两个对象
        // 一个是StringBuilder对象,主要用 index 函数来查找 关键的特征字符串,如起始zhanweifu, \r\n, \r\n\r\n 等
        // 一个是unprocessedArr,保存所有读到de buffer
        byte[] unprocessedArr = new byte[22000];
        StringBuilder unprocessedSB = new StringBuilder();
 
        int unprocessedCount = 0; // 记录当前未做处理的 索引值
        int pos = 0; // 记录当前未做处理的 索引值
      
        int totalReadCount = 0; // 记录当前总读出的字节数
 
        // 初始化actionType, dataType, 和 循环跳出条件
        ActionType actionType = ActionType.TO_FIND_BOUNDARY_START;
        DataType dataType = DataType.ZERO_TYPE;
        boolean notTerminated = true;
 
        int len = request.getContent_Length();
        byte[] buffer ;
        buffer = new byte[len];
        int availableCount = 0;
        int readCount = 0;
        System.out.println("总共 字节数 "+request.getContent_Length());
        Date before=new Date();
        Date now;
        while(totalReadCount <len){
            // 读取新的buffer出来,并存入到 unprocessedSB 和 unprocessedArr
//        	int firstByte = inputStream.read();
            availableCount = inputStream.available();
            if(availableCount!=0) {
            	System.out.println("availabel "+availableCount);
            }
//            readCount = inputStream.read(buffer, 0, availableCount);
            readCount = inputStream.read(buffer, 0, availableCount);
            if (readCount == -1){
            	System.out.println("读完body了,break");
                notTerminated = false;
                break;
                // break 读到EOF,结束
            }
            String bufferStr = new String(buffer,"ascii");
            System.out.println("read ...."+readCount);
            System.out.println(bufferStr);
            unprocessedSB = unprocessedSB.append(bufferStr);
            System.arraycopy(buffer, 0, unprocessedArr, totalReadCount, readCount);
            totalReadCount += readCount;
            if (readCount == 0){
//            	if(totalReadCount >=request.getContent_Length()) {
            		System.out.println("break");
//            	notTerminated = false;
                 break;
            	}
//            	try {now = new Date();
//                // 现在的时间减去开始的时间可以计算出来使用的时间
//                	long t = (now.getTime() - before.getTime())/1000;
//                	if(t>100) {
//                		System.out.println("超时..");
//                		break;
//                	}
//					Thread.sleep(200);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//            	continue;
//            }
            
 
//            // 判断是否已取到结束帧
//            if(unprocessedSB.indexOf("--"+boundary+"--") !=-1) {
//            if (unprocessedSB.indexOf("--"+boundary+"--")+("--"+boundary+"--").length() <=unprocessedCount) {
//            	System.out.println("index:"+unprocessedSB.indexOf("--"+boundary+"--"));
//                notTerminated = false;
//            }
//            }
            // 解析数据
            // 在对应的 actionType 下, 查找对应的目标字符串
            // 去到一帧完整的json或audio type的数据之后就做下一步处理
            while(unprocessedCount+2<totalReadCount) {//最后有/r/n,故+2
            switch (actionType){
                case TO_FIND_BOUNDARY_START: {
                    int findCount = unprocessedSB.indexOf("--"+boundary, unprocessedCount);
                    if (findCount != -1) {
                        actionType = ActionType.TO_FIND_BOUNDARY_DESC_END;
                        unprocessedCount = findCount + ("--"+boundary+"\r\n").length();
//                        pos =findCount+ ("--"+boundary+"\r\n").length();
                    }
                    break;
                }
                case TO_FIND_BOUNDARY_DESC_END: {
                    int findCount = unprocessedSB.indexOf("\r\n\r\n", unprocessedCount);
                    if (findCount != -1) {
                    	System.out.println("// 取出一帧数据的描述头");
                        // 取出一帧数据的描述头
                        String descHeadStr = unprocessedSB.substring(unprocessedCount, findCount);
                        System.out.println(descHeadStr);
                        // 更新 dataType
                        dataType = getDataType(descHeadStr);
                        // dataLength = getDataLength(descHeadStr);
                        actionType = ActionType.TO_FIND_CONTENT_END;
                        unprocessedCount = findCount + "\r\n\r\n".length();
                    }
                    break;
                }
                case TO_FIND_CONTENT_END: {
//                    int findCount = unprocessedSB.indexOf("\r\n", unprocessedCount);
                	//不能简单地以为这就结束了
                    int findCount = unprocessedSB.indexOf("--"+boundary, unprocessedCount)-2;
                    // -2 是因为 \r\n
                    if (findCount != -1) {
                        switch (dataType){
                            case JSON_TYPE: {
                            	System.out.println("// 至此,取到了一帧完整的json字符串");
                                // 至此,取到了一帧完整的json字符串
                                String jsonContentStr = unprocessedSB.substring(unprocessedCount, findCount);
                                System.out.println(jsonContentStr);
                                mmMap.put(filename, jsonContentStr);
                                actionType = ActionType.TO_FIND_BOUNDARY_START;
                                unprocessedCount = findCount + "\r\n".length();
                                break;
                            }
                            case AUDIO_TYPE:{
                                if (unprocessedSB.indexOf("--"+boundary, findCount) != -1) {
                                	System.out.println( "// 至此,取到了一帧完整的二进制音频数据");
                                	System.out.println("文件长度为: "+(findCount - unprocessedCount));
                                    // 至此,取到了一帧完整的二进制音频数据
                                    byte[] audioArr = new byte[findCount - unprocessedCount];
                                    System.arraycopy(unprocessedArr, unprocessedCount, audioArr, 0, findCount-unprocessedCount);
//                                  先保存原二进制编码, 等待后续处理
//                                    savePngtest.saveByByte(audioArr, Constant.MEDIA_DIR+filename);
                                    dtMap.put(filename, audioArr);
                                    actionType = ActionType.TO_FIND_BOUNDARY_START;
                                }
                                unprocessedCount = findCount + "\r\n".length();
                                break;
                            }
                        }
 
                    }
                    break;
                }
                default:
                    break;
            }//while
            }
        }
//        inputStream.close();
    }
 
    private DataType getDataType(String descHeadStr){
    	if(descHeadStr.indexOf("filename=")!= -1) {
    		Pattern pattern = Pattern.compile("filename=\"(.*?)\"");
    		Matcher matcher = pattern.matcher(descHeadStr);
    		if(matcher.find()) {
    		System.out.println(matcher.group(1));
    		this.filename = matcher.group(1);
    		return DataType.AUDIO_TYPE;
    		}
    		else {
    			System.out.println("没有找到 filename");
    		}
    		
//    		String[] ds = descHeadStr.split(";");
//    		for(String s:ds) {
//    			String[] es = s.split("=");
//    			if(es.length==2) {
//    				if(es[0].endsWith("filename")) {
//    					this.filename = es[1].replace("\"", "").trim();
//    					return DataType.AUDIO_TYPE;
//    				}
//    			}
//    		}
    		
    	}
    	else {
    		String[] ds = descHeadStr.split(";");
    		for(String s:ds) {
    			String[] es = s.split("=");
    			if(es.length==2) {
    				if(es[0].endsWith("name")) {
    					this.filename = es[1].replace("\"", "").trim();
    		    		return DataType.JSON_TYPE; // 先假设本段是json,实际Type 从 descHead里做字符串解析
    				}
    			}
    		}

    	}
    	return DataType.ZERO_TYPE;

    }
}