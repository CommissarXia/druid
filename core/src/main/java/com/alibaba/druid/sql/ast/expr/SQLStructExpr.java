package com.alibaba.druid.sql.ast.expr;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLExprImpl;
import com.alibaba.druid.sql.ast.SQLStructDataType;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

public class SQLStructExpr extends SQLExprImpl {
    private SQLStructDataType dataType;
    private final List<SQLAliasedExpr> items = new ArrayList<>();

    public SQLStructDataType getDataType() {
        return dataType;
    }

    public void setDataType(SQLStructDataType x) {
        x.setParent(this);
        this.dataType = x;
    }

    public void addItem(SQLAliasedExpr item) {
        item.setParent(this);
        items.add(item);
    }

    public void addItem(SQLExpr item, String alias) {
        items.add(
                new SQLAliasedExpr(item, alias));
    }

    public List<SQLAliasedExpr> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    protected void accept0(SQLASTVisitor v) {
        if (v.visit(this)) {
            acceptChild(v, items);
        }
        v.endVisit(this);
    }

    @Override
    public SQLStructExpr clone() {
        SQLStructExpr x = new SQLStructExpr();
       cloneTo(x);
        return x;
    }

    protected void cloneTo(SQLStructExpr x) {
        for (SQLAliasedExpr item : items) {
            x.addItem(item.clone());
        }
    }
}
