package com.sinosoft.one.monitor.os.linux.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QOsAvailabletemp is a Querydsl query type for OsAvailabletemp
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QOsAvailabletemp extends EntityPathBase<OsAvailabletemp> {

    private static final long serialVersionUID = 1047680940;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QOsAvailabletemp osAvailabletemp = new QOsAvailabletemp("osAvailabletemp");

    public final StringPath id = createString("id");

    public final QOs os;

    public final DateTimePath<java.util.Date> recordTime = createDateTime("recordTime", java.util.Date.class);

    public final StringPath status = createString("status");

    public QOsAvailabletemp(String variable) {
        this(OsAvailabletemp.class, forVariable(variable), INITS);
    }

    public QOsAvailabletemp(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QOsAvailabletemp(PathMetadata<?> metadata, PathInits inits) {
        this(OsAvailabletemp.class, metadata, inits);
    }

    public QOsAvailabletemp(Class<? extends OsAvailabletemp> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.os = inits.isInitialized("os") ? new QOs(forProperty("os")) : null;
    }

}

