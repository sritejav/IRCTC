package com.finacle.util;

import java.util.Hashtable;


public class TestUtil {

	
	public static Object[][] getData(String sheetName,String testCase,Xls_Reader xls) {
		
		// No of row which the testcase is starting
		// No of rows
		// No of columns
		
		// No of row which the testcase is starting
		int testStartRowNum=1;
		while(!xls.getCellData(sheetName,0,testStartRowNum).equals(testCase)){ // LoginTest
			testStartRowNum++;
		}
		//System.out.println(testCase +"   is starting from-------"+testStartRowNum);
		
		//No  of Rows
		int dataStartRowNum=testStartRowNum+2;
		int rows=0;
		while(!xls.getCellData(sheetName,0,dataStartRowNum+rows).equals("")){
			rows++;
			
		}
	//	System.out.println("No of rows="+rows);
		
		// No of colums
		int colStartRowNum=testStartRowNum+1;
		int cols=0;
		while(!xls.getCellData(sheetName, cols,colStartRowNum).equals("")){
			cols++;
		}
		//System.out.println("total no column "+testCase+" "+cols);
		
		
		Object testData[][]=new Object[rows][1];
		int index=0;
		Hashtable<String,String> table=null;
		
		//data extracting
		for(int rNum=dataStartRowNum;rNum<dataStartRowNum+rows;rNum++ ){
			table=new Hashtable<String,String>();
			for(int cNum=0;cNum<cols;cNum++){
			String key=xls.getCellData(sheetName,cNum ,colStartRowNum);	
			String value=xls.getCellData(sheetName,cNum, rNum);
			//System.out.println(value+"----");
			table.put(key,value);
			
		}
			//System.out.println("");
			testData[index][0]=table;
			index++;
		}
		
		return testData;

	}
	
	// Check the TestCase MOde
	public static boolean getrunmode(String testCase,Xls_Reader xls){
		for(int rNum=2;rNum<=xls.getRowCount("TestCases");rNum++){
			String testCaseName=xls.getCellData("TestCases","TCID", rNum);
			if(testCaseName.equals(testCase)){
				if(xls.getCellData("TestCases","Runmode", rNum).equalsIgnoreCase("y")){
					return true;
				}else{
					return false;
				}
			}	
			}
		return false;
		}
	

}
