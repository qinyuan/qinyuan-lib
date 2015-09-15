package com.qinyuan.lib.image;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to validate redundancy of images by database
 * Created by qinyuan on 15-3-18.
 */
public class DatabaseRedundantImageValidator implements RedundantImageValidator {
    private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseRedundantImageValidator.class);
    private List<ImageColumn> columns;
    private List<String> thumbnailSuffixes = ThumbnailSuffix.getDefaultSuffixes();

    public void setColumns(List<String> tableColumns) {
        this.columns = new ArrayList<>();
        for (String tableColumn : tableColumns) {
            String[] tableColumnArray = tableColumn.split("\\.");
            if (tableColumnArray.length >= 2) {
                ImageColumn imageColumn = new ImageColumn(tableColumnArray[0], tableColumnArray[1]);
                this.columns.add(imageColumn);
                LOGGER.info("Add {} as validation column.", tableColumn);
            }
        }
    }

    public void setThumbnailSuffixes(List<String> thumbnailSuffixes) {
        this.thumbnailSuffixes = thumbnailSuffixes;
    }

    @Override
    public boolean isRedundant(String imagePath) {
        if (StringUtils.isBlank(imagePath)) {
            return false;
        }

        if (this.columns == null || this.columns.size() == 0) {
            return false;
        }

        if (this.thumbnailSuffixes != null) {
            for (String thumbnailSuffix : this.thumbnailSuffixes) {
                imagePath = imagePath.replace(thumbnailSuffix, "");
            }
        }

        for (ImageColumn column : this.columns) {
            String argumentName = column.column + RandomStringUtils.randomNumeric(10);
            int count = new HibernateListBuilder().addFilter(column.column + " LIKE :" + argumentName)
                    .addArgument(argumentName, "%" + imagePath + "%").count(column.table);
            if (count > 0) {
                return false;
            }
        }
        return true;
    }

    private static class ImageColumn {
        String table;
        String column;

        ImageColumn(String table, String column) {
            this.table = table;
            this.column = column;
        }
    }
}
