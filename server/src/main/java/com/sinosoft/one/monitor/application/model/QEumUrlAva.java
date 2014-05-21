package com.sinosoft.one.monitor.application.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QEumUrlAva is a Querydsl query type for EumUrlAva
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QEumUrlAva extends EntityPathBase<EumUrlAva> {

    private static final long serialVersionUID = -475661090;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QEumUrlAva eumUrlAva = new QEumUrlAva("eumUrlAva");

    public final QEumUrl eumUrl;

    public final StringPath id = createString("id");

    public final DateTimePath<java.util.Date> recordTime = createDateTime("recordTime", java.util.Date.class);

    public final StringPath state = createString("state");

    public QEumUrlAva(String variable) {
        this(EumUrlAva.class, forVariable(variable), INITS);
    }

    public QEumUrlAva(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QEumUrlAva(PathMetadata<?> metadata, PathInits inits) {
        this(EumUrlAva.class, metadata, inits);
    }

    public QEumUrlAva(Class<? extends EumUrlAva> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.eumUrl = inits.isInitialized("eumUrl") ? new QEumUrl(forProperty("eumUrl")) : null;
    }

}

