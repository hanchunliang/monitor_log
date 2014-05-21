package com.sinosoft.one.monitor.os.linux.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QOsAvailabile is a Querydsl query type for OsAvailabile
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QOsAvailabile extends EntityPathBase<OsAvailable> {

    private static final long serialVersionUID = 1537749283;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QOsAvailabile osAvailabile = new QOsAvailabile("osAvailabile");

    public final StringPath aveFaultTime = createString("aveFaultTime");

    public final StringPath aveRepairTime = createString("aveRepairTime");

    public final StringPath crashTime = createString("crashTime");

    public final StringPath id = createString("id");

    public final StringPath normalRun = createString("normalRun");

    public final QOs os;

    public final StringPath timeSpan = createString("timeSpan");

    public QOsAvailabile(String variable) {
        this(OsAvailable.class, forVariable(variable), INITS);
    }

    public QOsAvailabile(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QOsAvailabile(PathMetadata<?> metadata, PathInits inits) {
        this(OsAvailable.class, metadata, inits);
    }

    public QOsAvailabile(Class<? extends OsAvailable> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.os = inits.isInitialized("os") ? new QOs(forProperty("os")) : null;
    }

}

