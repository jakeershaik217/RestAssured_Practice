package com.java.utils.ExcelUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public String Path;
	
	ExcelUtility(String Path) throws IOException{
		this.Path=Path;
		boolean flag=false;
		File file=new File(Path);
		if(!file.exists()) {
			file.createNewFile();
			flag=true;
		}
		FileInputStream fin=new FileInputStream(file);
        workbook=new XSSFWorkbook(fin);	
        sheet=flag?workbook.createSheet("Index0"):workbook.getSheet("Index0");
	}
	
	ExcelUtility() throws IOException{
		this.Path=System.getProperty("user.dir")+"\\src\\test\\resources\\ExcelData\\Test.xlsx";
		System.out.println(Path);
		boolean flag=false;
		File file=new File(Path);
		if(!file.exists()) {
			file.createNewFile();
			flag=true;
		}
		FileInputStream fin=new FileInputStream(file);
        this.workbook=new XSSFWorkbook(fin);	
       this.sheet=flag?workbook.createSheet("Index0"):workbook.getSheet("Index0");
	}
	
	public List<HashMap<Object,Object>> getAllDataInAMapCollection(){
		int RowCount=sheet.getLastRowNum()+1;
		int ColCount=sheet.getRow(0).getLastCellNum();
		System.out.println(RowCount+"----"+ColCount);
		List<HashMap<Object,Object>> list=new LinkedList<HashMap<Object,Object>>();
		List<String> Names=new LinkedList<>();
		for(int i=0;i<ColCount;i++)
			Names.add(sheet.getRow(0).getCell(i).getStringCellValue());
		for(int i=1;i<RowCount;i++) {
			HashMap<Object,Object> map=new HashMap<>();
			for(int j=0;j<ColCount;j++) {
				CellType type= sheet.getRow(i).getCell(j)==null?CellType.STRING:sheet.getRow(i).getCell(j).getCellType();
				  switch(type) {
				  case STRING:{
					  String Value=sheet.getRow(i).getCell(j)==null?"EMPTY":sheet.getRow(i).getCell(j).getStringCellValue();
					  map.put(Names.get(j), Value);break;
				  }
				  case BOOLEAN:map.put(Names.get(j), sheet.getRow(i).getCell(j).getBooleanCellValue());break;
				  case NUMERIC:map.put(Names.get(j), sheet.getRow(i).getCell(j).getNumericCellValue());break;
				  }
			}
			list.add(map);
		}
		
		return list;
	}
	
	public Object[][] getAsDataProviderFormat(){
		List<HashMap<Object,Object>> maps=getAllDataInAMapCollection();
		int size=maps.size();
		Object[][] objarray=new Object[size][1];
		for(int i=0;i<objarray.length;i++)
			objarray[i][0]=maps.get(i);
		return objarray;
	}
	
	public static void main(String[] args) throws IOException {
		ExcelUtility exs=new ExcelUtility();
		for(Object[] obj:exs.getAsDataProviderFormat()) {
			System.out.println(obj[0]);
		}
			
		
		
	}

}
