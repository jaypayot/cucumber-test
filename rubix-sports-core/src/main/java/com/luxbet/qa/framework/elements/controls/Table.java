package com.luxbet.qa.framework.elements.controls;

import com.luxbet.qa.framework.elements.icontrols.ITable;
import com.luxbet.qa.framework.testng.Verify;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Constructs a Table element either using By locator or WebElement.
 * @author Selva Nakuladeva
 * @since 30 Oct 2014
 */
public class Table extends ControlBase implements ITable {
	private static Logger logger = Logger.getLogger(Table.class);
	private WebElement _table;
	private WebElement _tableBody;
	private WebElement _tableHeader;
	private By _by;
	
	public Table(WebElement table) {
		super(table);	
		this._table = waitForVisibilityOf(table);
		this._tableBody = this._table.findElement(By.tagName("tbody"));
		this._tableHeader = this._table.findElement(By.tagName("thead"));
	}
		
	public Table(By by) {
		this(waitForPresenceOfElement(by));
		this._by = by;
	}
	
	public Table(String tableId) {
		this(By.id(tableId));
	}


	protected WebElement getTable() {
		return this._table;
	}
	
	protected WebElement getTableBody() {
		return this._tableBody;
	}
	
	protected WebElement getTableHeader() {
		return this._tableHeader;
	}
	
	/*************************************************************/
	/* Add private methods here */
	/*************************************************************/
	protected List<WebElement> getRows() {
		List<WebElement> rows = getTableBody().findElements(By.tagName("tr"));
		return rows;
	}
	/**
	 * @param index zero based index
	 * @return the row element at the given index 
	 */
	protected WebElement getRow(int index) {
		WebElement row = getRows().get(index);
		return row;
	}
	
	protected List<WebElement> getColumnHeaders() {
		// Hidden columns will have a different class name
		List<WebElement> columns = getTableHeader().findElements(By.tagName("th")); 
		return columns;
	}

    protected List<WebElement> getHiddenColumns() {
        TurnOffImplicitWait();
        List<WebElement> hiddenCols = getTableHeader().findElements(By.cssSelector("th.is-hidden"));
        TurnOnImplicitWait();
        return hiddenCols;
    }

    protected List<WebElement> getVisibleColumns() {
//        List<WebElement> visibleCols = null;
//        for (WebElement e: getColumnHeaders()) {
//            if (!isColumnHidden(e)) {
//                visibleCols.add(e);
//            }
//        }
//        return visibleCols;

        List<WebElement> visibleCols = getColumnHeaders();
        visibleCols.removeAll(getHiddenColumns());

        return visibleCols;
    }

	/**
	 * @param index zero based index
	 * @return the column header element at the given index 
	 */
	protected WebElement getColumnHeader(int index) {
		WebElement column = getColumnHeaders().get(index);
		return column;
	}
	
	protected WebElement getColumnHeader(String columnTitle) {
		WebElement colHeader = null;
		List<WebElement> cols = getColumnHeaders();
		for(WebElement col : cols) {
			if(getText(col).equals(columnTitle)) {
				colHeader = col;
				break;
			}
		}
		return colHeader;
	}
	
	protected int getColumnIndex(String columnTitle) {
		int columnIndex = -1;
		List<WebElement> cols = getColumnHeaders();
		for(int i=0; i<cols.size(); i++) {
			if(getText(cols.get(i)).equals(columnTitle)) {
				columnIndex = i;
				break;
			}
		}
		return columnIndex;
	}
	
	protected List<WebElement> getCells(WebElement row) {
		List<WebElement> cells = row.findElements(By.tagName("td"));
		return cells;
	}
	/**
	 * @param index zero based index
	 * @return the cell element at the given index 
	 */
	protected WebElement getCell(WebElement row, int index) {
		WebElement cell = getCells(row).get(index);
		return cell;
	}
	
	protected WebElement getCell(int rowIndex, int colIndex) {
		WebElement row = getRow(rowIndex);
		return getCell(row, colIndex);
	}
	
	protected WebElement getCell(int rowIndex, String columnTitle) {
		WebElement cell = getCell(rowIndex, getColumnIndex(columnTitle));

		return cell;
	}
	
	protected WebElement getCell(String columnTitle, String cellValue) {
		WebElement cell = null;
		int rowCount = getRowCount();
		for (int i=0; i<rowCount; i++) {
			WebElement e = getCell(i, columnTitle); 
			if(getText(e).equals(cellValue)) {
				// A match is found
				cell = e;
				break;
			}
		} 
		return cell;
	}
	
	protected WebElement getCell(WebElement row, String cellValue) {
		WebElement cell = null;
		List<WebElement> cells = getCells(row);
		for(WebElement c : cells) {
			if(getText(c).equals(cellValue)) {
				cell = c;
				break;
			}
		} 
		return cell;
	}
	
	protected WebElement getHyperLink(WebElement cell) {
		WebElement link = cell.findElement(By.tagName("a"));
		return link;	
	}

	// Made public for a requirement in redbook project
	public int getRowCount() {
		return getRows().size();
	}
	
	protected int getColumnCount() {
		return getColumnHeaders().size();
	}

    protected int getHiddenColumnCount() {
        return getHiddenColumns().size();
    }

	/*************************************************************/
	/* Add interface implementation methods here */
	/*************************************************************/
	@Override
	public void SelectHyperLinkByRowNumber(int rowNumber) {
		WebElement row = getRow(rowNumber-1);
		// get the first cell
		WebElement cell = getCell(row,0);
		getHyperLink(cell).click();		
	}

	@Override
	public void SelectHyperLinkByName(String columnName, String hyperLinkName) {
		//WebElement cell = getCell(columnName, hyperLinkName);
        WebElement link = this._tableBody.findElement(By.linkText(hyperLinkName));
        // link might not be in view, click on div to bring it into view
        getParent(link).click();
        // and click on the link
        if (!waitForStalenessOf(link, 1)) {
            link.click();
        }
	}

	@Override
	public void SelectRow(int rowNumber) {
		// Select the first cell in the row
		getCell(rowNumber-1, 0).click();		
	}

	@Override
	public void SelectRow(String columnName, String cellValue) {
		WebElement cell = getCell(columnName, cellValue);
		cell.click();
	}

	@Override
	public void EnterCellValue(String columnName, int rowNumber,
			String valueToEnter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void EnterCellValue(int columnNumber, int rowNumber,
			String valueToEnter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String GetCellValue(String columnName, int rowNumber) {
		WebElement cell = getCell(rowNumber-1, columnName);
		return getText(cell);
	}

	@Override
	public void VerifyAllItemsInColumn(String columnName, String csvValueList) {
		String[] columnValues = csvValueList.split(",");
		for (String value : columnValues) {
			VerifyItemPresentInColumn(columnName, value);
		}
	}

	@Override
	public void VerifyItemPresentInColumn(String columnName, String expected) {
		WebElement cell = getCell(columnName, expected);
		// If item not listed in the column then cell element would be null
		Verify.verifyTrue(cell != null, "FAIL! Expected item '" +expected+ "' is not present in the column '" +columnName+ "'");
	}

	@Override
	public void VerifyCellValue(String columnName, int rowNumber, String expected) {
		WebElement cell = getCell(rowNumber-1, columnName);
		String actual = getText(cell);
		Verify.verifyEquals(actual, expected, "FAIL! The cell value does not match, Expected<" +expected+ ">, but Actual<" + actual + ">");
	}

    @Override
    public void VerifyRowData(int rowNumber, String csvExpectedRowData) {
        String actual = GetRowData(rowNumber);
        Verify.verifyEquals(actual, csvExpectedRowData, "FAIL: Row data does not match; expected[" +csvExpectedRowData+ "], but actual[" +actual+ "]");
    }

    @Override
    public String GetRowData(int rowNumber) {
        String rowData = getRow(rowNumber-1).getAttribute("innerText");
        // It would be a tab separated value, convert this to csv
        rowData = rowData.replaceAll("\\t", ",");
        return rowData;
    }

    @Override
    public void VerifyRowDataFor(String columnName, String columnValue, String csvExpectedRowData) {
        String rowData = GetRowDataFor(columnName, columnValue);
        Verify.verifyEquals(rowData, csvExpectedRowData, "FAIL: Row data does not match; expected[" +csvExpectedRowData+ "], but actual[" +rowData+ "]");
    }

    @Override
    public String GetRowDataFor(String columnName, String columnValue) {
        String rowData = getParent(getCell(columnName,columnValue)).getAttribute("innerText");
        // It would be a tab separated value, convert this to csv
        rowData = rowData.replaceAll("\\t", ",");
        return rowData;
    }

    @Override
	public void VerifyRowCount(int expectedRows) {
		Verify.verifyTrue(getRowCount()==expectedRows, "FAIL! Row count does not match, Expected<" +expectedRows+ ">, but Actual<" +getRowCount()+ ">");
	}
	
	@Override
	public void VerifyColumnCount(int expectedColumns) {
        //int visibleColumnCount = getColumnCount() - getHiddenColumnCount();
        int visibleColumnCount = getVisibleColumns().size();
		Verify.verifyTrue(visibleColumnCount==expectedColumns, "FAIL! Row count does not match, Expected<" +expectedColumns+ ">, but Actual<" +getColumnCount()+ ">");
	}

	@Override
	public void VerifyColumnValuesTheSame(String columnName, String expected) {
		int columnIndex = getColumnIndex(columnName);
		int rowCount = getRowCount();
		for(int i=0; i<rowCount; i++) {
			String value = getText(getCell(i, columnIndex));
			Verify.verifyEquals(value, expected, "FAIL! VerifyColumnValuesTheSame() -> The cell value at row " +i+1+ " is different, Expected<" +expected+ ">, but Actual<" + value + ">");
		}
	}

	@Override
	public void VerifyColumnHeaders(String csvColumnHeaderNames) {
//		String colHeaders = getTableHeader().getAttribute("innerText");
//        colHeaders = colHeaders.replaceAll("\\t", ",");
//        colHeaders = colHeaders.replaceAll("\\n", ",");
//        Verify.verifyEquals(colHeaders, csvColumnHeaderNames, "FAIL: Expected column headers do not match the actual; expected[" +csvColumnHeaderNames+ "], but actual[" +colHeaders+ "]");

        List<String> expectedCols = new ArrayList<String>(Arrays.asList(csvColumnHeaderNames.split(",")));
        String colHeader = "";
        List<WebElement> visibleCols = getVisibleColumns();
        for(int i=0; i<visibleCols.size(); i++) {
            // Get the column name
            colHeader = getText(visibleCols.get(i));
            if(csvColumnHeaderNames.contains(colHeader)) {
                expectedCols.remove(colHeader);
            }
        }
        // After going through all visible columns, we can check if any more columns left in expectedCols list
        // If yes those are not displayed! mark it as a fail
        Verify.verifyTrue(expectedCols.size() == 0, "FAIL: One or more column headers are missing; Missing columns >> [" + expectedCols + "]");
	}

    @Override
    public void VerifyColumnPresent(String columnHeaderName) {
        boolean colPresent = false;
        for(WebElement col: getVisibleColumns()) {
            if(getText(col).equals(columnHeaderName)) {
                colPresent = true;
                break;
            }
        }
        Verify.verifyTrue(colPresent, "FAIL: Expected column <" +columnHeaderName+ "> not present in the table.");
    }

	@Override
	public IColumn Column(String columnName) {
		return new Column(columnName);
	}

    public class Column implements IColumn {
        private String _colName = null;
        private WebElement _col = null;

        public Column(String columnName) {
            this._colName = columnName;
            this._col = getColumn(columnName);
        }

        public Column(WebElement column) {
            this._col = column;
        }

        public WebElement getColumn() {
            return this._col;
        }

        /** Add private methods here **/
        private boolean isColumnHidden(WebElement column) {
            boolean hidden = false;
            String className = column.getAttribute("className");
            if(className.contains("is-hidden")) {
                hidden = true;
            }
            return hidden;
        }

        private WebElement getColumn(String colName) {
            WebElement col = null;
            for(WebElement e: getColumnHeaders()) {
                if(getColumnName(e).equals(colName)) {
                    col = e;
                    break;
                }
            }
            if(col==null) {
                log("ERROR: No match found for column name <" +colName+ ">");
            }

            return col;
        }

        private String getColumnName(WebElement col) {
            String colName = "";
            String className = col.getAttribute("className");
            if (className.contains("tabHeaderLabel")) {
                colName = "TAB ";
            } else if(className.contains("luxbetHeaderLabel")) {
                colName = "Luxbet ";
            }
            //colName += getText(col.findElement(By.cssSelector("div.tablesorter-header-inner")));
            // th dom structure changes when columns reorganised.
            colName += getText(col.findElement(By.tagName("div")));
            return colName;
        }

        private boolean isSortable(WebElement col) {
            boolean sortable = false;
            String className = col.getAttribute("className");
            if (className.contains("tablesorter-header")) {
                sortable = true;
            }
            return sortable;
        }

        private boolean isSortAscending(WebElement col) {
            boolean ascending = false;
            String classname = col.getAttribute("className");
            if (classname.contains("tablesorter-headerAsc")) {
                ascending = true;
            }
            return ascending;
        }

        private boolean isSortDescending(WebElement col) {
            boolean descending = false;
            String classname = col.getAttribute("className");
            if (classname.contains("tablesorter-headerDesc")) {
                descending = true;
            }
            return descending;
        }

        /** Add interface implementation here **/
        @Override
        public void VerifyName(String expected) {
            String actual = getColumnName(this._col);
            Verify.verifyEquals(actual, expected, "FAIL: Column name verification failed, expected<" +expected+ ">, but actual<" +actual+ ">");
        }

        @Override
        public void VerifyHidden() {
            boolean hidden = isColumnHidden(this._col);
            Verify.verifyTrue(hidden, "FAIL: Column header <" +this._colName+ "> is not hidden incorrectly");
        }

        @Override
        public void VerifyNotHidden() {
            boolean hidden = isColumnHidden(this._col);
            Verify.verifyFalse(hidden, "FAIL: Column header <" +this._colName+ "> is hidden incorrectly");
        }

        @Override
        public void VerifySortable() {
            boolean sortable = isSortable(this._col);
            Verify.verifyTrue(sortable, "FAIL: The column header <" +this._colName+ "> is not sortable incorrectly");
        }

        @Override
        public void VerifyNotSortable() {
            boolean sortable = isSortable(this._col);
            Verify.verifyFalse(sortable, "FAIL: The column header <" +this._colName+ "> is sortable incorrectly");
        }

        @Override
        public void SortAscendingOrder() {
            WebElement col = this._col;
            int timeout = 0;
            while (!isSortAscending(col) && timeout < 10) {
                col.click();
                timeout += 1;
            }
            if(!isSortAscending(col)) log("ERROR: Unable to sort column <" +_colName+ "> in ascending order");
        }

        @Override
        public void SortDescendingOrder() {
            WebElement col = this._col;
            int timeout = 0;
            while (!isSortDescending(col) && timeout < 10) {
                col.click();
                timeout += 1;
            }
            if(!isSortDescending(col)) log("ERROR: Unable to sort column <" +_colName+ "> in descending order");
        }

        @Override
        public void VerifySortAscending() {
            Verify.verifyTrue(isSortAscending(this._col), "FAIL: The column <" +_colName+ "> is not sorted in ascending order");
        }

        @Override
        public void VerifySortDescending() {
            Verify.verifyTrue(isSortDescending(this._col), "FAIL: The column <" +_colName+ "> is not sorted in descending order");
        }

    }
}
