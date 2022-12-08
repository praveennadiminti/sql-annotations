package com.mtheron.anntp.schema;

import com.mtheron.anntp.annotation.GetInsertStatement;
import javax.persistence.Column;
import javax.persistence.Table;

@GetInsertStatement
@Table(name = "testCase2table")
public class TestCase2 {

    @Column(name="column1name")
    private String column1;
    @Column(name = "column2name")
    private String column2;
    @Column(name = "column3name")
    private String column3;

    public String getColumn1() {
        return column1;
    }

    public String getColumn2() {
        return column2;
    }

    public String getColumn3() {
        return column3;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public void setColumn2(String column2) {
        this.column2 = column2;
    }

    public void setColumn3(String column3) {
        this.column3 = column3;
    }
}
