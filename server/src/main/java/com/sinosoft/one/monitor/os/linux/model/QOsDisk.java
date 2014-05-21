package com.sinosoft.one.monitor.os.linux.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QOsDisk is a Querydsl query type for OsDisk
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QOsDisk extends EntityPathBase<OsDisk> {

    private static final long serialVersionUID = -1029067986;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QOsDisk osDisk = new QOsDisk("osDisk");

    public final StringPath diskPath = createString("diskPath");

    public final StringPath free = createString("free");

    public final StringPath freeUtiliZation = createString("freeUtiliZation");

    public final StringPath id = createString("id");

    public final QOs os;

    public final DateTimePath<java.util.Date> sampleDate = createDateTime("sampleDate", java.util.Date.class);

    public final StringPath total = createString("total");

    public final StringPath used = createString("used");

    public final StringPath usedUtiliZation = createString("usedUtiliZation");

    public QOsDisk(String variable) {
        this(OsDisk.class, forVariable(variable), INITS);
    }

    public QOsDisk(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QOsDisk(PathMetadata<?> metadata, PathInits inits) {
        this(OsDisk.class, metadata, inits);
    }

    public QOsDisk(Class<? extends OsDisk> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.os = inits.isInitialized("os") ? new QOs(forProperty("os")) : null;
    }

}

