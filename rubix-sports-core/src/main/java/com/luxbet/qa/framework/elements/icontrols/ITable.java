package com.luxbet.qa.framework.elements.icontrols;

public interface ITable {

	/**
	 * Clicks on the hyperlink in the first column within the table grid. If you need to click on any other link then call 
     * SelectHyperLinkByName(String coulmnName, String hyperLinkName) to select the table link.
	 * @param rowNumber Row number of the hyperlink to-be-selected - 1 based
	 */
	void SelectHyperLinkByRowNumber(int rowNumber);
	/**
	 * Selects a hyperlink within the table
	 * @param coulmnName Name of the column where the hyperlink is present
	 * @param hyperLinkName Exact name of the hyperlink to-be-selected - case sensitive
	 */
    void SelectHyperLinkByName(String coulmnName, String hyperLinkName);
    //void SelectHyperLinkByReference(String hyperlinkColumn, String referenceColumn, String referenceValue);
    void SelectRow(int rowNumber);
    void SelectRow(String columnName, String cellValue);
    void EnterCellValue(String columnName, int rowNumber, String valueToEnter);
    void EnterCellValue(int columnNumber, int rowNumber, String valueToEnter);
    String GetCellValue(String columnName, int rowNumber);
    void VerifyAllItemsInColumn(String columnName, String csvValueList);
    void VerifyItemPresentInColumn(String columnName, String expected);
    void VerifyCellValue(String columnName, int rowNumber, String expected);        
    void VerifyRowData(int rowNumber, String csvExpectedRowData);
    String GetRowData(int rowNumber);
    void VerifyRowDataFor(String columnName, String columnValue, String csvExpectedRowData);
    String GetRowDataFor(String columnName, String columnValue);
    void VerifyRowCount(int expectedRows);
    void VerifyColumnCount(int expectedColumns);
    void VerifyColumnValuesTheSame(String columnName, String expected);
    void VerifyColumnHeaders(String csvColumnHeaderNames);
    void VerifyColumnPresent(String columnHeaderName);
    IColumn Column(String columnName);


	public interface IColumn
	{
//	    bool IsVisible();
//	    bool IsSortable();
//	    bool IsSorted();
//	    bool IsFilterable();
//	    bool IsFiltered();
	    void VerifyName(String expected);
	    void VerifyHidden();
	    void VerifyNotHidden();
	    void VerifySortable();
	    void VerifyNotSortable();
//	    void VerifyFilterable();
//	    void VerifyNotFilterable();
	    void SortAscendingOrder();
	    void SortDescendingOrder();
	    void VerifySortAscending();
	    void VerifySortDescending();
	}
}
