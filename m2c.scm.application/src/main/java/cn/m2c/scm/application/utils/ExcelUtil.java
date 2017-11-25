package cn.m2c.scm.application.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

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
        response.setHeader("Content-Disposition", "attachment;filename=" + urlEncode(fileName));
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
}
