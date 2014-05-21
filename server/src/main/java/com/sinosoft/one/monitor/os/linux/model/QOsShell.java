package com.sinosoft.one.monitor.os.linux.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QOsShell is a Querydsl query type for OsShell
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QOsShell extends EntityPathBase<OsShell> {

    private static final long serialVersionUID = -1822526785;

    public static final QOsShell osShell = new QOsShell("osShell");

    public final StringPath id = createString("id");

    public final SimplePath<java.sql.Clob> template = createSimple("template", java.sql.Clob.class);

    public final StringPath type = createString("type");

    public QOsShell(String variable) {
        super(OsShell.class, forVariable(variable));
    }

    public QOsShell(Path<? extends OsShell> entity) {
        super(entity.getType(), entity.getMetadata());
    }

    public QOsShell(PathMetadata<?> metadata) {
        super(OsShell.class, metadata);
    }

}

