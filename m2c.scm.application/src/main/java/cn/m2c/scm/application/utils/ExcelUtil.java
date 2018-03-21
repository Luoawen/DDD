package cn.m2c.scm.application.utils;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * excel导出封装类
 */
public class ExcelUtil {
    //通用
    public static <Q> void writeExcel(HttpServletResponse response, String fileName, List<Q> list, Class<Q> clazz) throws IOException, IllegalArgumentException, IllegalAccessException {
        HSSFWorkbook wb = new HSSFWorkbook();
        Field[] fields = getAllFields(clazz);
        ArrayList<String> headList = new ArrayList<String>();
        for (Field f : fields) {
            ExcelField field = f.getAnnotation(ExcelField.class);
            if (field != null) {
                headList.add(field.title());
            }
        }

        CellStyle style = getCellStyle(wb);
        Sheet sheet = wb.createSheet();
        /**
         * 设置Excel表的第一行即表头
         */
        Row row = sheet.createRow(0);
        for (int i = 0; i < headList.size(); i++) {
            Cell headCell = row.createCell(i);
            headCell.setCellValue(String.valueOf(headList.get(i)));
        }

        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Row rowData = sheet.createRow(i + 1);//创建数据行
                Q q = list.get(i);
                Field[] ff = getAllFields(q.getClass());
                int j = 0;
                for (Field f : ff) {
                    ExcelField field = f.getAnnotation(ExcelField.class);
                    if (field == null) {
                        continue;
                    }
                    f.setAccessible(true);
                    Object obj = f.get(q);
                    Cell cell = rowData.createCell(j);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellValue(String.valueOf(obj));
                    j++;
                }
            }
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + urlEncode(fileName+"xls"));
        response.setContentType("application/ms-excel");
        OutputStream ouPutStream = null;
        try {
            ouPutStream = response.getOutputStream();
            wb.write(ouPutStream);
        } finally {
            ouPutStream.close();
        }
    }

    /**
     * @Description:设置表头样式
     * @author kang
     * @date 2016年8月24日
     */
    public static CellStyle getCellStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.SOLID_FOREGROUND);//让单元格居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        style.setWrapText(true);//设置自动换行
        return style;
    }

    public static Field[] getAllFields(Class clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    //URL 编码, Encode默认为UTF-8.
    public static final String urlEncode(String s) throws UnsupportedEncodingException {
        return URLEncoder.encode(s, "UTF-8");
    }
    
    
    public  static HSSFWorkbook createExcelTemplate( String[] handers, 
            List<String[]> downData, String[] downRows, String[] sendOrderList,String[] errorLogList, String[] expressFailList){
        
        HSSFWorkbook wb = new HSSFWorkbook();//创建工作薄
        
        //表头样式
        
        //字体样式
        /*HSSFFont fontStyle = wb.createFont();    
        fontStyle.setFontName("微软雅黑");    
        fontStyle.setFontHeightInPoints((short)12);    
        fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
        style.setFont(fontStyle);*/
        
        //新建sheet
        HSSFSheet sheet1 = wb.createSheet("批量发货");
        HSSFSheet sheet2 = wb.createSheet("Sheet2");
        sheet1.setDefaultColumnWidth(20);
        HSSFSheet translation = wb.createSheet("表格说明"); 
	    int addMergedRegion = translation.addMergedRegion(new CellRangeAddress(2, 19, 1, 10));
	    HSSFRow sheet3 = translation.createRow(2);
	    HSSFCell info = sheet3.createCell(1, 1);
	    HSSFCellStyle style = wb.createCellStyle();  
	    style.setWrapText(true);
        style.setVerticalAlignment(style.VERTICAL_CENTER); // 创建一个居中格式  
	    info.setCellValue("请严格按照表格说明的规范填写，填写不合法均会导入失败；\r\n 1、表格已预置待发货的订货号，请勿篡改；\r\n 2、物流公司名称，请按照提供的标准填写，必填，否则导入失败；\r\n 3、物流单号，请按照实际物流公司单号填写，必填，1-20字符以内");
	    info.setCellStyle(style);

	    
//        if(expressFailList!=null && expressFailList.length>0){
//        	HSSFCell expressCell = null;
//        	for(int i = 0, length= errorLogList.length; i < length; ++i) {
//        		HSSFRow row = sheet1.createRow(i+1); 
//        		expressCell = row.createCell(1,1);
//        		expressCell.setCellValue(expressFailList[i]);
//        	}
//        }
//        if(errorLogList!=null && errorLogList.length>0){
//        	HSSFCell logCell = null;
//        	for(int i = 0, length= errorLogList.length; i < length; ++i) {
//        		HSSFRow row = sheet1.createRow(i+1); 
//        		logCell = row.createCell(3,1);
//        		logCell.setCellValue(errorLogList[i]);
//        	}
//        }
        
        HSSFCell dealerOrderCell = null;
        HSSFCell expressCell = null;
        for(int i = 0, length= sendOrderList.length; i < length; ++i) {
        	HSSFRow row = sheet1.createRow(i+1); 
        	dealerOrderCell = row.createCell(0,1);
        	dealerOrderCell.setCellValue(sendOrderList[i]);
         if(expressFailList!=null && expressFailList.length>0){
        	HSSFRow row2 = sheet1.createRow(i+1); 
     		expressCell = row2.createCell(1,1);
     		expressCell.setCellValue(expressFailList[i]);
         }
        	
        }
        
        //生成sheet1内容
        HSSFRow rowFirst = sheet1.createRow(0);//第一个sheet的第一行为标题
        //写标题
        for(int i=0;i<handers.length;i++){
            HSSFCell cell = rowFirst.createCell(i); //获取第一行的每个单元格
            sheet1.setColumnWidth(i, 5000); //设置每列的列宽
            cell.setCellStyle(style); //加样式
            cell.setCellValue(handers[i]); //往单元格里写数据
        }
        wb.setSheetHidden(1, true);
        //设置下拉框数据
        String[] arr = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        int index = 0;
        HSSFRow row = null;
        for(int r=0;r<downRows.length;r++){
            String[] dlData = downData.get(r);//获取下拉对象
            int rownum = Integer.parseInt(downRows[r]);
            
            if(dlData.length<5){ //255以内的下拉
                //255以内的下拉,参数分别是：作用的sheet、下拉内容数组、起始行、终止行、起始列、终止列
                sheet1.addValidationData(setDataValidation(sheet1, dlData, 1, 500, rownum ,rownum)); //超过255个报错 
            } else { //255以上的下拉，即下拉列表元素很多的情况
                
                //1、设置有效性
                   //String strFormula = "Sheet2!$A$1:$A$5000" ; //Sheet2第A1到A5000作为下拉列表来源数据
                String strFormula = "Sheet2!$"+arr[index]+"$1:$"+arr[index]+"$500"; //Sheet2第A1到A5000作为下拉列表来源数据
                   sheet2.setColumnWidth(r, 4000); //设置每列的列宽
                   //设置数据有效性加载在哪个单元格上,参数分别是：从sheet2获取A1到A5000作为一个下拉的数据、起始行、终止行、起始列、终止列
                      sheet1.addValidationData(SetDataValidation(strFormula, 1, 500, rownum, rownum)); //下拉列表元素很多的情况
                      
                      //2、生成sheet2内容
                      for(int j=0;j<dlData.length;j++){
                    if(index==0){ //第1个下拉选项，直接创建行、列
                        row = sheet2.createRow(j); //创建数据行
                        sheet2.setColumnWidth(j, 4000); //设置每列的列宽
                        row.createCell(0).setCellValue(dlData[j]); //设置对应单元格的值
                        
                    } else { //非第1个下拉选项
                        
                        int rowCount = sheet2.getLastRowNum();
                        //System.out.println("========== LastRowNum =========" + rowCount);
                        if(j<=rowCount){ //前面创建过的行，直接获取行，创建列
                            //获取行，创建列
                            sheet2.getRow(j).createCell(index).setCellValue(dlData[j]); //设置对应单元格的值
                            
                        } else { //未创建过的行，直接创建行、创建列
                            sheet2.setColumnWidth(j, 4000); //设置每列的列宽
                            //创建行、创建列
                            sheet2.createRow(j).createCell(index).setCellValue(dlData[j]); //设置对应单元格的值
                        }
                    }
                }
                      index++;
            }
        }
        return wb;
       }
        
        
        /**
	     * 
	     * @Title: SetDataValidation 
	     * @Description: 下拉列表元素很多的情况 (255以上的下拉)
	     * @param @param strFormula
	     * @param @param firstRow   起始行
	     * @param @param endRow     终止行
	     * @param @param firstCol   起始列
	     * @param @param endCol     终止列
	     * @param @return
	     * @return HSSFDataValidation
	     * @throws
	     */
	    private static HSSFDataValidation SetDataValidation(String strFormula, 
	            int firstRow, int endRow, int firstCol, int endCol) {
	        
	        // 设置数据有效性加载在哪个单元格上。四个参数分别是：起始行、终止行、起始列、终止列
	        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
	        DVConstraint constraint = DVConstraint.createFormulaListConstraint(strFormula);
	        HSSFDataValidation dataValidation = new HSSFDataValidation(regions,constraint);
	        
	        dataValidation.createErrorBox("Error", "Error");
	        dataValidation.createPromptBox("", null);
	    
	        return dataValidation;
	    }
	    
	    
	    /**
	     * 
	     * @Title: setDataValidation 
	     * @Description: 下拉列表元素不多的情况(255以内的下拉)
	     * @param @param sheet
	     * @param @param textList
	     * @param @param firstRow
	     * @param @param endRow
	     * @param @param firstCol
	     * @param @param endCol
	     * @param @return
	     * @return DataValidation
	     * @throws
	     */
	    private static DataValidation setDataValidation(Sheet sheet, String[] textList, int firstRow, int endRow, int firstCol, int endCol) {

	        DataValidationHelper helper = sheet.getDataValidationHelper();
	        //加载下拉列表内容
	        DataValidationConstraint constraint = helper.createExplicitListConstraint(textList);
	        //DVConstraint constraint = new DVConstraint();
	        constraint.setExplicitListValues(textList);
	        
	        //设置数据有效性加载在哪个单元格上。四个参数分别是：起始行、终止行、起始列、终止列
	        CellRangeAddressList regions = new CellRangeAddressList((short) firstRow, (short) endRow, (short) firstCol, (short) endCol);
	    
	        //数据有效性对象
	        DataValidation data_validation = helper.createValidation(constraint, regions);
	        //DataValidation data_validation = new DataValidation(regions, constraint);
	    
	        return data_validation;
	    }

}
