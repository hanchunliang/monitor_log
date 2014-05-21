package com.sinosoft.one.monitor.os.linux.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QOsCpu is a Querydsl query type for OsCpu
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QOsCpu extends EntityPathBase<OsCpu> {

    private static final long serialVersionUID = 2045013495;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QOsCpu osCpu = new QOsCpu("osCpu");

    public final StringPath blockProcess = createString("blockProcess");

    public final StringPath cpuIdle = createString("cpuIdle");

    public final StringPath id = createString("id");

    public final StringPath interRupt = createString("interRupt");

    public final StringPath ioWait = createString("ioWait");

    public final QOs os;

    public final StringPath runQueue = createString("runQueue");

    public final DateTimePath<java.util.Date> sampleDate = createDateTime("sampleDate", java.util.Date.class);

    public final StringPath sysTime = createString("sysTime");

    public final StringPath userTime = createString("userTime");

    public final StringPath utiliZation = createString("utiliZation");

    public QOsCpu(String variable) {
        this(OsCpu.class, forVariable(variable), INITS);
    }

    public QOsCpu(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QOsCpu(PathMetadata<?> metadata, PathInits inits) {
        this(OsCpu.class, metadata, inits);
    }

    public QOsCpu(Class<? extends OsCpu> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.os = inits.isInitialized("os") ? new QOs(forProperty("os")) : null;
    }

}

