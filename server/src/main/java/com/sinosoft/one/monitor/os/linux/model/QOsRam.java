package com.sinosoft.one.monitor.os.linux.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QOsRam is a Querydsl query type for OsRam
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QOsRam extends EntityPathBase<OsRam> {

    private static final long serialVersionUID = 2045027437;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QOsRam osRam = new QOsRam("osRam");

    public final StringPath id = createString("id");

    public final StringPath memTotal = createString("memTotal");

    public final StringPath memUsed = createString("memUsed");

    public final StringPath memUtiliZation = createString("memUtiliZation");

    public final QOs os;

    public final DateTimePath<java.util.Date> sampleDate = createDateTime("sampleDate", java.util.Date.class);

    public final StringPath swapTotal = createString("swapTotal");

    public final StringPath swapUsed = createString("swapUsed");

    public final StringPath swapUtiliZation = createString("swapUtiliZation");

    public QOsRam(String variable) {
        this(OsRam.class, forVariable(variable), INITS);
    }

    public QOsRam(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QOsRam(PathMetadata<?> metadata, PathInits inits) {
        this(OsRam.class, metadata, inits);
    }

    public QOsRam(Class<? extends OsRam> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.os = inits.isInitialized("os") ? new QOs(forProperty("os")) : null;
    }

}

