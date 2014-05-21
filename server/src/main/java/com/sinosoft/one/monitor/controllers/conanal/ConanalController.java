package com.sinosoft.one.monitor.controllers.conanal;

import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sinosoft.one.monitor.application.domain.ApplicationService;
import com.sinosoft.one.monitor.application.model.Application;
import com.sinosoft.one.monitor.conanal.domain.ConanalService;
import com.sinosoft.one.monitor.conanal.model.TableModel;
import com.sinosoft.one.monitor.conanal.util.PdfParagraph;
import com.sinosoft.one.monitor.conanal.util.StrHelp;
import com.sinosoft.one.monitor.logquery.domain.LogqueryService;
import com.sinosoft.one.monitor.logquery.model.GridRow;
import com.sinosoft.one.monitor.logquery.model.RowEntity;
import com.sinosoft.one.monitor.logquery.model.UrlTraceLogEntity;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.mvc.web.instruction.reply.transport.Json;
import org.springframework.beans.factory.annotation.Autowired;

import com.lowagie.text.*;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: king-bj
 * Date: 13-11-2
 * Time: 下午10:47
 * To change this template use File | Settings | File Templates.
 */

@Path
public class ConanalController {

    @Autowired
    private ConanalService conanalService;

    @Autowired
    private ApplicationService applicationService;

    @Get("conanal")
    public String getView() {
        return "conanal";
    }

    @Post("exportTable")
    @Get("exportTable")
    public void exportTable(Invocation inv) {
        List<TableModel> tableModelList = new ArrayList<TableModel>();
        tableModelList = conanalService.getCount();
        OutputStream out = null;
        //DataOutputStream dos = null;
        InputStream is = null;
        BufferedInputStream reader = null;
        String[] headers = { "应用名称", "URL地址", "操作信息", "URL访问次数" };
        try {
            out = new FileOutputStream("E://a.pdf");
            exportPdf("所有区域的应用url访问次数统计报表", headers, tableModelList, out, "yyyy-MM-dd");
            out.close();
            //下载
            inv.getResponse().setContentType("application/pdf");
            inv.getResponse().setHeader("Content-disposition", "attachment;filename="+new String("统计报表下载.pdf".getBytes("UTF-8"), "ISO-8859-1"));
            //dos = new DataOutputStream(inv.getResponse().getOutputStream());
            OutputStream outputStream = inv.getResponse().getOutputStream();
            is = new FileInputStream(new File("E://a.pdf"));
            //reader = new BufferedInputStream(new FileInputStream("E://a.pdf"));
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            byte[] temp = new byte[1024];
            int size = 0;
            while ((size = is.read(temp)) != -1) {
                byteArray.write(temp, 0, size);
            }
            is.close();
            //byte[] content = byteArray.toByteArray();
            byteArray.writeTo(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportPdf(String title, String[] headers, List<TableModel> tableModelList, OutputStream out, String pattern) {
        Rectangle rectPageSize = new Rectangle(PageSize.A4);
        Document document = new Document(rectPageSize, 50, 50, 50, 50);
        try {
            PdfWriter.getInstance(document, out);
            document.addTitle(StrHelp.getChinese(title));
            document.addSubject("export information");
            document.addAuthor("leno");
            document.addCreator("leno");
            document.addKeywords("pdf itext");

            HeaderFooter header = new HeaderFooter(new PdfParagraph(title, 20, false), false);
            header.setAlignment(Element.ALIGN_CENTER);
            //HeaderFooter footer = new HeaderFooter(new Phrase(new String("第 ".getBytes("UTF-8"), "ISO-8859-1")), new Phrase(new String(" 页".getBytes("UTF-8"), "ISO-8859-1")));
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            Font chineseFont= new Font(bfChinese, 12, Font.NORMAL);
            HeaderFooter footer = new HeaderFooter(new Phrase("第 ", chineseFont), new Phrase(" 页", chineseFont));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.setHeader(header);
            document.setFooter(footer);

            document.open();

            PdfPTable table = new PdfPTable(headers.length);
            table.setWidthPercentage(18 * headers.length);

            // 产生表格标题行
            for (int i = 0; i < headers.length; i++) {
                PdfPCell cell = new PdfPCell(new PdfParagraph(headers[i], 14, true));
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cell.setBorderColor(Color.black);
                table.addCell(cell);
            }

            for(int i = 0; i < tableModelList.size(); i++) {
                PdfPCell appNameCell = new PdfPCell(new PdfParagraph(tableModelList.get(i).getApplicationName()));
                appNameCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                appNameCell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(appNameCell);
                PdfPCell urlAreaCell = new PdfPCell(new PdfParagraph(tableModelList.get(i).getUrl()));
                urlAreaCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                urlAreaCell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(urlAreaCell);
                //先把id作为操作信息吧，我刚想起来 url中的描述字段是oracle的关键字，得改下
                PdfPCell urlOptInfoCell = new PdfPCell(new PdfParagraph(tableModelList.get(i).getUrlId()));
                urlOptInfoCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                urlOptInfoCell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(urlOptInfoCell);
                PdfPCell lookCountCell = new PdfPCell(new PdfParagraph(tableModelList.get(i).getUrlCount()));
                lookCountCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                lookCountCell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(lookCountCell);
            }
            document.add(table);
            document.close();
        } catch (Exception e) {

        }
    }

}
