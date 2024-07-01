package com.hzw.utils;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelMergeCustomerCellHandler implements CellWriteHandler {
    /**
     * 一级合并的列，从0开始算
     */
    private int[] mergeColIndex;

    /**
     * 从指定的行开始合并，从0开始算
     */
    private int mergeRowIndex;

    /**
     * 在单元格上的所有操作完成后调用，遍历每一个单元格，判断是否需要向上合并
     */
    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        // 获取当前单元格行下标
        int currRowIndex = cell.getRowIndex();
        // 获取当前单元格列下标
        int currColIndex = cell.getColumnIndex();
        // 判断是否大于指定行下标，如果大于则判断列是否也在指定的需要的合并单元列集合中
        if (currRowIndex > mergeRowIndex) {
            for (int i = 0; i < mergeColIndex.length; i++) {
                if (currColIndex == mergeColIndex[i]) {
                    if(currColIndex <= 18){
                        // 一级合并唯一标识
                        Object currLevelOneCode = cell.getRow().getCell(0).getStringCellValue();
                        Object preLevelOne = cell.getSheet().getRow(currRowIndex).getCell(0).getStringCellValue();
                        Object preLevelOneCode = cell.getSheet().getRow(currRowIndex - 1).getCell(0).getStringCellValue();
                        // 判断两条数据的是否是同一集合，只有同一集合的数据才能合并单元格
                        //  if(preLevelOneCode.equals(currLevelOneCode)){
                        // 如果都符合条件，则向上合并单元格
                        mergeWithPrevRow(writeSheetHolder, cell, currRowIndex, currColIndex);
                        break;
                        //    }
                    }else{
                        // 一级合并唯一标识
                        Object currLevelOneCode = cell.getRow().getCell(0).getStringCellValue();
                        Object preLevelOneCode = cell.getSheet().getRow(currRowIndex - 1).getCell(0).getStringCellValue();
                        // 二级合并唯一标识
                        Object currLevelTwoCode = cell.getRow().getCell(19).getStringCellValue();
                        Object preLevelTwoCode = cell.getSheet().getRow(currRowIndex - 1).getCell(19).getStringCellValue();
                        if(preLevelOneCode.equals(currLevelOneCode)&&preLevelTwoCode.equals(currLevelTwoCode)){
                            // 如果都符合条件，则向上合并单元格
                            mergeWithPrevRow(writeSheetHolder, cell, currRowIndex, currColIndex);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * 当前单元格向上合并
     *
     * @param writeSheetHolder 表格处理句柄
     * @param cell             当前单元格
     * @param currRowIndex     当前行
     * @param currColIndex     当前列
     */
    private void mergeWithPrevRow(WriteSheetHolder writeSheetHolder, Cell cell, int currRowIndex, int currColIndex) {
        // 获取当前单元格数值
        Object currData = cell.getCellTypeEnum() == CellType.STRING ? cell.getStringCellValue() : cell.getNumericCellValue();
        // 获取当前单元格正上方的单元格对象
        Cell preCell = cell.getSheet().getRow(currRowIndex - 1).getCell(currColIndex);
        // 获取当前单元格正上方的单元格的数值
        Object preData = preCell.getCellTypeEnum() == CellType.STRING ? preCell.getStringCellValue() : preCell.getNumericCellValue();
        // 将当前单元格数值与其正上方单元格的数值比较
        if (preData.equals(currData)) {
            Sheet sheet = writeSheetHolder.getSheet();
            List<CellRangeAddress> mergeRegions = sheet.getMergedRegions();
            // 当前单元格的正上方单元格是否是已合并单元格
            boolean isMerged = false;
            for (int i = 0; i < mergeRegions.size() && !isMerged; i++) {
                CellRangeAddress address = mergeRegions.get(i);
                // 若上一个单元格已经被合并，则先移出原有的合并单元，再重新添加合并单元
                if (address.isInRange(currRowIndex - 1, currColIndex)) {
                    sheet.removeMergedRegion(i);
                    address.setLastRow(currRowIndex);
                    sheet.addMergedRegion(address);
                    isMerged = true;
                }
            }
            // 若上一个单元格未被合并，则新增合并单元
            if (!isMerged) {
                CellRangeAddress cellRangeAddress = new CellRangeAddress(currRowIndex - 1, currRowIndex, currColIndex, currColIndex);
                sheet.addMergedRegion(cellRangeAddress);
            }
        }
    }
}

