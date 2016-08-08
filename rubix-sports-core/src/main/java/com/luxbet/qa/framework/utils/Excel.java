package com.luxbet.qa.framework.utils;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Excel {
	
	private static Logger logger = Logger.getLogger(Excel.class);
	
	private String _excelFile;
	private String _worksheet = null;
	private static Workbook _workbook;
    private static Sheet _sheet;
	// Considering first two rows are headers
	private Integer headerRowCount = 2; 
	private Integer _rowCountInCurrentWorkSheet = -1;
	private Integer _currentRow = -1;
	private Integer _currentColumn = -1;
	
	public static void main(String args[]) {
		Excel e = new Excel("c:\\automation\\Simulated_Races.xlsx", "Victoria Derby");
		System.out.println(e.GetCellValue(2, 7));
		String dateStr = e.GetCellValue(2, 8);
		Date date;// = new SimpleDateFormat("dd-mm-yyyy").parse(dateStr);
				
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
			String newstring = new SimpleDateFormat("dd-MM-yyyy").format(date);
			System.out.println(newstring);
			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println(e.GetCellValue(3, 7));
		System.out.println(e.GetCellValue(3, 8));
		System.out.println(e.GetCellValue(3, 9));
		
		System.out.println(e.GetCellValue(2, 3));
		dateStr = e.GetCellValue(1, 7); 
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
			String newstring = new SimpleDateFormat("HH:mm:ss").format(date);
			System.out.println(newstring);			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
/*	public Excel(String ExcelFilePath) {
		_excelFile = ExcelFilePath;
	}*/

	public Excel(String ExcelFilePath, String WorkSheet) {
		_excelFile = ExcelFilePath;
		_worksheet = WorkSheet;
		FileInputStream fileIn = null;
		try {
			fileIn = new FileInputStream(new File(_excelFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			this._workbook = new WorkbookFactory().create(fileIn);
            this._sheet = _workbook.getSheet(WorkSheet);
            fileIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		logger.info("Initializing Excel Sheet <" + _excelFile + ">, WorkSheet <" + _worksheet + ">");		
	}
	
	public Boolean isRowEmpty(Integer RowNumber) {
		
		try {
//		FileInputStream fileIn = new FileInputStream(new File(_excelFile));
//		Workbook workbook = new WorkbookFactory().create(fileIn);
		Sheet sheet = _workbook.getSheet(_worksheet);
		Row row = sheet.getRow(RowNumber-1);
	    for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
	        Cell cell = row.getCell(c);	        
	        if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
	        	//System.out.println("RowNumber = "+RowNumber+", Cell(" + c + ") = " + cell.getStringCellValue());
	            return false;
	        }
	    }
		} catch (Exception e) {
			logger.error("isRowEmpty() -> Exception: " + e.getMessage());
		}
	    return true;		
	}
	
	public int FirstRowNumber() {
		return headerRowCount;
	}
	
	public void SetFilePath(String ExcelFilePath) {
		_excelFile = ExcelFilePath;	
	}

	public String GetFilePath() {
		return _excelFile;		
	}

	public void SetCurrentWorkSheet(String WorkSheet) {
		_worksheet = WorkSheet;		
		_rowCountInCurrentWorkSheet = -1;
		logger.info("Current WorkSheet changed to <" + _worksheet + "> for Excel Sheet <" + _excelFile + ">");		
	}

	public String GetCurrentWorkSheet() {
		return _worksheet;		
	}

	public void SetCurrentRow(Integer Row) {
		_currentRow = Row;		
	}

	public Integer GetCurrentRow() {
		return _currentRow;		
	}

	
	public Integer HeaderRowCount() {
		return headerRowCount;
	}	

	public void AddRow(String rowData[]) {
		try {				
				String msg;
				//ShiftRows(headerRowCount, 1);
				FileInputStream fileIn = new FileInputStream(new File(_excelFile));
				Workbook workbook = new WorkbookFactory().create(fileIn);
				Sheet sheet = workbook.getSheet(_worksheet);
				Row row = sheet.createRow(sheet.getPhysicalNumberOfRows());
				_currentRow = sheet.getPhysicalNumberOfRows(); 
				msg = _worksheet + " -> Row " + _currentRow + " added. Values = ";
				for(int i=0; i<rowData.length; i++) {
					row.createCell(i).setCellValue(rowData[i]);
					msg += "[" + rowData[i] + "]";
				}						
				//System.out.println(System.lineSeparator() + "--");
				//System.out.println("_excelFile = " + _excelFile + ", excelsheet = " + _worksheet);
				//_currentRow = headerRowCount+1;				
				fileIn.close();
		    	FileOutputStream fileOut = new FileOutputStream(new File(_excelFile));
		    	workbook.write(fileOut);
		    	fileOut.flush();
		    	fileOut.close();
		    	logger.info(msg);//"After sheet.getPhysicalNumberOfRows() = " + sheet.getPhysicalNumberOfRows());			
		} catch(Exception e) {
			logger.error("Exception while AddRow()-> " + e.getMessage());
		}				
	}
	
/*	private void ShiftRows(int row, int count) {
		try {
			FileInputStream fileIn = new FileInputStream(new File(_excelFile));
			Workbook workbook = new WorkbookFactory().create(fileIn);
			Sheet sheet = workbook.getSheet(_worksheet);
			sheet.shiftRows(row, sheet.getPhysicalNumberOfRows(), count);
			fileIn.close();
	    	FileOutputStream fileOut = new FileOutputStream(new File(_excelFile));
	    	workbook.write(fileOut);
	    	fileOut.flush();
	    	fileOut.close();
		} catch(Exception e) {
			logger.error("Excel->ShiftRows()-> " + e.getMessage());
		}				
		
	}*/
	
	public void SetCellValueByHeader(String Value, String ColumnHeader) {
		SetCellValueByHeader(Value, ColumnHeader, _currentRow, _worksheet, null);
	}

	public void SetCellValueByHeader(String Value, String ColumnHeader, String CellDataFormat) {
		SetCellValueByHeader(Value, ColumnHeader, _currentRow, _worksheet, CellDataFormat);
	}
	
	public void SetCellValueByHeader(String Value, String ColumnHeader, int RowNumber, String WorkSheet, String CellDataFormat) {		
		Cell cell = null;
		try {
			FileInputStream fileIn = new FileInputStream(new File(_excelFile));
			Workbook workbook = new WorkbookFactory().create(fileIn);
			Sheet sheet = workbook.getSheet(WorkSheet);
			
			// Find the cell to write value to
			//find the column header
			Integer colCount = GetColumnCount(1, WorkSheet);
			
			for(int c=0; c<=colCount; c++) {
				cell = sheet.getRow(headerRowCount-1).getCell(c);
				if(getCellValueAsString(cell).toLowerCase().equals(ColumnHeader.toLowerCase())) {
					//System.out.println(ColumnHeader.toLowerCase() + " found at col " + c + ", row = " + (RowNumber-1));
					Row row = sheet.getRow(RowNumber-1);
					// If the row doesn't exist, create one
/*					if(row == null) {					
						//if(RowNumber == -1)
						//	RowNumber = headerRowCount;
						//sheet.shiftRows(RowNumber+1, sheet.getLastRowNum(), 1);
						sheet.shiftRows(RowNumber, sheet.getLastRowNum(), 1);
						row = sheet.createRow(RowNumber - 1);						
					}
*/					if(row != null) {					
						cell = row.createCell(c);						
						if(CellDataFormat != null) {
							DataFormat format = workbook.createDataFormat();
							CellStyle style = workbook.createCellStyle();
							style.setDataFormat(format.getFormat(CellDataFormat));
							cell.setCellStyle(style);
						}
						
						break;
					} // End if(row != null)
					else {
						logger.error("SetCellValueByHeader("+Value+","+ColumnHeader+","+RowNumber+","+WorkSheet+") -> Unable to get row reference to set cell value.");
					}
						
				}
			}
			
			// if Cell was found
			if(cell != null)
				cell.setCellValue(Value);
			fileIn.close();
			
	    	FileOutputStream fileOut = new FileOutputStream(new File(_excelFile));
	    	workbook.write(fileOut);
	    	fileOut.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}		


	}
	
	public Boolean SetCellValue(Integer ColNumber, Integer RowNumber, String Value) {
		Boolean result = false;
		try {
			FileInputStream fileIn = new FileInputStream(new File(_excelFile));
			Workbook workbook = new WorkbookFactory().create(fileIn);
			Sheet sheet = workbook.getSheet(_worksheet);
			Row row = sheet.getRow(RowNumber-1);
			fileIn.close();
			if(row != null) {					
				Cell cell = row.createCell(ColNumber-1);	
				cell.setCellValue(Value);
		    	FileOutputStream fileOut = new FileOutputStream(new File(_excelFile));
		    	workbook.write(fileOut);
		    	fileOut.close();
		    	result = true;
			}						
		} catch(Exception e) {
			logger.error(e.getMessage());
			return result;
		}		
		return result;
	}
	
	public Boolean IsCellNotEmpty(String ColumnHeader, int RowNumber) {
		if(GetCellValueByHeader(ColumnHeader, RowNumber) == null)
			return false;
		else
			return true;
	}
	
	public Boolean IsCellValuePresent(String Value, int Column, int Row) {
		String actual  = GetCellValue(Column, Row, _worksheet);		
		if(actual == null) return false;
		if(actual.equals(Value)) return true;		
		return false;
	}
	
	public String GetCellValueByHeader(String ColumnHeader, int RowNumber) {
		return GetCellValueByHeader(ColumnHeader, RowNumber, _worksheet);
	}
	
	public String GetCellValueByHeader(String ColumnHeader, int RowNumber, String WorkSheet) {
		Cell cell = null;
		String value = null;
		try {
//			FileInputStream fileIn = new FileInputStream(new File(_excelFile));
//			Workbook workbook = new WorkbookFactory().create(fileIn);
			Sheet sheet = _workbook.getSheet(WorkSheet);

			// Get the max of column count from first two header rows
			Integer colCount = GetColumnCount(2, WorkSheet);
			if(colCount < GetColumnCount(1, WorkSheet))
				colCount = GetColumnCount(1, WorkSheet);
			
			for(int c=0; c<colCount; c++) {
				cell = sheet.getRow(headerRowCount-1).getCell(c);
				if(getCellValueAsString(cell).toLowerCase().equals(ColumnHeader.toLowerCase())) {
					cell = sheet.getRow(RowNumber-1).getCell(c);
					value = getCellValueAsString(cell);
					break;
				}
			}
			
			//fileIn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}		
		return value;
	}
	
	public String GetCellValue(int ColumnNumber, int RowNumber) {
		return GetCellValue(ColumnNumber, RowNumber, _sheet);
	}
	
	public String GetCellValue(int ColumnNumber, int RowNumber, String WorkSheetName) {
		// Find the cell;
		Cell cell = null;
		try {
//			FileInputStream fileIn = new FileInputStream(new File(_excelFile));
//			Workbook workbook = new WorkbookFactory().create(fileIn);
			Sheet sheet = _workbook.getSheet(WorkSheetName);
			cell = sheet.getRow(RowNumber-1).getCell((ColumnNumber-1));			
			//fileIn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}		
		return getCellValueAsString(cell);
	}

	public String GetCellValue(int ColumnNumber, int RowNumber, Sheet workSheet) {
		// Find the cell;
		Cell cell = null;
		try {
			cell = workSheet.getRow(RowNumber-1).getCell((ColumnNumber-1));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return getCellValueAsString(cell);
	}

	public int GetNumberOfRows() {
		
		if(_rowCountInCurrentWorkSheet == -1)	{			
			try {
//				FileInputStream fileIn = new FileInputStream(new File(_excelFile));
//				_rowCountInCurrentWorkSheet = new WorkbookFactory().create(fileIn).getSheet(_worksheet).getPhysicalNumberOfRows();
//				fileIn.close();
                _rowCountInCurrentWorkSheet = _workbook.getSheet(_worksheet).getPhysicalNumberOfRows();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return _rowCountInCurrentWorkSheet;
	}
	
	public int GetRowCount(String WorkSheet) {
		int rowcount = -1;
		try {
//			FileInputStream fileIn = new FileInputStream(new File(_excelFile));
//			rowcount = new WorkbookFactory().create(fileIn).getSheet(WorkSheet).getPhysicalNumberOfRows();
//			fileIn.close();
            rowcount = _workbook.getSheet(_worksheet).getPhysicalNumberOfRows();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return rowcount;
	}

	public int GetRowCountExcludingHeaders(String WorkSheet) {
		return GetRowCount(WorkSheet) - headerRowCount;
	}
	
	public void ReCalculateFormulas(String WorkSheet) {
	
		try {
//	    	FileInputStream fileIn = new FileInputStream(new File(_excelFile));
//	    	Workbook workbook = new WorkbookFactory().create(fileIn);
	    	Sheet sheet = _workbook.getSheet(WorkSheet);
	    	FormulaEvaluator evaluator = _workbook.getCreationHelper().createFormulaEvaluator();
    	
		    for(Row r : sheet) {
		        for(Cell c : r) {
		            if(c.getCellType() == Cell.CELL_TYPE_FORMULA) {
		                evaluator.evaluateFormulaCell(c);
		            }
		        }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int GetColumnCount(int RowNumber, String WorkSheet) {		
		int colCount = -1;
		try {
//			FileInputStream fileIn = new FileInputStream(new File(_excelFile));
//			colCount = new WorkbookFactory().create(fileIn).getSheet(WorkSheet).getRow(RowNumber-1).getPhysicalNumberOfCells();
//			fileIn.close();
            colCount =  _workbook.getSheet(WorkSheet).getRow(RowNumber-1).getPhysicalNumberOfCells();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return colCount;
	}
		
	private String getCellValueAsString(Cell cell) {
		
		String cellValue = null;
		if(cell == null) return cellValue;
		
		if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA) {
			if(DateUtil.isCellDateFormatted(cell))			
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
		}		
		cell.setCellType(Cell.CELL_TYPE_STRING);				
		return cell.getStringCellValue();
	
		/*
        switch (cell.getCellType()) 
        {        
            case Cell.CELL_TYPE_NUMERIC:            	
            	cellValue = cell.getNumericCellValue() + "";
                break;
            case Cell.CELL_TYPE_STRING:
            	cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
            	cellValue = cell.getBooleanCellValue() + "";
            	break;            	
        }
        return cellValue;
        */
	}
	
	/*
	int getNumberOfRows(String file, String worksheet) {		
		int totalrows = -1;
		try {
			FileInputStream fileIn = new FileInputStream(new File(file));
			totalrows = new WorkbookFactory().create(fileIn).getSheet(worksheet).getLastRowNum() + 1;
			fileIn.close();			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return totalrows;
	}

	int getNumberOfCols(String file, String worksheet) {		
		int totalcols = -1;
		try {
			FileInputStream fileIn = new FileInputStream(new File(file));
			totalcols = new WorkbookFactory().create(fileIn).getSheet(worksheet).getRow(1).getPhysicalNumberOfCells();
			fileIn.close();			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return totalcols;
	}
	
	public void PrintExcelSheet() {
		String excelfile = "C:\\Automation2\\TestCase-BravoRefactor.xlsx";
		String worksheet = "TestCases";
		
		try {
	    	FileInputStream fileIn = new FileInputStream(new File(excelfile));                
	    	Workbook workbook = new WorkbookFactory().create(fileIn);
	    	Sheet sheet = workbook.getSheet(worksheet);   
    	
		    for(Row r : sheet) {
		    	System.out.print("Row (" + r.getRowNum() + ") - ");
		        for(Cell c : r) {
		        	System.out.print("("+c.getColumnIndex()+"-"+getCellValueAsString(c)+")");
		        }
		        System.out.println("");
		    }
		    fileIn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public String getCellValueAsString(Cell cell) {
		String cellValue = null;
		//System.out.println("getCellType = " + cell.getCellType());
        switch (cell.getCellType()) 
        {        
            case Cell.CELL_TYPE_NUMERIC:
            	cellValue = cell.getNumericCellValue() + "";
                break;
            case Cell.CELL_TYPE_STRING:
            	cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
            	cellValue = cell.getBooleanCellValue() + "";
            	break;            	
        }
        return cellValue;
	}
	
	public void SetCellValue(int row, int col, Double value) {
		try {

			String excelfile = "C:\\Automation2\\20140925 Cash Out Simulator vFINAL.xlsx";
			String worksheet = "Single Template";
	    	FileInputStream fileIn = new FileInputStream(new File(excelfile));                
	    	Workbook workbook = new WorkbookFactory().create(fileIn);
	    	Sheet sheet = workbook.getSheet(worksheet);     
	    	System.out.println("Before ("+row+","+col+") = " + getCellValueAsString(sheet.getRow(row).getCell(col)));
	    	sheet.getRow(row).getCell(col).setCellValue(value);
	    	System.out.println("sheet.getRow(row).getCell(col).getCellStyle().getDataFormatString() = " + sheet.getRow(row).getCell(col).getCellStyle().getDataFormatString());
	    	System.out.println("After ("+row+","+col+") = " + getCellValueAsString(sheet.getRow(row).getCell(col)));
	    	fileIn.close();
	    	FileOutputStream fileOut = new FileOutputStream(new File(excelfile));
	    	workbook.write(fileOut);
	    	fileOut.close();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public String SetSingleBetWorkSheet(Double InvestmentAmount, Double Price, Double BookPercentage, Double DiscountFactor) {
		String cashout = null;
		try {

	    	//FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
	    	
	    	SetCellValue(SingleBetWorkSheet.InvestmentAmount.Row(), SingleBetWorkSheet.InvestmentAmount.Col(), InvestmentAmount);
	    	ReCalculateFormulas();
	    	SetCellValue(SingleBetWorkSheet.OriginalPrice.Row(), SingleBetWorkSheet.OriginalPrice.Col(), Price);
	    	ReCalculateFormulas();
	    	SetCellValue(SingleBetWorkSheet.CurrentPrice.Row(), SingleBetWorkSheet.CurrentPrice.Col(), Price);
	    	ReCalculateFormulas();
	    	SetCellValue(SingleBetWorkSheet.BookPercentage.Row(), SingleBetWorkSheet.BookPercentage.Col(), BookPercentage);
	    	ReCalculateFormulas();
	    	SetCellValue(SingleBetWorkSheet.DiscountFactor.Row(), SingleBetWorkSheet.DiscountFactor.Col(), DiscountFactor);	    		    	
	    	ReCalculateFormulas();

			String excelfile = "C:\\Automation2\\20140925 Cash Out Simulator vFINAL.xlsx";
			String worksheet = "Single Template";
	    	FileInputStream fileIn = new FileInputStream(new File(excelfile));                
	    	Workbook workbook = new WorkbookFactory().create(fileIn);
	    	Sheet sheet = workbook.getSheet(worksheet);         
	    	FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
	    	
	    	//System.out.println("CashOutValuation = " + cashout);
	    	cashout = evaluator.evaluate(sheet.getRow(SingleBetWorkSheet.CashOutValuation.Row()).getCell(SingleBetWorkSheet.CashOutValuation.Col())).formatAsString();
	    	
	    	fileIn.close();
	    	
		} catch(Exception e) {
			e.printStackTrace();
		}
		return cashout;
	}		
	
	public void ReCalculateFormulas() {
		String excelfile = "C:\\Automation2\\20140925 Cash Out Simulator vFINAL.xlsx";
		String worksheet = "Single Template";
		
		try {
	    	FileInputStream fileIn = new FileInputStream(new File(excelfile));                
	    	Workbook workbook = new WorkbookFactory().create(fileIn);
	    	Sheet sheet = workbook.getSheet(worksheet);   
	    	FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
    	
		    for(Row r : sheet) {
		        for(Cell c : r) {
		            if(c.getCellType() == Cell.CELL_TYPE_FORMULA) {
		                evaluator.evaluateFormulaCell(c);
		            }
		        }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
	
}
